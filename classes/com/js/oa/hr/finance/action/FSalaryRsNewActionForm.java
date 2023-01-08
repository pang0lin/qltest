package com.js.oa.hr.finance.action;

import java.io.Serializable;
import org.apache.struts.action.ActionForm;

public class FSalaryRsNewActionForm extends ActionForm implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private String year;
  
  private String month;
  
  public String getYear() {
    return this.year;
  }
  
  public void setYear(String year) {
    this.year = year;
  }
  
  public String getMonth() {
    return this.month;
  }
  
  public void setMonth(String month) {
    this.month = month;
  }
}
