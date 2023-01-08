package com.js.oa.info.infomanager.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class InformationBrowserPO implements Serializable {
  private Long browserId;
  
  private Long empId;
  
  private InformationPO information;
  
  private String browserName;
  
  private String browserOrgName;
  
  private Date browseTime = null;
  
  private String browserOrgIdStr;
  
  private Long domainId;
  
  public Long getBrowserId() {
    return this.browserId;
  }
  
  public void setBrowserId(Long browserId) {
    this.browserId = browserId;
  }
  
  public Long getEmpId() {
    return this.empId;
  }
  
  public void setEmpId(Long empId) {
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
  
  public InformationPO getInformation() {
    return this.information;
  }
  
  public void setInformation(InformationPO information) {
    this.information = information;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof InforPersonalStatPO))
      return false; 
    InformationBrowserPO castOther = (InformationBrowserPO)other;
    return (new EqualsBuilder()).append(getBrowserId(), castOther.getBrowserId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getBrowserId()).toHashCode();
  }
  
  public Date getBrowseTime() {
    return this.browseTime;
  }
  
  public void setBrowseTime(Date browseTime) {
    this.browseTime = browseTime;
  }
  
  public String getBrowserOrgIdStr() {
    return this.browserOrgIdStr;
  }
  
  public void setBrowserOrgIdStr(String browserOrgIdStr) {
    this.browserOrgIdStr = browserOrgIdStr;
  }
  
  public Long getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }
}
