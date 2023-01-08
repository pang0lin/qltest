package com.js.doc.doc.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class GovCustomActionForm extends ActionForm {
  private String govFormName;
  
  private String govFormContent;
  
  private String govPrintFormContent;
  
  private String govFormId;
  
  private String govFormType;
  
  private String govCheckField;
  
  public String getGovFormType() {
    return this.govFormType;
  }
  
  public String getGovFormName() {
    return this.govFormName;
  }
  
  public String getGovFormContent() {
    return this.govFormContent;
  }
  
  public String getGovPrintFormContent() {
    return this.govPrintFormContent;
  }
  
  public String getGovCheckField() {
    return this.govCheckField;
  }
  
  public void setGovFormId(String govFormId) {
    this.govFormId = govFormId;
  }
  
  public void setGovFormType(String govFormType) {
    this.govFormType = govFormType;
  }
  
  public void setGovFormName(String govFormName) {
    this.govFormName = govFormName;
  }
  
  public void setGovFormContent(String govFormContent) {
    this.govFormContent = govFormContent;
  }
  
  public void setGovPrintFormContent(String govPrintFormContent) {
    this.govPrintFormContent = govPrintFormContent;
  }
  
  public void setGovCheckField(String govCheckField) {
    this.govCheckField = govCheckField;
  }
  
  public String getGovFormId() {
    return this.govFormId;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionmapping, HttpServletRequest httpservletrequest) {}
}
