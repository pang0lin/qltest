package com.js.oa.jsflow.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class FlowListActionForm extends ActionForm {
  private String packageId;
  
  private String packageName;
  
  private String processName;
  
  private String submitPerson;
  
  private String processId;
  
  public String getPackageId() {
    return this.packageId;
  }
  
  public void setPackageId(String packageId) {
    this.packageId = packageId;
  }
  
  public String getPackageName() {
    return this.packageName;
  }
  
  public void setPackageName(String packageName) {
    this.packageName = packageName;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public String getProcessName() {
    return this.processName;
  }
  
  public void setProcessName(String processName) {
    this.processName = processName;
  }
  
  public String getSubmitPerson() {
    return this.submitPerson;
  }
  
  public void setSubmitPerson(String submitPerson) {
    this.submitPerson = submitPerson;
  }
  
  public String getProcessId() {
    return this.processId;
  }
  
  public void setProcessId(String processId) {
    this.processId = processId;
  }
}
