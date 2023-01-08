package com.js.oa.scheme.worklog.po;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class WorkProjectClassPO implements Serializable {
  private Long classId;
  
  private Long classDomainId;
  
  private String className;
  
  private String classRange;
  
  private String classUserRange;
  
  private String classOrgRange;
  
  private String classGroupRange;
  
  private Long createdOrg;
  
  private Long createdEmp;
  
  private Set projectSteps = null;
  
  private Set workProjects = null;
  
  public WorkProjectClassPO(String className, Long classDomainId, String classRange, String classUserRange, String classOrgRange, String classGroupRange, Long createdOrg, Long createdEmp, Set projectSteps, Set workProjects) {
    this.className = className;
    this.classRange = classRange;
    this.classUserRange = classUserRange;
    this.classOrgRange = classOrgRange;
    this.classGroupRange = classGroupRange;
    this.createdOrg = createdOrg;
    this.createdEmp = createdEmp;
    this.projectSteps = projectSteps;
    this.workProjects = workProjects;
    this.classDomainId = classDomainId;
  }
  
  public WorkProjectClassPO() {}
  
  public WorkProjectClassPO(String className) {
    this.className = className;
  }
  
  public Long getClassId() {
    return this.classId;
  }
  
  public void setClassId(Long classId) {
    this.classId = classId;
  }
  
  public Long getClassDomainId() {
    return this.classDomainId;
  }
  
  public void setClassDomainId(Long classDomainId) {
    this.classDomainId = classDomainId;
  }
  
  public String getClassName() {
    return this.className;
  }
  
  public void setClassName(String className) {
    this.className = className;
  }
  
  public String getClassRange() {
    return this.classRange;
  }
  
  public void setClassRange(String classRange) {
    this.classRange = classRange;
  }
  
  public String getClassUserRange() {
    return this.classUserRange;
  }
  
  public void setClassUserRange(String classUserRange) {
    this.classUserRange = classUserRange;
  }
  
  public String getClassOrgRange() {
    return this.classOrgRange;
  }
  
  public void setClassOrgRange(String classOrgRange) {
    this.classOrgRange = classOrgRange;
  }
  
  public String getClassGroupRange() {
    return this.classGroupRange;
  }
  
  public void setClassGroupRange(String classGroupRange) {
    this.classGroupRange = classGroupRange;
  }
  
  public Long getCreatedOrg() {
    return this.createdOrg;
  }
  
  public void setCreatedOrg(Long createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public Long getCreatedEmp() {
    return this.createdEmp;
  }
  
  public void setCreatedEmp(Long createdEmp) {
    this.createdEmp = createdEmp;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getClassId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof WorkProjectClassPO))
      return false; 
    WorkProjectClassPO castOther = (WorkProjectClassPO)other;
    return (new EqualsBuilder())
      .append(getClassId(), castOther.getClassId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getClassId())
      .toHashCode();
  }
  
  public Set getProjectSteps() {
    return this.projectSteps;
  }
  
  public void setProjectSteps(Set projectSteps) {
    this.projectSteps = projectSteps;
  }
  
  public Set getWorkProjects() {
    return this.workProjects;
  }
  
  public void setWorkProjects(Set workProjects) {
    this.workProjects = workProjects;
  }
}
