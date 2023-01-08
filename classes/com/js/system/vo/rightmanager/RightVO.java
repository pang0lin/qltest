package com.js.system.vo.rightmanager;

import com.js.system.vo.rolemanager.RightScopeVO;
import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class RightVO implements Serializable {
  private Long rightId;
  
  private String rightName;
  
  private String rightType;
  
  private String rightClass;
  
  private int rightHasScope;
  
  private String rightDescription;
  
  private String rightSelectRange;
  
  private String rightStatus;
  
  private Set role = null;
  
  private Set rightScopes = null;
  
  private String rightCode;
  
  private String domainId;
  
  private String rightCustomer;
  
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
  
  public Set getRole() {
    return this.role;
  }
  
  public void setRole(Set role) {
    this.role = role;
  }
  
  public Set getRightScopes() {
    return this.rightScopes;
  }
  
  public void setRightScopes(Set rightScopes) {
    this.rightScopes = rightScopes;
  }
  
  public void addRightScopes(RightScopeVO rightScopeVO) {
    this.rightScopes.add(rightScopeVO);
  }
  
  public void removeRightScopes(RightScopeVO rightScopeVO) {
    this.rightScopes.remove(rightScopeVO);
  }
  
  public void removeRole() {
    this.role = null;
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
    if (!(other instanceof RightVO))
      return false; 
    RightVO castOther = (RightVO)other;
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
  
  public String getRightStatus() {
    return this.rightStatus;
  }
  
  public void setRightStatus(String rightStatus) {
    this.rightStatus = rightStatus;
  }
  
  public String getRightCustomer() {
    return this.rightCustomer;
  }
  
  public void setRightCustomer(String rightCustomer) {
    this.rightCustomer = rightCustomer;
  }
}
