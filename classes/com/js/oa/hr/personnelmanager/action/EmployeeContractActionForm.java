package com.js.oa.hr.personnelmanager.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class EmployeeContractActionForm extends ActionForm {
  private Long id;
  
  private Long empID;
  
  private String givenDate;
  
  private String contractStyle;
  
  private String beginDate;
  
  private String endDate;
  
  private String contractNO;
  
  private String contractType;
  
  private String contractLimit;
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.contractType = "0";
    this.contractLimit = "2";
  }
  
  public String getBeginDate() {
    return this.beginDate;
  }
  
  public void setBeginDate(String beginDate) {
    this.beginDate = beginDate;
  }
  
  public String getContractStyle() {
    return this.contractStyle;
  }
  
  public void setContractStyle(String contractStyle) {
    this.contractStyle = contractStyle;
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
  
  public String getGivenDate() {
    return this.givenDate;
  }
  
  public void setGivenDate(String givenDate) {
    this.givenDate = givenDate;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public String getContractType() {
    return this.contractType;
  }
  
  public String getContractLimit() {
    return this.contractLimit;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public void setContractType(String contractType) {
    this.contractType = contractType;
  }
  
  public void setContractLimit(String contractLimit) {
    this.contractLimit = contractLimit;
  }
  
  public String getContractNO() {
    return this.contractNO;
  }
  
  public void setContractNO(String contractNO) {
    this.contractNO = contractNO;
  }
}
