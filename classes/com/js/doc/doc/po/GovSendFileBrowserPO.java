package com.js.doc.doc.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class GovSendFileBrowserPO implements Serializable {
  private long id;
  
  private long empId;
  
  private String browserName;
  
  private String browserOrgName;
  
  private Date browseTime = null;
  
  private GovDocumentSendFilePO govSendFile;
  
  private String domainId;
  
  public void setGovSendFile(GovDocumentSendFilePO govSendFile) {
    this.govSendFile = govSendFile;
    this.govSendFile = govSendFile;
  }
  
  public GovDocumentSendFilePO getGovSendFile() {
    return this.govSendFile;
  }
  
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
  
  public Date getBrowseTime() {
    return this.browseTime;
  }
  
  public void setBrowseTime(Date browseTime) {
    this.browseTime = browseTime;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof GovSendFileBrowserPO))
      return false; 
    GovSendFileBrowserPO castOther = (GovSendFileBrowserPO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
}
