package com.ali.dingding.util;

import com.ali.dingding.pojo.AccessToken;
import com.ali.dingding.pojo.AccessTokenItem;
import com.ali.dingding.pojo.AccessTokenRoom;
import com.alibaba.fastjson.JSONObject;
import com.js.oa.weixin.manage.DingdingManageAction;
import java.util.Date;
import net.sf.json.JSONException;
import org.apache.log4j.Logger;

public class DingdingUtil {
  private static Logger log = Logger.getLogger(DingdingUtil.class);
  
  private static final String access_token_url = "https://oapi.dingtalk.com/gettoken?corpid=CORPID&corpsecret=CORPSECRET";
  
  private static final String jsapi_ticket_url = "https://oapi.dingtalk.com/get_jsapi_ticket?access_token=ACCESS_TOKE";
  
  public static AccessToken getAccessToken() throws OApiException {
    AccessToken accessToken = null;
    AccessTokenRoom.getAccessTokenItem();
    long time = (new Date()).getTime() - AccessTokenItem.getBegin();
    AccessTokenItem accessTokenItemTemp = AccessTokenRoom.getAccessTokenItem();
    if (accessTokenItemTemp != null && time < (accessTokenItemTemp.getAccessToken().getExpiresIn() * 1000)) {
      accessToken = accessTokenItemTemp.getAccessToken();
    } else {
      String appSecret = DingdingManageAction.getPropValue("C_Secret");
      String corpId = DingdingManageAction.getPropValue("sCorpID");
      String requestUrl = "https://oapi.dingtalk.com/gettoken?corpid=CORPID&corpsecret=CORPSECRET".replace("CORPID", corpId).replace("CORPSECRET", appSecret);
      JSONObject jsonObject = HttpHelper.httpGet(requestUrl);
      if (jsonObject != null)
        try {
          accessToken = new AccessToken();
          String tokenString = jsonObject.getString("access_token");
          accessToken.setToken(tokenString);
          accessToken.setExpiresIn(7200);
          jsonObject = HttpHelper.httpGet("https://oapi.dingtalk.com/get_jsapi_ticket?access_token=ACCESS_TOKE".replace("ACCESS_TOKE", tokenString));
          if (jsonObject != null)
            accessToken.setJsapiTicket(jsonObject.getString("ticket")); 
          AccessTokenRoom.setAccessToken(accessToken);
          log.info("获取access_token成功，access_token = " + accessToken.getToken());
        } catch (JSONException e) {
          accessToken = null;
        }  
    } 
    return accessToken;
  }
  
  public static void main(String[] args) throws OApiException {
    getAccessToken();
    System.out.println("at1 = ");
  }
}
