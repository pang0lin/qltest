package com.js.oa.rws.pojo;

public class RwsOrganization extends RwsObject {
  private String ou;
  
  private String deptCode;
  
  private String deptType;
  
  private String guid;
  
  private String orderCode;
  
  public String getOu() {
    return this.ou;
  }
  
  public void setOu(String ou) {
    this.ou = ou;
  }
  
  public String getGuid() {
    return this.guid;
  }
  
  public void setGuid(String guid) {
    this.guid = guid;
  }
  
  public String getDeptCode() {
    return this.deptCode;
  }
  
  public void setDeptCode(String deptCode) {
    this.deptCode = deptCode;
  }
  
  public String getDeptType() {
    return this.deptType;
  }
  
  public void setDeptType(String deptType) {
    this.deptType = deptType;
  }
  
  public String getOrderCode() {
    return this.orderCode;
  }
  
  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }
}
