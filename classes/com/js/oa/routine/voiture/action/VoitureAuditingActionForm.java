package com.js.oa.routine.voiture.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class VoitureAuditingActionForm extends ActionForm {
  private String applyId;
  
  private String auditingAccount;
  
  private String auditingStyle = "1";
  
  private String voitureName;
  
  private String empName;
  
  private String orgName;
  
  private String destination;
  
  private String useStartDate;
  
  private String endDate;
  
  private String ycr;
  
  private String departurePlace;
  
  public String getYcr() {
    return this.ycr;
  }
  
  public void setYcr(String ycr) {
    this.ycr = ycr;
  }
  
  public String getDeparturePlace() {
    return this.departurePlace;
  }
  
  public void setDeparturePlace(String departurePlace) {
    this.departurePlace = departurePlace;
  }
  
  public String getApplyId() {
    return this.applyId;
  }
  
  public void setApplyId(String applyId) {
    this.applyId = applyId;
  }
  
  public String getAuditingAccount() {
    return this.auditingAccount;
  }
  
  public void setAuditingAccount(String auditingAccount) {
    this.auditingAccount = auditingAccount;
  }
  
  public String getAuditingStyle() {
    return this.auditingStyle;
  }
  
  public void setAuditingStyle(String auditingStyle) {
    this.auditingStyle = auditingStyle;
  }
  
  public String getVoitureName() {
    return this.voitureName;
  }
  
  public void setVoitureName(String voitureName) {
    this.voitureName = voitureName;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
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
  
  public String getDestination() {
    return this.destination;
  }
  
  public void setDestination(String destination) {
    this.destination = destination;
  }
  
  public String getUseStartDate() {
    return this.useStartDate;
  }
  
  public void setUseStartDate(String useStartDate) {
    this.useStartDate = useStartDate;
  }
  
  public String getEndDate() {
    return this.endDate;
  }
  
  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }
}
