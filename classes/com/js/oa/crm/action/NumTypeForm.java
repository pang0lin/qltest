package com.js.oa.crm.action;

import org.apache.struts.action.ActionForm;

public class NumTypeForm extends ActionForm {
  private long id;
  
  private String name;
  
  private int initValue;
  
  private int initLong;
  
  private String type;
  
  private String createUserId;
  
  private String createUserName;
  
  private String createTime;
  
  private String numDesc;
  
  public int getInitLong() {
    return this.initLong;
  }
  
  public void setInitLong(int initLong) {
    this.initLong = initLong;
  }
  
  public int getInitValue() {
    return this.initValue;
  }
  
  public void setInitValue(int initValue) {
    this.initValue = initValue;
  }
  
  public String getNumDesc() {
    return this.numDesc;
  }
  
  public void setNumDesc(String numDesc) {
    this.numDesc = numDesc;
  }
  
  public String getType() {
    return this.type;
  }
  
  public void setType(String type) {
    this.type = type;
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
