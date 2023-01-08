package com.js.oa.scheme.taskcenter.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class TaskRemindPO implements Serializable {
  private Long remindId;
  
  private Long remindDomainId;
  
  private Long empId;
  
  private Long taskId;
  
  public TaskRemindPO(Long empId, Long taskId, Long remindDomainId) {
    this.empId = empId;
    this.taskId = taskId;
    this.remindDomainId = remindDomainId;
  }
  
  public TaskRemindPO() {}
  
  public Long getRemindId() {
    return this.remindId;
  }
  
  public void setRemindId(Long remindId) {
    this.remindId = remindId;
  }
  
  public Long getRemindDomainId() {
    return this.remindDomainId;
  }
  
  public void setRemindDomainId(Long remindDomainId) {
    this.remindDomainId = remindDomainId;
  }
  
  public Long getEmpId() {
    return this.empId;
  }
  
  public void setEmpId(Long empId) {
    this.empId = empId;
  }
  
  public Long getTaskId() {
    return this.taskId;
  }
  
  public void setTaskId(Long taskId) {
    this.taskId = taskId;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("remindId", getRemindId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof TaskRemindPO))
      return false; 
    TaskRemindPO castOther = (TaskRemindPO)other;
    return (new EqualsBuilder())
      .append(getRemindId(), castOther.getRemindId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getRemindId())
      .toHashCode();
  }
}
