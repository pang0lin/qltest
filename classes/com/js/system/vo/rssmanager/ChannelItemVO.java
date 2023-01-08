package com.js.system.vo.rssmanager;

import java.io.Serializable;

public class ChannelItemVO implements Serializable {
  private Long itemId;
  
  private Long channelId;
  
  private String itemLink;
  
  private String itemTitle;
  
  private String itemDesc;
  
  private String pubDate;
  
  private String readState;
  
  public Long getChannelId() {
    return this.channelId;
  }
  
  public String getReadState() {
    return this.readState;
  }
  
  public void setReadState(String readState) {
    this.readState = readState;
  }
  
  public void setChannelId(Long channelId) {
    this.channelId = channelId;
  }
  
  public String getItemDesc() {
    return this.itemDesc;
  }
  
  public void setItemDesc(String itemDesc) {
    this.itemDesc = itemDesc;
  }
  
  public Long getItemId() {
    return this.itemId;
  }
  
  public void setItemId(Long itemId) {
    this.itemId = itemId;
  }
  
  public String getItemLink() {
    return this.itemLink;
  }
  
  public void setItemLink(String itemLink) {
    this.itemLink = itemLink;
  }
  
  public String getItemTitle() {
    return this.itemTitle;
  }
  
  public void setItemTitle(String itemTitle) {
    this.itemTitle = itemTitle;
  }
  
  public String getPubDate() {
    return this.pubDate;
  }
  
  public void setPubDate(String pubDate) {
    this.pubDate = pubDate;
  }
}
