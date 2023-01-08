package com.js.oa.jsflow.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class WFPackageActionForm extends ActionForm {
  private String packageDescription;
  
  private String packageName;
  
  private String wfPackageId;
  
  private int orderCode;
  
  public String getPackageDescription() {
    return this.packageDescription;
  }
  
  public void setPackageDescription(String packageDescription) {
    this.packageDescription = packageDescription;
  }
  
  public String getPackageName() {
    return this.packageName;
  }
  
  public void setPackageName(String packageName) {
    this.packageName = packageName;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public String getWfPackageId() {
    return this.wfPackageId;
  }
  
  public void setWfPackageId(String wfPackageId) {
    this.wfPackageId = wfPackageId;
  }
  
  public int getOrderCode() {
    return this.orderCode;
  }
  
  public void setOrderCode(int orderCode) {
    this.orderCode = orderCode;
  }
}
