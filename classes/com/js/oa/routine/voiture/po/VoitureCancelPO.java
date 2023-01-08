package com.js.oa.routine.voiture.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;

public class VoitureCancelPO implements Serializable {
  private Long id;
  
  private VoitureApplyPO voitureApplyPO;
  
  private Date cancelDate = null;
  
  private String cancelTime;
  
  private String cancelMode;
  
  private String cancelReason;
  
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
  
  public Date getCancelDate() {
    return this.cancelDate;
  }
  
  public void setCancelDate(Date cancelDate) {
    this.cancelDate = cancelDate;
  }
  
  public String getCancelTime() {
    return this.cancelTime;
  }
  
  public void setCancelTime(String cancelTime) {
    this.cancelTime = cancelTime;
  }
  
  public String getCancelMode() {
    return this.cancelMode;
  }
  
  public void setCancelMode(String cancelMode) {
    this.cancelMode = cancelMode;
  }
  
  public String getCancelReason() {
    return this.cancelReason;
  }
  
  public void setCancelReason(String cancelReason) {
    this.cancelReason = cancelReason;
  }
  
  public Long getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }
}
