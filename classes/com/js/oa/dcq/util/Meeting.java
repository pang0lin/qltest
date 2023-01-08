package com.js.oa.dcq.util;

import java.util.List;

public class Meeting {
  private String execMsg;
  
  private String exeCode;
  
  private List<MeetingAccdoc> meetingInformIssue;
  
  public String getExecMsg() {
    return this.execMsg;
  }
  
  public void setExecMsg(String execMsg) {
    this.execMsg = execMsg;
  }
  
  public String getExeCode() {
    return this.exeCode;
  }
  
  public void setExeCode(String exeCode) {
    this.exeCode = exeCode;
  }
  
  public List<MeetingAccdoc> getMeetingInformIssue() {
    return this.meetingInformIssue;
  }
  
  public void setMeetingInformIssue(List<MeetingAccdoc> meetingInformIssue) {
    this.meetingInformIssue = meetingInformIssue;
  }
}
