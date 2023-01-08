package com.js.oa.userdb.action;

import org.apache.struts.action.ActionForm;

public class ShowActionForm extends ActionForm {
  private String tableid;
  
  private String operate;
  
  private String fieldname;
  
  private String fieldlist;
  
  private String fieldshow;
  
  private String fieldwidth;
  
  private String fieldvalue;
  
  private String fieldtype;
  
  private String fielddesname;
  
  public String getTableid() {
    return this.tableid;
  }
  
  public void setTableid(String tableid) {
    this.tableid = tableid;
  }
  
  public String getOperate() {
    return this.operate;
  }
  
  public void setOperate(String operate) {
    this.operate = operate;
  }
  
  public String getFieldname() {
    return this.fieldname;
  }
  
  public void setFieldname(String fieldname) {
    this.fieldname = fieldname;
  }
  
  public String getFieldlist() {
    return this.fieldlist;
  }
  
  public void setFieldlist(String fieldlist) {
    this.fieldlist = fieldlist;
  }
  
  public String getFieldshow() {
    return this.fieldshow;
  }
  
  public void setFieldshow(String fieldshow) {
    this.fieldshow = fieldshow;
  }
  
  public String getFieldwidth() {
    return this.fieldwidth;
  }
  
  public void setFieldwidth(String fieldwidth) {
    this.fieldwidth = fieldwidth;
  }
  
  public String getFieldvalue() {
    return this.fieldvalue;
  }
  
  public void setFieldvalue(String fieldvalue) {
    this.fieldvalue = fieldvalue;
  }
  
  public String getFieldtype() {
    return this.fieldtype;
  }
  
  public void setFieldtype(String fieldtype) {
    this.fieldtype = fieldtype;
  }
  
  public String getFielddesname() {
    return this.fielddesname;
  }
  
  public void setFielddesname(String fielddesname) {
    this.fielddesname = fielddesname;
  }
}
