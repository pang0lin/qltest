package com.js.oa.hr.subsidiarywork.po;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class QuesthemePO implements Serializable {
  private Long questhemeId;
  
  private Long questionnaireId;
  
  private String title;
  
  private Integer type;
  
  private Float score;
  
  private Float orderCode;
  
  private QuestionnairePO questionnaire;
  
  private Set themeOption = null;
  
  private String domainId;
  
  public QuesthemePO(String title, Integer type, Float score, Float orderCode, QuestionnairePO questionnaire, Set themeOption) {
    this.title = title;
    this.type = type;
    this.score = score;
    this.orderCode = orderCode;
    this.questionnaire = questionnaire;
    this.themeOption = themeOption;
  }
  
  public Float getOrderCode() {
    return this.orderCode;
  }
  
  public void setOrderCode(Float orderCode) {
    this.orderCode = orderCode;
  }
  
  public Long getQuesthemeId() {
    return this.questhemeId;
  }
  
  public void setQuesthemeId(Long questhemeId) {
    this.questhemeId = questhemeId;
  }
  
  public QuestionnairePO getQuestionnaire() {
    return this.questionnaire;
  }
  
  public void setQuestionnaire(QuestionnairePO questionnaire) {
    this.questionnaire = questionnaire;
  }
  
  public Long getQuestionnaireId() {
    return this.questionnaireId;
  }
  
  public void setQuestionnaireId(Long questionnaireId) {
    this.questionnaireId = questionnaireId;
  }
  
  public Float getScore() {
    return this.score;
  }
  
  public void setScore(Float score) {
    this.score = score;
  }
  
  public String getTitle() {
    return this.title;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  public Integer getType() {
    return this.type;
  }
  
  public void setType(Integer type) {
    this.type = type;
  }
  
  public Set getThemeOption() {
    return this.themeOption = this.themeOption;
  }
  
  public void setThemeOption(Set themeOption) {
    this.themeOption = themeOption;
  }
  
  public QuesthemePO() {}
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("questhemeId", getQuesthemeId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof QuesthemePO))
      return false; 
    QuesthemePO castOther = (QuesthemePO)other;
    return (new EqualsBuilder())
      .append(getQuesthemeId(), castOther.getQuesthemeId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getQuesthemeId())
      .toHashCode();
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
}
