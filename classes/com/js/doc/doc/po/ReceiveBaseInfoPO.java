package com.js.doc.doc.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class ReceiveBaseInfoPO implements Serializable {
  private Long id;
  
  private String receiveFileType;
  
  private String pigeonholeType;
  
  private String decumentKind;
  
  private String receiveSecretLevel;
  
  private String urgencyLevel;
  
  private String keepTerm;
  
  private String seqType;
  
  private String receiveDropDownSelect1;
  
  private String receiveDropDownSelect2;
  
  public String getPigeonholeType() {
    return this.pigeonholeType;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public String getUrgencyLevel() {
    return this.urgencyLevel;
  }
  
  public String getReceiveFileType() {
    return this.receiveFileType;
  }
  
  public void setReceiveSecretLevel(String receiveSecretLevel) {
    this.receiveSecretLevel = receiveSecretLevel;
  }
  
  public void setPigeonholeType(String pigeonholeType) {
    this.pigeonholeType = pigeonholeType;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public void setUrgencyLevel(String urgencyLevel) {
    this.urgencyLevel = urgencyLevel;
  }
  
  public void setReceiveFileType(String receiveFileType) {
    this.receiveFileType = receiveFileType;
  }
  
  public void setKeepTerm(String keepTerm) {
    this.keepTerm = keepTerm;
  }
  
  public void setDecumentKind(String decumentKind) {
    this.decumentKind = decumentKind;
  }
  
  public void setSeqType(String seqType) {
    this.seqType = seqType;
  }
  
  public String getReceiveSecretLevel() {
    return this.receiveSecretLevel;
  }
  
  public String getKeepTerm() {
    return this.keepTerm;
  }
  
  public String getDecumentKind() {
    return this.decumentKind;
  }
  
  public String getSeqType() {
    return this.seqType;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof ReceiveBaseInfoPO))
      return false; 
    ReceiveBaseInfoPO castOther = (ReceiveBaseInfoPO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
  
  public String getReceiveDropDownSelect1() {
    return this.receiveDropDownSelect1;
  }
  
  public void setReceiveDropDownSelect1(String receiveDropDownSelect1) {
    this.receiveDropDownSelect1 = receiveDropDownSelect1;
  }
  
  public String getReceiveDropDownSelect2() {
    return this.receiveDropDownSelect2;
  }
  
  public void setReceiveDropDownSelect2(String receiveDropDownSelect2) {
    this.receiveDropDownSelect2 = receiveDropDownSelect2;
  }
}
