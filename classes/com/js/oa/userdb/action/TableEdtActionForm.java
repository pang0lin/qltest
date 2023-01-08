package com.js.oa.userdb.action;

import org.apache.struts.action.ActionForm;

public class TableEdtActionForm extends ActionForm {
  private String tablecode;
  
  private String tabledesname;
  
  private String tablename;
  
  private String tablemodel;
  
  private String tablefilepath;
  
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
  
  public String getTablename() {
    return this.tablename;
  }
  
  public void setTablename(String tablename) {
    this.tablename = tablename;
  }
  
  public String getTablemodel() {
    return this.tablemodel;
  }
  
  public void setTablemodel(String tablemodel) {
    this.tablemodel = tablemodel;
  }
  
  public String getTablefilepath() {
    return this.tablefilepath;
  }
  
  public void setTablefilepath(String tablefilepath) {
    this.tablefilepath = tablefilepath;
  }
}
