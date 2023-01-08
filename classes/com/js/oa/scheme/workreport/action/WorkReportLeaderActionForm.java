package com.js.oa.scheme.workreport.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class WorkReportLeaderActionForm extends ActionForm {
  private String queryItem;
  
  private String queryText;
  
  private String usersId;
  
  private String usersName;
  
  private String postilContent;
  
  private String postilEmpName;
  
  private String receiveRecordId;
  
  private String queryName;
  
  private String reportType;
  
  private String nextWorkClew;
  
  private String grade;
  
  private String postilResult;
  
  private String postilGrade;
  
  private String reportDepart;
  
  private String reportEmpId;
  
  private String reportDepartId;
  
  private String reportJob;
  
  private String selYear;
  
  private String reportReader;
  
  private String reportReaderId;
  
  private String transReason;
  
  private String reportCourse;
  
  public String getQueryItem() {
    return this.queryItem;
  }
  
  public void setQueryItem(String queryItem) {
    this.queryItem = queryItem;
  }
  
  public String getQueryText() {
    return this.queryText;
  }
  
  public void setQueryText(String queryText) {
    this.queryText = queryText;
  }
  
  public String getUsersId() {
    return this.usersId;
  }
  
  public void setUsersId(String usersId) {
    this.usersId = usersId;
  }
  
  public String getUsersName() {
    return this.usersName;
  }
  
  public void setUsersName(String usersName) {
    this.usersName = usersName;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.queryItem = null;
    this.queryText = null;
    this.usersId = null;
    this.usersName = null;
    this.postilContent = null;
    this.postilEmpName = null;
  }
  
  public String getPostilContent() {
    return this.postilContent;
  }
  
  public void setPostilContent(String postilContent) {
    this.postilContent = postilContent;
  }
  
  public String getPostilEmpName() {
    return this.postilEmpName;
  }
  
  public void setPostilEmpName(String postilEmpName) {
    this.postilEmpName = postilEmpName;
  }
  
  public String getReceiveRecordId() {
    return this.receiveRecordId;
  }
  
  public void setReceiveRecordId(String receiveRecordId) {
    this.receiveRecordId = receiveRecordId;
  }
  
  public String getQueryName() {
    return this.queryName;
  }
  
  public void setQueryName(String queryName) {
    this.queryName = queryName;
  }
  
  public String getReportType() {
    return this.reportType;
  }
  
  public void setReportType(String reportType) {
    this.reportType = reportType;
  }
  
  public String getNextWorkClew() {
    return this.nextWorkClew;
  }
  
  public void setNextWorkClew(String nextWorkClew) {
    this.nextWorkClew = nextWorkClew;
  }
  
  public String getGrade() {
    return this.grade;
  }
  
  public void setGrade(String grade) {
    this.grade = grade;
  }
  
  public String getPostilGrade() {
    return this.postilGrade;
  }
  
  public void setPostilGrade(String postilGrade) {
    this.postilGrade = postilGrade;
  }
  
  public String getPostilResult() {
    return this.postilResult;
  }
  
  public void setPostilResult(String postilResult) {
    this.postilResult = postilResult;
  }
  
  public String getReportDepart() {
    return this.reportDepart;
  }
  
  public void setReportDepart(String reportDepart) {
    this.reportDepart = reportDepart;
  }
  
  public String getReportDepartId() {
    return this.reportDepartId;
  }
  
  public void setReportDepartId(String reportDepartId) {
    this.reportDepartId = reportDepartId;
  }
  
  public String getReportEmpId() {
    return this.reportEmpId;
  }
  
  public void setReportEmpId(String reportEmpId) {
    this.reportEmpId = reportEmpId;
  }
  
  public String getReportJob() {
    return this.reportJob;
  }
  
  public void setReportJob(String reportJob) {
    this.reportJob = reportJob;
  }
  
  public String getSelYear() {
    return this.selYear;
  }
  
  public void setSelYear(String selYear) {
    this.selYear = selYear;
  }
  
  public String getReportReader() {
    return this.reportReader;
  }
  
  public void setReportReader(String reportReader) {
    this.reportReader = reportReader;
  }
  
  public String getReportReaderId() {
    return this.reportReaderId;
  }
  
  public void setReportReaderId(String reportReaderId) {
    this.reportReaderId = reportReaderId;
  }
  
  public String getTransReason() {
    return this.transReason;
  }
  
  public String getReportCourse() {
    return this.reportCourse;
  }
  
  public void setTransReason(String transReason) {
    this.transReason = transReason;
  }
  
  public void setReportCourse(String reportCourse) {
    this.reportCourse = reportCourse;
  }
}
