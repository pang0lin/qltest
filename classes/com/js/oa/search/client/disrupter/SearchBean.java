package com.js.oa.search.client.disrupter;

public class SearchBean {
  private String methodName;
  
  private String id;
  
  private String tableName;
  
  public String getMethodName() {
    return this.methodName;
  }
  
  public void setMethodName(String methodName) {
    this.methodName = methodName;
  }
  
  public String getId() {
    return this.id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public String getTableName() {
    return this.tableName;
  }
  
  public void setTableName(String tableName) {
    this.tableName = tableName;
  }
}
