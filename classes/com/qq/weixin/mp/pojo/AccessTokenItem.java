package com.qq.weixin.mp.pojo;

public class AccessTokenItem {
  private String appid;
  
  private AccessToken accessToken;
  
  private static long begin;
  
  public AccessToken getAccessToken() {
    return this.accessToken;
  }
  
  public void setAccessToken(AccessToken accessToken) {
    this.accessToken = accessToken;
  }
  
  public static long getBegin() {
    return begin;
  }
  
  public static void setBegin(long begin) {
    AccessTokenItem.begin = begin;
  }
  
  public String getAppid() {
    return this.appid;
  }
  
  public void setAppid(String appid) {
    this.appid = appid;
  }
}
