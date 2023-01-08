package com.js.oa.hr.personnelmanager.po;

import com.js.system.vo.usermanager.EmployeeVO;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class EmpSocialinsurancePO implements Serializable {
  private Long id;
  
  private EmployeeVO emp = null;
  
  private String payMonth;
  
  private String payType;
  
  private Double payBase;
  
  private String stopMonth;
  
  private String memos;
  
  private Long empOrg;
  
  public Long getId() {
    return this.id;
  }
  
  public String getMemos() {
    return this.memos;
  }
  
  public String getPayType() {
    return this.payType;
  }
  
  public String getStopMonth() {
    return this.stopMonth;
  }
  
  public EmployeeVO getEmp() {
    return this.emp;
  }
  
  public String getPayMonth() {
    return this.payMonth;
  }
  
  public void setPayBase(Double payBase) {
    this.payBase = payBase;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public void setMemos(String memos) {
    this.memos = memos;
  }
  
  public void setPayType(String payType) {
    this.payType = payType;
  }
  
  public void setStopMonth(String stopMonth) {
    this.stopMonth = stopMonth;
  }
  
  public void setEmp(EmployeeVO emp) {
    this.emp = emp;
  }
  
  public void setPayMonth(String payMonth) {
    this.payMonth = payMonth;
  }
  
  public void setEmpOrg(Long empOrg) {
    this.empOrg = empOrg;
  }
  
  public Double getPayBase() {
    return this.payBase;
  }
  
  public Long getEmpOrg() {
    return this.empOrg;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof EmpSocialinsurancePO))
      return false; 
    EmpSocialinsurancePO castOther = (EmpSocialinsurancePO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
}
