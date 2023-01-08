package com.js.oa.routine.boardroom.po;

import java.util.Date;

public class BoardRoomDiscussionPO {
  private Long discussionId;
  
  private String discussionContext;
  
  private Long discussionEmpId;
  
  private String discussionEmpName;
  
  private Integer flag;
  
  private Date discussionDate = null;
  
  private Long applyId;
  
  private Long orgId;
  
  public Long getDiscussionId() {
    return this.discussionId;
  }
  
  public void setDiscussionId(Long discussionId) {
    this.discussionId = discussionId;
  }
  
  public String getDiscussionContext() {
    return this.discussionContext;
  }
  
  public void setDiscussionContext(String discussionContext) {
    this.discussionContext = discussionContext;
  }
  
  public Long getDiscussionEmpId() {
    return this.discussionEmpId;
  }
  
  public void setDiscussionEmpId(Long discussionEmpId) {
    this.discussionEmpId = discussionEmpId;
  }
  
  public String getDiscussionEmpName() {
    return this.discussionEmpName;
  }
  
  public void setDiscussionEmpName(String discussionEmpName) {
    this.discussionEmpName = discussionEmpName;
  }
  
  public Integer getFlag() {
    return this.flag;
  }
  
  public void setFlag(Integer flag) {
    this.flag = flag;
  }
  
  public Date getDiscussionDate() {
    return this.discussionDate;
  }
  
  public void setDiscussionDate(Date discussionDate) {
    this.discussionDate = discussionDate;
  }
  
  public Long getApplyId() {
    return this.applyId;
  }
  
  public void setApplyId(Long applyId) {
    this.applyId = applyId;
  }
  
  public Long getOrgId() {
    return this.orgId;
  }
  
  public void setOrgId(Long orgId) {
    this.orgId = orgId;
  }
}
