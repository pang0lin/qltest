package com.js.oa.datasync.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class DataSyncSetPO implements Serializable {
  private Long id;
  
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
  
  private String mainTableName;
  
  private String subTableName;
  
  private String syncCondition;
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
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
  
  public String getMainTableName() {
    return this.mainTableName;
  }
  
  public void setMainTableName(String mainTableName) {
    this.mainTableName = mainTableName;
  }
  
  public String getSubTableName() {
    return this.subTableName;
  }
  
  public void setSubTableName(String subTableName) {
    this.subTableName = subTableName;
  }
  
  public String getSyncCondition() {
    return this.syncCondition;
  }
  
  public void setSyncCondition(String syncCondition) {
    this.syncCondition = syncCondition;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof DataSyncSetPO))
      return false; 
    DataSyncSetPO castOther = (DataSyncSetPO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
}
