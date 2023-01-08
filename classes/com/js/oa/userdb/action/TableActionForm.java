package com.js.oa.userdb.action;

import org.apache.struts.action.ActionForm;

public class TableActionForm extends ActionForm {
  private String operate;
  
  private String scode;
  
  private String sname;
  
  private String tableid;
  
  private String tablename;
  
  private String[] id;
  
  private String tablecode;
  
  private String tablemodel;
  
  private String tabledesname;
  
  private String tablefilepath;
  
  private String tableowner;
  
  private String tabledate;
  
  private String domainid;
  
  public String[] getId() {
    return this.id;
  }
  
  public void setId(String[] id) {
    this.id = id;
  }
  
  public String getOperate() {
    return this.operate;
  }
  
  public void setOperate(String operate) {
    this.operate = operate;
  }
  
  public String getScode() {
    return this.scode;
  }
  
  public void setScode(String scode) {
    this.scode = scode;
  }
  
  public String getSname() {
    return this.sname;
  }
  
  public void setSname(String sname) {
    this.sname = sname;
  }
  
  public String getTableid() {
    return this.tableid;
  }
  
  public void setTableid(String tableid) {
    this.tableid = tableid;
  }
  
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
  
  public String getTablemodel() {
    return this.tablemodel;
  }
  
  public void setTablemodel(String tablemodel) {
    this.tablemodel = tablemodel;
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
  
  public String getTabledate() {
    return this.tabledate;
  }
  
  public void setTabledate(String tabledate) {
    this.tabledate = tabledate;
  }
  
  public String getDomainid() {
    return this.domainid;
  }
  
  public void setDomainid(String domainid) {
    this.domainid = domainid;
  }
}
