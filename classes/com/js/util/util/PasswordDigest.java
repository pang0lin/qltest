package com.js.util.util;

import com.js.exception.JSException;
import com.sun.crypto.provider.SunJCE;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public final class PasswordDigest {
  private static final String Algorithm = "DES";
  
  private static final byte[] encodedKey = new byte[] { -50, -43, 19, 112, 
      -14, -122, 103, -111 };
  
  public static String getDigest(String originalInfo) throws JSException {
    MessageDigest alg = null;
    try {
      alg = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException ex) {
      System.out.println(ex.toString());
      throw new JSException("计算摘要失败", ex);
    } 
    alg.update(originalInfo.getBytes());
    byte[] digest = alg.digest();
    return byte2hex(digest);
  }
  
  public static String getDigestSelf(String originalInfo) throws JSException {
    MessageDigest alg = null;
    try {
      alg = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException ex) {
      System.out.println(ex.toString());
      throw new JSException("计算摘要失败", ex);
    } 
    byte[] bytesOld = originalInfo.getBytes();
    byte[] bytesNew = new byte[bytesOld.length + 1];
    System.arraycopy(bytesOld, 0, bytesNew, 0, bytesOld.length);
    bytesNew[bytesOld.length] = 0;
    alg.update(bytesNew);
    byte[] digest = alg.digest();
    return byte2hex(digest);
  }
  
  public static String byte2hex(byte[] b) {
    String hs = "";
    String stmp = "";
    for (int n = 0; n < b.length; n++) {
      stmp = Integer.toHexString(b[n] & 0xFF);
      if (stmp.length() == 1) {
        hs = String.valueOf(hs) + "0" + stmp;
      } else {
        hs = String.valueOf(hs) + stmp;
      } 
    } 
    return hs.toUpperCase();
  }
  
  public static byte[] hex2byte(String a) {
    int len = a.length() / 2;
    byte[] b = new byte[len];
    for (int i = 0; i < len; i++)
      b[i] = (byte)Integer.parseInt(a.substring(i * 2, i * 2 + 2), 16); 
    return b;
  }
  
  public static String encrypt(String source) throws JSException {
    try {
      Security.addProvider(new SunJCE());
      SecretKeySpec destmp = new SecretKeySpec(
          encodedKey, "DES");
      SecretKey deskey = destmp;
      Cipher c1 = Cipher.getInstance("DES");
      c1.init(1, deskey);
      byte[] cipherByte = c1.doFinal(source.getBytes());
      return byte2hex(cipherByte);
    } catch (Exception ex) {
      throw new JSException("加密失败", ex);
    } 
  }
  
  public static String decrypt(String source) throws JSException {
    try {
      Security.addProvider(new SunJCE());
      SecretKeySpec destmp = new SecretKeySpec(
          encodedKey, "DES");
      SecretKey deskey = destmp;
      Cipher c1 = Cipher.getInstance("DES");
      c1 = Cipher.getInstance("DES");
      c1.init(2, deskey);
      byte[] clearByte = c1.doFinal(hex2byte(source));
      return new String(clearByte);
    } catch (Exception ex) {
      throw new JSException("解密失败", ex);
    } 
  }
  
  public static String getKey() throws NoSuchAlgorithmException {
    Security.addProvider(new SunJCE());
    KeyGenerator keygen = KeyGenerator.getInstance("DES");
    SecretKey deskey = keygen.generateKey();
    byte[] byteKey = deskey.getEncoded();
    String key = byte2hex(byteKey);
    return key;
  }
}
