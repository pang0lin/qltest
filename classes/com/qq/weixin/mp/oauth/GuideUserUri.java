package com.qq.weixin.mp.oauth;

import com.js.oa.weixin.manage.WeixinManageAction;

public class GuideUserUri {
  private static final String URI = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=RESPONSE_TYPE&scope=SCOPE&state=STATE#wechat_redirect";
  
  private String sCorpID;
  
  private String redirect_uri;
  
  private static final String response_type = "code";
  
  private String scope = "jsoa";
  
  private String state;
  
  public GuideUserUri() {
    this.sCorpID = WeixinManageAction.getPropValue("sCorpID");
    setScope(true);
  }
  
  public void setScope(boolean isBaseOrUserinfo) {
    this.scope = isBaseOrUserinfo ? "snsapi_base" : "snsapi_userinfo";
  }
  
  public String getUri() {
    String uri = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=RESPONSE_TYPE&scope=SCOPE&state=STATE#wechat_redirect";
    uri = uri.replace("APPID", this.sCorpID).replace("REDIRECT_URI", this.redirect_uri)
      .replace("RESPONSE_TYPE", "code").replace("SCOPE", this.scope)
      .replace("STATE", (this.state == null) ? "" : this.state);
    return uri;
  }
  
  public void setRedirect_uri(String redirect_uri) {
    this.redirect_uri = redirect_uri.replace(":", "%3A").replace("/", "%2F")
      .replace("?", "%3F").replace("=", "%3D").replace("&", "%26");
  }
  
  public void setState(String state) {
    this.state = state;
  }
}
