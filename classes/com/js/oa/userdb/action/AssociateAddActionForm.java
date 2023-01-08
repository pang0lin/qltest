package com.js.oa.userdb.action;

import org.apache.struts.action.ActionForm;

public class AssociateAddActionForm extends ActionForm {
  private String tablename;
  
  private String tableid;
  
  private String limitfield;
  
  private String limitprytable;
  
  private String limitpryfield;
  
  private String operate;
  
  private String user;
  
  private String domainid;
  
  public String getTablename() {
    return this.tablename;
  }
  
  public void setTablename(String tablename) {
    this.tablename = tablename;
  }
  
  public String getTableid() {
    return this.tableid;
  }
  
  public void setTableid(String tableid) {
    this.tableid = tableid;
  }
  
  public String getLimitfield() {
    return this.limitfield;
  }
  
  public void setLimitfield(String limitfield) {
    this.limitfield = limitfield;
  }
  
  public String getLimitprytable() {
    return this.limitprytable;
  }
  
  public void setLimitprytable(String limitprytable) {
    this.limitprytable = limitprytable;
  }
  
  public String getLimitpryfield() {
    return this.limitpryfield;
  }
  
  public void setLimitpryfield(String limitpryfield) {
    this.limitpryfield = limitpryfield;
  }
  
  public String getOperate() {
    return this.operate;
  }
  
  public void setOperate(String operate) {
    this.operate = operate;
  }
  
  public String getUser() {
    return this.user;
  }
  
  public void setUser(String user) {
    this.user = user;
  }
  
  public String getDomainid() {
    return this.domainid;
  }
  
  public void setDomainid(String domainid) {
    this.domainid = domainid;
  }
}
