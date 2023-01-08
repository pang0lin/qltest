package com.js.oa.userdb.action;

import org.apache.struts.action.ActionForm;

public class TableSearchActionForm extends ActionForm {
  private String sname;
  
  public String getSname() {
    return this.sname;
  }
  
  public void setSname(String sname) {
    this.sname = sname;
  }
}
