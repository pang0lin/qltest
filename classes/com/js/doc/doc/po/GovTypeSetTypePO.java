package com.js.doc.doc.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class GovTypeSetTypePO implements Serializable {
  private long id;
  
  private String typeName;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  private String domainId;
  
  public long getId() {
    return this.id;
  }
  
  public Long getCreatedOrg() {
    return this.createdOrg;
  }
  
  public Long getCreatedEmp() {
    return this.createdEmp;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public void setCreatedOrg(Long createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public void setCreatedEmp(Long createdEmp) {
    this.createdEmp = createdEmp;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
  
  public String getTypeName() {
    return this.typeName;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof GovTopicWordPO))
      return false; 
    GovTopicWordPO castOther = (GovTopicWordPO)other;
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
