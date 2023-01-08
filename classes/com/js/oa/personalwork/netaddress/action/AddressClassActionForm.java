package com.js.oa.personalwork.netaddress.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class AddressClassActionForm extends ActionForm {
  private String className;
  
  private String done;
  
  private int classIsShare = 0;
  
  public int getClassIsShare() {
    return this.classIsShare;
  }
  
  public void setClassIsShare(int classIsShare) {
    this.classIsShare = classIsShare;
  }
  
  public String getClassName() {
    return this.className;
  }
  
  public void setClassName(String className) {
    this.className = className;
  }
  
  public String getDone() {
    return this.done;
  }
  
  public void setDone(String done) {
    this.done = done;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.className = null;
    this.done = null;
  }
}
