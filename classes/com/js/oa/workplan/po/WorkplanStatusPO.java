package com.js.oa.workplan.po;

import java.io.Serializable;

public class WorkplanStatusPO implements Serializable {
  private long id;
  
  private String statusName;
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public String getStatusName() {
    return this.statusName;
  }
  
  public void setStatusName(String statusName) {
    this.statusName = statusName;
  }
}
