package com.js.oa.hr.subsidiarywork.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AnswerSheetOptionPO implements Serializable {
  private Long answerSheetOptionId;
  
  private Long themeOptionId;
  
  private AnswerSheetContentPO answerSheetContent;
  
  private String domainId;
  
  public AnswerSheetOptionPO(Long answerSheetOptionId, AnswerSheetContentPO answerSheetContent) {
    this.themeOptionId = this.themeOptionId;
    this.answerSheetContent = answerSheetContent;
  }
  
  public AnswerSheetContentPO getAnswerSheetContent() {
    return this.answerSheetContent;
  }
  
  public void setAnswerSheetContent(AnswerSheetContentPO answerSheetContent) {
    this.answerSheetContent = answerSheetContent;
  }
  
  public Long getAnswerSheetOptionId() {
    return this.answerSheetOptionId;
  }
  
  public void setAnswerSheetOptionId(Long answerSheetOptionId) {
    this.answerSheetOptionId = answerSheetOptionId;
  }
  
  public Long getThemeOptionId() {
    return this.themeOptionId;
  }
  
  public void setThemeOptionId(Long themeOptionId) {
    this.themeOptionId = themeOptionId;
  }
  
  public AnswerSheetOptionPO() {}
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("answerSheetOptionId", getAnswerSheetOptionId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof AnswerSheetOptionPO))
      return false; 
    AnswerSheetOptionPO castOther = (AnswerSheetOptionPO)other;
    return (new EqualsBuilder())
      .append(getAnswerSheetOptionId(), castOther.getAnswerSheetOptionId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getAnswerSheetOptionId())
      .toHashCode();
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
}
