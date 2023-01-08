package com.js.oa.info.infomanager.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class InforOrgStatActionForm extends ActionForm {
  private String accumulateNum;
  
  private String monthIssueNum;
  
  private String orgId;
  
  private String orgIdString;
  
  private String orgName;
  
  private String statMonth;
  
  private String statYear;
  
  public String getAccumulateNum() {
    return this.accumulateNum;
  }
  
  public void setAccumulateNum(String accumulateNum) {
    this.accumulateNum = accumulateNum;
  }
  
  public String getMonthIssueNum() {
    return this.monthIssueNum;
  }
  
  public void setMonthIssueNum(String monthIssueNum) {
    this.monthIssueNum = monthIssueNum;
  }
  
  public String getOrgId() {
    return this.orgId;
  }
  
  public void setOrgId(String orgId) {
    this.orgId = orgId;
  }
  
  public String getOrgIdString() {
    return this.orgIdString;
  }
  
  public void setOrgIdString(String orgIdString) {
    this.orgIdString = orgIdString;
  }
  
  public String getOrgName() {
    return this.orgName;
  }
  
  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }
  
  public String getStatMonth() {
    return this.statMonth;
  }
  
  public void setStatMonth(String statMonth) {
    this.statMonth = statMonth;
  }
  
  public String getStatYear() {
    return this.statYear;
  }
  
  public void setStatYear(String statYear) {
    this.statYear = statYear;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
}
