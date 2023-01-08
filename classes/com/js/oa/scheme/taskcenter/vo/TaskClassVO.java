package com.js.oa.scheme.taskcenter.vo;

import java.io.Serializable;

public class TaskClassVO implements Serializable {
  private Long classId;
  
  private Long classDomainId;
  
  private String className;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  private String empName;
  
  private Boolean maintenance;
  
  private Boolean see;
  
  private String usedScopeName;
  
  private String usedScopeId;
  
  private String usedScopeOrgId;
  
  private String usedScopeGroupId;
  
  private String orderCode;
  
  public TaskClassVO() {}
  
  public TaskClassVO(Long classId, Long classDomainId, String className) {
    this.classId = classId;
    this.className = className;
    this.classDomainId = classDomainId;
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
  
  public boolean equals(Object o) {
    if (this == o)
      return true; 
    if (!(o instanceof TaskClassVO))
      return false; 
    TaskClassVO taskClassVO = (TaskClassVO)o;
    if (!this.classId.equals(taskClassVO.classId))
      return false; 
    if ((this.className != null) ? !this.className.equals(taskClassVO.className) : (taskClassVO.className != null))
      return false; 
    if ((this.createdEmp != null) ? !this.createdEmp.equals(taskClassVO.createdEmp) : (taskClassVO.createdEmp != null))
      return false; 
    if ((this.createdOrg != null) ? !this.createdOrg.equals(taskClassVO.createdOrg) : (taskClassVO.createdOrg != null))
      return false; 
    if ((this.empName != null) ? !this.empName.equals(taskClassVO.empName) : (taskClassVO.empName != null))
      return false; 
    if ((this.classDomainId != null) ? !this.classDomainId.equals(taskClassVO.classDomainId) : (taskClassVO.classDomainId != null))
      return false; 
    return true;
  }
  
  public int hashCode() {
    int result = this.classId.hashCode();
    result = 29 * result + ((this.className != null) ? this.className.hashCode() : 0);
    result = 29 * result + ((this.createdEmp != null) ? this.createdEmp.hashCode() : 0);
    result = 29 * result + ((this.className != null) ? this.className.hashCode() : 0);
    result = 29 * result + ((this.className != null) ? this.className.hashCode() : 0);
    result = 29 * result + ((this.classDomainId != null) ? this.classDomainId.hashCode() : 0);
    return result;
  }
  
  public String toString() {
    return "TaskClassVO{classId=" + 
      this.classId + 
      ", classDomainId=" + this.classDomainId + 
      ", className='" + this.className + "'" + 
      "}";
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
  
  public String getEmpName() {
    return this.empName;
  }
  
  public String getUsedScopeId() {
    return this.usedScopeId;
  }
  
  public String getUsedScopeGroupId() {
    return this.usedScopeGroupId;
  }
  
  public String getUsedScopeName() {
    return this.usedScopeName;
  }
  
  public String getUsedScopeOrgId() {
    return this.usedScopeOrgId;
  }
  
  public void setEmpName(String empName) {
    this.empName = empName;
  }
  
  public void setUsedScopeId(String usedScopeId) {
    this.usedScopeId = usedScopeId;
  }
  
  public void setUsedScopeGroupId(String usedScopeGroupId) {
    this.usedScopeGroupId = usedScopeGroupId;
  }
  
  public void setUsedScopeName(String usedScopeName) {
    this.usedScopeName = usedScopeName;
  }
  
  public void setUsedScopeOrgId(String usedScopeOrgId) {
    this.usedScopeOrgId = usedScopeOrgId;
  }
  
  public String getOrderCode() {
    return this.orderCode;
  }
  
  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }
}
