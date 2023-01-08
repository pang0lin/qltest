package com.js.oa.routine.voiture.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class VoitureCancelActionForm extends ActionForm {
  private String applyId;
  
  private String cancelDate;
  
  private String cancelMode;
  
  private String cancelReason;
  
  private String cancelTime;
  
  private String voitureId;
  
  public String getApplyId() {
    return this.applyId;
  }
  
  public void setApplyId(String applyId) {
    this.applyId = applyId;
  }
  
  public String getCancelDate() {
    return this.cancelDate;
  }
  
  public void setCancelDate(String cancelDate) {
    this.cancelDate = cancelDate;
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
  
  public String getCancelTime() {
    return this.cancelTime;
  }
  
  public void setCancelTime(String cancelTime) {
    this.cancelTime = cancelTime;
  }
  
  public String getVoitureId() {
    return this.voitureId;
  }
  
  public void setVoitureId(String voitureId) {
    this.voitureId = voitureId;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
}
