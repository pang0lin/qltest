package com.js.oa.security.ikey;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HmacMD5 {
  public String ByteArrayToString(byte[] tk, int iLen) {
    byte[] tk1 = new byte[iLen * 2 + 1];
    for (int i = 0; i < iLen; i++) {
      int iTemp = tk[i];
      if (tk[i] < 0) {
        iTemp = tk[i] + 256;
      } else {
        iTemp = tk[i];
      } 
      byte bTemp = (byte)(iTemp / 16);
      if (bTemp < 10) {
        tk1[i * 2] = (byte)(bTemp + 48);
      } else {
        tk1[i * 2] = (byte)(bTemp - 10 + 65);
      } 
      bTemp = (byte)(iTemp % 16);
      if (bTemp < 10) {
        tk1[i * 2 + 1] = (byte)(bTemp + 48);
      } else {
        tk1[i * 2 + 1] = (byte)(bTemp - 10 + 65);
      } 
    } 
    tk1[iLen * 2] = 0;
    String strTemp = new String(tk1);
    return strTemp;
  }
  
  public byte[] StringToByteArray(String strHex, int iLen) {
    byte[] tk = new byte[iLen];
    int strLen = strHex.length();
    if (strLen < 2 * iLen);
    for (int i = 0; i < iLen; i++) {
      int bTemp, bValue = strHex.charAt(i * 2);
      if (48 <= bValue && bValue <= 57) {
        bTemp = (bValue - 48) * 16;
      } else if (97 <= bValue && bValue <= 102) {
        bTemp = (bValue - 97 + 10) * 16;
      } else if (65 <= bValue && bValue <= 70) {
        bTemp = (bValue - 65 + 10) * 16;
      } else {
        bTemp = 0;
      } 
      bValue = strHex.charAt(i * 2 + 1);
      if (48 <= bValue && bValue <= 57) {
        bTemp += bValue - 48;
      } else if (97 <= bValue && bValue <= 102) {
        bTemp += bValue - 97 + 10;
      } else if (65 <= bValue && bValue <= 70) {
        bTemp += bValue - 65 + 10;
      } 
      if (bTemp > 128)
        bTemp -= 256; 
      tk[i] = (byte)bTemp;
    } 
    return tk;
  }
  
  public String GenerateDigest(String str, String str1) {
    int key_len = 0;
    int data_len = 0;
    int i = 0;
    byte[] k_ipad = new byte[64];
    byte[] k_opad = new byte[64];
    byte[] tk = new byte[64];
    byte[] tk1 = new byte[64];
    key_len = str1.length();
    byte[] KeyBytes = new byte[key_len];
    KeyBytes = str1.getBytes();
    byte[] Temp = new byte[key_len];
    if (key_len > 64)
      try {
        MessageDigest Digest = MessageDigest.getInstance("MD5");
        Digest.update(KeyBytes);
        tk = Digest.digest();
        key_len = 16;
        for (i = 0; i < 16; i++)
          KeyBytes[i] = tk[i]; 
      } catch (NoSuchAlgorithmException ex) {
        ex.printStackTrace();
        return "";
      }  
    for (i = 0; i < key_len; i++) {
      Temp[i] = KeyBytes[i];
      KeyBytes[i] = (byte)(KeyBytes[i] ^ 0x36);
      k_ipad[i] = KeyBytes[i];
      Temp[i] = (byte)(Temp[i] ^ 0x5C);
      k_opad[i] = Temp[i];
    } 
    while (i < 64) {
      k_ipad[i] = 54;
      k_opad[i] = 92;
      i++;
    } 
    try {
      data_len = str.length() / 2;
      byte[] KeyBytes1 = new byte[data_len];
      KeyBytes1 = StringToByteArray(str, data_len);
      MessageDigest Digest = MessageDigest.getInstance("MD5");
      Digest.update(k_ipad);
      Digest.update(KeyBytes1);
      tk = Digest.digest();
    } catch (NoSuchAlgorithmException ex) {
      ex.printStackTrace();
      return "";
    } 
    try {
      MessageDigest Digest1 = MessageDigest.getInstance("MD5");
      Digest1.update(k_opad);
      Digest1.update(tk);
      tk1 = Digest1.digest();
      return ByteArrayToString(tk1, 16);
    } catch (NoSuchAlgorithmException ex) {
      ex.printStackTrace();
      return "";
    } 
  }
}
