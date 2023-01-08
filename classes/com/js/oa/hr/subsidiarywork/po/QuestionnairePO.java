package com.js.oa.hr.subsidiarywork.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class QuestionnairePO implements Serializable {
  private Long questionnaireId;
  
  private String title;
  
  private String actorEmp;
  
  private String actorOrg;
  
  private String actorGroup;
  
  private String actorName;
  
  private String examineEmp;
  
  private String examineOrg;
  
  private String examineGroup;
  
  private String examineName;
  
  private Integer status;
  
  private Date startDate = null;
  
  private Date endDate = null;
  
  private Integer grade;
  
  private Long createdEmp;
  
  private Long cratedOrg;
  
  private Set questheme = null;
  
  private String domainId;
  
  public QuestionnairePO(String title, String actorEmp, String actorOrg, String actorGroup, String actorName, String examineEmp, String examineOrg, String examineGroup, String examineName, Integer status, Date startDate, Date endDate, Integer grade, Long createdEmp, Long cratedOrg, Set questheme) {
    this.title = title;
    this.actorEmp = actorEmp;
    this.actorOrg = actorOrg;
    this.actorGroup = actorGroup;
    this.actorName = actorName;
    this.examineEmp = examineEmp;
    this.examineOrg = examineOrg;
    this.examineGroup = examineGroup;
    this.examineName = examineName;
    this.status = status;
    this.startDate = startDate;
    this.endDate = endDate;
    this.grade = grade;
    this.createdEmp = createdEmp;
    this.cratedOrg = cratedOrg;
    this.questheme = questheme;
  }
  
  public String getActorEmp() {
    return this.actorEmp;
  }
  
  public void setActorEmp(String actorEmp) {
    this.actorEmp = actorEmp;
  }
  
  public String getActorGroup() {
    return this.actorGroup;
  }
  
  public void setActorGroup(String actorGroup) {
    this.actorGroup = actorGroup;
  }
  
  public String getActorName() {
    return this.actorName;
  }
  
  public void setActorName(String actorName) {
    this.actorName = actorName;
  }
  
  public String getActorOrg() {
    return this.actorOrg;
  }
  
  public void setActorOrg(String actorOrg) {
    this.actorOrg = actorOrg;
  }
  
  public Long getCratedOrg() {
    return this.cratedOrg;
  }
  
  public void setCratedOrg(Long cratedOrg) {
    this.cratedOrg = cratedOrg;
  }
  
  public Long getCreatedEmp() {
    return this.createdEmp;
  }
  
  public void setCreatedEmp(Long createdEmp) {
    this.createdEmp = createdEmp;
  }
  
  public Date getEndDate() {
    return this.endDate;
  }
  
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
  
  public String getExamineEmp() {
    return this.examineEmp;
  }
  
  public void setExamineEmp(String examineEmp) {
    this.examineEmp = examineEmp;
  }
  
  public String getExamineGroup() {
    return this.examineGroup;
  }
  
  public void setExamineGroup(String examineGroup) {
    this.examineGroup = examineGroup;
  }
  
  public String getExamineName() {
    return this.examineName;
  }
  
  public void setExamineName(String examineName) {
    this.examineName = examineName;
  }
  
  public String getExamineOrg() {
    return this.examineOrg;
  }
  
  public void setExamineOrg(String examineOrg) {
    this.examineOrg = examineOrg;
  }
  
  public Integer getGrade() {
    return this.grade;
  }
  
  public void setGrade(Integer grade) {
    this.grade = grade;
  }
  
  public Long getQuestionnaireId() {
    return this.questionnaireId;
  }
  
  public void setQuestionnaireId(Long questionnaireId) {
    this.questionnaireId = questionnaireId;
  }
  
  public Date getStartDate() {
    return this.startDate;
  }
  
  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }
  
  public Integer getStatus() {
    return this.status;
  }
  
  public void setStatus(Integer status) {
    this.status = status;
  }
  
  public String getTitle() {
    return this.title;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  public Set getQuestheme() {
    return this.questheme;
  }
  
  public void setQuestheme(Set questheme) {
    this.questheme = questheme;
  }
  
  public QuestionnairePO() {}
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("questionnaireId", getQuestionnaireId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof QuestionnairePO))
      return false; 
    QuestionnairePO castOther = (QuestionnairePO)other;
    return (new EqualsBuilder())
      .append(getQuestionnaireId(), castOther.getQuestionnaireId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getQuestionnaireId())
      .toHashCode();
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
}
