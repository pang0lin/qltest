package com.js.oa.jsflow.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class WFProtectControlPO implements Serializable {
  private Long wfProtectControlId;
  
  private int controlType;
  
  private Long controlField;
  
  private WFActivityPO wfActivity;
  
  private String domainId;
  
  public Long getWfProtectControlId() {
    return this.wfProtectControlId;
  }
  
  public void setWfProtectControlId(Long wfProtectControlId) {
    this.wfProtectControlId = wfProtectControlId;
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
    if (!(other instanceof WFProtectControlPO))
      return false; 
    WFProtectControlPO castOther = (WFProtectControlPO)other;
    return (new EqualsBuilder()).append(getWfProtectControlId(), castOther.getWfProtectControlId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getWfProtectControlId()).toHashCode();
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
}
