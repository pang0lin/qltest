package com.js.oa.hr.kq.action;

import java.util.Date;
import org.apache.struts.action.ActionForm;

public class KqWeiXinBMDActionForm extends ActionForm {
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
