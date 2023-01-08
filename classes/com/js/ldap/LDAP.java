package com.js.ldap;

import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class LDAP {
  protected InitialLdapContext cnt = null;
  
  protected Control[] connCtls = null;
  
  protected static Hashtable table = null;
  
  protected static int interval;
  
  protected static int autoSync;
  
  protected static String base;
  
  protected static int useLDAP = 0;
  
  protected static int useCert = 1;
  
  public static int getUseCert() {
    return useCert;
  }
  
  public LDAP() {
    if (useLDAP == 0)
      init(); 
  }
  
  private void init() {
    try {
      table = new Hashtable<Object, Object>();
      String path = System.getProperty("user.dir");
      String filePath = String.valueOf(path) + "/jsconfig/sysconfig.xml";
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(filePath);
      Element ldapConfig = doc.getRootElement().getChild("LdapConfig");
      if (ldapConfig != null) {
        useLDAP = Integer.parseInt(ldapConfig.getAttribute("use").getValue());
        if (ldapConfig.getAttribute("useCert") != null)
          useCert = Integer.parseInt(ldapConfig.getAttribute("useCert").getValue()); 
        if (useLDAP == 1) {
          Element element = ldapConfig.getChild("ContextProperty");
          List<Element> elementProperties = element.getChildren();
          int i;
          for (i = 0; i < elementProperties.size(); i++) {
            String propertyName = ((Element)elementProperties.get(i))
              .getAttribute(
                "name").getValue();
            String propertyValue = ((Element)elementProperties.get(i))
              .getAttribute(
                "value").getValue();
            table.put(propertyName, propertyValue);
          } 
          element = ldapConfig.getChild("SystemProperty");
          elementProperties = element.getChildren();
          for (i = 0; i < elementProperties.size(); i++) {
            String propertyName = ((Element)elementProperties.get(i))
              .getAttribute(
                "name").getValue();
            String propertyValue = ((Element)elementProperties.get(i))
              .getAttribute(
                "value").getValue();
            System.setProperty(propertyName, propertyValue);
          } 
          base = ldapConfig.getChild("LdapBase")
            .getAttributeValue(
              "value");
          autoSync = Integer.parseInt(ldapConfig.getChild(
                "AutoSync")
              .getAttributeValue("value"));
          interval = Integer.parseInt(
              
              !ldapConfig.getChild("AutoSync").getAttributeValue("interval").equals("") ? ldapConfig.getChild(
                "AutoSync")
              .getAttributeValue("interval") : "-1");
        } 
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  protected void getContext() throws Exception {
    if (table == null)
      init(); 
    try {
      if (useCert == 0) {
        Properties env = new Properties();
        env.put("java.naming.factory.initial", table.get("java.naming.factory.initial"));
        env.put("java.naming.security.authentication", table.get("java.naming.security.authentication"));
        env.put("java.naming.security.principal", table.get("java.naming.security.principal"));
        env.put("java.naming.security.credentials", table.get("java.naming.security.credentials"));
        env.put("java.naming.provider.url", table.get("java.naming.provider.url"));
        this.cnt = new InitialLdapContext(env, null);
      } else {
        this.connCtls = new Control[] { new BindConnectionControl() };
        this.cnt = new InitialLdapContext(table, this.connCtls);
      } 
    } catch (Exception err) {
      System.out.println("LDAP服务未配置");
    } 
  }
  
  public void queryItem(String filter) throws Exception {}
  
  public boolean setOrgLeader() {
    return false;
  }
  
  public boolean setUserLeader() {
    return false;
  }
  
  public boolean updatePass(String userName, String userPassword) {
    return false;
  }
  
  public boolean getAutoSync() {
    if (autoSync == 1)
      return true; 
    return false;
  }
  
  public int getInterval() {
    return interval;
  }
  
  public int getUseLDAP() {
    return useLDAP;
  }
  
  public String Authenticate(String account, String password) {
    return null;
  }
}
