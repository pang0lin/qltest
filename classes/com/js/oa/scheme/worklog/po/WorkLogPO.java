package com.js.oa.scheme.worklog.po;

import com.js.oa.relproject.po.RelProjectPO;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class WorkLogPO implements Serializable {
  private String contentType;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  private String dailyId;
  
  private String empName;
  
  private String endPeriod;
  
  private String hadread;
  
  private String logContent;
  
  private Date logDate = null;
  
  private Long logDomainId;
  
  private Long logId;
  
  private String logRemark;
  
  private String logResult;
  
  private String logType;
  
  private Date logWriteDate = null;
  
  private Float manHour;
  
  private String projectName;
  
  private String projectStep;
  
  private String startPeriod;
  
  private Long taskAchieve;
  
  private String taskLoad;
  
  private String projectTaskCode;
  
  private String projectTaskName;
  
  private String weather;
  
  private WorkProjectPO workProject;
  
  private Set worklogComment = null;
  
  private RelProjectPO relProject = null;
  
  public WorkLogPO(String empName, Long logDomainId, WorkProjectPO workProject, String projectStep, String logContent, Date logDate, Float manHour, Long createdEmp, Long createdOrg) {
    this.empName = empName;
    this.workProject = workProject;
    this.projectStep = projectStep;
    this.logContent = logContent;
    this.logDate = logDate;
    this.manHour = manHour;
    this.createdEmp = createdEmp;
    this.createdOrg = createdOrg;
    this.logDomainId = logDomainId;
  }
  
  public WorkLogPO(String empName, Long logDomainId, WorkProjectPO workProject, String projectStep, String logContent, Date logDate, Float manHour, Long createdEmp, Long createdOrg, RelProjectPO relProject) {
    this.empName = empName;
    this.workProject = workProject;
    this.projectStep = projectStep;
    this.logContent = logContent;
    this.logDate = logDate;
    this.manHour = manHour;
    this.createdEmp = createdEmp;
    this.createdOrg = createdOrg;
    this.logDomainId = logDomainId;
    this.relProject = relProject;
  }
  
  public WorkLogPO() {}
  
  public WorkLogPO(WorkProjectPO workProject, Long logDomainId, String projectStep, Long createdEmp) {
    this.workProject = workProject;
    this.projectStep = projectStep;
    this.createdEmp = createdEmp;
    this.logDomainId = logDomainId;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof WorkLogPO))
      return false; 
    WorkLogPO castOther = (WorkLogPO)other;
    return (new EqualsBuilder()).append(getLogId(), castOther.getLogId()).isEquals();
  }
  
  public String getContentType() {
    return this.contentType;
  }
  
  public Long getCreatedEmp() {
    return this.createdEmp;
  }
  
  public Long getCreatedOrg() {
    return this.createdOrg;
  }
  
  public String getDailyId() {
    return this.dailyId;
  }
  
  public String getEmpName() {
    return this.empName;
  }
  
  public String getEndPeriod() {
    return this.endPeriod;
  }
  
  public String getHadread() {
    return this.hadread;
  }
  
  public String getLogContent() {
    return this.logContent;
  }
  
  public Date getLogDate() {
    return this.logDate;
  }
  
  public Long getLogDomainId() {
    return this.logDomainId;
  }
  
  public Long getLogId() {
    return this.logId;
  }
  
  public String getLogRemark() {
    return this.logRemark;
  }
  
  public String getLogResult() {
    return this.logResult;
  }
  
  public String getLogType() {
    return this.logType;
  }
  
  public Date getLogWriteDate() {
    return this.logWriteDate;
  }
  
  public Float getManHour() {
    return this.manHour;
  }
  
  public String getProjectName() {
    return this.projectName;
  }
  
  public String getProjectStep() {
    return this.projectStep;
  }
  
  public String getStartPeriod() {
    return this.startPeriod;
  }
  
  public Long getTaskAchieve() {
    return this.taskAchieve;
  }
  
  public String getTaskLoad() {
    return this.taskLoad;
  }
  
  public String getWeather() {
    return this.weather;
  }
  
  public WorkProjectPO getWorkProject() {
    return this.workProject;
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
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getLogId()).toHashCode();
  }
  
  public void setContentType(String contentType) {
    this.contentType = contentType;
  }
  
  public void setCreatedEmp(Long createdEmp) {
    this.createdEmp = createdEmp;
  }
  
  public void setCreatedOrg(Long createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public void setDailyId(String dailyId) {
    this.dailyId = dailyId;
  }
  
  public void setEmpName(String empName) {
    this.empName = empName;
  }
  
  public void setEndPeriod(String endPeriod) {
    this.endPeriod = endPeriod;
  }
  
  public void setHadread(String hadread) {
    this.hadread = hadread;
  }
  
  public void setLogContent(String logContent) {
    this.logContent = logContent;
  }
  
  public void setLogDate(Date logDate) {
    this.logDate = logDate;
  }
  
  public void setLogDomainId(Long logDomainId) {
    this.logDomainId = logDomainId;
  }
  
  public void setLogId(Long logId) {
    this.logId = logId;
  }
  
  public void setLogRemark(String logRemark) {
    this.logRemark = logRemark;
  }
  
  public void setLogResult(String logResult) {
    this.logResult = logResult;
  }
  
  public void setLogType(String logType) {
    this.logType = logType;
  }
  
  public void setLogWriteDate(Date logWriteDate) {
    this.logWriteDate = logWriteDate;
  }
  
  public void setManHour(Float manHour) {
    this.manHour = manHour;
  }
  
  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }
  
  public void setProjectStep(String projectStep) {
    this.projectStep = projectStep;
  }
  
  public void setStartPeriod(String startPeriod) {
    this.startPeriod = startPeriod;
  }
  
  public void setTaskAchieve(Long taskAchieve) {
    this.taskAchieve = taskAchieve;
  }
  
  public void setTaskLoad(String taskLoad) {
    this.taskLoad = taskLoad;
  }
  
  public void setWeather(String weather) {
    this.weather = weather;
  }
  
  public void setWorkProject(WorkProjectPO workProject) {
    this.workProject = workProject;
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
  
  public String toString() {
    return (new ToStringBuilder(this)).append("logId", getLogId()).toString();
  }
  
  public RelProjectPO getRelProject() {
    return this.relProject;
  }
  
  public void setRelProject(RelProjectPO relProject) {
    this.relProject = relProject;
  }
}
