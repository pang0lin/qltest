package com.js.oa.jsflow.vo;

import java.io.Serializable;

public class WorkLogVO implements Serializable {
  private Long logId;
  
  private String sendUserId;
  
  private String sendUserName;
  
  private String sendTime;
  
  private String sendAction;
  
  private String receiveUserId;
  
  private String receiveUserName;
  
  private String processId;
  
  private String tableId;
  
  private String recordId;
  
  private String domainId;
  
  public Long getLogId() {
    return this.logId;
  }
  
  public void setLogId(Long logId) {
    this.logId = logId;
  }
  
  public String getSendUserId() {
    return this.sendUserId;
  }
  
  public void setSendUserId(String sendUserId) {
    this.sendUserId = sendUserId;
  }
  
  public String getSendUserName() {
    return this.sendUserName;
  }
  
  public void setSendUserName(String sendUserName) {
    this.sendUserName = sendUserName;
  }
  
  public String getSendTime() {
    return this.sendTime;
  }
  
  public void setSendTime(String sendTime) {
    this.sendTime = sendTime;
  }
  
  public String getSendAction() {
    return this.sendAction;
  }
  
  public void setSendAction(String sendAction) {
    this.sendAction = sendAction;
  }
  
  public String getReceiveUserId() {
    return this.receiveUserId;
  }
  
  public void setReceiveUserId(String receiveUserId) {
    this.receiveUserId = receiveUserId;
  }
  
  public String getReceiveUserName() {
    return this.receiveUserName;
  }
  
  public void setReceiveUserName(String receiveUserName) {
    this.receiveUserName = receiveUserName;
  }
  
  public String getProcessId() {
    return this.processId;
  }
  
  public void setProcessId(String processId) {
    this.processId = processId;
  }
  
  public String getTableId() {
    return this.tableId;
  }
  
  public void setTableId(String tableId) {
    this.tableId = tableId;
  }
  
  public String getRecordId() {
    return this.recordId;
  }
  
  public void setRecordId(String recordId) {
    this.recordId = recordId;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
}
