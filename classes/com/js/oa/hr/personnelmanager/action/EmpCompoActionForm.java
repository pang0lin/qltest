package com.js.oa.hr.personnelmanager.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class EmpCompoActionForm extends ActionForm {
  private Long id;
  
  private String occurDate;
  
  private String accident;
  
  private String compensateUnit;
  
  private String insuranceCompany;
  
  private String compensateMoney;
  
  private String appraisalLevel;
  
  private String empID;
  
  private String empName;
  
  private String empOrg;
  
  private String orgName;
  
  public String getAccident() {
    return this.accident;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public String getEmpID() {
    return this.empID;
  }
  
  public String getAppraisalLevel() {
    return this.appraisalLevel;
  }
  
  public String getInsuranceCompany() {
    return this.insuranceCompany;
  }
  
  public String getCompensateUnit() {
    return this.compensateUnit;
  }
  
  public String getOccurDate() {
    return this.occurDate;
  }
  
  public void setCompensateMoney(String compensateMoney) {
    this.compensateMoney = compensateMoney;
  }
  
  public void setAccident(String accident) {
    this.accident = accident;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public void setEmpID(String empID) {
    this.empID = empID;
  }
  
  public void setAppraisalLevel(String appraisalLevel) {
    this.appraisalLevel = appraisalLevel;
  }
  
  public void setInsuranceCompany(String insuranceCompany) {
    this.insuranceCompany = insuranceCompany;
  }
  
  public void setCompensateUnit(String compensateUnit) {
    this.compensateUnit = compensateUnit;
  }
  
  public void setOccurDate(String occurDate) {
    this.occurDate = occurDate;
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
  
  public String getCompensateMoney() {
    return this.compensateMoney;
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
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.appraisalLevel = "0";
  }
}
