package com.js.oa.hr.personnelmanager.action;

import org.apache.struts.action.ActionForm;

public class EmpRemindActionForm extends ActionForm {
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
