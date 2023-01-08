package com.js.oa.hr.kq.po;

import java.util.Date;

public class KqDutyOutPO {
  private Long outId;
  
  private String outEmp;
  
  private Date outDate = null;
  
  private float outHour;
  
  private String outType;
  
  private Date outInputDate = null;
  
  public Long getOutId() {
    return this.outId;
  }
  
  public void setOutId(Long outId) {
    this.outId = outId;
  }
  
  public String getOutEmp() {
    return this.outEmp;
  }
  
  public void setOutEmp(String outEmp) {
    this.outEmp = outEmp;
  }
  
  public Date getOutDate() {
    return this.outDate;
  }
  
  public void setOutDate(Date outDate) {
    this.outDate = outDate;
  }
  
  public float getOutHour() {
    return this.outHour;
  }
  
  public void setOutHour(float outHour) {
    this.outHour = outHour;
  }
  
  public String getOutType() {
    return this.outType;
  }
  
  public void setOutType(String outType) {
    this.outType = outType;
  }
  
  public Date getOutInputDate() {
    return this.outInputDate;
  }
  
  public void setOutInputDate(Date outInputDate) {
    this.outInputDate = outInputDate;
  }
}
