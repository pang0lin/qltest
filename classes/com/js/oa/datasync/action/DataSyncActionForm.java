package com.js.oa.datasync.action;

import org.apache.struts.action.ActionForm;

public class DataSyncActionForm extends ActionForm {
  private String id;
  
  private String syncTitle;
  
  private String memo;
  
  private String processId;
  
  private String activityId;
  
  private String syncId;
  
  private String setTitle;
  
  private String description;
  
  private String setType;
  
  private String dataSourceType;
  
  private String dataSourceName;
  
  private String operateTable;
  
  private String operateType;
  
  private String exeCondition;
  
  private Integer executeOrder;
  
  public String getId() {
    return this.id;
  }
  
  public void setId(String id) {
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
  
  public String getSyncId() {
    return this.syncId;
  }
  
  public void setSyncId(String syncId) {
    this.syncId = syncId;
  }
  
  public String getSetTitle() {
    return this.setTitle;
  }
  
  public void setSetTitle(String setTitle) {
    this.setTitle = setTitle;
  }
  
  public String getDescription() {
    return this.description;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
  
  public String getSetType() {
    return this.setType;
  }
  
  public void setSetType(String setType) {
    this.setType = setType;
  }
  
  public String getDataSourceType() {
    return this.dataSourceType;
  }
  
  public void setDataSourceType(String dataSourceType) {
    this.dataSourceType = dataSourceType;
  }
  
  public String getDataSourceName() {
    return this.dataSourceName;
  }
  
  public void setDataSourceName(String dataSourceName) {
    this.dataSourceName = dataSourceName;
  }
  
  public String getOperateTable() {
    return this.operateTable;
  }
  
  public void setOperateTable(String operateTable) {
    this.operateTable = operateTable;
  }
  
  public String getOperateType() {
    return this.operateType;
  }
  
  public void setOperateType(String operateType) {
    this.operateType = operateType;
  }
  
  public String getExeCondition() {
    return this.exeCondition;
  }
  
  public void setExeCondition(String exeCondition) {
    this.exeCondition = exeCondition;
  }
  
  public Integer getExecuteOrder() {
    return this.executeOrder;
  }
  
  public void setExecuteOrder(Integer executeOrder) {
    this.executeOrder = executeOrder;
  }
}
