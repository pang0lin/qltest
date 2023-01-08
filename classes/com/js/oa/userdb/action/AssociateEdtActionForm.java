package com.js.oa.userdb.action;

import org.apache.struts.action.ActionForm;

public class AssociateEdtActionForm extends ActionForm {
  private String limitid;
  
  private String limitpryfield;
  
  private String limitprytable;
  
  public String getLimitid() {
    return this.limitid;
  }
  
  public void setLimitid(String limitid) {
    this.limitid = limitid;
  }
  
  public String getLimitpryfield() {
    return this.limitpryfield;
  }
  
  public void setLimitpryfield(String limitpryfield) {
    this.limitpryfield = limitpryfield;
  }
  
  public String getLimitprytable() {
    return this.limitprytable;
  }
  
  public void setLimitprytable(String limitprytable) {
    this.limitprytable = limitprytable;
  }
}
