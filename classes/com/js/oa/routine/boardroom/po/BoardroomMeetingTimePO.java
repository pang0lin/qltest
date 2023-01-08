package com.js.oa.routine.boardroom.po;

import java.io.Serializable;
import java.util.Date;

public class BoardroomMeetingTimePO implements Serializable {
  private Long id;
  
  private BoardRoomApplyPO apply;
  
  private Date meetingDate = null;
  
  private String startTime;
  
  private String endTime;
  
  private Integer sortNum;
  
  private Integer status;
  
  private String descriptions;
  
  private int meetingDay;
  
  private Long beginLong;
  
  private Long endLong;
  
  public String getEndTime() {
    return this.endTime;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public String getStartTime() {
    return this.startTime;
  }
  
  public Date getMeetingDate() {
    return this.meetingDate;
  }
  
  public BoardRoomApplyPO getApply() {
    return this.apply;
  }
  
  public Integer getSortNum() {
    return this.sortNum;
  }
  
  public Integer getStatus() {
    return this.status;
  }
  
  public String getDescriptions() {
    return this.descriptions;
  }
  
  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }
  
  public void setMeetingDate(Date meetingDate) {
    this.meetingDate = meetingDate;
  }
  
  public void setApply(BoardRoomApplyPO apply) {
    this.apply = apply;
  }
  
  public void setSortNum(Integer sortNum) {
    this.sortNum = sortNum;
  }
  
  public void setStatus(Integer status) {
    this.status = status;
  }
  
  public void setDescriptions(String descriptions) {
    this.descriptions = descriptions;
  }
  
  public int getMeetingDay() {
    return this.meetingDay;
  }
  
  public void setMeetingDay(int meetingDay) {
    this.meetingDay = meetingDay;
  }
  
  public Long getBeginLong() {
    return this.beginLong;
  }
  
  public void setBeginLong(Long beginLong) {
    this.beginLong = beginLong;
  }
  
  public Long getEndLong() {
    return this.endLong;
  }
  
  public void setEndLong(Long endLong) {
    this.endLong = endLong;
  }
}
