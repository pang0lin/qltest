package com.js.oa.hr.officemanager.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class DutyPO implements Serializable {
  private long id;
  
  private String dutyNO;
  
  private String dutyName;
  
  private String domainId;
  
  private String dutyLevel;
  
  private String dutyDescribe;
  
  private Long corpId;
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public String getDutyNO() {
    return this.dutyNO;
  }
  
  public void setDutyNO(String dutyNO) {
    this.dutyNO = dutyNO;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof DutyPO))
      return false; 
    DutyPO castOther = (DutyPO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
  }
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public String getDutyName() {
    return this.dutyName;
  }
  
  public void setDutyName(String dutyName) {
    this.dutyName = dutyName;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
  
  public String getDutyLevel() {
    return this.dutyLevel;
  }
  
  public String getDutyDescribe() {
    return this.dutyDescribe;
  }
  
  public void setDutyLevel(String dutyLevel) {
    this.dutyLevel = dutyLevel;
  }
  
  public void setDutyDescribe(String dutyDescribe) {
    this.dutyDescribe = dutyDescribe;
  }
  
  public Long getCorpId() {
    return this.corpId;
  }
  
  public void setCorpId(Long corpId) {
    this.corpId = corpId;
  }
}
