package com.js.oa.bbs.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ForumActionForm extends ActionForm {
  private String queryClass;
  
  private String queryItem;
  
  private String queryMan;
  
  private String queryText;
  
  private String[] accessoryName;
  
  private String addSign;
  
  private String forumSign;
  
  private String[] accessorySaveName;
  
  private String forumTitle;
  
  private String forumContent1;
  
  private String classId;
  
  private String queryForumType;
  
  private String allForumClass;
  
  private String anonymous;
  
  private String queryTitle;
  
  private String fExamins;
  
  private String examinYesOrNot;
  
  public String getQueryClass() {
    return this.queryClass;
  }
  
  public void setQueryClass(String queryClass) {
    this.queryClass = queryClass;
  }
  
  public String getQueryItem() {
    return this.queryItem;
  }
  
  public void setQueryItem(String queryItem) {
    this.queryItem = queryItem;
  }
  
  public String getQueryMan() {
    return this.queryMan;
  }
  
  public void setQueryMan(String queryMan) {
    this.queryMan = queryMan;
  }
  
  public String getQueryText() {
    return this.queryText;
  }
  
  public void setQueryText(String queryText) {
    this.queryText = queryText;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.queryClass = null;
    this.queryItem = null;
    this.queryMan = null;
    this.queryText = null;
    this.accessoryName = null;
    this.addSign = "1";
    this.accessorySaveName = null;
    this.forumTitle = null;
    this.forumContent1 = null;
  }
  
  public String[] getAccessoryName() {
    return this.accessoryName;
  }
  
  public void setAccessoryName(String[] accessoryName) {
    this.accessoryName = accessoryName;
  }
  
  public String[] getAccessorySaveName() {
    return this.accessorySaveName;
  }
  
  public void setAccessorySaveName(String[] accessorySaveName) {
    this.accessorySaveName = accessorySaveName;
  }
  
  public String getAddSign() {
    return this.addSign;
  }
  
  public void setAddSign(String addSign) {
    this.addSign = addSign;
  }
  
  public String getForumSign() {
    return this.forumSign;
  }
  
  public void setForumSign(String forumSign) {
    this.forumSign = forumSign;
  }
  
  public String getForumTitle() {
    return this.forumTitle;
  }
  
  public void setForumTitle(String forumTitle) {
    this.forumTitle = forumTitle;
  }
  
  public String getForumContent1() {
    return this.forumContent1;
  }
  
  public void setForumContent1(String forumContent1) {
    this.forumContent1 = forumContent1;
  }
  
  public String getClassId() {
    return this.classId;
  }
  
  public void setClassId(String classId) {
    this.classId = classId;
  }
  
  public String getQueryForumType() {
    return this.queryForumType;
  }
  
  public void setQueryForumType(String queryForumType) {
    this.queryForumType = queryForumType;
  }
  
  public String getAllForumClass() {
    return this.allForumClass;
  }
  
  public void setAllForumClass(String allForumClass) {
    this.allForumClass = allForumClass;
  }
  
  public String getAnonymous() {
    return this.anonymous;
  }
  
  public void setAnonymous(String anonymous) {
    this.anonymous = anonymous;
  }
  
  public String getQueryTitle() {
    return this.queryTitle;
  }
  
  public String getFExamins() {
    return this.fExamins;
  }
  
  public String getExaminYesOrNot() {
    return this.examinYesOrNot;
  }
  
  public void setQueryTitle(String queryTitle) {
    this.queryTitle = queryTitle;
  }
  
  public void setFExamins(String fExamins) {
    this.fExamins = fExamins;
  }
  
  public void setExaminYesOrNot(String examinYesOrNot) {
    this.examinYesOrNot = examinYesOrNot;
  }
}
