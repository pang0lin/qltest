package com.js.oa.security.seamoon.po;

import java.io.Serializable;
import java.sql.Timestamp;

public class SecSeaMoon implements Serializable {
  private String sn;
  
  private String snKey;
  
  private String userId;
  
  private String userName;
  
  private String secStatus;
  
  private Timestamp beginTime = null;
  
  private Timestamp endTime = null;
  
  public SecSeaMoon() {}
  
  public SecSeaMoon(String snKey, String userId, String userName, String secStatus, Timestamp beginTime, Timestamp endTime) {
    this.snKey = snKey;
    this.userId = userId;
    this.userName = userName;
    this.secStatus = secStatus;
    this.beginTime = beginTime;
    this.endTime = endTime;
  }
  
  public String getSn() {
    return this.sn;
  }
  
  public void setSn(String sn) {
    this.sn = sn;
  }
  
  public String getSnKey() {
    return this.snKey;
  }
  
  public void setSnKey(String snKey) {
    this.snKey = snKey;
  }
  
  public String getUserId() {
    return this.userId;
  }
  
  public void setUserId(String userId) {
    this.userId = userId;
  }
  
  public String getUserName() {
    return this.userName;
  }
  
  public void setUserName(String userName) {
    this.userName = userName;
  }
  
  public String getSecStatus() {
    return this.secStatus;
  }
  
  public void setSecStatus(String secStatus) {
    this.secStatus = secStatus;
  }
  
  public Timestamp getBeginTime() {
    return this.beginTime;
  }
  
  public void setBeginTime(Timestamp beginTime) {
    this.beginTime = beginTime;
  }
  
  public Timestamp getEndTime() {
    return this.endTime;
  }
  
  public void setEndTime(Timestamp endTime) {
    this.endTime = endTime;
  }
}
