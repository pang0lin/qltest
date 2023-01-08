package com.js.doc.doc.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class GovFileReadPO implements Serializable {
  private Long id;
  
  private String fileType;
  
  private String code;
  
  private String title;
  
  private String sendLeader;
  
  private String leaderComment;
  
  private String sendOrgComment;
  
  private String dealResult;
  
  private String linkMan;
  
  private String linkPhone;
  
  private Date sendTime = null;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  private Set accessory = null;
  
  private String content;
  
  private Integer numCode;
  
  private Integer numCodeYear;
  
  private String note;
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getFileType() {
    return this.fileType;
  }
  
  public void setFileType(String fileType) {
    this.fileType = fileType;
  }
  
  public String getCode() {
    return this.code;
  }
  
  public void setCode(String code) {
    this.code = code;
  }
  
  public String getTitle() {
    return this.title;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  public String getSendLeader() {
    return this.sendLeader;
  }
  
  public void setSendLeader(String sendLeader) {
    this.sendLeader = sendLeader;
  }
  
  public String getLeaderComment() {
    return this.leaderComment;
  }
  
  public void setLeaderComment(String leaderComment) {
    this.leaderComment = leaderComment;
  }
  
  public String getSendOrgComment() {
    return this.sendOrgComment;
  }
  
  public void setSendOrgComment(String sendOrgComment) {
    this.sendOrgComment = sendOrgComment;
  }
  
  public String getDealResult() {
    return this.dealResult;
  }
  
  public void setDealResult(String dealResult) {
    this.dealResult = dealResult;
  }
  
  public String getLinkMan() {
    return this.linkMan;
  }
  
  public void setLinkMan(String linkMan) {
    this.linkMan = linkMan;
  }
  
  public String getLinkPhone() {
    return this.linkPhone;
  }
  
  public void setLinkPhone(String linkPhone) {
    this.linkPhone = linkPhone;
  }
  
  public Date getSendTime() {
    return this.sendTime;
  }
  
  public void setSendTime(Date sendTime) {
    this.sendTime = sendTime;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof GovFileReadPO))
      return false; 
    GovFileReadPO castOther = (GovFileReadPO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
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
  
  public Set getAccessory() {
    return this.accessory;
  }
  
  public void setAccessory(Set accessory) {
    this.accessory = accessory;
  }
  
  public String getContent() {
    return this.content;
  }
  
  public void setContent(String content) {
    this.content = content;
  }
  
  public Integer getNumCode() {
    return this.numCode;
  }
  
  public void setNumCode(Integer numCode) {
    this.numCode = numCode;
  }
  
  public Integer getNumCodeYear() {
    return this.numCodeYear;
  }
  
  public void setNumCodeYear(Integer numCodeYear) {
    this.numCodeYear = numCodeYear;
  }
  
  public String getNote() {
    return this.note;
  }
  
  public void setNote(String note) {
    this.note = note;
  }
}
