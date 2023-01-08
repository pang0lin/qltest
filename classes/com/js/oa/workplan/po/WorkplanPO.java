package com.js.oa.workplan.po;

import java.io.Serializable;
import java.util.Date;

public class WorkplanPO implements Serializable {
  private long id;
  
  private String leaderId;
  
  private String leaderName;
  
  private String recordId;
  
  private String recordName;
  
  private Date workDate = null;
  
  private String workYearMonth;
  
  private String workStatus;
  
  private String workType;
  
  private String workPlace;
  
  private String description;
  
  private Date recordTime = null;
  
  private String dutyName;
  
  private String orgName;
  
  public String getDutyName() {
    return this.dutyName;
  }
  
  public void setDutyName(String dutyName) {
    this.dutyName = dutyName;
  }
  
  public String getOrgName() {
    return this.orgName;
  }
  
  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }
  
  public Date getRecordTime() {
    return this.recordTime;
  }
  
  public void setRecordTime(Date recordTime) {
    this.recordTime = recordTime;
  }
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public String getLeaderId() {
    return this.leaderId;
  }
  
  public void setLeaderId(String leaderId) {
    this.leaderId = leaderId;
  }
  
  public String getLeaderName() {
    return this.leaderName;
  }
  
  public void setLeaderName(String leaderName) {
    this.leaderName = leaderName;
  }
  
  public String getRecordId() {
    return this.recordId;
  }
  
  public void setRecordId(String recordId) {
    this.recordId = recordId;
  }
  
  public String getRecordName() {
    return this.recordName;
  }
  
  public void setRecordName(String recordName) {
    this.recordName = recordName;
  }
  
  public Date getWorkDate() {
    return this.workDate;
  }
  
  public void setWorkDate(Date workDate) {
    this.workDate = workDate;
  }
  
  public String getWorkYearMonth() {
    return this.workYearMonth;
  }
  
  public void setWorkYearMonth(String workYearMonth) {
    this.workYearMonth = workYearMonth;
  }
  
  public String getWorkStatus() {
    return this.workStatus;
  }
  
  public void setWorkStatus(String workStatus) {
    this.workStatus = workStatus;
  }
  
  public String getWorkType() {
    return this.workType;
  }
  
  public void setWorkType(String workType) {
    this.workType = workType;
  }
  
  public String getWorkPlace() {
    return this.workPlace;
  }
  
  public void setWorkPlace(String workPlace) {
    this.workPlace = workPlace;
  }
  
  public String getDescription() {
    return this.description;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
}
