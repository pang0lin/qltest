package com.js.oa.hr.kq.po;

import java.util.Date;

public class KqRecordPO {
  private long id;
  
  private long userId;
  
  private long orgId;
  
  private Date recordTime = null;
  
  private int recordStatus;
  
  private int recordSeq;
  
  private int dutyType;
  
  private long timeDiff;
  
  private Date dutyTime = null;
  
  private String dutyName;
  
  private int nosign;
  
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
  
  public long getOrgId() {
    return this.orgId;
  }
  
  public void setOrgId(long orgId) {
    this.orgId = orgId;
  }
  
  public Date getRecordTime() {
    return this.recordTime;
  }
  
  public void setRecordTime(Date recordTime) {
    this.recordTime = recordTime;
  }
  
  public int getRecordStatus() {
    return this.recordStatus;
  }
  
  public void setRecordStatus(int recordStatus) {
    this.recordStatus = recordStatus;
  }
  
  public int getRecordSeq() {
    return this.recordSeq;
  }
  
  public void setRecordSeq(int recordSeq) {
    this.recordSeq = recordSeq;
  }
  
  public int getDutyType() {
    return this.dutyType;
  }
  
  public void setDutyType(int dutyType) {
    this.dutyType = dutyType;
  }
  
  public long getTimeDiff() {
    return this.timeDiff;
  }
  
  public void setTimeDiff(long timeDiff) {
    this.timeDiff = timeDiff;
  }
  
  public Date getDutyTime() {
    return this.dutyTime;
  }
  
  public void setDutyTime(Date dutyTime) {
    this.dutyTime = dutyTime;
  }
  
  public int getNosign() {
    return this.nosign;
  }
  
  public void setNosign(int nosign) {
    this.nosign = nosign;
  }
  
  public String getDutyName() {
    return this.dutyName;
  }
  
  public void setDutyName(String dutyName) {
    this.dutyName = dutyName;
  }
}
