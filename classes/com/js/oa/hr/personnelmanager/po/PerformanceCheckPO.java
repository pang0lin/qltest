package com.js.oa.hr.personnelmanager.po;

import com.js.system.vo.usermanager.EmployeeVO;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class PerformanceCheckPO implements Serializable {
  private Long id;
  
  private EmployeeVO emp = null;
  
  private String checkYear;
  
  private String checkMonth;
  
  private Float checkMark;
  
  private Float addMark;
  
  private String addReason;
  
  private Float deductMark;
  
  private String deductReason;
  
  private Long empOrg;
  
  public Long getId() {
    return this.id;
  }
  
  public String getCheckYear() {
    return this.checkYear;
  }
  
  public String getCheckMonth() {
    return this.checkMonth;
  }
  
  public String getDeductReason() {
    return this.deductReason;
  }
  
  public EmployeeVO getEmp() {
    return this.emp;
  }
  
  public Float getCheckMark() {
    return this.checkMark;
  }
  
  public Float getAddMark() {
    return this.addMark;
  }
  
  public Float getDeductMark() {
    return this.deductMark;
  }
  
  public void setAddReason(String addReason) {
    this.addReason = addReason;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public void setCheckYear(String checkYear) {
    this.checkYear = checkYear;
  }
  
  public void setCheckMonth(String checkMonth) {
    this.checkMonth = checkMonth;
  }
  
  public void setDeductReason(String deductReason) {
    this.deductReason = deductReason;
  }
  
  public void setEmp(EmployeeVO emp) {
    this.emp = emp;
  }
  
  public void setCheckMark(Float checkMark) {
    this.checkMark = checkMark;
  }
  
  public void setAddMark(Float addMark) {
    this.addMark = addMark;
  }
  
  public void setDeductMark(Float deductMark) {
    this.deductMark = deductMark;
  }
  
  public void setEmpOrg(Long empOrg) {
    this.empOrg = empOrg;
  }
  
  public String getAddReason() {
    return this.addReason;
  }
  
  public Long getEmpOrg() {
    return this.empOrg;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof PerformanceCheckPO))
      return false; 
    PerformanceCheckPO castOther = (PerformanceCheckPO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
}
