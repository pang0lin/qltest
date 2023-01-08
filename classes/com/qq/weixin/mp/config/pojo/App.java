package com.qq.weixin.mp.config.pojo;

import java.util.List;

public class App {
  private String appid;
  
  private String appbh;
  
  private String appname;
  
  private String subscribeTip;
  
  private String imgURL;
  
  private String dingId;
  
  private String dingUrl;
  
  private String appSecret;
  
  List<Menu> menu;
  
  public String getAppSecret() {
    return this.appSecret;
  }
  
  public void setAppSecret(String appSecret) {
    this.appSecret = appSecret;
  }
  
  public String getAppid() {
    return this.appid;
  }
  
  public void setAppid(String appid) {
    this.appid = appid;
  }
  
  public String getAppbh() {
    return this.appbh;
  }
  
  public void setAppbh(String appbh) {
    this.appbh = appbh;
  }
  
  public String getAppname() {
    return this.appname;
  }
  
  public void setAppname(String appname) {
    this.appname = appname;
  }
  
  public List<Menu> getMenu() {
    return this.menu;
  }
  
  public void setMenu(List<Menu> menu) {
    this.menu = menu;
  }
  
  public String getSubscribeTip() {
    return this.subscribeTip;
  }
  
  public void setSubscribeTip(String subscribeTip) {
    this.subscribeTip = subscribeTip;
  }
  
  public String getImgURL() {
    return this.imgURL;
  }
  
  public void setImgURL(String imgURL) {
    this.imgURL = imgURL;
  }
  
  public String getDingId() {
    return this.dingId;
  }
  
  public void setDingId(String dingId) {
    this.dingId = dingId;
  }
  
  public String getDingUrl() {
    return this.dingUrl;
  }
  
  public void setDingUrl(String dingUrl) {
    this.dingUrl = dingUrl;
  }
}
