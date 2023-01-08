package com.js.oa.hr.subsidiarywork.po;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AnswerSheetContentPO implements Serializable {
  private Long contentId;
  
  private String content;
  
  private Long themeId;
  
  private Float score;
  
  private AnswerSheetPO answerSheet;
  
  private Set answerSheetOption = null;
  
  private String domainId;
  
  public AnswerSheetContentPO(String content, Long themeId, Float score, AnswerSheetPO answerSheet, Set answerSheetOption) {
    this.content = content;
    this.themeId = themeId;
    this.score = score;
    this.answerSheet = answerSheet;
    this.answerSheetOption = answerSheetOption;
  }
  
  public AnswerSheetPO getAnswerSheet() {
    return this.answerSheet;
  }
  
  public void setAnswerSheet(AnswerSheetPO answerSheet) {
    this.answerSheet = answerSheet;
  }
  
  public Set getAnswerSheetOption() {
    return this.answerSheetOption;
  }
  
  public void setAnswerSheetOption(Set answerSheetOption) {
    this.answerSheetOption = answerSheetOption;
  }
  
  public String getContent() {
    return this.content;
  }
  
  public void setContent(String content) {
    this.content = content;
  }
  
  public Long getContentId() {
    return this.contentId;
  }
  
  public void setContentId(Long contentId) {
    this.contentId = contentId;
  }
  
  public Float getScore() {
    return this.score;
  }
  
  public void setScore(Float score) {
    this.score = score;
  }
  
  public Long getThemeId() {
    return this.themeId;
  }
  
  public void setThemeId(Long themeId) {
    this.themeId = themeId;
  }
  
  public AnswerSheetContentPO() {}
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("contentId", getContentId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof AnswerSheetContentPO))
      return false; 
    AnswerSheetContentPO castOther = (AnswerSheetContentPO)other;
    return (new EqualsBuilder())
      .append(getContentId(), castOther.getContentId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getContentId())
      .toHashCode();
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
}
