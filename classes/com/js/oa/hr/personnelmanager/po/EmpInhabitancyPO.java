package com.js.oa.hr.personnelmanager.po;

import com.js.system.vo.usermanager.EmployeeVO;
import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class EmpInhabitancyPO implements Serializable {
  private Long id;
  
  private EmployeeVO emp = null;
  
  private Date beginDate = null;
  
  private Date continueDate = null;
  
  private String yearLimit;
  
  private String memos;
  
  private Long empOrg;
  
  public Long getId() {
    return this.id;
  }
  
  public String getMemos() {
    return this.memos;
  }
  
  public EmployeeVO getEmp() {
    return this.emp;
  }
  
  public Date getContinueDate() {
    return this.continueDate;
  }
  
  public Date getBeginDate() {
    return this.beginDate;
  }
  
  public void setYearLimit(String yearLimit) {
    this.yearLimit = yearLimit;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public void setMemos(String memos) {
    this.memos = memos;
  }
  
  public void setEmp(EmployeeVO emp) {
    this.emp = emp;
  }
  
  public void setContinueDate(Date continueDate) {
    this.continueDate = continueDate;
  }
  
  public void setBeginDate(Date beginDate) {
    this.beginDate = beginDate;
  }
  
  public void setEmpOrg(Long empOrg) {
    this.empOrg = empOrg;
  }
  
  public String getYearLimit() {
    return this.yearLimit;
  }
  
  public Long getEmpOrg() {
    return this.empOrg;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof EmpInhabitancyPO))
      return false; 
    EmpInhabitancyPO castOther = (EmpInhabitancyPO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
}
