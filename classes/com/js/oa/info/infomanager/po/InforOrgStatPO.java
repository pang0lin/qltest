package com.js.oa.info.infomanager.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class InforOrgStatPO implements Serializable {
  private Long statId;
  
  private Long orgId;
  
  private String orgIdString;
  
  private int statYear;
  
  private int statMonth;
  
  private int monthIssueNum;
  
  private Long accumulateNum;
  
  private String orgName;
  
  private int orgLevel;
  
  private Long domainId;
  
  private Long corpId;
  
  public Long getCorpId() {
    return this.corpId;
  }
  
  public void setCorpId(Long corpId) {
    this.corpId = corpId;
  }
  
  public Long getStatId() {
    return this.statId;
  }
  
  public void setStatId(Long statId) {
    this.statId = statId;
  }
  
  public Long getOrgId() {
    return this.orgId;
  }
  
  public void setOrgId(Long orgId) {
    this.orgId = orgId;
  }
  
  public String getOrgName() {
    return this.orgName;
  }
  
  public void setOrgName(String orgName) {
    this.orgName = orgName;
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
  
  public int getOrgLevel() {
    return this.orgLevel;
  }
  
  public void setOrgLevel(int orgLevel) {
    this.orgLevel = orgLevel;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof InforPersonalStatPO))
      return false; 
    InforOrgStatPO castOther = (InforOrgStatPO)other;
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
}
