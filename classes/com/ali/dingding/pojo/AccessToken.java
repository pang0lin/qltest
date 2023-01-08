package com.ali.dingding.pojo;

public class AccessToken {
  private String token;
  
  private String jsapiTicket;
  
  private int expiresIn;
  
  public String getToken() {
    return this.token;
  }
  
  public void setToken(String token) {
    this.token = token;
  }
  
  public String getJsapiTicket() {
    return this.jsapiTicket;
  }
  
  public void setJsapiTicket(String jsapiTicket) {
    this.jsapiTicket = jsapiTicket;
  }
  
  public int getExpiresIn() {
    return this.expiresIn;
  }
  
  public void setExpiresIn(int expiresIn) {
    this.expiresIn = expiresIn;
  }
}
