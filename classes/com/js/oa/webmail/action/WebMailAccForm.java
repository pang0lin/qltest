package com.js.oa.webmail.action;

import org.apache.struts.action.ActionForm;

public class WebMailAccForm extends ActionForm {
  private static final long serialVersionUID = 1L;
  
  private Long mailAccId;
  
  private Long userId;
  
  private String mailAccUser;
  
  private String mailAccPwd;
  
  private String smtp;
  
  private String pop;
  
  private int smtpPort;
  
  private int popPort;
  
  private String disName;
  
  private String defaultFlag;
  
  private String bakFlag;
  
  private String smtpJMFS;
  
  private String popJMFS;
  
  public WebMailAccForm() {}
  
  public WebMailAccForm(Long mailAccId, String mailAccUser, String mailAccPwd, Long userId) {
    this.mailAccId = mailAccId;
    this.mailAccUser = mailAccUser;
    this.mailAccPwd = mailAccPwd;
    this.userId = userId;
  }
  
  public Long getMailAccId() {
    return this.mailAccId;
  }
  
  public void setMailAccId(Long mailAccId) {
    this.mailAccId = mailAccId;
  }
  
  public String getMailAccPwd() {
    return this.mailAccPwd;
  }
  
  public void setMailAccPwd(String mailAccPwd) {
    this.mailAccPwd = mailAccPwd;
  }
  
  public String getMailAccUser() {
    return this.mailAccUser;
  }
  
  public void setMailAccUser(String mailAccUser) {
    this.mailAccUser = mailAccUser;
  }
  
  public Long getUserId() {
    return this.userId;
  }
  
  public void setUserId(Long userId) {
    this.userId = userId;
  }
  
  public String getPop() {
    return this.pop;
  }
  
  public void setPop(String pop) {
    this.pop = pop;
  }
  
  public String getSmtp() {
    return this.smtp;
  }
  
  public void setSmtp(String smtp) {
    this.smtp = smtp;
  }
  
  public String getDefaultFlag() {
    return this.defaultFlag;
  }
  
  public void setDefaultFlag(String defaultFlag) {
    this.defaultFlag = defaultFlag;
  }
  
  public String getDisName() {
    return this.disName;
  }
  
  public void setDisName(String disName) {
    String name = disName;
    name = String.valueOf(name.substring(0, name.lastIndexOf("@"))) + " <" + name + ">";
    this.disName = name;
  }
  
  public String getBakFlag() {
    return this.bakFlag;
  }
  
  public void setBakFlag(String bakFlag) {
    this.bakFlag = bakFlag;
  }
  
  public int getSmtpPort() {
    return this.smtpPort;
  }
  
  public void setSmtpPort(int smtpPort) {
    this.smtpPort = smtpPort;
  }
  
  public int getPopPort() {
    return this.popPort;
  }
  
  public void setPopPort(int popPort) {
    this.popPort = popPort;
  }
  
  public String getSmtpJMFS() {
    return this.smtpJMFS;
  }
  
  public void setSmtpJMFS(String smtpJMFS) {
    this.smtpJMFS = smtpJMFS;
  }
  
  public String getPopJMFS() {
    return this.popJMFS;
  }
  
  public void setPopJMFS(String popJMFS) {
    this.popJMFS = popJMFS;
  }
}
