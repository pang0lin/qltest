package com.js.message.lava;

public class Organization {
  private String code;
  
  private String name;
  
  private String parentcode;
  
  private String sign;
  
  private String location;
  
  private String email;
  
  private String remark;
  
  private String dataopr;
  
  public String getDataopr() {
    return this.dataopr;
  }
  
  public void setDataopr(String dataopr) {
    this.dataopr = dataopr;
  }
  
  public String getCode() {
    return this.code;
  }
  
  public void setCode(String code) {
    this.code = code;
  }
  
  public String getEmail() {
    return this.email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public String getLocation() {
    return this.location;
  }
  
  public void setLocation(String location) {
    this.location = location;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getParentcode() {
    return this.parentcode;
  }
  
  public void setParentcode(String parentcode) {
    this.parentcode = parentcode;
  }
  
  public String getRemark() {
    return this.remark;
  }
  
  public void setRemark(String remark) {
    this.remark = remark;
  }
  
  public String getSign() {
    return this.sign;
  }
  
  public void setSign(String sign) {
    this.sign = sign;
  }
}
