package com.js.util.util;

import com.js.lang.NoOpEntityResolver;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class IllSuffixSingleton {
  private static IllSuffixSingleton singleton = null;
  
  private List<String> suffixNamesList;
  
  private IllSuffixSingleton() {
    init();
  }
  
  public static IllSuffixSingleton getInstance() {
    if (singleton == null)
      singleton = new IllSuffixSingleton(); 
    return singleton;
  }
  
  private void init() {
    this.suffixNamesList = new ArrayList<String>();
    String path = "";
    path = System.getProperty("user.dir");
    if (!"".equals(path)) {
      path = path.replace("\\", "/");
      String configFile = String.valueOf(path) + "/jsconfig/upload-type-config.xml";
      FileInputStream configFileInputStream = null;
      try {
        configFileInputStream = new FileInputStream(new File(configFile));
        SAXBuilder builder = new SAXBuilder();
        builder.setValidation(false);
        builder.setEntityResolver(new NoOpEntityResolver());
        Document doc = builder.build(configFileInputStream);
        Element node = doc.getRootElement();
        Element picPath = node.getChildren().get(0);
        List<Element> nodeList = picPath.getChildren("type");
        String name = "";
        for (int i = 0; i < nodeList.size(); i++) {
          node = nodeList.get(i);
          name = node.getAttributeValue("ext");
          this.suffixNamesList.add(name);
          name = null;
        } 
        configFileInputStream.close();
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        if (configFileInputStream != null)
          try {
            configFileInputStream.close();
          } catch (IOException e) {
            e.printStackTrace();
          }  
      } 
    } 
  }
  
  public boolean checkSuffix(String suffix) {
    if (this.suffixNamesList.contains(suffix.toLowerCase()))
      return false; 
    return true;
  }
  
  public void distroy() {
    this.suffixNamesList.clear();
    this.suffixNamesList = null;
    singleton = null;
  }
}
