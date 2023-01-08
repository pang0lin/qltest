package com.js.oa.message.po;

import java.util.Date;

public class MsHistoryPO {
  private Long historyId;
  
  private String fromUserId;
  
  private String fromOrgId;
  
  private String sendToPerson;
  
  private Date sendTime = null;
  
  private String msContext;
  
  private String result;
  
  private Long sendLong;
  
  private Integer flag;
  
  private String extendCode;
  
  private String receiveCode;
  
  private String receiveContent;
  
  public String getReceiveCode() {
    return this.receiveCode;
  }
  
  public void setReceiveCode(String receiveCode) {
    this.receiveCode = receiveCode;
  }
  
  public String getReceiveContent() {
    return this.receiveContent;
  }
  
  public void setReceiveContent(String receiveContent) {
    this.receiveContent = receiveContent;
  }
  
  public void setExtendCode(String extendCode) {
    this.extendCode = extendCode;
  }
  
  public String getExtendCode() {
    return this.extendCode;
  }
  
  public String getMsContext() {
    return this.msContext;
  }
  
  public void setMsContext(String msContext) {
    this.msContext = msContext;
  }
  
  public String getResult() {
    return this.result;
  }
  
  public void setResult(String result) {
    this.result = result;
  }
  
  public Long getHistoryId() {
    return this.historyId;
  }
  
  public void setHistoryId(Long historyId) {
    this.historyId = historyId;
  }
  
  public String getFromUserId() {
    return this.fromUserId;
  }
  
  public void setFromUserId(String fromUserId) {
    this.fromUserId = fromUserId;
  }
  
  public String getFromOrgId() {
    return this.fromOrgId;
  }
  
  public void setFromOrgId(String fromOrgId) {
    this.fromOrgId = fromOrgId;
  }
  
  public String getSendToPerson() {
    return this.sendToPerson;
  }
  
  public void setSendToPerson(String sendToPerson) {
    this.sendToPerson = sendToPerson;
  }
  
  public Date getSendTime() {
    return this.sendTime;
  }
  
  public void setSendTime(Date sendTime) {
    this.sendTime = sendTime;
  }
  
  public Long getSendLong() {
    return this.sendLong;
  }
  
  public void setSendLong(Long sendLong) {
    this.sendLong = sendLong;
  }
  
  public Integer getFlag() {
    return this.flag;
  }
  
  public void setFlag(Integer flag) {
    this.flag = flag;
  }
}
