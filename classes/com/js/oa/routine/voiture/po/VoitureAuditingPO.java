package com.js.oa.routine.voiture.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

public class VoitureAuditingPO implements Serializable {
  private Long id;
  
  private VoitureApplyPO voitureApplyPO;
  
  private String auditingStyle;
  
  private String auditingAccount;
  
  private Long domainId;
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public VoitureApplyPO getVoitureApplyPO() {
    return this.voitureApplyPO;
  }
  
  public void setVoitureApplyPO(VoitureApplyPO voitureApplyPO) {
    this.voitureApplyPO = voitureApplyPO;
  }
  
  public String getAuditingStyle() {
    return this.auditingStyle;
  }
  
  public void setAuditingStyle(String auditingStyle) {
    this.auditingStyle = auditingStyle;
  }
  
  public String getAuditingAccount() {
    return this.auditingAccount;
  }
  
  public void setAuditingAccount(String auditingAccount) {
    this.auditingAccount = auditingAccount;
  }
  
  public Long getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }
}
