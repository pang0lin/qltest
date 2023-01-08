package com.js.oa.scheme.taskcenter.po;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class TaskPO implements Serializable {
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
  
  private Integer taskPriority;
  
  private Integer taskStatus;
  
  private Integer taskHasPass;
  
  private Integer taskFinishRate;
  
  private Integer taskRemind;
  
  private Integer taskHasJunior;
  
  private Date taskRealEndTime = null;
  
  private Integer taskOrderCode = new Integer(0);
  
  private String parentIdString;
  
  private Long taskParentId;
  
  private Long taskBaseId;
  
  private Long createdEmp;
  
  private String createdEmpName;
  
  private Long createdOrg;
  
  private String createdOrgName;
  
  private Integer isArranged;
  
  private Set taskExecs = null;
  
  private String taskAppend;
  
  private Set taskAccessory = null;
  
  private String taskCancelReason;
  
  private String tasktimelimit;
  
  private Integer islandmark;
  
  private Integer tasksortcode;
  
  private Set project = new HashSet(0);
  
  private Long relProjectId;
  
  private Long meetingId;
  
  private String taskIdStrings;
  
  private Long stickie = Long.valueOf(0L);
  
  private Long anpaiStickie = Long.valueOf(0L);
  
  public Long getRelProjectId() {
    return this.relProjectId;
  }
  
  public void setRelProjectId(Long relProjectId) {
    this.relProjectId = relProjectId;
  }
  
  public TaskPO(String taskSn, Long taskDomainId, String taskType, String taskTitle, String taskDescription, Date taskBeginTime, Date taskEndTime, Long taskAwakeTime, Date taskCreatedTime, Long taskPrincipal, Long taskChecker, String taskJoinedEmp, String taskJoineOrg, Integer taskPriority, Integer taskStatus, Integer taskHasPass, Integer taskFinishRate, Integer taskRemind, Integer taskOrderCode, String parentIdString, Long taskParentId, Long taskBaseId, Long createdEmp, Long createdOrg, Set taskExecs, String taskPrincipalName, String taskCheckerName, String taskJoinedEmpName, String taskJoinOrgName, String createdEmpName, String createdOrgName, Integer taskHasJunior, Date taskRealEndTime, Integer isArranged, Set taskAccessory) {
    this.taskSn = taskSn;
    this.taskType = taskType;
    this.taskTitle = taskTitle;
    this.taskDescription = taskDescription;
    this.taskBeginTime = taskBeginTime;
    this.taskEndTime = taskEndTime;
    this.taskAwakeTime = taskAwakeTime;
    this.taskCreatedTime = taskCreatedTime;
    this.taskPrincipal = taskPrincipal;
    this.taskChecker = taskChecker;
    this.taskJoinedEmp = taskJoinedEmp;
    this.taskJoineOrg = taskJoineOrg;
    this.taskPriority = taskPriority;
    this.taskStatus = taskStatus;
    this.taskHasPass = taskHasPass;
    this.taskFinishRate = taskFinishRate;
    this.taskRemind = taskRemind;
    this.taskOrderCode = taskOrderCode;
    this.parentIdString = parentIdString;
    this.taskParentId = taskParentId;
    this.taskBaseId = taskBaseId;
    this.createdEmp = createdEmp;
    this.createdOrg = createdOrg;
    this.taskExecs = taskExecs;
    this.createdEmpName = createdEmpName;
    this.createdOrgName = createdOrgName;
    this.taskCheckerName = taskCheckerName;
    this.taskJoinedEmpName = taskJoinedEmpName;
    this.taskJoinOrgName = taskJoinOrgName;
    this.taskPrincipalName = taskPrincipalName;
    this.taskHasJunior = taskHasJunior;
    this.taskRealEndTime = taskRealEndTime;
    this.isArranged = isArranged;
    this.taskDomainId = taskDomainId;
    this.taskAccessory = taskAccessory;
  }
  
  public TaskPO() {}
  
  public TaskPO(String taskTitle, Long taskDomainId, Date taskBeginTime, Date taskEndTime, Date taskCreatedTime, Long taskPrincipal, Integer taskFinishRate, String parentIdString, Long createdEmp, Set taskAccessory) {
    this.taskTitle = taskTitle;
    this.taskBeginTime = taskBeginTime;
    this.taskEndTime = taskEndTime;
    this.taskCreatedTime = taskCreatedTime;
    this.taskPrincipal = taskPrincipal;
    this.taskFinishRate = taskFinishRate;
    this.parentIdString = parentIdString;
    this.createdEmp = createdEmp;
    this.taskDomainId = taskDomainId;
    this.taskAccessory = taskAccessory;
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
  
  public Long getTaskChecker() {
    return this.taskChecker;
  }
  
  public void setTaskChecker(Long taskChecker) {
    this.taskChecker = taskChecker;
  }
  
  public String getTaskJoinedEmp() {
    return this.taskJoinedEmp;
  }
  
  public void setTaskJoinedEmp(String taskJoinedEmp) {
    this.taskJoinedEmp = taskJoinedEmp;
  }
  
  public String getTaskJoineOrg() {
    return this.taskJoineOrg;
  }
  
  public void setTaskJoineOrg(String taskJoineOrg) {
    this.taskJoineOrg = taskJoineOrg;
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
  
  public Integer getTaskHasPass() {
    return this.taskHasPass;
  }
  
  public void setTaskHasPass(Integer taskHasPass) {
    this.taskHasPass = taskHasPass;
  }
  
  public Integer getTaskFinishRate() {
    return this.taskFinishRate;
  }
  
  public void setTaskFinishRate(Integer taskFinishRate) {
    this.taskFinishRate = taskFinishRate;
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
  
  public Long getCreatedOrg() {
    return this.createdOrg;
  }
  
  public void setCreatedOrg(Long createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getTaskId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof TaskPO))
      return false; 
    TaskPO castOther = (TaskPO)other;
    return (new EqualsBuilder())
      .append(getTaskId(), castOther.getTaskId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getTaskId())
      .toHashCode();
  }
  
  public Set getTaskExecs() {
    return this.taskExecs;
  }
  
  public void setTaskExecs(Set taskExecs) {
    this.taskExecs = taskExecs;
  }
  
  public Integer getTaskRemind() {
    return this.taskRemind;
  }
  
  public void setTaskRemind(Integer taskRemind) {
    this.taskRemind = taskRemind;
  }
  
  public String getCreatedEmpName() {
    return this.createdEmpName;
  }
  
  public void setCreatedEmpName(String createdEmpName) {
    this.createdEmpName = createdEmpName;
  }
  
  public String getCreatedOrgName() {
    return this.createdOrgName;
  }
  
  public void setCreatedOrgName(String createdOrgName) {
    this.createdOrgName = createdOrgName;
  }
  
  public String getTaskCheckerName() {
    return this.taskCheckerName;
  }
  
  public void setTaskCheckerName(String taskCheckerName) {
    this.taskCheckerName = taskCheckerName;
  }
  
  public String getTaskJoinedEmpName() {
    return this.taskJoinedEmpName;
  }
  
  public void setTaskJoinedEmpName(String taskJoinedEmpName) {
    this.taskJoinedEmpName = taskJoinedEmpName;
  }
  
  public String getTaskJoinOrgName() {
    return this.taskJoinOrgName;
  }
  
  public void setTaskJoinOrgName(String taskJoinOrgName) {
    this.taskJoinOrgName = taskJoinOrgName;
  }
  
  public String getTaskPrincipalName() {
    return this.taskPrincipalName;
  }
  
  public void setTaskPrincipalName(String taskPrincipalName) {
    this.taskPrincipalName = taskPrincipalName;
  }
  
  public Integer getTaskHasJunior() {
    return this.taskHasJunior;
  }
  
  public void setTaskHasJunior(Integer taskHasJunior) {
    this.taskHasJunior = taskHasJunior;
  }
  
  public Date getTaskRealEndTime() {
    return this.taskRealEndTime;
  }
  
  public void setTaskRealEndTime(Date taskRealEndTime) {
    this.taskRealEndTime = taskRealEndTime;
  }
  
  public Integer getIsArranged() {
    return this.isArranged;
  }
  
  public void setIsArranged(Integer isArranged) {
    this.isArranged = isArranged;
  }
  
  public String getTaskAppend() {
    return this.taskAppend;
  }
  
  public void setTaskAppend(String taskAppend) {
    this.taskAppend = taskAppend;
  }
  
  public Set getTaskAccessory() {
    return this.taskAccessory;
  }
  
  public String getTaskCancelReason() {
    return this.taskCancelReason;
  }
  
  public Integer getIslandmark() {
    return this.islandmark;
  }
  
  public Set getProject() {
    return this.project;
  }
  
  public Integer getTasksortcode() {
    return this.tasksortcode;
  }
  
  public String getTasktimelimit() {
    return this.tasktimelimit;
  }
  
  public void setTaskAccessory(Set taskAccessory) {
    this.taskAccessory = taskAccessory;
  }
  
  public void setTaskCancelReason(String taskCancelReason) {
    this.taskCancelReason = taskCancelReason;
  }
  
  public void setIslandmark(Integer islandmark) {
    this.islandmark = islandmark;
  }
  
  public void setProject(Set project) {
    this.project = project;
  }
  
  public void setTasksortcode(Integer tasksortcode) {
    this.tasksortcode = tasksortcode;
  }
  
  public void setTasktimelimit(String tasktimelimit) {
    this.tasktimelimit = tasktimelimit;
  }
  
  public Long getMeetingId() {
    return this.meetingId;
  }
  
  public void setMeetingId(Long meetingId) {
    this.meetingId = meetingId;
  }
  
  public String getTaskIdStrings() {
    return this.taskIdStrings;
  }
  
  public void setTaskIdStrings(String taskIdStrings) {
    this.taskIdStrings = taskIdStrings;
  }
  
  public Long getStickie() {
    return this.stickie;
  }
  
  public void setStickie(Long stickie) {
    this.stickie = stickie;
  }
  
  public Long getAnpaiStickie() {
    return this.anpaiStickie;
  }
  
  public void setAnpaiStickie(Long anpaiStickie) {
    this.anpaiStickie = anpaiStickie;
  }
}
