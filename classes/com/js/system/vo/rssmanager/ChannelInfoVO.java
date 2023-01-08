package com.js.system.vo.rssmanager;

import java.io.Serializable;

public class ChannelInfoVO implements Serializable {
  private Long infoId;
  
  private Long channelId;
  
  private String channelLink;
  
  private String channelTitle;
  
  private String channelDesc;
  
  private String imageUrl;
  
  private String imageTitle;
  
  private String imageLink;
  
  private String copyRight;
  
  private String pubDate;
  
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
  
  public String getChannelLink() {
    return this.channelLink;
  }
  
  public void setChannelLink(String channelLink) {
    this.channelLink = channelLink;
  }
  
  public String getChannelTitle() {
    return this.channelTitle;
  }
  
  public void setChannelTitle(String channelTitle) {
    this.channelTitle = channelTitle;
  }
  
  public String getCopyRight() {
    return this.copyRight;
  }
  
  public void setCopyRight(String copyRight) {
    this.copyRight = copyRight;
  }
  
  public String getImageLink() {
    return this.imageLink;
  }
  
  public void setImageLink(String imageLink) {
    this.imageLink = imageLink;
  }
  
  public String getImageTitle() {
    return this.imageTitle;
  }
  
  public void setImageTitle(String imageTitle) {
    this.imageTitle = imageTitle;
  }
  
  public String getImageUrl() {
    return this.imageUrl;
  }
  
  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }
  
  public Long getInfoId() {
    return this.infoId;
  }
  
  public void setInfoId(Long infoId) {
    this.infoId = infoId;
  }
  
  public String getPubDate() {
    return this.pubDate;
  }
  
  public void setPubDate(String pubDate) {
    this.pubDate = pubDate;
  }
}
