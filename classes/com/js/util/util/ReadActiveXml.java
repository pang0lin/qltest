package com.js.util.util;

import java.io.File;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ReadActiveXml {
  private static ReadActiveXml readActiveXml = null;
  
  private String servertcpport;
  
  private String serverudpport;
  
  private String use;
  
  private String isempty;
  
  private String ip;
  
  private ReadActiveXml() {
    String path = System.getProperty("user.dir");
    String configFile = String.valueOf(path) + "/jsconfig/othersystem.xml";
    try {
      File file = new File(configFile);
      if (file.exists()) {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(configFile);
        Element rootElm = document.getRootElement();
        Element usedElm = rootElm.element("used");
        this.use = usedElm.getText();
        if (this.use.equals("iactive")) {
          Element iactiveElm = rootElm.element("iactive");
          Element serverElm = iactiveElm.element("server");
          Element tcpElm = serverElm.element("tcp");
          Element ipElm = serverElm.element("ip");
          Element web_serverElm = iactiveElm.element("web_server");
          Element httpElm = web_serverElm.element("http");
          this.servertcpport = tcpElm.getText();
          this.serverudpport = httpElm.getText();
          Element isemptyElm = iactiveElm.element("isempty");
          this.isempty = isemptyElm.getText();
          this.ip = ipElm.getText();
        } else {
          this.servertcpport = "";
          this.serverudpport = "";
          this.isempty = "";
          this.ip = "";
        } 
      } else {
        this.use = "js";
        this.servertcpport = "";
        this.serverudpport = "";
        this.isempty = "";
        this.ip = "";
      } 
    } catch (DocumentException e) {
      e.printStackTrace();
    } 
  }
  
  public static ReadActiveXml getReadActive() {
    if (readActiveXml == null)
      readActiveXml = new ReadActiveXml(); 
    return readActiveXml;
  }
  
  public String getServertcpport() {
    return this.servertcpport;
  }
  
  public void setServertcpport(String servertcpport) {
    this.servertcpport = servertcpport;
  }
  
  public String getServerudpport() {
    return this.serverudpport;
  }
  
  public void setServerudpport(String serverudpport) {
    this.serverudpport = serverudpport;
  }
  
  public String getUse() {
    return this.use;
  }
  
  public void setUse(String use) {
    this.use = use;
  }
  
  public String getIsempty() {
    return this.isempty;
  }
  
  public void setIsempty(String isempty) {
    this.isempty = isempty;
  }
  
  public String getIp() {
    return this.ip;
  }
  
  public void setIp(String ip) {
    this.ip = ip;
  }
}
