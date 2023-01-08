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

public class PicSingleton {
  private static PicSingleton singleton = null;
  
  private List<String> picNamesList;
  
  private PicSingleton() {
    init();
  }
  
  public static PicSingleton getInstance() {
    if (singleton == null)
      singleton = new PicSingleton(); 
    return singleton;
  }
  
  private void init() {
    this.picNamesList = new ArrayList<String>();
    String path = "";
    path = System.getProperty("user.dir");
    if (!"".equals(path)) {
      path = path.replace("\\", "/");
      String configFile = String.valueOf(path) + "/jsconfig/picconfig.xml";
      FileInputStream configFileInputStream = null;
      try {
        configFileInputStream = new FileInputStream(new File(configFile));
        SAXBuilder builder = new SAXBuilder();
        builder.setValidation(false);
        builder.setEntityResolver(new NoOpEntityResolver());
        Document doc = builder.build(configFileInputStream);
        Element node = doc.getRootElement();
        Element picPath = node.getChildren().get(0);
        List<Element> nodeList = picPath.getChildren("pic");
        String name = "";
        for (int i = 0; i < nodeList.size(); i++) {
          node = nodeList.get(i);
          name = node.getAttributeValue("name");
          this.picNamesList.add(name);
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
  
  public boolean isExist(String picStr) {
    if (this.picNamesList.contains(picStr.toLowerCase()))
      return true; 
    return false;
  }
  
  public void distroy() {
    this.picNamesList.clear();
    this.picNamesList = null;
    singleton = null;
  }
}
