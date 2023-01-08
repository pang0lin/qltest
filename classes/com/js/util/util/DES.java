package com.js.util.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DES {
  private byte[] desKey;
  
  public static String decrypt(String message, String key) throws Exception {
    byte[] bytesrc = convertHexString(message);
    Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
    DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
    SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
    IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
    cipher.init(2, secretKey, iv);
    byte[] retByte = cipher.doFinal(bytesrc);
    return new String(retByte);
  }
  
  public static byte[] encrypt(String message, String key) throws Exception {
    Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
    DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
    SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
    IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
    cipher.init(1, secretKey, iv);
    return cipher.doFinal(message.getBytes("UTF-8"));
  }
  
  public static byte[] convertHexString(String ss) {
    byte[] digest = new byte[ss.length() / 2];
    for (int i = 0; i < digest.length; i++) {
      String byteString = ss.substring(2 * i, 2 * i + 2);
      int byteValue = Integer.parseInt(byteString, 16);
      digest[i] = (byte)byteValue;
    } 
    return digest;
  }
  
  public static void main(String[] args) throws Exception {
    String key = "JiusiSSO";
    String value = "12345612345678";
    System.out.println("加密数据:" + value);
    String a = encryptFunction(key, value);
    System.out.println("加密后的数据为:" + a);
    String b = decryptFunction(key, a);
    System.out.println("解密后的数据:" + b);
  }
  
  public static String encryptFunction(String key, String value) {
    String jiami = value;
    try {
      jiami = toHexString(encrypt(URLEncoder.encode(value, "utf-8").toLowerCase(), key)).toUpperCase();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return jiami;
  }
  
  public static String decryptFunction(String key, String value) {
    String jiemi = value;
    try {
      jiemi = URLDecoder.decode(decrypt(value, key), "utf-8");
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return jiemi;
  }
  
  public static String toHexString(byte[] b) {
    StringBuffer hexString = new StringBuffer();
    for (int i = 0; i < b.length; i++) {
      String plainText = Integer.toHexString(0xFF & b[i]);
      if (plainText.length() < 2)
        plainText = "0" + plainText; 
      hexString.append(plainText);
    } 
    return hexString.toString();
  }
}
