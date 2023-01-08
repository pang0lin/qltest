package com.js.oa.hr.personnelmanager.action;

import org.apache.struts.action.ActionForm;

public class EmployeePxjlActionForm extends ActionForm {
  private Long id;
  
  private String kssj;
  
  private String jssj;
  
  private String zxs;
  
  private String cjpxxm;
  
  private String pxxz;
  
  private String hdzs;
  
  private String pxdd;
  
  private Long empID;
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getKssj() {
    return this.kssj;
  }
  
  public void setKssj(String kssj) {
    this.kssj = kssj;
  }
  
  public String getJssj() {
    return this.jssj;
  }
  
  public void setJssj(String jssj) {
    this.jssj = jssj;
  }
  
  public String getZxs() {
    return this.zxs;
  }
  
  public void setZxs(String zxs) {
    this.zxs = zxs;
  }
  
  public String getCjpxxm() {
    return this.cjpxxm;
  }
  
  public void setCjpxxm(String cjpxxm) {
    this.cjpxxm = cjpxxm;
  }
  
  public String getPxxz() {
    return this.pxxz;
  }
  
  public void setPxxz(String pxxz) {
    this.pxxz = pxxz;
  }
  
  public String getHdzs() {
    return this.hdzs;
  }
  
  public void setHdzs(String hdzs) {
    this.hdzs = hdzs;
  }
  
  public String getPxdd() {
    return this.pxdd;
  }
  
  public void setPxdd(String pxdd) {
    this.pxdd = pxdd;
  }
  
  public Long getEmpID() {
    return this.empID;
  }
  
  public void setEmpID(Long empID) {
    this.empID = empID;
  }
}
