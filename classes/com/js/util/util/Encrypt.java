package com.js.util.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Encrypt {
  public static void main(String[] args) {}
  
  public String enCoderEn(String str) {
    String retStr = "";
    byte[] coder = str.getBytes();
    int i = 0, j = 0;
    while (i < str.length()) {
      byte cur;
      if (i < str.length() - 1) {
        cur = (byte)(str.charAt(i) >>> j | 
          str.charAt(i + 1) << 7 - j & 0xFF);
      } else {
        cur = (byte)(str.charAt(i) >>> j & 0x7F);
      } 
      coder[i] = cur;
      if (cur > 0) {
        String tmpstr = Integer.toHexString(cur).toUpperCase();
        if (tmpstr.length() < 2)
          tmpstr = "0" + tmpstr; 
        retStr = String.valueOf(retStr) + tmpstr;
      } else {
        String tmpstr = Integer.toHexString(cur).toUpperCase()
          .substring(6);
        if (tmpstr.length() < 2)
          tmpstr = "0" + tmpstr; 
        retStr = String.valueOf(retStr) + tmpstr;
      } 
      i++;
      j = (j + 1) % 7;
      if (j == 0)
        i++; 
    } 
    return retStr;
  }
  
  public String deCoderEn(String str) {
    String retStr = "";
    int len = str.length();
    int i = 0, j = 0;
    byte tmpNumHead = 0;
    while (i * 2 + 2 <= len) {
      char c;
      String s = str.charAt(i * 2 + 0) + str.charAt(i * 2 + 1);
      byte tmpInt = (byte)Integer.parseInt(s, 16);
      byte tmpNumEnd = (byte)(tmpInt & 255 >>> j + 1);
      tmpNumEnd = (byte)(tmpNumEnd << j);
      if (j == 6) {
        char c1 = (char)(tmpNumEnd + tmpNumHead);
        tmpNumHead = (byte)(tmpInt >>> 7 - j);
        if (tmpNumHead < 0)
          tmpNumHead = (byte)(tmpNumHead & 255 >>> 7 - j); 
        String str1 = String.valueOf(c1) + (char)tmpNumHead;
        tmpNumHead = 0;
      } else {
        c = (char)(tmpNumEnd + tmpNumHead);
        tmpNumHead = (byte)(tmpInt >>> 7 - j);
        if (tmpNumHead < 0)
          tmpNumHead = (byte)(tmpNumHead & 255 >>> 7 - j); 
      } 
      i++;
      j = (j + 1) % 7;
      retStr = String.valueOf(retStr) + c;
    } 
    return retStr;
  }
  
  public String enCoderCn(String str) {
    String retstr = "";
    char[] coder = str.toCharArray();
    for (int i = 0; i < str.length(); i++) {
      int s = coder[i];
      if (s < 128) {
        String tmpstr = Integer.toHexString(s).toUpperCase();
        if (tmpstr.length() < 4)
          if (tmpstr.length() == 2) {
            tmpstr = "00" + tmpstr;
          } else if (tmpstr.length() == 3) {
            tmpstr = "0" + tmpstr;
          }  
        retstr = String.valueOf(retstr) + tmpstr;
      } else {
        String tmpstr = Integer.toHexString(s).toUpperCase();
        if (tmpstr.length() < 4)
          if (tmpstr.length() == 2) {
            tmpstr = "00" + tmpstr;
          } else if (tmpstr.length() == 3) {
            tmpstr = "0" + tmpstr;
          }  
        retstr = String.valueOf(retstr) + tmpstr;
      } 
    } 
    return retstr;
  }
  
  public String deCoderCn(String str) {
    String retStr = "";
    int len = str.length();
    int i = 0;
    while (i * 4 + 4 <= len) {
      String s = str.charAt(i * 4 + 0) + str.charAt(i * 4 + 1) + 
        str.charAt(i * 4 + 2) + str.charAt(i * 4 + 3);
      retStr = String.valueOf(retStr) + (char)Integer.parseInt(s, 16);
      i++;
    } 
    return retStr;
  }
  
  private boolean isEnMsg(String str) {
    char[] coder = str.toCharArray();
    boolean retval = true;
    for (int i = 0; i < str.length(); i++) {
      if (coder[i] > 'ÿ')
        retval = false; 
    } 
    return retval;
  }
  
  public String enCoder(String strMsg) {
    String tmpStr = "";
    int strlen = strMsg.length();
    if (isEnMsg(strMsg)) {
      tmpStr = Integer.toHexString(strlen).toUpperCase();
      tmpStr = enCoderEn(strMsg);
    } else {
      tmpStr = Integer.toHexString(strlen * 2).toUpperCase();
      tmpStr = enCoderCn(strMsg);
    } 
    return tmpStr;
  }
  
  public void codeMac(String mac) {}
  
  public String BASE64Encoder(String str) {
    BASE64Encoder en = new BASE64Encoder();
    String bss = new String();
    try {
      bss = en.encode(str.getBytes("GBK"));
    } catch (Exception ex) {
      System.out.println("编码不成功！");
    } 
    return bss;
  }
  
  public String BASE64Decoder(String str) {
    BASE64Decoder dn = new BASE64Decoder();
    byte[] b = (byte[])null;
    try {
      b = dn.decodeBuffer(str);
      str = new String(b);
    } catch (Exception ex) {
      System.out.println("解码不成功");
    } 
    return str;
  }
}
