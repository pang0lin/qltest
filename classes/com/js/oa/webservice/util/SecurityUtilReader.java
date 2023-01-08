package com.js.oa.webservice.util;

import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class SecurityUtilReader {
  private static SecurityUtilReader instance;
  
  public static SecurityUtilReader getInstance() {
    if (instance == null) {
      init();
      instance = new SecurityUtilReader();
    } 
    return instance;
  }
  
  public static void init() {
    try {
      String path = System.getProperty("user.dir");
      String filePath = String.valueOf(path) + "/jsconfig/security.xml";
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(filePath);
      Element root = doc.getRootElement();
      List<Element> list = root.getChildren();
      if (list != null && list.size() > 0)
        for (int i = 0; i < list.size(); i++) {
          Element securityNode = list.get(i);
          SecurityPojo security = new SecurityPojo();
          security.setUse(securityNode.getAttributeValue("use"));
          security.setServicetype(securityNode.getAttributeValue("servicetype"));
          security.setIprange(securityNode.getAttributeValue("iprange"));
          security.setKey(securityNode.getAttributeValue("key"));
          SecurityRoom.addAPP(security);
        }  
    } catch (Exception e) {
      System.out.println("未配置security.xml");
    } 
  }
}
