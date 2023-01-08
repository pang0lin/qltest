package com.js.oa.lanxin.util;

import com.qq.weixin.mp.util.WeixinUtil;
import net.sf.json.JSONObject;

public class LanXinUtil {
  private static final String LANXIN_URL = "http://open-dev.lanxin.cn/sns/oauth2/access_token?code=CODE&appid=APPID&grant_type=authorization_code";
  
  public static String getPhoneNoByCode(String code, String appid) {
    String phoneNo = "";
    String url = "http://open-dev.lanxin.cn/sns/oauth2/access_token?code=CODE&appid=APPID&grant_type=authorization_code".replaceAll("CODE", code).replaceAll("APPID", appid);
    JSONObject result = WeixinUtil.httpRequestForHttp(url, "GET", null);
    try {
      phoneNo = result.getString("openid");
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return phoneNo;
  }
}
