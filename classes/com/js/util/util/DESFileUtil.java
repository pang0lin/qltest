package com.js.util.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DESFileUtil {
  private static final String password = "987643212";
  
  private SecureRandom random = new SecureRandom();
  
  public byte[] desCrypto(byte[] datasource) {
    try {
      Cipher cipher = Cipher.getInstance("DES");
      cipher.init(1, getSecretKey(), this.random);
      return cipher.doFinal(datasource);
    } catch (Throwable e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public void downloadFile(InputStream is, HttpServletResponse response) throws Exception {
    Cipher cipher = Cipher.getInstance("DES");
    cipher.init(2, getSecretKey(), this.random);
    ServletOutputStream servletOutputStream = response.getOutputStream();
    CipherOutputStream cos = new CipherOutputStream((OutputStream)servletOutputStream, cipher);
    byte[] buffer = new byte[1024];
    int r;
    while ((r = is.read(buffer)) >= 0)
      cos.write(buffer, 0, r); 
    cos.close();
    servletOutputStream.close();
    is.close();
  }
  
  public void img(HttpServletRequest request, HttpServletResponse response, String path) {
    try {
      Cipher cipher = Cipher.getInstance("DES");
      cipher.init(2, getSecretKey(), this.random);
      os(cipher, path, (OutputStream)response.getOutputStream());
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void os(Cipher cipher, String path, OutputStream out) {
    try {
      InputStream is = new FileInputStream(path);
      CipherOutputStream cos = new CipherOutputStream(out, cipher);
      byte[] buffer = new byte[1024];
      int r;
      while ((r = is.read(buffer)) >= 0)
        cos.write(buffer, 0, r); 
      cos.close();
      out.close();
      is.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public SecretKey getSecretKey() {
    try {
      DESKeySpec desKey = new DESKeySpec("987643212".getBytes());
      SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
      SecretKey securekey = keyFactory.generateSecret(desKey);
      return securekey;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
  }
}
