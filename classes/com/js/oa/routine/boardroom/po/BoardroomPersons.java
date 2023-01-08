package com.js.oa.routine.boardroom.po;

import java.io.Serializable;
import java.util.Date;

public class BoardroomPersons implements Serializable {
  private Long id;
  
  private BoardRoomApplyPO apply;
  
  private Long empId;
  
  private String empName;
  
  private Long orgId;
  
  private String orgName;
  
  private Date replyDate = null;
  
  private String status;
  
  private String content;
  
  public String getOrgName() {
    return this.orgName;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public Date getReplyDate() {
    return this.replyDate;
  }
  
  public String getStatus() {
    return this.status;
  }
  
  public String getEmpName() {
    return this.empName;
  }
  
  public String getContent() {
    return this.content;
  }
  
  public Long getEmpId() {
    return this.empId;
  }
  
  public void setOrgId(Long orgId) {
    this.orgId = orgId;
  }
  
  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public void setReplyDate(Date replyDate) {
    this.replyDate = replyDate;
  }
  
  public void setStatus(String status) {
    this.status = status;
  }
  
  public void setEmpName(String empName) {
    this.empName = empName;
  }
  
  public void setContent(String content) {
    this.content = content;
  }
  
  public void setEmpId(Long empId) {
    this.empId = empId;
  }
  
  public void setApply(BoardRoomApplyPO apply) {
    this.apply = apply;
  }
  
  public Long getOrgId() {
    return this.orgId;
  }
  
  public BoardRoomApplyPO getApply() {
    return this.apply;
  }
}
