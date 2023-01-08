package com.js.system.vo.usermanager;

import java.io.Serializable;

public class ChildrenVO implements Serializable {
  private Long id;
  
  private String gx;
  
  private String xm;
  
  private String csny;
  
  private String sfzhm;
  
  private String zzmm;
  
  private String gzdwjbm;
  
  private String zw;
  
  private String bz;
  
  private EmployeeVO employeeVO;
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
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
  
  public EmployeeVO getEmployeeVO() {
    return this.employeeVO;
  }
  
  public void setEmployeeVO(EmployeeVO employeeVO) {
    this.employeeVO = employeeVO;
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
