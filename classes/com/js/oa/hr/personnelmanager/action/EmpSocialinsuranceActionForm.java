package com.js.oa.hr.personnelmanager.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class EmpSocialinsuranceActionForm extends ActionForm {
  private Long id;
  
  private String payMonth;
  
  private String payType;
  
  private String payBase;
  
  private String stopMonth;
  
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
  
  public String getPayType() {
    return this.payType;
  }
  
  public String getStopMonth() {
    return this.stopMonth;
  }
  
  public String getPayMonth() {
    return this.payMonth;
  }
  
  public void setPayBase(String payBase) {
    this.payBase = payBase;
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
  
  public void setPayType(String payType) {
    this.payType = payType;
  }
  
  public void setStopMonth(String stopMonth) {
    this.stopMonth = stopMonth;
  }
  
  public void setPayMonth(String payMonth) {
    this.payMonth = payMonth;
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
  
  public String getPayBase() {
    return this.payBase;
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
