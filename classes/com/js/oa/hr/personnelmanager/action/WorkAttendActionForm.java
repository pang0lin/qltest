package com.js.oa.hr.personnelmanager.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class WorkAttendActionForm extends ActionForm {
  private String emp;
  
  private String org;
  
  private String year;
  
  private String month;
  
  private String empName;
  
  private String orgName;
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public String getEmp() {
    return this.emp;
  }
  
  public void setEmp(String emp) {
    this.emp = emp;
  }
  
  public String getOrg() {
    return this.org;
  }
  
  public void setOrg(String org) {
    this.org = org;
  }
  
  public String getYear() {
    return this.year;
  }
  
  public void setYear(String year) {
    this.year = year;
  }
  
  public String getMonth() {
    return this.month;
  }
  
  public void setMonth(String month) {
    this.month = month;
  }
  
  public String getEmpName() {
    return this.empName;
  }
  
  public void setEmpName(String empName) {
    this.empName = empName;
  }
  
  public String getOrgName() {
    return this.orgName;
  }
  
  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }
}
