package com.js.oa.scheme.taskcenter.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class TaskClassPO implements Serializable {
  private Long classId;
  
  private Long classDomainId;
  
  private String className;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  private String usedScopeName;
  
  private String usedScopeId;
  
  private String usedScopeOrgId;
  
  private String usedScopeGroupId;
  
  private String orderCode;
  
  public TaskClassPO(String className, Long classDomainId, Long createdEmp, Long createdOrg) {
    this.className = className;
    this.classDomainId = classDomainId;
    this.createdEmp = createdEmp;
    this.createdOrg = createdOrg;
  }
  
  public TaskClassPO() {}
  
  public Long getClassId() {
    return this.classId;
  }
  
  public void setClassId(Long classId) {
    this.classId = classId;
  }
  
  public Long getClassDomainId() {
    return this.classDomainId;
  }
  
  public void setClassDomainId(Long classDomainId) {
    this.classDomainId = classDomainId;
  }
  
  public String getClassName() {
    return this.className;
  }
  
  public void setClassName(String className) {
    this.className = className;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("classId", getClassId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof TaskClassPO))
      return false; 
    TaskClassPO castOther = (TaskClassPO)other;
    return (new EqualsBuilder())
      .append(getClassId(), castOther.getClassId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getClassId())
      .toHashCode();
  }
  
  public Long getCreatedEmp() {
    return this.createdEmp;
  }
  
  public Long getCreatedOrg() {
    return this.createdOrg;
  }
  
  public String getUsedScopeId() {
    return this.usedScopeId;
  }
  
  public String getUsedScopeGroupId() {
    return this.usedScopeGroupId;
  }
  
  public String getUsedScopeName() {
    return this.usedScopeName;
  }
  
  public String getUsedScopeOrgId() {
    return this.usedScopeOrgId;
  }
  
  public void setCreatedEmp(Long createdEmp) {
    this.createdEmp = createdEmp;
  }
  
  public void setCreatedOrg(Long createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public void setUsedScopeId(String usedScopeId) {
    this.usedScopeId = usedScopeId;
  }
  
  public void setUsedScopeGroupId(String usedScopeGroupId) {
    this.usedScopeGroupId = usedScopeGroupId;
  }
  
  public void setUsedScopeName(String usedScopeName) {
    this.usedScopeName = usedScopeName;
  }
  
  public void setUsedScopeOrgId(String usedScopeOrgId) {
    this.usedScopeOrgId = usedScopeOrgId;
  }
  
  public String getOrderCode() {
    return this.orderCode;
  }
  
  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }
}
