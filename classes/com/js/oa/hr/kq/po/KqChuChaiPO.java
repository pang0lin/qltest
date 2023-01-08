package com.js.oa.hr.kq.po;

import java.util.Date;

public class KqChuChaiPO {
  private Long chuChaiId;
  
  private String chuChaiEmp;
  
  private String chuChaiDate;
  
  private Date chuChaiStart = null;
  
  private Date chuChaiEnd = null;
  
  private Float chuChaiHour;
  
  private String chuChaiType;
  
  public Long getChuChaiId() {
    return this.chuChaiId;
  }
  
  public void setChuChaiId(Long chuChaiId) {
    this.chuChaiId = chuChaiId;
  }
  
  public String getChuChaiEmp() {
    return this.chuChaiEmp;
  }
  
  public void setChuChaiEmp(String chuChaiEmp) {
    this.chuChaiEmp = chuChaiEmp;
  }
  
  public String getChuChaiDate() {
    return this.chuChaiDate;
  }
  
  public void setChuChaiDate(String chuChaiDate) {
    this.chuChaiDate = chuChaiDate;
  }
  
  public Date getChuChaiStart() {
    return this.chuChaiStart;
  }
  
  public void setChuChaiStart(Date chuChaiStart) {
    this.chuChaiStart = chuChaiStart;
  }
  
  public Date getChuChaiEnd() {
    return this.chuChaiEnd;
  }
  
  public void setChuChaiEnd(Date chuChaiEnd) {
    this.chuChaiEnd = chuChaiEnd;
  }
  
  public Float getChuChaiHour() {
    return this.chuChaiHour;
  }
  
  public void setChuChaiHour(Float chuChaiHour) {
    this.chuChaiHour = chuChaiHour;
  }
  
  public String getChuChaiType() {
    return this.chuChaiType;
  }
  
  public void setChuChaiType(String chuChaiType) {
    this.chuChaiType = chuChaiType;
  }
}
