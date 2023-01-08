package com.js.oa.crm.action;

import org.apache.struts.action.ActionForm;

public class VisitTypeForm extends ActionForm {
  private long id;
  
  private String name;
  
  private String createUserId;
  
  private String createUserName;
  
  private String createTime;
  
  private String userRange;
  
  private String userRangeName;
  
  private String orgRange;
  
  private String orgRangeName;
  
  public String getOrgRange() {
    return this.orgRange;
  }
  
  public void setOrgRange(String orgRange) {
    this.orgRange = orgRange;
  }
  
  public String getOrgRangeName() {
    return this.orgRangeName;
  }
  
  public void setOrgRangeName(String orgRangeName) {
    this.orgRangeName = orgRangeName;
  }
  
  public String getUserRange() {
    return this.userRange;
  }
  
  public void setUserRange(String userRange) {
    this.userRange = userRange;
  }
  
  public String getUserRangeName() {
    return this.userRangeName;
  }
  
  public void setUserRangeName(String userRangeName) {
    this.userRangeName = userRangeName;
  }
  
  public String getCreateTime() {
    return this.createTime;
  }
  
  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }
  
  public String getCreateUserId() {
    return this.createUserId;
  }
  
  public void setCreateUserId(String createUserId) {
    this.createUserId = createUserId;
  }
  
  public String getCreateUserName() {
    return this.createUserName;
  }
  
  public void setCreateUserName(String createUserName) {
    this.createUserName = createUserName;
  }
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
}
