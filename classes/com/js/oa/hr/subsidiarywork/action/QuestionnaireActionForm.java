package com.js.oa.hr.subsidiarywork.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class QuestionnaireActionForm extends ActionForm {
  private String title;
  
  private String actorName;
  
  private String actorId;
  
  private String examineName;
  
  private String examineId;
  
  private Integer status = new Integer(0);
  
  private Integer grade = new Integer(0);
  
  private Float score;
  
  private Float orderCode;
  
  private String saveType;
  
  public String getActorId() {
    return this.actorId;
  }
  
  public void setActorId(String actorId) {
    this.actorId = actorId;
  }
  
  public String getActorName() {
    return this.actorName;
  }
  
  public void setActorName(String actorName) {
    this.actorName = actorName;
  }
  
  public String getExamineId() {
    return this.examineId;
  }
  
  public void setExamineId(String examineId) {
    this.examineId = examineId;
  }
  
  public String getExamineName() {
    return this.examineName;
  }
  
  public void setExamineName(String examineName) {
    this.examineName = examineName;
  }
  
  public Integer getGrade() {
    return this.grade;
  }
  
  public void setGrade(Integer grade) {
    this.grade = grade;
  }
  
  public Integer getStatus() {
    return this.status;
  }
  
  public void setStatus(Integer status) {
    this.status = status;
  }
  
  public String getTitle() {
    return this.title;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  public Float getScore() {
    return this.score;
  }
  
  public void setScore(Float score) {
    this.score = score;
  }
  
  public Float getOrderCode() {
    return this.orderCode;
  }
  
  public void setOrderCode(Float orderCode) {
    this.orderCode = orderCode;
  }
  
  public String getSaveType() {
    return this.saveType;
  }
  
  public void setSaveType(String saveType) {
    this.saveType = saveType;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.saveType = null;
    this.title = null;
    this.actorName = null;
    this.actorId = null;
    this.examineName = null;
    this.examineId = null;
    this.status = new Integer(0);
    this.grade = new Integer(0);
    this.score = null;
    this.orderCode = null;
  }
}
