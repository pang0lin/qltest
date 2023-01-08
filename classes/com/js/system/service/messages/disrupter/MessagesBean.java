package com.js.system.service.messages.disrupter;

import java.util.Date;

public class MessagesBean {
  private String methodName;
  
  private String title;
  
  private String url;
  
  private String userIds;
  
  private String moduleType;
  
  private Date startTime = null;
  
  private Date endTime = null;
  
  private String send_UserName;
  
  private Long data_id;
  
  private int showType;
  
  private int hasURL;
  
  private String myId;
  
  private int sendSMS = 1;
  
  private int sendEmail = 1;
  
  public int getSendEmail() {
    return this.sendEmail;
  }
  
  public void setSendEmail(int sendEmail) {
    this.sendEmail = sendEmail;
  }
  
  public String getMyId() {
    return this.myId;
  }
  
  public void setMyId(String myId) {
    this.myId = myId;
  }
  
  public int getHasURL() {
    return this.hasURL;
  }
  
  public void setHasURL(int hasURL) {
    this.hasURL = hasURL;
  }
  
  public String getMethodName() {
    return this.methodName;
  }
  
  public void setMethodName(String methodName) {
    this.methodName = methodName;
  }
  
  public String getTitle() {
    return this.title;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  public String getUrl() {
    return this.url;
  }
  
  public void setUrl(String url) {
    this.url = url;
  }
  
  public String getUserIds() {
    return this.userIds;
  }
  
  public void setUserIds(String userIds) {
    String id = ",";
    String[] userIdStr = userIds.split(",");
    for (int i = 0; i < userIdStr.length; i++) {
      if (!id.contains("," + userIdStr[i] + ","))
        id = String.valueOf(id) + userIdStr[i] + ","; 
    } 
    userIds = (id.length() < 3) ? "" : id.substring(1, id.length() - 1);
    this.userIds = userIds;
  }
  
  public String getModuleType() {
    return this.moduleType;
  }
  
  public void setModuleType(String moduleType) {
    this.moduleType = moduleType;
  }
  
  public Date getStartTime() {
    return this.startTime;
  }
  
  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }
  
  public Date getEndTime() {
    return this.endTime;
  }
  
  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }
  
  public String getSend_UserName() {
    return this.send_UserName;
  }
  
  public void setSend_UserName(String send_UserName) {
    this.send_UserName = send_UserName;
  }
  
  public Long getData_id() {
    return this.data_id;
  }
  
  public void setData_id(Long data_id) {
    this.data_id = data_id;
  }
  
  public int getShowType() {
    return this.showType;
  }
  
  public void setShowType(int showType) {
    this.showType = showType;
  }
  
  public int getSendSMS() {
    return this.sendSMS;
  }
  
  public void setSendSMS(int sendSMS) {
    this.sendSMS = sendSMS;
  }
}
