package com.js.oa.online.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ChatPO implements Serializable {
  private Long chatId;
  
  private String sendUserId;
  
  private String toUserId;
  
  private String content;
  
  private String chatHasaccessory;
  
  private String chatTime;
  
  private String isRead;
  
  private Set chatAccessory = null;
  
  private String msgSelfFlag;
  
  private String accessorySize;
  
  private String chatFlag;
  
  public Set getChatAccessory() {
    return this.chatAccessory;
  }
  
  public void setChatAccessory(Set chatAccessory) {
    this.chatAccessory = chatAccessory;
  }
  
  public ChatPO(Long chatId, String sendUserId, String toUserId, String content, String chatHasaccessory, String chatTime, String isRead, Set chatAccessory) {
    this.chatId = chatId;
    this.sendUserId = sendUserId;
    this.toUserId = toUserId;
    this.content = content;
    this.chatHasaccessory = chatHasaccessory;
    this.chatTime = chatTime;
    this.isRead = isRead;
    this.chatAccessory = chatAccessory;
    this.chatFlag = String.valueOf((new Date()).getTime()) + ";;;" + sendUserId;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("chatId", getChatId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof ChatPO))
      return false; 
    ChatPO chatPO = (ChatPO)other;
    return (new EqualsBuilder())
      .append(getChatId(), chatPO.getChatId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getChatId())
      .toHashCode();
  }
  
  public ChatPO() {}
  
  public String getIsRead() {
    return this.isRead;
  }
  
  public void setIsRead(String isRead) {
    this.isRead = isRead;
  }
  
  public String getChatTime() {
    return this.chatTime;
  }
  
  public void setChatTime(String chatTime) {
    this.chatTime = chatTime;
  }
  
  public String getSendUserId() {
    return this.sendUserId;
  }
  
  public void setSendUserId(String sendUserId) {
    this.sendUserId = sendUserId;
  }
  
  public String getToUserId() {
    return this.toUserId;
  }
  
  public void setToUserId(String toUserId) {
    this.toUserId = toUserId;
  }
  
  public String getContent() {
    return this.content;
  }
  
  public void setContent(String content) {
    this.content = content;
  }
  
  public String getChatHasaccessory() {
    return this.chatHasaccessory;
  }
  
  public void setChatHasaccessory(String chatHasaccessory) {
    this.chatHasaccessory = chatHasaccessory;
  }
  
  public Long getChatId() {
    return this.chatId;
  }
  
  public void setChatId(Long chatId) {
    this.chatId = chatId;
  }
  
  public String getAccessorySize() {
    return this.accessorySize;
  }
  
  public void setAccessorySize(String accessorySize) {
    this.accessorySize = accessorySize;
  }
  
  public String getMsgSelfFlag() {
    return this.msgSelfFlag;
  }
  
  public void setMsgSelfFlag(String msgSelfFlag) {
    this.msgSelfFlag = msgSelfFlag;
  }
  
  public String getChatFlag() {
    return this.chatFlag;
  }
  
  public void setChatFlag(String chatFlag) {
    this.chatFlag = chatFlag;
  }
}
