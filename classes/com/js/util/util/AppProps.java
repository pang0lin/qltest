package com.js.util.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

public class AppProps {
  private static Properties properties = null;
  
  private static Object propertiesLock = new Object();
  
  private static String resourceURI = "application.properties";
  
  static {
    loadProps();
  }
  
  public static String getProp(String name) {
    String property = properties.getProperty(name);
    if (property == null)
      return null; 
    return property.trim();
  }
  
  public static void setProp(String name, String value) {
    synchronized (propertiesLock) {
      properties.setProperty(name, value);
      saveProps();
    } 
  }
  
  public static void deleteProp(String name) {
    synchronized (propertiesLock) {
      properties.remove(name);
      saveProps();
    } 
  }
  
  public static Enumeration propNames() {
    return properties.propertyNames();
  }
  
  public static void reloadProp() {
    loadProps();
  }
  
  private static void loadProps() {
    properties = new Properties();
    InputStream in = null;
    try {
      in = new FileInputStream(new File(resourceURI));
      properties.load(in);
    } catch (Exception e) {
      System.err
        .println("Error reading Application properties in PropertyManager.loadProps() " + 
          e);
      e.printStackTrace();
    } finally {
      try {
        in.close();
      } catch (Exception exception) {}
    } 
  }
  
  private static void saveProps() {
    String path = "";
    OutputStream out = null;
    try {
      out = new FileOutputStream(resourceURI);
      properties.store(out, "application.properties -- " + 
          new Date());
    } catch (Exception ioe) {
      ioe.printStackTrace();
    } finally {
      try {
        out.close();
      } catch (Exception exception) {}
    } 
  }
  
  public static boolean propFileIsReadable() {
    return (new File(resourceURI)).canRead();
  }
  
  public static boolean propFileExists() {
    return (new File(resourceURI)).exists();
  }
  
  public static boolean propFileIsWritable() {
    return (new File(resourceURI)).canWrite();
  }
  
  public static String getAppVersion() {
    return String.valueOf(getAppVersionMajor()) + "." + getAppVersionMinor() + "." + 
      getAppVersionRevision();
  }
  
  public static String getAppVersionMajor() {
    return getProp("app_major_version");
  }
  
  public static String getAppVersionMinor() {
    return getProp("app_minor_version");
  }
  
  public static String getAppVersionRevision() {
    return getProp("app_revision_version");
  }
}
