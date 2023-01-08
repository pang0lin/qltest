package com.js.doc.doc.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class GovCustomFieldPO implements Serializable {
  private Long id;
  
  private String gffName;
  
  private String gffDisplayName;
  
  private int govFormType;
  
  private Long domainId;
  
  private int gffDisplayType;
  
  private int gffLength;
  
  private int gffFieldType;
  
  private int gffIsNull;
  
  public boolean equals(Object other) {
    if (!(other instanceof GovCustomFieldPO))
      return false; 
    GovCustomFieldPO castOther = (GovCustomFieldPO)other;
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
  
  public String getGffName() {
    return this.gffName;
  }
  
  public String getGffDisplayName() {
    return this.gffDisplayName;
  }
  
  public Long getDomainId() {
    return this.domainId;
  }
  
  public int getGffDisplayType() {
    return this.gffDisplayType;
  }
  
  public int getGffLength() {
    return this.gffLength;
  }
  
  public int getGffFieldType() {
    return this.gffFieldType;
  }
  
  public int getGffIsNull() {
    return this.gffIsNull;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public void setGovFormType(int govFormType) {
    this.govFormType = govFormType;
  }
  
  public void setGffName(String gffName) {
    this.gffName = gffName;
  }
  
  public void setGffDisplayName(String gffDisplayName) {
    this.gffDisplayName = gffDisplayName;
  }
  
  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }
  
  public void setGffDisplayType(int gffDisplayType) {
    this.gffDisplayType = gffDisplayType;
  }
  
  public void setGffLength(int gffLength) {
    this.gffLength = gffLength;
  }
  
  public void setGffFieldType(int gffFieldType) {
    this.gffFieldType = gffFieldType;
  }
  
  public void setGffIsNull(int gffIsNull) {
    this.gffIsNull = gffIsNull;
  }
}
