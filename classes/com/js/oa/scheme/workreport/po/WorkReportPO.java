package com.js.oa.scheme.workreport.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class WorkReportPO implements Serializable {
  private long id;
  
  private long reportDomainId;
  
  private long empId;
  
  private String reportEmpName;
  
  private String reportRemark;
  
  private String accessoryName;
  
  private String accessorySaveName;
  
  private byte reportType;
  
  private Date reportTime = null;
  
  private Date reortUpdateTime = null;
  
  private String reportReader;
  
  private String reportReaderId;
  
  private byte hadRead;
  
  private long templateId;
  
  private Set reportLeader = null;
  
  private Set reportPostil = null;
  
  private String content1;
  
  private String content2;
  
  private String nextReport;
  
  private String previousReport;
  
  private String reportCourse;
  
  private String reportJob;
  
  private String reportDepart;
  
  private String reportOrgID;
  
  private String sendType;
  
  private long relprojectId;
  
  private String reportName;
  
  private Date reportInputTime = null;
  
  public String getReportName() {
    return this.reportName;
  }
  
  public void setReportName(String reportName) {
    this.reportName = reportName;
  }
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public long getReportDomainId() {
    return this.reportDomainId;
  }
  
  public void setReportDomainId(long reportDomainId) {
    this.reportDomainId = reportDomainId;
  }
  
  public long getEmpId() {
    return this.empId;
  }
  
  public void setEmpId(long empId) {
    this.empId = empId;
  }
  
  public String getReportEmpName() {
    return this.reportEmpName;
  }
  
  public void setReportEmpName(String reportEmpName) {
    this.reportEmpName = reportEmpName;
  }
  
  public String getPreviousReport() {
    return this.previousReport;
  }
  
  public void setPreviousReport(String previousReport) {
    this.previousReport = previousReport;
  }
  
  public String getNextReport() {
    return this.nextReport;
  }
  
  public void setNextReport(String nextReport) {
    this.nextReport = nextReport;
  }
  
  public String getReportRemark() {
    return this.reportRemark;
  }
  
  public void setReportRemark(String reportRemark) {
    this.reportRemark = reportRemark;
  }
  
  public String getAccessoryName() {
    return this.accessoryName;
  }
  
  public void setAccessoryName(String accessoryName) {
    this.accessoryName = accessoryName;
  }
  
  public String getAccessorySaveName() {
    return this.accessorySaveName;
  }
  
  public void setAccessorySaveName(String accessorySaveName) {
    this.accessorySaveName = accessorySaveName;
  }
  
  public byte getReportType() {
    return this.reportType;
  }
  
  public void setReportType(byte reportType) {
    this.reportType = reportType;
  }
  
  public Date getReportTime() {
    return this.reportTime;
  }
  
  public void setReportTime(Date reportTime) {
    this.reportTime = reportTime;
  }
  
  public Date getReortUpdateTime() {
    return this.reortUpdateTime;
  }
  
  public void setReortUpdateTime(Date reortUpdateTime) {
    this.reortUpdateTime = reortUpdateTime;
  }
  
  public String getReportReader() {
    return this.reportReader;
  }
  
  public void setReportReader(String reportReader) {
    this.reportReader = reportReader;
  }
  
  public String getReportReaderId() {
    return this.reportReaderId;
  }
  
  public void setReportReaderId(String reportReaderId) {
    this.reportReaderId = reportReaderId;
  }
  
  public byte getHadRead() {
    return this.hadRead;
  }
  
  public void setHadRead(byte hadRead) {
    this.hadRead = hadRead;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof WorkReportPO))
      return false; 
    WorkReportPO castOther = (WorkReportPO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
  }
  
  public long getTemplateId() {
    return this.templateId;
  }
  
  public void setTemplateId(long templateId) {
    this.templateId = templateId;
  }
  
  public Set getReportLeader() {
    return this.reportLeader;
  }
  
  public void setReportLeader(Set reportLeader) {
    this.reportLeader = reportLeader;
  }
  
  public Set getReportPostil() {
    return this.reportPostil;
  }
  
  public void setReportPostil(Set reportPostil) {
    this.reportPostil = reportPostil;
  }
  
  public String getContent1() {
    return this.content1;
  }
  
  public void setContent1(String content1) {
    this.content1 = content1;
  }
  
  public String getContent2() {
    return this.content2;
  }
  
  public void setContent2(String content2) {
    this.content2 = content2;
  }
  
  public String getReportCourse() {
    return this.reportCourse;
  }
  
  public void setReportCourse(String reportCourse) {
    this.reportCourse = reportCourse;
  }
  
  public String getReportDepart() {
    return this.reportDepart;
  }
  
  public void setReportDepart(String reportDepart) {
    this.reportDepart = reportDepart;
  }
  
  public String getReportJob() {
    return this.reportJob;
  }
  
  public void setReportJob(String reportJob) {
    this.reportJob = reportJob;
  }
  
  public String getReportOrgID() {
    return this.reportOrgID;
  }
  
  public void setReportOrgID(String reportOrgID) {
    this.reportOrgID = reportOrgID;
  }
  
  public String getSendType() {
    return this.sendType;
  }
  
  public void setSendType(String sendType) {
    this.sendType = sendType;
  }
  
  public long getRelprojectId() {
    return this.relprojectId;
  }
  
  public void setRelprojectId(long relprojectId) {
    this.relprojectId = relprojectId;
  }
  
  public Date getReportInputTime() {
    return this.reportInputTime;
  }
  
  public void setReportInputTime(Date reportInputTime) {
    this.reportInputTime = reportInputTime;
  }
}
