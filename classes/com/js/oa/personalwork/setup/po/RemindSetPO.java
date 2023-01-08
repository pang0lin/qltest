package com.js.oa.personalwork.setup.po;

public class RemindSetPO {
  private long id;
  
  private long emp_id;
  
  private String status;
  
  private String type;
  
  public String getType() {
    return this.type;
  }
  
  public void setType(String type) {
    this.type = type;
  }
  
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
  
  public String getStatus() {
    return this.status;
  }
  
  public void setStatus(String status) {
    this.status = status;
  }
}
