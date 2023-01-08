package com.js.oa.logon.action;

import org.apache.struts.action.ActionForm;

public class ResetPasswordForm extends ActionForm {
  private String userAccount;
  
  private String oldPassword;
  
  private String newPassword;
  
  private String confirmNewPassword;
  
  public String getUserAccount() {
    return this.userAccount;
  }
  
  public void setUserAccount(String userAccount) {
    this.userAccount = userAccount;
  }
  
  public String getOldPassword() {
    return this.oldPassword;
  }
  
  public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
  }
  
  public String getNewPassword() {
    return this.newPassword;
  }
  
  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }
  
  public String getConfirmNewPassword() {
    return this.confirmNewPassword;
  }
  
  public void setConfirmNewPassword(String confirmPassword) {
    this.confirmNewPassword = confirmPassword;
  }
}
