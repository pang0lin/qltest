package com.buguniao;

import java.sql.Connection;
import java.sql.DriverManager;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class ServerConn {
  private static String driverName = "";
  
  private static String username = "";
  
  private static String password = "";
  
  private static String url = "";
  
  private static String messagePath = "";
  
  private static String oaServerPath = null;
  
  private static String messageHasURL = "0";
  
  public void init() {
    try {
      String path = System.getProperty("user.dir");
      String filePath = String.valueOf(path) + "/jsconfig/othersystem.xml";
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(filePath);
      Element bgnNode = doc.getRootElement().getChild("buguniao");
      Element databaseNode = bgnNode.getChild("datasource");
      driverName = databaseNode.getChildText("driverClassName");
      username = databaseNode.getChildText("username");
      password = databaseNode.getChildText("password");
      url = databaseNode.getChildText("url");
      messagePath = bgnNode.getChildText("messagePath");
      oaServerPath = bgnNode.getChildText("oaServerPath");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public Connection getConnection() {
    if ("".equals(driverName))
      init(); 
    try {
      Class.forName(driverName);
      Connection conn = DriverManager.getConnection(url, username, password);
      return conn;
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public String getMessagePath() {
    if ("".equals(messagePath))
      init(); 
    return messagePath;
  }
  
  public String getOaAServerPath() {
    if (oaServerPath == null)
      init(); 
    return oaServerPath;
  }
  
  public static String getMessageHasURL() {
    return messageHasURL;
  }
  
  public static void setMessageHasURL(String messageHasURL) {
    ServerConn.messageHasURL = messageHasURL;
  }
}
