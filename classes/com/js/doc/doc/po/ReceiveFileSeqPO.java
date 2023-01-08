package com.js.doc.doc.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class ReceiveFileSeqPO implements Serializable {
  private Long id;
  
  private String seqNameR;
  
  private String seqProceNameR;
  
  private Long seqProceId;
  
  private Long seqBitNumR;
  
  private Long seqIsYearR;
  
  private Long seqInitValueR;
  
  private String seqFormatR;
  
  private String seqModeR;
  
  private Long seqfigR;
  
  private Long seqIsName;
  
  private String receiveUser;
  
  private String receiveOrg;
  
  private String receiveGroup;
  
  private String receiveScopeName;
  
  private String receiveScopeId;
  
  private String seqType;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  private Long corpId;
  
  private String repeatOfYear;
  
  private String seqCurYear;
  
  public String getRepeatOfYear() {
    return this.repeatOfYear;
  }
  
  public void setRepeatOfYear(String repeatOfYear) {
    this.repeatOfYear = repeatOfYear;
  }
  
  public String getSeqCurYear() {
    return this.seqCurYear;
  }
  
  public void setSeqCurYear(String seqCurYear) {
    this.seqCurYear = seqCurYear;
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
  
  public Long getCorpId() {
    return this.corpId;
  }
  
  public void setCorpId(Long corpId) {
    this.corpId = corpId;
  }
  
  public String getSeqModeR() {
    return this.seqModeR;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public Long getSeqIsYearR() {
    return this.seqIsYearR;
  }
  
  public Long getSeqfigR() {
    return this.seqfigR;
  }
  
  public String getSeqProceNameR() {
    return this.seqProceNameR;
  }
  
  public String getSeqNameR() {
    return this.seqNameR;
  }
  
  public Long getSeqProceId() {
    return this.seqProceId;
  }
  
  public Long getSeqInitValueR() {
    return this.seqInitValueR;
  }
  
  public String getSeqFormatR() {
    return this.seqFormatR;
  }
  
  public void setSeqBitNumR(Long seqBitNumR) {
    this.seqBitNumR = seqBitNumR;
  }
  
  public void setSeqModeR(String seqModeR) {
    this.seqModeR = seqModeR;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public void setSeqIsYearR(Long seqIsYearR) {
    this.seqIsYearR = seqIsYearR;
  }
  
  public void setSeqfigR(Long seqfigR) {
    this.seqfigR = seqfigR;
  }
  
  public void setSeqProceNameR(String seqProceNameR) {
    this.seqProceNameR = seqProceNameR;
  }
  
  public void setSeqNameR(String seqNameR) {
    this.seqNameR = seqNameR;
  }
  
  public void setSeqProceId(Long seqProceId) {
    this.seqProceId = seqProceId;
  }
  
  public void setSeqInitValueR(Long seqInitValueR) {
    this.seqInitValueR = seqInitValueR;
  }
  
  public void setSeqFormatR(String seqFormatR) {
    this.seqFormatR = seqFormatR;
  }
  
  public void setSeqIsName(Long seqIsName) {
    this.seqIsName = seqIsName;
  }
  
  public void setReceiveUser(String receiveUser) {
    this.receiveUser = receiveUser;
  }
  
  public void setReceiveScopeName(String receiveScopeName) {
    this.receiveScopeName = receiveScopeName;
  }
  
  public void setReceiveScopeId(String receiveScopeId) {
    this.receiveScopeId = receiveScopeId;
  }
  
  public void setReceiveOrg(String receiveOrg) {
    this.receiveOrg = receiveOrg;
  }
  
  public void setReceiveGroup(String receiveGroup) {
    this.receiveGroup = receiveGroup;
  }
  
  public void setSeqType(String seqType) {
    this.seqType = seqType;
  }
  
  public Long getSeqBitNumR() {
    return this.seqBitNumR;
  }
  
  public Long getSeqIsName() {
    return this.seqIsName;
  }
  
  public String getReceiveUser() {
    return this.receiveUser;
  }
  
  public String getReceiveScopeName() {
    return this.receiveScopeName;
  }
  
  public String getReceiveScopeId() {
    return this.receiveScopeId;
  }
  
  public String getReceiveOrg() {
    return this.receiveOrg;
  }
  
  public String getReceiveGroup() {
    return this.receiveGroup;
  }
  
  public String getSeqType() {
    return this.seqType;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof ReceiveFileSeqPO))
      return false; 
    ReceiveFileSeqPO castOther = (ReceiveFileSeqPO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
}
