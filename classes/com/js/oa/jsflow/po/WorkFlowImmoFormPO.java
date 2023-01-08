package com.js.oa.jsflow.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class WorkFlowImmoFormPO implements Serializable {
  private Long immoFormId;
  
  private String realName;
  
  private Long moduleId;
  
  private String displayName;
  
  private String primaryKey;
  
  public Long getImmoFormId() {
    return this.immoFormId;
  }
  
  public void setImmoFormId(Long immoFormId) {
    this.immoFormId = immoFormId;
  }
  
  public String getRealName() {
    return this.realName;
  }
  
  public void setRealName(String realName) {
    this.realName = realName;
  }
  
  public Long getModuleId() {
    return this.moduleId;
  }
  
  public void setModuleId(Long moduleId) {
    this.moduleId = moduleId;
  }
  
  public String getDisplayName() {
    return this.displayName;
  }
  
  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }
  
  public String getPrimaryKey() {
    return this.primaryKey;
  }
  
  public void setPrimaryKey(String primaryKey) {
    this.primaryKey = primaryKey;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof WorkFlowImmoFormPO))
      return false; 
    WorkFlowImmoFormPO castOther = (WorkFlowImmoFormPO)other;
    return (new EqualsBuilder()).append(getImmoFormId(), castOther.getImmoFormId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getImmoFormId()).toHashCode();
  }
}
