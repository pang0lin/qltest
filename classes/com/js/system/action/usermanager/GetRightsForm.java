package com.js.system.action.usermanager;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class GetRightsForm extends ActionForm {
  private String roleId;
  
  private String roleIds;
  
  private String hadSelectedRight;
  
  public String getRoleId() {
    return this.roleId;
  }
  
  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }
  
  public String getRoleIds() {
    return this.roleIds;
  }
  
  public void setRoleIds(String roleIds) {
    this.roleIds = roleIds;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public String getHadSelectedRight() {
    return this.hadSelectedRight;
  }
  
  public void setHadSelectedRight(String hadSelectedRight) {
    this.hadSelectedRight = hadSelectedRight;
  }
}
