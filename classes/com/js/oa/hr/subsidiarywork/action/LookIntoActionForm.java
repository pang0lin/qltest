package com.js.oa.hr.subsidiarywork.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class LookIntoActionForm extends ActionForm {
  private String surveyContent;
  
  private String surveyRange;
  
  private String surveyStatus;
  
  private String surveyType;
  
  private String surveyBeginTime;
  
  private String surveyEndTime;
  
  private String con;
  
  private String done;
  
  private String surveyRangeName;
  
  private String editId;
  
  private String[][] items;
  
  private String delItems;
  
  private String[] updateItems;
  
  private String[] newItems;
  
  private String[] updateItemsIds;
  
  public String[][] getItems() {
    return this.items;
  }
  
  public void setItems(String[][] items) {
    this.items = items;
  }
  
  public String getSurveyContent() {
    return this.surveyContent;
  }
  
  public void setSurveyContent(String surveyContent) {
    this.surveyContent = surveyContent;
  }
  
  public String getSurveyRange() {
    return this.surveyRange;
  }
  
  public void setSurveyRange(String surveyRange) {
    this.surveyRange = surveyRange;
  }
  
  public String getSurveyStatus() {
    return this.surveyStatus;
  }
  
  public void setSurveyStatus(String surveyStatus) {
    this.surveyStatus = surveyStatus;
  }
  
  public String getSurveyType() {
    return this.surveyType;
  }
  
  public void setSurveyType(String surveyType) {
    this.surveyType = surveyType;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public String getSurveyBeginTime() {
    return this.surveyBeginTime;
  }
  
  public void setSurveyBeginTime(String surveyBeginTime) {
    this.surveyBeginTime = surveyBeginTime;
  }
  
  public String getSurveyEndTime() {
    return this.surveyEndTime;
  }
  
  public void setSurveyEndTime(String surveyEndTime) {
    this.surveyEndTime = surveyEndTime;
  }
  
  public String getCon() {
    return this.con;
  }
  
  public void setCon(String con) {
    this.con = con;
  }
  
  public String getDone() {
    return this.done;
  }
  
  public void setDone(String done) {
    this.done = done;
  }
  
  public String getSurveyRangeName() {
    return this.surveyRangeName;
  }
  
  public void setSurveyRangeName(String surveyRangeName) {
    this.surveyRangeName = surveyRangeName;
  }
  
  public String getEditId() {
    return this.editId;
  }
  
  public void setEditId(String editId) {
    this.editId = editId;
  }
  
  public String getDelItems() {
    return this.delItems;
  }
  
  public void setDelItems(String delItems) {
    this.delItems = delItems;
  }
  
  public String[] getUpdateItems() {
    return this.updateItems;
  }
  
  public void setUpdateItems(String[] updateItems) {
    this.updateItems = updateItems;
  }
  
  public String[] getNewItems() {
    return this.newItems;
  }
  
  public void setNewItems(String[] newItems) {
    this.newItems = newItems;
  }
  
  public String[] getUpdateItemsIds() {
    return this.updateItemsIds;
  }
  
  public void setUpdateItemsIds(String[] updateItemsIds) {
    this.updateItemsIds = updateItemsIds;
  }
}
