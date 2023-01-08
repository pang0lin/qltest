package com.js.oa.hr.examination.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ExaminationManageActionForm extends ActionForm {
  private Long id;
  
  private String examName;
  
  private String scopeRange;
  
  private String scopeCode;
  
  private String scopeEmpID;
  
  private String scopeOrgID;
  
  private String scopeGroupID;
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  private String state = "1";
  
  private String startTime;
  
  private String endTime;
  
  private Long radioAmount;
  
  private Long radioMark;
  
  private Long checkAmount;
  
  private Long checkMark;
  
  private Long questionAmount;
  
  private Long questionMark;
  
  private String saveType;
  
  private String radioIds;
  
  private String checkIds;
  
  private String questionIds;
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.examName = null;
    this.scopeRange = null;
    this.scopeCode = null;
    this.state = "1";
    this.radioAmount = null;
    this.radioMark = null;
    this.checkAmount = null;
    this.checkMark = null;
    this.questionAmount = null;
    this.questionMark = null;
  }
  
  public Long getCheckAmount() {
    return this.checkAmount;
  }
  
  public void setCheckAmount(Long checkAmount) {
    this.checkAmount = checkAmount;
  }
  
  public Long getCheckMark() {
    return this.checkMark;
  }
  
  public void setCheckMark(Long checkMark) {
    this.checkMark = checkMark;
  }
  
  public String getEndTime() {
    return this.endTime;
  }
  
  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }
  
  public String getExamName() {
    return this.examName;
  }
  
  public void setExamName(String examName) {
    this.examName = examName;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public Long getQuestionAmount() {
    return this.questionAmount;
  }
  
  public void setQuestionAmount(Long questionAmount) {
    this.questionAmount = questionAmount;
  }
  
  public Long getQuestionMark() {
    return this.questionMark;
  }
  
  public void setQuestionMark(Long questionMark) {
    this.questionMark = questionMark;
  }
  
  public Long getRadioAmount() {
    return this.radioAmount;
  }
  
  public void setRadioAmount(Long radioAmount) {
    this.radioAmount = radioAmount;
  }
  
  public Long getRadioMark() {
    return this.radioMark;
  }
  
  public void setRadioMark(Long radioMark) {
    this.radioMark = radioMark;
  }
  
  public String getScopeCode() {
    return this.scopeCode;
  }
  
  public void setScopeCode(String scopeCode) {
    this.scopeCode = scopeCode;
  }
  
  public String getScopeEmpID() {
    return this.scopeEmpID;
  }
  
  public void setScopeEmpID(String scopeEmpID) {
    this.scopeEmpID = scopeEmpID;
  }
  
  public String getScopeGroupID() {
    return this.scopeGroupID;
  }
  
  public void setScopeGroupID(String scopeGroupID) {
    this.scopeGroupID = scopeGroupID;
  }
  
  public String getScopeOrgID() {
    return this.scopeOrgID;
  }
  
  public void setScopeOrgID(String scopeOrgID) {
    this.scopeOrgID = scopeOrgID;
  }
  
  public String getScopeRange() {
    return this.scopeRange;
  }
  
  public void setScopeRange(String scopeRange) {
    this.scopeRange = scopeRange;
  }
  
  public String getStartTime() {
    return this.startTime;
  }
  
  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }
  
  public String getState() {
    return this.state;
  }
  
  public void setState(String state) {
    this.state = state;
  }
  
  public String getSaveType() {
    return this.saveType;
  }
  
  public void setSaveType(String saveType) {
    this.saveType = saveType;
  }
  
  public String getCheckIds() {
    return this.checkIds;
  }
  
  public void setCheckIds(String checkIds) {
    this.checkIds = checkIds;
  }
  
  public String getQuestionIds() {
    return this.questionIds;
  }
  
  public void setQuestionIds(String questionIds) {
    this.questionIds = questionIds;
  }
  
  public String getRadioIds() {
    return this.radioIds;
  }
  
  public void setRadioIds(String radioIds) {
    this.radioIds = radioIds;
  }
}
