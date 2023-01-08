package com.js.oa.info.templatemanager.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class TemplateActionForm extends ActionForm {
  private String con;
  
  private String done;
  
  private String userOrgGroup;
  
  private String templateContent;
  
  private String templateRemark;
  
  private String templateTitle;
  
  private String useRangeName;
  
  private String module;
  
  private String ispublic;
  
  public String getModule() {
    return this.module;
  }
  
  public void setModule(String module) {
    this.module = module;
  }
  
  public String getIspublic() {
    return this.ispublic;
  }
  
  public void setIspublic(String ispublic) {
    this.ispublic = ispublic;
  }
  
  public String getCon() {
    return this.con;
  }
  
  public void setCon(String con) {
    this.con = con;
  }
  
  public String getDone() {
    return this.done;
  }
  
  public void setDone(String done) {
    this.done = done;
  }
  
  public String getUserOrgGroup() {
    return this.userOrgGroup;
  }
  
  public void setUserOrgGroup(String userOrgGroup) {
    this.userOrgGroup = userOrgGroup;
  }
  
  public String getTemplateContent() {
    return this.templateContent;
  }
  
  public void setTemplateContent(String templateContent) {
    this.templateContent = templateContent;
  }
  
  public String getTemplateRemark() {
    return this.templateRemark;
  }
  
  public void setTemplateRemark(String templateRemark) {
    this.templateRemark = templateRemark;
  }
  
  public String getTemplateTitle() {
    return this.templateTitle;
  }
  
  public void setTemplateTitle(String templateTitle) {
    this.templateTitle = templateTitle;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.con = null;
    this.done = null;
    this.userOrgGroup = null;
    this.useRangeName = null;
    this.templateContent = null;
    this.templateRemark = null;
    this.templateTitle = null;
  }
  
  public String getUseRangeName() {
    return this.useRangeName;
  }
  
  public void setUseRangeName(String useRangeName) {
    this.useRangeName = useRangeName;
  }
}
