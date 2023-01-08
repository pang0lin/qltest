package com.js.doc.doc.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class SenddocumentTopicalPO implements Serializable {
  private Long id;
  
  private Integer tableType;
  
  private String areaType;
  
  private String sortTopical;
  
  private String attributeTopical;
  
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
  
  public Integer getTableType() {
    return this.tableType;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public String getAreaType() {
    return this.areaType;
  }
  
  public String getSortTopical() {
    return this.sortTopical;
  }
  
  public void setAttributeTopical(String attributeTopical) {
    this.attributeTopical = attributeTopical;
  }
  
  public void setTableType(Integer tableType) {
    this.tableType = tableType;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public void setAreaType(String areaType) {
    this.areaType = areaType;
  }
  
  public void setSortTopical(String sortTopical) {
    this.sortTopical = sortTopical;
  }
  
  public String getAttributeTopical() {
    return this.attributeTopical;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof SenddocumentTopicalPO))
      return false; 
    SenddocumentTopicalPO castOther = (SenddocumentTopicalPO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
}
