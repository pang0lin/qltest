package com.js.system.vo.usermanager;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class CompetenceVO implements Serializable {
  private Long id;
  
  private String certificateName;
  
  private Date beginDate = null;
  
  private Date endDate = null;
  
  private String awardUnits;
  
  private EmployeeVO employeeVO;
  
  public boolean equals(Object other) {
    if (!(other instanceof CompetenceVO))
      return false; 
    CompetenceVO castOther = (CompetenceVO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
  
  public String getAwardUnits() {
    return this.awardUnits;
  }
  
  public void setAwardUnits(String awardUnits) {
    this.awardUnits = awardUnits;
  }
  
  public Date getBeginDate() {
    return this.beginDate;
  }
  
  public void setBeginDate(Date beginDate) {
    this.beginDate = beginDate;
  }
  
  public String getCertificateName() {
    return this.certificateName;
  }
  
  public void setCertificateName(String certificateName) {
    this.certificateName = certificateName;
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
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
}
