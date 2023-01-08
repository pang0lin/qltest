package com.js.oa.hr.finance.action;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import org.apache.struts.action.ActionForm;

public class FTableActionForm extends ActionForm implements Serializable {
  private Long tableId;
  
  private String tableName;
  
  private String tableDesname;
  
  private Long tablePage;
  
  private Date tableDate = null;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  public FTableActionForm() {}
  
  public FTableActionForm(String tableName, String tableDesname, Timestamp tableDate, Long createdEmp, Long createdOrg) {
    this.tableName = tableName;
    this.tableDesname = tableDesname;
    this.tableDate = tableDate;
    this.createdEmp = createdEmp;
    this.createdOrg = createdOrg;
  }
  
  public Long getTableId() {
    return this.tableId;
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
  
  public Date getTableDate() {
    return this.tableDate;
  }
  
  public void setTableDate(Date tableDate) {
    this.tableDate = tableDate;
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
