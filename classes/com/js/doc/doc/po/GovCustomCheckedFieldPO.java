package com.js.doc.doc.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class GovCustomCheckedFieldPO implements Serializable {
  private Long id;
  
  private String gffName;
  
  private String gffDisplayName;
  
  private int govFormType;
  
  private int gffType;
  
  private Long govFormId;
  
  private Long domainId;
  
  public boolean equals(Object other) {
    if (!(other instanceof GovCustomCheckedFieldPO))
      return false; 
    GovCustomCheckedFieldPO castOther = (GovCustomCheckedFieldPO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
  
  public Long getId() {
    return this.id;
  }
  
  public int getGovFormType() {
    return this.govFormType;
  }
  
  public Long getGovFormId() {
    return this.govFormId;
  }
  
  public int getGffType() {
    return this.gffType;
  }
  
  public String getGffName() {
    return this.gffName;
  }
  
  public String getGffDisplayName() {
    return this.gffDisplayName;
  }
  
  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public void setGovFormType(int govFormType) {
    this.govFormType = govFormType;
  }
  
  public void setGovFormId(Long govFormId) {
    this.govFormId = govFormId;
  }
  
  public void setGffType(int gffType) {
    this.gffType = gffType;
  }
  
  public void setGffName(String gffName) {
    this.gffName = gffName;
  }
  
  public void setGffDisplayName(String gffDisplayName) {
    this.gffDisplayName = gffDisplayName;
  }
  
  public Long getDomainId() {
    return this.domainId;
  }
}
