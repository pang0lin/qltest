package com.js.doc.doc.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class GovSendFileCheckWithWorkFlowAccePO implements Serializable {
  private Long id;
  
  private String displayName;
  
  private String saveName;
  
  private GovSendFileCheckWithWorkFlowPO sendFileCheck;
  
  private String domainId;
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getDisplayName() {
    return this.displayName;
  }
  
  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }
  
  public String getSaveName() {
    return this.saveName;
  }
  
  public void setSaveName(String saveName) {
    this.saveName = saveName;
  }
  
  public GovSendFileCheckWithWorkFlowPO getSendFileCheck() {
    return this.sendFileCheck;
  }
  
  public void setSendFileCheck(GovSendFileCheckWithWorkFlowPO sendFileCheck) {
    this.sendFileCheck = sendFileCheck;
  }
  
  public String toString() {
    return (new ToStringBuilder(this)).append("id", getId()).toString();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
}
