package com.js.util.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class UploadConfig {
  private static UploadConfig uploadConfig;
  
  private static Map uploadMap = null;
  
  public static UploadConfig getInstance() {
    if (uploadConfig == null)
      uploadConfig = new UploadConfig(); 
    return uploadConfig;
  }
  
  private UploadConfig() {
    try {
      uploadMap = new HashMap<Object, Object>();
      String path = System.getProperty("user.dir");
      String configFile = String.valueOf(path) + "/jsconfig/sysconfig.xml";
      FileInputStream configFileInputStream = new FileInputStream(new File(configFile));
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(configFileInputStream);
      Element root = doc.getRootElement();
      Element node = root.getChild("Lan");
      uploadMap.put("LanIP", node.getAttributeValue("ip"));
      node = root.getChild("FileServer");
      if (node.getAttributeValue("type") != null && node.getAttributeValue("type").toString().equals("0")) {
        uploadMap.put("FileServer", node.getAttributeValue("path"));
      } else {
        uploadMap.put("FileServer", "http://" + node.getAttributeValue("server") + ":" + node.getAttributeValue("port") + node.getAttributeValue("path"));
      } 
      node = root.getChild("FileSizeLimitAll");
      String FileSizeLimitAll = node.getAttributeValue("value");
      node = root.getChild("FtpServer");
      HashMap<Object, Object> ftpMap = new HashMap<Object, Object>();
      ftpMap.put("server", node.getAttributeValue("server"));
      ftpMap.put("port", node.getAttributeValue("port"));
      ftpMap.put("user", node.getAttributeValue("user"));
      String password = node.getAttributeValue("password");
      ftpMap.put("oriPass", password);
      int len = password.length();
      String str1 = password.substring(0, 1);
      if (len > 6) {
        str2 = password.substring(1, 6);
        str3 = password.substring(6);
      } else {
        str2 = password.substring(1);
        str3 = "";
      } 
      ftpMap.put("password", String.valueOf(str1) + str3);
      str1 = "";
      for (int i = 0; i < str2.length(); i++)
        str1 = String.valueOf(str2.charAt(i)) + str1; 
      ftpMap.put("ddd", str1);
      ftpMap.put("FileSizeLimitAll", FileSizeLimitAll);
      uploadMap.put("FtpMap", ftpMap);
      node = root.getChild("FileInnerServer");
      if (node.getAttributeValue("type") != null && node.getAttributeValue("type").toString().equals("0")) {
        uploadMap.put("FileInnerServer", node.getAttributeValue("path"));
      } else {
        uploadMap.put("FileInnerServer", "http://" + node.getAttributeValue("server") + ":" + node.getAttributeValue("port") + node.getAttributeValue("path"));
      } 
      node = root.getChild("FtpInnerServer");
      HashMap<Object, Object> ftpInnerMap = new HashMap<Object, Object>();
      ftpInnerMap.put("server", node.getAttributeValue("server"));
      ftpInnerMap.put("port", node.getAttributeValue("port"));
      ftpInnerMap.put("user", node.getAttributeValue("user"));
      password = node.getAttributeValue("password");
      ftpInnerMap.put("oriPass", password);
      str1 = "";
      String str2 = "";
      String str3 = "";
      len = password.length();
      str1 = password.substring(0, 1);
      if (len > 6) {
        str2 = password.substring(1, 6);
        str3 = password.substring(6);
      } else {
        str2 = password.substring(1);
        str3 = "";
      } 
      ftpInnerMap.put("password", String.valueOf(str1) + str3);
      str1 = "";
      for (int j = 0; j < str2.length(); j++)
        str1 = String.valueOf(str2.charAt(j)) + str1; 
      ftpInnerMap.put("ddd", str1);
      ftpInnerMap.put("FileSizeLimitAll", FileSizeLimitAll);
      uploadMap.put("FtpInnerMap", ftpInnerMap);
      configFileInputStream.close();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
    } 
  }
  
  public Map getUploadMap() {
    return uploadMap;
  }
}
