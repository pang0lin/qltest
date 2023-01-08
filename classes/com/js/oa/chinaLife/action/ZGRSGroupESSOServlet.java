package com.js.oa.chinaLife.action;

import com.ultrapower.casp.client.LoginUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.ResourceBundle;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class ZGRSGroupESSOServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  private static Map<String, String> ldapInfo = null;
  
  private String configFile = "";
  
  public void init() throws ServletException {
    super.init();
    ResourceBundle resource = ResourceBundle.getBundle("zgrs_essoconfig");
    this.configFile = resource.getObject("filePath").toString();
    LoginUtil.getInstance().init(this.configFile);
    ldapInfo = getLdapInfo();
  }
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    if (LoginUtil.getInstance().isEnable()) {
      String uid = request.getSession().getAttribute("userAccount").toString();
      Hashtable<Object, Object> user = new Hashtable<Object, Object>();
      InitialLdapContext ctx = null;
      Hashtable<Object, Object> env = new Hashtable<Object, Object>();
      env.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
      env.put("java.naming.provider.url", ldapInfo.get("url"));
      env.put("java.naming.security.authentication", ldapInfo.get("authentication"));
      env.put("java.naming.security.principal", ldapInfo.get("manager"));
      env.put("java.naming.security.credentials", ldapInfo.get("password"));
      String sapuid = "";
      try {
        ctx = new InitialLdapContext(env, null);
        SearchControls constraints = new SearchControls();
        constraints.setSearchScope(2);
        NamingEnumeration<SearchResult> namingenumeration = null;
        String query = "(uid=" + uid + ")";
        namingenumeration = ctx.search(ldapInfo.get("root"), query, constraints);
        if (namingenumeration != null && namingenumeration.hasMore()) {
          SearchResult sr = namingenumeration.next();
          String dn = String.valueOf(sr.getName()) + "," + (String)ldapInfo.get("root");
          String[] attArrays = { "sapuid" };
          Attributes ar = ctx.getAttributes(dn, attArrays);
          Attribute attr = ar.get("sapuid");
          if (attr != null)
            sapuid = attr.get().toString(); 
        } 
      } catch (Exception e) {
        e.printStackTrace(System.out);
      } finally {
        try {
          ctx.close();
        } catch (Exception e) {
          e.printStackTrace(System.out);
        } 
      } 
      String resNum = request.getParameter("resNum");
      String corpCode = request.getParameter("corpCode");
      String appUrlInfo = LoginUtil.getInstance().getCorpSSOUrl(sapuid, resNum, corpCode);
      if (appUrlInfo != null && appUrlInfo != "")
        if (!appUrlInfo.startsWith("1001"))
          response.sendRedirect(appUrlInfo);  
    } 
  }
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
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
}
