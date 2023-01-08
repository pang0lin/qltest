package com.js.oa.hr.kq.action;

import org.apache.struts.action.ActionForm;

public class KqOvertimeActionForm extends ActionForm {
  private String cause;
  
  private String attendUserName;
  
  private String userOrgGroup;
  
  public String getCause() {
    return this.cause;
  }
  
  public void setCause(String cause) {
    this.cause = cause;
  }
  
  public String getAttendUserName() {
    return this.attendUserName;
  }
  
  public void setAttendUserName(String attendUserName) {
    this.attendUserName = attendUserName;
  }
  
  public String getUserOrgGroup() {
    return this.userOrgGroup;
  }
  
  public void setUserOrgGroup(String userOrgGroup) {
    this.userOrgGroup = userOrgGroup;
  }
}
