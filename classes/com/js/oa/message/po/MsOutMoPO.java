package com.js.oa.message.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class MsOutMoPO implements Serializable {
  private Long outMoId;
  
  private String sendOutMan;
  
  private Long sendOutId;
  
  private String domainId;
  
  public void setOutMoId(Long outMoId) {
    this.outMoId = outMoId;
  }
  
  public void setSendOutId(Long sendOutId) {
    this.sendOutId = sendOutId;
  }
  
  public void setSendOutMan(String sendOutMan) {
    this.sendOutMan = sendOutMan;
  }
  
  public Long getOutMoId() {
    return this.outMoId;
  }
  
  public Long getSendOutId() {
    return this.sendOutId;
  }
  
  public String getSendOutMan() {
    return this.sendOutMan;
  }
  
  public MsOutMoPO() {}
  
  public MsOutMoPO(String sendOutMan, Long sendOutId) {
    this.sendOutId = sendOutId;
    this.sendOutMan = sendOutMan;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("outMoId", getOutMoId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof MsOutMoPO))
      return false; 
    MsOutMoPO castOther = (MsOutMoPO)other;
    return (new EqualsBuilder())
      .append(getOutMoId(), castOther.getOutMoId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getOutMoId())
      .toHashCode();
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
}
