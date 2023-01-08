package com.js.oa.hr.kq.po;

import java.util.Date;

public class KqWaiChuPO {
  private Long waiChuId;
  
  private String waiChuEmp;
  
  private String waiChuDate;
  
  private Date waiChuStart = null;
  
  private Date waiChuEnd = null;
  
  private Float waiChuHour;
  
  private String waiChuType;
  
  public Long getWaiChuId() {
    return this.waiChuId;
  }
  
  public void setWaiChuId(Long waiChuId) {
    this.waiChuId = waiChuId;
  }
  
  public String getWaiChuEmp() {
    return this.waiChuEmp;
  }
  
  public void setWaiChuEmp(String waiChuEmp) {
    this.waiChuEmp = waiChuEmp;
  }
  
  public String getWaiChuDate() {
    return this.waiChuDate;
  }
  
  public void setWaiChuDate(String waiChuDate) {
    this.waiChuDate = waiChuDate;
  }
  
  public Date getWaiChuStart() {
    return this.waiChuStart;
  }
  
  public void setWaiChuStart(Date waiChuStart) {
    this.waiChuStart = waiChuStart;
  }
  
  public Date getWaiChuEnd() {
    return this.waiChuEnd;
  }
  
  public void setWaiChuEnd(Date waiChuEnd) {
    this.waiChuEnd = waiChuEnd;
  }
  
  public Float getWaiChuHour() {
    return this.waiChuHour;
  }
  
  public void setWaiChuHour(Float waiChuHour) {
    this.waiChuHour = waiChuHour;
  }
  
  public String getWaiChuType() {
    return this.waiChuType;
  }
  
  public void setWaiChuType(String waiChuType) {
    this.waiChuType = waiChuType;
  }
}
