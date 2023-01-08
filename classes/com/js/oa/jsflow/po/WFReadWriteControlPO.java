package com.js.oa.jsflow.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class WFReadWriteControlPO implements Serializable {
  private Long wfReadWriteControlId;
  
  private int controlType;
  
  private Long controlField;
  
  private WFActivityPO wfActivity;
  
  private String domainId;
  
  public Long getWfReadWriteControlId() {
    return this.wfReadWriteControlId;
  }
  
  public void setWfReadWriteControlId(Long wfReadWriteControlId) {
    this.wfReadWriteControlId = wfReadWriteControlId;
  }
  
  public int getControlType() {
    return this.controlType;
  }
  
  public void setControlType(int controlType) {
    this.controlType = controlType;
  }
  
  public Long getControlField() {
    return this.controlField;
  }
  
  public void setControlField(Long controlField) {
    this.controlField = controlField;
  }
  
  public WFActivityPO getWfActivity() {
    return this.wfActivity;
  }
  
  public void setWfActivity(WFActivityPO wfActivity) {
    this.wfActivity = wfActivity;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof WFReadWriteControlPO))
      return false; 
    WFReadWriteControlPO castOther = (WFReadWriteControlPO)other;
    return (new EqualsBuilder()).append(getWfReadWriteControlId(), castOther.getWfReadWriteControlId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getWfReadWriteControlId()).toHashCode();
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
}
