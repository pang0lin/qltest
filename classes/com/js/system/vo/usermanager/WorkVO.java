package com.js.system.vo.usermanager;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class WorkVO implements Serializable {
  private Long id;
  
  private String workunit;
  
  private String workduty;
  
  private String proveperson;
  
  private String telephone;
  
  private String workMemo;
  
  private Date beginDate = null;
  
  private Date endDate = null;
  
  private EmployeeVO employeeVO;
  
  public Long getId() {
    return this.id;
  }
  
  public Date getEndDate() {
    return this.endDate;
  }
  
  public String getProveperson() {
    return this.proveperson;
  }
  
  public String getTelephone() {
    return this.telephone;
  }
  
  public String getWorkduty() {
    return this.workduty;
  }
  
  public String getWorkMemo() {
    return this.workMemo;
  }
  
  public Date getBeginDate() {
    return this.beginDate;
  }
  
  public String getWorkunit() {
    return this.workunit;
  }
  
  public void setEmployeeVO(EmployeeVO employeeVO) {
    this.employeeVO = employeeVO;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
  
  public void setProveperson(String proveperson) {
    this.proveperson = proveperson;
  }
  
  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }
  
  public void setWorkduty(String workduty) {
    this.workduty = workduty;
  }
  
  public void setWorkMemo(String workMemo) {
    this.workMemo = workMemo;
  }
  
  public void setBeginDate(Date beginDate) {
    this.beginDate = beginDate;
  }
  
  public void setWorkunit(String workunit) {
    this.workunit = workunit;
  }
  
  public EmployeeVO getEmployeeVO() {
    return this.employeeVO;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof WorkVO))
      return false; 
    WorkVO castOther = (WorkVO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
  }
}
