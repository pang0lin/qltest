package com.qq.weixin.mp.util;

import com.js.oa.weixin.manage.WeixinManageAction;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import org.apache.log4j.Logger;

public class SignUtil {
  private static Logger log = Logger.getLogger(SignUtil.class);
  
  private static String token;
  
  static {
    renewToken();
  }
  
  public static void renewToken() {
    token = WeixinManageAction.getPropValue("token");
    log.debug("token = " + token);
  }
  
  public static boolean checkSignature(String signature, String timestamp, String nonce) {
    String[] arr = { token, timestamp, nonce };
    Arrays.sort((Object[])arr);
    StringBuilder content = new StringBuilder();
    for (int i = 0; i < arr.length; i++)
      content.append(arr[i]); 
    MessageDigest md = null;
    String tmpStr = null;
    byte[] bts = content.toString().getBytes();
    try {
      md = MessageDigest.getInstance("SHA-1");
      byte[] digest = md.digest(bts);
      tmpStr = byteToStr(digest);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } 
    return (tmpStr != null) ? tmpStr.equalsIgnoreCase(signature) : false;
  }
  
  private static String byteToStr(byte[] bts) {
    String res = "";
    String tmp = null;
    for (int i = 0; i < bts.length; i++) {
      tmp = Integer.toHexString(bts[i] & 0xFF);
      if (tmp.length() == 1)
        res = String.valueOf(res) + "0"; 
      res = String.valueOf(res) + tmp;
    } 
    return res;
  }
}
