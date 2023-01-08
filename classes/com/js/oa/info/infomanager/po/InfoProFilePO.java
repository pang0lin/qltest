package com.js.oa.info.infomanager.po;

import java.io.Serializable;
import java.util.Date;

public class InfoProFilePO implements Serializable {
  private Long fileId;
  
  private Long filePreId;
  
  private String proFile;
  
  private String fileName;
  
  private String fileNum;
  
  private String character;
  
  private String version;
  
  private Date fileDate = null;
  
  private Date reviewDate = null;
  
  private String department;
  
  private String author;
  
  private String viewMan;
  
  private String isNew;
  
  private String fileViewName;
  
  private String fileAppendName;
  
  private String filePath;
  
  private String appendPath;
  
  private String editDate;
  
  private String reviewRecordId;
  
  private String reviseRecordId;
  
  private String isAppend;
  
  public Long getFileId() {
    return this.fileId;
  }
  
  public void setFileId(Long fileId) {
    this.fileId = fileId;
  }
  
  public String getProFile() {
    return this.proFile;
  }
  
  public void setProFile(String proFile) {
    this.proFile = proFile;
  }
  
  public String getFileName() {
    return this.fileName;
  }
  
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
  
  public String getFileNum() {
    return this.fileNum;
  }
  
  public void setFileNum(String fileNum) {
    this.fileNum = fileNum;
  }
  
  public String getCharacter() {
    return this.character;
  }
  
  public void setCharacter(String character) {
    this.character = character;
  }
  
  public Date getFileDate() {
    return this.fileDate;
  }
  
  public void setFileDate(Date fileDate) {
    this.fileDate = fileDate;
  }
  
  public Date getReviewDate() {
    return this.reviewDate;
  }
  
  public void setReviewDate(Date reviewDate) {
    this.reviewDate = reviewDate;
  }
  
  public String getDepartment() {
    return this.department;
  }
  
  public void setDepartment(String department) {
    this.department = department;
  }
  
  public String getAuthor() {
    return this.author;
  }
  
  public void setAuthor(String author) {
    this.author = author;
  }
  
  public String getViewMan() {
    return this.viewMan;
  }
  
  public void setViewMan(String viewMan) {
    this.viewMan = viewMan;
  }
  
  public Long getFilePreId() {
    return this.filePreId;
  }
  
  public void setFilePreId(Long filePreId) {
    this.filePreId = filePreId;
  }
  
  public String getVersion() {
    return this.version;
  }
  
  public void setVersion(String version) {
    this.version = version;
  }
  
  public String getIsNew() {
    return this.isNew;
  }
  
  public void setIsNew(String isNew) {
    this.isNew = isNew;
  }
  
  public String getFilePath() {
    return this.filePath;
  }
  
  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }
  
  public String getAppendPath() {
    return this.appendPath;
  }
  
  public void setAppendPath(String appendPath) {
    this.appendPath = appendPath;
  }
  
  public String getFileViewName() {
    return this.fileViewName;
  }
  
  public void setFileViewName(String fileViewName) {
    this.fileViewName = fileViewName;
  }
  
  public String getFileAppendName() {
    return this.fileAppendName;
  }
  
  public void setFileAppendName(String fileAppendName) {
    this.fileAppendName = fileAppendName;
  }
  
  public String getReviewRecordId() {
    return this.reviewRecordId;
  }
  
  public void setReviewRecordId(String reviewRecordId) {
    this.reviewRecordId = reviewRecordId;
  }
  
  public String getReviseRecordId() {
    return this.reviseRecordId;
  }
  
  public void setReviseRecordId(String reviseRecordId) {
    this.reviseRecordId = reviseRecordId;
  }
  
  public String getIsAppend() {
    return this.isAppend;
  }
  
  public void setIsAppend(String isAppend) {
    this.isAppend = isAppend;
  }
  
  public String getEditDate() {
    return this.editDate;
  }
  
  public void setEditDate(String editDate) {
    this.editDate = editDate;
  }
}
