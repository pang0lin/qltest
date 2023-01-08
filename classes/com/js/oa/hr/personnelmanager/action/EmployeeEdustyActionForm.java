package com.js.oa.hr.personnelmanager.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class EmployeeEdustyActionForm extends ActionForm {
  private Long id;
  
  private String schools;
  
  private String speciality;
  
  private String education;
  
  private String degree;
  
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
  
  public String getEducation() {
    return this.education;
  }
  
  public void setEducation(String education) {
    this.education = education;
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
  
  public String getSchools() {
    return this.schools;
  }
  
  public void setSchools(String schools) {
    this.schools = schools;
  }
  
  public String getSpeciality() {
    return this.speciality;
  }
  
  public void setSpeciality(String speciality) {
    this.speciality = speciality;
  }
  
  public Long getEmpID() {
    return this.empID;
  }
  
  public void setEmpID(Long empID) {
    this.empID = empID;
  }
  
  public String getDegree() {
    return this.degree;
  }
  
  public void setDegree(String degree) {
    this.degree = degree;
  }
}
