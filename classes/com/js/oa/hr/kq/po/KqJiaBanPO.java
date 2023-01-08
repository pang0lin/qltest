package com.js.oa.hr.kq.po;

import java.util.Date;

public class KqJiaBanPO {
  private Long jiaBanId;
  
  private String jiaBanEmp;
  
  private String jiaBanDate;
  
  private Date jiaBanStart = null;
  
  private Date jiaBanEnd = null;
  
  private Float jiaBanHour;
  
  private String jiaBanType;
  
  public Long getJiaBanId() {
    return this.jiaBanId;
  }
  
  public void setJiaBanId(Long jiaBanId) {
    this.jiaBanId = jiaBanId;
  }
  
  public String getJiaBanEmp() {
    return this.jiaBanEmp;
  }
  
  public void setJiaBanEmp(String jiaBanEmp) {
    this.jiaBanEmp = jiaBanEmp;
  }
  
  public String getJiaBanDate() {
    return this.jiaBanDate;
  }
  
  public void setJiaBanDate(String jiaBanDate) {
    this.jiaBanDate = jiaBanDate;
  }
  
  public Date getJiaBanStart() {
    return this.jiaBanStart;
  }
  
  public void setJiaBanStart(Date jiaBanStart) {
    this.jiaBanStart = jiaBanStart;
  }
  
  public Date getJiaBanEnd() {
    return this.jiaBanEnd;
  }
  
  public void setJiaBanEnd(Date jiaBanEnd) {
    this.jiaBanEnd = jiaBanEnd;
  }
  
  public Float getJiaBanHour() {
    return this.jiaBanHour;
  }
  
  public void setJiaBanHour(Float jiaBanHour) {
    this.jiaBanHour = jiaBanHour;
  }
  
  public String getJiaBanType() {
    return this.jiaBanType;
  }
  
  public void setJiaBanType(String jiaBanType) {
    this.jiaBanType = jiaBanType;
  }
}
