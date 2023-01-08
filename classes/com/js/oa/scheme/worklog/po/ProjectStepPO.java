package com.js.oa.scheme.worklog.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ProjectStepPO implements Serializable {
  private Long stepId;
  
  private Long stepDomainId;
  
  private WorkProjectClassPO workProjectClass;
  
  private String stepName;
  
  public ProjectStepPO(WorkProjectClassPO workProjectClass, String stepName, Long stepDomainId) {
    this.workProjectClass = workProjectClass;
    this.stepName = stepName;
    this.stepDomainId = stepDomainId;
  }
  
  public ProjectStepPO() {}
  
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
  
  public String getStepName() {
    return this.stepName;
  }
  
  public void setStepName(String stepName) {
    this.stepName = stepName;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("stepId", getStepId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof ProjectStepPO))
      return false; 
    ProjectStepPO castOther = (ProjectStepPO)other;
    return (new EqualsBuilder())
      .append(getStepId(), castOther.getStepId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getStepId())
      .toHashCode();
  }
  
  public WorkProjectClassPO getWorkProjectClass() {
    return this.workProjectClass;
  }
  
  public void setWorkProjectClass(WorkProjectClassPO workProjectClass) {
    this.workProjectClass = workProjectClass;
  }
}
