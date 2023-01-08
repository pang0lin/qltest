package com.js.doc.doc.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class GovTypeSetPO implements Serializable {
  private long id;
  
  private String typeSetName;
  
  private String typeSetWordNumber;
  
  private long createdEmp;
  
  private String sendToGroup;
  
  private String sendToOrg;
  
  private String sendToUser;
  
  private String sendToName;
  
  private long createdOrg;
  
  private String redHeadId;
  
  private String domainId;
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public String getTypeSetName() {
    return this.typeSetName;
  }
  
  public void setTypeSetName(String typeSetName) {
    this.typeSetName = typeSetName;
  }
  
  public String getTypeSetWordNumber() {
    return this.typeSetWordNumber;
  }
  
  public void setTypeSetWordNumber(String typeSetWordNumber) {
    this.typeSetWordNumber = typeSetWordNumber;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof GovTypeSetPO))
      return false; 
    GovTypeSetPO castOther = (GovTypeSetPO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
  }
  
  public long getCreatedEmp() {
    return this.createdEmp;
  }
  
  public void setCreatedEmp(long createdEmp) {
    this.createdEmp = createdEmp;
  }
  
  public String getSendToGroup() {
    return this.sendToGroup;
  }
  
  public void setSendToGroup(String sendToGroup) {
    this.sendToGroup = sendToGroup;
  }
  
  public String getSendToOrg() {
    return this.sendToOrg;
  }
  
  public void setSendToOrg(String sendToOrg) {
    this.sendToOrg = sendToOrg;
  }
  
  public String getSendToUser() {
    return this.sendToUser;
  }
  
  public void setSendToUser(String sendToUser) {
    this.sendToUser = sendToUser;
  }
  
  public String getSendToName() {
    return this.sendToName;
  }
  
  public void setSendToName(String sendToName) {
    this.sendToName = sendToName;
  }
  
  public long getCreatedOrg() {
    return this.createdOrg;
  }
  
  public void setCreatedOrg(long createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public String getRedHeadId() {
    return this.redHeadId;
  }
  
  public void setRedHeadId(String redHeadId) {
    this.redHeadId = redHeadId;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
}
