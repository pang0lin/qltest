package com.js.system.vo.groupmanager;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class GroupVO implements Serializable {
  private Long groupId;
  
  private String groupName;
  
  private String groupDescription;
  
  private String groupUserString;
  
  private Set employees = null;
  
  private String createdOrg;
  
  private String createdEmp;
  
  private String groupUserNames;
  
  private String rangeName;
  
  private String rangeEmp;
  
  private String rangeOrg;
  
  private String rangeGroup;
  
  private String domainId;
  
  private String groupType;
  
  private String groupOrder;
  
  private String orgIdString;
  
  public GroupVO(String groupName, String groupDescription, Set employees) {
    this.groupName = groupName;
    this.groupDescription = groupDescription;
    this.employees = employees;
  }
  
  public GroupVO() {}
  
  public GroupVO(String groupName) {
    this.groupName = groupName;
  }
  
  public String getOrgIdString() {
    return this.orgIdString;
  }
  
  public void setOrgIdString(String orgIdString) {
    this.orgIdString = orgIdString;
  }
  
  public Long getGroupId() {
    return this.groupId;
  }
  
  public void setGroupId(Long groupId) {
    this.groupId = groupId;
  }
  
  public String getGroupName() {
    return this.groupName;
  }
  
  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }
  
  public String getGroupDescription() {
    return this.groupDescription;
  }
  
  public void setGroupDescription(String groupDescription) {
    this.groupDescription = groupDescription;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getGroupId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof GroupVO))
      return false; 
    GroupVO castOther = (GroupVO)other;
    return (new EqualsBuilder())
      .append(getGroupId(), castOther.getGroupId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getGroupId())
      .toHashCode();
  }
  
  public Set getEmployees() {
    return this.employees;
  }
  
  public void setEmployees(Set employees) {
    this.employees = employees;
  }
  
  public String getGroupUserString() {
    return this.groupUserString;
  }
  
  public void setGroupUserString(String groupUserString) {
    this.groupUserString = groupUserString;
  }
  
  public String getCreatedOrg() {
    return this.createdOrg;
  }
  
  public void setCreatedOrg(String createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public String getCreatedEmp() {
    return this.createdEmp;
  }
  
  public void setCreatedEmp(String createdEmp) {
    this.createdEmp = createdEmp;
  }
  
  public String getGroupUserNames() {
    return this.groupUserNames;
  }
  
  public void setGroupUserNames(String groupUserNames) {
    this.groupUserNames = groupUserNames;
  }
  
  public String getRangeName() {
    return this.rangeName;
  }
  
  public void setRangeName(String rangeName) {
    this.rangeName = rangeName;
  }
  
  public String getRangeEmp() {
    return this.rangeEmp;
  }
  
  public void setRangeEmp(String rangeEmp) {
    this.rangeEmp = rangeEmp;
  }
  
  public String getRangeOrg() {
    return this.rangeOrg;
  }
  
  public void setRangeOrg(String rangeOrg) {
    this.rangeOrg = rangeOrg;
  }
  
  public String getRangeGroup() {
    return this.rangeGroup;
  }
  
  public void setRangeGroup(String rangeGroup) {
    this.rangeGroup = rangeGroup;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
  
  public String getGroupType() {
    return this.groupType;
  }
  
  public void setGroupType(String groupType) {
    this.groupType = groupType;
  }
  
  public String getGroupOrder() {
    return this.groupOrder;
  }
  
  public void setGroupOrder(String groupOrder) {
    this.groupOrder = groupOrder;
  }
}
