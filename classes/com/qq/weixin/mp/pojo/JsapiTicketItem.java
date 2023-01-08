package com.qq.weixin.mp.pojo;

public class JsapiTicketItem {
  private String appid;
  
  private JsapiTicket jsapiTicket;
  
  private static long begin;
  
  public JsapiTicket getJsapiTicket() {
    return this.jsapiTicket;
  }
  
  public void setJsapiTicket(JsapiTicket jsapiTicket) {
    this.jsapiTicket = jsapiTicket;
  }
  
  public static long getBegin() {
    return begin;
  }
  
  public static void setBegin(long begin) {
    JsapiTicketItem.begin = begin;
  }
  
  public String getAppid() {
    return this.appid;
  }
  
  public void setAppid(String appid) {
    this.appid = appid;
  }
}
