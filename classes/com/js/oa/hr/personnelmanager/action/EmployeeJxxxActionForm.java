package com.js.oa.hr.personnelmanager.action;

import org.apache.struts.action.ActionForm;

public class EmployeeJxxxActionForm extends ActionForm {
  private String year;
  
  private String rank;
  
  public String getYear() {
    return this.year;
  }
  
  public void setYear(String year) {
    this.year = year;
  }
  
  public String getRank() {
    return this.rank;
  }
  
  public void setRank(String rank) {
    this.rank = rank;
  }
}
