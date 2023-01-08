package com.js.oa.jsflow.po;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class TTypePO implements Serializable {
  private Long typeId;
  
  private String typeDesName;
  
  private String typeName;
  
  private String typeDes;
  
  private Set field = null;
  
  public Long getTypeId() {
    return this.typeId;
  }
  
  public void setTypeId(Long typeId) {
    this.typeId = typeId;
  }
  
  public String getTypeDesName() {
    return this.typeDesName;
  }
  
  public void setTypeDesName(String typeDesName) {
    this.typeDesName = typeDesName;
  }
  
  public String getTypeName() {
    return this.typeName;
  }
  
  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }
  
  public String getTypeDes() {
    return this.typeDes;
  }
  
  public void setTypeDes(String typeDes) {
    this.typeDes = typeDes;
  }
  
  public Set getField() {
    return this.field;
  }
  
  public void setField(Set field) {
    this.field = field;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof TTypePO))
      return false; 
    TTypePO castOther = (TTypePO)other;
    return (new EqualsBuilder()).append(getTypeId(), castOther.getTypeId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getTypeId()).toHashCode();
  }
}
