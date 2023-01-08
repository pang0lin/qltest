package com.js.oa.scheme.workreport.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class WorkReportPostilPO implements Serializable {
  private long id;
  
  private long postilDomainId;
  
  private String postilEmpName;
  
  private String postilContent;
  
  private Date postilTime = null;
  
  private WorkReportPO report;
  
  private String postilEmpSign;
  
  private String nextWorkClew;
  
  private String grade;
  
  private long postilEmpID;
  
  private String postilResult;
  
  private String postilGrade;
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public long getPostilDomainId() {
    return this.postilDomainId;
  }
  
  public void setPostilDomainId(long postilDomainId) {
    this.postilDomainId = postilDomainId;
  }
  
  public String getPostilEmpName() {
    return this.postilEmpName;
  }
  
  public void setPostilEmpName(String postilEmpName) {
    this.postilEmpName = postilEmpName;
  }
  
  public String getPostilContent() {
    return this.postilContent;
  }
  
  public void setPostilContent(String postilContent) {
    this.postilContent = postilContent;
  }
  
  public Date getPostilTime() {
    return this.postilTime;
  }
  
  public void setPostilTime(Date postilTime) {
    this.postilTime = postilTime;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof WorkReportPostilPO))
      return false; 
    WorkReportPostilPO castOther = (WorkReportPostilPO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
  }
  
  public WorkReportPO getReport() {
    return this.report;
  }
  
  public void setReport(WorkReportPO report) {
    this.report = report;
  }
  
  public String getPostilEmpSign() {
    return this.postilEmpSign;
  }
  
  public void setPostilEmpSign(String postilEmpSign) {
    this.postilEmpSign = postilEmpSign;
  }
  
  public String getGrade() {
    return this.grade;
  }
  
  public void setGrade(String grade) {
    this.grade = grade;
  }
  
  public String getNextWorkClew() {
    return this.nextWorkClew;
  }
  
  public void setNextWorkClew(String nextWorkClew) {
    this.nextWorkClew = nextWorkClew;
  }
  
  public long getPostilEmpID() {
    return this.postilEmpID;
  }
  
  public void setPostilEmpID(long postilEmpID) {
    this.postilEmpID = postilEmpID;
  }
  
  public String getPostilGrade() {
    return this.postilGrade;
  }
  
  public void setPostilGrade(String postilGrade) {
    this.postilGrade = postilGrade;
  }
  
  public String getPostilResult() {
    return this.postilResult;
  }
  
  public void setPostilResult(String postilResult) {
    this.postilResult = postilResult;
  }
}
