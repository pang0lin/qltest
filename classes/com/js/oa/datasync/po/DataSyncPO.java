package com.js.oa.datasync.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class DataSyncPO implements Serializable {
  private Long id;
  
  private String syncTitle;
  
  private String memo;
  
  private String processId;
  
  private String activityId;
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getSyncTitle() {
    return this.syncTitle;
  }
  
  public void setSyncTitle(String syncTitle) {
    this.syncTitle = syncTitle;
  }
  
  public String getMemo() {
    return this.memo;
  }
  
  public void setMemo(String memo) {
    this.memo = memo;
  }
  
  public String getProcessId() {
    return this.processId;
  }
  
  public void setProcessId(String processId) {
    this.processId = processId;
  }
  
  public String getActivityId() {
    return this.activityId;
  }
  
  public void setActivityId(String activityId) {
    this.activityId = activityId;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof DataSyncPO))
      return false; 
    DataSyncPO castOther = (DataSyncPO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
}
