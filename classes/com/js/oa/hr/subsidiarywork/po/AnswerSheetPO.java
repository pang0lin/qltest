package com.js.oa.hr.subsidiarywork.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AnswerSheetPO implements Serializable {
  private Long answerSheetId;
  
  private Long ballotEmp;
  
  private Date ballotDate = null;
  
  private String readedman;
  
  private Long questionnaireId;
  
  private Set answerSheetContent = null;
  
  private String domainId;
  
  public AnswerSheetPO(Long ballotEmp, Date ballotDate, Long questionnaireId, Set answerSheetContent) {
    this.ballotEmp = ballotEmp;
    this.ballotDate = ballotDate;
    this.questionnaireId = questionnaireId;
    this.answerSheetContent = answerSheetContent;
  }
  
  public Set getAnswerSheetContent() {
    return this.answerSheetContent;
  }
  
  public void setAnswerSheetContent(Set answerSheetContent) {
    this.answerSheetContent = answerSheetContent;
  }
  
  public Long getAnswerSheetId() {
    return this.answerSheetId;
  }
  
  public void setAnswerSheetId(Long answerSheetId) {
    this.answerSheetId = answerSheetId;
  }
  
  public Date getBallotDate() {
    return this.ballotDate;
  }
  
  public void setBallotDate(Date ballotDate) {
    this.ballotDate = ballotDate;
  }
  
  public Long getBallotEmp() {
    return this.ballotEmp;
  }
  
  public void setBallotEmp(Long ballotEmp) {
    this.ballotEmp = ballotEmp;
  }
  
  public Long getQuestionnaireId() {
    return this.questionnaireId;
  }
  
  public void setQuestionnaireId(Long questionnaireId) {
    this.questionnaireId = questionnaireId;
  }
  
  public AnswerSheetPO() {}
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("answerSheetId", getAnswerSheetId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof AnswerSheetPO))
      return false; 
    AnswerSheetPO castOther = (AnswerSheetPO)other;
    return (new EqualsBuilder())
      .append(getAnswerSheetId(), castOther.getAnswerSheetId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getAnswerSheetId())
      .toHashCode();
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
  
  public String getReadedman() {
    return this.readedman;
  }
  
  public void setReadedman(String readedman) {
    this.readedman = readedman;
  }
}
