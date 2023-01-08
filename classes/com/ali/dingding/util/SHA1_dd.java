package com.ali.dingding.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1_dd {
  public String getSHA1(String ticket, String nonceStr, long timeStamp, String url) {
    String plain = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr + 
      "&timestamp=" + String.valueOf(timeStamp) + "&url=" + url;
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-1");
      System.out.println(plain);
      md.update(plain.getBytes());
      byte[] digest = md.digest();
      StringBuffer hexstr = new StringBuffer();
      String shaHex = "";
      byte b;
      int i;
      byte[] arrayOfByte1;
      for (i = (arrayOfByte1 = digest).length, b = 0; b < i; ) {
        byte b1 = arrayOfByte1[b];
        shaHex = Integer.toHexString(b1 & 0xFF);
        if (shaHex.length() < 2)
          hexstr.append(0); 
        hexstr.append(shaHex);
        b++;
      } 
      return hexstr.toString();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return "";
    } 
  }
}
