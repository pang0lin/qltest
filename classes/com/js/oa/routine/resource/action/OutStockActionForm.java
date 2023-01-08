package com.js.oa.routine.resource.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class OutStockActionForm extends ActionForm {
  private String stock;
  
  private String ssDept;
  
  private String ssMan;
  
  private String ptMoney;
  
  private String remark;
  
  private String ssMasterId;
  
  private String makeMan;
  
  private String usePlace;
  
  private String serial;
  
  private String ssOrg;
  
  private String ssOrgName;
  
  private String ssMode;
  
  private String ssStoreMan;
  
  private String ssPicker;
  
  private String ssTypeDefine;
  
  private String ssUseManID;
  
  private String ssUseMan;
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public String getStock() {
    return this.stock;
  }
  
  public void setStock(String stock) {
    this.stock = stock;
  }
  
  public String getSsDept() {
    return this.ssDept;
  }
  
  public void setSsDept(String ssDept) {
    this.ssDept = ssDept;
  }
  
  public String getSsMan() {
    return this.ssMan;
  }
  
  public void setSsMan(String ssMan) {
    this.ssMan = ssMan;
  }
  
  public String getPtMoney() {
    return this.ptMoney;
  }
  
  public void setPtMoney(String ptMoney) {
    this.ptMoney = ptMoney;
  }
  
  public String getRemark() {
    return this.remark;
  }
  
  public void setRemark(String remark) {
    this.remark = remark;
  }
  
  public String getSsMasterId() {
    return this.ssMasterId;
  }
  
  public void setSsMasterId(String ssMasterId) {
    this.ssMasterId = ssMasterId;
  }
  
  public String getMakeMan() {
    return this.makeMan;
  }
  
  public void setMakeMan(String makeMan) {
    this.makeMan = makeMan;
  }
  
  public void setUsePlace(String usePlace) {
    this.usePlace = usePlace;
  }
  
  public String getUsePlace() {
    return this.usePlace;
  }
  
  public String getSerial() {
    return this.serial;
  }
  
  public void setSerial(String serial) {
    this.serial = serial;
  }
  
  public String getSsMode() {
    return this.ssMode;
  }
  
  public void setSsMode(String ssMode) {
    this.ssMode = ssMode;
  }
  
  public String getSsOrg() {
    return this.ssOrg;
  }
  
  public void setSsOrg(String ssOrg) {
    this.ssOrg = ssOrg;
  }
  
  public String getSsOrgName() {
    return this.ssOrgName;
  }
  
  public void setSsOrgName(String ssOrgName) {
    this.ssOrgName = ssOrgName;
  }
  
  public String getSsPicker() {
    return this.ssPicker;
  }
  
  public void setSsPicker(String ssPicker) {
    this.ssPicker = ssPicker;
  }
  
  public String getSsStoreMan() {
    return this.ssStoreMan;
  }
  
  public void setSsStoreMan(String ssStoreMan) {
    this.ssStoreMan = ssStoreMan;
  }
  
  public String getSsTypeDefine() {
    return this.ssTypeDefine;
  }
  
  public void setSsTypeDefine(String ssTypeDefine) {
    this.ssTypeDefine = ssTypeDefine;
  }
  
  public String getSsUseMan() {
    return this.ssUseMan;
  }
  
  public void setSsUseMan(String ssUseMan) {
    this.ssUseMan = ssUseMan;
  }
  
  public String getSsUseManID() {
    return this.ssUseManID;
  }
  
  public void setSsUseManID(String ssUseManID) {
    this.ssUseManID = ssUseManID;
  }
}
