package com.js.lang;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class Resource {
  private static String HEAD_FILE = "com/js/lang/resource/SystemResource";
  
  private static Map instance = new HashMap<Object, Object>();
  
  private ResourceBundle resourceBundle = null;
  
  private static Resource resource;
  
  private static HashMap resMap = new HashMap<Object, Object>();
  
  static {
    try {
      String path = "";
      path = System.getProperty("user.dir");
      if (!"".equals(path)) {
        String configFile = String.valueOf(path) + "/jsconfig/struts-config.xml";
        FileInputStream configFileInputStream = new FileInputStream(new File(configFile));
        SAXBuilder builder = new SAXBuilder();
        builder.setValidation(false);
        builder.setEntityResolver(new NoOpEntityResolver());
        Document doc = builder.build(configFileInputStream);
        Element node = doc.getRootElement();
        List<Element> nodeList = node.getChildren("message-resources");
        for (int i = 0; i < nodeList.size(); i++) {
          node = nodeList.get(i);
          String bundle = node.getAttributeValue("key");
          String para = node.getAttributeValue("parameter");
          resMap.put(bundle, para);
        } 
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  public static Resource getInstance(String bundle, String locale) {
    String resoureKey = String.valueOf(bundle) + "_" + locale;
    if (instance.get(resoureKey) != null)
      return (Resource)instance.get(resoureKey); 
    resource = new Resource(bundle, locale);
    instance.put(resoureKey, resource);
    return resource;
  }
  
  private Resource() {}
  
  private Resource(String bundle, String locale) {
    Locale loc = new Locale(locale);
    String resFile = (String)resMap.get(bundle);
    this.resourceBundle = ResourceBundle.getBundle(resFile, loc);
  }
  
  public String getValue(String key) {
    try {
      return this.resourceBundle.getString(key);
    } catch (Exception ex) {
      ex.printStackTrace();
      if (key.indexOf(".") > 0)
        key = key.substring(key.lastIndexOf(".") + 1); 
      return key;
    } 
  }
  
  public static String getValue(String locale, String bundle, String key) {
    try {
      return getInstance(bundle, locale).getValue(key);
    } catch (Exception ex) {
      ex.printStackTrace();
      if (key.indexOf(".") > 0)
        key = key.substring(key.lastIndexOf(".") + 1); 
      return key;
    } 
  }
  
  public void setResHeadName(String headName) {
    HEAD_FILE = headName;
  }
}
