package com.js.system.vo.messages;

public class Remind {
  private long id;
  
  private long data_id;
  
  private long emp_id;
  
  private String remind_type;
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public long getData_id() {
    return this.data_id;
  }
  
  public void setData_id(long data_id) {
    this.data_id = data_id;
  }
  
  public long getEmp_id() {
    return this.emp_id;
  }
  
  public void setEmp_id(long emp_id) {
    this.emp_id = emp_id;
  }
  
  public String getRemind_type() {
    return this.remind_type;
  }
  
  public void setRemind_type(String remind_type) {
    this.remind_type = remind_type;
  }
}
