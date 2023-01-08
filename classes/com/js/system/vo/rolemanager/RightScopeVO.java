package com.js.system.vo.rolemanager;

import com.js.system.vo.rightmanager.RightVO;
import com.js.system.vo.usermanager.EmployeeVO;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class RightScopeVO implements Serializable {
  private long rightScopeId;
  
  private short rightScopeType;
  
  private String rightScopeScope;
  
  private RightVO right = null;
  
  private EmployeeVO employee = null;
  
  private String rightScopeUser;
  
  private String rightScopeGroup;
  
  private String rightScope;
  
  private String domainId;
  
  public RightScopeVO(long rightScopeId, short rightScopeType, String rightScopeScope, RightVO right, EmployeeVO employee) {
    this.rightScopeId = rightScopeId;
    this.rightScopeType = rightScopeType;
    this.rightScopeScope = rightScopeScope;
    this.right = right;
    this.employee = employee;
  }
  
  public RightScopeVO() {}
  
  public RightScopeVO(long rightScopeId) {
    this.rightScopeId = rightScopeId;
  }
  
  public long getRightScopeId() {
    return this.rightScopeId;
  }
  
  public void setRightScopeId(long rightScopeId) {
    this.rightScopeId = rightScopeId;
  }
  
  public short getRightScopeType() {
    return this.rightScopeType;
  }
  
  public void setRightScopeType(short rightScopeType) {
    this.rightScopeType = rightScopeType;
  }
  
  public String getRightScopeScope() {
    return this.rightScopeScope;
  }
  
  public void setRightScopeScope(String rightScopeScope) {
    this.rightScopeScope = rightScopeScope;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("rightScopeId", getRightScopeId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof RightScopeVO))
      return false; 
    RightScopeVO castOther = (RightScopeVO)other;
    return (new EqualsBuilder())
      .append(getRightScopeId(), castOther.getRightScopeId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getRightScopeId())
      .toHashCode();
  }
  
  public EmployeeVO getEmployee() {
    return this.employee;
  }
  
  public void setEmployee(EmployeeVO employee) {
    this.employee = employee;
  }
  
  public RightVO getRight() {
    return this.right;
  }
  
  public void setRight(RightVO right) {
    this.right = right;
  }
  
  public String getRightScopeUser() {
    return this.rightScopeUser;
  }
  
  public void setRightScopeUser(String rightScopeUser) {
    this.rightScopeUser = rightScopeUser;
  }
  
  public String getRightScopeGroup() {
    return this.rightScopeGroup;
  }
  
  public void setRightScopeGroup(String rightScopeGroup) {
    this.rightScopeGroup = rightScopeGroup;
  }
  
  public String getRightScope() {
    return this.rightScope;
  }
  
  public void setRightScope(String rightScope) {
    this.rightScope = rightScope;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
}
