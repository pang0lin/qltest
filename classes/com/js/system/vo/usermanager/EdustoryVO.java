package com.js.system.vo.usermanager;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class EdustoryVO implements Serializable {
  private Long id;
  
  private String schools;
  
  private String speciality;
  
  private String education;
  
  private Date beginDate = null;
  
  private Date endDate = null;
  
  private String degree;
  
  private EmployeeVO employeeVO;
  
  public Date getBeginDate() {
    return this.beginDate;
  }
  
  public void setBeginDate(Date beginDate) {
    this.beginDate = beginDate;
  }
  
  public String getEducation() {
    return this.education;
  }
  
  public void setEducation(String education) {
    this.education = education;
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
  
  public String getSpeciality() {
    return this.speciality;
  }
  
  public void setSpeciality(String speciality) {
    this.speciality = speciality;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("empId", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof EdustoryVO))
      return false; 
    EdustoryVO castOther = (EdustoryVO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
  }
  
  public String getSchools() {
    return this.schools;
  }
  
  public void setSchools(String schools) {
    this.schools = schools;
  }
  
  public String getDegree() {
    return this.degree;
  }
  
  public void setDegree(String degree) {
    this.degree = degree;
  }
}
