package com.js.oa.webmail.action;

import com.js.oa.webmail.util.IOManager;
import org.apache.struts.action.ActionForm;

public class WebMailForm extends ActionForm {
  private static final long serialVersionUID = 1L;
  
  private Long mailInfoId;
  
  private String mailId;
  
  private Long userId;
  
  private String mailBox;
  
  private String affixId;
  
  private String frm;
  
  private String too;
  
  private String cc;
  
  private String bc;
  
  private String subject;
  
  private String content;
  
  private String grade;
  
  private String isReply;
  
  private String isHtml;
  
  private int mailSize;
  
  private String sendDate;
  
  private String frmPerson;
  
  private String mailState;
  
  public String getMailState() {
    return this.mailState;
  }
  
  public void setMailState(String mailState) {
    this.mailState = mailState;
  }
  
  public String getFrmPerson() {
    return this.frm;
  }
  
  public void setFrmPerson(String frmPerson) {
    this.frmPerson = frmPerson;
  }
  
  public WebMailForm() {}
  
  public WebMailForm(String mailId, String subject, String frm) {
    this.mailId = mailId;
    this.subject = subject;
    this.frm = frm;
  }
  
  public String getFrm() {
    return this.frm;
  }
  
  public void setFrm(String frm) {
    this.frm = frm;
  }
  
  public String getMailId() {
    return this.mailId;
  }
  
  public void setMailId(String mailId) {
    this.mailId = mailId;
  }
  
  public String getSubject() {
    return this.subject;
  }
  
  public void setSubject(String subject) {
    this.subject = subject;
  }
  
  public String getContent() {
    return this.content;
  }
  
  public void setContent(String content) {
    this.content = content;
  }
  
  public String getAffixId() {
    return this.affixId;
  }
  
  public void setAffixId(String affixId) {
    this.affixId = affixId;
  }
  
  public String getBc() {
    return this.bc;
  }
  
  public void setBc(String bc) {
    this.bc = bc;
  }
  
  public String getCc() {
    return this.cc;
  }
  
  public void setCc(String cc) {
    this.cc = cc;
  }
  
  public String getGrade() {
    return this.grade;
  }
  
  public void setGrade(String grade) {
    this.grade = grade;
  }
  
  public String getIsHtml() {
    return this.isHtml;
  }
  
  public void setIsHtml(String isHtml) {
    this.isHtml = isHtml;
  }
  
  public String getIsReply() {
    return this.isReply;
  }
  
  public void setIsReply(String isReply) {
    this.isReply = isReply;
  }
  
  public String getMailBox() {
    return this.mailBox;
  }
  
  public void setMailBox(String mailBox) {
    this.mailBox = mailBox;
  }
  
  public String getSendDate() {
    return this.sendDate;
  }
  
  public void setSendDate(String sendDate) {
    this.sendDate = sendDate;
  }
  
  public String getToo() {
    return this.too;
  }
  
  public void setToo(String too) {
    this.too = too;
  }
  
  public Long getUserId() {
    return this.userId;
  }
  
  public void setUserId(Long userId) {
    this.userId = userId;
  }
  
  public int getMailSize() {
    return this.mailSize;
  }
  
  public void setMailSize(int mailSize) {
    this.mailSize = mailSize;
  }
  
  public String getContentByIOStr(String path) {
    return IOManager.getFileContent(path);
  }
  
  public Long getMailInfoId() {
    return this.mailInfoId;
  }
  
  public void setMailInfoId(Long mailInfoId) {
    this.mailInfoId = mailInfoId;
  }
}
