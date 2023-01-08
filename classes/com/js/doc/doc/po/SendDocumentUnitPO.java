package com.js.doc.doc.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class SendDocumentUnitPO implements Serializable {
  private Long id;
  
  private String unitType;
  
  private String unitWholeName;
  
  private String unitShortName;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  private Long corpId;
  
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
  
  public Long getCorpId() {
    return this.corpId;
  }
  
  public void setCorpId(Long corpId) {
    this.corpId = corpId;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public String getUnitWholeName() {
    return this.unitWholeName;
  }
  
  public String getUnitShortName() {
    return this.unitShortName;
  }
  
  public void setUnitType(String unitType) {
    this.unitType = unitType;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public void setUnitWholeName(String unitWholeName) {
    this.unitWholeName = unitWholeName;
  }
  
  public void setUnitShortName(String unitShortName) {
    this.unitShortName = unitShortName;
  }
  
  public String getUnitType() {
    return this.unitType;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof SendDocumentUnitPO))
      return false; 
    SendDocumentUnitPO castOther = (SendDocumentUnitPO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
}
