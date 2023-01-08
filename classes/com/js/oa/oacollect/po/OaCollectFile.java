package com.js.oa.oacollect.po;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class OaCollectFile implements Serializable {
  private Long id;
  
  private Long collectId;
  
  private String collectTitle;
  
  private String fileName;
  
  private String fileNameSys;
  
  private String filePath;
  
  private Long collectEmpId;
  
  private String collectEmpName;
  
  private Long collectOrgId;
  
  private String collectOrgName;
  
  private Long collectEmpStatus;
  
  private Long remindCount;
  
  private Date createDate = null;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  private String fileType;
  
  private Float fileSize;
  
  public OaCollectFile() {}
  
  public OaCollectFile(Long collectId, String collectTitle, String fileName, String filePath, Long collectEmpId, String collectEmpName, Long collectOrgId, String collectOrgName, Long collectEmpStatus, Long remindCount, Timestamp createDate, Long createdEmp, Long createdOrg) {
    this.collectId = collectId;
    this.collectTitle = collectTitle;
    this.fileName = fileName;
    this.filePath = filePath;
    this.collectEmpId = collectEmpId;
    this.collectEmpName = collectEmpName;
    this.collectOrgId = collectOrgId;
    this.collectOrgName = collectOrgName;
    this.collectEmpStatus = collectEmpStatus;
    this.remindCount = remindCount;
    this.createDate = createDate;
    this.createdEmp = createdEmp;
    this.createdOrg = createdOrg;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getFileNameSys() {
    return this.fileNameSys;
  }
  
  public void setFileNameSys(String fileNameSys) {
    this.fileNameSys = fileNameSys;
  }
  
  public String getFileType() {
    return this.fileType;
  }
  
  public void setFileType(String fileType) {
    this.fileType = fileType;
  }
  
  public Long getCollectId() {
    return this.collectId;
  }
  
  public void setCollectId(Long collectId) {
    this.collectId = collectId;
  }
  
  public String getCollectTitle() {
    return this.collectTitle;
  }
  
  public void setCollectTitle(String collectTitle) {
    this.collectTitle = collectTitle;
  }
  
  public String getFileName() {
    return this.fileName;
  }
  
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
  
  public String getFilePath() {
    return this.filePath;
  }
  
  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }
  
  public Long getCollectEmpId() {
    return this.collectEmpId;
  }
  
  public void setCollectEmpId(Long collectEmpId) {
    this.collectEmpId = collectEmpId;
  }
  
  public String getCollectEmpName() {
    return this.collectEmpName;
  }
  
  public void setCollectEmpName(String collectEmpName) {
    this.collectEmpName = collectEmpName;
  }
  
  public Long getCollectOrgId() {
    return this.collectOrgId;
  }
  
  public void setCollectOrgId(Long collectOrgId) {
    this.collectOrgId = collectOrgId;
  }
  
  public String getCollectOrgName() {
    return this.collectOrgName;
  }
  
  public void setCollectOrgName(String collectOrgName) {
    this.collectOrgName = collectOrgName;
  }
  
  public Long getCollectEmpStatus() {
    return this.collectEmpStatus;
  }
  
  public void setCollectEmpStatus(Long collectEmpStatus) {
    this.collectEmpStatus = collectEmpStatus;
  }
  
  public Long getRemindCount() {
    return this.remindCount;
  }
  
  public void setRemindCount(Long remindCount) {
    this.remindCount = remindCount;
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
  
  public Float getFileSize() {
    return this.fileSize;
  }
  
  public void setFileSize(Float fileSize) {
    this.fileSize = fileSize;
  }
}
