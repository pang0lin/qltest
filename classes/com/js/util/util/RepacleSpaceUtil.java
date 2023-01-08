package com.js.util.util;

public class RepacleSpaceUtil {
  public static String getNoSpaceStr(String strValue) {
    String value = "";
    for (int i = 0; i < strValue.length(); i++) {
      if (strValue.codePointAt(i) != 10 && strValue.codePointAt(i) != 13)
        value = String.valueOf(value) + strValue.charAt(i); 
    } 
    return value;
  }
}
