package com.js.oa.routine.voiture.action;

import org.apache.struts.action.ActionForm;

public class VoitureMaintainActionForm extends ActionForm {
  private Long id;
  
  private Long voitureId;
  
  private String maintainTime;
  
  private String voitureName;
  
  public Long getId() {
    return this.id;
  }
  
  public String getMaintainTime() {
    return this.maintainTime;
  }
  
  public Long getVoitureId() {
    return this.voitureId;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public void setMaintainTime(String maintainTime) {
    this.maintainTime = maintainTime;
  }
  
  public void setVoitureId(Long voitureId) {
    this.voitureId = voitureId;
  }
  
  public String getVoitureName() {
    return this.voitureName;
  }
  
  public void setVoitureName(String voitureName) {
    this.voitureName = voitureName;
  }
}
