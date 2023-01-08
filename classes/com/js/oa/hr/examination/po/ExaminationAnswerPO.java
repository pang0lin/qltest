package com.js.oa.hr.examination.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ExaminationAnswerPO implements Serializable {
  private Long answerID;
  
  private Long examinationID;
  
  private Long empID;
  
  private String empName;
  
  private Long orgID;
  
  private String orgName;
  
  private Date examTime = null;
  
  private Long score;
  
  private Set examinationAnswerItem = null;
  
  public Long getAnswerID() {
    return this.answerID;
  }
  
  public void setAnswerID(Long answerID) {
    this.answerID = answerID;
  }
  
  public Long getEmpID() {
    return this.empID;
  }
  
  public void setEmpID(Long empID) {
    this.empID = empID;
  }
  
  public String getEmpName() {
    return this.empName;
  }
  
  public void setEmpName(String empName) {
    this.empName = empName;
  }
  
  public Set getExaminationAnswerItem() {
    return this.examinationAnswerItem;
  }
  
  public void setExaminationAnswerItem(Set examinationAnswerItem) {
    this.examinationAnswerItem = examinationAnswerItem;
  }
  
  public Long getExaminationID() {
    return this.examinationID;
  }
  
  public void setExaminationID(Long examinationID) {
    this.examinationID = examinationID;
  }
  
  public Date getExamTime() {
    return this.examTime;
  }
  
  public void setExamTime(Date examTime) {
    this.examTime = examTime;
  }
  
  public Long getOrgID() {
    return this.orgID;
  }
  
  public void setOrgID(Long orgID) {
    this.orgID = orgID;
  }
  
  public String getOrgName() {
    return this.orgName;
  }
  
  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }
  
  public Long getScore() {
    return this.score;
  }
  
  public void setScore(Long score) {
    this.score = score;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getAnswerID())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof ExaminationAnswerPO))
      return false; 
    ExaminationAnswerPO castOther = (ExaminationAnswerPO)other;
    return (new EqualsBuilder())
      .append(getAnswerID(), castOther.getAnswerID())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getAnswerID())
      .toHashCode();
  }
}
