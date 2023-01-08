package com.js.oa.info.util;

public class ChannelTree {
  private ChannelNode root;
  
  public ChannelTree() {
    this.root = new ChannelNode("0");
  }
  
  public ChannelTree(String channelId, String parentId, int includeChild) {
    this.root = new ChannelNode(channelId, parentId, includeChild);
  }
  
  public String iterate(ChannelNode node) {
    StringBuffer sb = new StringBuffer();
    if (node != null)
      for (ChannelNode curNode : node.getChildList()) {
        if (curNode.getIncludeChild() == 1) {
          sb.append(curNode.getChannelId()).append(",");
          if (curNode.getChildList() != null && curNode.getChildList().size() > 0)
            sb.append(iterate(curNode)).append(","); 
        } 
      }  
    return sb.toString();
  }
}
