package com.js.util.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class SysConfigReader {
  public static String readConfigValue(String nodeName, String attributeName) {
    String value = "";
    FileInputStream configFileInputStream = null;
    try {
      String path = System.getProperty("user.dir");
      String configFile = String.valueOf(path) + "/jsconfig/sysconfig.xml";
      configFileInputStream = new FileInputStream(
          new File(configFile));
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(configFileInputStream);
      Element root = doc.getRootElement();
      Element node = root.getChild(nodeName);
      if (node != null)
        value = node.getAttributeValue(attributeName); 
    } catch (Exception e) {
      System.out.println("读取配置失败，NodeName=" + nodeName + ";AttributeName=" + attributeName + "    错误信息如下：");
      e.printStackTrace();
    } finally {
      if (configFileInputStream != null)
        try {
          configFileInputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }  
    } 
    return value;
  }
}
