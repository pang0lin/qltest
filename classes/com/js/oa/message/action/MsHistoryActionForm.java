package com.js.oa.message.action;

import java.util.Date;
import org.apache.struts.action.ActionForm;

public class MsHistoryActionForm extends ActionForm {
  private Long historyId;
  
  private String fromUserId;
  
  private String fromOrgId;
  
  private String sendToPerson;
  
  private Date sendTime = null;
  
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
}
