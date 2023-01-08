package com.js.util.util;

import java.util.List;

public class TranDataBaseConfig {
  public String fromBaseName;
  
  public String toBaseName;
  
  public String toBaseType;
  
  public String fromTable;
  
  public String toTable;
  
  public String where;
  
  public String orderBy;
  
  public List<String[]> fieldList;
  
  public String use;
  
  public String tranTime;
  
  public String getTranTime() {
    return this.tranTime;
  }
  
  public void setTranTime(String tranTime) {
    this.tranTime = tranTime;
  }
  
  public String getUse() {
    return this.use;
  }
  
  public void setUse(String use) {
    this.use = use;
  }
  
  public String getFromBaseName() {
    return this.fromBaseName;
  }
  
  public void setFromBaseName(String fromBaseName) {
    this.fromBaseName = fromBaseName;
  }
  
  public String getToBaseName() {
    return this.toBaseName;
  }
  
  public void setToBaseName(String toBaseName) {
    this.toBaseName = toBaseName;
  }
  
  public String getToBaseType() {
    return this.toBaseType;
  }
  
  public void setToBaseType(String toBaseType) {
    this.toBaseType = toBaseType;
  }
  
  public String getFromTable() {
    return this.fromTable;
  }
  
  public void setFromTable(String fromTable) {
    this.fromTable = fromTable;
  }
  
  public String getToTable() {
    return this.toTable;
  }
  
  public void setToTable(String toTable) {
    this.toTable = toTable;
  }
  
  public String getWhere() {
    return this.where;
  }
  
  public void setWhere(String where) {
    this.where = where;
  }
  
  public String getOrderBy() {
    return this.orderBy;
  }
  
  public void setOrderBy(String orderBy) {
    this.orderBy = orderBy;
  }
  
  public List<String[]> getFieldList() {
    return this.fieldList;
  }
  
  public void setFieldList(List<String[]> fieldList) {
    this.fieldList = fieldList;
  }
}
