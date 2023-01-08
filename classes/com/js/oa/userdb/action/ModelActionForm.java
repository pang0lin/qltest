package com.js.oa.userdb.action;

import org.apache.struts.action.ActionForm;

public class ModelActionForm extends ActionForm {
  private String operate;
  
  private String[] id;
  
  private String modelid;
  
  private String domainid;
  
  public String getOperate() {
    return this.operate;
  }
  
  public void setOperate(String operate) {
    this.operate = operate;
  }
  
  public String[] getId() {
    return this.id;
  }
  
  public void setId(String[] id) {
    this.id = id;
  }
  
  public String getModelid() {
    return this.modelid;
  }
  
  public void setModelid(String modelid) {
    this.modelid = modelid;
  }
  
  public String getDomainid() {
    return this.domainid;
  }
  
  public void setDomainid(String domainid) {
    this.domainid = domainid;
  }
}
