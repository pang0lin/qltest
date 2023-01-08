package com.js.oa.hr.finance.util;

import com.js.oa.portal.util.RsXMLReader;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

public class SAPConn {
  private static Logger log = Logger.getLogger(SAPConn.class);
  
  private static final String ABAP_AS_POOLED = "ABAP_AS_WITH_POOL";
  
  static {
    Properties connectProperties = new Properties();
    connectProperties.setProperty("jco.client.ashost", RsXMLReader.getValue("sap", "host", ""));
    connectProperties.setProperty("jco.client.sysnr", RsXMLReader.getValue("sap", "sysnr", ""));
    connectProperties.setProperty("jco.client.client", RsXMLReader.getValue("sap", "client", ""));
    connectProperties.setProperty("jco.client.user", RsXMLReader.getValue("sap", "user", ""));
    connectProperties.setProperty("jco.client.passwd", RsXMLReader.getValue("sap", "passwd", ""));
    connectProperties.setProperty("jco.client.lang", RsXMLReader.getValue("sap", "lang", ""));
    connectProperties.setProperty("jco.destination.pool_capacity", RsXMLReader.getValue("sap", "pool_capacity", ""));
    connectProperties.setProperty("jco.destination.peak_limit", RsXMLReader.getValue("sap", "peak_limit", ""));
    createDataFile("ABAP_AS_WITH_POOL", "jcoDestination", connectProperties);
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
      destination = JCoDestinationManager.getDestination("ABAP_AS_WITH_POOL");
    } catch (JCoException e) {
      log.error("Connect SAP fault, error msg: " + e.toString());
    } 
    return destination;
  }
}
