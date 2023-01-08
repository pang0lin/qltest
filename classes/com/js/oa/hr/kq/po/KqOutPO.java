package com.js.oa.hr.kq.po;

import java.util.Date;

public class KqOutPO {
  private long id;
  
  private long userId;
  
  private String userName;
  
  private long orgId;
  
  private String orgName;
  
  private String place;
  
  private Date signTime = null;
  
  private Date startTime = null;
  
  private Date endTime = null;
  
  private String cause;
  
  private long projectId;
  
  private int status;
  
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
  
  public String getPlace() {
    return this.place;
  }
  
  public void setPlace(String place) {
    this.place = place;
  }
  
  public String getCause() {
    return this.cause;
  }
  
  public void setCause(String cause) {
    this.cause = cause;
  }
  
  public long getProjectId() {
    return this.projectId;
  }
  
  public void setProjectId(long projectId) {
    this.projectId = projectId;
  }
  
  public int getStatus() {
    return this.status;
  }
  
  public void setStatus(int status) {
    this.status = status;
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
  
  public Date getSignTime() {
    return this.signTime;
  }
  
  public void setSignTime(Date signTime) {
    this.signTime = signTime;
  }
}
