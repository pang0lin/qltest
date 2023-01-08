package com.js.oa.webmail.po;

import com.js.oa.webmail.util.IOManager;
import java.io.Serializable;

public class WebMail implements Serializable {
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
  
  private String hasAttach;
  
  private String mailState;
  
  private int mailSize;
  
  private int priority = 0;
  
  private String replyTo = "";
  
  private String sendDate;
  
  public WebMail() {}
  
  public WebMail(String mailId, String subject, String frm) {
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
  
  public String getMailState() {
    return this.mailState;
  }
  
  public void setMailState(String mailState) {
    this.mailState = mailState;
  }
  
  public Long getMailInfoId() {
    return this.mailInfoId;
  }
  
  public void setMailInfoId(Long mailInfoId) {
    this.mailInfoId = mailInfoId;
  }
  
  public String getHasAttach() {
    return this.hasAttach;
  }
  
  public void setHasAttach(String hasAttach) {
    this.hasAttach = hasAttach;
  }
  
  public String getFromName() {
    int ipos = -1;
    if ((ipos = this.frm.indexOf('<')) != -1 && ipos > 0)
      return this.frm.substring(0, ipos); 
    return this.frm;
  }
  
  public String getFromAdd() {
    int ib = -1, ie = -1;
    if ((ib = this.frm.indexOf('<')) != -1 && (ie = this.frm.indexOf('>')) != -1)
      return this.frm.substring(ib + 1, ie); 
    return this.frm;
  }
  
  public int getPriority() {
    return this.priority;
  }
  
  public void setPriority(int priority) {
    this.priority = priority;
  }
  
  public String getReplyTo() {
    return this.replyTo;
  }
  
  public void setReplyTo(String replyTo) {
    this.replyTo = replyTo;
  }
  
  public String getStrByTran(String str) {
    String address = "";
    if (str != null && !str.equals("") && str.indexOf("<") >= 0) {
      address = str.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    } else {
      address = str;
    } 
    return address;
  }
}
