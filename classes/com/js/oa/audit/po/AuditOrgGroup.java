package com.js.oa.audit.po;

import java.io.Serializable;

public class AuditOrgGroup implements Serializable {
  private Long auditOrgGroupId;
  
  private Long groupId;
  
  private String groupName;
  
  private String groupdescription;
  
  private Long createdorg;
  
  private Long createdemp;
  
  private String userrange;
  
  private String groupUserNames;
  
  private String groupUserString;
  
  private String rangename;
  
  private String rangeemp;
  
  private String rangeorg;
  
  private String rangegroup;
  
  private Long domainId;
  
  private Long groupType;
  
  private String groupOrder;
  
  private Long auditLogId;
  
  private String operationType;
  
  public AuditOrgGroup() {}
  
  public AuditOrgGroup(Long auditOrgGroupId) {
    this.auditOrgGroupId = auditOrgGroupId;
  }
  
  public AuditOrgGroup(Long auditOrgGroupId, Long groupId, String groupName, String groupdescription, Long createdorg, Long createdemp, String userrange, String groupusernames, String groupuserstring, String rangename, String rangeemp, String rangeorg, String rangegroup, Long domainId, Long groupType, String groupOrder, Long auditLogId, String operationType) {
    this.auditOrgGroupId = auditOrgGroupId;
    this.groupId = groupId;
    this.groupName = groupName;
    this.groupdescription = groupdescription;
    this.createdorg = createdorg;
    this.createdemp = createdemp;
    this.userrange = userrange;
    this.groupUserNames = groupusernames;
    this.groupUserString = groupuserstring;
    this.rangename = rangename;
    this.rangeemp = rangeemp;
    this.rangeorg = rangeorg;
    this.rangegroup = rangegroup;
    this.domainId = domainId;
    this.groupType = groupType;
    this.groupOrder = groupOrder;
    this.auditLogId = auditLogId;
    this.operationType = operationType;
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
  
  public String getGroupdescription() {
    return this.groupdescription;
  }
  
  public void setGroupdescription(String groupdescription) {
    this.groupdescription = groupdescription;
  }
  
  public Long getCreatedorg() {
    return this.createdorg;
  }
  
  public void setCreatedorg(Long createdorg) {
    this.createdorg = createdorg;
  }
  
  public Long getCreatedemp() {
    return this.createdemp;
  }
  
  public void setCreatedemp(Long createdemp) {
    this.createdemp = createdemp;
  }
  
  public String getUserrange() {
    return this.userrange;
  }
  
  public void setUserrange(String userrange) {
    this.userrange = userrange;
  }
  
  public String getGroupUserNames() {
    return this.groupUserNames;
  }
  
  public void setGroupUserNames(String groupUserNames) {
    this.groupUserNames = groupUserNames;
  }
  
  public String getGroupUserString() {
    return this.groupUserString;
  }
  
  public void setGroupUserString(String groupUserString) {
    this.groupUserString = groupUserString;
  }
  
  public String getRangename() {
    return this.rangename;
  }
  
  public void setRangename(String rangename) {
    this.rangename = rangename;
  }
  
  public String getRangeemp() {
    return this.rangeemp;
  }
  
  public void setRangeemp(String rangeemp) {
    this.rangeemp = rangeemp;
  }
  
  public String getRangeorg() {
    return this.rangeorg;
  }
  
  public void setRangeorg(String rangeorg) {
    this.rangeorg = rangeorg;
  }
  
  public String getRangegroup() {
    return this.rangegroup;
  }
  
  public void setRangegroup(String rangegroup) {
    this.rangegroup = rangegroup;
  }
  
  public Long getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }
  
  public Long getGroupType() {
    return this.groupType;
  }
  
  public void setGroupType(Long groupType) {
    this.groupType = groupType;
  }
  
  public String getGroupOrder() {
    return this.groupOrder;
  }
  
  public void setGroupOrder(String groupOrder) {
    this.groupOrder = groupOrder;
  }
  
  public Long getAuditLogId() {
    return this.auditLogId;
  }
  
  public void setAuditLogId(Long auditLogId) {
    this.auditLogId = auditLogId;
  }
  
  public String getOperationType() {
    return this.operationType;
  }
  
  public void setOperationType(String operationType) {
    this.operationType = operationType;
  }
  
  public Long getAuditOrgGroupId() {
    return this.auditOrgGroupId;
  }
  
  public void setAuditOrgGroupId(Long auditOrgGroupId) {
    this.auditOrgGroupId = auditOrgGroupId;
  }
}
