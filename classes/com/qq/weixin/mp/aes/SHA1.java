package com.qq.weixin.mp.aes;

import java.security.MessageDigest;
import java.util.Arrays;

class SHA1 {
  public static String getSHA1(String token, String timestamp, String nonce, String encrypt) throws AesException {
    try {
      String[] array = { token, timestamp, nonce, encrypt };
      StringBuffer sb = new StringBuffer();
      Arrays.sort((Object[])array);
      for (int i = 0; i < 4; i++)
        sb.append(array[i]); 
      String str = sb.toString();
      MessageDigest md = MessageDigest.getInstance("SHA-1");
      md.update(str.getBytes());
      byte[] digest = md.digest();
      StringBuffer hexstr = new StringBuffer();
      String shaHex = "";
      for (int j = 0; j < digest.length; j++) {
        shaHex = Integer.toHexString(digest[j] & 0xFF);
        if (shaHex.length() < 2)
          hexstr.append(0); 
        hexstr.append(shaHex);
      } 
      return hexstr.toString();
    } catch (Exception e) {
      e.printStackTrace();
      throw new AesException(-40003);
    } 
  }
}
