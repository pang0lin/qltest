package com.js.oa.webmail.po;

import java.io.Serializable;

public class WebMailTemp implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private Long tempId;
  
  private String mailId;
  
  public WebMailTemp() {}
  
  public WebMailTemp(Long tempId, String mailId) {
    this.tempId = tempId;
    this.mailId = mailId;
  }
  
  public String getMailId() {
    return this.mailId;
  }
  
  public void setMailId(String mailId) {
    this.mailId = mailId;
  }
  
  public Long getTempId() {
    return this.tempId;
  }
  
  public void setTempId(Long tempId) {
    this.tempId = tempId;
  }
}
