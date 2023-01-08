package com.js.doc.doc.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class GovSendFileNoBrowserPO implements Serializable {
  private long id;
  
  private GovDocumentSendFilePO govSendFile;
  
  private long empId;
  
  private String browserName;
  
  private String browserOrgName;
  
  private String mobilePhone;
  
  private String domainId;
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public long getEmpId() {
    return this.empId;
  }
  
  public void setEmpId(long empId) {
    this.empId = empId;
  }
  
  public String getBrowserName() {
    return this.browserName;
  }
  
  public void setBrowserName(String browserName) {
    this.browserName = browserName;
  }
  
  public String getBrowserOrgName() {
    return this.browserOrgName;
  }
  
  public void setBrowserOrgName(String browserOrgName) {
    this.browserOrgName = browserOrgName;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof GovSendFileNoBrowserPO))
      return false; 
    GovSendFileNoBrowserPO castOther = (GovSendFileNoBrowserPO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
  }
  
  public String getMobilePhone() {
    return this.mobilePhone;
  }
  
  public void setMobilePhone(String mobilePhone) {
    this.mobilePhone = mobilePhone;
  }
  
  public GovDocumentSendFilePO getGovSendFile() {
    return this.govSendFile;
  }
  
  public void setGovSendFile(GovDocumentSendFilePO govSendFile) {
    this.govSendFile = govSendFile;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
}
