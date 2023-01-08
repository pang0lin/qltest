package com.js.doc.doc.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class SendDocumentSeqPO implements Serializable {
  private Long id;
  
  private Integer seqBitNum;
  
  private Integer seqFileType;
  
  private String seqFormat;
  
  private Integer seqInitValue;
  
  private Integer seqIsUse;
  
  private Integer seqIsYear;
  
  private String seqMode;
  
  private String seqName;
  
  private String seqUnitName;
  
  private Integer seqfig;
  
  private Integer seqIsWord;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  private Long corpId;
  
  public boolean equals(Object other) {
    if (!(other instanceof SendDocumentSeqPO))
      return false; 
    SendDocumentSeqPO castOther = (SendDocumentSeqPO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId()).isEquals();
  }
  
  public Long getId() {
    return this.id;
  }
  
  public String getSeqFormat() {
    return this.seqFormat;
  }
  
  public String getSeqMode() {
    return this.seqMode;
  }
  
  public String getSeqName() {
    return this.seqName;
  }
  
  public String getSeqUnitName() {
    return this.seqUnitName;
  }
  
  public Integer getSeqIsUse() {
    return this.seqIsUse;
  }
  
  public Integer getSeqInitValue() {
    return this.seqInitValue;
  }
  
  public Integer getSeqIsYear() {
    return this.seqIsYear;
  }
  
  public Integer getSeqBitNum() {
    return this.seqBitNum;
  }
  
  public Integer getSeqFileType() {
    return this.seqFileType;
  }
  
  public Integer getSeqfig() {
    return this.seqfig;
  }
  
  public Integer getSeqIsWord() {
    return this.seqIsWord;
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public void setSeqFormat(String seqFormat) {
    this.seqFormat = seqFormat;
  }
  
  public void setSeqMode(String seqMode) {
    this.seqMode = seqMode;
  }
  
  public void setSeqName(String seqName) {
    this.seqName = seqName;
  }
  
  public void setSeqUnitName(String seqUnitName) {
    this.seqUnitName = seqUnitName;
  }
  
  public void setSeqIsUse(Integer seqIsUse) {
    this.seqIsUse = seqIsUse;
  }
  
  public void setSeqInitValue(Integer seqInitValue) {
    this.seqInitValue = seqInitValue;
  }
  
  public void setSeqIsYear(Integer seqIsYear) {
    this.seqIsYear = seqIsYear;
  }
  
  public void setSeqBitNum(Integer seqBitNum) {
    this.seqBitNum = seqBitNum;
  }
  
  public void setSeqFileType(Integer seqFileType) {
    this.seqFileType = seqFileType;
  }
  
  public void setSeqfig(Integer seqfig) {
    this.seqfig = seqfig;
  }
  
  public void setSeqIsWord(Integer seqIsWord) {
    this.seqIsWord = seqIsWord;
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
}
