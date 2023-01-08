package com.js.doc.doc.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class GovComeFileUnitActionForm extends ActionForm {
  private String comeFileUnitName;
  
  public String getComeFileUnitName() {
    return this.comeFileUnitName;
  }
  
  public void setComeFileUnitName(String comeFileUnitName) {
    this.comeFileUnitName = comeFileUnitName;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.comeFileUnitName = null;
  }
}
