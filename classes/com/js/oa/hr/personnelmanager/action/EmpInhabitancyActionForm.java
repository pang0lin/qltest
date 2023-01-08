package com.js.oa.hr.personnelmanager.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class EmpInhabitancyActionForm extends ActionForm {
  private Long id;
  
  private String beginDate;
  
  private String continueDate;
  
  private String yearLimit;
  
  private String memos;
  
  private String empID;
  
  private String empName;
  
  private String empOrg;
  
  private String orgName;
  
  public Long getId() {
    return this.id;
  }
  
  public String getMemos() {
    return this.memos;
  }
  
  public String getEmpID() {
    return this.empID;
  }
  
  public String getContinueDate() {
    return this.continueDate;
  }
  
  public String getBeginDate() {
    return this.beginDate;
  }
  
  public void setYearLimit(String yearLimit) {
    this.yearLimit = yearLimit;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public void setMemos(String memos) {
    this.memos = memos;
  }
  
  public void setEmpID(String empID) {
    this.empID = empID;
  }
  
  public void setContinueDate(String continueDate) {
    this.continueDate = continueDate;
  }
  
  public void setBeginDate(String beginDate) {
    this.beginDate = beginDate;
  }
  
  public void setEmpOrg(String empOrg) {
    this.empOrg = empOrg;
  }
  
  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }
  
  public void setEmpName(String empName) {
    this.empName = empName;
  }
  
  public String getYearLimit() {
    return this.yearLimit;
  }
  
  public String getEmpOrg() {
    return this.empOrg;
  }
  
  public String getOrgName() {
    return this.orgName;
  }
  
  public String getEmpName() {
    return this.empName;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
}
