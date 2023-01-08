package com.js.oa.chat.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class ChatPO implements Serializable {
  private Long chatId;
  
  private String chatContent;
  
  private String chatTo;
  
  private String chatHasattach;
  
  private String chatAttachsize;
  
  private String senderId;
  
  private String senderName;
  
  private Date chatTime = null;
  
  private Set chatUser = null;
  
  private Set chatAttach = null;
  
  public Long getChatId() {
    return this.chatId;
  }
  
  public void setChatId(Long chatId) {
    this.chatId = chatId;
  }
  
  public String getChatContent() {
    return this.chatContent;
  }
  
  public void setChatContent(String chatContent) {
    this.chatContent = chatContent;
  }
  
  public String getChatTo() {
    return this.chatTo;
  }
  
  public void setChatTo(String chatTo) {
    this.chatTo = chatTo;
  }
  
  public String getChatHasattach() {
    return this.chatHasattach;
  }
  
  public void setChatHasattach(String chatHasattach) {
    this.chatHasattach = chatHasattach;
  }
  
  public String getChatAttachsize() {
    return this.chatAttachsize;
  }
  
  public void setChatAttachsize(String chatAttachsize) {
    this.chatAttachsize = chatAttachsize;
  }
  
  public String getSenderId() {
    return this.senderId;
  }
  
  public void setSenderId(String senderId) {
    this.senderId = senderId;
  }
  
  public String getSenderName() {
    return this.senderName;
  }
  
  public void setSenderName(String senderName) {
    this.senderName = senderName;
  }
  
  public Date getChatTime() {
    return this.chatTime;
  }
  
  public void setChatTime(Date chatTime) {
    this.chatTime = chatTime;
  }
  
  public Set getChatUser() {
    return this.chatUser;
  }
  
  public void setChatUser(Set chatUser) {
    this.chatUser = chatUser;
  }
  
  public Set getChatAttach() {
    return this.chatAttach;
  }
  
  public void setChatAttach(Set chatAttach) {
    this.chatAttach = chatAttach;
  }
}
