package com.js.oa.userdb.action;

import org.apache.struts.action.ActionForm;

public class ModelAddActionForm extends ActionForm {
  private String code;
  
  private String name;
  
  private String remark;
  
  private String user;
  
  private String operate;
  
  private String domainid;
  
  public String getCode() {
    return this.code;
  }
  
  public void setCode(String code) {
    this.code = code;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getRemark() {
    return this.remark;
  }
  
  public void setRemark(String remark) {
    this.remark = remark;
  }
  
  public String getUser() {
    return this.user;
  }
  
  public void setUser(String user) {
    this.user = user;
  }
  
  public String getOperate() {
    return this.operate;
  }
  
  public void setOperate(String operate) {
    this.operate = operate;
  }
  
  public String getDomainid() {
    return this.domainid;
  }
  
  public void setDomainid(String domainid) {
    this.domainid = domainid;
  }
}
