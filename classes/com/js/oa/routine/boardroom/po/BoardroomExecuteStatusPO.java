package com.js.oa.routine.boardroom.po;

import java.io.Serializable;

public class BoardroomExecuteStatusPO implements Serializable {
  private Long id;
  
  private Long applyId;
  
  private Long meetingId;
  
  private String empName;
  
  private Long empId;
  
  private String orgName;
  
  private Long orgId;
  
  private Integer sortNum;
  
  private String isJoined;
  
  private String memos;
  
  public String getOrgName() {
    return this.orgName;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public String getMemos() {
    return this.memos;
  }
  
  public String getEmpName() {
    return this.empName;
  }
  
  public Integer getSortNum() {
    return this.sortNum;
  }
  
  public Long getEmpId() {
    return this.empId;
  }
  
  public String getIsJoined() {
    return this.isJoined;
  }
  
  public Long getApplyId() {
    return this.applyId;
  }
  
  public Long getOrgId() {
    return this.orgId;
  }
  
  public void setMeetingId(Long meetingId) {
    this.meetingId = meetingId;
  }
  
  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public void setMemos(String memos) {
    this.memos = memos;
  }
  
  public void setEmpName(String empName) {
    this.empName = empName;
  }
  
  public void setSortNum(Integer sortNum) {
    this.sortNum = sortNum;
  }
  
  public void setEmpId(Long empId) {
    this.empId = empId;
  }
  
  public void setIsJoined(String isJoined) {
    this.isJoined = isJoined;
  }
  
  public void setApplyId(Long applyId) {
    this.applyId = applyId;
  }
  
  public void setOrgId(Long orgId) {
    this.orgId = orgId;
  }
  
  public Long getMeetingId() {
    return this.meetingId;
  }
}
