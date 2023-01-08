package com.js.oa.webmail.util;

import java.util.Date;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UniqueCode {
  private static String sep = "";
  
  private static short counter = 0;
  
  private static short smallCounter = 998;
  
  private static String szIP = null;
  
  private static final Log logger = LogFactory.getLog(UniqueCode.class);
  
  protected static short getCount() {
    synchronized (UniqueCode.class) {
      if (counter < 0)
        counter = 0; 
      if (counter > 10000)
        counter = 0; 
      short tmp31_28 = counter;
      counter = (short)(tmp31_28 + 1);
      return tmp31_28;
    } 
  }
  
  protected static short getSmallCount() {
    synchronized (UniqueCode.class) {
      if (smallCounter < 0)
        smallCounter = 0; 
      if (smallCounter > 999)
        smallCounter = 0; 
      short tmp31_28 = smallCounter;
      smallCounter = (short)(tmp31_28 + 1);
      return tmp31_28;
    } 
  }
  
  protected static short getHiTime() {
    return (short)(int)(System.currentTimeMillis() >>> 32L);
  }
  
  protected static int getLoTime() {
    return (int)System.currentTimeMillis();
  }
  
  protected static String format(int intval) {
    String formatted = Integer.toHexString(intval);
    StringBuffer buf = new StringBuffer("00000000");
    buf.replace(8 - formatted.length(), 8, formatted);
    return buf.toString();
  }
  
  protected static String format(short shortval) {
    String formatted = Integer.toHexString(shortval);
    StringBuffer buf = new StringBuffer("0000");
    buf.replace(4 - formatted.length(), 4, formatted);
    return buf.toString();
  }
  
  public static String generate() {
    return String.valueOf(format(getHiTime())) + sep + format(getLoTime()) + sep + 
      format(getCount());
  }
  
  public static long generateNum() {
    return System.currentTimeMillis() + getSmallCount();
  }
  
  public static String generateDate() {
    Date d = new Date(System.currentTimeMillis());
    if (szIP == null) {
      szIP = "127.0.0.1";
      if (szIP != null) {
        szIP = System14.replace(szIP, ".", "");
      } else {
        szIP = "";
      } 
    } 
    return String.valueOf(szIP) + DateUtil.formatDate(d, "yyyyMMddHHmmssSSS") + 
      getSmallCount();
  }
  
  public static void main(String[] args) {
    UniqueCode uniqueCode1 = new UniqueCode();
    int i;
    for (i = 0; i < 100; i++)
      System.out.println(generate()); 
    for (i = 0; i < 100; i++)
      System.out.println(generateDate()); 
  }
}
