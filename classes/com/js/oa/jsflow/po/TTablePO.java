package com.js.oa.jsflow.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class TTablePO implements Serializable {
  private Long tableId;
  
  private String tableCode;
  
  private String tableDesName;
  
  private String tableName;
  
  private Long tableModel;
  
  private String tableFilePath;
  
  private String tableManId;
  
  private String tableMan;
  
  private int tableRight;
  
  private String tableOwner;
  
  private Date tableDate = null;
  
  private Set field = null;
  
  public Long getTableId() {
    return this.tableId;
  }
  
  public void setTableId(Long tableId) {
    this.tableId = tableId;
  }
  
  public String getTableCode() {
    return this.tableCode;
  }
  
  public void setTableCode(String tableCode) {
    this.tableCode = tableCode;
  }
  
  public String getTableDesName() {
    return this.tableDesName;
  }
  
  public void setTableDesName(String tableDesName) {
    this.tableDesName = tableDesName;
  }
  
  public String getTableName() {
    return this.tableName;
  }
  
  public void setTableName(String tableName) {
    this.tableName = tableName;
  }
  
  public Long getTableModel() {
    return this.tableModel;
  }
  
  public void setTableModel(Long tableModel) {
    this.tableModel = tableModel;
  }
  
  public String getTableFilePath() {
    return this.tableFilePath;
  }
  
  public void setTableFilePath(String tableFilePath) {
    this.tableFilePath = tableFilePath;
  }
  
  public String getTableManId() {
    return this.tableManId;
  }
  
  public void setTableManId(String tableManId) {
    this.tableManId = tableManId;
  }
  
  public String getTableMan() {
    return this.tableMan;
  }
  
  public void setTableMan(String tableMan) {
    this.tableMan = tableMan;
  }
  
  public int getTableRight() {
    return this.tableRight;
  }
  
  public void setTableRight(int tableRight) {
    this.tableRight = tableRight;
  }
  
  public String getTableOwner() {
    return this.tableOwner;
  }
  
  public void setTableOwner(String tableOwner) {
    this.tableOwner = tableOwner;
  }
  
  public Date getTableDate() {
    return this.tableDate;
  }
  
  public void setTableDate(Date tableDate) {
    this.tableDate = tableDate;
  }
  
  public Set getField() {
    return this.field;
  }
  
  public void setField(Set field) {
    this.field = field;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof TTablePO))
      return false; 
    TTablePO castOther = (TTablePO)other;
    return (new EqualsBuilder()).append(getTableId(), castOther.getTableId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getTableId()).toHashCode();
  }
}
