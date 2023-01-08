package com.js.oa.hr.examination.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ExaminationStockActionForm extends ActionForm {
  private Long examinationType;
  
  private String examinationStyle;
  
  private String subject;
  
  private String result;
  
  private String sign;
  
  private String saveType;
  
  private Long examinationID;
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.examinationType = new Long(0L);
    this.examinationStyle = null;
    this.subject = null;
    this.result = null;
  }
  
  public String getExaminationStyle() {
    return this.examinationStyle;
  }
  
  public void setExaminationStyle(String examinationStyle) {
    this.examinationStyle = examinationStyle;
  }
  
  public Long getExaminationType() {
    return this.examinationType;
  }
  
  public void setExaminationType(Long examinationType) {
    this.examinationType = examinationType;
  }
  
  public String getResult() {
    return this.result;
  }
  
  public void setResult(String result) {
    this.result = result;
  }
  
  public String getSign() {
    return this.sign;
  }
  
  public void setSign(String sign) {
    this.sign = sign;
  }
  
  public String getSubject() {
    return this.subject;
  }
  
  public void setSubject(String subject) {
    this.subject = subject;
  }
  
  public String getSaveType() {
    return this.saveType;
  }
  
  public void setSaveType(String saveType) {
    this.saveType = saveType;
  }
  
  public Long getExaminationID() {
    return this.examinationID;
  }
  
  public void setExaminationID(Long examinationID) {
    this.examinationID = examinationID;
  }
}
