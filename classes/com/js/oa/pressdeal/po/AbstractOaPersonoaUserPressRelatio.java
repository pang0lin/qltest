package com.js.oa.pressdeal.po;

import java.io.Serializable;

public abstract class AbstractOaPersonoaUserPressRelatio implements Serializable {
  private int hashValue = 0;
  
  private Long entityId;
  
  private Long pressId;
  
  private Byte pressStatus;
  
  private String userName;
  
  private Long domainId;
  
  private Float standbyFloat;
  
  private String standbyStr;
  
  private Long userId;
  
  private Long orgId;
  
  private String orgName;
  
  public AbstractOaPersonoaUserPressRelatio() {}
  
  public AbstractOaPersonoaUserPressRelatio(Long entityId) {
    setEntityId(entityId);
  }
  
  public Long getEntityId() {
    return this.entityId;
  }
  
  public void setEntityId(Long entityId) {
    this.hashValue = 0;
    this.entityId = entityId;
  }
  
  public Long getPressId() {
    return this.pressId;
  }
  
  public void setPressId(Long pressId) {
    this.pressId = pressId;
  }
  
  public Byte getPressStatus() {
    return this.pressStatus;
  }
  
  public void setPressStatus(Byte pressStatus) {
    this.pressStatus = pressStatus;
  }
  
  public String getUserName() {
    return this.userName;
  }
  
  public void setUserName(String userName) {
    this.userName = userName;
  }
  
  public Long getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }
  
  public Float getStandbyFloat() {
    return this.standbyFloat;
  }
  
  public void setStandbyFloat(Float standbyFloat) {
    this.standbyFloat = standbyFloat;
  }
  
  public String getStandbyStr() {
    return this.standbyStr;
  }
  
  public void setStandbyStr(String standbyStr) {
    this.standbyStr = standbyStr;
  }
  
  public Long getUserId() {
    return this.userId;
  }
  
  public void setUserId(Long userId) {
    this.userId = userId;
  }
  
  public Long getOrgId() {
    return this.orgId;
  }
  
  public void setOrgId(Long orgId) {
    this.orgId = orgId;
  }
  
  public String getOrgName() {
    return this.orgName;
  }
  
  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }
  
  public boolean equals(Object rhs) {
    if (rhs == null)
      return false; 
    if (!(rhs instanceof OaPersonoaUserPressRelatioPO))
      return false; 
    OaPersonoaUserPressRelatioPO that = (OaPersonoaUserPressRelatioPO)rhs;
    if (getEntityId() != null && that.getEntityId() != null)
      if (!getEntityId().equals(that.getEntityId()))
        return false;  
    return true;
  }
  
  public int hashCode() {
    if (this.hashValue == 0) {
      int result = 17;
      int entityIdValue = (getEntityId() == null) ? 0 : getEntityId().hashCode();
      result = result * 37 + entityIdValue;
      this.hashValue = result;
    } 
    return this.hashValue;
  }
}
