package com.js.util.util;

import java.util.ResourceBundle;

public class PropertiesTrans {
  public static String getValueByKey(String key) {
    ResourceBundle resource = ResourceBundle.getBundle("info");
    return resource.getString(key);
  }
  
  public static void main(String[] args) {
    try {
      ResourceBundle resource = ResourceBundle.getBundle("info");
      System.out.println(resource.getString("MessageFormat"));
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
