package com.qq.weixin.mp.util;

import com.qq.weixin.mp.pojo.AccessToken;
import net.sf.json.JSONObject;

public class WeixinUserUtil {
  private static String GET_USER_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&userid=USERID";
  
  public static void main(String[] args) {
    String str = "abcdefg";
    System.out.println(str.substring(0, str.length() - 1));
  }
  
  public static String getUserAvatar(String userId) {
    String avatar = "";
    AccessToken at = WeixinUtil.getAccessToken();
    String url = GET_USER_URL.replace("ACCESS_TOKEN", at.getToken()).replace("USERID", userId);
    JSONObject result = WeixinUtil.httpRequest(url, "GET", null);
    if ("0".equals(result.getString("errcode"))) {
      if (result.containsKey("avatar"))
        avatar = result.getString("avatar"); 
      if (avatar == null)
        avatar = ""; 
      if (!avatar.equals("") && avatar.endsWith("/0"))
        avatar = String.valueOf(avatar.substring(0, avatar.length() - 2)) + "/100"; 
    } 
    return avatar;
  }
}
