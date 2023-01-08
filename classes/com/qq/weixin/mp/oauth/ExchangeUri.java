package com.qq.weixin.mp.oauth;

import com.qq.weixin.mp.util.WeixinUtil;

public class ExchangeUri {
  private static final String URI = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE&agentid=AGENTID";
  
  private String code;
  
  private String agentId = "";
  
  private String app = "";
  
  public ExchangeUri(String code, String appId) {
    this.agentId = appId;
    this.app = this.app;
    this.code = code;
  }
  
  public String getUri() {
    String uri = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE&agentid=AGENTID";
    uri = uri.replace("ACCESS_TOKEN", WeixinUtil.getAccessToken(this.agentId).getToken()).replace("CODE", this.code)
      .replace("AGENTID", this.agentId);
    return uri;
  }
}
