package com.js.oa.message.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class MsLimitPO implements Serializable {
  private Long limitId;
  
  private String sendLimitId;
  
  private String sendLimitMan;
  
  private String sendLimitOrg;
  
  private String sendLimitGroup;
  
  private Long limitCount;
  
  private String domainId;
  
  private Long monthCount;
  
  private Long dayCount;
  
  public void setLimitId(Long limitId) {
    this.limitId = limitId;
  }
  
  public void setLimitCount(Long limitCount) {
    this.limitCount = limitCount;
  }
  
  public void setSendLimitId(String sendLimitId) {
    this.sendLimitId = sendLimitId;
  }
  
  public void setSendLimitMan(String sendLimitMan) {
    this.sendLimitMan = sendLimitMan;
  }
  
  public Long getLimitId() {
    return this.limitId;
  }
  
  public Long getLimitCount() {
    return this.limitCount;
  }
  
  public String getSendLimitId() {
    return this.sendLimitId;
  }
  
  public String getSendLimitMan() {
    return this.sendLimitMan;
  }
  
  public MsLimitPO() {}
  
  public MsLimitPO(String sendLimitId, String sendLimitMan, Long limitCount) {
    this.limitCount = limitCount;
    this.sendLimitId = sendLimitId;
    this.sendLimitMan = sendLimitMan;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("limitId", getLimitId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof MsLimitPO))
      return false; 
    MsLimitPO castOther = (MsLimitPO)other;
    return (new EqualsBuilder())
      .append(getLimitId(), castOther.getLimitId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getLimitId())
      .toHashCode();
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public Long getDayCount() {
    return this.dayCount;
  }
  
  public Long getMonthCount() {
    return this.monthCount;
  }
  
  public String getSendLimitGroup() {
    return this.sendLimitGroup;
  }
  
  public String getSendLimitOrg() {
    return this.sendLimitOrg;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
  
  public void setDayCount(Long dayCount) {
    this.dayCount = dayCount;
  }
  
  public void setMonthCount(Long monthCount) {
    this.monthCount = monthCount;
  }
  
  public void setSendLimitGroup(String sendLimitGroup) {
    this.sendLimitGroup = sendLimitGroup;
  }
  
  public void setSendLimitOrg(String sendLimitOrg) {
    this.sendLimitOrg = sendLimitOrg;
  }
}
