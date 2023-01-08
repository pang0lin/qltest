package com.js.sso.util;

public class XMLUtil {
  public static String getXMlString(String mark, String data) {
    StringBuffer xmlStr = new StringBuffer();
    xmlStr.append("<" + mark + ">");
    xmlStr.append(data);
    xmlStr.append("</" + mark + ">");
    return xmlStr.toString();
  }
}
