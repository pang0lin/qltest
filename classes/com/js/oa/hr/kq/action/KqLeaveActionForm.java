package com.js.oa.hr.kq.action;

import org.apache.struts.action.ActionForm;

public class KqLeaveActionForm extends ActionForm {
  private String cause;
  
  private int leaveType;
  
  private String empId;
  
  private String empName;
  
  public String getEmpId() {
    return this.empId;
  }
  
  public void setEmpId(String empId) {
    this.empId = empId;
  }
  
  public String getEmpName() {
    return this.empName;
  }
  
  public void setEmpName(String empName) {
    this.empName = empName;
  }
  
  public String getCause() {
    return this.cause;
  }
  
  public void setCause(String cause) {
    this.cause = cause;
  }
  
  public int getLeaveType() {
    return this.leaveType;
  }
  
  public void setLeaveType(int leaveType) {
    this.leaveType = leaveType;
  }
}
