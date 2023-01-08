package com.js.system.vo.messages;

public class MessagesStatusSet {
  private long id;
  
  private long emp_id;
  
  private int status;
  
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
  
  public int getStatus() {
    return this.status;
  }
  
  public void setStatus(int status) {
    this.status = status;
  }
}
