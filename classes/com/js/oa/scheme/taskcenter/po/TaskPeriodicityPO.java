package com.js.oa.scheme.taskcenter.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class TaskPeriodicityPO implements Serializable {
  private Long periodicityId;
  
  private Long domainId;
  
  private String taskType;
  
  private String taskTitle;
  
  private String taskDescription;
  
  private Date taskCreatedTime = null;
  
  private Long taskPrincipal;
  
  private String taskPrincipalName;
  
  private Long taskChecker;
  
  private String taskCheckerName;
  
  private String taskJoinedEmp;
  
  private String taskJoinedEmpName;
  
  private String taskJoineOrg;
  
  private String taskJoinOrgName;
  
  private Integer taskPriority;
  
  private Integer onTimeMode;
  
  private String onTimeContent;
  
  private Long createdEmp;
  
  private String createdEmpName;
  
  private Long createdOrg;
  
  private String createdOrgName;
  
  private Set taskAccessory = null;
  
  public TaskPeriodicityPO(Long domainId, String taskType, String taskTitle, String taskDescription, Date taskCreatedTime, Long taskPrincipal, Long taskChecker, String taskJoinedEmp, String taskJoineOrg, Integer taskPriority, Integer onTimeMode, String onTimeContent, Long createdEmp, Long createdOrg, String createdEmpName, String createdOrgName, Set taskAccessory) {
    this.taskType = taskType;
    this.taskTitle = taskTitle;
    this.taskDescription = taskDescription;
    this.taskCreatedTime = taskCreatedTime;
    this.taskPrincipal = taskPrincipal;
    this.taskChecker = taskChecker;
    this.taskJoinedEmp = taskJoinedEmp;
    this.taskJoineOrg = taskJoineOrg;
    this.taskPriority = taskPriority;
    this.onTimeMode = onTimeMode;
    this.onTimeContent = onTimeContent;
    this.createdEmp = createdEmp;
    this.createdOrg = createdOrg;
    this.createdEmpName = createdEmpName;
    this.createdOrgName = createdOrgName;
    this.taskCheckerName = this.taskCheckerName;
    this.taskJoinedEmpName = this.taskJoinedEmpName;
    this.taskJoinOrgName = this.taskJoinOrgName;
    this.taskPrincipalName = this.taskPrincipalName;
    this.domainId = domainId;
    this.taskAccessory = taskAccessory;
  }
  
  public TaskPeriodicityPO() {}
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getPeriodicityId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof TaskPeriodicityPO))
      return false; 
    TaskPeriodicityPO castOther = (TaskPeriodicityPO)other;
    return (new EqualsBuilder())
      .append(getPeriodicityId(), castOther.getPeriodicityId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getPeriodicityId())
      .toHashCode();
  }
  
  public Long getCreatedEmp() {
    return this.createdEmp;
  }
  
  public void setCreatedEmp(Long createdEmp) {
    this.createdEmp = createdEmp;
  }
  
  public String getCreatedEmpName() {
    return this.createdEmpName;
  }
  
  public void setCreatedEmpName(String createdEmpName) {
    this.createdEmpName = createdEmpName;
  }
  
  public Long getCreatedOrg() {
    return this.createdOrg;
  }
  
  public void setCreatedOrg(Long createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public String getCreatedOrgName() {
    return this.createdOrgName;
  }
  
  public void setCreatedOrgName(String createdOrgName) {
    this.createdOrgName = createdOrgName;
  }
  
  public Long getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }
  
  public Long getPeriodicityId() {
    return this.periodicityId;
  }
  
  public void setPeriodicityId(Long periodicityId) {
    this.periodicityId = periodicityId;
  }
  
  public Long getTaskChecker() {
    return this.taskChecker;
  }
  
  public void setTaskChecker(Long taskChecker) {
    this.taskChecker = taskChecker;
  }
  
  public String getTaskCheckerName() {
    return this.taskCheckerName;
  }
  
  public void setTaskCheckerName(String taskCheckerName) {
    this.taskCheckerName = taskCheckerName;
  }
  
  public Date getTaskCreatedTime() {
    return this.taskCreatedTime;
  }
  
  public void setTaskCreatedTime(Date taskCreatedTime) {
    this.taskCreatedTime = taskCreatedTime;
  }
  
  public String getTaskDescription() {
    return this.taskDescription;
  }
  
  public void setTaskDescription(String taskDescription) {
    this.taskDescription = taskDescription;
  }
  
  public String getTaskJoinedEmp() {
    return this.taskJoinedEmp;
  }
  
  public void setTaskJoinedEmp(String taskJoinedEmp) {
    this.taskJoinedEmp = taskJoinedEmp;
  }
  
  public String getTaskJoinedEmpName() {
    return this.taskJoinedEmpName;
  }
  
  public void setTaskJoinedEmpName(String taskJoinedEmpName) {
    this.taskJoinedEmpName = taskJoinedEmpName;
  }
  
  public String getTaskJoineOrg() {
    return this.taskJoineOrg;
  }
  
  public void setTaskJoineOrg(String taskJoineOrg) {
    this.taskJoineOrg = taskJoineOrg;
  }
  
  public String getTaskJoinOrgName() {
    return this.taskJoinOrgName;
  }
  
  public void setTaskJoinOrgName(String taskJoinOrgName) {
    this.taskJoinOrgName = taskJoinOrgName;
  }
  
  public Long getTaskPrincipal() {
    return this.taskPrincipal;
  }
  
  public void setTaskPrincipal(Long taskPrincipal) {
    this.taskPrincipal = taskPrincipal;
  }
  
  public String getTaskPrincipalName() {
    return this.taskPrincipalName;
  }
  
  public void setTaskPrincipalName(String taskPrincipalName) {
    this.taskPrincipalName = taskPrincipalName;
  }
  
  public Integer getTaskPriority() {
    return this.taskPriority;
  }
  
  public void setTaskPriority(Integer taskPriority) {
    this.taskPriority = taskPriority;
  }
  
  public String getTaskTitle() {
    return this.taskTitle;
  }
  
  public void setTaskTitle(String taskTitle) {
    this.taskTitle = taskTitle;
  }
  
  public String getTaskType() {
    return this.taskType;
  }
  
  public void setTaskType(String taskType) {
    this.taskType = taskType;
  }
  
  public String getOnTimeContent() {
    return this.onTimeContent;
  }
  
  public void setOnTimeContent(String onTimeContent) {
    this.onTimeContent = onTimeContent;
  }
  
  public Integer getOnTimeMode() {
    return this.onTimeMode;
  }
  
  public void setOnTimeMode(Integer onTimeMode) {
    this.onTimeMode = onTimeMode;
  }
  
  public Set getTaskAccessory() {
    return this.taskAccessory;
  }
  
  public void setTaskAccessory(Set taskAccessory) {
    this.taskAccessory = taskAccessory;
  }
}
