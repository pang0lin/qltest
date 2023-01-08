package com.js.system.vo.usermanager;

import java.io.Serializable;

public class EmployeeOrgVO implements Serializable {
  private Long empId;
  
  private String orgId;
  
  public Long getEmpId() {
    return this.empId;
  }
  
  public String getOrgId() {
    return this.orgId;
  }
  
  public void setEmpId(Long empId) {
    this.empId = empId;
  }
  
  public void setOrgId(String orgId) {
    this.orgId = orgId;
  }
}
