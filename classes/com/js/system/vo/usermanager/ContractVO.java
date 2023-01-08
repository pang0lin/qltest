package com.js.system.vo.usermanager;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ContractVO implements Serializable {
  private Long id;
  
  private Date givenDate = null;
  
  private String contractStyle;
  
  private Date beginDate = null;
  
  private Date endDate = null;
  
  private String contractNO;
  
  private EmployeeVO employeeVO;
  
  private String contractType;
  
  private String contractLimit;
  
  public Date getBeginDate() {
    return this.beginDate;
  }
  
  public void setBeginDate(Date beginDate) {
    this.beginDate = beginDate;
  }
  
  public String getContractStyle() {
    return this.contractStyle;
  }
  
  public void setContractStyle(String contractStyle) {
    this.contractStyle = contractStyle;
  }
  
  public EmployeeVO getEmployeeVO() {
    return this.employeeVO;
  }
  
  public void setEmployeeVO(EmployeeVO employeeVO) {
    this.employeeVO = employeeVO;
  }
  
  public Date getEndDate() {
    return this.endDate;
  }
  
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
  
  public Date getGivenDate() {
    return this.givenDate;
  }
  
  public void setGivenDate(Date givenDate) {
    this.givenDate = givenDate;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public String getContractType() {
    return this.contractType;
  }
  
  public String getContractLimit() {
    return this.contractLimit;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public void setContractType(String contractType) {
    this.contractType = contractType;
  }
  
  public void setContractLimit(String contractLimit) {
    this.contractLimit = contractLimit;
  }
  
  public String toString() {
    return (new ToStringBuilder(this)).append("empId", getId()).toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof ContractVO))
      return false; 
    ContractVO castOther = (ContractVO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
  
  public String getContractNO() {
    return this.contractNO;
  }
  
  public void setContractNO(String contractNO) {
    this.contractNO = contractNO;
  }
}
