package com.js.oa.dcq.po;

import java.io.Serializable;

public class DcqMessagesubmissionPO implements Serializable {
  private Long messageId;
  
  private String oid;
  
  private String browserIDStr;
  
  private String receives;
  
  private String infoTitle;
  
  private String infoContent;
  
  private String urgencyLevel;
  
  private String telephone;
  
  private String amendments;
  
  private int releaseStatus;
  
  private int isReturn;
  
  private int Status;
  
  private String reportUnit;
  
  private String sendPersonID;
  
  private String leadersIDStr;
  
  private String mark;
  
  private String bz;
  
  public Long getMessageId() {
    return this.messageId;
  }
  
  public void setMessageId(Long messageId) {
    this.messageId = messageId;
  }
  
  public String getOid() {
    return this.oid;
  }
  
  public void setOid(String oid) {
    this.oid = oid;
  }
  
  public String getBrowserIDStr() {
    return this.browserIDStr;
  }
  
  public void setBrowserIDStr(String browserIDStr) {
    this.browserIDStr = browserIDStr;
  }
  
  public String getReceives() {
    return this.receives;
  }
  
  public void setReceives(String receives) {
    this.receives = receives;
  }
  
  public String getInfoTitle() {
    return this.infoTitle;
  }
  
  public void setInfoTitle(String infoTitle) {
    this.infoTitle = infoTitle;
  }
  
  public String getInfoContent() {
    return this.infoContent;
  }
  
  public void setInfoContent(String infoContent) {
    this.infoContent = infoContent;
  }
  
  public String getUrgencyLevel() {
    return this.urgencyLevel;
  }
  
  public void setUrgencyLevel(String urgencyLevel) {
    this.urgencyLevel = urgencyLevel;
  }
  
  public String getTelephone() {
    return this.telephone;
  }
  
  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }
  
  public String getAmendments() {
    return this.amendments;
  }
  
  public void setAmendments(String amendments) {
    this.amendments = amendments;
  }
  
  public int getReleaseStatus() {
    return this.releaseStatus;
  }
  
  public void setReleaseStatus(int releaseStatus) {
    this.releaseStatus = releaseStatus;
  }
  
  public int getIsReturn() {
    return this.isReturn;
  }
  
  public void setIsReturn(int isReturn) {
    this.isReturn = isReturn;
  }
  
  public int getStatus() {
    return this.Status;
  }
  
  public void setStatus(int status) {
    this.Status = status;
  }
  
  public String getReportUnit() {
    return this.reportUnit;
  }
  
  public void setReportUnit(String reportUnit) {
    this.reportUnit = reportUnit;
  }
  
  public String getSendPersonID() {
    return this.sendPersonID;
  }
  
  public void setSendPersonID(String sendPersonID) {
    this.sendPersonID = sendPersonID;
  }
  
  public String getLeadersIDStr() {
    return this.leadersIDStr;
  }
  
  public void setLeadersIDStr(String leadersIDStr) {
    this.leadersIDStr = leadersIDStr;
  }
  
  public String getMark() {
    return this.mark;
  }
  
  public void setMark(String mark) {
    this.mark = mark;
  }
  
  public String getBz() {
    return this.bz;
  }
  
  public void setBz(String bz) {
    this.bz = bz;
  }
}
