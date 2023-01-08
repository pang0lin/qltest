package com.ali.dingding.util;

import com.ali.dingding.pojo.AccessToken;
import com.alibaba.fastjson.JSONObject;

public class DingdingUserUtil {
  private static String GET_USER_URL = "https://oapi.dingtalk.com/user/get?access_token=ACCESS_TOKEN&userid=USERID";
  
  public static void main(String[] args) throws OApiException {
    String str = "abcdefg";
  }
  
  public static String getUserAvatar(String userId) throws OApiException {
    String avatar = "";
    AccessToken at = DingdingUtil.getAccessToken();
    String url = GET_USER_URL.replace("ACCESS_TOKEN", at.getToken()).replace("USERID", userId);
    JSONObject result = HttpHelper.httpGet(url);
    if (result.containsKey("avatar"))
      avatar = result.getString("avatar"); 
    if (avatar == null)
      avatar = ""; 
    if (!avatar.equals("") && avatar.endsWith("/0"))
      avatar = String.valueOf(avatar.substring(0, avatar.length() - 2)) + "/64"; 
    return avatar;
  }
}
