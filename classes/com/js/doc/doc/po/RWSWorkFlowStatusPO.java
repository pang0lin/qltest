package com.js.doc.doc.po;

import java.io.Serializable;
import java.util.Date;

public class RWSWorkFlowStatusPO implements Serializable {
  private Long id;
  
  private String tableName;
  
  private Long recordId;
  
  private String status;
  
  private Long yuguidang_empid;
  
  private Date yuguidang_time = null;
  
  private Long guidang_empid;
  
  private Date guidang_time = null;
  
  private String guidang_guid;
  
  private Long quxiaoguidang_empid;
  
  private Date quxiaoguidang_time = null;
  
  private Long tongyiguidang_empid;
  
  private Date tongyiguidang_time = null;
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getTableName() {
    return this.tableName;
  }
  
  public void setTableName(String tableName) {
    this.tableName = tableName;
  }
  
  public Long getRecordId() {
    return this.recordId;
  }
  
  public void setRecordId(Long recordId) {
    this.recordId = recordId;
  }
  
  public String getStatus() {
    return this.status;
  }
  
  public void setStatus(String status) {
    this.status = status;
  }
  
  public Long getYuguidang_empid() {
    return this.yuguidang_empid;
  }
  
  public void setYuguidang_empid(Long yuguidang_empid) {
    this.yuguidang_empid = yuguidang_empid;
  }
  
  public Date getYuguidang_time() {
    return this.yuguidang_time;
  }
  
  public void setYuguidang_time(Date yuguidang_time) {
    this.yuguidang_time = yuguidang_time;
  }
  
  public Long getGuidang_empid() {
    return this.guidang_empid;
  }
  
  public void setGuidang_empid(Long guidang_empid) {
    this.guidang_empid = guidang_empid;
  }
  
  public Date getGuidang_time() {
    return this.guidang_time;
  }
  
  public void setGuidang_time(Date guidang_time) {
    this.guidang_time = guidang_time;
  }
  
  public String getGuidang_guid() {
    return this.guidang_guid;
  }
  
  public void setGuidang_guid(String guidang_guid) {
    this.guidang_guid = guidang_guid;
  }
  
  public Long getQuxiaoguidang_empid() {
    return this.quxiaoguidang_empid;
  }
  
  public void setQuxiaoguidang_empid(Long quxiaoguidang_empid) {
    this.quxiaoguidang_empid = quxiaoguidang_empid;
  }
  
  public Date getQuxiaoguidang_time() {
    return this.quxiaoguidang_time;
  }
  
  public void setQuxiaoguidang_time(Date quxiaoguidang_time) {
    this.quxiaoguidang_time = quxiaoguidang_time;
  }
  
  public Long getTongyiguidang_empid() {
    return this.tongyiguidang_empid;
  }
  
  public void setTongyiguidang_empid(Long tongyiguidang_empid) {
    this.tongyiguidang_empid = tongyiguidang_empid;
  }
  
  public Date getTongyiguidang_time() {
    return this.tongyiguidang_time;
  }
  
  public void setTongyiguidang_time(Date tongyiguidang_time) {
    this.tongyiguidang_time = tongyiguidang_time;
  }
}
