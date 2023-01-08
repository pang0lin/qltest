package com.js.oa.portal.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class CustomDeskUrlActionForm extends ActionForm {
  private Long id;
  
  private String urlname;
  
  private String urlapp;
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getUrlname() {
    return this.urlname;
  }
  
  public void setUrlname(String urlname) {
    this.urlname = urlname;
  }
  
  public String getUrlapp() {
    return this.urlapp;
  }
  
  public void setUrlapp(String urlapp) {
    this.urlapp = urlapp;
  }
}
