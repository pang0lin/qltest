package com.js.oa.hr.personnelmanager.po;

import java.io.Serializable;

public class EmpRemindPO implements Serializable {
  private Long id;
  
  private String empName;
  
  private String empId;
  
  private String remindTime;
  
  private String remindType;
  
  private String sendToId;
  
  private String sendToName;
  
  private Long createId;
  
  private String creater;
  
  private String createTime;
  
  private String state;
  
  public EmpRemindPO() {}
  
  public EmpRemindPO(String empName, String empId, String remindTime, String remindType, String sendToId, String sendToName, Long createId, String creater, String createTime, String state) {
    this.empName = empName;
    this.empId = empId;
    this.remindTime = remindTime;
    this.remindType = remindType;
    this.sendToId = sendToId;
    this.sendToName = sendToName;
    this.createId = createId;
    this.creater = creater;
    this.createTime = createTime;
    this.state = state;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getEmpName() {
    return this.empName;
  }
  
  public void setEmpName(String empName) {
    this.empName = empName;
  }
  
  public String getEmpId() {
    return this.empId;
  }
  
  public void setEmpId(String empId) {
    this.empId = empId;
  }
  
  public String getRemindTime() {
    return this.remindTime;
  }
  
  public void setRemindTime(String remindTime) {
    this.remindTime = remindTime;
  }
  
  public String getRemindType() {
    return this.remindType;
  }
  
  public void setRemindType(String remindType) {
    this.remindType = remindType;
  }
  
  public String getSendToId() {
    return this.sendToId;
  }
  
  public void setSendToId(String sendToId) {
    this.sendToId = sendToId;
  }
  
  public String getSendToName() {
    return this.sendToName;
  }
  
  public void setSendToName(String sendToName) {
    this.sendToName = sendToName;
  }
  
  public Long getCreateId() {
    return this.createId;
  }
  
  public void setCreateId(Long createId) {
    this.createId = createId;
  }
  
  public String getCreater() {
    return this.creater;
  }
  
  public void setCreater(String creater) {
    this.creater = creater;
  }
  
  public String getCreateTime() {
    return this.createTime;
  }
  
  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }
  
  public String getState() {
    return this.state;
  }
  
  public void setState(String state) {
    this.state = state;
  }
}
