package com.js.ldap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class OpenLDAP {
  private DirContext ctx = null;
  
  private static Map<String, String> ldapInfo = null;
  
  private Long curTime = Long.valueOf(0L);
  
  public OpenLDAP() {
    getLdapInfo();
  }
  
  public static void main(String[] args) {
    OpenLDAP openDSExamples = new OpenLDAP();
    openDSExamples.authenticate("888", "111111");
  }
  
  public void queryItem() {
    this.curTime = Long.valueOf((new Date()).getTime());
    List<Map<String, String>> userList = new ArrayList<Map<String, String>>();
    String filter = "(&(|(objectClass=person)))";
    try {
      String[] attr = { "cn", "uid", "mail" };
      NamingEnumeration<SearchResult> em = getFilter(ldapInfo.get("LdapBase"), filter, attr, 2);
      while (em != null && em.hasMore()) {
        Map<String, String> map = new HashMap<String, String>();
        SearchResult result = em.next();
        Attributes attrs = result.getAttributes();
        map.put("nameStr", result.getName());
        if (attrs.size() != 0) {
          String attValue = null;
          for (NamingEnumeration<? extends Attribute> namingEnum_1 = attrs.getAll(); namingEnum_1.hasMoreElements(); ) {
            Attribute attribute = namingEnum_1.next();
            String attID = attribute.getID();
            for (NamingEnumeration<?> namingEnum_2 = attribute.getAll(); namingEnum_2.hasMoreElements(); ) {
              attValue = (String)namingEnum_2.nextElement();
              if (attID.equals("ou") || attID.equals("cn")) {
                StringBuffer buffer = new StringBuffer();
                byte[] b = attValue.toString().getBytes();
                for (int i = 0; i < b.length; i++)
                  buffer.append(b[i]); 
                map.put("objectGUID", buffer.toString());
                map.put(attID, attValue);
                continue;
              } 
              if (map.get(attID) != null) {
                map.put(attID, String.valueOf(map.get(attID)) + ", " + attValue);
                continue;
              } 
              map.put(attID, attValue);
            } 
          } 
        } 
        if (map.get("ou") == null)
          userList.add(map); 
      } 
      (new OpenLDAPToOA()).userOperate(userList, ldapInfo, this.curTime);
    } catch (Exception e) {
      System.out.println("从openDS取值出现错误！");
      e.printStackTrace();
    } 
    System.out.println("共有" + userList.size() + "人");
  }
  
  public String authenticate(String account, String password) {
    String valide = "-1";
    String filter = "(&(|(objectClass=person))(uid=" + account + "))";
    List<String> userList = getfilterNodes(ldapInfo.get("LdapBase"), filter, 2);
    if (userList.size() < 1) {
      System.err.println(String.valueOf(account) + "  账号不存在！");
      valide = "-1";
    } else {
      for (int i = 0; i < userList.size() && !"1".equals(valide); i++) {
        try {
          this.ctx.addToEnvironment("java.naming.security.principal", userList.get(i));
          this.ctx.addToEnvironment("java.naming.security.credentials", password);
          ((InitialLdapContext)this.ctx).reconnect(null);
          valide = "1";
        } catch (Exception e) {
          valide = "0";
        } 
      } 
    } 
    return valide;
  }
  
  public List<String> getfilterNodes(String dn, String filter, int level) {
    List<String> list = new ArrayList<String>();
    String[] attr = { "cn", "uid" };
    try {
      NamingEnumeration<SearchResult> em = getFilter(dn, filter, null, level);
      while (em != null && em.hasMoreElements()) {
        SearchResult rs = em.nextElement();
        list.add(String.valueOf(rs.getName()) + "," + dn);
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return list;
  }
  
  private Map<String, String> getLdapInfoFile() {
    Map<String, String> map = new HashMap<String, String>();
    try {
      String path = System.getProperty("user.dir");
      String filePath = String.valueOf(path) + "/jsconfig/sysconfig.xml";
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(filePath);
      Element ldapConfig = doc.getRootElement().getChild("userConfig");
      if (ldapConfig != null) {
        map.put("ldapUse", ldapConfig.getAttribute("use").getValue());
        map.put("ldapType", ldapConfig.getAttribute("type").getValue());
        if (!"0".equals(map.get("ldapUse"))) {
          if ("openDS".equalsIgnoreCase(map.get("ldapType"))) {
            map.put("initial", (ldapConfig.getChild("initial") == null) ? "" : ldapConfig.getChild("initial").getAttributeValue("value"));
            map.put("url", (ldapConfig.getChild("url") == null) ? "" : ldapConfig.getChild("url").getAttributeValue("value"));
            map.put("authentication", (ldapConfig.getChild("authentication") == null) ? "" : ldapConfig.getChild("authentication").getAttributeValue("value"));
            map.put("credentials", (ldapConfig.getChild("credentials") == null) ? "" : ldapConfig.getChild("credentials").getAttributeValue("value"));
            map.put("principal", (ldapConfig.getChild("principal") == null) ? "" : ldapConfig.getChild("principal").getAttributeValue("value"));
            map.put("LdapBase", (ldapConfig.getChild("LdapBase") == null) ? "" : ldapConfig.getChild("LdapBase").getAttributeValue("value"));
          } else {
            map.put("dataSource", (ldapConfig.getChild("dataSource") == null) ? "" : ldapConfig.getChildText("dataSource"));
            map.put("onceNum", (ldapConfig.getChild("onceNum") == null) ? "100" : ldapConfig.getChildText("onceNum"));
            map.put("allUserSql", (ldapConfig.getChild("allUserSql") == null) ? "" : ldapConfig.getChildText("allUserSql"));
            map.put("oneUseSql", (ldapConfig.getChild("oneUseSql") == null) ? "" : ldapConfig.getChildText("oneUseSql"));
          } 
          map.put("AutoSync", (ldapConfig.getChild("AutoSync") == null) ? "" : ldapConfig.getChild("AutoSync").getAttributeValue("value"));
          map.put("staticTime", (ldapConfig.getChild("AutoSync") == null) ? "" : ldapConfig.getChild("AutoSync").getAttributeValue("staticTime"));
          map.put("interval", (ldapConfig.getChild("AutoSync") == null) ? "-1" : ldapConfig.getChild("AutoSync").getAttributeValue("interval"));
        } 
      } 
    } catch (JDOMException e) {
      System.out.println("检查是否有/jsconfig/sysconfig.xml文件");
    } catch (IOException e) {
      System.out.println("检查是否有/jsconfig/sysconfig.xml文件");
    } 
    return map;
  }
  
  private void createLdapContext() {
    if (ldapInfo != null && "1".equals(ldapInfo.get("ldapUse"))) {
      Hashtable<Object, Object> env = new Hashtable<Object, Object>();
      env.put("java.naming.factory.initial", ldapInfo.get("initial"));
      env.put("java.naming.provider.url", ldapInfo.get("url"));
      env.put("java.naming.security.authentication", ldapInfo.get("authentication"));
      env.put("java.naming.security.principal", ldapInfo.get("principal"));
      env.put("java.naming.security.credentials", ldapInfo.get("credentials"));
      try {
        this.ctx = new InitialLdapContext(env, null);
        System.out.println("openDS 认证成功!");
      } catch (Exception e) {
        System.err.println("openDS 认证出错!");
        e.printStackTrace();
      } 
    } else {
      System.err.println("openDS 同步未开启！");
    } 
  }
  
  private NamingEnumeration getFilter(String DN, String filter, int level) throws Exception {
    return getFilter(DN, filter, null, level);
  }
  
  private NamingEnumeration getFilter(String DN, String filter, String[] attrs, int level) throws Exception {
    SearchControls searchCons = new SearchControls();
    searchCons.setSearchScope(level);
    searchCons.setCountLimit(0L);
    searchCons.setTimeLimit(0);
    if (attrs != null)
      searchCons.setReturningAttributes(attrs); 
    if (this.ctx == null)
      createLdapContext(); 
    NamingEnumeration<SearchResult> em = this.ctx.search(DN, filter, searchCons);
    return em;
  }
  
  public Map<String, String> getLdapInfo() {
    if (ldapInfo == null)
      ldapInfo = getLdapInfoFile(); 
    return ldapInfo;
  }
  
  public void setLdapInfo(Map<String, String> ldapInfo) {
    OpenLDAP.ldapInfo = ldapInfo;
  }
}
