package com.js.oa.hr.personnelmanager.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class EmployeeChangeTypePO implements Serializable {
  private String empChangeType;
  
  private Long id;
  
  private String domainId;
  
  public String getEmpChangeType() {
    return this.empChangeType;
  }
  
  public void setEmpChangeType(String empChangeType) {
    this.empChangeType = empChangeType;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof EmployeeChangeTypePO))
      return false; 
    EmployeeChangeTypePO castOther = (EmployeeChangeTypePO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
}
