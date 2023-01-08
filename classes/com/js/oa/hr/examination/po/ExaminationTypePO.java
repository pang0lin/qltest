package com.js.oa.hr.examination.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ExaminationTypePO implements Serializable {
  private Long id;
  
  private String typeName;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  private Long domainID;
  
  public Long getCreatedEmp() {
    return this.createdEmp;
  }
  
  public void setCreatedEmp(Long createdEmp) {
    this.createdEmp = createdEmp;
  }
  
  public Long getCreatedOrg() {
    return this.createdOrg;
  }
  
  public void setCreatedOrg(Long createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public Long getDomainID() {
    return this.domainID;
  }
  
  public void setDomainID(Long domainID) {
    this.domainID = domainID;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getTypeName() {
    return this.typeName;
  }
  
  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof ExaminationTypePO))
      return false; 
    ExaminationTypePO castOther = (ExaminationTypePO)other;
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
