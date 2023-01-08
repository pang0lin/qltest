package com.js.util.mail;

public final class Mail {
  private String sendTo;
  
  private String copyTo;
  
  private String secretTo;
  
  private String subjectTitle;
  
  private String[] fileAffix;
  
  private String popo3;
  
  private String boby;
  
  private boolean html;
  
  private String sendType;
  
  public String getSendType() {
    return this.sendType;
  }
  
  public void setSendType(String sendType) {
    this.sendType = sendType;
  }
  
  public boolean isHtml() {
    return this.html;
  }
  
  public void setHtml(boolean html) {
    this.html = html;
  }
  
  public String getBoby() {
    return this.boby;
  }
  
  public void setBoby(String boby) {
    this.boby = boby;
  }
  
  public String[] getFileAffix() {
    return this.fileAffix;
  }
  
  public void setFileAffix(String[] fileAffix) {
    this.fileAffix = fileAffix;
  }
  
  public String getPopo3() {
    return this.popo3;
  }
  
  public void setPopo3(String popo3) {
    this.popo3 = popo3;
  }
  
  public String getSendTo() {
    return this.sendTo;
  }
  
  public void setSendTo(String sendTo) {
    this.sendTo = sendTo;
  }
  
  public String getSubjectTitle() {
    return this.subjectTitle;
  }
  
  public void setSubjectTitle(String subjectTitle) {
    if (subjectTitle.equals("")) {
      this.subjectTitle = "无主题";
    } else {
      this.subjectTitle = subjectTitle;
    } 
  }
  
  public String getCopyTo() {
    return this.copyTo;
  }
  
  public void setCopyTo(String copyTo) {
    this.copyTo = copyTo;
  }
  
  public String getSecretTo() {
    return this.secretTo;
  }
  
  public void setSecretTo(String secretTo) {
    this.secretTo = secretTo;
  }
}
