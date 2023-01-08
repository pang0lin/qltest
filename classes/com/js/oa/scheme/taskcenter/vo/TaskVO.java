package com.js.oa.scheme.taskcenter.vo;

import com.js.oa.scheme.taskcenter.po.TaskPO;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class TaskVO implements Serializable {
  private Long taskId;
  
  private Long taskDomainId;
  
  private String taskSn;
  
  private String taskType;
  
  private String taskTitle;
  
  private String taskDescription;
  
  private Date taskBeginTime = null;
  
  private String taskBeginTimeFormat;
  
  private Date taskEndTime = null;
  
  private String taskEndTimeFormat;
  
  private Long taskAwakeTime;
  
  private Date taskCreatedTime = null;
  
  private String taskCreatedTimeFormat;
  
  private Date taskRealEndTime = null;
  
  private String taskRealEndTimeFormat;
  
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
  
  private Integer taskOrderCode;
  
  private String parentIdString;
  
  private Long taskParentId;
  
  private Long taskBaseId;
  
  private Long createdEmp;
  
  private String createdEmpName;
  
  private Integer isArranged;
  
  private Long createdOrg;
  
  private String createdOrgName;
  
  private Boolean maintenance;
  
  private Boolean see;
  
  private Boolean canCancel;
  
  private Boolean canReport;
  
  private Boolean canDelete;
  
  private Boolean canNew;
  
  private String taskAppend;
  
  private TaskPO taskPO = null;
  
  private String taskTypeShow;
  
  private String taskCancelReason;
  
  private String tasktimelimit;
  
  private Integer islandmark;
  
  private Integer tasksortcode;
  
  private Long stickie;
  
  private Long anpaiStickie;
  
  private Set project = new HashSet(0);
  
  private Long meetingId;
  
  private Long relProjectId;
  
  public TaskVO() {}
  
  public TaskVO(Long taskId, Long taskDomainId, String taskSn, String taskType, String taskTitle, String taskDescription, Date taskBeginTime, Date taskEndTime, Long taskAwakeTime, Date taskCreatedTime, Long taskPrincipal, String taskPrincipalName, Long taskChecker, String taskCheckerName, String taskJoinedEmp, String taskJoinedEmpName, String taskJoineOrg, String taskJoinOrgName, Integer taskPriority, Integer taskStatus, Integer taskHasPass, Integer taskFinishRate, Integer taskRemind, Integer taskOrderCode, String parentIdString, Long taskParentId, Long taskBaseId, Long createdEmp, String createdEmpName, Long createdOrg, String createdOrgName, Boolean maintenance, Boolean see, Boolean canCancel, Boolean canReport, Boolean canDelete, Boolean canNew, TaskPO taskPO, String taskTypeShow, Long relProjectId, Long meetingId) {
    this.taskId = taskId;
    this.taskSn = taskSn;
    this.taskType = taskType;
    this.taskTitle = taskTitle;
    this.taskDescription = taskDescription;
    this.taskBeginTime = taskBeginTime;
    this.taskEndTime = taskEndTime;
    this.taskAwakeTime = taskAwakeTime;
    this.taskCreatedTime = taskCreatedTime;
    this.taskPrincipal = taskPrincipal;
    this.taskPrincipalName = taskPrincipalName;
    this.taskChecker = taskChecker;
    this.taskCheckerName = taskCheckerName;
    this.taskJoinedEmp = taskJoinedEmp;
    this.taskJoinedEmpName = taskJoinedEmpName;
    this.taskJoineOrg = taskJoineOrg;
    this.taskJoinOrgName = taskJoinOrgName;
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
    this.createdEmpName = createdEmpName;
    this.createdOrg = createdOrg;
    this.createdOrgName = createdOrgName;
    this.maintenance = maintenance;
    this.see = see;
    this.canCancel = canCancel;
    this.canReport = canReport;
    this.canDelete = canDelete;
    this.canNew = canNew;
    this.taskDomainId = taskDomainId;
    this.taskPO = taskPO;
    this.taskTypeShow = taskTypeShow;
    this.relProjectId = relProjectId;
    this.meetingId = meetingId;
  }
  
  public Long getRelProjectId() {
    return this.relProjectId;
  }
  
  public void setRelProjectId(Long relProjectId) {
    this.relProjectId = relProjectId;
  }
  
  public Long getMeetingId() {
    return this.meetingId;
  }
  
  public void setMeetingId(Long meetingId) {
    this.meetingId = meetingId;
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
  
  public String getTaskBeginTimeFormat() {
    return dateToString(getTaskBeginTime());
  }
  
  public Date getTaskEndTime() {
    return this.taskEndTime;
  }
  
  public String getTaskEndTimeFormat() {
    return dateToString(getTaskEndTime());
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
  
  public String getTaskCreatedTimeFormat() {
    return dateToString(getTaskCreatedTime());
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
  
  public Integer getTaskRemind() {
    return this.taskRemind;
  }
  
  public void setTaskRemind(Integer taskRemind) {
    this.taskRemind = taskRemind;
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
  
  public Boolean getMaintenance() {
    return this.maintenance;
  }
  
  public void setMaintenance(Boolean maintenance) {
    this.maintenance = maintenance;
  }
  
  public Boolean getSee() {
    return this.see;
  }
  
  public void setSee(Boolean see) {
    this.see = see;
  }
  
  public Boolean getCanCancel() {
    return this.canCancel;
  }
  
  public void setCanCancel(Boolean canCancel) {
    this.canCancel = canCancel;
  }
  
  public boolean equals(Object o) {
    if (this == o)
      return true; 
    if (!(o instanceof TaskVO))
      return false; 
    TaskVO taskVO = (TaskVO)o;
    if ((this.canCancel != null) ? !this.canCancel.equals(taskVO.canCancel) : (taskVO.canCancel != null))
      return false; 
    if ((this.createdEmp != null) ? !this.createdEmp.equals(taskVO.createdEmp) : (taskVO.createdEmp != null))
      return false; 
    if ((this.createdEmpName != null) ? !this.createdEmpName.equals(taskVO.createdEmpName) : (taskVO.createdEmpName != null))
      return false; 
    if ((this.createdOrg != null) ? !this.createdOrg.equals(taskVO.createdOrg) : (taskVO.createdOrg != null))
      return false; 
    if ((this.createdOrgName != null) ? !this.createdOrgName.equals(taskVO.createdOrgName) : (taskVO.createdOrgName != null))
      return false; 
    if ((this.maintenance != null) ? !this.maintenance.equals(taskVO.maintenance) : (taskVO.maintenance != null))
      return false; 
    if ((this.parentIdString != null) ? !this.parentIdString.equals(taskVO.parentIdString) : (taskVO.parentIdString != null))
      return false; 
    if ((this.see != null) ? !this.see.equals(taskVO.see) : (taskVO.see != null))
      return false; 
    if ((this.taskAwakeTime != null) ? !this.taskAwakeTime.equals(taskVO.taskAwakeTime) : (taskVO.taskAwakeTime != null))
      return false; 
    if ((this.taskBaseId != null) ? !this.taskBaseId.equals(taskVO.taskBaseId) : (taskVO.taskBaseId != null))
      return false; 
    if ((this.taskBeginTime != null) ? !this.taskBeginTime.equals(taskVO.taskBeginTime) : (taskVO.taskBeginTime != null))
      return false; 
    if ((this.taskChecker != null) ? !this.taskChecker.equals(taskVO.taskChecker) : (taskVO.taskChecker != null))
      return false; 
    if ((this.taskCheckerName != null) ? !this.taskCheckerName.equals(taskVO.taskCheckerName) : (taskVO.taskCheckerName != null))
      return false; 
    if ((this.taskCreatedTime != null) ? !this.taskCreatedTime.equals(taskVO.taskCreatedTime) : (taskVO.taskCreatedTime != null))
      return false; 
    if ((this.taskDescription != null) ? !this.taskDescription.equals(taskVO.taskDescription) : (taskVO.taskDescription != null))
      return false; 
    if ((this.taskEndTime != null) ? !this.taskEndTime.equals(taskVO.taskEndTime) : (taskVO.taskEndTime != null))
      return false; 
    if ((this.taskFinishRate != null) ? !this.taskFinishRate.equals(taskVO.taskFinishRate) : (taskVO.taskFinishRate != null))
      return false; 
    if ((this.isArranged != null) ? !this.isArranged.equals(taskVO.isArranged) : (taskVO.isArranged != null))
      return false; 
    if ((this.taskHasPass != null) ? !this.taskHasPass.equals(taskVO.taskHasPass) : (taskVO.taskHasPass != null))
      return false; 
    if (!this.taskId.equals(taskVO.taskId))
      return false; 
    if ((this.taskJoinOrgName != null) ? !this.taskJoinOrgName.equals(taskVO.taskJoinOrgName) : (taskVO.taskJoinOrgName != null))
      return false; 
    if ((this.taskJoineOrg != null) ? !this.taskJoineOrg.equals(taskVO.taskJoineOrg) : (taskVO.taskJoineOrg != null))
      return false; 
    if ((this.taskJoinedEmp != null) ? !this.taskJoinedEmp.equals(taskVO.taskJoinedEmp) : (taskVO.taskJoinedEmp != null))
      return false; 
    if ((this.taskJoinedEmpName != null) ? !this.taskJoinedEmpName.equals(taskVO.taskJoinedEmpName) : (taskVO.taskJoinedEmpName != null))
      return false; 
    if ((this.taskOrderCode != null) ? !this.taskOrderCode.equals(taskVO.taskOrderCode) : (taskVO.taskOrderCode != null))
      return false; 
    if ((this.taskParentId != null) ? !this.taskParentId.equals(taskVO.taskParentId) : (taskVO.taskParentId != null))
      return false; 
    if ((this.taskPrincipal != null) ? !this.taskPrincipal.equals(taskVO.taskPrincipal) : (taskVO.taskPrincipal != null))
      return false; 
    if ((this.taskPrincipalName != null) ? !this.taskPrincipalName.equals(taskVO.taskPrincipalName) : (taskVO.taskPrincipalName != null))
      return false; 
    if ((this.taskPriority != null) ? !this.taskPriority.equals(taskVO.taskPriority) : (taskVO.taskPriority != null))
      return false; 
    if ((this.taskRemind != null) ? !this.taskRemind.equals(taskVO.taskRemind) : (taskVO.taskRemind != null))
      return false; 
    if ((this.taskSn != null) ? !this.taskSn.equals(taskVO.taskSn) : (taskVO.taskSn != null))
      return false; 
    if ((this.taskStatus != null) ? !this.taskStatus.equals(taskVO.taskStatus) : (taskVO.taskStatus != null))
      return false; 
    if ((this.taskTitle != null) ? !this.taskTitle.equals(taskVO.taskTitle) : (taskVO.taskTitle != null))
      return false; 
    if ((this.taskType != null) ? !this.taskType.equals(taskVO.taskType) : (taskVO.taskType != null))
      return false; 
    if ((this.taskHasJunior != null) ? !this.taskHasJunior.equals(taskVO.taskHasJunior) : (taskVO.taskHasJunior != null))
      return false; 
    if ((this.taskRealEndTime != null) ? !this.taskRealEndTime.equals(taskVO.taskRealEndTime) : (taskVO.taskRealEndTime != null))
      return false; 
    if ((this.canReport != null) ? !this.canReport.equals(taskVO.canReport) : (taskVO.canReport != null))
      return false; 
    if ((this.canDelete != null) ? !this.canDelete.equals(taskVO.canDelete) : (taskVO.canDelete != null))
      return false; 
    if ((this.canNew != null) ? !this.canNew.equals(taskVO.canNew) : (taskVO.canNew != null))
      return false; 
    if ((this.taskDomainId != null) ? !this.taskDomainId.equals(taskVO.taskDomainId) : (taskVO.taskDomainId != null))
      return false; 
    if ((this.taskPO != null) ? !this.taskPO.equals(taskVO.taskPO) : (taskVO.taskPO != null))
      return false; 
    if ((this.taskTypeShow != null) ? !this.taskTypeShow.equals(taskVO.taskTypeShow) : (taskVO.taskTypeShow != null))
      return false; 
    return true;
  }
  
  public int hashCode() {
    int result = this.taskId.hashCode();
    result = 29 * result + ((this.taskSn != null) ? this.taskSn.hashCode() : 0);
    result = 29 * result + ((this.taskType != null) ? this.taskType.hashCode() : 0);
    result = 29 * result + ((this.taskTitle != null) ? this.taskTitle.hashCode() : 0);
    result = 29 * result + ((this.taskDescription != null) ? this.taskDescription.hashCode() : 0);
    result = 29 * result + ((this.taskBeginTime != null) ? this.taskBeginTime.hashCode() : 0);
    result = 29 * result + ((this.taskEndTime != null) ? this.taskEndTime.hashCode() : 0);
    result = 29 * result + ((this.taskAwakeTime != null) ? this.taskAwakeTime.hashCode() : 0);
    result = 29 * result + ((this.taskCreatedTime != null) ? this.taskCreatedTime.hashCode() : 0);
    result = 29 * result + ((this.taskPrincipal != null) ? this.taskPrincipal.hashCode() : 0);
    result = 29 * result + ((this.isArranged != null) ? this.isArranged.hashCode() : 0);
    result = 29 * result + ((this.taskPrincipalName != null) ? this.taskPrincipalName.hashCode() : 0);
    result = 29 * result + ((this.taskChecker != null) ? this.taskChecker.hashCode() : 0);
    result = 29 * result + ((this.taskCheckerName != null) ? this.taskCheckerName.hashCode() : 0);
    result = 29 * result + ((this.taskJoinedEmp != null) ? this.taskJoinedEmp.hashCode() : 0);
    result = 29 * result + ((this.taskJoinedEmpName != null) ? this.taskJoinedEmpName.hashCode() : 0);
    result = 29 * result + ((this.taskJoineOrg != null) ? this.taskJoineOrg.hashCode() : 0);
    result = 29 * result + ((this.taskJoinOrgName != null) ? this.taskJoinOrgName.hashCode() : 0);
    result = 29 * result + ((this.taskPriority != null) ? this.taskPriority.hashCode() : 0);
    result = 29 * result + ((this.taskStatus != null) ? this.taskStatus.hashCode() : 0);
    result = 29 * result + ((this.taskHasPass != null) ? this.taskHasPass.hashCode() : 0);
    result = 29 * result + ((this.taskFinishRate != null) ? this.taskFinishRate.hashCode() : 0);
    result = 29 * result + ((this.taskRemind != null) ? this.taskRemind.hashCode() : 0);
    result = 29 * result + ((this.taskOrderCode != null) ? this.taskOrderCode.hashCode() : 0);
    result = 29 * result + ((this.parentIdString != null) ? this.parentIdString.hashCode() : 0);
    result = 29 * result + ((this.taskParentId != null) ? this.taskParentId.hashCode() : 0);
    result = 29 * result + ((this.taskBaseId != null) ? this.taskBaseId.hashCode() : 0);
    result = 29 * result + ((this.createdEmp != null) ? this.createdEmp.hashCode() : 0);
    result = 29 * result + ((this.createdEmpName != null) ? this.createdEmpName.hashCode() : 0);
    result = 29 * result + ((this.createdOrg != null) ? this.createdOrg.hashCode() : 0);
    result = 29 * result + ((this.createdOrgName != null) ? this.createdOrgName.hashCode() : 0);
    result = 29 * result + ((this.maintenance != null) ? this.maintenance.hashCode() : 0);
    result = 29 * result + ((this.see != null) ? this.see.hashCode() : 0);
    result = 29 * result + ((this.canCancel != null) ? this.canCancel.hashCode() : 0);
    result = 29 * result + ((this.canReport != null) ? this.canReport.hashCode() : 0);
    result = 29 * result + ((this.taskHasJunior != null) ? this.taskHasJunior.hashCode() : 0);
    result = 29 * result + ((this.taskRealEndTime != null) ? this.taskRealEndTime.hashCode() : 0);
    result = 29 * result + ((this.canDelete != null) ? this.canDelete.hashCode() : 0);
    result = 29 * result + ((this.canNew != null) ? this.canNew.hashCode() : 0);
    result = 29 * result + ((this.taskAppend != null) ? this.taskAppend.hashCode() : 0);
    result = 29 * result + ((this.taskDomainId != null) ? this.taskDomainId.hashCode() : 0);
    result = 29 * result + ((this.taskPO != null) ? this.taskPO.hashCode() : 0);
    result = 29 * result + ((this.taskTypeShow != null) ? this.taskTypeShow.hashCode() : 0);
    return result;
  }
  
  public String toString() {
    return "TaskVO{taskId=" + 
      this.taskId + 
      ", taskDomainId=" + this.taskDomainId + 
      ", taskSn='" + this.taskSn + "'" + 
      ", taskType='" + this.taskType + "'" + 
      ", taskTitle='" + this.taskTitle + "'" + 
      ", taskDescription='" + this.taskDescription + "'" + 
      ", taskBeginTime=" + this.taskBeginTime + 
      ", taskEndTime=" + this.taskEndTime + 
      ", taskAwakeTime=" + this.taskAwakeTime + 
      ", taskCreatedTime=" + this.taskCreatedTime + 
      ", taskPrincipal=" + this.taskPrincipal + 
      ", taskPrincipalName='" + this.taskPrincipalName + "'" + 
      ", taskChecker=" + this.taskChecker + 
      ", taskCheckerName='" + this.taskCheckerName + "'" + 
      ", taskJoinedEmp='" + this.taskJoinedEmp + "'" + 
      ", taskJoinedEmpName='" + this.taskJoinedEmpName + "'" + 
      ", taskJoineOrg='" + this.taskJoineOrg + "'" + 
      ", taskJoinOrgName='" + this.taskJoinOrgName + "'" + 
      ", taskPriority=" + this.taskPriority + 
      ", taskStatus=" + this.taskStatus + 
      ", taskHasPass=" + this.taskHasPass + 
      ", taskFinishRate=" + this.taskFinishRate + 
      ", taskRemind=" + this.taskRemind + 
      ", taskOrderCode=" + this.taskOrderCode + 
      ", parentIdString='" + this.parentIdString + "'" + 
      ", taskParentId=" + this.taskParentId + 
      ", taskBaseId=" + this.taskBaseId + 
      ", createdEmp=" + this.createdEmp + 
      ", createdEmpName='" + this.createdEmpName + "'" + 
      ", createdOrg=" + this.createdOrg + 
      ", createdOrgName='" + this.createdOrgName + "'" + 
      ", maintenance=" + this.maintenance + 
      ", see=" + this.see + 
      ", canCancel=" + this.canCancel + 
      ", canReport=" + this.canReport + 
      ", canDelete=" + this.canDelete + 
      ", canNew=" + this.canNew + 
      ", taskHasJunior=" + this.taskHasJunior + 
      ", taskRealEndTime=" + this.taskRealEndTime + 
      ",isArranged=" + this.isArranged + 
      ",taskAppend=" + this.taskAppend + 
      ",taskPO=" + this.taskPO + 
      ",taskTypeShow='" + this.taskTypeShow + "'" + 
      "}";
  }
  
  private String dateToString(Date date) {
    if (date != null) {
      DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);
      String s = null;
      s = formatter.format(date);
      return s;
    } 
    return null;
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
  
  public String getTaskRealEndTimeFormat() {
    return dateToString(getTaskRealEndTime());
  }
  
  public Boolean getCanReport() {
    return this.canReport;
  }
  
  public void setCanReport(Boolean canReport) {
    this.canReport = canReport;
  }
  
  public Boolean getCanDelete() {
    return this.canDelete;
  }
  
  public void setCanDelete(Boolean canDelete) {
    this.canDelete = canDelete;
  }
  
  public Integer getIsArranged() {
    return this.isArranged;
  }
  
  public void setIsArranged(Integer isArranged) {
    this.isArranged = isArranged;
  }
  
  public Boolean getCanNew() {
    return this.canNew;
  }
  
  public void setCanNew(Boolean canNew) {
    this.canNew = canNew;
  }
  
  public String getTaskAppend() {
    return this.taskAppend;
  }
  
  public void setTaskAppend(String taskAppend) {
    this.taskAppend = taskAppend;
  }
  
  public TaskPO getTaskPO() {
    return this.taskPO;
  }
  
  public void setTaskPO(TaskPO taskPO) {
    this.taskPO = taskPO;
  }
  
  public void setTaskRealEndTimeFormat(String taskRealEndTimeFormat) {
    this.taskRealEndTimeFormat = taskRealEndTimeFormat;
  }
  
  public void setTaskBeginTimeFormat(String taskBeginTimeFormat) {
    this.taskBeginTimeFormat = taskBeginTimeFormat;
  }
  
  public void setTaskCreatedTimeFormat(String taskCreatedTimeFormat) {
    this.taskCreatedTimeFormat = taskCreatedTimeFormat;
  }
  
  public void setTaskEndTimeFormat(String taskEndTimeFormat) {
    this.taskEndTimeFormat = taskEndTimeFormat;
  }
  
  public String getTaskTypeShow() {
    return this.taskTypeShow;
  }
  
  public String getTaskCancelReason() {
    return this.taskCancelReason;
  }
  
  public Set getProject() {
    return this.project;
  }
  
  public String getTasktimelimit() {
    return this.tasktimelimit;
  }
  
  public Integer getTasksortcode() {
    return this.tasksortcode;
  }
  
  public Integer getIslandmark() {
    return this.islandmark;
  }
  
  public void setTaskTypeShow(String taskTypeShow) {
    this.taskTypeShow = taskTypeShow;
  }
  
  public void setTaskCancelReason(String taskCancelReason) {
    this.taskCancelReason = taskCancelReason;
  }
  
  public void setProject(Set project) {
    this.project = project;
  }
  
  public void setTasktimelimit(String tasktimelimit) {
    this.tasktimelimit = tasktimelimit;
  }
  
  public void setTasksortcode(Integer tasksortcode) {
    this.tasksortcode = tasksortcode;
  }
  
  public void setIslandmark(Integer islandmark) {
    this.islandmark = islandmark;
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
