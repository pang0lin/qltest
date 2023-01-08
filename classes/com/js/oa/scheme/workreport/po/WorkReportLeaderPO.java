package com.js.oa.scheme.workreport.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class WorkReportLeaderPO implements Serializable {
  private long id;
  
  private long rlDomainId;
  
  private long empId;
  
  private long reportId;
  
  private byte hadRead;
  
  private WorkReportPO report;
  
  public WorkReportLeaderPO(long empId, long rlDomainId, WorkReportPO reportId, byte hadread) {
    this.empId = empId;
    this.report = reportId;
    this.hadRead = hadread;
    this.rlDomainId = rlDomainId;
  }
  
  public WorkReportLeaderPO() {}
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public long getRlDomainId() {
    return this.rlDomainId;
  }
  
  public void setRlDomainId(long rlDomainid) {
    this.rlDomainId = rlDomainid;
  }
  
  public long getEmpId() {
    return this.empId;
  }
  
  public void setEmpId(long empId) {
    this.empId = empId;
  }
  
  public WorkReportPO getReport() {
    return this.report;
  }
  
  public void setReport(WorkReportPO report) {
    this.report = report;
  }
  
  public byte getHadRead() {
    return this.hadRead;
  }
  
  public long getReportId() {
    return this.reportId;
  }
  
  public void setHadRead(byte hadRead) {
    this.hadRead = hadRead;
  }
  
  public void setReportId(long reportId) {
    this.reportId = reportId;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof WorkReportLeaderPO))
      return false; 
    WorkReportLeaderPO castOther = (WorkReportLeaderPO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
  }
}
