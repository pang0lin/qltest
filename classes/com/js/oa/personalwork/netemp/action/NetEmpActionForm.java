package com.js.oa.personalwork.netemp.action;

import org.apache.struts.action.ActionForm;

public class NetEmpActionForm extends ActionForm {
  private String superiorsId;
  
  private String superiorsName;
  
  private String underlingId;
  
  private String underlingName;
  
  private String netEmpId;
  
  private String netEmpName;
  
  public String getSuperiorsId() {
    return this.superiorsId;
  }
  
  public void setSuperiorsId(String superiorsId) {
    this.superiorsId = superiorsId;
  }
  
  public String getSuperiorsName() {
    return this.superiorsName;
  }
  
  public void setSuperiorsName(String superiorsName) {
    this.superiorsName = superiorsName;
  }
  
  public String getUnderlingId() {
    return this.underlingId;
  }
  
  public void setUnderlingId(String underlingId) {
    this.underlingId = underlingId;
  }
  
  public String getUnderlingName() {
    return this.underlingName;
  }
  
  public void setUnderlingName(String underlingName) {
    this.underlingName = underlingName;
  }
  
  public String getNetEmpId() {
    return this.netEmpId;
  }
  
  public void setNetEmpId(String netEmpId) {
    this.netEmpId = netEmpId;
  }
  
  public String getNetEmpName() {
    return this.netEmpName;
  }
  
  public void setNetEmpName(String netEmpName) {
    this.netEmpName = netEmpName;
  }
}
