package com.js.util.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class AES {
  private static byte[] ivector = "0102030405060708".getBytes();
  
  public static String encrypt2Str(String sSrc, String sKey) throws Exception {
    SecretKeySpec skeySpec = new SecretKeySpec(sKey.getBytes(), "AES");
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    IvParameterSpec iv = new IvParameterSpec(ivector);
    cipher.init(1, skeySpec, iv);
    byte[] encrypted = cipher.doFinal(sSrc.getBytes("UTF-8"));
    return (new BASE64Encoder()).encode(encrypted);
  }
  
  public static String decrypt2Str(String sSrc, String sKey) throws Exception {
    try {
      byte[] raw = sKey.getBytes("UTF-8");
      SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      IvParameterSpec iv = new IvParameterSpec(ivector);
      cipher.init(2, skeySpec, iv);
      byte[] encrypted1 = (new BASE64Decoder()).decodeBuffer(sSrc);
      try {
        byte[] original = cipher.doFinal(encrypted1);
        String originalString = new String(original, "utf-8");
        return originalString;
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      } 
    } catch (Exception ex) {
      System.out.println(ex.toString());
      return null;
    } 
  }
  
  public static String Encrypt(String sSrc, String sKey) throws Exception {
    if (sKey == null) {
      System.out.print("Key为空null");
      return null;
    } 
    if (sKey.length() != 16) {
      System.out.print("Key长度不是16位");
      return null;
    } 
    byte[] raw = sKey.getBytes("utf-8");
    SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    cipher.init(1, skeySpec);
    byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
    return (new Base64()).encodeToString(encrypted);
  }
  
  public static String Decrypt(String sSrc, String sKey) throws Exception {
    try {
      if (sKey == null) {
        System.out.print("Key为空null");
        return null;
      } 
      if (sKey.length() != 16) {
        System.out.print("Key长度不是16位");
        return null;
      } 
      byte[] raw = sKey.getBytes("utf-8");
      SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      cipher.init(2, skeySpec);
      byte[] encrypted1 = (new Base64()).decode(sSrc);
      try {
        byte[] original = cipher.doFinal(encrypted1);
        String originalString = new String(original, "utf-8");
        return originalString;
      } catch (Exception e) {
        System.out.println(e.toString());
        return null;
      } 
    } catch (Exception ex) {
      System.out.println(ex.toString());
      return null;
    } 
  }
  
  public static void main(String[] args) {
    String key = "ff77ba474e1ef8fc";
    String password = "duhui;2017/03/23 10:00:12";
    try {
      String data = Encrypt(password, key);
      data = URLEncoder.encode(data);
      System.out.println("加密后：" + data);
      data = URLDecoder.decode(data);
      String pass = Decrypt(data, key);
      System.out.println("解密后：" + pass);
      String str = "111,112,113,114,";
      String[] arr = str.split(",");
      for (int i = 0; i < arr.length; i++)
        System.out.println(String.valueOf(arr[i]) + "----"); 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
}
