package com.js.oa.hr.personnelmanager.po;

import com.js.system.vo.usermanager.EmployeeVO;
import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class EmpCompoPO implements Serializable {
  private Long id;
  
  private EmployeeVO emp = null;
  
  private Date occurDate = null;
  
  private String accident;
  
  private String compensateUnit;
  
  private String insuranceCompany;
  
  private Double compensateMoney;
  
  private Long empOrg;
  
  private String appraisalLevel;
  
  public String getAccident() {
    return this.accident;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public String getAppraisalLevel() {
    return this.appraisalLevel;
  }
  
  public EmployeeVO getEmp() {
    return this.emp;
  }
  
  public String getInsuranceCompany() {
    return this.insuranceCompany;
  }
  
  public String getCompensateUnit() {
    return this.compensateUnit;
  }
  
  public Date getOccurDate() {
    return this.occurDate;
  }
  
  public void setCompensateMoney(Double compensateMoney) {
    this.compensateMoney = compensateMoney;
  }
  
  public void setAccident(String accident) {
    this.accident = accident;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public void setAppraisalLevel(String appraisalLevel) {
    this.appraisalLevel = appraisalLevel;
  }
  
  public void setEmp(EmployeeVO emp) {
    this.emp = emp;
  }
  
  public void setInsuranceCompany(String insuranceCompany) {
    this.insuranceCompany = insuranceCompany;
  }
  
  public void setCompensateUnit(String compensateUnit) {
    this.compensateUnit = compensateUnit;
  }
  
  public void setOccurDate(Date occurDate) {
    this.occurDate = occurDate;
  }
  
  public void setEmpOrg(Long empOrg) {
    this.empOrg = empOrg;
  }
  
  public Double getCompensateMoney() {
    return this.compensateMoney;
  }
  
  public Long getEmpOrg() {
    return this.empOrg;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof EmpCompoPO))
      return false; 
    EmpCompoPO castOther = (EmpCompoPO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
}
