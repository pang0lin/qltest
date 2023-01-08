package com.js.oa.hr.subsidiarywork.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;

public class NetSurveyVotePO implements Serializable {
  private long surveyId;
  
  private long employeeId;
  
  private NetSurveyItemPO item;
  
  private long id;
  
  private String domainId;
  
  private Date voteDate = null;
  
  public NetSurveyVotePO(long id, NetSurveyItemPO item, long surveyId, long employeeId) {
    this.id = id;
    this.surveyId = surveyId;
    this.employeeId = employeeId;
    this.item = item;
  }
  
  public NetSurveyVotePO() {}
  
  public long getSurveyId() {
    return this.surveyId;
  }
  
  public void setSurveyId(long surveyId) {
    this.surveyId = surveyId;
  }
  
  public long getEmployeeId() {
    return this.employeeId;
  }
  
  public void setEmployeeId(long employeeId) {
    this.employeeId = employeeId;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .toString();
  }
  
  public NetSurveyItemPO getItem() {
    return this.item;
  }
  
  public void setItem(NetSurveyItemPO item) {
    this.item = item;
  }
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public Date getVoteDate() {
    return this.voteDate;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
  
  public void setVoteDate(Date voteDate) {
    this.voteDate = voteDate;
  }
}
