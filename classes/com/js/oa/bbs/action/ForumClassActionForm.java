package com.js.oa.bbs.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ForumClassActionForm extends ActionForm {
  private String allForumClass;
  
  private String currentSortCode;
  
  private String parentSort;
  
  private String userOrgGroup;
  
  private String insertSite;
  
  private String classParentName;
  
  private String className;
  
  private String classOwnerName;
  
  private String classRemark;
  
  private String classUserName;
  
  private short classLevel;
  
  private long classOwnerId;
  
  private long classParent;
  
  private String queryText;
  
  private String editSort;
  
  private String editType;
  
  private String classOwnerIds;
  
  private String banPrint;
  
  private String classSort;
  
  private String checkExamin;
  
  private String estopAnonymity;
  
  private String startPeriod;
  
  private String endPeriod;
  
  private int fullDay;
  
  private Long relProjectId;
  
  public Long getRelProjectId() {
    return this.relProjectId;
  }
  
  public void setRelProjectId(Long relProjectId) {
    this.relProjectId = relProjectId;
  }
  
  public String getAllForumClass() {
    return this.allForumClass;
  }
  
  public void setAllForumClass(String allForumClass) {
    this.allForumClass = allForumClass;
  }
  
  public String getClassName() {
    return this.className;
  }
  
  public void setClassName(String className) {
    this.className = className;
  }
  
  public long getClassOwnerId() {
    return this.classOwnerId;
  }
  
  public void setClassOwnerId(long classOwnerId) {
    this.classOwnerId = classOwnerId;
  }
  
  public String getClassOwnerName() {
    return this.classOwnerName;
  }
  
  public void setClassOwnerName(String classOwnerName) {
    this.classOwnerName = classOwnerName;
  }
  
  public String getClassRemark() {
    return this.classRemark;
  }
  
  public void setClassRemark(String classRemark) {
    this.classRemark = classRemark;
  }
  
  public String getClassUserName() {
    return this.classUserName;
  }
  
  public void setClassUserName(String classUserName) {
    this.classUserName = classUserName;
  }
  
  public String getUserOrgGroup() {
    return this.userOrgGroup;
  }
  
  public void setUserOrgGroup(String userOrgGroup) {
    this.userOrgGroup = userOrgGroup;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.allForumClass = null;
    this.className = null;
    this.classOwnerId = 0L;
    this.classOwnerName = null;
    this.classRemark = null;
    this.classUserName = null;
    this.userOrgGroup = null;
    this.checkExamin = null;
  }
  
  public String getInsertSite() {
    return this.insertSite;
  }
  
  public void setInsertSite(String insertSite) {
    this.insertSite = insertSite;
  }
  
  public String getCurrentSortCode() {
    return this.currentSortCode;
  }
  
  public void setCurrentSortCode(String currentSortCode) {
    this.currentSortCode = currentSortCode;
  }
  
  public String getParentSort() {
    return this.parentSort;
  }
  
  public void setParentSort(String parentSort) {
    this.parentSort = parentSort;
  }
  
  public short getClassLevel() {
    return this.classLevel;
  }
  
  public void setClassLevel(short classLevel) {
    this.classLevel = classLevel;
  }
  
  public long getClassParent() {
    return this.classParent;
  }
  
  public void setClassParent(long classParent) {
    this.classParent = classParent;
  }
  
  public String getQueryText() {
    return this.queryText;
  }
  
  public void setQueryText(String queryText) {
    this.queryText = queryText;
  }
  
  public String getEditSort() {
    return this.editSort;
  }
  
  public void setEditSort(String editSort) {
    this.editSort = editSort;
  }
  
  public String getEditType() {
    return this.editType;
  }
  
  public String getCheckExamin() {
    return this.checkExamin;
  }
  
  public String getEndPeriod() {
    return this.endPeriod;
  }
  
  public String getStartPeriod() {
    return this.startPeriod;
  }
  
  public int getFullDay() {
    return this.fullDay;
  }
  
  public String getClassOwnerIds() {
    return this.classOwnerIds;
  }
  
  public String getBanPrint() {
    return this.banPrint;
  }
  
  public String getEstopAnonymity() {
    return this.estopAnonymity;
  }
  
  public void setEditType(String editType) {
    this.editType = editType;
  }
  
  public void setCheckExamin(String checkExamin) {
    this.checkExamin = checkExamin;
  }
  
  public void setEndPeriod(String endPeriod) {
    this.endPeriod = endPeriod;
  }
  
  public void setStartPeriod(String startPeriod) {
    this.startPeriod = startPeriod;
  }
  
  public void setFullDay(int fullDay) {
    this.fullDay = fullDay;
  }
  
  public void setClassOwnerIds(String classOwnerIds) {
    this.classOwnerIds = classOwnerIds;
  }
  
  public void setBanPrint(String banPrint) {
    this.banPrint = banPrint;
  }
  
  public void setEstopAnonymity(String estopAnonymity) {
    this.estopAnonymity = estopAnonymity;
  }
  
  public String getClassParentName() {
    return this.classParentName;
  }
  
  public void setClassParentName(String classParentName) {
    this.classParentName = classParentName;
  }
  
  public String getClassSort() {
    return this.classSort;
  }
  
  public void setClassSort(String classSort) {
    this.classSort = classSort;
  }
}
