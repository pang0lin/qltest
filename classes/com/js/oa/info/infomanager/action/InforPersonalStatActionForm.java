package com.js.oa.info.infomanager.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class InforPersonalStatActionForm extends ActionForm {
  private String statYear;
  
  private String statMonth;
  
  private String empName;
  
  private String orgName;
  
  private String monthIssueNum;
  
  private String accumulateNum;
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public String getStatYear() {
    return this.statYear;
  }
  
  public void setStatYear(String statYear) {
    this.statYear = statYear;
  }
  
  public String getStatMonth() {
    return this.statMonth;
  }
  
  public void setStatMonth(String statMonth) {
    this.statMonth = statMonth;
  }
  
  public String getEmpName() {
    return this.empName;
  }
  
  public void setEmpName(String empName) {
    this.empName = empName;
  }
  
  public String getOrgName() {
    return this.orgName;
  }
  
  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }
  
  public String getMonthIssueNum() {
    return this.monthIssueNum;
  }
  
  public void setMonthIssueNum(String monthIssueNum) {
    this.monthIssueNum = monthIssueNum;
  }
  
  public String getAccumulateNum() {
    return this.accumulateNum;
  }
  
  public void setAccumulateNum(String accumulateNum) {
    this.accumulateNum = accumulateNum;
  }
}
