package com.qq.weixin.mp.test;

import org.apache.log4j.Logger;

public class UserAuthorize2 {
  private static Logger log = Logger.getLogger(UserAuthorize2.class);
  
  private static final String OBTAIN_CODE_PAGE = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
  
  public static String obtainCode() {
    String url = "";
    String appId = "wx110b77cd5b083202";
    String redirectUri = "http://kodoyang.nat123.net/jsoa/weixin?/WorkDealWithAction.do?action=list&status=0";
    redirectUri = redirectUri.replace(":", "%3A").replace("/", "%2F").replace("?", "%3F").replace("=", "%3D").replace("&", "%26");
    String scope = "snsapi_base";
    String state = "123";
    url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect".replace("APPID", appId);
    url = url.replace("REDIRECT_URI", redirectUri);
    url = url.replace("SCOPE", scope);
    url = url.replace("STATE", state);
    log.info("url = " + url);
    return url;
  }
}
