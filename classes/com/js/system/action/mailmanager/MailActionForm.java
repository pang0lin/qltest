package com.js.system.action.mailmanager;

import org.apache.struts.action.ActionForm;

public class MailActionForm extends ActionForm {
  private Long mailId;
  
  private String fromUser;
  
  private String passWord;
  
  private int port;
  
  private String encryptionType = "SSL";
  
  private String createTime;
  
  private String bakString;
  
  private String bakString1;
  
  public String getBakString() {
    return this.bakString;
  }
  
  public void setBakString(String bakString) {
    this.bakString = bakString;
  }
  
  public String getBakString1() {
    return this.bakString1;
  }
  
  public void setBakString1(String bakString1) {
    this.bakString1 = bakString1;
  }
  
  public String getCreateTime() {
    return this.createTime;
  }
  
  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }
  
  public String getFromUser() {
    return this.fromUser;
  }
  
  public void setFromUser(String fromUser) {
    this.fromUser = fromUser;
  }
  
  public String getPassWord() {
    return this.passWord;
  }
  
  public void setPassWord(String passWord) {
    this.passWord = passWord;
  }
  
  public Long getMailId() {
    return this.mailId;
  }
  
  public void setMailId(Long mailId) {
    this.mailId = mailId;
  }
  
  public int getPort() {
    return this.port;
  }
  
  public void setPort(int port) {
    this.port = port;
  }
  
  public String getEncryptionType() {
    return this.encryptionType;
  }
  
  public void setEncryptionType(String encryptionType) {
    this.encryptionType = encryptionType;
  }
}
