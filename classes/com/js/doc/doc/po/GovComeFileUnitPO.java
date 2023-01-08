package com.js.doc.doc.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class GovComeFileUnitPO implements Serializable {
  private long id;
  
  private String comeFileUnitName;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  private String domainId;
  
  public GovComeFileUnitPO(String comefileunitName) {
    this.comeFileUnitName = comefileunitName;
  }
  
  public GovComeFileUnitPO() {}
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public String getComeFileUnitName() {
    return this.comeFileUnitName;
  }
  
  public void setComeFileUnitName(String comeFileUnitName) {
    this.comeFileUnitName = comeFileUnitName;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof GovComeFileUnitPO))
      return false; 
    GovComeFileUnitPO castOther = (GovComeFileUnitPO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
  }
  
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
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
}
