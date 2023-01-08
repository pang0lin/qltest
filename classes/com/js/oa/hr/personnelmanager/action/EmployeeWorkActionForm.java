package com.js.oa.hr.personnelmanager.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class EmployeeWorkActionForm extends ActionForm {
  private Long id;
  
  private String workunit;
  
  private String workduty;
  
  private String proveperson;
  
  private String telephone;
  
  private String workMemo;
  
  private String beginDate;
  
  private String endDate;
  
  private Long empID;
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public String getBeginDate() {
    return this.beginDate;
  }
  
  public void setBeginDate(String beginDate) {
    this.beginDate = beginDate;
  }
  
  public String getEndDate() {
    return this.endDate;
  }
  
  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public Long getEmpID() {
    return this.empID;
  }
  
  public String getProveperson() {
    return this.proveperson;
  }
  
  public String getTelephone() {
    return this.telephone;
  }
  
  public String getWorkduty() {
    return this.workduty;
  }
  
  public String getWorkMemo() {
    return this.workMemo;
  }
  
  public String getWorkunit() {
    return this.workunit;
  }
  
  public void setEmpID(Long empID) {
    this.empID = empID;
  }
  
  public void setProveperson(String proveperson) {
    this.proveperson = proveperson;
  }
  
  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }
  
  public void setWorkduty(String workduty) {
    this.workduty = workduty;
  }
  
  public void setWorkMemo(String workMemo) {
    this.workMemo = workMemo;
  }
  
  public void setWorkunit(String workunit) {
    this.workunit = workunit;
  }
}
