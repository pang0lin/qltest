package com.js.oa.audit.po;

import java.io.Serializable;

public class AuditManager implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private Long managerId;
  
  private Long empId;
  
  private String empIds;
  
  private String empNames;
  
  private String empName;
  
  private Integer rightScopeType;
  
  private String rightScopeScope;
  
  private String rightScope;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  private String createdEmpName;
  
  public AuditManager() {}
  
  public AuditManager(Long empId, Integer rightscopetype, String rightscopescope, Long createdemp, Long createdorg) {
    this.empId = empId;
    this.rightScopeType = rightscopetype;
    this.rightScopeScope = rightscopescope;
    this.createdEmp = createdemp;
    this.createdOrg = createdorg;
  }
  
  public Long getManagerId() {
    return this.managerId;
  }
  
  public void setManagerId(Long managerId) {
    this.managerId = managerId;
  }
  
  public Long getEmpId() {
    return this.empId;
  }
  
  public void setEmpId(Long empId) {
    this.empId = empId;
  }
  
  public Integer getRightScopeType() {
    return this.rightScopeType;
  }
  
  public void setRightScopeType(Integer rightScopeType) {
    this.rightScopeType = rightScopeType;
  }
  
  public String getRightScopeScope() {
    return this.rightScopeScope;
  }
  
  public void setRightScopeScope(String rightScopeScope) {
    this.rightScopeScope = rightScopeScope;
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
  
  public void setEmpName(String empName) {
    this.empName = empName;
  }
  
  public String getCreatedEmpName() {
    return this.createdEmpName;
  }
  
  public void setCreatedEmpName(String createdEmpName) {
    this.createdEmpName = createdEmpName;
  }
  
  public String getRightScope() {
    return this.rightScope;
  }
  
  public void setRightScope(String rightScope) {
    this.rightScope = rightScope;
  }
  
  public String getEmpIds() {
    return this.empIds;
  }
  
  public void setEmpIds(String empIds) {
    this.empIds = empIds;
  }
  
  public String getEmpNames() {
    return this.empNames;
  }
  
  public void setEmpNames(String empNames) {
    this.empNames = empNames;
  }
}
