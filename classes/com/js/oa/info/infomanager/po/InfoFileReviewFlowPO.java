package com.js.oa.info.infomanager.po;

import java.io.Serializable;
import java.util.Date;

public class InfoFileReviewFlowPO implements Serializable {
  private Long Id;
  
  private String fileName;
  
  private Date reviewDate = null;
  
  private String version;
  
  private String result;
  
  private Long editId;
  
  private String editName;
  
  private String fileNum;
  
  public Long getId() {
    return this.Id;
  }
  
  public void setId(Long id) {
    this.Id = id;
  }
  
  public String getFileName() {
    return this.fileName;
  }
  
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
  
  public Date getReviewDate() {
    return this.reviewDate;
  }
  
  public void setReviewDate(Date reviewDate) {
    this.reviewDate = reviewDate;
  }
  
  public String getVersion() {
    return this.version;
  }
  
  public void setVersion(String version) {
    this.version = version;
  }
  
  public String getResult() {
    return this.result;
  }
  
  public void setResult(String result) {
    this.result = result;
  }
  
  public Long getEditId() {
    return this.editId;
  }
  
  public void setEditId(Long editId) {
    this.editId = editId;
  }
  
  public String getEditName() {
    return this.editName;
  }
  
  public void setEditName(String editName) {
    this.editName = editName;
  }
  
  public String getFileNum() {
    return this.fileNum;
  }
  
  public void setFileNum(String fileNum) {
    this.fileNum = fileNum;
  }
}
