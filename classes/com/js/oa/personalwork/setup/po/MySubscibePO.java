package com.js.oa.personalwork.setup.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class MySubscibePO implements Serializable {
  private long id;
  
  private long empId;
  
  private long channelId;
  
  private Date subscibeBeginTime = null;
  
  private Date subscibeEndTime = null;
  
  public MySubscibePO(long empId, long channelId, Date subscibebegintime, Date subscibeendtime) {
    this.empId = empId;
    this.channelId = channelId;
    this.subscibeBeginTime = subscibebegintime;
    this.subscibeEndTime = subscibeendtime;
  }
  
  public MySubscibePO() {}
  
  public MySubscibePO(long empId) {
    this.empId = empId;
  }
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public long getEmpId() {
    return this.empId;
  }
  
  public void setEmpId(long empId) {
    this.empId = empId;
  }
  
  public long getChannelId() {
    return this.channelId;
  }
  
  public void setChannelId(long channelId) {
    this.channelId = channelId;
  }
  
  public Date getSubscibeBeginTime() {
    return this.subscibeBeginTime;
  }
  
  public void setSubscibeBeginTime(Date subscibeBeginTime) {
    this.subscibeBeginTime = subscibeBeginTime;
  }
  
  public Date getSubscibeEndTime() {
    return this.subscibeEndTime;
  }
  
  public void setSubscibeEndTime(Date subscibeEndTime) {
    this.subscibeEndTime = subscibeEndTime;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof MySubscibePO))
      return false; 
    MySubscibePO castOther = (MySubscibePO)other;
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
