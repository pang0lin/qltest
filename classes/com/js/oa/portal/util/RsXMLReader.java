package com.js.oa.portal.util;

import java.io.File;
import java.io.FileInputStream;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class RsXMLReader {
  public static void main(String[] args) {
    System.out.println(getValue("rsgw", "wsdl", ""));
  }
  
  public static String getValue(String nodeName, String attribute, String defaultVlaue) {
    String result = defaultVlaue;
    FileInputStream configFileInputStream = null;
    try {
      String path = System.getProperty("user.dir");
      String configFile = String.valueOf(path) + "/jsconfig/rsconfig.xml";
      configFileInputStream = new FileInputStream(
          new File(configFile));
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(configFileInputStream);
      Element root = doc.getRootElement();
      Element node = root.getChild(nodeName);
      if (node != null)
        result = node.getAttributeValue(attribute); 
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      try {
        configFileInputStream.close();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return result;
  }
}
