package com.js.oa.scheme.taskcenter.po;

import java.util.Date;

public class TaskPressPO {
  private Long pressId;
  
  private String pressContext;
  
  private String pressUserName;
  
  private String toUserName;
  
  private Long pressUserId;
  
  private String toUserId;
  
  private Date pressDate = new Date();
  
  private Integer isHidden = Integer.valueOf(0);
  
  private Long taskId;
  
  private Integer presstype = Integer.valueOf(1);
  
  public Long getPressId() {
    return this.pressId;
  }
  
  public void setPressId(Long pressId) {
    this.pressId = pressId;
  }
  
  public String getPressContext() {
    return this.pressContext;
  }
  
  public void setPressContext(String pressContext) {
    this.pressContext = pressContext;
  }
  
  public String getPressUserName() {
    return this.pressUserName;
  }
  
  public void setPressUserName(String pressUserName) {
    this.pressUserName = pressUserName;
  }
  
  public String getToUserName() {
    return this.toUserName;
  }
  
  public void setToUserName(String toUserName) {
    this.toUserName = toUserName;
  }
  
  public Long getPressUserId() {
    return this.pressUserId;
  }
  
  public void setPressUserId(Long pressUserId) {
    this.pressUserId = pressUserId;
  }
  
  public String getToUserId() {
    return this.toUserId;
  }
  
  public void setToUserId(String toUserId) {
    this.toUserId = toUserId;
  }
  
  public Date getPressDate() {
    return this.pressDate;
  }
  
  public void setPressDate(Date pressDate) {
    this.pressDate = pressDate;
  }
  
  public Integer getIsHidden() {
    return this.isHidden;
  }
  
  public void setIsHidden(Integer isHidden) {
    this.isHidden = isHidden;
  }
  
  public Long getTaskId() {
    return this.taskId;
  }
  
  public void setTaskId(Long taskId) {
    this.taskId = taskId;
  }
  
  public Integer getPresstype() {
    return this.presstype;
  }
  
  public void setPresstype(Integer presstype) {
    this.presstype = presstype;
  }
}
