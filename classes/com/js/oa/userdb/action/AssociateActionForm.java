package com.js.oa.userdb.action;

import org.apache.struts.action.ActionForm;

public class AssociateActionForm extends ActionForm {
  private String tablename;
  
  private String operate;
  
  private String tableid;
  
  private String[] id;
  
  private String limitid;
  
  private String domainid;
  
  public String getTablename() {
    return this.tablename;
  }
  
  public void setTablename(String tablename) {
    this.tablename = tablename;
  }
  
  public String getOperate() {
    return this.operate;
  }
  
  public void setOperate(String operate) {
    this.operate = operate;
  }
  
  public String getTableid() {
    return this.tableid;
  }
  
  public void setTableid(String tableid) {
    this.tableid = tableid;
  }
  
  public String[] getId() {
    return this.id;
  }
  
  public void setId(String[] id) {
    this.id = id;
  }
  
  public String getLimitid() {
    return this.limitid;
  }
  
  public void setLimitid(String limitid) {
    this.limitid = limitid;
  }
  
  public String getDomainid() {
    return this.domainid;
  }
  
  public void setDomainid(String domainid) {
    this.domainid = domainid;
  }
}
