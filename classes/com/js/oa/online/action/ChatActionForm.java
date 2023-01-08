package com.js.oa.online.action;

import java.io.Serializable;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ChatActionForm extends ActionForm implements Serializable {
  private Long chatId;
  
  private String sendUserId;
  
  private String toUserId;
  
  private String content;
  
  private String chatHasaccessory;
  
  private String chatTime;
  
  private String isRead;
  
  private String msgSelfFlag;
  
  private Set chatAccessory = null;
  
  private String accessorySize;
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public Long getChatId() {
    return this.chatId;
  }
  
  public void setChatId(Long chatId) {
    this.chatId = chatId;
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
  
  public String getChatTime() {
    return this.chatTime;
  }
  
  public void setChatTime(String chatTime) {
    this.chatTime = chatTime;
  }
  
  public String getIsRead() {
    return this.isRead;
  }
  
  public void setIsRead(String isRead) {
    this.isRead = isRead;
  }
  
  public Set getChatAccessory() {
    return this.chatAccessory;
  }
  
  public void setChatAccessory(Set chatAccessory) {
    this.chatAccessory = chatAccessory;
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
}
