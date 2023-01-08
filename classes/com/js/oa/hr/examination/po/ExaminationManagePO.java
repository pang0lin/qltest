package com.js.oa.hr.examination.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ExaminationManagePO implements Serializable {
  private Long id;
  
  private String examName;
  
  private String scopeRange;
  
  private String scopeEmpID;
  
  private String scopeOrgID;
  
  private String scopeGroupID;
  
  private String state;
  
  private Date startDate = null;
  
  private String startTime;
  
  private Date endDate = null;
  
  private String endTime;
  
  private Long radioAmount;
  
  private Long radioMark;
  
  private Long checkAmount;
  
  private Long checkMark;
  
  private Long questionAmount;
  
  private Long questionMark;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  private Long domainId;
  
  private String radioIds;
  
  private String checkIds;
  
  private String questionIds;
  
  public Long getCheckAmount() {
    return this.checkAmount;
  }
  
  public void setCheckAmount(Long checkAmount) {
    this.checkAmount = checkAmount;
  }
  
  public Long getCheckMark() {
    return this.checkMark;
  }
  
  public void setCheckMark(Long checkMark) {
    this.checkMark = checkMark;
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
  
  public Long getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }
  
  public Date getEndDate() {
    return this.endDate;
  }
  
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
  
  public String getEndTime() {
    return this.endTime;
  }
  
  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }
  
  public String getExamName() {
    return this.examName;
  }
  
  public void setExamName(String examName) {
    this.examName = examName;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public Long getQuestionAmount() {
    return this.questionAmount;
  }
  
  public void setQuestionAmount(Long questionAmount) {
    this.questionAmount = questionAmount;
  }
  
  public Long getQuestionMark() {
    return this.questionMark;
  }
  
  public void setQuestionMark(Long questionMark) {
    this.questionMark = questionMark;
  }
  
  public Long getRadioAmount() {
    return this.radioAmount;
  }
  
  public void setRadioAmount(Long radioAmount) {
    this.radioAmount = radioAmount;
  }
  
  public Long getRadioMark() {
    return this.radioMark;
  }
  
  public void setRadioMark(Long radioMark) {
    this.radioMark = radioMark;
  }
  
  public String getScopeEmpID() {
    return this.scopeEmpID;
  }
  
  public void setScopeEmpID(String scopeEmpID) {
    this.scopeEmpID = scopeEmpID;
  }
  
  public String getScopeGroupID() {
    return this.scopeGroupID;
  }
  
  public void setScopeGroupID(String scopeGroupID) {
    this.scopeGroupID = scopeGroupID;
  }
  
  public String getScopeOrgID() {
    return this.scopeOrgID;
  }
  
  public void setScopeOrgID(String scopeOrgID) {
    this.scopeOrgID = scopeOrgID;
  }
  
  public String getScopeRange() {
    return this.scopeRange;
  }
  
  public void setScopeRange(String scopeRange) {
    this.scopeRange = scopeRange;
  }
  
  public Date getStartDate() {
    return this.startDate;
  }
  
  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }
  
  public String getStartTime() {
    return this.startTime;
  }
  
  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }
  
  public String getState() {
    return this.state;
  }
  
  public void setState(String state) {
    this.state = state;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof ExaminationManagePO))
      return false; 
    ExaminationManagePO castOther = (ExaminationManagePO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
  }
  
  public String getCheckIds() {
    return this.checkIds;
  }
  
  public void setCheckIds(String checkIds) {
    this.checkIds = checkIds;
  }
  
  public String getQuestionIds() {
    return this.questionIds;
  }
  
  public void setQuestionIds(String questionIds) {
    this.questionIds = questionIds;
  }
  
  public String getRadioIds() {
    return this.radioIds;
  }
  
  public void setRadioIds(String radioIds) {
    this.radioIds = radioIds;
  }
}
