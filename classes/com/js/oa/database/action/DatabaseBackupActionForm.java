package com.js.oa.database.action;

import org.apache.struts.action.ActionForm;

public class DatabaseBackupActionForm extends ActionForm {
  private int id;
  
  private String date;
  
  public int getId() {
    return this.id;
  }
  
  public void setId(int id) {
    this.id = id;
  }
  
  public String getDate() {
    return this.date;
  }
  
  public void setDate(String date) {
    this.date = date;
  }
}
