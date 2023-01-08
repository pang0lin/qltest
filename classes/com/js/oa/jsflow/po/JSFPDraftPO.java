package com.js.oa.jsflow.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class JSFPDraftPO implements Serializable {
  private Long id;
  
  private String processId;
  
  private String tableId;
  
  private String recordId;
  
  private String userId;
  
  private String processName;
  
  private String workTitle;
  
  private Date saveTime = null;
  
  private String relProjectId;
  
  public boolean equals(Object other) {
    if (!(other instanceof JSFPDraftPO))
      return false; 
    JSFPDraftPO castOther = (JSFPDraftPO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getProcessId() {
    return this.processId;
  }
  
  public void setProcessId(String processId) {
    this.processId = processId;
  }
  
  public String getTableId() {
    return this.tableId;
  }
  
  public void setTableId(String tableId) {
    this.tableId = tableId;
  }
  
  public String getRecordId() {
    return this.recordId;
  }
  
  public void setRecordId(String recordId) {
    this.recordId = recordId;
  }
  
  public String getUserId() {
    return this.userId;
  }
  
  public void setUserId(String userId) {
    this.userId = userId;
  }
  
  public String getProcessName() {
    return this.processName;
  }
  
  public void setProcessName(String processName) {
    this.processName = processName;
  }
  
  public String getWorkTitle() {
    return this.workTitle;
  }
  
  public void setWorkTitle(String workTitle) {
    this.workTitle = workTitle;
  }
  
  public Date getSaveTime() {
    return this.saveTime;
  }
  
  public void setSaveTime(Date saveTime) {
    this.saveTime = saveTime;
  }
  
  public String getRelProjectId() {
    return this.relProjectId;
  }
  
  public void setRelProjectId(String relProjectId) {
    this.relProjectId = relProjectId;
  }
}
