package com.js.system.action.usermanager;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class UserSearchForm extends ActionForm {
  private String cnName;
  
  private String enName;
  
  private String isSuper;
  
  private String orgId;
  
  private String orgName;
  
  private String userAccount;
  
  private String empLeaderId;
  
  private String empLeaderName;
  
  public String getCnName() {
    return this.cnName;
  }
  
  public void setCnName(String cnName) {
    this.cnName = cnName;
  }
  
  public String getEnName() {
    return this.enName;
  }
  
  public void setEnName(String enName) {
    this.enName = enName;
  }
  
  public String getIsSuper() {
    return this.isSuper;
  }
  
  public void setIsSuper(String isSuper) {
    this.isSuper = isSuper;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.cnName = null;
    this.enName = null;
    this.orgName = null;
    this.isSuper = null;
    this.userAccount = null;
    this.empLeaderId = null;
    this.empLeaderName = null;
  }
  
  public String getOrgId() {
    return this.orgId;
  }
  
  public String getOrgName() {
    return this.orgName;
  }
  
  public void setOrgId(String orgId) {
    this.orgId = orgId;
  }
  
  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }
  
  public String getUserAccount() {
    return this.userAccount;
  }
  
  public void setUserAccount(String userAccount) {
    this.userAccount = userAccount;
  }
  
  public String getEmpLeaderId() {
    return this.empLeaderId;
  }
  
  public void setEmpLeaderId(String empLeaderId) {
    this.empLeaderId = empLeaderId;
  }
  
  public String getEmpLeaderName() {
    return this.empLeaderName;
  }
  
  public void setEmpLeaderName(String empLeaderName) {
    this.empLeaderName = empLeaderName;
  }
}
