package com.js.oa.jsflow.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class WFPackagePO implements Serializable {
  private Long wfPackageId;
  
  private String packageName;
  
  private Date packageCreatedDate = null;
  
  private String packageDescription;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  private Set wfWorkFlowProcess = null;
  
  private int moduleId;
  
  private int orderCode;
  
  private String domainId;
  
  public Long getWfPackageId() {
    return this.wfPackageId;
  }
  
  public void setWfPackageId(Long wfPackageId) {
    this.wfPackageId = wfPackageId;
  }
  
  public String getPackageName() {
    return this.packageName;
  }
  
  public void setPackageName(String packageName) {
    this.packageName = packageName;
  }
  
  public Date getPackageCreatedDate() {
    return this.packageCreatedDate;
  }
  
  public void setPackageCreatedDate(Date packageCreatedDate) {
    this.packageCreatedDate = packageCreatedDate;
  }
  
  public String getPackageDescription() {
    return this.packageDescription;
  }
  
  public void setPackageDescription(String packageDescription) {
    this.packageDescription = packageDescription;
  }
  
  public Long getCreatedEmp() {
    return this.createdEmp;
  }
  
  public void setCreatedEmp(Long createdEmp) {
    this.createdEmp = createdEmp;
  }
  
  public Long getCreatedOrg() {
    return this.createdOrg;
  }
  
  public void setCreatedOrg(Long createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public Set getWfWorkFlowProcess() {
    return this.wfWorkFlowProcess;
  }
  
  public void setWfWorkFlowProcess(Set wfWorkFlowProcess) {
    this.wfWorkFlowProcess = wfWorkFlowProcess;
  }
  
  public int getModuleId() {
    return this.moduleId;
  }
  
  public void setModuleId(int moduleId) {
    this.moduleId = moduleId;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof WFPackagePO))
      return false; 
    WFPackagePO castOther = (WFPackagePO)other;
    return (new EqualsBuilder()).append(getWfPackageId(), castOther.getWfPackageId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getWfPackageId()).toHashCode();
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
  
  public int getOrderCode() {
    return this.orderCode;
  }
  
  public void setOrderCode(int orderCode) {
    this.orderCode = orderCode;
  }
}
