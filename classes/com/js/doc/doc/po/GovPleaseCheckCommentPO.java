package com.js.doc.doc.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class GovPleaseCheckCommentPO implements Serializable {
  private long id;
  
  private long empId;
  
  private String empName;
  
  private String empSign;
  
  private String empIdea;
  
  private Date createdTime = null;
  
  private GovPleaseCheckPO pleaseCheck;
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public GovPleaseCheckPO getPleaseCheck() {
    return this.pleaseCheck;
  }
  
  public void setPleaseCheck(GovPleaseCheckPO pleaseCheck) {
    this.pleaseCheck = pleaseCheck;
  }
  
  public long getEmpId() {
    return this.empId;
  }
  
  public void setEmpId(long empId) {
    this.empId = empId;
  }
  
  public String getEmpName() {
    return this.empName;
  }
  
  public void setEmpName(String empName) {
    this.empName = empName;
  }
  
  public String getEmpSign() {
    return this.empSign;
  }
  
  public void setEmpSign(String empSign) {
    this.empSign = empSign;
  }
  
  public String getEmpIdea() {
    return this.empIdea;
  }
  
  public void setEmpIdea(String empIdea) {
    this.empIdea = empIdea;
  }
  
  public Date getCreatedTime() {
    return this.createdTime;
  }
  
  public void setCreatedTime(Date createdTime) {
    this.createdTime = createdTime;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof GovPleaseCheckCommentPO))
      return false; 
    GovPleaseCheckCommentPO castOther = (GovPleaseCheckCommentPO)other;
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
