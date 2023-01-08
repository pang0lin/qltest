package com.js.oa.hr.personnelmanager.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class WorkAttendancePO implements Serializable {
  private Long id;
  
  private Long org;
  
  private Long emp;
  
  private String record;
  
  private Integer year;
  
  private Integer month;
  
  private Date fillDate = null;
  
  private String domainId;
  
  public boolean equals(Object other) {
    if (!(other instanceof WorkAttendancePO))
      return false; 
    WorkAttendancePO castOther = (WorkAttendancePO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public Long getOrg() {
    return this.org;
  }
  
  public void setOrg(Long org) {
    this.org = org;
  }
  
  public Long getEmp() {
    return this.emp;
  }
  
  public void setEmp(Long emp) {
    this.emp = emp;
  }
  
  public String getRecord() {
    return this.record;
  }
  
  public void setRecord(String record) {
    this.record = record;
  }
  
  public Integer getYear() {
    return this.year;
  }
  
  public void setYear(Integer year) {
    this.year = year;
  }
  
  public Integer getMonth() {
    return this.month;
  }
  
  public void setMonth(Integer month) {
    this.month = month;
  }
  
  public Date getFillDate() {
    return this.fillDate;
  }
  
  public void setFillDate(Date fillDate) {
    this.fillDate = fillDate;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
}
