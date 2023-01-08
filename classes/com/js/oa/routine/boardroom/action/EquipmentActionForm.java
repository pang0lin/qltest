package com.js.oa.routine.boardroom.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class EquipmentActionForm extends ActionForm {
  private String saveType;
  
  private String name;
  
  private String code;
  
  private Float cost = Float.valueOf("0.00");
  
  private String manageDept;
  
  private String manageDeptName;
  
  private String remark;
  
  private String borrower;
  
  private String borrowerName;
  
  private String borrowerOrg;
  
  private String borrowerOrgName;
  
  private String purpose;
  
  private String standard;
  
  private String storeManID;
  
  private String storeManName;
  
  private Integer status;
  
  public String getSaveType() {
    return this.saveType;
  }
  
  public void setSaveType(String saveType) {
    this.saveType = saveType;
  }
  
  public Float getCost() {
    return this.cost;
  }
  
  public Integer getStatus() {
    return this.status;
  }
  
  public void setStatus(Integer status) {
    this.status = status;
  }
  
  public void setCost(Float cost) {
    this.cost = cost;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getRemark() {
    return this.remark;
  }
  
  public void setRemark(String remark) {
    this.remark = remark;
  }
  
  public String getCode() {
    return this.code;
  }
  
  public void setCode(String code) {
    this.code = code;
  }
  
  public String getManageDept() {
    return this.manageDept;
  }
  
  public void setManageDept(String manageDept) {
    this.manageDept = manageDept;
  }
  
  public String getManageDeptName() {
    return this.manageDeptName;
  }
  
  public void setManageDeptName(String manageDeptName) {
    this.manageDeptName = manageDeptName;
  }
  
  public String getBorrower() {
    return this.borrower;
  }
  
  public void setBorrower(String borrower) {
    this.borrower = borrower;
  }
  
  public String getBorrowerName() {
    return this.borrowerName;
  }
  
  public void setBorrowerName(String borrowerName) {
    this.borrowerName = borrowerName;
  }
  
  public String getBorrowerOrg() {
    return this.borrowerOrg;
  }
  
  public void setBorrowerOrg(String borrowerOrg) {
    this.borrowerOrg = borrowerOrg;
  }
  
  public String getBorrowerOrgName() {
    return this.borrowerOrgName;
  }
  
  public void setBorrowerOrgName(String borrowerOrgName) {
    this.borrowerOrgName = borrowerOrgName;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.saveType = null;
    this.name = null;
    this.code = null;
    this.cost = Float.valueOf("0.00");
    this.manageDept = null;
    this.manageDeptName = null;
    this.remark = null;
    this.borrower = null;
    this.borrowerName = null;
    this.borrowerOrg = null;
    this.borrowerOrgName = null;
    this.purpose = null;
    this.standard = null;
    this.storeManID = null;
    this.storeManName = null;
  }
  
  public String getPurpose() {
    return this.purpose;
  }
  
  public void setPurpose(String purpose) {
    this.purpose = purpose;
  }
  
  public String getStandard() {
    return this.standard;
  }
  
  public void setStandard(String standard) {
    this.standard = standard;
  }
  
  public String getStoreManID() {
    return this.storeManID;
  }
  
  public void setStoreManID(String storeManID) {
    this.storeManID = storeManID;
  }
  
  public String getStoreManName() {
    return this.storeManName;
  }
  
  public void setStoreManName(String storeManName) {
    this.storeManName = storeManName;
  }
}
