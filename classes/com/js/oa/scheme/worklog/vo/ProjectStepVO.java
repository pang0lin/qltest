package com.js.oa.scheme.worklog.vo;

import com.js.oa.scheme.worklog.po.ProjectStepPO;
import java.io.Serializable;

public class ProjectStepVO implements Serializable {
  private Long stepId;
  
  private Long stepDomainId;
  
  private Long classId;
  
  private String className;
  
  private String stepName;
  
  public ProjectStepVO() {}
  
  public ProjectStepVO(Long stepId, Long stepDomainId, Long classId, String stepName) {
    this.stepId = stepId;
    this.classId = classId;
    this.stepName = stepName;
    this.stepDomainId = stepDomainId;
  }
  
  public ProjectStepVO(Long stepId, Long stepDomainId, Long classId, String stepName, String className) {
    this.stepId = stepId;
    this.classId = classId;
    this.stepName = stepName;
    this.className = className;
    this.stepDomainId = stepDomainId;
  }
  
  public Long getStepId() {
    return this.stepId;
  }
  
  public void setStepId(Long stepId) {
    this.stepId = stepId;
  }
  
  public Long getStepDomainId() {
    return this.stepDomainId;
  }
  
  public void setStepDomainId(Long stepDomainId) {
    this.stepDomainId = stepDomainId;
  }
  
  public Long getClassId() {
    return this.classId;
  }
  
  public void setClassId(Long classId) {
    this.classId = classId;
  }
  
  public String getStepName() {
    return this.stepName;
  }
  
  public void setStepName(String stepName) {
    this.stepName = stepName;
  }
  
  public boolean equals(Object o) {
    if (this == o)
      return true; 
    if (!(o instanceof ProjectStepVO))
      return false; 
    ProjectStepVO projectStepVO = (ProjectStepVO)o;
    if ((this.classId != null) ? !this.classId.equals(projectStepVO.classId) : (projectStepVO.classId != null))
      return false; 
    if (!this.stepId.equals(projectStepVO.stepId))
      return false; 
    if ((this.stepName != null) ? !this.stepName.equals(projectStepVO.stepName) : (projectStepVO.stepName != null))
      return false; 
    if ((this.className != null) ? !this.className.equals(projectStepVO.className) : (projectStepVO.className != null))
      return false; 
    if ((this.stepDomainId != null) ? !this.stepDomainId.equals(projectStepVO.stepDomainId) : (projectStepVO.stepDomainId != null))
      return false; 
    return true;
  }
  
  public int hashCode() {
    int result = this.stepId.hashCode();
    result = 29 * result + ((this.classId != null) ? this.classId.hashCode() : 0);
    result = 29 * result + ((this.stepName != null) ? this.stepName.hashCode() : 0);
    result = 29 * result + ((this.className != null) ? this.className.hashCode() : 0);
    result = 29 * result + ((this.stepDomainId != null) ? this.stepDomainId.hashCode() : 0);
    return result;
  }
  
  public ProjectStepVO conversionPO(ProjectStepPO projectStepPO) {
    this.stepId = projectStepPO.getStepId();
    this.classId = projectStepPO.getWorkProjectClass().getClassId();
    this.stepName = projectStepPO.getStepName();
    this.className = projectStepPO.getWorkProjectClass().getClassName();
    this.stepDomainId = projectStepPO.getStepDomainId();
    return this;
  }
  
  public String toString() {
    return "ProjectStepVO{stepId=" + 
      this.stepId + 
      ", stepDomainId=" + this.stepDomainId + 
      ", classId=" + this.classId + 
      ", stepName='" + this.stepName + "'" + 
      ", className='" + this.className + "'" + 
      "}";
  }
  
  public String getClassName() {
    return this.className;
  }
  
  public void setClassName(String className) {
    this.className = className;
  }
}
