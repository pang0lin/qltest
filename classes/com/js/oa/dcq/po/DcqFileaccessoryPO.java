package com.js.oa.dcq.po;

import java.io.Serializable;

public class DcqFileaccessoryPO implements Serializable {
  private Long ID;
  
  private String fileID;
  
  private String fileName;
  
  private String businessType;
  
  private String businessID;
  
  private String filepath;
  
  private String fileSaveName;
  
  private String bz;
  
  public Long getID() {
    return this.ID;
  }
  
  public void setID(Long iD) {
    this.ID = iD;
  }
  
  public String getFileID() {
    return this.fileID;
  }
  
  public void setFileID(String fileID) {
    this.fileID = fileID;
  }
  
  public String getFileName() {
    return this.fileName;
  }
  
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
  
  public String getBusinessType() {
    return this.businessType;
  }
  
  public void setBusinessType(String businessType) {
    this.businessType = businessType;
  }
  
  public String getFilepath() {
    return this.filepath;
  }
  
  public void setFilepath(String filepath) {
    this.filepath = filepath;
  }
  
  public String getFileSaveName() {
    return this.fileSaveName;
  }
  
  public void setFileSaveName(String fileSaveName) {
    this.fileSaveName = fileSaveName;
  }
  
  public String getBz() {
    return this.bz;
  }
  
  public void setBz(String bz) {
    this.bz = bz;
  }
  
  public String getBusinessID() {
    return this.businessID;
  }
  
  public void setBusinessID(String businessID) {
    this.businessID = businessID;
  }
}
