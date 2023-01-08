package com.js.oa.routine.resource.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class IntoStockActionForm extends ActionForm {
  private String stock;
  
  private String supp;
  
  private String man;
  
  private String remark;
  
  private String ptMoney;
  
  private String makeMan;
  
  private String ptMasterId;
  
  private String ptOrg;
  
  private String ptOrgName;
  
  private String ptMode;
  
  private String ptStoreMan;
  
  private String ptBuyer;
  
  private String ptTypeDefine;
  
  private String ptHandleName;
  
  private String invoiceNO;
  
  private String serial;
  
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
  
  public String getSupp() {
    return this.supp;
  }
  
  public void setSupp(String supp) {
    this.supp = supp;
  }
  
  public String getMan() {
    return this.man;
  }
  
  public void setMan(String man) {
    this.man = man;
  }
  
  public String getRemark() {
    return this.remark;
  }
  
  public void setRemark(String remark) {
    this.remark = remark;
  }
  
  public String getPtMoney() {
    return this.ptMoney;
  }
  
  public void setPtMoney(String ptMoney) {
    this.ptMoney = ptMoney;
  }
  
  public String getMakeMan() {
    return this.makeMan;
  }
  
  public void setMakeMan(String makeMan) {
    this.makeMan = makeMan;
  }
  
  public String getPtMasterId() {
    return this.ptMasterId;
  }
  
  public void setPtMasterId(String ptMasterId) {
    this.ptMasterId = ptMasterId;
  }
  
  public String getSerial() {
    return this.serial;
  }
  
  public void setSerial(String serial) {
    this.serial = serial;
  }
  
  public String getPtBuyer() {
    return this.ptBuyer;
  }
  
  public void setPtBuyer(String ptBuyer) {
    this.ptBuyer = ptBuyer;
  }
  
  public String getPtMode() {
    return this.ptMode;
  }
  
  public void setPtMode(String ptMode) {
    this.ptMode = ptMode;
  }
  
  public String getPtOrg() {
    return this.ptOrg;
  }
  
  public void setPtOrg(String ptOrg) {
    this.ptOrg = ptOrg;
  }
  
  public String getPtOrgName() {
    return this.ptOrgName;
  }
  
  public void setPtOrgName(String ptOrgName) {
    this.ptOrgName = ptOrgName;
  }
  
  public String getPtStoreMan() {
    return this.ptStoreMan;
  }
  
  public void setPtStoreMan(String ptStoreMan) {
    this.ptStoreMan = ptStoreMan;
  }
  
  public String getPtTypeDefine() {
    return this.ptTypeDefine;
  }
  
  public void setPtTypeDefine(String ptTypeDefine) {
    this.ptTypeDefine = ptTypeDefine;
  }
  
  public String getInvoiceNO() {
    return this.invoiceNO;
  }
  
  public void setInvoiceNO(String invoiceNO) {
    this.invoiceNO = invoiceNO;
  }
  
  public String getPtHandleName() {
    return this.ptHandleName;
  }
  
  public void setPtHandleName(String ptHandleName) {
    this.ptHandleName = ptHandleName;
  }
}
