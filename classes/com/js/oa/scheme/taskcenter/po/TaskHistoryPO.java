package com.js.oa.scheme.taskcenter.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class TaskHistoryPO implements Serializable {
  private Long taskhisId;
  
  private Long taskId;
  
  private Long taskDomainId;
  
  private String taskSn;
  
  private String taskType;
  
  private String taskTitle;
  
  private String taskDescription;
  
  private Date taskBeginTime = null;
  
  private Date taskEndTime = null;
  
  private Long taskAwakeTime;
  
  private Date taskCreatedTime = null;
  
  private Long taskPrincipal;
  
  private String taskPrincipalName;
  
  private Long taskChecker;
  
  private String taskCheckerName;
  
  private String taskJoinedEmp;
  
  private String taskJoinedEmpName;
  
  private String taskJoineOrg;
  
  private String taskJoinOrgName;
  
  private Integer taskStatus;
  
  private Integer taskFinishRate;
  
  private Integer taskRemind;
  
  private Integer taskHasJunior;
  
  private Integer isArranged;
  
  private Integer taskOrderCode;
  
  private String parentIdString;
  
  private Long taskParentId;
  
  private Long taskBaseId;
  
  private Long createdEmp;
  
  private String createdEmpName;
  
  private Long createdOrg;
  
  private String createdOrgName;
  
  private Date taskRealEndTime = null;
  
  private String taskAppend;
  
  private Integer taskPriority;
  
  private Set inforHistoryAccessory = null;
  
  private Integer taskHasPass;
  
  private Date taskModiTime = null;
  
  private String taskModiUser;
  
  private String taskModiUserId;
  
  private String taskModiOrgId;
  
  private String taskModiOrgName;
  
  public Long getTaskhisId() {
    return this.taskhisId;
  }
  
  public void setTaskhisId(Long taskhisId) {
    this.taskhisId = taskhisId;
  }
  
  public Long getTaskId() {
    return this.taskId;
  }
  
  public void setTaskId(Long taskId) {
    this.taskId = taskId;
  }
  
  public Long getTaskDomainId() {
    return this.taskDomainId;
  }
  
  public void setTaskDomainId(Long taskDomainId) {
    this.taskDomainId = taskDomainId;
  }
  
  public String getTaskSn() {
    return this.taskSn;
  }
  
  public void setTaskSn(String taskSn) {
    this.taskSn = taskSn;
  }
  
  public String getTaskType() {
    return this.taskType;
  }
  
  public void setTaskType(String taskType) {
    this.taskType = taskType;
  }
  
  public String getTaskTitle() {
    return this.taskTitle;
  }
  
  public void setTaskTitle(String taskTitle) {
    this.taskTitle = taskTitle;
  }
  
  public String getTaskDescription() {
    return this.taskDescription;
  }
  
  public void setTaskDescription(String taskDescription) {
    this.taskDescription = taskDescription;
  }
  
  public Date getTaskBeginTime() {
    return this.taskBeginTime;
  }
  
  public void setTaskBeginTime(Date taskBeginTime) {
    this.taskBeginTime = taskBeginTime;
  }
  
  public Date getTaskEndTime() {
    return this.taskEndTime;
  }
  
  public void setTaskEndTime(Date taskEndTime) {
    this.taskEndTime = taskEndTime;
  }
  
  public Long getTaskAwakeTime() {
    return this.taskAwakeTime;
  }
  
  public void setTaskAwakeTime(Long taskAwakeTime) {
    this.taskAwakeTime = taskAwakeTime;
  }
  
  public Date getTaskCreatedTime() {
    return this.taskCreatedTime;
  }
  
  public void setTaskCreatedTime(Date taskCreatedTime) {
    this.taskCreatedTime = taskCreatedTime;
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
  
  public Integer getTaskPriority() {
    return this.taskPriority;
  }
  
  public void setTaskPriority(Integer taskPriority) {
    this.taskPriority = taskPriority;
  }
  
  public Integer getTaskStatus() {
    return this.taskStatus;
  }
  
  public void setTaskStatus(Integer taskStatus) {
    this.taskStatus = taskStatus;
  }
  
  public Integer getTaskFinishRate() {
    return this.taskFinishRate;
  }
  
  public void setTaskFinishRate(Integer taskFinishRate) {
    this.taskFinishRate = taskFinishRate;
  }
  
  public Integer getTaskRemind() {
    return this.taskRemind;
  }
  
  public void setTaskRemind(Integer taskRemind) {
    this.taskRemind = taskRemind;
  }
  
  public Integer getTaskHasJunior() {
    return this.taskHasJunior;
  }
  
  public void setTaskHasJunior(Integer taskHasJunior) {
    this.taskHasJunior = taskHasJunior;
  }
  
  public Integer getIsArranged() {
    return this.isArranged;
  }
  
  public void setIsArranged(Integer isArranged) {
    this.isArranged = isArranged;
  }
  
  public Integer getTaskOrderCode() {
    return this.taskOrderCode;
  }
  
  public void setTaskOrderCode(Integer taskOrderCode) {
    this.taskOrderCode = taskOrderCode;
  }
  
  public String getParentIdString() {
    return this.parentIdString;
  }
  
  public void setParentIdString(String parentIdString) {
    this.parentIdString = parentIdString;
  }
  
  public Long getTaskParentId() {
    return this.taskParentId;
  }
  
  public void setTaskParentId(Long taskParentId) {
    this.taskParentId = taskParentId;
  }
  
  public Long getTaskBaseId() {
    return this.taskBaseId;
  }
  
  public void setTaskBaseId(Long taskBaseId) {
    this.taskBaseId = taskBaseId;
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
  
  public Date getTaskRealEndTime() {
    return this.taskRealEndTime;
  }
  
  public void setTaskRealEndTime(Date taskRealEndTime) {
    this.taskRealEndTime = taskRealEndTime;
  }
  
  public String getTaskAppend() {
    return this.taskAppend;
  }
  
  public void setTaskAppend(String taskAppend) {
    this.taskAppend = taskAppend;
  }
  
  public Set getInforHistoryAccessory() {
    return this.inforHistoryAccessory;
  }
  
  public void setInforHistoryAccessory(Set inforHistoryAccessory) {
    this.inforHistoryAccessory = inforHistoryAccessory;
  }
  
  public Integer getTaskHasPass() {
    return this.taskHasPass;
  }
  
  public void setTaskHasPass(Integer taskHasPass) {
    this.taskHasPass = taskHasPass;
  }
  
  public Date getTaskModiTime() {
    return this.taskModiTime;
  }
  
  public void setTaskModiTime(Date taskModiTime) {
    this.taskModiTime = taskModiTime;
  }
  
  public String getTaskModiUser() {
    return this.taskModiUser;
  }
  
  public void setTaskModiUser(String taskModiUser) {
    this.taskModiUser = taskModiUser;
  }
  
  public String getTaskModiUserId() {
    return this.taskModiUserId;
  }
  
  public void setTaskModiUserId(String taskModiUserId) {
    this.taskModiUserId = taskModiUserId;
  }
  
  public String getTaskModiOrgId() {
    return this.taskModiOrgId;
  }
  
  public void setTaskModiOrgId(String taskModiOrgId) {
    this.taskModiOrgId = taskModiOrgId;
  }
  
  public String getTaskModiOrgName() {
    return this.taskModiOrgName;
  }
  
  public void setTaskModiOrgName(String taskModiOrgName) {
    this.taskModiOrgName = taskModiOrgName;
  }
}
