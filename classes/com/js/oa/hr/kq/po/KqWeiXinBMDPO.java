package com.js.oa.hr.kq.po;

import java.io.Serializable;
import java.util.Date;

public class KqWeiXinBMDPO implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private long id;
  
  private long emp_id;
  
  private Date beginTime = null;
  
  private Date endTime = null;
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public long getEmp_id() {
    return this.emp_id;
  }
  
  public void setEmp_id(long emp_id) {
    this.emp_id = emp_id;
  }
  
  public Date getBeginTime() {
    return this.beginTime;
  }
  
  public void setBeginTime(Date beginTime) {
    this.beginTime = beginTime;
  }
  
  public Date getEndTime() {
    return this.endTime;
  }
  
  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }
}
