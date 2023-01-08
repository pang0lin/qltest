package com.js.oa.hr.subsidiarywork.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class NetSurveyPO implements Serializable {
  private long id;
  
  private String surveyContent;
  
  private String surveyRange;
  
  private byte surveyStatus;
  
  private byte surveyType;
  
  private Date surveyBeginTime = null;
  
  private Date surveyEndTime = null;
  
  private long createdOrg;
  
  private Set surveyItems = null;
  
  private String surveyRangeName;
  
  private long createdEmp;
  
  private String domainId;
  
  public NetSurveyPO(String surveyRangeName, Set surveyItems, String surveycontent, String surveyrange, byte surveystatus, byte surveytype, Date surveybegintime, Date surveyendtime, long surveyorgid) {
    this.surveyContent = surveycontent;
    this.surveyRange = surveyrange;
    this.surveyStatus = surveystatus;
    this.surveyType = surveytype;
    this.surveyBeginTime = surveybegintime;
    this.surveyEndTime = surveyendtime;
    this.createdOrg = surveyorgid;
    this.surveyItems = surveyItems;
    this.surveyRangeName = surveyRangeName;
  }
  
  public NetSurveyPO() {}
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public String getSurveyContent() {
    return this.surveyContent;
  }
  
  public void setSurveyContent(String surveyContent) {
    this.surveyContent = surveyContent;
  }
  
  public String getSurveyRange() {
    return this.surveyRange;
  }
  
  public void setSurveyRange(String surveyRange) {
    this.surveyRange = surveyRange;
  }
  
  public byte getSurveyStatus() {
    return this.surveyStatus;
  }
  
  public void setSurveyStatus(byte surveyStatus) {
    this.surveyStatus = surveyStatus;
  }
  
  public byte getSurveyType() {
    return this.surveyType;
  }
  
  public void setSurveyType(byte surveyType) {
    this.surveyType = surveyType;
  }
  
  public Date getSurveyBeginTime() {
    return this.surveyBeginTime;
  }
  
  public void setSurveyBeginTime(Date surveyBeginTime) {
    this.surveyBeginTime = surveyBeginTime;
  }
  
  public Date getSurveyEndTime() {
    return this.surveyEndTime;
  }
  
  public void setSurveyEndTime(Date surveyEndTime) {
    this.surveyEndTime = surveyEndTime;
  }
  
  public long getCreatedOrg() {
    return this.createdOrg;
  }
  
  public void setCreatedOrg(long createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof NetSurveyPO))
      return false; 
    NetSurveyPO castOther = (NetSurveyPO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
  }
  
  public Set getSurveyItems() {
    return this.surveyItems;
  }
  
  public void setSurveyItems(Set surveyItems) {
    this.surveyItems = surveyItems;
  }
  
  public String getSurveyRangeName() {
    return this.surveyRangeName;
  }
  
  public void setSurveyRangeName(String surveyRangeName) {
    this.surveyRangeName = surveyRangeName;
  }
  
  public long getCreatedEmp() {
    return this.createdEmp;
  }
  
  public void setCreatedEmp(long createdEmp) {
    this.createdEmp = createdEmp;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
}
