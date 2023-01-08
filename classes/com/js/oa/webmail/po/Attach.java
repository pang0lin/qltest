package com.js.oa.webmail.po;

import java.io.Serializable;

public class Attach implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private Long attachId;
  
  private Long mailInfoId;
  
  private String attachName;
  
  private String attachDisName;
  
  private String attachSizeStr;
  
  public String getAttachSizeStr() {
    return this.attachSizeStr;
  }
  
  public void setAttachSizeStr(String attachSizeStr) {
    this.attachSizeStr = attachSizeStr;
  }
  
  public String getAttachDisName() {
    return this.attachDisName;
  }
  
  public void setAttachDisName(String attachDisName) {
    this.attachDisName = attachDisName;
  }
  
  public Long getAttachId() {
    return this.attachId;
  }
  
  public void setAttachId(Long attachId) {
    this.attachId = attachId;
  }
  
  public String getAttachName() {
    return this.attachName;
  }
  
  public void setAttachName(String attachName) {
    this.attachName = attachName;
  }
  
  public Long getMailInfoId() {
    return this.mailInfoId;
  }
  
  public void setMailInfoId(Long mailInfoId) {
    this.mailInfoId = mailInfoId;
  }
}
