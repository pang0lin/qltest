package com.js.oa.routine.boardroom.po;

public class BoardRoomRegularPO {
  private Long regularId;
  
  private Integer meetingCircle;
  
  private Integer meetingLength;
  
  private Integer typeFlag;
  
  private Long bdroomAppType;
  
  private String meetingDateBegin;
  
  private String meetingDateEnd;
  
  private String everyMeetingBegin = "0";
  
  private String everyMeetingEnd = "0";
  
  private Integer everyMeetingBeginTime;
  
  private Integer everyMeetingEndTime;
  
  public Long getRegularId() {
    return this.regularId;
  }
  
  public void setRegularId(Long regularId) {
    this.regularId = regularId;
  }
  
  public Integer getMeetingCircle() {
    return this.meetingCircle;
  }
  
  public void setMeetingCircle(Integer meetingCircle) {
    this.meetingCircle = meetingCircle;
  }
  
  public Integer getMeetingLength() {
    return this.meetingLength;
  }
  
  public void setMeetingLength(Integer meetingLength) {
    this.meetingLength = meetingLength;
  }
  
  public Integer getTypeFlag() {
    return this.typeFlag;
  }
  
  public void setTypeFlag(Integer typeFlag) {
    this.typeFlag = typeFlag;
  }
  
  public Long getBdroomAppType() {
    return this.bdroomAppType;
  }
  
  public void setBdroomAppType(Long bdroomAppType) {
    this.bdroomAppType = bdroomAppType;
  }
  
  public String getMeetingDateBegin() {
    return this.meetingDateBegin;
  }
  
  public void setMeetingDateBegin(String meetingDateBegin) {
    this.meetingDateBegin = meetingDateBegin;
  }
  
  public String getMeetingDateEnd() {
    return this.meetingDateEnd;
  }
  
  public void setMeetingDateEnd(String meetingDateEnd) {
    this.meetingDateEnd = meetingDateEnd;
  }
  
  public String getEveryMeetingBegin() {
    return this.everyMeetingBegin;
  }
  
  public void setEveryMeetingBegin(String everyMeetingBegin) {
    this.everyMeetingBegin = everyMeetingBegin;
  }
  
  public String getEveryMeetingEnd() {
    return this.everyMeetingEnd;
  }
  
  public void setEveryMeetingEnd(String everyMeetingEnd) {
    this.everyMeetingEnd = everyMeetingEnd;
  }
  
  public Integer getEveryMeetingBeginTime() {
    return this.everyMeetingBeginTime;
  }
  
  public void setEveryMeetingBeginTime(Integer everyMeetingBeginTime) {
    this.everyMeetingBeginTime = everyMeetingBeginTime;
  }
  
  public Integer getEveryMeetingEndTime() {
    return this.everyMeetingEndTime;
  }
  
  public void setEveryMeetingEndTime(Integer everyMeetingEndTime) {
    this.everyMeetingEndTime = everyMeetingEndTime;
  }
}
