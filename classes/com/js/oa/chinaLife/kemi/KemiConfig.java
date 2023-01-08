package com.js.oa.chinaLife.kemi;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class KemiConfig {
  public static Map<String, String> getKemiInfo() {
    Map<String, String> map = new HashMap<String, String>();
    String path = System.getProperty("user.dir");
    try {
      String filePath = String.valueOf(path) + "/jsconfig/rsconfig.xml";
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(filePath);
      Element kemiConfig = doc.getRootElement().getChild("kemi");
      if (kemiConfig != null) {
        map.put("url", kemiConfig.getChildText("url"));
        map.put("method", kemiConfig.getChildText("method"));
        map.put("update", kemiConfig.getChildText("update"));
      } 
    } catch (JDOMException e) {
      System.out.println("检查" + path + "/jsconfig/rsconfig.xml中是否有kemi的配置");
    } catch (IOException e) {
      System.out.println("检查是否有" + path + "/jsconfig/rsconfig.xml文件");
    } 
    return map;
  }
}
