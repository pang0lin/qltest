package com.js.oa.info.infomanager.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class InforPersonalStatPO implements Serializable {
  private Long statId;
  
  private Long empId;
  
  private String empName;
  
  private Long orgId;
  
  private String orgIdString;
  
  private int statYear;
  
  private int statMonth;
  
  private int monthIssueNum;
  
  private Long accumulateNum;
  
  private String orgName;
  
  private Long domainId;
  
  private Long corpId;
  
  public Long getStatId() {
    return this.statId;
  }
  
  public void setStatId(Long statId) {
    this.statId = statId;
  }
  
  public Long getEmpId() {
    return this.empId;
  }
  
  public void setEmpId(Long empId) {
    this.empId = empId;
  }
  
  public String getEmpName() {
    return this.empName;
  }
  
  public void setEmpName(String empName) {
    this.empName = empName;
  }
  
  public Long getOrgId() {
    return this.orgId;
  }
  
  public void setOrgId(Long orgId) {
    this.orgId = orgId;
  }
  
  public String getOrgIdString() {
    return this.orgIdString;
  }
  
  public void setOrgIdString(String orgIdString) {
    this.orgIdString = orgIdString;
  }
  
  public int getStatYear() {
    return this.statYear;
  }
  
  public void setStatYear(int statYear) {
    this.statYear = statYear;
  }
  
  public int getStatMonth() {
    return this.statMonth;
  }
  
  public void setStatMonth(int statMonth) {
    this.statMonth = statMonth;
  }
  
  public int getMonthIssueNum() {
    return this.monthIssueNum;
  }
  
  public void setMonthIssueNum(int monthIssueNum) {
    this.monthIssueNum = monthIssueNum;
  }
  
  public Long getAccumulateNum() {
    return this.accumulateNum;
  }
  
  public void setAccumulateNum(Long accumulateNum) {
    this.accumulateNum = accumulateNum;
  }
  
  public String getOrgName() {
    return this.orgName;
  }
  
  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof InforPersonalStatPO))
      return false; 
    InforPersonalStatPO castOther = (InforPersonalStatPO)other;
    return (new EqualsBuilder()).append(getStatId(), castOther.getStatId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getStatId()).toHashCode();
  }
  
  public Long getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }
  
  public Long getCorpId() {
    return this.corpId;
  }
  
  public void setCorpId(Long corpId) {
    this.corpId = corpId;
  }
}
