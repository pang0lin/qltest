package com.js.system.vo.rightmanager;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class SimpleRightVO implements Serializable {
  private Long rightId;
  
  private String rightName;
  
  private String rightType;
  
  private String rightClass;
  
  private int rightHasScope;
  
  private String rightSelectRange;
  
  private String rightDescription;
  
  private String rightCode;
  
  private String domainId;
  
  public String getRightDescription() {
    return this.rightDescription;
  }
  
  public int getRightHasScope() {
    return this.rightHasScope;
  }
  
  public Long getRightId() {
    return this.rightId;
  }
  
  public String getRightName() {
    return this.rightName;
  }
  
  public String getRightType() {
    return this.rightType;
  }
  
  public void setRightDescription(String rightDescription) {
    this.rightDescription = rightDescription;
  }
  
  public void setRightHasScope(int rightHasScope) {
    this.rightHasScope = rightHasScope;
  }
  
  public void setRightId(Long rightId) {
    this.rightId = rightId;
  }
  
  public void setRightName(String rightName) {
    this.rightName = rightName;
  }
  
  public void setRightType(String rightType) {
    this.rightType = rightType;
  }
  
  public String getRightClass() {
    return this.rightClass;
  }
  
  public void setRightClass(String rightClass) {
    this.rightClass = rightClass;
  }
  
  public String getRightSelectRange() {
    return this.rightSelectRange;
  }
  
  public void setRightSelectRange(String rightSelectRange) {
    this.rightSelectRange = rightSelectRange;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof SimpleRightVO))
      return false; 
    SimpleRightVO castOther = (SimpleRightVO)other;
    return (new EqualsBuilder())
      .append(getRightId(), castOther.getRightId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getRightId())
      .toHashCode();
  }
  
  public String getRightCode() {
    return this.rightCode;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setRightCode(String rightCode) {
    this.rightCode = rightCode;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
}
