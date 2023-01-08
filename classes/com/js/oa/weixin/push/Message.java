package com.js.oa.weixin.push;

import java.util.Date;

public class Message {
  private Integer toUserId;
  
  private String type;
  
  private String title;
  
  private String url;
  
  private Date time = null;
  
  private String sendUserName;
  
  public Integer getToUserId() {
    return this.toUserId;
  }
  
  public void setToUserId(Integer toUserId) {
    this.toUserId = toUserId;
  }
  
  public String getType() {
    return this.type;
  }
  
  public void setType(String type) {
    this.type = type;
  }
  
  public String getTitle() {
    return this.title;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  public String getUrl() {
    return this.url;
  }
  
  public void setUrl(String url) {
    this.url = url;
  }
  
  public Date getTime() {
    return this.time;
  }
  
  public void setTime(Date time) {
    this.time = time;
  }
  
  public String getSendUserName() {
    return this.sendUserName;
  }
  
  public void setSendUserName(String sendUserName) {
    this.sendUserName = sendUserName;
  }
}
