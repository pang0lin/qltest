package com.js.oa.hr.kq.po;

import java.util.Date;

public class KqLeavePO {
  private long id;
  
  private long userId;
  
  private String userName;
  
  private long orgId;
  
  private String orgName;
  
  private Date startTime = null;
  
  private Date endTime = null;
  
  private String cause;
  
  private int status;
  
  private int leaveType;
  
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
  
  public String getOrgName() {
    return this.orgName;
  }
  
  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }
  
  public Date getStartTime() {
    return this.startTime;
  }
  
  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }
  
  public Date getEndTime() {
    return this.endTime;
  }
  
  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }
  
  public String getCause() {
    return this.cause;
  }
  
  public void setCause(String cause) {
    this.cause = cause;
  }
  
  public int getStatus() {
    return this.status;
  }
  
  public void setStatus(int status) {
    this.status = status;
  }
  
  public int getLeaveType() {
    return this.leaveType;
  }
  
  public void setLeaveType(int leaveType) {
    this.leaveType = leaveType;
  }
}
