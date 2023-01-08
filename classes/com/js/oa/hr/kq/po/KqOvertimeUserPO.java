package com.js.oa.hr.kq.po;

public class KqOvertimeUserPO {
  private long id;
  
  private long overtimeId;
  
  private long userId;
  
  private String userName;
  
  private long orgId;
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public long getUserId() {
    return this.userId;
  }
  
  public void setUserId(long userId) {
    this.userId = userId;
  }
  
  public String getUserName() {
    return this.userName;
  }
  
  public void setUserName(String userName) {
    this.userName = userName;
  }
  
  public long getOrgId() {
    return this.orgId;
  }
  
  public void setOrgId(long orgId) {
    this.orgId = orgId;
  }
  
  public long getOvertimeId() {
    return this.overtimeId;
  }
  
  public void setOvertimeId(long overtimeId) {
    this.overtimeId = overtimeId;
  }
}
