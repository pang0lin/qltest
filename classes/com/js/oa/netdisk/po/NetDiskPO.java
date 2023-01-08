package com.js.oa.netdisk.po;

import java.io.Serializable;
import java.util.Date;

public class NetDiskPO implements Serializable {
  private Long fileId;
  
  private String fileName;
  
  private String fileSaveName;
  
  private String fileExtName;
  
  private Long fileType;
  
  private String fileOwn;
  
  private Long fileOwnId;
  
  private Long fileFatherId;
  
  private String filePath;
  
  private Long fileIsShare;
  
  private String fileShareToName;
  
  private String fileShareToOrg;
  
  private String fileShareToGroup;
  
  private String fileShareToEmp;
  
  private Date fileCreatedTime = null;
  
  private Long fileSize;
  
  private String fileNote;
  
  private String fileSaveNameMin;
  
  private String fileOwenAccount;
  
  private String fileIdString;
  
  private String domainId;
  
  private String fileShareToNameWrite;
  
  private String fileShareToOrgWrite;
  
  private String fileShareToGroupWrite;
  
  private String fileShareToEmpWrite;
  
  private Date readTimeFrom = null;
  
  private Date readTimeTo = null;
  
  private Date writeTimeFrom = null;
  
  private Date writeTimeTo = null;
  
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
  
  public String getFileSaveName() {
    return this.fileSaveName;
  }
  
  public void setFileSaveName(String fileSaveName) {
    this.fileSaveName = fileSaveName;
  }
  
  public String getFileExtName() {
    return this.fileExtName;
  }
  
  public void setFileExtName(String fileExtName) {
    this.fileExtName = fileExtName;
  }
  
  public Long getFileType() {
    return this.fileType;
  }
  
  public void setFileType(Long fileType) {
    this.fileType = fileType;
  }
  
  public String getFileOwn() {
    return this.fileOwn;
  }
  
  public void setFileOwn(String fileOwn) {
    this.fileOwn = fileOwn;
  }
  
  public Long getFileOwnId() {
    return this.fileOwnId;
  }
  
  public void setFileOwnId(Long fileOwnId) {
    this.fileOwnId = fileOwnId;
  }
  
  public Long getFileFatherId() {
    return this.fileFatherId;
  }
  
  public void setFileFatherId(Long fileFatherId) {
    this.fileFatherId = fileFatherId;
  }
  
  public String getFilePath() {
    return this.filePath;
  }
  
  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }
  
  public Long getFileIsShare() {
    return this.fileIsShare;
  }
  
  public void setFileIsShare(Long fileIsShare) {
    this.fileIsShare = fileIsShare;
  }
  
  public String getFileShareToName() {
    return this.fileShareToName;
  }
  
  public void setFileShareToName(String fileShareToName) {
    this.fileShareToName = fileShareToName;
  }
  
  public String getFileShareToOrg() {
    return this.fileShareToOrg;
  }
  
  public void setFileShareToOrg(String fileShareToOrg) {
    this.fileShareToOrg = fileShareToOrg;
  }
  
  public String getFileShareToGroup() {
    return this.fileShareToGroup;
  }
  
  public void setFileShareToGroup(String fileShareToGroup) {
    this.fileShareToGroup = fileShareToGroup;
  }
  
  public String getFileShareToEmp() {
    return this.fileShareToEmp;
  }
  
  public void setFileShareToEmp(String fileShareToEmp) {
    this.fileShareToEmp = fileShareToEmp;
  }
  
  public Date getFileCreatedTime() {
    return this.fileCreatedTime;
  }
  
  public void setFileCreatedTime(Date fileCreatedTime) {
    this.fileCreatedTime = fileCreatedTime;
  }
  
  public Long getFileSize() {
    return this.fileSize;
  }
  
  public void setFileSize(Long fileSize) {
    this.fileSize = fileSize;
  }
  
  public String getFileNote() {
    return this.fileNote;
  }
  
  public void setFileNote(String fileNote) {
    this.fileNote = fileNote;
  }
  
  public String getFileSaveNameMin() {
    return this.fileSaveNameMin;
  }
  
  public void setFileSaveNameMin(String fileSaveNameMin) {
    this.fileSaveNameMin = fileSaveNameMin;
  }
  
  public String getFileOwenAccount() {
    return this.fileOwenAccount;
  }
  
  public void setFileOwenAccount(String fileOwenAccount) {
    this.fileOwenAccount = fileOwenAccount;
  }
  
  public String getFileIdString() {
    return this.fileIdString;
  }
  
  public void setFileIdString(String fileIdString) {
    this.fileIdString = fileIdString;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
  
  public String getFileShareToEmpWrite() {
    return this.fileShareToEmpWrite;
  }
  
  public void setFileShareToEmpWrite(String fileShareToEmpWrite) {
    this.fileShareToEmpWrite = fileShareToEmpWrite;
  }
  
  public String getFileShareToGroupWrite() {
    return this.fileShareToGroupWrite;
  }
  
  public void setFileShareToGroupWrite(String fileShareToGroupWrite) {
    this.fileShareToGroupWrite = fileShareToGroupWrite;
  }
  
  public String getFileShareToNameWrite() {
    return this.fileShareToNameWrite;
  }
  
  public void setFileShareToNameWrite(String fileShareToNameWrite) {
    this.fileShareToNameWrite = fileShareToNameWrite;
  }
  
  public String getFileShareToOrgWrite() {
    return this.fileShareToOrgWrite;
  }
  
  public void setFileShareToOrgWrite(String fileShareToOrgWrite) {
    this.fileShareToOrgWrite = fileShareToOrgWrite;
  }
  
  public Date getReadTimeFrom() {
    return this.readTimeFrom;
  }
  
  public void setReadTimeFrom(Date readTimeFrom) {
    this.readTimeFrom = readTimeFrom;
  }
  
  public Date getReadTimeTo() {
    return this.readTimeTo;
  }
  
  public void setReadTimeTo(Date readTimeTo) {
    this.readTimeTo = readTimeTo;
  }
  
  public Date getWriteTimeFrom() {
    return this.writeTimeFrom;
  }
  
  public void setWriteTimeFrom(Date writeTimeFrom) {
    this.writeTimeFrom = writeTimeFrom;
  }
  
  public Date getWriteTimeTo() {
    return this.writeTimeTo;
  }
  
  public void setWriteTimeTo(Date writeTimeTo) {
    this.writeTimeTo = writeTimeTo;
  }
}
