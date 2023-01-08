package com.js.oa.hr.personnelmanager.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class EmployeeCompetenceActionForm extends ActionForm {
  private Long id;
  
  private String certificateName;
  
  private String beginDate;
  
  private String endDate;
  
  private String awardUnits;
  
  private Long empID;
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public String getAwardUnits() {
    return this.awardUnits;
  }
  
  public void setAwardUnits(String awardUnits) {
    this.awardUnits = awardUnits;
  }
  
  public String getBeginDate() {
    return this.beginDate;
  }
  
  public void setBeginDate(String beginDate) {
    this.beginDate = beginDate;
  }
  
  public String getCertificateName() {
    return this.certificateName;
  }
  
  public void setCertificateName(String certificateName) {
    this.certificateName = certificateName;
  }
  
  public Long getEmpID() {
    return this.empID;
  }
  
  public void setEmpID(Long empID) {
    this.empID = empID;
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
}
