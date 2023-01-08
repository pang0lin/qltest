package com.js.oa.chat.po;

import java.io.Serializable;

public class ChatUserPO implements Serializable {
  private Long id;
  
  private String empId;
  
  private String chatStatus;
  
  private String isRead;
  
  private ChatPO chat;
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getEmpId() {
    return this.empId;
  }
  
  public void setEmpId(String empId) {
    this.empId = empId;
  }
  
  public String getChatStatus() {
    return this.chatStatus;
  }
  
  public void setChatStatus(String chatStatus) {
    this.chatStatus = chatStatus;
  }
  
  public String getIsRead() {
    return this.isRead;
  }
  
  public void setIsRead(String isRead) {
    this.isRead = isRead;
  }
  
  public ChatPO getChat() {
    return this.chat;
  }
  
  public void setChat(ChatPO chat) {
    this.chat = chat;
  }
}
