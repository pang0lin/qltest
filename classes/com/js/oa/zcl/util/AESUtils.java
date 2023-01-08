package com.js.oa.zcl.util;

import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {
  public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
    KeyGenerator kgen = KeyGenerator.getInstance("AES");
    kgen.init(128, new SecureRandom(encryptKey.getBytes()));
    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(1, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
    return cipher.doFinal(content.getBytes("GBK"));
  }
  
  public static String aesEncrypt(String content, String encryptKey) throws Exception {
    return Base64Utils.encode(aesEncryptToBytes(content, encryptKey));
  }
  
  public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
    KeyGenerator kgen = KeyGenerator.getInstance("AES");
    kgen.init(128, new SecureRandom(decryptKey.getBytes()));
    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(2, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
    byte[] decryptBytes = cipher.doFinal(encryptBytes);
    return new String(decryptBytes);
  }
  
  public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
    if (encryptStr == null || encryptStr.length() == 0)
      return null; 
    if (encryptStr.length() == 0)
      return ""; 
    return aesDecryptByBytes(Base64Utils.decode(encryptStr), decryptKey);
  }
  
  public static void main(String[] args) throws Exception {
    String content = "AES加密测试";
    System.out.println("加密前：" + content);
    String key = "123456";
    System.out.println("密钥：" + key);
    String encrypt = aesEncrypt(content, key);
    System.out.println("加密后：" + encrypt);
    String decrypt = aesDecrypt(encrypt, key);
    System.out.println("解密后：" + decrypt);
  }
}
