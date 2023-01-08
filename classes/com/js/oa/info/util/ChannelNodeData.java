package com.js.oa.info.util;

public class ChannelNodeData {
  private int includeChild;
  
  private String channelId;
  
  private String parentId;
  
  public int getIncludeChild() {
    return this.includeChild;
  }
  
  public void setIncludeChild(int includeChild) {
    this.includeChild = includeChild;
  }
  
  public String getChannelId() {
    return this.channelId;
  }
  
  public void setChannelId(String channelId) {
    this.channelId = channelId;
  }
  
  public String getParentId() {
    return this.parentId;
  }
  
  public void setParentId(String parentId) {
    this.parentId = parentId;
  }
}
