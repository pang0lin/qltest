package com.js.oa.pressdeal.po;

import java.io.Serializable;
import java.sql.Timestamp;

public abstract class AbstractOaPersonoaPress implements Serializable {
  private int hashValue = 0;
  
  private Long pressId;
  
  private String title;
  
  private Timestamp dispatchTime = null;
  
  private String content;
  
  private String sendUsername;
  
  private String sendUserDep;
  
  private String receiveUsernameStr;
  
  private String subcatorgryName;
  
  private String catorgry;
  
  private Long domainId;
  
  private String standby;
  
  private Float standbyFloat;
  
  private Short isNew;
  
  private Byte pressStatus;
  
  private String workflowurl;
  
  public AbstractOaPersonoaPress() {}
  
  public AbstractOaPersonoaPress(Long pressId) {
    setPressId(pressId);
  }
  
  public Long getPressId() {
    return this.pressId;
  }
  
  public void setPressId(Long pressId) {
    this.hashValue = 0;
    this.pressId = pressId;
  }
  
  public String getTitle() {
    return this.title;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  public Timestamp getDispatchTime() {
    return this.dispatchTime;
  }
  
  public void setDispatchTime(Timestamp dispatchTime) {
    this.dispatchTime = dispatchTime;
  }
  
  public String getContent() {
    return this.content;
  }
  
  public void setContent(String content) {
    this.content = content;
  }
  
  public String getSendUsername() {
    return this.sendUsername;
  }
  
  public void setSendUsername(String sendUsername) {
    this.sendUsername = sendUsername;
  }
  
  public String getSendUserDep() {
    return this.sendUserDep;
  }
  
  public void setSendUserDep(String sendUserDep) {
    this.sendUserDep = sendUserDep;
  }
  
  public String getReceiveUsernameStr() {
    return this.receiveUsernameStr;
  }
  
  public void setReceiveUsernameStr(String receiveUsernameStr) {
    this.receiveUsernameStr = receiveUsernameStr;
  }
  
  public String getSubcatorgryName() {
    return this.subcatorgryName;
  }
  
  public void setSubcatorgryName(String subcatorgryName) {
    this.subcatorgryName = subcatorgryName;
  }
  
  public String getCatorgry() {
    return this.catorgry;
  }
  
  public void setCatorgry(String catorgry) {
    this.catorgry = catorgry;
  }
  
  public Long getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }
  
  public String getStandby() {
    return this.standby;
  }
  
  public void setStandby(String standby) {
    this.standby = standby;
  }
  
  public Float getStandbyFloat() {
    return this.standbyFloat;
  }
  
  public void setStandbyFloat(Float standbyFloat) {
    this.standbyFloat = standbyFloat;
  }
  
  public Short getIsNew() {
    return this.isNew;
  }
  
  public Byte getPressStatus() {
    return this.pressStatus;
  }
  
  public void setIsNew(Short isNew) {
    this.isNew = isNew;
  }
  
  public void setPressStatus(Byte pressStatus) {
    this.pressStatus = pressStatus;
  }
  
  public String getWorkflowurl() {
    return this.workflowurl;
  }
  
  public void setWorkflowurl(String workflowurl) {
    this.workflowurl = workflowurl;
  }
  
  public boolean equals(Object rhs) {
    if (rhs == null)
      return false; 
    if (!(rhs instanceof OaPersonoaPressPO))
      return false; 
    OaPersonoaPressPO that = (OaPersonoaPressPO)rhs;
    if (getPressId() != null && that.getPressId() != null)
      if (!getPressId().equals(that.getPressId()))
        return false;  
    return true;
  }
  
  public int hashCode() {
    if (this.hashValue == 0) {
      int result = 17;
      int pressIdValue = (getPressId() == null) ? 0 : getPressId().hashCode();
      result = result * 37 + pressIdValue;
      this.hashValue = result;
    } 
    return this.hashValue;
  }
}
