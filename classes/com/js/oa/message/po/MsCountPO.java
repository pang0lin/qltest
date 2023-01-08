package com.js.oa.message.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class MsCountPO implements Serializable {
  private Long countId;
  
  private Long sumCount;
  
  private String sendCountMan;
  
  private Long sendCountId;
  
  private String modelName;
  
  private Long modelCountId;
  
  private Date countDate = null;
  
  private Long domainId;
  
  public void setCountId(Long countId) {
    this.countId = countId;
  }
  
  public void setSumCount(Long sumCount) {
    this.sumCount = sumCount;
  }
  
  public void setCountDate(Date countDate) {
    this.countDate = countDate;
  }
  
  public void setModelCountId(Long modelCountId) {
    this.modelCountId = modelCountId;
  }
  
  public void setSendCountId(Long sendCountId) {
    this.sendCountId = sendCountId;
  }
  
  public void setSendCountMan(String sendCountMan) {
    this.sendCountMan = sendCountMan;
  }
  
  public void setModelName(String modelName) {
    this.modelName = modelName;
  }
  
  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }
  
  public Long getCountId() {
    return this.countId;
  }
  
  public Long getSumCount() {
    return this.sumCount;
  }
  
  public Date getCountDate() {
    return this.countDate;
  }
  
  public Long getModelCountId() {
    return this.modelCountId;
  }
  
  public Long getSendCountId() {
    return this.sendCountId;
  }
  
  public String getSendCountMan() {
    return this.sendCountMan;
  }
  
  public String getModelName() {
    return this.modelName;
  }
  
  public Long getDomainId() {
    return this.domainId;
  }
  
  public MsCountPO() {}
  
  public MsCountPO(Long sumCount, Long modelCountId, String modelName, Date countDate, Long sendCountId, String sendCountMan) {
    this.sumCount = sumCount;
    this.modelCountId = modelCountId;
    this.countDate = countDate;
    this.sendCountId = sendCountId;
    this.modelName = modelName;
    this.sendCountMan = sendCountMan;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("countId", getCountId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof MsCountPO))
      return false; 
    MsCountPO castOther = (MsCountPO)other;
    return (new EqualsBuilder())
      .append(getCountId(), castOther.getCountId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getCountId())
      .toHashCode();
  }
}
