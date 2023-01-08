package com.js.oa.hr.examination.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ExaminationSelfTestActionForm extends ActionForm {
  private Long testEmpID;
  
  private String testEmpName;
  
  private Long testOrgID;
  
  private String testOrgName;
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.testEmpID = null;
    this.testEmpName = null;
    this.testOrgID = null;
    this.testOrgName = null;
  }
  
  public Long getTestEmpID() {
    return this.testEmpID;
  }
  
  public void setTestEmpID(Long testEmpID) {
    this.testEmpID = testEmpID;
  }
  
  public String getTestEmpName() {
    return this.testEmpName;
  }
  
  public void setTestEmpName(String testEmpName) {
    this.testEmpName = testEmpName;
  }
  
  public Long getTestOrgID() {
    return this.testOrgID;
  }
  
  public void setTestOrgID(Long testOrgID) {
    this.testOrgID = testOrgID;
  }
  
  public String getTestOrgName() {
    return this.testOrgName;
  }
  
  public void setTestOrgName(String testOrgName) {
    this.testOrgName = testOrgName;
  }
}
