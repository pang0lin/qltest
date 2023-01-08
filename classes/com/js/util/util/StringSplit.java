package com.js.util.util;

public class StringSplit {
  public static String splitWith(String src, String regex, String other) {
    String retString = "";
    for (int i = 0; i < src.length() - 1; i++) {
      String traStr = src.substring(i, i + 1);
      int countRegex = 0;
      if (traStr.equals(regex) || countRegex % 2 != 0) {
        if (traStr.equals(regex))
          countRegex++; 
        int k = src.indexOf(regex, i + 1);
        if (k != -1) {
          String dis = src.substring(i, k + 1);
          if (checkOther(dis, other)) {
            retString = String.valueOf(retString) + dis;
          } else {
            src = src.substring(k, src.length());
            retString = String.valueOf(retString) + splitWith(src, regex, other);
            return retString;
          } 
          i = k;
        } 
      } 
    } 
    return retString;
  }
  
  private static boolean checkOther(String dist, String other) {
    for (int i = 0; i < other.length(); i++) {
      if (dist.indexOf(other.substring(i, i + 1)) != -1)
        return false; 
    } 
    return true;
  }
  
  public static String splitOrgIdString(String src, String regex, String other) {
    String retString = "";
    for (int i = 0; i < src.length() - 1; i++) {
      String traStr = src.substring(i, i + 1);
      int countRegex = 0;
      if (traStr.equals(regex) || countRegex % 2 != 0) {
        if (traStr.equals(regex))
          countRegex++; 
        int k = src.indexOf(regex, i + 1);
        if (k != -1) {
          String dis = src.substring(i, k + 1);
          if (checkOther(dis, other)) {
            retString = String.valueOf(retString) + dis;
          } else {
            src = src.substring(k, src.length());
            retString = String.valueOf(retString) + splitWith(src, regex, other);
            return retString;
          } 
          i = k;
        } 
      } 
    } 
    return retString;
  }
}
