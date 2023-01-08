package com.js.oa.info.infomanager.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class AssociateInfoPO implements Serializable {
  private Long associateInfoId;
  
  private Long associateInfo;
  
  private Long masterInfo;
  
  private Long domainId;
  
  public Long getAssociateInfoId() {
    return this.associateInfoId;
  }
  
  public void setAssociateInfoId(Long associateInfoId) {
    this.associateInfoId = associateInfoId;
  }
  
  public Long getMasterInfo() {
    return this.masterInfo;
  }
  
  public void setMasterInfo(Long masterInfo) {
    this.masterInfo = masterInfo;
  }
  
  public Long getAssociateInfo() {
    return this.associateInfo;
  }
  
  public void setAssociateInfo(Long associateInfo) {
    this.associateInfo = associateInfo;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof InforHistoryAccessoryPO))
      return false; 
    AssociateInfoPO castOther = (AssociateInfoPO)other;
    return (new EqualsBuilder()).append(getAssociateInfoId(), castOther.getAssociateInfoId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getAssociateInfoId()).toHashCode();
  }
  
  public Long getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }
}
