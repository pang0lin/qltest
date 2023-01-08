package com.js.system.vo.rssmanager;

import java.io.Serializable;

public class CategoryChannelVO implements Serializable {
  private Long channelId;
  
  private Long categoryId;
  
  private Long createUserId;
  
  private String channelName;
  
  private String channelUrl;
  
  private String createTime;
  
  private String channelDesc;
  
  private String string1;
  
  private String string2;
  
  public Long getCategoryId() {
    return this.categoryId;
  }
  
  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }
  
  public String getCreateTime() {
    return this.createTime;
  }
  
  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }
  
  public Long getCreateUserId() {
    return this.createUserId;
  }
  
  public void setCreateUserId(Long createUserId) {
    this.createUserId = createUserId;
  }
  
  public String getString1() {
    return this.string1;
  }
  
  public void setString1(String string1) {
    this.string1 = string1;
  }
  
  public String getString2() {
    return this.string2;
  }
  
  public void setString2(String string2) {
    this.string2 = string2;
  }
  
  public String getChannelDesc() {
    return this.channelDesc;
  }
  
  public void setChannelDesc(String channelDesc) {
    this.channelDesc = channelDesc;
  }
  
  public Long getChannelId() {
    return this.channelId;
  }
  
  public void setChannelId(Long channelId) {
    this.channelId = channelId;
  }
  
  public String getChannelName() {
    return this.channelName;
  }
  
  public void setChannelName(String channelName) {
    this.channelName = channelName;
  }
  
  public String getChannelUrl() {
    return this.channelUrl;
  }
  
  public void setChannelUrl(String channelUrl) {
    this.channelUrl = channelUrl;
  }
}
