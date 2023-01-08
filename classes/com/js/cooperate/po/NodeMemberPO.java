package com.js.cooperate.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;

public class NodeMemberPO implements Serializable {
  private Long id;
  
  private Long bodyId;
  
  private Long nodeId;
  
  private Long empId;
  
  private String empName;
  
  private Date handTime = null;
  
  private Date doneTime = null;
  
  private Integer isPoster;
  
  private Integer status;
  
  private Integer tracker;
  
  public Integer getTracker() {
    return this.tracker;
  }
  
  public void setTracker(Integer tracker) {
    this.tracker = tracker;
  }
  
  public Integer getStatus() {
    return this.status;
  }
  
  public void setStatus(Integer status) {
    this.status = status;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public Long getBodyId() {
    return this.bodyId;
  }
  
  public void setBodyId(Long bodyId) {
    this.bodyId = bodyId;
  }
  
  public Long getNodeId() {
    return this.nodeId;
  }
  
  public void setNodeId(Long nodeId) {
    this.nodeId = nodeId;
  }
  
  public Long getEmpId() {
    return this.empId;
  }
  
  public void setEmpId(Long empId) {
    this.empId = empId;
  }
  
  public String getEmpName() {
    return this.empName;
  }
  
  public void setEmpName(String empName) {
    this.empName = empName;
  }
  
  public Date getHandTime() {
    return this.handTime;
  }
  
  public void setHandTime(Date handTime) {
    this.handTime = handTime;
  }
  
  public Date getDoneTime() {
    return this.doneTime;
  }
  
  public void setDoneTime(Date doneTime) {
    this.doneTime = doneTime;
  }
  
  public Integer getIsPoster() {
    return this.isPoster;
  }
  
  public void setIsPoster(Integer isPoster) {
    this.isPoster = isPoster;
  }
}
