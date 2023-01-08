package com.js.oa.info.isodoc.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class IsoBorrowUserPO implements Serializable {
  Long isoBorrowUserId;
  
  Long userId;
  
  String userName;
  
  Long orgId;
  
  String orgName;
  
  Long informationId;
  
  Date borrowBeginTime = null;
  
  Date borrowEndTime = null;
  
  String borrowStatus;
  
  String borrowReason;
  
  Long domainId;
  
  String informationName;
  
  String inforChannelName;
  
  String documentNO;
  
  Long inforChannelId;
  
  public boolean equals(Object other) {
    if (!(other instanceof IsoBorrowUserPO))
      return false; 
    IsoBorrowUserPO castOther = (IsoBorrowUserPO)other;
    return (new EqualsBuilder()).append(getIsoBorrowUserId(), castOther.getIsoBorrowUserId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getIsoBorrowUserId()).toHashCode();
  }
  
  public String getUserName() {
    return this.userName;
  }
  
  public Long getUserId() {
    return this.userId;
  }
  
  public String getOrgName() {
    return this.orgName;
  }
  
  public Long getOrgId() {
    return this.orgId;
  }
  
  public Long getIsoBorrowUserId() {
    return this.isoBorrowUserId;
  }
  
  public Long getDomainId() {
    return this.domainId;
  }
  
  public String getBorrowStatus() {
    return this.borrowStatus;
  }
  
  public String getBorrowReason() {
    return this.borrowReason;
  }
  
  public Date getBorrowEndTime() {
    return this.borrowEndTime;
  }
  
  public void setBorrowBeginTime(Date borrowBeginTime) {
    this.borrowBeginTime = borrowBeginTime;
  }
  
  public void setUserName(String userName) {
    this.userName = userName;
  }
  
  public void setUserId(Long userId) {
    this.userId = userId;
  }
  
  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }
  
  public void setOrgId(Long orgId) {
    this.orgId = orgId;
  }
  
  public void setIsoBorrowUserId(Long isoBorrowUserId) {
    this.isoBorrowUserId = isoBorrowUserId;
  }
  
  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }
  
  public void setBorrowStatus(String borrowStatus) {
    this.borrowStatus = borrowStatus;
  }
  
  public void setBorrowReason(String borrowReason) {
    this.borrowReason = borrowReason;
  }
  
  public void setBorrowEndTime(Date borrowEndTime) {
    this.borrowEndTime = borrowEndTime;
  }
  
  public void setInformationName(String informationName) {
    this.informationName = informationName;
  }
  
  public void setInformationId(Long informationId) {
    this.informationId = informationId;
  }
  
  public void setInforChannelName(String inforChannelName) {
    this.inforChannelName = inforChannelName;
  }
  
  public void setInforChannelId(Long inforChannelId) {
    this.inforChannelId = inforChannelId;
  }
  
  public void setDocumentNO(String documentNO) {
    this.documentNO = documentNO;
  }
  
  public Date getBorrowBeginTime() {
    return this.borrowBeginTime;
  }
  
  public String getInformationName() {
    return this.informationName;
  }
  
  public Long getInformationId() {
    return this.informationId;
  }
  
  public String getInforChannelName() {
    return this.inforChannelName;
  }
  
  public Long getInforChannelId() {
    return this.inforChannelId;
  }
  
  public String getDocumentNO() {
    return this.documentNO;
  }
}
