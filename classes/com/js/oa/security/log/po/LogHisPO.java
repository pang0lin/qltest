package com.js.oa.security.log.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class LogHisPO implements Serializable {
  private Long logId;
  
  private String empId;
  
  private String empName;
  
  private String empOrgName;
  
  private String moduleSerial;
  
  private Date oprStartTime = null;
  
  private Date oprEndTime = null;
  
  private String oprSubModule;
  
  private String oprType;
  
  private String oprContent;
  
  private String logIP;
  
  private String domainId;
  
  public LogHisPO(Long logId, String empId, String empName, String empOrgName, String moduleSerial, Date oprStartTime, Date oprEndTime, String oprSubModule, String oprType, String oprContent, String logIP, String domainId) {
    this.logId = logId;
    this.empId = empId;
    this.empName = empName;
    this.empOrgName = empOrgName;
    this.moduleSerial = moduleSerial;
    this.oprStartTime = oprStartTime;
    this.oprEndTime = oprEndTime;
    this.oprSubModule = oprSubModule;
    this.oprType = oprType;
    this.oprContent = oprContent;
    this.logIP = logIP;
    this.domainId = domainId;
  }
  
  public LogHisPO(Long logId, String doaminId) {
    this.logId = logId;
    this.domainId = doaminId;
  }
  
  public LogHisPO() {}
  
  public void setLogId(Long logId) {
    this.logId = logId;
  }
  
  public void setEmpId(String empId) {
    this.empId = empId;
  }
  
  public void setEmpName(String empName) {
    this.empName = empName;
  }
  
  public void setEmpOrgName(String empOrgName) {
    this.empOrgName = empOrgName;
  }
  
  public void setModuleSerial(String moduleSerial) {
    this.moduleSerial = moduleSerial;
  }
  
  public void setOprStartTime(Date oprStartTime) {
    this.oprStartTime = oprStartTime;
  }
  
  public void setOprEndTime(Date oprEndTime) {
    this.oprEndTime = oprEndTime;
  }
  
  public void setOprSubModule(String oprSubModule) {
    this.oprSubModule = oprSubModule;
  }
  
  public void setOprType(String oprType) {
    this.oprType = oprType;
  }
  
  public void setOprContent(String oprContent) {
    this.oprContent = oprContent;
  }
  
  public void setLogIP(String logIP) {
    this.logIP = logIP;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
  
  public Long getLogId() {
    return this.logId;
  }
  
  public String getEmpId() {
    return this.empId;
  }
  
  public String getEmpName() {
    return this.empName;
  }
  
  public String getEmpOrgName() {
    return this.empOrgName;
  }
  
  public String getModuleSerial() {
    return this.moduleSerial;
  }
  
  public Date getOprStartTime() {
    return this.oprStartTime;
  }
  
  public Date getOprEndTime() {
    return this.oprEndTime;
  }
  
  public String getOprSubModule() {
    return this.oprSubModule;
  }
  
  public String getOprType() {
    return this.oprType;
  }
  
  public String getOprContent() {
    return this.oprContent;
  }
  
  public String getLogIP() {
    return this.logIP;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getLogId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof LogHisPO))
      return false; 
    LogHisPO castOther = (LogHisPO)other;
    return (new EqualsBuilder())
      .append(getLogId(), castOther.getLogId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getLogId())
      .toHashCode();
  }
}
