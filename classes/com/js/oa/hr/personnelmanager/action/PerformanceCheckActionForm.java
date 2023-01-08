package com.js.oa.hr.personnelmanager.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class PerformanceCheckActionForm extends ActionForm {
  private Long id;
  
  private String checkYear;
  
  private String checkMonth;
  
  private String checkMark;
  
  private String addMark;
  
  private String addReason;
  
  private String deductMark;
  
  private String deductReason;
  
  private String empID;
  
  private String empName;
  
  private String empOrg;
  
  private String orgName;
  
  public Long getId() {
    return this.id;
  }
  
  public String getCheckYear() {
    return this.checkYear;
  }
  
  public String getEmpID() {
    return this.empID;
  }
  
  public String getCheckMonth() {
    return this.checkMonth;
  }
  
  public String getAddReason() {
    return this.addReason;
  }
  
  public String getDeductReason() {
    return this.deductReason;
  }
  
  public String getCheckMark() {
    return this.checkMark;
  }
  
  public String getAddMark() {
    return this.addMark;
  }
  
  public void setDeductMark(String deductMark) {
    this.deductMark = deductMark;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public void setCheckYear(String checkYear) {
    this.checkYear = checkYear;
  }
  
  public void setEmpID(String empID) {
    this.empID = empID;
  }
  
  public void setCheckMonth(String checkMonth) {
    this.checkMonth = checkMonth;
  }
  
  public void setAddReason(String addReason) {
    this.addReason = addReason;
  }
  
  public void setDeductReason(String deductReason) {
    this.deductReason = deductReason;
  }
  
  public void setCheckMark(String checkMark) {
    this.checkMark = checkMark;
  }
  
  public void setAddMark(String addMark) {
    this.addMark = addMark;
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
  
  public String getDeductMark() {
    return this.deductMark;
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
