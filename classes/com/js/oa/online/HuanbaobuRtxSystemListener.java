package com.js.oa.online;

import com.js.thread.HuanbaobuRtxTask;
import com.js.util.util.DataSourceBase;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.ResourceBundle;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class HuanbaobuRtxSystemListener implements HttpSessionAttributeListener, ServletContextListener, HttpSessionListener {
  private static ServletContext context = null;
  
  public void sessionCreated(HttpSessionEvent se) {}
  
  public void sessionDestroyed(HttpSessionEvent se) {}
  
  public void attributeAdded(HttpSessionBindingEvent e) {}
  
  public void attributeReplaced(HttpSessionBindingEvent e) {}
  
  public void attributeRemoved(HttpSessionBindingEvent e) {}
  
  public void contextDestroyed(ServletContextEvent sce) {}
  
  public void contextInitialized(ServletContextEvent sce) {
    try {
      ResourceBundle resource = ResourceBundle.getBundle("info");
      String path = resource.getString("AppPath");
      System.setProperty("user.dir", path);
      System.out.println("当前系统路径:" + path);
      System.out.println("如不正确请到/jsoa/WEB-INF/classes/info.properties 文件中修改 AppPath为正确的值！");
    } catch (Exception ex) {
      System.out.println("使用默认系统路径...");
    } 
    Connection conn = null;
    String curPath = System.getProperty("user.dir");
    String file = String.valueOf(curPath) + "/jsconfig/hibernate.property.xml";
    try {
      FileInputStream configFileInputStream = new FileInputStream(new File(file));
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(configFileInputStream);
      Element node = doc.getRootElement().getChild("Hibernate");
      String server = node.getAttributeValue("server");
      if ("weblogic".equals(server) || "tongweb5".equals(server)) {
        DataSourceBase.setDataSourceName("jdbc/jsdb");
        DataSourceBase.setDataSourcePreName("");
      } else {
        DataSourceBase.setDataSourceName("java:comp/env/jdbc/jsdb");
        DataSourceBase.setDataSourcePreName("java:comp/env/");
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    HuanbaobuRtxTask task = new HuanbaobuRtxTask();
    task.start();
  }
}
