package com.js.oa.hr.kq.po;

import java.util.Date;

public class KqOvertimePO {
  private long id;
  
  private long userId;
  
  private long orgId;
  
  private String orgName;
  
  private String userName;
  
  private String cause;
  
  private long projectId;
  
  private Date startTime = null;
  
  private Date endTime = null;
  
  private String attendUserId;
  
  private String attendUserName;
  
  private String attendOrgId;
  
  private String attendGroupId;
  
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
  
  public String getAttendUserId() {
    return this.attendUserId;
  }
  
  public void setAttendUserId(String attendUserId) {
    this.attendUserId = attendUserId;
  }
  
  public String getAttendUserName() {
    return this.attendUserName;
  }
  
  public void setAttendUserName(String attendUserName) {
    this.attendUserName = attendUserName;
  }
  
  public String getAttendOrgId() {
    return this.attendOrgId;
  }
  
  public void setAttendOrgId(String attendOrgId) {
    this.attendOrgId = attendOrgId;
  }
  
  public String getAttendGroupId() {
    return this.attendGroupId;
  }
  
  public void setAttendGroupId(String attendGroupId) {
    this.attendGroupId = attendGroupId;
  }
  
  public int getStatus() {
    return this.status;
  }
  
  public void setStatus(int status) {
    this.status = status;
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
}
