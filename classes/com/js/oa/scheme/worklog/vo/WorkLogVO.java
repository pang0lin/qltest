package com.js.oa.scheme.worklog.vo;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

public class WorkLogVO implements Serializable {
  private Long logId;
  
  private Long logDomainId;
  
  private String empName;
  
  private Long projectId;
  
  private String projectName;
  
  private Long classId;
  
  private String projectStep;
  
  private String logContent;
  
  private Date logDate = null;
  
  private String logDateFormat;
  
  private Float manHour;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  private Boolean maintenance;
  
  private Boolean see;
  
  private Float projectStepTotalTime;
  
  private String projectTaskName;
  
  private String taskLoad;
  
  private String projectTaskCode;
  
  private Set worklogComment = null;
  
  public WorkLogVO() {}
  
  public WorkLogVO(Long logId, Long logDomainId, String empName, Long projectId, String projectStep, String logContent, Date logDate, Float manHour, Long createdEmp, Long createdOrg, String projectName) {
    this.logId = logId;
    this.empName = empName;
    this.projectId = projectId;
    this.projectStep = projectStep;
    this.logContent = logContent;
    this.logDate = logDate;
    this.manHour = manHour;
    this.createdEmp = createdEmp;
    this.createdOrg = createdOrg;
    this.projectName = projectName;
    this.logDomainId = logDomainId;
  }
  
  public Long getLogId() {
    return this.logId;
  }
  
  public void setLogId(Long logId) {
    this.logId = logId;
  }
  
  public Long getLogDomainId() {
    return this.logDomainId;
  }
  
  public void setLogDomainId(Long logDomainId) {
    this.logDomainId = logDomainId;
  }
  
  public String getEmpName() {
    return this.empName;
  }
  
  public void setEmpName(String empName) {
    this.empName = empName;
  }
  
  public Long getProjectId() {
    return this.projectId;
  }
  
  public void setProjectId(Long projectId) {
    this.projectId = projectId;
  }
  
  public Long getClassId() {
    return this.classId;
  }
  
  public void setClassId(Long classId) {
    this.classId = classId;
  }
  
  public String getProjectStep() {
    return this.projectStep;
  }
  
  public void setProjectStep(String projectStep) {
    this.projectStep = projectStep;
  }
  
  public String getLogContent() {
    return this.logContent;
  }
  
  public void setLogContent(String logContent) {
    this.logContent = logContent;
  }
  
  public Date getLogDate() {
    return this.logDate;
  }
  
  public void setLogDate(Date logDate) {
    this.logDate = logDate;
  }
  
  public Float getManHour() {
    return this.manHour;
  }
  
  public void setManHour(Float manHour) {
    this.manHour = manHour;
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
  
  public boolean equals(Object o) {
    if (this == o)
      return true; 
    if (!(o instanceof WorkLogVO))
      return false; 
    WorkLogVO workLogVO = (WorkLogVO)o;
    if ((this.createdEmp != null) ? !this.createdEmp.equals(workLogVO.createdEmp) : (workLogVO.createdEmp != null))
      return false; 
    if ((this.createdOrg != null) ? !this.createdOrg.equals(workLogVO.createdOrg) : (workLogVO.createdOrg != null))
      return false; 
    if ((this.empName != null) ? !this.empName.equals(workLogVO.empName) : (workLogVO.empName != null))
      return false; 
    if ((this.logContent != null) ? !this.logContent.equals(workLogVO.logContent) : (workLogVO.logContent != null))
      return false; 
    if ((this.logDate != null) ? !this.logDate.equals(workLogVO.logDate) : (workLogVO.logDate != null))
      return false; 
    if ((this.logDateFormat != null) ? !this.logDateFormat.equals(workLogVO.logDateFormat) : (workLogVO.logDateFormat != null))
      return false; 
    if ((this.logId != null) ? !this.logId.equals(workLogVO.logId) : (workLogVO.logId != null))
      return false; 
    if ((this.manHour != null) ? !this.manHour.equals(workLogVO.manHour) : (workLogVO.manHour != null))
      return false; 
    if ((this.projectId != null) ? !this.projectId.equals(workLogVO.projectId) : (workLogVO.projectId != null))
      return false; 
    if ((this.projectName != null) ? !this.projectName.equals(workLogVO.projectName) : (workLogVO.projectName != null))
      return false; 
    if ((this.projectStep != null) ? !this.projectStep.equals(workLogVO.projectStep) : (workLogVO.projectStep != null))
      return false; 
    if ((this.classId != null) ? !this.classId.equals(workLogVO.classId) : (workLogVO.classId != null))
      return false; 
    if ((this.projectStepTotalTime != null) ? !this.projectStepTotalTime.equals(workLogVO.projectStepTotalTime) : (workLogVO.projectStepTotalTime != null))
      return false; 
    if ((this.logDomainId != null) ? !this.logDomainId.equals(workLogVO.logDomainId) : (workLogVO.logDomainId != null))
      return false; 
    return true;
  }
  
  public int hashCode() {
    int result = (this.logId != null) ? this.logId.hashCode() : 0;
    result = 29 * result + ((this.empName != null) ? this.empName.hashCode() : 0);
    result = 29 * result + ((this.projectId != null) ? this.projectId.hashCode() : 0);
    result = 29 * result + ((this.projectName != null) ? this.projectName.hashCode() : 0);
    result = 29 * result + ((this.projectStep != null) ? this.projectStep.hashCode() : 0);
    result = 29 * result + ((this.logContent != null) ? this.logContent.hashCode() : 0);
    result = 29 * result + ((this.logDate != null) ? this.logDate.hashCode() : 0);
    result = 29 * result + ((this.logDateFormat != null) ? this.logDateFormat.hashCode() : 0);
    result = 29 * result + ((this.manHour != null) ? this.manHour.hashCode() : 0);
    result = 29 * result + ((this.createdEmp != null) ? this.createdEmp.hashCode() : 0);
    result = 29 * result + ((this.createdOrg != null) ? this.createdOrg.hashCode() : 0);
    result = 29 * result + ((this.classId != null) ? this.classId.hashCode() : 0);
    result = 29 * result + ((this.projectStepTotalTime != null) ? this.projectStepTotalTime.hashCode() : 0);
    result = 29 * result + ((this.logDomainId != null) ? this.logDomainId.hashCode() : 0);
    return result;
  }
  
  public Boolean getMaintenance() {
    return this.maintenance;
  }
  
  public Boolean getSee() {
    return this.see;
  }
  
  public void setMaintenance(Boolean maintenance) {
    this.maintenance = maintenance;
  }
  
  public void setSee(Boolean see) {
    this.see = see;
  }
  
  public String getProjectName() {
    return this.projectName;
  }
  
  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }
  
  public String getLogDateFormat() {
    return DateToString(getLogDate());
  }
  
  private String DateToString(Date date) {
    if (date != null) {
      DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);
      String s = null;
      s = formatter.format(date);
      return s;
    } 
    return null;
  }
  
  public void setProjectStepTotalTime(Float projectStepTotalTime) {
    this.projectStepTotalTime = projectStepTotalTime;
  }
  
  public void setWorklogComment(Set worklogComment) {
    this.worklogComment = worklogComment;
  }
  
  public void setProjectTaskName(String projectTaskName) {
    this.projectTaskName = projectTaskName;
  }
  
  public void setProjectTaskCode(String projectTaskCode) {
    this.projectTaskCode = projectTaskCode;
  }
  
  public void setTaskLoad(String taskLoad) {
    this.taskLoad = taskLoad;
  }
  
  public void setLogDateFormat(String logDateFormat) {
    this.logDateFormat = logDateFormat;
  }
  
  public Float getProjectStepTotalTime() {
    return this.projectStepTotalTime;
  }
  
  public Set getWorklogComment() {
    return this.worklogComment;
  }
  
  public String getProjectTaskName() {
    return this.projectTaskName;
  }
  
  public String getProjectTaskCode() {
    return this.projectTaskCode;
  }
  
  public String getTaskLoad() {
    return this.taskLoad;
  }
}
