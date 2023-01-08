package com.qq.weixin.mp.pojo;

public class JsapiTicket {
  private String ticket;
  
  private int expiresIn;
  
  public String getTicket() {
    return this.ticket;
  }
  
  public void setTicket(String ticket) {
    this.ticket = ticket;
  }
  
  public int getExpiresIn() {
    return this.expiresIn;
  }
  
  public void setExpiresIn(int expiresIn) {
    this.expiresIn = expiresIn;
  }
}
