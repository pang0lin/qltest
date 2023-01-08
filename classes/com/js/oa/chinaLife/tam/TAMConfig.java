package com.js.oa.chinaLife.tam;

import java.io.IOException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class TAMConfig {
  private static String wsdlUrl = null;
  
  private static void getTAMInfo() {
    try {
      String path = System.getProperty("user.dir");
      String filePath = String.valueOf(path) + "/jsconfig/rsconfig.xml";
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(filePath);
      Element tamConfig = doc.getRootElement().getChild("tam");
      if (tamConfig != null)
        setWsdlUrl(tamConfig.getChildText("url")); 
    } catch (JDOMException e) {
      System.out.println("检查是否有/jsconfig/rsconfig.xml文件中是否存在tam节点，默认url:http://192.168.32.137:9080/tamwebservice/TAMUserCreateServiceService/WEB-INF/wsdl/TAMUserCreateServiceService.wsdl");
      wsdlUrl = "http://192.168.32.137:9080/tamwebservice/TAMUserCreateServiceService/WEB-INF/wsdl/TAMUserCreateServiceService.wsdl";
    } catch (IOException e) {
      System.out.println("检查是否有/jsconfig/rsconfig.xml文件");
    } 
  }
  
  public static String getWsdlUrl() {
    if (wsdlUrl == null)
      getTAMInfo(); 
    return wsdlUrl;
  }
  
  public static void setWsdlUrl(String wsdlUrl) {
    TAMConfig.wsdlUrl = wsdlUrl;
  }
}
