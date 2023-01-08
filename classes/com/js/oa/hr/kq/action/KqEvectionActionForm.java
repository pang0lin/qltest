package com.js.oa.hr.kq.action;

import org.apache.struts.action.ActionForm;

public class KqEvectionActionForm extends ActionForm {
  private String place;
  
  private String cause;
  
  public String getCause() {
    return this.cause;
  }
  
  public void setCause(String cause) {
    this.cause = cause;
  }
  
  public String getPlace() {
    return this.place;
  }
  
  public void setPlace(String place) {
    this.place = place;
  }
}
