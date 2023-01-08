package com.js.oa.module.util;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ImportConfigReader {
  private String clazz;
  
  private String url;
  
  private String userId;
  
  private String password;
  
  private String tableId;
  
  private String fields;
  
  public String getClazz() {
    return this.clazz;
  }
  
  public String getUrl() {
    return this.url;
  }
  
  public String getUserId() {
    return this.userId;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
  
  public void setClazz(String clazz) {
    this.clazz = clazz;
  }
  
  public void setUrl(String url) {
    this.url = url;
  }
  
  public void setUserId(String userId) {
    this.userId = userId;
  }
  
  public void setTableId(String tableId) {
    this.tableId = tableId;
  }
  
  public void setFields(String fields) {
    this.fields = fields;
  }
  
  public String getPassword() {
    return this.password;
  }
  
  public String getTableId() {
    return this.tableId;
  }
  
  public String getFields() {
    return this.fields;
  }
  
  public void GetConfigReaderXML(String url) {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.parse(url);
      doc.normalize();
      NodeList links = doc.getElementsByTagName("configes");
      for (int i = 0; i < links.getLength(); i++) {
        Element link = (Element)links.item(i);
        setClazz(link.getElementsByTagName("ResourceTableDesName")
            .item(0)
            .getFirstChild().getNodeValue());
        setUrl(link.getElementsByTagName("ResourceTableName").item(
              0)
            .getFirstChild().getNodeValue());
        setUserId(link.getElementsByTagName("ResourceDomain").item(
              0)
            .getFirstChild().getNodeValue());
        setPassword(link.getElementsByTagName("MobilPhone")
            .item(0).getFirstChild().getNodeValue());
        setTableId(link.getElementsByTagName("CheckUnique")
            .item(0).getFirstChild().getNodeValue());
        setFields(link.getElementsByTagName("CheckUnique")
            .item(0).getFirstChild().getNodeValue());
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
}
