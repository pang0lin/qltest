package com.js.doc.doc.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class SendDocumentNumPO implements Serializable {
  private Integer bitNum;
  
  private String fileWord;
  
  private Long id;
  
  private Integer initValue;
  
  private String keyValue;
  
  private String numFormat;
  
  private Integer numIsYear;
  
  private String numMode;
  
  private String numName;
  
  private String numNote;
  
  private String numType;
  
  private Integer numfig;
  
  private int oldYear;
  
  private Integer numIsWord;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  private Long corpId;
  
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
  
  public boolean equals(Object other) {
    if (!(other instanceof SendDocumentNumPO))
      return false; 
    SendDocumentNumPO castOther = (SendDocumentNumPO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId()).isEquals();
  }
  
  public Integer getBitNum() {
    return this.bitNum;
  }
  
  public String getFileWord() {
    return this.fileWord;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public Integer getInitValue() {
    return this.initValue;
  }
  
  public String getKeyValue() {
    return this.keyValue;
  }
  
  public String getNumFormat() {
    return this.numFormat;
  }
  
  public Integer getNumIsYear() {
    return this.numIsYear;
  }
  
  public String getNumMode() {
    return this.numMode;
  }
  
  public String getNumName() {
    return this.numName;
  }
  
  public String getNumNote() {
    return this.numNote;
  }
  
  public String getNumType() {
    return this.numType;
  }
  
  public Integer getNumfig() {
    return this.numfig;
  }
  
  public int getOldYear() {
    return this.oldYear;
  }
  
  public Integer getNumIsWord() {
    return this.numIsWord;
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
  
  public void setBitNum(Integer bitNum) {
    this.bitNum = bitNum;
  }
  
  public void setFileWord(String fileWord) {
    this.fileWord = fileWord;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public void setInitValue(Integer initValue) {
    this.initValue = initValue;
  }
  
  public void setKeyValue(String keyValue) {
    this.keyValue = keyValue;
  }
  
  public void setNumFormat(String numFormat) {
    this.numFormat = numFormat;
  }
  
  public void setNumIsYear(Integer numIsYear) {
    this.numIsYear = numIsYear;
  }
  
  public void setNumMode(String numMode) {
    this.numMode = numMode;
  }
  
  public void setNumName(String numName) {
    this.numName = numName;
  }
  
  public void setNumNote(String numNote) {
    this.numNote = numNote;
  }
  
  public void setNumType(String numType) {
    this.numType = numType;
  }
  
  public void setNumfig(Integer numfig) {
    this.numfig = numfig;
  }
  
  public void setOldYear(int oldYear) {
    this.oldYear = oldYear;
  }
  
  public void setNumIsWord(Integer numIsWord) {
    this.numIsWord = numIsWord;
  }
}
