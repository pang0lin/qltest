package com.js.oa.hgydyy.utils;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import xjava.security.IllegalBlockSizeException;

public class AESUtil {
  public static void main(String[] args) {}
  
  public static String decrypt(String content, String password) {
    try {
      byte[] shaByte = sha(password.getBytes());
      byte[] enCodeFormat = Arrays.copyOf(shaByte, 16);
      SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      cipher.init(2, key);
      byte[] result = cipher.doFinal(Base64.decode(content));
      return new String(result, "utf-8");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (NoSuchPaddingException e) {
      e.printStackTrace();
    } catch (InvalidKeyException e) {
      e.printStackTrace();
    } catch (IllegalBlockSizeException e) {
      e.printStackTrace();
    } catch (BadPaddingException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (IllegalBlockSizeException e) {
      e.printStackTrace();
    } 
    return null;
  }
  
  private static byte[] sha(byte[] bytes) throws NoSuchAlgorithmException {
    MessageDigest digest = MessageDigest.getInstance("SHA");
    return digest.digest(bytes);
  }
}
