package com.js.oa.hr.examination.po;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ExaminationStockPO implements Serializable {
  private Long examinationID;
  
  private Long examinationType;
  
  private String examinationStyle;
  
  private String subject;
  
  private String result;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  private Long domainId;
  
  private String sign;
  
  private Set examinationItem = null;
  
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
  
  public Long getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }
  
  public Long getExaminationID() {
    return this.examinationID;
  }
  
  public void setExaminationID(Long examinationID) {
    this.examinationID = examinationID;
  }
  
  public Set getExaminationItem() {
    return this.examinationItem;
  }
  
  public void setExaminationItem(Set examinationItem) {
    this.examinationItem = examinationItem;
  }
  
  public String getExaminationStyle() {
    return this.examinationStyle;
  }
  
  public void setExaminationStyle(String examinationStyle) {
    this.examinationStyle = examinationStyle;
  }
  
  public Long getExaminationType() {
    return this.examinationType;
  }
  
  public void setExaminationType(Long examinationType) {
    this.examinationType = examinationType;
  }
  
  public String getResult() {
    return this.result;
  }
  
  public void setResult(String result) {
    this.result = result;
  }
  
  public String getSign() {
    return this.sign;
  }
  
  public void setSign(String sign) {
    this.sign = sign;
  }
  
  public String getSubject() {
    return this.subject;
  }
  
  public void setSubject(String subject) {
    this.subject = subject;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getExaminationID())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof ExaminationStockPO))
      return false; 
    ExaminationStockPO castOther = (ExaminationStockPO)other;
    return (new EqualsBuilder())
      .append(getExaminationID(), castOther.getExaminationID())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getExaminationID())
      .toHashCode();
  }
}
