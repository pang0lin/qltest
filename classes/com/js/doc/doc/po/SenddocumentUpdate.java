package com.js.doc.doc.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class SenddocumentUpdate implements Serializable {
  private Long id;
  
  private Long updateEmpId;
  
  private String updateEmpName;
  
  private Long updateOrgId;
  
  private String updateOrgName;
  
  private String sendTitle;
  
  private String sendMainTo;
  
  private String sendCopyTo;
  
  private String updateTime;
  
  private Long sendFileId;
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public void setUpdateEmpId(Long updateEmpId) {
    this.updateEmpId = updateEmpId;
  }
  
  public void setUpdateOrgId(Long updateOrgId) {
    this.updateOrgId = updateOrgId;
  }
  
  public void setSendMainTo(String sendMainTo) {
    this.sendMainTo = sendMainTo;
  }
  
  public void setSendTitle(String sendTitle) {
    this.sendTitle = sendTitle;
  }
  
  public void setUpdateOrgName(String updateOrgName) {
    this.updateOrgName = updateOrgName;
  }
  
  public void setSendCopyTo(String sendCopyTo) {
    this.sendCopyTo = sendCopyTo;
  }
  
  public void setUpdateTime(String updateTime) {
    this.updateTime = updateTime;
  }
  
  public void setUpdateEmpName(String updateEmpName) {
    this.updateEmpName = updateEmpName;
  }
  
  public void setSendFileId(Long sendFileId) {
    this.sendFileId = sendFileId;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public Long getUpdateEmpId() {
    return this.updateEmpId;
  }
  
  public Long getUpdateOrgId() {
    return this.updateOrgId;
  }
  
  public String getSendMainTo() {
    return this.sendMainTo;
  }
  
  public String getSendTitle() {
    return this.sendTitle;
  }
  
  public String getUpdateOrgName() {
    return this.updateOrgName;
  }
  
  public String getSendCopyTo() {
    return this.sendCopyTo;
  }
  
  public String getUpdateTime() {
    return this.updateTime;
  }
  
  public String getUpdateEmpName() {
    return this.updateEmpName;
  }
  
  public Long getSendFileId() {
    return this.sendFileId;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof SenddocumentUpdate))
      return false; 
    SenddocumentUpdate castOther = (SenddocumentUpdate)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
}
