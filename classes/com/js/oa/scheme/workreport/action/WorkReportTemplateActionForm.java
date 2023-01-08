package com.js.oa.scheme.workreport.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class WorkReportTemplateActionForm extends ActionForm {
  private String TemplateContent;
  
  private String TemplateDescript;
  
  private String TemplateName;
  
  private String userOrgGroup;
  
  private String templateUseRange;
  
  public String getTemplateContent() {
    return this.TemplateContent;
  }
  
  public void setTemplateContent(String TemplateContent) {
    this.TemplateContent = TemplateContent;
  }
  
  public String getTemplateDescript() {
    return this.TemplateDescript;
  }
  
  public void setTemplateDescript(String TemplateDescript) {
    this.TemplateDescript = TemplateDescript;
  }
  
  public String getTemplateName() {
    return this.TemplateName;
  }
  
  public void setTemplateName(String TemplateName) {
    this.TemplateName = TemplateName;
  }
  
  public String getUserOrgGroup() {
    return this.userOrgGroup;
  }
  
  public void setUserOrgGroup(String userOrgGroup) {
    this.userOrgGroup = userOrgGroup;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.TemplateContent = null;
    this.TemplateDescript = null;
    this.TemplateName = null;
    this.userOrgGroup = null;
    this.templateUseRange = null;
  }
  
  public String getTemplateUseRange() {
    return this.templateUseRange;
  }
  
  public void setTemplateUseRange(String templateUseRange) {
    this.templateUseRange = templateUseRange;
  }
}
