package com.js.system.manager.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class SearchUserForm extends ActionForm {
  private String userCName;
  
  private String userEName;
  
  private String userOrganization;
  
  public String getUserCName() {
    return this.userCName;
  }
  
  public void setUserCName(String userCName) {
    this.userCName = userCName;
  }
  
  public String getUserEName() {
    return this.userEName;
  }
  
  public void setUserEName(String userEName) {
    this.userEName = userEName;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.userCName = null;
    this.userEName = null;
  }
  
  public String getUserOrganization() {
    return this.userOrganization;
  }
  
  public void setUserOrganization(String userOrganization) {
    this.userOrganization = userOrganization;
  }
}
