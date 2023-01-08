package com.js.wap.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTools {
  public static String getMDHM(Long mill) {
    if (mill == null)
      return ""; 
    return (new SimpleDateFormat("MM-dd HH:mm")).format(new Date(mill.longValue()));
  }
  
  public static String getYMDHM(Long mill) {
    if (mill == null)
      return ""; 
    return (new SimpleDateFormat("yyyy-MM-dd HH:mm")).format(new Date(mill.longValue()));
  }
  
  public static String getHM(Long mill) {
    if (mill == null)
      return ""; 
    return (new SimpleDateFormat("HH:mm")).format(new Date(mill.longValue()));
  }
  
  public static String getHM(Date date) {
    if (date == null)
      return ""; 
    return (new SimpleDateFormat("HH:mm")).format(date);
  }
  
  public static String getMD(Date date) {
    if (date == null)
      return ""; 
    return (new SimpleDateFormat("MM-dd")).format(date);
  }
  
  public static String getYMD(Date date) {
    if (date == null)
      return ""; 
    return (new SimpleDateFormat("yyyy-MM-dd")).format(date);
  }
  
  public static String getBEStr(String str) {
    if (str.length() == 6)
      return String.valueOf(str.substring(4)) + "月份"; 
    return String.valueOf(str.substring(4, 6)) + "-" + str.substring(6, 8) + 
      "至" + str.substring(13, 15) + "-" + str.substring(15, 17);
  }
  
  public static int getSecondByNum(String str) {
    int num = Integer.valueOf(str).intValue();
    num--;
    return 1800 * num;
  }
  
  public static String addZero(String str) {
    str = str.trim();
    if (str.length() < 5)
      str = "0" + str; 
    return str;
  }
}
