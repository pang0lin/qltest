package com.js.cooperate.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AttachPO implements Serializable {
  private Long id;
  
  private Long conId;
  
  private String fileName;
  
  private String saveName;
  
  private Integer fileType;
  
  private Long bodyId;
  
  public Long getBodyId() {
    return this.bodyId;
  }
  
  public void setBodyId(Long bodyId) {
    this.bodyId = bodyId;
  }
  
  public Long getConId() {
    return this.conId;
  }
  
  public void setConId(Long conId) {
    this.conId = conId;
  }
  
  public String getFileName() {
    return this.fileName;
  }
  
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
  
  public String getSaveName() {
    return this.saveName;
  }
  
  public void setSaveName(String saveName) {
    this.saveName = saveName;
  }
  
  public Integer getFileType() {
    return this.fileType;
  }
  
  public void setFileType(Integer fileType) {
    this.fileType = fileType;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
}
