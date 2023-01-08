package com.js.oa.archives.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ArchivesWaitPigeonholePO implements Serializable {
  private Long waitPigeonholeId;
  
  private Long pigeonholeFileId;
  
  private String pigeonholeCaption;
  
  private String pigeonholeTypeName;
  
  private String pigeonholeFileName;
  
  private String pigeonholePromulgator;
  
  private Date pigeonholeDate = null;
  
  private Integer pigeonholeState;
  
  private Date pigeonholeManageDate = null;
  
  private String createdEmp;
  
  private String createdOrg;
  
  private String domainId;
  
  private Long fileID;
  
  public ArchivesWaitPigeonholePO(Long pigeonholeFileId, String pigeonholeCaption, String pigeonholeTypeName, String pigeonholeFileName, String pigeonholePromulgator, Date pigeonholeDate, Integer pigeonholeState, Date pigeonholeManageDate, String domainId) {
    this.pigeonholeFileId = pigeonholeFileId;
    this.pigeonholeCaption = pigeonholeCaption;
    this.pigeonholeTypeName = pigeonholeTypeName;
    this.pigeonholeFileName = pigeonholeFileName;
    this.pigeonholePromulgator = pigeonholePromulgator;
    this.pigeonholeDate = pigeonholeDate;
    this.pigeonholeState = pigeonholeState;
    this.pigeonholeManageDate = pigeonholeManageDate;
    this.domainId = domainId;
  }
  
  public ArchivesWaitPigeonholePO() {}
  
  public String getPigeonholeCaption() {
    return this.pigeonholeCaption;
  }
  
  public void setPigeonholeCaption(String pigeonholeCaption) {
    this.pigeonholeCaption = pigeonholeCaption;
  }
  
  public Date getPigeonholeDate() {
    return this.pigeonholeDate;
  }
  
  public void setPigeonholeDate(Date pigeonholeDate) {
    this.pigeonholeDate = pigeonholeDate;
  }
  
  public Long getPigeonholeFileId() {
    return this.pigeonholeFileId;
  }
  
  public void setPigeonholeFileId(Long pigeonholeFileId) {
    this.pigeonholeFileId = pigeonholeFileId;
  }
  
  public String getPigeonholeFileName() {
    return this.pigeonholeFileName;
  }
  
  public void setPigeonholeFileName(String pigeonholeFileName) {
    this.pigeonholeFileName = pigeonholeFileName;
  }
  
  public Date getPigeonholeManageDate() {
    return this.pigeonholeManageDate;
  }
  
  public void setPigeonholeManageDate(Date pigeonholeManageDate) {
    this.pigeonholeManageDate = pigeonholeManageDate;
  }
  
  public String getPigeonholePromulgator() {
    return this.pigeonholePromulgator;
  }
  
  public void setPigeonholePromulgator(String pigeonholePromulgator) {
    this.pigeonholePromulgator = pigeonholePromulgator;
  }
  
  public Integer getPigeonholeState() {
    return this.pigeonholeState;
  }
  
  public void setPigeonholeState(Integer pigeonholeState) {
    this.pigeonholeState = pigeonholeState;
  }
  
  public String getPigeonholeTypeName() {
    return this.pigeonholeTypeName;
  }
  
  public void setPigeonholeTypeName(String pigeonholeTypeName) {
    this.pigeonholeTypeName = pigeonholeTypeName;
  }
  
  public Long getWaitPigeonholeId() {
    return this.waitPigeonholeId;
  }
  
  public void setWaitPigeonholeId(Long waitPigeonholeId) {
    this.waitPigeonholeId = waitPigeonholeId;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("waitPigeonholeId", getWaitPigeonholeId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof ArchivesWaitPigeonholePO))
      return false; 
    ArchivesWaitPigeonholePO castOther = (ArchivesWaitPigeonholePO)other;
    return (new EqualsBuilder())
      .append(getWaitPigeonholeId(), 
        castOther.getWaitPigeonholeId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getWaitPigeonholeId())
      .toHashCode();
  }
  
  public String getCreatedEmp() {
    return this.createdEmp;
  }
  
  public void setCreatedEmp(String createdEmp) {
    this.createdEmp = createdEmp;
  }
  
  public String getCreatedOrg() {
    return this.createdOrg;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setCreatedOrg(String createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
  
  public Long getFileID() {
    return this.fileID;
  }
  
  public void setFileID(Long fileID) {
    this.fileID = fileID;
  }
}
