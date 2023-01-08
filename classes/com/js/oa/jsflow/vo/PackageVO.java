package com.js.oa.jsflow.vo;

import java.io.Serializable;
import java.util.Date;

public class PackageVO implements Serializable {
  private long id;
  
  private String name;
  
  private Date createdDate = null;
  
  private long createdEmp;
  
  private long createdOrg;
  
  private int moduleId;
  
  private String description;
  
  private String domainId;
  
  private int orderCode;
  
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
  
  public Date getCreatedDate() {
    return this.createdDate;
  }
  
  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }
  
  public long getCreatedEmp() {
    return this.createdEmp;
  }
  
  public void setCreatedEmp(long createdEmp) {
    this.createdEmp = createdEmp;
  }
  
  public long getCreatedOrg() {
    return this.createdOrg;
  }
  
  public void setCreatedOrg(long createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public int getModuleId() {
    return this.moduleId;
  }
  
  public void setModuleId(int moduleId) {
    this.moduleId = moduleId;
  }
  
  public String getDescription() {
    return this.description;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
  
  public int getOrderCode() {
    return this.orderCode;
  }
  
  public void setOrderCode(int orderCode) {
    this.orderCode = orderCode;
  }
}
