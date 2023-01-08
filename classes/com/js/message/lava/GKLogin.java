package com.js.message.lava;

import java.security.MessageDigest;

public class GKLogin {
  private static final String[] hexDigits = new String[] { 
      "0", "1", "2", "3", "4", "5", "6", 
      "7", 
      "8", "9", 
      "A", "B", "C", "D", "E", 
      "F" };
  
  public boolean singleLogin(String gid, String zoneid, String passport) {
    boolean flag = false;
    GKUtilClass gKUtilClass = new GKUtilClass();
    int GID = (Integer.parseInt(zoneid) << 32) + Integer.parseInt(gid);
    String str = 
      "<request type='login' subtype='passport' msid=''><message><user GID='" + 
      
      GID + "' gid='" + gid + "' zoneid='" + zoneid + "'>" + 
      "<passport>" + passport + "</passport>" + 
      "</user>" + 
      "</message>" + 
      "</request>";
    flag = gKUtilClass.getFlag(str);
    return flag;
  }
  
  public static String byteArrayToHexString(byte[] b) {
    StringBuffer resultSb = new StringBuffer();
    for (int i = 0; i < b.length; i++)
      resultSb.append(byteToHexString(b[i])); 
    return resultSb.toString();
  }
  
  private static String byteToHexString(byte b) {
    int n = b;
    if (n < 0)
      n += 256; 
    int d1 = n / 16;
    int d2 = n % 16;
    return String.valueOf(hexDigits[d1]) + hexDigits[d2];
  }
  
  public static String MD5Encode(String origin) {
    String resultString = null;
    try {
      resultString = new String(origin);
      MessageDigest md = MessageDigest.getInstance("MD5");
      resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
    } catch (Exception exception) {}
    return resultString;
  }
  
  public static String getLoginURL(String gid, String Pwd) {
    String url = "elava://login?gid=5000." + gid + "&pwd=" + 
      MD5Encode(Pwd);
    return url;
  }
}
