package com.js.oa.hr.examination.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ExaminationSelfTestPO implements Serializable {
  private Long selfTestID;
  
  private Long testEmpID;
  
  private String testEmpName;
  
  private Long testOrgID;
  
  private String testOrgName;
  
  private Date testTime = null;
  
  private Long domainId;
  
  private Set examinationSelfTestItem = null;
  
  public Long getSelfTestID() {
    return this.selfTestID;
  }
  
  public void setSelfTestID(Long selfTestID) {
    this.selfTestID = selfTestID;
  }
  
  public Long getTestEmpID() {
    return this.testEmpID;
  }
  
  public void setTestEmpID(Long testEmpID) {
    this.testEmpID = testEmpID;
  }
  
  public String getTestEmpName() {
    return this.testEmpName;
  }
  
  public void setTestEmpName(String testEmpName) {
    this.testEmpName = testEmpName;
  }
  
  public Long getTestOrgID() {
    return this.testOrgID;
  }
  
  public void setTestOrgID(Long testOrgID) {
    this.testOrgID = testOrgID;
  }
  
  public String getTestOrgName() {
    return this.testOrgName;
  }
  
  public void setTestOrgName(String testOrgName) {
    this.testOrgName = testOrgName;
  }
  
  public Date getTestTime() {
    return this.testTime;
  }
  
  public void setTestTime(Date testTime) {
    this.testTime = testTime;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getSelfTestID())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof ExaminationSelfTestPO))
      return false; 
    ExaminationSelfTestPO castOther = (ExaminationSelfTestPO)other;
    return (new EqualsBuilder())
      .append(getSelfTestID(), castOther.getSelfTestID())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getSelfTestID())
      .toHashCode();
  }
  
  public Long getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }
  
  public Set getExaminationSelfTestItem() {
    return this.examinationSelfTestItem;
  }
  
  public void setExaminationSelfTestItem(Set examinationSelfTestItem) {
    this.examinationSelfTestItem = examinationSelfTestItem;
  }
}
