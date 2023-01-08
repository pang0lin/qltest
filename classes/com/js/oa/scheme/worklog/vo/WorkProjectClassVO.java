package com.js.oa.scheme.worklog.vo;

import com.js.oa.scheme.worklog.po.WorkProjectClassPO;
import java.io.Serializable;

public class WorkProjectClassVO implements Serializable {
  private Long classId;
  
  private String className;
  
  private Long classDomainId;
  
  private String classRange;
  
  private String classUserRange;
  
  private String classOrgRange;
  
  private String classGroupRange;
  
  private Long createdOrg;
  
  private Long createdEmp;
  
  private Boolean maintenance;
  
  private Boolean see;
  
  public WorkProjectClassVO() {}
  
  public String getClassGroupRange() {
    return this.classGroupRange;
  }
  
  public void setClassGroupRange(String classGroupRange) {
    this.classGroupRange = classGroupRange;
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
  
  public String getClassOrgRange() {
    return this.classOrgRange;
  }
  
  public void setClassOrgRange(String classOrgRange) {
    this.classOrgRange = classOrgRange;
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
  
  public WorkProjectClassVO(Long classId, Long classDomainId, String className, String classRange, String classUserRange, String classOrgRange, String classGroupRange, Long createdOrg, Long createdEmp) {
    this.classId = classId;
    this.classDomainId = classDomainId;
    this.className = className;
    this.classRange = classRange;
    this.classUserRange = classUserRange;
    this.classOrgRange = classOrgRange;
    this.classGroupRange = classGroupRange;
    this.createdOrg = createdOrg;
    this.createdEmp = createdEmp;
  }
  
  public WorkProjectClassVO(Long classId, Long classDomainId, String className, String classRange, String classUserRange, String classOrgRange, String classGroupRange, Long createdOrg, Long createdEmp, Boolean maintenance, Boolean see) {
    this.classId = classId;
    this.classDomainId = classDomainId;
    this.className = className;
    this.classRange = classRange;
    this.classUserRange = classUserRange;
    this.classOrgRange = classOrgRange;
    this.classGroupRange = classGroupRange;
    this.createdOrg = createdOrg;
    this.createdEmp = createdEmp;
    this.maintenance = maintenance;
    this.see = see;
  }
  
  public boolean equals(Object o) {
    if (this == o)
      return true; 
    if (!(o instanceof WorkProjectClassVO))
      return false; 
    WorkProjectClassVO workProjectClassVO = (WorkProjectClassVO)o;
    if ((this.classGroupRange != null) ? 
      !this.classGroupRange.equals(workProjectClassVO.classGroupRange) : (
      workProjectClassVO.classGroupRange != null))
      return false; 
    if (!this.classId.equals(workProjectClassVO.classId))
      return false; 
    if (!this.classDomainId.equals(workProjectClassVO.classDomainId))
      return false; 
    if (!this.className.equals(workProjectClassVO.className))
      return false; 
    if ((this.classOrgRange != null) ? 
      !this.classOrgRange.equals(workProjectClassVO.classOrgRange) : (
      workProjectClassVO.classOrgRange != null))
      return false; 
    if ((this.classRange != null) ? 
      !this.classRange.equals(workProjectClassVO.classRange) : (
      workProjectClassVO.classRange != null))
      return false; 
    if ((this.classUserRange != null) ? 
      !this.classUserRange.equals(workProjectClassVO.classUserRange) : (
      workProjectClassVO.classUserRange != null))
      return false; 
    if ((this.createdEmp != null) ? 
      !this.createdEmp.equals(workProjectClassVO.createdEmp) : (
      workProjectClassVO.createdEmp != null))
      return false; 
    if ((this.createdOrg != null) ? 
      !this.createdOrg.equals(workProjectClassVO.createdOrg) : (
      workProjectClassVO.createdOrg != null))
      return false; 
    if ((this.maintenance != null) ? 
      !this.maintenance.equals(workProjectClassVO.maintenance) : (
      workProjectClassVO.maintenance != null))
      return false; 
    if ((this.see != null) ? 
      !this.see.equals(workProjectClassVO.see) : (
      workProjectClassVO.see != null))
      return false; 
    return true;
  }
  
  public int hashCode() {
    int result = this.classId.hashCode();
    result = 29 * result + this.className.hashCode();
    result = 29 * result + ((this.classRange != null) ? this.classRange.hashCode() : 0);
    result = 29 * result + (
      (this.classUserRange != null) ? this.classUserRange.hashCode() : 0);
    result = 29 * result + (
      (this.classOrgRange != null) ? this.classOrgRange.hashCode() : 0);
    result = 29 * result + (
      (this.classGroupRange != null) ? this.classGroupRange.hashCode() : 0);
    result = 29 * result + ((this.createdOrg != null) ? this.createdOrg.hashCode() : 0);
    result = 29 * result + ((this.createdEmp != null) ? this.createdEmp.hashCode() : 0);
    result = 29 * result + (
      (this.maintenance != null) ? this.maintenance.hashCode() : 0);
    result = 29 * result + ((this.see != null) ? this.see.hashCode() : 0);
    result = 29 * result + ((this.classDomainId != null) ? this.classDomainId.hashCode() : 0);
    return result;
  }
  
  public WorkProjectClassVO conversionPO(WorkProjectClassPO workProjectClassPO) {
    this.classId = workProjectClassPO.getClassId();
    this.className = workProjectClassPO.getClassName();
    this.classGroupRange = workProjectClassPO.getClassGroupRange();
    this.classOrgRange = workProjectClassPO.getClassOrgRange();
    this.classRange = workProjectClassPO.getClassRange();
    this.classUserRange = workProjectClassPO.getClassUserRange();
    this.createdEmp = workProjectClassPO.getCreatedEmp();
    this.createdOrg = workProjectClassPO.getCreatedOrg();
    this.classDomainId = workProjectClassPO.getClassDomainId();
    return this;
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
}
