package com.js.oa.hr.personnelmanager.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class StationActionForm extends ActionForm {
  private String name;
  
  private String describe;
  
  private String no;
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getDescribe() {
    return this.describe;
  }
  
  public void setDescribe(String describe) {
    this.describe = describe;
  }
  
  public String getNo() {
    return this.no;
  }
  
  public void setNo(String no) {
    this.no = no;
  }
}
