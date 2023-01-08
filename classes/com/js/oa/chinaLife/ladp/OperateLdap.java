package com.js.oa.chinaLife.ladp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import javax.naming.AuthenticationException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class OperateLdap {
  private static DirContext ctx = null;
  
  private static Map<String, String> ldapInfo = null;
  
  private static String base = "cn=users,dc=clamc,dc=com";
  
  private DirContext getCtx() {
    if (ldapInfo == null)
      ldapInfo = getLdapInfo(); 
    base = ldapInfo.get("root");
    Hashtable<Object, Object> env = new Hashtable<Object, Object>();
    env.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
    env.put("java.naming.provider.url", ldapInfo.get("url"));
    env.put("java.naming.security.authentication", ldapInfo.get("authentication"));
    env.put("java.naming.security.principal", ldapInfo.get("manager"));
    env.put("java.naming.security.credentials", ldapInfo.get("password"));
    try {
      ctx = new InitialDirContext(env);
    } catch (AuthenticationException e) {
      System.out.println("ldap认证失败");
      e.printStackTrace();
    } catch (Exception e) {
      System.out.println("ldap认证出错");
      e.printStackTrace();
    } 
    return ctx;
  }
  
  public boolean addUser(String uid, Map<String, String> attMap) {
    boolean success = false;
    try {
      ctx = getCtx();
      BasicAttributes attrsbu = new BasicAttributes();
      BasicAttribute objclassSet = new BasicAttribute("objectclass");
      objclassSet.add("top");
      objclassSet.add("person");
      objclassSet.add("inetOrgPerson");
      objclassSet.add("organizationalPerson");
      objclassSet.add("chinalife-baseperson");
      attrsbu.put(objclassSet);
      for (String key : attMap.keySet())
        attrsbu.put(key, attMap.get(key)); 
      if (attMap.get("cn") == null)
        attrsbu.put("cn", uid); 
      if (attMap.get("sn") == null)
        attrsbu.put("sn", uid); 
      ctx.createSubcontext("uid=" + uid + "," + base, attrsbu);
      ctx.close();
      success = true;
    } catch (NamingException ex) {
      ex.printStackTrace();
    } 
    closeLdap();
    return success;
  }
  
  public boolean deleteUser(String uid) {
    boolean result = false;
    ctx = getCtx();
    String userDN = "uid=" + uid + "," + base;
    try {
      ctx.destroySubcontext(userDN);
      result = true;
    } catch (NamingException e) {
      e.printStackTrace();
    } 
    closeLdap();
    return result;
  }
  
  public boolean updateUser(String uid, Map<String, String> attMap) {
    boolean result = false;
    ctx = getCtx();
    BasicAttributes attrs = new BasicAttributes(true);
    for (String key : attMap.keySet())
      attrs.put(key, attMap.get(key)); 
    try {
      ctx.modifyAttributes("uid=" + uid + "," + base, 2, attrs);
      result = true;
    } catch (NamingException e) {
      e.printStackTrace();
    } 
    closeLdap();
    return result;
  }
  
  public String authenticateUser(String uid, String password) {
    String result = "-1";
    if (ldapInfo == null)
      ldapInfo = getLdapInfo(); 
    base = ldapInfo.get("root");
    Hashtable<Object, Object> authENV = new Hashtable<Object, Object>();
    authENV.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
    authENV.put("java.naming.provider.url", ldapInfo.get("url"));
    authENV.put("java.naming.security.authentication", ldapInfo.get("authentication"));
    authENV.put("java.naming.security.principal", "uid=" + uid + "," + base);
    authENV.put("java.naming.security.credentials", password);
    try {
      DirContext authCtx = new InitialDirContext(authENV);
      result = "0";
      authCtx.close();
      authENV.clear();
    } catch (AuthenticationException e) {
      result = "1";
      System.out.println("认证失败------------------------uid=" + uid + "," + base);
      e.printStackTrace();
    } catch (Exception e) {
      result = "1";
      System.out.println("认证出错------------------------uid=" + uid + "," + base);
      e.printStackTrace();
    } 
    return result;
  }
  
  public void findUser(String uid) {
    ctx = getCtx();
    SearchControls searchCtls = new SearchControls();
    searchCtls.setSearchScope(2);
    String searchFilter = "uid=" + uid;
    String searchBase = base;
    int totalResults = 0;
    try {
      NamingEnumeration<SearchResult> answer = ctx.search(searchBase, searchFilter, searchCtls);
      while (answer.hasMoreElements()) {
        SearchResult sr = answer.next();
        Attributes Attrs = sr.getAttributes();
        if (Attrs != null)
          try {
            for (NamingEnumeration<? extends Attribute> ne = Attrs.getAll(); ne.hasMore(); ) {
              Attribute Attr = ne.next();
              for (NamingEnumeration<?> e = Attr.getAll(); e.hasMore(); totalResults++)
                String str = e.next().toString(); 
            } 
          } catch (NamingException e) {
            e.printStackTrace();
          }  
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    closeLdap();
  }
  
  private static void closeLdap() {
    if (ctx != null)
      try {
        ctx.close();
      } catch (NamingException e) {
        e.printStackTrace();
      }  
  }
  
  private Map<String, String> getLdapInfo() {
    Map<String, String> map = new HashMap<String, String>();
    try {
      String path = System.getProperty("user.dir");
      String filePath = String.valueOf(path) + "/jsconfig/rsconfig.xml";
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(filePath);
      Element ldapConfig = doc.getRootElement().getChild("ldap");
      if (ldapConfig != null) {
        map.put("url", ldapConfig.getChildText("url"));
        map.put("root", ldapConfig.getChildText("root"));
        map.put("manager", ldapConfig.getChildText("manager"));
        map.put("authentication", ldapConfig.getChildText("authentication"));
        map.put("password", ldapConfig.getChildText("password"));
      } 
    } catch (JDOMException e) {
      System.out.println("检查是否有/jsconfig/rsconfig.xml文件");
    } catch (IOException e) {
      System.out.println("检查是否有/jsconfig/rsconfig.xml文件");
    } 
    return map;
  }
  
  public static void main(String[] args) {
    OperateLdap lo = new OperateLdap();
    Map<String, String> attMap = new HashMap<String, String>();
    attMap.put("displayName", "徐学云");
    attMap.put("sn", "徐学云");
    attMap.put("mail", "xxy@163.com");
    attMap.put("userPassword", "111111");
    lo.addUser("xxy", attMap);
  }
}
