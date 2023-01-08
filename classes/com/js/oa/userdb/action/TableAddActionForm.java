package com.js.oa.userdb.action;

import org.apache.struts.action.ActionForm;

public class TableAddActionForm extends ActionForm {
  private String tablename;
  
  private String tablecode;
  
  private String tabledesname;
  
  private String tablefilepath;
  
  private String tableowner;
  
  private String operate;
  
  private String modelid;
  
  private String domainid;
  
  public String getTablename() {
    return this.tablename;
  }
  
  public void setTablename(String tablename) {
    this.tablename = tablename;
  }
  
  public String getTablecode() {
    return this.tablecode;
  }
  
  public void setTablecode(String tablecode) {
    this.tablecode = tablecode;
  }
  
  public String getTabledesname() {
    return this.tabledesname;
  }
  
  public void setTabledesname(String tabledesname) {
    this.tabledesname = tabledesname;
  }
  
  public String getTablefilepath() {
    return this.tablefilepath;
  }
  
  public void setTablefilepath(String tablefilepath) {
    this.tablefilepath = tablefilepath;
  }
  
  public String getTableowner() {
    return this.tableowner;
  }
  
  public void setTableowner(String tableowner) {
    this.tableowner = tableowner;
  }
  
  public String getOperate() {
    return this.operate;
  }
  
  public void setOperate(String operate) {
    this.operate = operate;
  }
  
  public String getModelid() {
    return this.modelid;
  }
  
  public void setModelid(String modelid) {
    this.modelid = modelid;
  }
  
  public String getDomainid() {
    return this.domainid;
  }
  
  public void setDomainid(String domainid) {
    this.domainid = domainid;
  }
}
