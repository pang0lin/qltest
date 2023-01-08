package com.js.oa.online.po;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ChatAccessoryPO {
  private Long accessoryId;
  
  private ChatPO chatPO;
  
  private String accessoryName;
  
  private String accessorySavename;
  
  public Long getAccessoryId() {
    return this.accessoryId;
  }
  
  public void setAccessoryId(Long accessoryId) {
    this.accessoryId = accessoryId;
  }
  
  public ChatPO getChatPO() {
    return this.chatPO;
  }
  
  public void setChatPO(ChatPO chatPO) {
    this.chatPO = chatPO;
  }
  
  public String getAccessoryName() {
    return this.accessoryName;
  }
  
  public void setAccessoryName(String accessoryName) {
    this.accessoryName = accessoryName;
  }
  
  public String getAccessorySavename() {
    return this.accessorySavename;
  }
  
  public void setAccessorySavename(String accessorySavename) {
    this.accessorySavename = accessorySavename;
  }
  
  public ChatAccessoryPO() {}
  
  public ChatAccessoryPO(Long accessoryId, ChatPO chatPO, String accessoryName, String accessorySavename) {
    this.accessoryId = accessoryId;
    this.chatPO = chatPO;
    this.accessoryName = accessoryName;
    this.accessorySavename = accessorySavename;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("accessoryId", getAccessoryId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof ChatAccessoryPO))
      return false; 
    ChatAccessoryPO chatAccessoryPO = (ChatAccessoryPO)other;
    return (new EqualsBuilder())
      .append(getAccessorySavename(), chatAccessoryPO.getAccessorySavename())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getAccessoryId())
      .toHashCode();
  }
}
