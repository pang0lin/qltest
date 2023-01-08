package com.js.oa.module.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ModuleSEQPO implements Serializable {
  private Long id;
  
  private String menuCaseName;
  
  private Long menuId;
  
  private String qlFields;
  
  private Long domainId;
  
  private Integer caseType;
  
  public Long getId() {
    return this.id;
  }
  
  public Long getDomainId() {
    return this.domainId;
  }
  
  public String getQlFields() {
    return this.qlFields;
  }
  
  public void setMenuId(Long menuId) {
    this.menuId = menuId;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }
  
  public void setQlFields(String qlFields) {
    this.qlFields = qlFields;
  }
  
  public void setMenuCaseName(String menuCaseName) {
    this.menuCaseName = menuCaseName;
  }
  
  public void setCaseType(Integer caseType) {
    this.caseType = caseType;
  }
  
  public Long getMenuId() {
    return this.menuId;
  }
  
  public String getMenuCaseName() {
    return this.menuCaseName;
  }
  
  public Integer getCaseType() {
    return this.caseType;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof ModuleSEQPO))
      return false; 
    ModuleSEQPO castOther = (ModuleSEQPO)other;
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
