package com.js.oa.hr.personnelmanager.action;

import org.apache.struts.action.ActionForm;

public class EmployeeChildrenActionForm extends ActionForm {
  private Long id;
  
  private String gx;
  
  private String xm;
  
  private String csny;
  
  private String zzmm;
  
  private String gzdwjbm;
  
  private String zw;
  
  private String bz;
  
  private Long empID;
  
  private String sfzhm;
  
  public String getGx() {
    return this.gx;
  }
  
  public void setGx(String gx) {
    this.gx = gx;
  }
  
  public String getXm() {
    return this.xm;
  }
  
  public void setXm(String xm) {
    this.xm = xm;
  }
  
  public String getCsny() {
    return this.csny;
  }
  
  public void setCsny(String csny) {
    this.csny = csny;
  }
  
  public String getZzmm() {
    return this.zzmm;
  }
  
  public void setZzmm(String zzmm) {
    this.zzmm = zzmm;
  }
  
  public String getGzdwjbm() {
    return this.gzdwjbm;
  }
  
  public void setGzdwjbm(String gzdwjbm) {
    this.gzdwjbm = gzdwjbm;
  }
  
  public String getZw() {
    return this.zw;
  }
  
  public void setZw(String zw) {
    this.zw = zw;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public Long getEmpID() {
    return this.empID;
  }
  
  public void setEmpID(Long empID) {
    this.empID = empID;
  }
  
  public String getBz() {
    return this.bz;
  }
  
  public void setBz(String bz) {
    this.bz = bz;
  }
  
  public String getSfzhm() {
    return this.sfzhm;
  }
  
  public void setSfzhm(String sfzhm) {
    this.sfzhm = sfzhm;
  }
}
