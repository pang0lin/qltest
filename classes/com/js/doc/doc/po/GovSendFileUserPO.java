package com.js.doc.doc.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class GovSendFileUserPO implements Serializable {
  private Long id;
  
  private Long empId;
  
  private GovDocumentSendFilePO sendFile;
  
  private String domainId;
  
  private String isReaded;
  
  private String orgId;
  
  private String outSeeType;
  
  private Date readDate = null;
  
  private String isDelete;
  
  private String userName;
  
  private String orgName;
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public Long getEmpId() {
    return this.empId;
  }
  
  public void setEmpId(Long empId) {
    this.empId = empId;
  }
  
  public GovDocumentSendFilePO getSendFile() {
    return this.sendFile;
  }
  
  public void setSendFile(GovDocumentSendFilePO sendFile) {
    this.sendFile = sendFile;
  }
  
  public String toString() {
    return (new ToStringBuilder(this)).append("id", getId()).toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof GovSendFileUserPO))
      return false; 
    GovSendFileUserPO castOther = (GovSendFileUserPO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
  
  public String getIsReaded() {
    return this.isReaded;
  }
  
  public String getOrgId() {
    return this.orgId;
  }
  
  public String getOutSeeType() {
    return this.outSeeType;
  }
  
  public String getIsDelete() {
    return this.isDelete;
  }
  
  public String getUserName() {
    return this.userName;
  }
  
  public String getOrgName() {
    return this.orgName;
  }
  
  public Date getReadDate() {
    return this.readDate;
  }
  
  public void setIsReaded(String isReaded) {
    this.isReaded = isReaded;
  }
  
  public void setOrgId(String orgId) {
    this.orgId = orgId;
  }
  
  public void setOutSeeType(String outSeeType) {
    this.outSeeType = outSeeType;
  }
  
  public void setIsDelete(String isDelete) {
    this.isDelete = isDelete;
  }
  
  public void setUserName(String userName) {
    this.userName = userName;
  }
  
  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }
  
  public void setReadDate(Date readDate) {
    this.readDate = readDate;
  }
}
