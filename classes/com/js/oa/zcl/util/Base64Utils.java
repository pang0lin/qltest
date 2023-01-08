package com.js.oa.zcl.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Base64Utils {
  private static final char[] legalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789._".toCharArray();
  
  private static final char fillChar = '@';
  
  public static String encode(byte[] data) {
    int start = 0;
    int len = data.length;
    StringBuffer buf = new StringBuffer(data.length * 3 / 2);
    int end = len - 3;
    int i = start;
    int n = 0;
    while (i <= end) {
      int d = (data[i] & 0xFF) << 16 | (
        data[i + 1] & 0xFF) << 8 | 
        data[i + 2] & 0xFF;
      buf.append(legalChars[d >> 18 & 0x3F]);
      buf.append(legalChars[d >> 12 & 0x3F]);
      buf.append(legalChars[d >> 6 & 0x3F]);
      buf.append(legalChars[d & 0x3F]);
      i += 3;
      if (n++ >= 14) {
        n = 0;
        buf.append(" ");
      } 
    } 
    if (i == start + len - 2) {
      int d = (data[i] & 0xFF) << 16 | (
        data[i + 1] & 0xFF) << 8;
      buf.append(legalChars[d >> 18 & 0x3F]);
      buf.append(legalChars[d >> 12 & 0x3F]);
      buf.append(legalChars[d >> 6 & 0x3F]);
      buf.append('@');
    } else if (i == start + len - 1) {
      int d = (data[i] & 0xFF) << 16;
      buf.append(legalChars[d >> 18 & 0x3F]);
      buf.append(legalChars[d >> 12 & 0x3F]);
      buf.append('@');
      buf.append('@');
    } 
    return buf.toString();
  }
  
  private static int decode(char c) {
    if (c >= 'A' && c <= 'Z')
      return c - 65; 
    if (c >= 'a' && c <= 'z')
      return c - 97 + 26; 
    if (c >= '0' && c <= '9')
      return c - 48 + 26 + 26; 
    switch (c) {
      case '.':
        return 62;
      case '_':
        return 63;
      case '@':
        return 0;
    } 
    throw new RuntimeException("unexpected code: " + c);
  }
  
  public static byte[] decode(String s) {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    try {
      decode(s, bos);
    } catch (IOException e) {
      throw new RuntimeException();
    } 
    byte[] decodedBytes = bos.toByteArray();
    try {
      bos.close();
      bos = null;
    } catch (IOException ex) {
      System.err.println("Error while decoding BASE64: " + ex.toString());
    } 
    return decodedBytes;
  }
  
  private static void decode(String s, OutputStream os) throws IOException {
    int i = 0;
    int len = s.length();
    while (true) {
      if (i >= len || s.charAt(i) > ' ') {
        if (i == len)
          break; 
        int tri = (decode(s.charAt(i)) << 18) + (
          decode(s.charAt(i + 1)) << 12) + (
          decode(s.charAt(i + 2)) << 6) + 
          decode(s.charAt(i + 3));
        os.write(tri >> 16 & 0xFF);
        if (s.charAt(i + 2) == '@')
          break; 
        os.write(tri >> 8 & 0xFF);
        if (s.charAt(i + 3) == '@')
          break; 
        os.write(tri & 0xFF);
        i += 4;
        continue;
      } 
      i++;
    } 
  }
}
