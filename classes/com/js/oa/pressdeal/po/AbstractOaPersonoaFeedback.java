package com.js.oa.pressdeal.po;

import java.io.Serializable;
import java.sql.Timestamp;

public abstract class AbstractOaPersonoaFeedback implements Serializable {
  private int hashValue = 0;
  
  private Long feedbackId;
  
  private Timestamp feedbackTime = null;
  
  private String userName;
  
  private Long pressId;
  
  private String content;
  
  private Long domainId;
  
  private String standbyStr;
  
  private Float standbyFloat;
  
  private Byte feedbackStatus;
  
  public AbstractOaPersonoaFeedback() {}
  
  public AbstractOaPersonoaFeedback(Long feedbackId) {
    setFeedbackId(feedbackId);
  }
  
  public Long getFeedbackId() {
    return this.feedbackId;
  }
  
  public void setFeedbackId(Long feedbackId) {
    this.hashValue = 0;
    this.feedbackId = feedbackId;
  }
  
  public Timestamp getFeedbackTime() {
    return this.feedbackTime;
  }
  
  public void setFeedbackTime(Timestamp feedbackTime) {
    this.feedbackTime = feedbackTime;
  }
  
  public String getUserName() {
    return this.userName;
  }
  
  public void setUserName(String userName) {
    this.userName = userName;
  }
  
  public Long getPressId() {
    return this.pressId;
  }
  
  public void setPressId(Long pressId) {
    this.pressId = pressId;
  }
  
  public String getContent() {
    return this.content;
  }
  
  public void setContent(String content) {
    this.content = content;
  }
  
  public Long getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }
  
  public String getStandbyStr() {
    return this.standbyStr;
  }
  
  public void setStandbyStr(String standbyStr) {
    this.standbyStr = standbyStr;
  }
  
  public Float getStandbyFloat() {
    return this.standbyFloat;
  }
  
  public Byte getFeedbackStatus() {
    return this.feedbackStatus;
  }
  
  public void setStandbyFloat(Float standbyFloat) {
    this.standbyFloat = standbyFloat;
  }
  
  public void setFeedbackStatus(Byte feedbackStatus) {
    this.feedbackStatus = feedbackStatus;
  }
  
  public boolean equals(Object rhs) {
    if (rhs == null)
      return false; 
    if (!(rhs instanceof OaPersonoaFeedbackPO))
      return false; 
    OaPersonoaFeedbackPO that = (OaPersonoaFeedbackPO)rhs;
    if (getFeedbackId() != null && that.getFeedbackId() != null)
      if (!getFeedbackId().equals(that.getFeedbackId()))
        return false;  
    return true;
  }
  
  public int hashCode() {
    if (this.hashValue == 0) {
      int result = 17;
      int feedbackIdValue = (getFeedbackId() == null) ? 0 : getFeedbackId().hashCode();
      result = result * 37 + feedbackIdValue;
      this.hashValue = result;
    } 
    return this.hashValue;
  }
}
