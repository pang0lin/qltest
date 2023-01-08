package com.js.system.action.rightmanager;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class RightActionForm extends ActionForm {
  private String rightName;
  
  private String rightId;
  
  private String rightType;
  
  private String rightDescription;
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public String getRightName() {
    return this.rightName;
  }
  
  public void setRightName(String rightName) {
    this.rightName = rightName;
  }
  
  public String getRightId() {
    return this.rightId;
  }
  
  public void setRightId(String rightId) {
    this.rightId = rightId;
  }
  
  public String getRightType() {
    return this.rightType;
  }
  
  public void setRightType(String rightType) {
    this.rightType = rightType;
  }
  
  public String getRightDescription() {
    return this.rightDescription;
  }
  
  public void setRightDescription(String rightDescription) {
    this.rightDescription = rightDescription;
  }
}
