package com.js.oa.audit.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class RolePO implements Serializable {
  private long auditRoleId;
  
  private long roleId;
  
  private String roleName;
  
  private String operate;
  
  private long logId;
  
  private String roleDescription;
  
  private long createdEmp;
  
  private long createdOrg;
  
  private String roleUserId;
  
  private String roleUserName;
  
  private String domainId;
  
  private String roleUseRange;
  
  private String roleUseName;
  
  private String roleRange;
  
  private String roleRangeName;
  
  private String roleRangeType;
  
  public RolePO(long roleId, String roleName, String roleDescription) {
    this.roleId = roleId;
    this.roleName = roleName;
    this.roleDescription = roleDescription;
  }
  
  public RolePO() {}
  
  public RolePO(long roleId, String roleName) {
    this.roleId = roleId;
    this.roleName = roleName;
  }
  
  public long getRoleId() {
    return this.roleId;
  }
  
  public void setRoleId(long roleId) {
    this.roleId = roleId;
  }
  
  public String getRoleName() {
    return this.roleName;
  }
  
  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }
  
  public String getRoleDescription() {
    return this.roleDescription;
  }
  
  public void setRoleDescription(String roleDescription) {
    this.roleDescription = roleDescription;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("roleId", getRoleId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof RolePO))
      return false; 
    RolePO castOther = (RolePO)other;
    return (new EqualsBuilder())
      .append(getRoleId(), castOther.getRoleId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getRoleId())
      .toHashCode();
  }
  
  public long getCreatedEmp() {
    return this.createdEmp;
  }
  
  public long getCreatedOrg() {
    return this.createdOrg;
  }
  
  public void setCreatedEmp(long createdEmp) {
    this.createdEmp = createdEmp;
  }
  
  public void setCreatedOrg(long createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public String getRoleUserId() {
    return this.roleUserId;
  }
  
  public void setRoleUserId(String roleUserId) {
    this.roleUserId = roleUserId;
  }
  
  public String getRoleUserName() {
    return this.roleUserName;
  }
  
  public void setRoleUserName(String roleUserName) {
    this.roleUserName = roleUserName;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
  
  public String getRoleUseRange() {
    return this.roleUseRange;
  }
  
  public void setRoleUseRange(String roleUseRange) {
    this.roleUseRange = roleUseRange;
  }
  
  public String getRoleUseName() {
    return this.roleUseName;
  }
  
  public void setRoleUseName(String roleUseName) {
    this.roleUseName = roleUseName;
  }
  
  public String getRoleRange() {
    return this.roleRange;
  }
  
  public void setRoleRange(String roleRange) {
    this.roleRange = roleRange;
  }
  
  public String getRoleRangeName() {
    return this.roleRangeName;
  }
  
  public void setRoleRangeName(String roleRangeName) {
    this.roleRangeName = roleRangeName;
  }
  
  public String getRoleRangeType() {
    return this.roleRangeType;
  }
  
  public void setRoleRangeType(String roleRangeType) {
    this.roleRangeType = roleRangeType;
  }
  
  public long getAuditRoleId() {
    return this.auditRoleId;
  }
  
  public void setAuditRoleId(long auditRoleId) {
    this.auditRoleId = auditRoleId;
  }
  
  public String getOperate() {
    return this.operate;
  }
  
  public void setOperate(String operate) {
    this.operate = operate;
  }
  
  public long getLogId() {
    return this.logId;
  }
  
  public void setLogId(long logId) {
    this.logId = logId;
  }
}
