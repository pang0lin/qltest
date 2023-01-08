package com.js.oa.hr.personnelmanager.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class EmpChangeActionForm extends ActionForm {
  private String saveType;
  
  private Long empChangeEmpId;
  
  private String empChangeEmpName;
  
  private Long empChangeOldOrg;
  
  private String empChangeOldOrgName;
  
  private Long empChangeNewOrg;
  
  private String empChangeNewOrgName;
  
  private String empChangeChangeType;
  
  private Long empChangeOldDuty;
  
  private Long empChangeNewDuty;
  
  private String empChangeType;
  
  private String beginDate;
  
  private String endDate;
  
  private String htrq;
  
  public String getSaveType() {
    return this.saveType;
  }
  
  public void setSaveType(String saveType) {
    this.saveType = saveType;
  }
  
  public String getEmpChangeChangeType() {
    return this.empChangeChangeType;
  }
  
  public void setEmpChangeChangeType(String empChangeChangeType) {
    this.empChangeChangeType = empChangeChangeType;
  }
  
  public Long getEmpChangeEmpId() {
    return this.empChangeEmpId;
  }
  
  public void setEmpChangeEmpId(Long empChangeEmpId) {
    this.empChangeEmpId = empChangeEmpId;
  }
  
  public Long getEmpChangeNewOrg() {
    return this.empChangeNewOrg;
  }
  
  public void setEmpChangeNewOrg(Long empChangeNewOrg) {
    this.empChangeNewOrg = empChangeNewOrg;
  }
  
  public Long getEmpChangeOldOrg() {
    return this.empChangeOldOrg;
  }
  
  public void setEmpChangeOldOrg(Long empChangeOldOrg) {
    this.empChangeOldOrg = empChangeOldOrg;
  }
  
  public String getEmpChangeEmpName() {
    return this.empChangeEmpName;
  }
  
  public void setEmpChangeEmpName(String empChangeEmpName) {
    this.empChangeEmpName = empChangeEmpName;
  }
  
  public String getEmpChangeNewOrgName() {
    return this.empChangeNewOrgName;
  }
  
  public void setEmpChangeNewOrgName(String empChangeNewOrgName) {
    this.empChangeNewOrgName = empChangeNewOrgName;
  }
  
  public String getEmpChangeOldOrgName() {
    return this.empChangeOldOrgName;
  }
  
  public void setEmpChangeOldOrgName(String empChangeOldOrgName) {
    this.empChangeOldOrgName = empChangeOldOrgName;
  }
  
  public String getEmpChangeType() {
    return this.empChangeType;
  }
  
  public void setEmpChangeType(String empChangeType) {
    this.empChangeType = empChangeType;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.saveType = null;
    this.empChangeOldOrg = null;
    this.empChangeNewOrg = null;
    this.empChangeChangeType = null;
    this.empChangeEmpName = null;
    this.empChangeOldOrgName = null;
    this.empChangeNewOrgName = null;
    this.empChangeType = null;
    this.empChangeEmpId = null;
  }
  
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
  
  public String getHtrq() {
    return this.htrq;
  }
  
  public void setHtrq(String htrq) {
    this.htrq = htrq;
  }
  
  public Long getEmpChangeNewDuty() {
    return this.empChangeNewDuty;
  }
  
  public void setEmpChangeNewDuty(Long empChangeNewDuty) {
    this.empChangeNewDuty = empChangeNewDuty;
  }
  
  public Long getEmpChangeOldDuty() {
    return this.empChangeOldDuty;
  }
  
  public void setEmpChangeOldDuty(Long empChangeOldDuty) {
    this.empChangeOldDuty = empChangeOldDuty;
  }
}
