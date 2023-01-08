package com.js.oa.info.util;

import java.util.ArrayList;
import java.util.List;

public class ChannelNode {
  private ChannelNode parentNode;
  
  private List<ChannelNode> childList;
  
  private int includeChild;
  
  private String channelId;
  
  private String parentId;
  
  public ChannelNode() {
    this.childList = new ArrayList<ChannelNode>();
  }
  
  public ChannelNode(String channelId) {
    channelId = channelId;
    this.childList = new ArrayList<ChannelNode>();
  }
  
  public ChannelNode(String channelId, String parentId, int includeChild) {
    channelId = channelId;
    parentId = parentId;
    includeChild = includeChild;
    this.childList = new ArrayList<ChannelNode>();
  }
  
  public ChannelNode(String channelId, String parentId, int includeChild, ChannelNode parentNode) {
    channelId = channelId;
    parentId = parentId;
    includeChild = includeChild;
    parentNode = parentNode;
    this.childList = new ArrayList<ChannelNode>();
  }
  
  public List<ChannelNode> getChildList() {
    return this.childList;
  }
  
  public ChannelNode getParentNode() {
    return this.parentNode;
  }
  
  public void setParentNode(ChannelNode parentNode) {
    this.parentNode = parentNode;
  }
  
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
  
  public void setChildList(List<ChannelNode> childList) {
    this.childList = childList;
  }
}
