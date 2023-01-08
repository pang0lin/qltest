package com.js.oa.hr.personnelmanager.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class EmployeeChangePO implements Serializable {
  private Long empChangeId;
  
  private Date empChangeDate = null;
  
  private Long empChangeEmpId;
  
  private Long empChangeOldOrg;
  
  private Long empChangeNewOrg;
  
  private Long empChangeOldDuty;
  
  private Long empChangeNewDuty;
  
  private String empChangeChangeType;
  
  private String domainId;
  
  public EmployeeChangePO(Date empChangeDate, Long empChangeEmpId, Long empChangeOldOrg, Long empChangeNewOrg, Long empChangeOldDuty, Long empChangeNewDuty, String empChangeChangeType) {
    this.empChangeDate = empChangeDate;
    this.empChangeEmpId = empChangeEmpId;
    this.empChangeOldOrg = empChangeOldOrg;
    this.empChangeNewOrg = empChangeNewOrg;
    this.empChangeOldDuty = empChangeOldDuty;
    this.empChangeNewDuty = empChangeNewDuty;
    this.empChangeChangeType = empChangeChangeType;
  }
  
  public EmployeeChangePO() {}
  
  public String getEmpChangeChangeType() {
    return this.empChangeChangeType;
  }
  
  public void setEmpChangeChangeType(String empChangeChangeType) {
    this.empChangeChangeType = empChangeChangeType;
  }
  
  public Date getEmpChangeDate() {
    return this.empChangeDate;
  }
  
  public void setEmpChangeDate(Date empChangeDate) {
    this.empChangeDate = empChangeDate;
  }
  
  public Long getEmpChangeEmpId() {
    return this.empChangeEmpId;
  }
  
  public void setEmpChangeEmpId(Long empChangeEmpId) {
    this.empChangeEmpId = empChangeEmpId;
  }
  
  public Long getEmpChangeId() {
    return this.empChangeId;
  }
  
  public void setEmpChangeId(Long empChangeId) {
    this.empChangeId = empChangeId;
  }
  
  public Long getEmpChangeNewDuty() {
    return this.empChangeNewDuty;
  }
  
  public void setEmpChangeNewDuty(Long empChangeNewDuty) {
    this.empChangeNewDuty = empChangeNewDuty;
  }
  
  public Long getEmpChangeNewOrg() {
    return this.empChangeNewOrg;
  }
  
  public void setEmpChangeNewOrg(Long empChangeNewOrg) {
    this.empChangeNewOrg = empChangeNewOrg;
  }
  
  public Long getEmpChangeOldDuty() {
    return this.empChangeOldDuty;
  }
  
  public void setEmpChangeOldDuty(Long empChangeOldDuty) {
    this.empChangeOldDuty = empChangeOldDuty;
  }
  
  public Long getEmpChangeOldOrg() {
    return this.empChangeOldOrg;
  }
  
  public void setEmpChangeOldOrg(Long empChangeOldOrg) {
    this.empChangeOldOrg = empChangeOldOrg;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("empChangeId", getEmpChangeId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof EmployeeChangePO))
      return false; 
    EmployeeChangePO castOther = (EmployeeChangePO)other;
    return (new EqualsBuilder())
      .append(getEmpChangeId(), castOther.getEmpChangeId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getEmpChangeId())
      .toHashCode();
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
}
