package com.js.oa.info.isodoc.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class IsoPaperPO implements Serializable {
  Long isoPaperId;
  
  Long informationId;
  
  String documentNO;
  
  String informationName;
  
  String infromationVersion;
  
  Date provideTime = null;
  
  Long provideNum;
  
  String providePage;
  
  String receiveUser;
  
  String receiveOrg;
  
  String receiveGroup;
  
  Long provideUserId;
  
  String provideUserName;
  
  Long provideOrgId;
  
  String provideOrgName;
  
  String receiveScopeName;
  
  String receiveScopeId;
  
  String paperStatus;
  
  Long domainId;
  
  Long backUserId;
  
  String backUserName;
  
  Date backTime = null;
  
  String channelName;
  
  public boolean equals(Object other) {
    if (!(other instanceof IsoPaperPO))
      return false; 
    IsoPaperPO castOther = (IsoPaperPO)other;
    return (new EqualsBuilder()).append(getIsoPaperId(), castOther.getIsoPaperId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getIsoPaperId()).toHashCode();
  }
  
  public String getReceiveUser() {
    return this.receiveUser;
  }
  
  public String getReceiveOrg() {
    return this.receiveOrg;
  }
  
  public String getProvideUserName() {
    return this.provideUserName;
  }
  
  public Long getProvideUserId() {
    return this.provideUserId;
  }
  
  public Date getProvideTime() {
    return this.provideTime;
  }
  
  public String getProvideOrgName() {
    return this.provideOrgName;
  }
  
  public Long getProvideOrgId() {
    return this.provideOrgId;
  }
  
  public Long getProvideNum() {
    return this.provideNum;
  }
  
  public Long getIsoPaperId() {
    return this.isoPaperId;
  }
  
  public String getInfromationVersion() {
    return this.infromationVersion;
  }
  
  public String getInformationName() {
    return this.informationName;
  }
  
  public Long getInformationId() {
    return this.informationId;
  }
  
  public Long getDomainId() {
    return this.domainId;
  }
  
  public void setDocumentNO(String documentNO) {
    this.documentNO = documentNO;
  }
  
  public void setReceiveUser(String receiveUser) {
    this.receiveUser = receiveUser;
  }
  
  public void setReceiveOrg(String receiveOrg) {
    this.receiveOrg = receiveOrg;
  }
  
  public void setProvideUserName(String provideUserName) {
    this.provideUserName = provideUserName;
  }
  
  public void setProvideUserId(Long provideUserId) {
    this.provideUserId = provideUserId;
  }
  
  public void setProvideTime(Date provideTime) {
    this.provideTime = provideTime;
  }
  
  public void setProvideOrgName(String provideOrgName) {
    this.provideOrgName = provideOrgName;
  }
  
  public void setProvideOrgId(Long provideOrgId) {
    this.provideOrgId = provideOrgId;
  }
  
  public void setProvideNum(Long provideNum) {
    this.provideNum = provideNum;
  }
  
  public void setIsoPaperId(Long isoPaperId) {
    this.isoPaperId = isoPaperId;
  }
  
  public void setInfromationVersion(String infromationVersion) {
    this.infromationVersion = infromationVersion;
  }
  
  public void setInformationName(String informationName) {
    this.informationName = informationName;
  }
  
  public void setInformationId(Long informationId) {
    this.informationId = informationId;
  }
  
  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }
  
  public void setReceiveGroup(String receiveGroup) {
    this.receiveGroup = receiveGroup;
  }
  
  public void setReceiveScopeName(String receiveScopeName) {
    this.receiveScopeName = receiveScopeName;
  }
  
  public void setPaperStatus(String paperStatus) {
    this.paperStatus = paperStatus;
  }
  
  public void setReceiveScopeId(String receiveScopeId) {
    this.receiveScopeId = receiveScopeId;
  }
  
  public void setBackUserName(String backUserName) {
    this.backUserName = backUserName;
  }
  
  public void setBackUserId(Long backUserId) {
    this.backUserId = backUserId;
  }
  
  public void setBackTime(Date backTime) {
    this.backTime = backTime;
  }
  
  public void setChannelName(String channelName) {
    this.channelName = channelName;
  }
  
  public void setProvidePage(String providePage) {
    this.providePage = providePage;
  }
  
  public String getDocumentNO() {
    return this.documentNO;
  }
  
  public String getReceiveGroup() {
    return this.receiveGroup;
  }
  
  public String getReceiveScopeName() {
    return this.receiveScopeName;
  }
  
  public String getPaperStatus() {
    return this.paperStatus;
  }
  
  public String getReceiveScopeId() {
    return this.receiveScopeId;
  }
  
  public String getBackUserName() {
    return this.backUserName;
  }
  
  public Long getBackUserId() {
    return this.backUserId;
  }
  
  public Date getBackTime() {
    return this.backTime;
  }
  
  public String getChannelName() {
    return this.channelName;
  }
  
  public String getProvidePage() {
    return this.providePage;
  }
  
  public String getProvidePage1() {
    return this.providePage;
  }
}
