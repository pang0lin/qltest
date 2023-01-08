package com.js.doc.doc.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class SendFlowResavePO implements Serializable {
  private Long id;
  
  private Long flowEmpId;
  
  private String flowContent;
  
  private String flowType;
  
  private Long sendId;
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public void setFlowType(String flowType) {
    this.flowType = flowType;
  }
  
  public void setFlowContent(String flowContent) {
    this.flowContent = flowContent;
  }
  
  public void setFlowEmpId(Long flowEmpId) {
    this.flowEmpId = flowEmpId;
  }
  
  public void setSendId(Long sendId) {
    this.sendId = sendId;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public String getFlowType() {
    return this.flowType;
  }
  
  public String getFlowContent() {
    return this.flowContent;
  }
  
  public Long getFlowEmpId() {
    return this.flowEmpId;
  }
  
  public Long getSendId() {
    return this.sendId;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof SendFlowResavePO))
      return false; 
    SendFlowResavePO castOther = (SendFlowResavePO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
}
