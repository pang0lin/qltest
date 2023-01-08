package com.js.oa.scheme.taskcenter.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class TaskPeriodicityAccessoryPO implements Serializable {
  private Long accessoryId;
  
  private String accessoryName;
  
  private String accessorySaveName;
  
  private Long domainId;
  
  private TaskPeriodicityPO periodicity;
  
  public Long getAccessoryId() {
    return this.accessoryId;
  }
  
  public void setAccessoryId(Long accessoryId) {
    this.accessoryId = accessoryId;
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
  
  public Long getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof TaskHistoryAccessoryPO))
      return false; 
    TaskHistoryAccessoryPO castOther = (TaskHistoryAccessoryPO)other;
    return (new EqualsBuilder()).append(getAccessoryId(), castOther.getAccessoryId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getAccessoryId()).toHashCode();
  }
  
  public TaskPeriodicityPO getPeriodicity() {
    return this.periodicity;
  }
  
  public void setPeriodicity(TaskPeriodicityPO periodicity) {
    this.periodicity = periodicity;
  }
}
