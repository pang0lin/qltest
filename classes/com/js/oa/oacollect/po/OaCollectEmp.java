package com.js.oa.oacollect.po;

import java.io.Serializable;

public class OaCollectEmp implements Serializable {
  private Long id;
  
  private Long collectId;
  
  private Long empId;
  
  private String empName;
  
  private Long empStatus;
  
  private Long remindCount;
  
  public OaCollectEmp() {}
  
  public OaCollectEmp(Long collectId, Long empId, String empName, Long empStatus) {
    this.collectId = collectId;
    this.empId = empId;
    this.empName = empName;
    this.empStatus = empStatus;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public Long getCollectId() {
    return this.collectId;
  }
  
  public void setCollectId(Long collectId) {
    this.collectId = collectId;
  }
  
  public Long getEmpId() {
    return this.empId;
  }
  
  public void setEmpId(Long empId) {
    this.empId = empId;
  }
  
  public String getEmpName() {
    return this.empName;
  }
  
  public void setEmpName(String empName) {
    this.empName = empName;
  }
  
  public Long getEmpStatus() {
    return this.empStatus;
  }
  
  public void setEmpStatus(Long empStatus) {
    this.empStatus = empStatus;
  }
  
  public Long getRemindCount() {
    return this.remindCount;
  }
  
  public void setRemindCount(Long remindCount) {
    this.remindCount = remindCount;
  }
}
