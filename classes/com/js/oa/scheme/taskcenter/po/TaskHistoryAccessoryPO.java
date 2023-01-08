package com.js.oa.scheme.taskcenter.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class TaskHistoryAccessoryPO implements Serializable {
  private Long accessoryId;
  
  private String accessoryName;
  
  private String accessorySaveName;
  
  private Long domainId;
  
  private TaskHistoryPO informationHistory;
  
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
  
  public TaskHistoryPO getInformationHistory() {
    return this.informationHistory;
  }
  
  public void setInformationHistory(TaskHistoryPO informationHistory) {
    this.informationHistory = informationHistory;
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
}
