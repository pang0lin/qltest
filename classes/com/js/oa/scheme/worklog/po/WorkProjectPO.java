package com.js.oa.scheme.worklog.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class WorkProjectPO implements Serializable {
  private Set WorkProjectTasks = null;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  private String hasProjectTask;
  
  private Date projectBeginTime = null;
  
  private String projectCode;
  
  private Long projectDomainId;
  
  private Date projectEndTime = null;
  
  private String projectGroupRange;
  
  private Long projectId;
  
  private Long projectManager;
  
  private String projectName;
  
  private String projectOrgRange;
  
  private String projectRange;
  
  private Integer projectStatus;
  
  private String projectUserRange;
  
  private Set workLogs = null;
  
  private WorkProjectClassPO workProjectClass;
  
  public WorkProjectPO(WorkProjectClassPO workProjectClass, String projectName, Long projectManager, Integer projectStatus, String projectRange, String projectUserRange, String projectOrgRange, String projectGroupRange, Date projectBeginTime, Date projectEndTime, Long createdOrg, Long createdEmp, Long projectDomainId, Set workLogs) {
    this.workProjectClass = workProjectClass;
    this.projectName = projectName;
    this.projectManager = projectManager;
    this.projectStatus = projectStatus;
    this.projectRange = projectRange;
    this.projectUserRange = projectUserRange;
    this.projectOrgRange = projectOrgRange;
    this.projectGroupRange = projectGroupRange;
    this.projectBeginTime = projectBeginTime;
    this.projectEndTime = projectEndTime;
    this.createdOrg = createdOrg;
    this.createdEmp = createdEmp;
    this.workLogs = workLogs;
    this.projectDomainId = projectDomainId;
  }
  
  public WorkProjectPO() {}
  
  public WorkProjectPO(String projectName, Long projectManager) {
    this.projectName = projectName;
    this.projectManager = projectManager;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof WorkProjectPO))
      return false; 
    WorkProjectPO castOther = (WorkProjectPO)other;
    return (new EqualsBuilder()).append(getProjectId(), castOther.getProjectId()).isEquals();
  }
  
  public Long getCreatedEmp() {
    return this.createdEmp;
  }
  
  public Long getCreatedOrg() {
    return this.createdOrg;
  }
  
  public String getHasProjectTask() {
    return this.hasProjectTask;
  }
  
  public Date getProjectBeginTime() {
    return this.projectBeginTime;
  }
  
  public String getProjectCode() {
    return this.projectCode;
  }
  
  public Long getProjectDomainId() {
    return this.projectDomainId;
  }
  
  public Date getProjectEndTime() {
    return this.projectEndTime;
  }
  
  public String getProjectGroupRange() {
    return this.projectGroupRange;
  }
  
  public Long getProjectId() {
    return this.projectId;
  }
  
  public Long getProjectManager() {
    return this.projectManager;
  }
  
  public String getProjectName() {
    return this.projectName;
  }
  
  public String getProjectOrgRange() {
    return this.projectOrgRange;
  }
  
  public String getProjectRange() {
    return this.projectRange;
  }
  
  public Integer getProjectStatus() {
    return this.projectStatus;
  }
  
  public String getProjectUserRange() {
    return this.projectUserRange;
  }
  
  public Set getWorkLogs() {
    return this.workLogs;
  }
  
  public WorkProjectClassPO getWorkProjectClass() {
    return this.workProjectClass;
  }
  
  public Set getWorkProjectTasks() {
    return this.WorkProjectTasks;
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getProjectId()).toHashCode();
  }
  
  public void setCreatedEmp(Long createdEmp) {
    this.createdEmp = createdEmp;
  }
  
  public void setCreatedOrg(Long createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public void setHasProjectTask(String hasProjectTask) {
    this.hasProjectTask = hasProjectTask;
  }
  
  public void setProjectBeginTime(Date projectBeginTime) {
    this.projectBeginTime = projectBeginTime;
  }
  
  public void setProjectCode(String projectCode) {
    this.projectCode = projectCode;
  }
  
  public void setProjectDomainId(Long projectDomainId) {
    this.projectDomainId = projectDomainId;
  }
  
  public void setProjectEndTime(Date projectEndTime) {
    this.projectEndTime = projectEndTime;
  }
  
  public void setProjectGroupRange(String projectGroupRange) {
    this.projectGroupRange = projectGroupRange;
  }
  
  public void setProjectId(Long projectId) {
    this.projectId = projectId;
  }
  
  public void setProjectManager(Long projectManager) {
    this.projectManager = projectManager;
  }
  
  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }
  
  public void setProjectOrgRange(String projectOrgRange) {
    this.projectOrgRange = projectOrgRange;
  }
  
  public void setProjectRange(String projectRange) {
    this.projectRange = projectRange;
  }
  
  public void setProjectStatus(Integer projectStatus) {
    this.projectStatus = projectStatus;
  }
  
  public void setProjectUserRange(String projectUserRange) {
    this.projectUserRange = projectUserRange;
  }
  
  public void setWorkLogs(Set workLogs) {
    this.workLogs = workLogs;
  }
  
  public void setWorkProjectClass(WorkProjectClassPO workProjectClass) {
    this.workProjectClass = workProjectClass;
  }
  
  public void setWorkProjectTasks(Set WorkProjectTasks) {
    this.WorkProjectTasks = WorkProjectTasks;
  }
  
  public String toString() {
    return (new ToStringBuilder(this)).append("id", getProjectId()).toString();
  }
}
