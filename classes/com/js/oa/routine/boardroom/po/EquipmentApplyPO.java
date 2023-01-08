package com.js.oa.routine.boardroom.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class EquipmentApplyPO implements Serializable {
  private Long equipmentApplyId;
  
  private EquipmentPO equipment;
  
  private Long empId;
  
  private String empName;
  
  private Long orgId;
  
  private String orgName;
  
  private Date startDate = null;
  
  private String startTime;
  
  private Date endDate = null;
  
  private String endTime;
  
  private String purpose;
  
  private Integer status;
  
  private String domainId;
  
  public EquipmentApplyPO(EquipmentPO equipment, Long empId, String empName, Long orgId, String orgName, Date startDate, String startTime, Date endDate, String endTime, String purpose, Integer status) {
    this.equipment = equipment;
    this.empId = empId;
    this.empName = empName;
    this.orgId = orgId;
    this.orgName = orgName;
    this.startDate = startDate;
    this.startTime = startTime;
    this.endDate = endDate;
    this.endTime = endTime;
    this.purpose = purpose;
    this.status = status;
  }
  
  public EquipmentApplyPO() {}
  
  public Long getEmpId() {
    return this.empId;
  }
  
  public void setEmpId(Long empId) {
    this.empId = empId;
  }
  
  public String getEmpName() {
    return this.empName;
  }
  
  public void setEmpName(String empName) {
    this.empName = empName;
  }
  
  public Date getEndDate() {
    return this.endDate;
  }
  
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
  
  public String getEndTime() {
    return this.endTime;
  }
  
  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }
  
  public EquipmentPO getEquipment() {
    return this.equipment;
  }
  
  public void setEquipment(EquipmentPO equipment) {
    this.equipment = equipment;
  }
  
  public Long getEquipmentApplyId() {
    return this.equipmentApplyId;
  }
  
  public void setEquipmentApplyId(Long equipmentApplyId) {
    this.equipmentApplyId = equipmentApplyId;
  }
  
  public Long getOrgId() {
    return this.orgId;
  }
  
  public void setOrgId(Long orgId) {
    this.orgId = orgId;
  }
  
  public String getOrgName() {
    return this.orgName;
  }
  
  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }
  
  public String getPurpose() {
    return this.purpose;
  }
  
  public void setPurpose(String purpose) {
    this.purpose = purpose;
  }
  
  public Date getStartDate() {
    return this.startDate;
  }
  
  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }
  
  public String getStartTime() {
    return this.startTime;
  }
  
  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }
  
  public Integer getStatus() {
    return this.status;
  }
  
  public void setStatus(Integer status) {
    this.status = status;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("equipmentApplyId", getEquipmentApplyId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof EquipmentApplyPO))
      return false; 
    EquipmentApplyPO castOther = (EquipmentApplyPO)other;
    return (new EqualsBuilder())
      .append(getEquipmentApplyId(), castOther.getEquipmentApplyId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getEquipmentApplyId())
      .toHashCode();
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
}
