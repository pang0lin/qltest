package com.js.oa.weixin.manage;

import org.apache.struts.action.ActionForm;

public class WeixinManageActionForm extends ActionForm {
  private String appId;
  
  private String appSecret;
  
  private String serverUrl;
  
  private String token;
  
  private String geotable_id;
  
  public String getAppId() {
    return this.appId;
  }
  
  public void setAppId(String appId) {
    this.appId = appId;
  }
  
  public String getAppSecret() {
    return this.appSecret;
  }
  
  public void setAppSecret(String appSecret) {
    this.appSecret = appSecret;
  }
  
  public String getServerUrl() {
    return this.serverUrl;
  }
  
  public void setServerUrl(String serverUrl) {
    this.serverUrl = serverUrl;
  }
  
  public String getToken() {
    return this.token;
  }
  
  public void setToken(String token) {
    this.token = token;
  }
  
  public String getGeotable_id() {
    return this.geotable_id;
  }
  
  public void setGeotable_id(String geotable_id) {
    this.geotable_id = geotable_id;
  }
}
