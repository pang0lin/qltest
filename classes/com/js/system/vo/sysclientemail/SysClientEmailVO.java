package com.js.system.vo.sysclientemail;

public class SysClientEmailVO {
  private long id;
  
  private String sendTime;
  
  private String emailUrl;
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public String getEmailUrl() {
    return this.emailUrl;
  }
  
  public void setEmailUrl(String emailUrl) {
    this.emailUrl = emailUrl;
  }
  
  public String getSendTime() {
    return this.sendTime;
  }
  
  public void setSendTime(String sendTime) {
    this.sendTime = sendTime;
  }
}
