package com.js.util.util;

import com.js.lang.NoOpEntityResolver;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class RTXStrSingleton {
  private static RTXStrSingleton singleton = null;
  
  private Map<String, String> rtxMap;
  
  private String rtxMsgStatus;
  
  private RTXStrSingleton() {
    init();
  }
  
  public static RTXStrSingleton getInstance() {
    if (singleton == null)
      singleton = new RTXStrSingleton(); 
    return singleton;
  }
  
  public boolean getRtxMsgStatus() {
    if (this.rtxMsgStatus != null && "1".equals(this.rtxMsgStatus.trim()))
      return true; 
    return false;
  }
  
  public String getRtxStr(String str) {
    return this.rtxMap.get(str);
  }
  
  private void init() {
    this.rtxMap = new HashMap<String, String>();
    String path = "";
    path = System.getProperty("user.dir");
    if (!"".equals(path)) {
      path = path.replace("\\", "/");
      String configFile = String.valueOf(path) + "/jsconfig/othersystem.xml";
      FileInputStream configFileInputStream = null;
      try {
        configFileInputStream = new FileInputStream(new File(configFile));
        SAXBuilder builder = new SAXBuilder();
        builder.setValidation(false);
        builder.setEntityResolver(new NoOpEntityResolver());
        Document doc = builder.build(configFileInputStream);
        Element node = doc.getRootElement();
        Element rtxNnode = node.getChild("rtx");
        this.rtxMsgStatus = rtxNnode.getChild("rtxMsgStatus").getText();
        Element rtxServerStrNode = rtxNnode.getChild("rtxServerStr");
        Element OAServerStrNode = rtxNnode.getChild("OAServerStr");
        Element OAServerAuthNode = rtxNnode.getChild("OAServerAuth");
        this.rtxMap.put("rtxServerStr", rtxServerStrNode.getText());
        this.rtxMap.put("OAServerStr", OAServerStrNode.getText());
        this.rtxMap.put("OAServerAuth", OAServerAuthNode.getText());
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
}
