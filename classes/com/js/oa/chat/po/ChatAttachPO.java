package com.js.oa.chat.po;

import java.io.Serializable;

public class ChatAttachPO implements Serializable {
  private Long attachId;
  
  private String fileName;
  
  private String fileSaveName;
  
  private ChatPO chat;
  
  public Long getAttachId() {
    return this.attachId;
  }
  
  public void setAttachId(Long attachId) {
    this.attachId = attachId;
  }
  
  public String getFileName() {
    return this.fileName;
  }
  
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
  
  public String getFileSaveName() {
    return this.fileSaveName;
  }
  
  public void setFileSaveName(String fileSaveName) {
    this.fileSaveName = fileSaveName;
  }
  
  public ChatPO getChat() {
    return this.chat;
  }
  
  public void setChat(ChatPO chat) {
    this.chat = chat;
  }
}
