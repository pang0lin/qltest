package com.js.doc.doc.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class ReceiveAssociatePO implements Serializable {
  Long aossiateId;
  
  Long receiveFileId;
  
  Long sendFileId;
  
  Long transUserId;
  
  Long transOrgId;
  
  Long domainId;
  
  public boolean equals(Object other) {
    if (!(other instanceof ReceiveAssociatePO))
      return false; 
    ReceiveAssociatePO castOther = (ReceiveAssociatePO)other;
    return (new EqualsBuilder()).append(getAossiateId(), castOther.getAossiateId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getAossiateId()).toHashCode();
  }
  
  public Long getTransUserId() {
    return this.transUserId;
  }
  
  public Long getTransOrgId() {
    return this.transOrgId;
  }
  
  public Long getSendFileId() {
    return this.sendFileId;
  }
  
  public Long getReceiveFileId() {
    return this.receiveFileId;
  }
  
  public Long getDomainId() {
    return this.domainId;
  }
  
  public void setAossiateId(Long aossiateId) {
    this.aossiateId = aossiateId;
  }
  
  public void setTransUserId(Long transUserId) {
    this.transUserId = transUserId;
  }
  
  public void setTransOrgId(Long transOrgId) {
    this.transOrgId = transOrgId;
  }
  
  public void setSendFileId(Long sendFileId) {
    this.sendFileId = sendFileId;
  }
  
  public void setReceiveFileId(Long receiveFileId) {
    this.receiveFileId = receiveFileId;
  }
  
  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }
  
  public Long getAossiateId() {
    return this.aossiateId;
  }
}
