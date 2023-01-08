package com.js.oa.weixin.workflow;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class WeiXinFileDealWithActionForm extends ActionForm {
  private String workTitle;
  
  private String fileType;
  
  private String workStatus;
  
  private String pressDeal;
  
  private String workStepName;
  
  private String workSubmitPerson;
  
  private String submitOrg;
  
  public String getWorkSubmitPerson() {
    return this.workSubmitPerson;
  }
  
  public void setWorkSubmitPerson(String workSubmitPerson) {
    this.workSubmitPerson = workSubmitPerson;
  }
  
  public String getSubmitOrg() {
    return this.submitOrg;
  }
  
  public void setSubmitOrg(String submitOrg) {
    this.submitOrg = submitOrg;
  }
  
  public String getWorkStepName() {
    return this.workStepName;
  }
  
  public void setWorkStepName(String workStepName) {
    this.workStepName = workStepName;
  }
  
  public String getWorkTitle() {
    return this.workTitle;
  }
  
  public void setWorkTitle(String workTitle) {
    this.workTitle = workTitle;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public String getFileType() {
    return this.fileType;
  }
  
  public void setFileType(String fileType) {
    this.fileType = fileType;
  }
  
  public String getWorkStatus() {
    return this.workStatus;
  }
  
  public void setWorkStatus(String workStatus) {
    this.workStatus = workStatus;
  }
  
  public String getPressDeal() {
    return this.pressDeal;
  }
  
  public void setPressDeal(String pressDeal) {
    this.pressDeal = pressDeal;
  }
}
