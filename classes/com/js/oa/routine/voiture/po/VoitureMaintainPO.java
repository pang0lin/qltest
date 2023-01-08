package com.js.oa.routine.voiture.po;

import java.io.Serializable;
import java.util.Date;

public class VoitureMaintainPO implements Serializable {
  private Long id;
  
  private VoiturePO voiturePO;
  
  private Date maintainTime = null;
  
  private Long domainId;
  
  public Long getId() {
    return this.id;
  }
  
  public Date getMaintainTime() {
    return this.maintainTime;
  }
  
  public VoiturePO getVoiturePO() {
    return this.voiturePO;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public void setMaintainTime(Date maintainTime) {
    this.maintainTime = maintainTime;
  }
  
  public void setVoiturePO(VoiturePO voiturePO) {
    this.voiturePO = voiturePO;
  }
  
  public Long getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }
}
