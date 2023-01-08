package com.js.util.sap;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class SAPConn {
  private static Logger log = Logger.getLogger(SAPConn.class);
  
  private static String defaultPoolName = "ABAP_AS_WITH_POOL";
  
  static {
    FileInputStream configFileInputStream = null;
    try {
      String path = System.getProperty("user.dir");
      String configFile = String.valueOf(path) + "/jsconfig/sapconfig.xml";
      configFileInputStream = new FileInputStream(new File(configFile));
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(configFileInputStream);
      Element root = doc.getRootElement();
      List<Element> poolList = root.getChildren("sap");
      if (poolList.size() > 0)
        for (int i = 0; i < poolList.size(); i++) {
          Element node = poolList.get(i);
          String poolname = node.getChildText("name");
          String host = node.getChildText("host");
          String sysnr = node.getChildText("sysnr");
          String client = node.getChildText("client");
          String user = node.getChildText("user");
          String password = node.getChildText("password");
          String lang = node.getChildText("lang");
          String pool_capacity = node.getChildText("pool_capacity");
          String peak_limit = node.getChildText("peak_limit");
          if (i == 0)
            defaultPoolName = poolname; 
          Properties connectProperties = new Properties();
          connectProperties.setProperty("jco.client.ashost", host);
          connectProperties.setProperty("jco.client.sysnr", sysnr);
          connectProperties.setProperty("jco.client.client", client);
          connectProperties.setProperty("jco.client.user", user);
          connectProperties.setProperty("jco.client.passwd", password);
          connectProperties.setProperty("jco.client.lang", lang);
          connectProperties.setProperty("jco.destination.pool_capacity", pool_capacity);
          connectProperties.setProperty("jco.destination.peak_limit", peak_limit);
          createDataFile(poolname, "jcoDestination", connectProperties);
        }  
      configFileInputStream.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  private static void createDataFile(String name, String suffix, Properties properties) {
    File cfg = new File(String.valueOf(name) + "." + suffix);
    if (cfg.exists())
      cfg.deleteOnExit(); 
    try {
      FileOutputStream fos = new FileOutputStream(cfg, false);
      properties.store(fos, "for tests only !");
      fos.close();
    } catch (Exception e) {
      log.error("Create Data file fault, error msg: " + e.toString());
      throw new RuntimeException("Unable to create the destination file " + cfg.getName(), e);
    } 
  }
  
  public static JCoDestination connect() {
    JCoDestination destination = null;
    try {
      destination = JCoDestinationManager.getDestination(defaultPoolName);
    } catch (JCoException e) {
      log.error("Connect SAP fault, error msg: " + e.toString());
    } 
    return destination;
  }
  
  public static JCoDestination connect(String poolName) {
    JCoDestination destination = null;
    try {
      if (poolName == null || "".equals(poolName)) {
        destination = JCoDestinationManager.getDestination(defaultPoolName);
      } else {
        destination = JCoDestinationManager.getDestination(poolName);
      } 
    } catch (JCoException e) {
      log.error("Connect SAP fault, error msg: " + e.toString());
    } 
    return destination;
  }
}
