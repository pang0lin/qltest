package com.js.oa.info.infomanager.po;

import java.io.Serializable;
import java.util.Date;

public class InfoProFileReviewPO implements Serializable {
  private Long ReviewId;
  
  private Long fileId;
  
  private String fileName;
  
  private Date reviewDate = null;
  
  private String version;
  
  private String result;
  
  private Long editId;
  
  private String editName;
  
  private String fileNum;
  
  private String date;
  
  public Long getReviewId() {
    return this.ReviewId;
  }
  
  public void setReviewId(Long reviewId) {
    this.ReviewId = reviewId;
  }
  
  public Long getFileId() {
    return this.fileId;
  }
  
  public void setFileId(Long fileId) {
    this.fileId = fileId;
  }
  
  public String getFileName() {
    return this.fileName;
  }
  
  public void setFileName(String fileName) {
    this.fileName = fileName;
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
  
  public String getFileNum() {
    return this.fileNum;
  }
  
  public void setFileNum(String fileNum) {
    this.fileNum = fileNum;
  }
  
  public String getDate() {
    return this.date;
  }
  
  public void setDate(String date) {
    this.date = date;
  }
}
