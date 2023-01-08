package com.js.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class GetConnection {
  public static Connection getConnection() {
    String user = "";
    String password = "";
    String path = System.getProperty("user.dir");
    String filePath = String.valueOf(path) + "/jsconfig/sysconfig.xml";
    SAXBuilder builder = new SAXBuilder();
    Document doc = null;
    try {
      doc = builder.build(filePath);
    } catch (JDOMException e1) {
      e1.printStackTrace();
    } catch (IOException e1) {
      e1.printStackTrace();
    } 
    Element WorkUse = doc.getRootElement().getChild("ArchivesDatabase");
    String driverName = WorkUse.getChild("driverName").getAttribute("value").getValue();
    user = WorkUse.getChild("dbUser").getAttribute("value").getValue();
    password = WorkUse.getChild("password").getAttribute("value").getValue();
    String url = WorkUse.getChild("dbUrl").getAttribute("value").getValue();
    System.out.println("url:" + url);
    Connection conn = null;
    try {
      Class.forName(driverName).newInstance();
      System.out.println("connection to db...");
      conn = DriverManager.getConnection(url, user, password);
      System.out.println("successful!");
    } catch (Exception e) {
      System.out.print("Connect failed");
      e.printStackTrace();
    } 
    return conn;
  }
}
