package com.js.util.util;

public class HTMLEncoding {
  public static String htmlEncoding(String value) {
    if (value == null)
      return null; 
    value = value.replaceAll("&", "&amp;");
    value = value.replace("　", "&nbsp;");
    value = value.replaceAll("''", "&#39;");
    value = value.replaceAll("\"", "&quot;");
    value = value.replaceAll("<", "&lt;");
    value = value.replaceAll(">", "&gt;");
    return value;
  }
}
