package com.js.oa.scheme.workreport.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class WorkReportTransmitPO implements Serializable {
  private long id;
  
  private long transFromEMP;
  
  private String transFromEMPName;
  
  private String transToEMP;
  
  private String transToEMPName;
  
  private String transReason;
  
  private long workreportID;
  
  private long domain;
  
  private Date transTime = null;
  
  public long getDomain() {
    return this.domain;
  }
  
  public void setDomain(long domain) {
    this.domain = domain;
  }
  
  public long getId() {
    return this.id;
  }
  
  private void setId(long id) {
    this.id = id;
  }
  
  public long getTransFromEMP() {
    return this.transFromEMP;
  }
  
  public void setTransFromEMP(long transFromEMP) {
    this.transFromEMP = transFromEMP;
  }
  
  public String getTransFromEMPName() {
    return this.transFromEMPName;
  }
  
  public void setTransFromEMPName(String transFromEMPName) {
    this.transFromEMPName = transFromEMPName;
  }
  
  public String getTransReason() {
    return this.transReason;
  }
  
  public void setTransReason(String transReason) {
    this.transReason = transReason;
  }
  
  public Date getTransTime() {
    return this.transTime;
  }
  
  public void setTransTime(Date transTime) {
    this.transTime = transTime;
  }
  
  public String getTransToEMP() {
    return this.transToEMP;
  }
  
  public void setTransToEMP(String transToEMP) {
    this.transToEMP = transToEMP;
  }
  
  public String getTransToEMPName() {
    return this.transToEMPName;
  }
  
  public void setTransToEMPName(String transToEMPName) {
    this.transToEMPName = transToEMPName;
  }
  
  public long getWorkreportID() {
    return this.workreportID;
  }
  
  public void setWorkreportID(long workreportID) {
    this.workreportID = workreportID;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof WorkReportTransmitPO))
      return false; 
    WorkReportTransmitPO castOther = (WorkReportTransmitPO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
}
