package com.js.oa.userdb.action;

import org.apache.struts.action.ActionForm;

public class ShowEdtActionForm extends ActionForm {
  private String fieldname;
  
  private String fieldlist;
  
  private String fieldshow;
  
  private String fieldwidth;
  
  private String fieldvalue;
  
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
}
