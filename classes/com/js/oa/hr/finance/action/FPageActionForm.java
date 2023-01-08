package com.js.oa.hr.finance.action;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import org.apache.struts.action.ActionForm;

public class FPageActionForm extends ActionForm implements Serializable {
  private Long pageId;
  
  private String pageName;
  
  private String pageContent;
  
  private Long pageIsUse;
  
  private String pageFile;
  
  private Long tableId;
  
  private String tableDesname;
  
  private Date createDate = null;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  public FPageActionForm() {}
  
  public FPageActionForm(String pageName, String pageContent, Long pageIsUse, String pageFile, Long tableId, String tableDesname, Timestamp createDate, Long createdEmp, Long createdOrg) {
    this.pageName = pageName;
    this.pageContent = pageContent;
    this.pageIsUse = pageIsUse;
    this.pageFile = pageFile;
    this.tableId = tableId;
    this.tableDesname = tableDesname;
    this.createDate = createDate;
    this.createdEmp = createdEmp;
    this.createdOrg = createdOrg;
  }
  
  public Long getPageId() {
    return this.pageId;
  }
  
  public void setPageId(Long pageId) {
    this.pageId = pageId;
  }
  
  public String getPageName() {
    return this.pageName;
  }
  
  public void setPageName(String pageName) {
    this.pageName = pageName;
  }
  
  public String getPageContent() {
    return this.pageContent;
  }
  
  public void setPageContent(String pageContent) {
    this.pageContent = pageContent;
  }
  
  public Long getPageIsUse() {
    return this.pageIsUse;
  }
  
  public void setPageIsUse(Long pageIsUse) {
    this.pageIsUse = pageIsUse;
  }
  
  public String getPageFile() {
    return this.pageFile;
  }
  
  public void setPageFile(String pageFile) {
    this.pageFile = pageFile;
  }
  
  public Long getTableId() {
    return this.tableId;
  }
  
  public void setTableId(Long tableId) {
    this.tableId = tableId;
  }
  
  public String getTableDesname() {
    return this.tableDesname;
  }
  
  public void setTableDesname(String tableDesname) {
    this.tableDesname = tableDesname;
  }
  
  public Date getCreateDate() {
    return this.createDate;
  }
  
  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
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
