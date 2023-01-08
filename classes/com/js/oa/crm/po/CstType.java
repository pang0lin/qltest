package com.js.oa.crm.po;

import java.io.Serializable;

public class CstType implements Serializable {
  private long id;
  
  private String name;
  
  private String createUserId;
  
  private String createUserName;
  
  private String createTime;
  
  private String type;
  
  private String customerDesc;
  
  public String getCustomerDesc() {
    return this.customerDesc;
  }
  
  public void setCustomerDesc(String customerDesc) {
    this.customerDesc = customerDesc;
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
  
  public String getType() {
    return this.type;
  }
  
  public void setType(String type) {
    this.type = type;
  }
}
