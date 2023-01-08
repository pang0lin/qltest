package com.js.oa.hr.finance.po;

import java.io.Serializable;
import java.util.Date;

public class FTable implements Serializable {
  private Long tableId;
  
  private String tableName;
  
  private String tableDesname;
  
  private Long tablePage;
  
  private Date createdDate = null;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  public FTable() {}
  
  public FTable(String tableName, String tableDesname, Date createdDate, Long createdEmp, Long createdOrg) {
    this.tableName = tableName;
    this.tableDesname = tableDesname;
    this.createdDate = createdDate;
    this.createdEmp = createdEmp;
    this.createdOrg = createdOrg;
  }
  
  public Long getTableId() {
    return this.tableId;
  }
  
  public Date getCreatedDate() {
    return this.createdDate;
  }
  
  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }
  
  public Long getTablePage() {
    return this.tablePage;
  }
  
  public void setTablePage(Long tablePage) {
    this.tablePage = tablePage;
  }
  
  public void setTableId(Long tableId) {
    this.tableId = tableId;
  }
  
  public String getTableName() {
    return this.tableName;
  }
  
  public void setTableName(String tableName) {
    this.tableName = tableName;
  }
  
  public String getTableDesname() {
    return this.tableDesname;
  }
  
  public void setTableDesname(String tableDesname) {
    this.tableDesname = tableDesname;
  }
  
  public Long getCreatedEmp() {
    return this.createdEmp;
  }
  
  public void setCreatedEmp(Long createdEmp) {
    this.createdEmp = createdEmp;
  }
  
  public Long getCreatedOrg() {
    return this.createdOrg;
  }
  
  public void setCreatedOrg(Long createdOrg) {
    this.createdOrg = createdOrg;
  }
}
