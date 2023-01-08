package com.js.oa.scheme.worklog.vo;

import java.io.Serializable;
import java.util.Date;

public class WorkProjectVO implements Serializable {
  private Long classId;
  
  private String className;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  private String empName;
  
  private String hasProjectTask;
  
  private Boolean maintenance;
  
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
  
  private Boolean see;
  
  public WorkProjectVO() {}
  
  public WorkProjectVO(Long projectId, Long projectDomainId, Long classId, String projectName, Long projectManager, Integer projectStatus, String projectRange, String projectUserRange, String projectOrgRange, String projectGroupRange, Date projectBeginTime, Date projectEndTime, Long createdOrg, Long createdEmp, String projectCode, String hasProjectTask) {
    this.projectCode = projectCode;
    this.hasProjectTask = hasProjectTask;
    this.projectId = projectId;
    this.classId = classId;
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
    this.projectDomainId = projectDomainId;
  }
  
  public WorkProjectVO(Long projectId, Long projectDomainId, Long classId, String projectName, Long projectManager, Integer projectStatus, String projectRange, String projectUserRange, String projectOrgRange, String projectGroupRange, Date projectBeginTime, Date projectEndTime, Long createdOrg, Long createdEmp, Boolean maintenance, Boolean see, String projectCode, String hasProjectTask) {
    this.projectCode = projectCode;
    this.hasProjectTask = hasProjectTask;
    this.projectId = projectId;
    this.classId = classId;
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
    this.maintenance = maintenance;
    this.see = see;
    this.projectDomainId = projectDomainId;
  }
  
  public boolean equals(Object o) {
    if (this == o)
      return true; 
    if (!(o instanceof WorkProjectVO))
      return false; 
    WorkProjectVO workProjectVO = (WorkProjectVO)o;
    if ((this.classId != null) ? !this.classId.equals(workProjectVO.classId) : (workProjectVO.classId != null))
      return false; 
    if ((this.className != null) ? !this.className.equals(workProjectVO.className) : (workProjectVO.className != null))
      return false; 
    if ((this.createdEmp != null) ? !this.createdEmp.equals(workProjectVO.createdEmp) : (workProjectVO.createdEmp != null))
      return false; 
    if ((this.empName != null) ? !this.empName.equals(workProjectVO.empName) : (workProjectVO.empName != null))
      return false; 
    if ((this.createdOrg != null) ? !this.createdOrg.equals(workProjectVO.createdOrg) : (workProjectVO.createdOrg != null))
      return false; 
    if ((this.maintenance != null) ? !this.maintenance.equals(workProjectVO.maintenance) : (workProjectVO.maintenance != null))
      return false; 
    if ((this.projectBeginTime != null) ? !this.projectBeginTime.equals(workProjectVO.projectBeginTime) : (workProjectVO.projectBeginTime != null))
      return false; 
    if ((this.projectEndTime != null) ? !this.projectEndTime.equals(workProjectVO.projectEndTime) : (workProjectVO.projectEndTime != null))
      return false; 
    if ((this.projectGroupRange != null) ? !this.projectGroupRange.equals(workProjectVO.projectGroupRange) : (workProjectVO.projectGroupRange != null))
      return false; 
    if ((this.projectId != null) ? !this.projectId.equals(workProjectVO.projectId) : (workProjectVO.projectId != null))
      return false; 
    if ((this.projectManager != null) ? !this.projectManager.equals(workProjectVO.projectManager) : (workProjectVO.projectManager != null))
      return false; 
    if ((this.projectName != null) ? !this.projectName.equals(workProjectVO.projectName) : (workProjectVO.projectName != null))
      return false; 
    if ((this.projectOrgRange != null) ? !this.projectOrgRange.equals(workProjectVO.projectOrgRange) : (workProjectVO.projectOrgRange != null))
      return false; 
    if ((this.projectRange != null) ? !this.projectRange.equals(workProjectVO.projectRange) : (workProjectVO.projectRange != null))
      return false; 
    if ((this.projectStatus != null) ? !this.projectStatus.equals(workProjectVO.projectStatus) : (workProjectVO.projectStatus != null))
      return false; 
    if ((this.projectUserRange != null) ? !this.projectUserRange.equals(workProjectVO.projectUserRange) : (workProjectVO.projectUserRange != null))
      return false; 
    if ((this.see != null) ? !this.see.equals(workProjectVO.see) : (workProjectVO.see != null))
      return false; 
    return (this.projectDomainId != null) ? this.projectDomainId.equals(workProjectVO.projectDomainId) : ((workProjectVO.projectDomainId == null));
  }
  
  public Long getClassId() {
    return this.classId;
  }
  
  public String getClassName() {
    return this.className;
  }
  
  public Long getCreatedEmp() {
    return this.createdEmp;
  }
  
  public Long getCreatedOrg() {
    return this.createdOrg;
  }
  
  public String getEmpName() {
    return this.empName;
  }
  
  public String getHasProjectTask() {
    return this.hasProjectTask;
  }
  
  public Boolean getMaintenance() {
    return this.maintenance;
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
  
  public Boolean getSee() {
    return this.see;
  }
  
  public int hashCode() {
    int result = (this.projectId != null) ? this.projectId.hashCode() : 0;
    result = 29 * result + ((this.classId != null) ? this.classId.hashCode() : 0);
    result = 29 * result + ((this.className != null) ? this.className.hashCode() : 0);
    result = 29 * result + ((this.projectName != null) ? this.projectName.hashCode() : 0);
    result = 29 * result + ((this.projectManager != null) ? this.projectManager.hashCode() : 0);
    result = 29 * result + ((this.projectStatus != null) ? this.projectStatus.hashCode() : 0);
    result = 29 * result + ((this.projectRange != null) ? this.projectRange.hashCode() : 0);
    result = 29 * result + ((this.projectUserRange != null) ? this.projectUserRange.hashCode() : 0);
    result = 29 * result + ((this.projectOrgRange != null) ? this.projectOrgRange.hashCode() : 0);
    result = 29 * result + ((this.projectGroupRange != null) ? this.projectGroupRange.hashCode() : 0);
    result = 29 * result + ((this.projectBeginTime != null) ? this.projectBeginTime.hashCode() : 0);
    result = 29 * result + ((this.projectEndTime != null) ? this.projectEndTime.hashCode() : 0);
    result = 29 * result + ((this.createdOrg != null) ? this.createdOrg.hashCode() : 0);
    result = 29 * result + ((this.createdEmp != null) ? this.createdEmp.hashCode() : 0);
    result = 29 * result + ((this.empName != null) ? this.empName.hashCode() : 0);
    result = 29 * result + ((this.maintenance != null) ? this.maintenance.hashCode() : 0);
    result = 29 * result + ((this.see != null) ? this.see.hashCode() : 0);
    result = 29 * result + ((this.projectDomainId != null) ? this.projectDomainId.hashCode() : 0);
    return result;
  }
  
  public void setClassId(Long classId) {
    this.classId = classId;
  }
  
  public void setClassName(String className) {
    this.className = className;
  }
  
  public void setCreatedEmp(Long createdEmp) {
    this.createdEmp = createdEmp;
  }
  
  public void setCreatedOrg(Long createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public void setEmpName(String empName) {
    this.empName = empName;
  }
  
  public void setHasProjectTask(String hasProjectTask) {
    this.hasProjectTask = hasProjectTask;
  }
  
  public void setMaintenance(Boolean maintenance) {
    this.maintenance = maintenance;
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
  
  public void setSee(Boolean see) {
    this.see = see;
  }
}
