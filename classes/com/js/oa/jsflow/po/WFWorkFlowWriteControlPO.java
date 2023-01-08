package com.js.oa.jsflow.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class WFWorkFlowWriteControlPO implements Serializable {
  private Long wfWorkFlowWriteControlId;
  
  private Long writeControlField;
  
  private WFWorkFlowProcessPO wfWorkFlowProcess;
  
  private String domainId;
  
  private String controlType;
  
  public Long getWfWorkFlowWriteControlId() {
    return this.wfWorkFlowWriteControlId;
  }
  
  public void setWfWorkFlowWriteControlId(Long wfWorkFlowWriteControlId) {
    this.wfWorkFlowWriteControlId = wfWorkFlowWriteControlId;
  }
  
  public Long getWriteControlField() {
    return this.writeControlField;
  }
  
  public void setWriteControlField(Long writeControlField) {
    this.writeControlField = writeControlField;
  }
  
  public WFWorkFlowProcessPO getWfWorkFlowProcess() {
    return this.wfWorkFlowProcess;
  }
  
  public void setWfWorkFlowProcess(WFWorkFlowProcessPO wfWorkFlowProcess) {
    this.wfWorkFlowProcess = wfWorkFlowProcess;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof WFWorkFlowWriteControlPO))
      return false; 
    WFWorkFlowWriteControlPO castOther = (WFWorkFlowWriteControlPO)other;
    return (new EqualsBuilder()).append(getWfWorkFlowWriteControlId(), castOther.getWfWorkFlowWriteControlId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getWfWorkFlowWriteControlId()).toHashCode();
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
  
  public String getControlType() {
    return this.controlType;
  }
  
  public void setControlType(String controlType) {
    this.controlType = controlType;
  }
}
