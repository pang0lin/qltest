package com.js.oa.userdb.action;

import org.apache.struts.action.ActionForm;

public class FieldActionForm extends ActionForm {
  private String tablename;
  
  private String operate;
  
  private String tableid;
  
  private String fieldname;
  
  private String[] fieldid;
  
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
  
  public String getFieldname() {
    return this.fieldname;
  }
  
  public void setFieldname(String fieldname) {
    this.fieldname = fieldname;
  }
  
  public String[] getFieldid() {
    return this.fieldid;
  }
  
  public void setFieldid(String[] fieldid) {
    this.fieldid = fieldid;
  }
}
