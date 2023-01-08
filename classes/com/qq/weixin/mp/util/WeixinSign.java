package com.qq.weixin.mp.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WeixinSign {
  public static void main(String[] args) {
    String jsapi_ticket = "jsapi_ticket";
    String url = "http://example.com";
    Map<String, String> ret = sign(jsapi_ticket, url);
    for (Map.Entry<String, String> entry : ret.entrySet())
      System.out.println((new StringBuilder()).append(entry.getKey()).append(", ").append(entry.getValue()).toString()); 
  }
  
  public static Map<String, String> sign(String jsapi_ticket, String url) {
    Map<String, String> ret = new HashMap<String, String>();
    String nonce_str = create_nonce_str();
    String timestamp = create_timestamp();
    jsapi_ticket = WeixinUtil.getJsapi_ticket().getTicket();
    String signature = "";
    String string1 = "jsapi_ticket=" + jsapi_ticket + 
      "&noncestr=" + nonce_str + 
      "&timestamp=" + timestamp + 
      "&url=" + url;
    try {
      MessageDigest crypt = MessageDigest.getInstance("SHA-1");
      crypt.reset();
      crypt.update(string1.getBytes("UTF-8"));
      signature = byteToHex(crypt.digest());
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } 
    ret.put("url", url);
    ret.put("jsapi_ticket", jsapi_ticket);
    ret.put("nonceStr", nonce_str);
    ret.put("timestamp", timestamp);
    ret.put("signature", signature);
    return ret;
  }
  
  private static String byteToHex(byte[] hash) {
    Formatter formatter = new Formatter();
    byte b;
    int i;
    byte[] arrayOfByte;
    for (i = (arrayOfByte = hash).length, b = 0; b < i; ) {
      byte b1 = arrayOfByte[b];
      formatter.format("%02x", new Object[] { Byte.valueOf(b1) });
      b++;
    } 
    String result = formatter.toString();
    formatter.close();
    return result;
  }
  
  private static String create_nonce_str() {
    return UUID.randomUUID().toString();
  }
  
  private static String create_timestamp() {
    return Long.toString(System.currentTimeMillis() / 1000L);
  }
}
