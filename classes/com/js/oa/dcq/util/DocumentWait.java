package com.js.oa.dcq.util;

public class DocumentWait {
  private long waitId;
  
  private String oid;
  
  private String title;
  
  private String accDate;
  
  private String remindContent;
  
  private String docType;
  
  public long getWaitId() {
    return this.waitId;
  }
  
  public void setWaitId(long waitId) {
    this.waitId = waitId;
  }
  
  public String getOid() {
    return this.oid;
  }
  
  public void setOid(String oid) {
    this.oid = oid;
  }
  
  public String getTitle() {
    return this.title;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  public String getAccDate() {
    return this.accDate;
  }
  
  public void setAccDate(String accDate) {
    this.accDate = accDate;
  }
  
  public String getRemindContent() {
    return this.remindContent;
  }
  
  public void setRemindContent(String remindContent) {
    this.remindContent = remindContent;
  }
  
  public String getDocType() {
    return this.docType;
  }
  
  public void setDocType(String docType) {
    this.docType = docType;
  }
}
