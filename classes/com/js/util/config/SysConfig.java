package com.js.util.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class SysConfig {
  private static SysConfig sysConfig;
  
  private static String fileServerURL;
  
  private static Map ftpServerMap = null;
  
  private static String databaseType;
  
  private String ipAddress;
  
  public SysConfig() {
    init();
  }
  
  public SysConfig(String address) {
    this.ipAddress = address;
  }
  
  public static SysConfig getInstance() {
    if (sysConfig == null)
      sysConfig = new SysConfig(); 
    return sysConfig;
  }
  
  public static SysConfig getInstance(String address) {
    return sysConfig = new SysConfig(address);
  }
  
  public String getFileServerURL() {
    init();
    return fileServerURL;
  }
  
  public static String getDatabaseType() {
    if (sysConfig == null)
      sysConfig = new SysConfig(); 
    return databaseType;
  }
  
  public Map getFtpServer() {
    init();
    return ftpServerMap;
  }
  
  private void init() {
    try {
      String str2, str3, path = System.getProperty("user.dir");
      String configFile = String.valueOf(path) + "/jsconfig/sysconfig.xml";
      FileInputStream configFileInputStream = new FileInputStream(
          new File(configFile));
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(configFileInputStream);
      Element root = doc.getRootElement();
      Element node = null;
      if (this.ipAddress != null && (this.ipAddress.startsWith("192") || this.ipAddress.startsWith("10"))) {
        node = root.getChild("FileInnerServer");
      } else {
        node = root.getChild("FileServer");
      } 
      fileServerURL = "http://" + node.getAttributeValue("server") + ":" + node.getAttributeValue("port") + node.getAttributeValue("path");
      if (this.ipAddress != null && (this.ipAddress.startsWith("192") || this.ipAddress.startsWith("10"))) {
        node = root.getChild("FtpInnerServer");
      } else {
        node = root.getChild("FtpServer");
      } 
      ftpServerMap = new HashMap<Object, Object>();
      ftpServerMap.put("server", node.getAttributeValue("server"));
      ftpServerMap.put("port", node.getAttributeValue("port"));
      ftpServerMap.put("user", node.getAttributeValue("user"));
      String password = node.getAttributeValue("password");
      ftpServerMap.put("oriPass", password);
      int len = password.length();
      String str1 = password.substring(0, 1);
      if (len > 6) {
        str2 = password.substring(1, 6);
        str3 = password.substring(6);
      } else {
        str2 = password.substring(1);
        str3 = "";
      } 
      ftpServerMap.put("password", String.valueOf(str1) + str3);
      str1 = "";
      for (int i = 0; i < str2.length(); i++)
        str1 = String.valueOf(str2.charAt(i)) + str1; 
      ftpServerMap.put("ddd", str1);
      node = root.getChild("Database");
      databaseType = node.getAttributeValue("type");
      configFileInputStream.close();
    } catch (Exception ex) {
      System.out.println("读取配置文件出错!" + ex);
    } 
  }
}
