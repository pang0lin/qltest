package com.js.oa.info.infomanager.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class InfoIframeActionForm extends ActionForm {
  private String templateContent;
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public String getTemplateContent() {
    return this.templateContent;
  }
  
  public void setTemplateContent(String templateContent) {
    this.templateContent = templateContent;
  }
}
