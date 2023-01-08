package com.js.system.vo.usermanager;

import java.io.Serializable;

public class JcxxVO implements Serializable {
  private Long id;
  
  private EmployeeVO employeeVO;
  
  private String hjsj;
  
  private String hjmc;
  
  private String hjsx;
  
  private String hjjb;
  
  private String cfsj;
  
  private String cfmc;
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public EmployeeVO getEmployeeVO() {
    return this.employeeVO;
  }
  
  public void setEmployeeVO(EmployeeVO employeeVO) {
    this.employeeVO = employeeVO;
  }
  
  public String getHjsj() {
    return this.hjsj;
  }
  
  public void setHjsj(String hjsj) {
    this.hjsj = hjsj;
  }
  
  public String getHjmc() {
    return this.hjmc;
  }
  
  public void setHjmc(String hjmc) {
    this.hjmc = hjmc;
  }
  
  public String getHjsx() {
    return this.hjsx;
  }
  
  public void setHjsx(String hjsx) {
    this.hjsx = hjsx;
  }
  
  public String getHjjb() {
    return this.hjjb;
  }
  
  public void setHjjb(String hjjb) {
    this.hjjb = hjjb;
  }
  
  public String getCfsj() {
    return this.cfsj;
  }
  
  public void setCfsj(String cfsj) {
    this.cfsj = cfsj;
  }
  
  public String getCfmc() {
    return this.cfmc;
  }
  
  public void setCfmc(String cfmc) {
    this.cfmc = cfmc;
  }
}
