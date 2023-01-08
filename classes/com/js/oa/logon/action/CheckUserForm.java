package com.js.oa.logon.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class CheckUserForm extends ActionForm {
  private String userName;
  
  private String userPassword;
  
  private String keyDigest;
  
  public String getUserName() {
    return this.userName;
  }
  
  public void setUserName(String userName) {
    this.userName = userName;
  }
  
  public String getUserPassword() {
    return this.userPassword;
  }
  
  public void setUserPassword(String userPassword) {
    this.userPassword = userPassword;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public String getKeyDigest() {
    return this.keyDigest;
  }
  
  public void setKeyDigest(String keyDigest) {
    this.keyDigest = keyDigest;
  }
}
