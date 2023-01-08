package com.js.oa.datasync.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class DataSyncFieldPO implements Serializable {
  private Long id;
  
  private Long syncSetId;
  
  private String fromField;
  
  private String fromType;
  
  private String fromValueSet;
  
  private String toField;
  
  private String toType;
  
  private String fieldIsMainForm;
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public Long getSyncSetId() {
    return this.syncSetId;
  }
  
  public void setSyncSetId(Long syncSetId) {
    this.syncSetId = syncSetId;
  }
  
  public String getFromField() {
    return this.fromField;
  }
  
  public void setFromField(String fromField) {
    this.fromField = fromField;
  }
  
  public String getFromType() {
    return this.fromType;
  }
  
  public void setFromType(String fromType) {
    this.fromType = fromType;
  }
  
  public String getFromValueSet() {
    return this.fromValueSet;
  }
  
  public void setFromValueSet(String fromValueSet) {
    this.fromValueSet = fromValueSet;
  }
  
  public String getToField() {
    return this.toField;
  }
  
  public void setToField(String toField) {
    this.toField = toField;
  }
  
  public String getToType() {
    return this.toType;
  }
  
  public void setToType(String toType) {
    this.toType = toType;
  }
  
  public String getFieldIsMainForm() {
    return this.fieldIsMainForm;
  }
  
  public void setFieldIsMainForm(String fieldIsMainForm) {
    this.fieldIsMainForm = fieldIsMainForm;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof DataSyncFieldPO))
      return false; 
    DataSyncFieldPO castOther = (DataSyncFieldPO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
}
