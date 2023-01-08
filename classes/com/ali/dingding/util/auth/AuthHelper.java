package com.ali.dingding.util.auth;

import com.ali.dingding.util.HttpHelper;
import com.ali.dingding.util.OApiException;
import com.ali.dingding.util.OApiResultException;
import com.alibaba.fastjson.JSONObject;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class AuthHelper {
  public static String getAccessToken() throws OApiException {
    String url = "https://oapi.dingtalk.com/gettoken?corpid=dingc10f6331efb8649e&corpsecret=O0EgI5ol-euXWoA2Ls5N7jtRtBtBhRLtdOeOudTtLTheiUO3UQcdDokGJrICIgrS";
    JSONObject response = HttpHelper.httpGet(url);
    if (response.containsKey("access_token"))
      return response.getString("access_token"); 
    throw new OApiResultException("access_token");
  }
  
  public static String getJsapiTicket(String accessToken) throws OApiException {
    String url = "https://oapi.dingtalk.com/get_jsapi_ticket?type=jsapi&access_token=" + 
      accessToken;
    JSONObject response = HttpHelper.httpGet(url);
    if (response.containsKey("ticket"))
      return response.getString("ticket"); 
    throw new OApiResultException("ticket");
  }
  
  public static String sign(String ticket, String nonceStr, long timeStamp, String url) throws OApiException {
    String plain = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr + 
      "&timestamp=" + String.valueOf(timeStamp) + "&url=" + url;
    try {
      MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
      sha1.reset();
      sha1.update(plain.getBytes("UTF-8"));
      return bytesToHex(sha1.digest());
    } catch (NoSuchAlgorithmException e) {
      throw new OApiResultException(e.getMessage());
    } catch (UnsupportedEncodingException e) {
      throw new OApiResultException(e.getMessage());
    } 
  }
  
  private static String bytesToHex(byte[] hash) {
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
}
