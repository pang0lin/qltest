package com.js.oa.scheme.workreport.action;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class WorkReportActionForm extends ActionForm {
  private String nextReport;
  
  private String previousReport;
  
  private String reportReader;
  
  private String reportReaderId;
  
  private String reportRemark;
  
  private String reportTime;
  
  private String reportType;
  
  private String templateId;
  
  private String[] accessoryName;
  
  private String[] accessorySaveName;
  
  private String queryText;
  
  private String queryItem;
  
  private String accessoryTmpName;
  
  private String accessorySaveTmpName;
  
  private Date reportTime1 = null;
  
  private String reportCourse;
  
  private String reportJob;
  
  private String reportDepart;
  
  private String startDate;
  
  private String endDate;
  
  private String reportEmpName;
  
  private String reportEmpId;
  
  private String reportDepartId;
  
  private String selYear;
  
  private String sendType;
  
  private String reportName;
  
  private String editId;
  
  public String getEditId() {
    return this.editId;
  }
  
  public void setEditId(String editId) {
    this.editId = editId;
  }
  
  public String getReportName() {
    return this.reportName;
  }
  
  public void setReportName(String reportName) {
    this.reportName = reportName;
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
  
  public String getNextReport() {
    return this.nextReport;
  }
  
  public void setNextReport(String nextReport) {
    this.nextReport = nextReport;
  }
  
  public String getPreviousReport() {
    return this.previousReport;
  }
  
  public void setPreviousReport(String previousReport) {
    this.previousReport = previousReport;
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
  
  public String getReportRemark() {
    return this.reportRemark;
  }
  
  public void setReportRemark(String reportRemark) {
    this.reportRemark = reportRemark;
  }
  
  public String getReportTime() {
    return this.reportTime;
  }
  
  public void setReportTime(String reportTime) {
    this.reportTime = reportTime;
  }
  
  public String getReportType() {
    return this.reportType;
  }
  
  public void setReportType(String reportType) {
    this.reportType = reportType;
  }
  
  public String getTemplateId() {
    return this.templateId;
  }
  
  public void setTemplateId(String templateId) {
    this.templateId = templateId;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.accessoryName = null;
    this.accessorySaveName = null;
    this.nextReport = null;
    this.previousReport = null;
    this.reportReader = null;
    this.reportReaderId = null;
    this.reportRemark = null;
    this.reportTime = null;
    this.reportType = null;
    this.templateId = null;
  }
  
  public String getQueryText() {
    return this.queryText;
  }
  
  public void setQueryText(String queryText) {
    this.queryText = queryText;
  }
  
  public String getQueryItem() {
    return this.queryItem;
  }
  
  public void setQueryItem(String queryItem) {
    this.queryItem = queryItem;
  }
  
  public String getAccessoryTmpName() {
    return this.accessoryTmpName;
  }
  
  public void setAccessoryTmpName(String accessoryTmpName) {
    this.accessoryTmpName = accessoryTmpName;
  }
  
  public String getAccessorySaveTmpName() {
    return this.accessorySaveTmpName;
  }
  
  public void setAccessorySaveTmpName(String accessorySaveTmpName) {
    this.accessorySaveTmpName = accessorySaveTmpName;
  }
  
  public Date getReportTime1() {
    return this.reportTime1;
  }
  
  public void setReportTime1(Date reportTime1) {
    this.reportTime1 = reportTime1;
  }
  
  public String getReportCourse() {
    return this.reportCourse;
  }
  
  public void setReportCourse(String reportCourse) {
    this.reportCourse = reportCourse;
  }
  
  public String getReportDepart() {
    return this.reportDepart;
  }
  
  public void setReportDepart(String reportDepart) {
    this.reportDepart = reportDepart;
  }
  
  public String getReportJob() {
    return this.reportJob;
  }
  
  public void setReportJob(String reportJob) {
    this.reportJob = reportJob;
  }
  
  public String getEndDate() {
    return this.endDate;
  }
  
  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }
  
  public String getStartDate() {
    return this.startDate;
  }
  
  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }
  
  public String getReportEmpName() {
    return this.reportEmpName;
  }
  
  public void setReportEmpName(String reportEmpName) {
    this.reportEmpName = reportEmpName;
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
  
  public String getSelYear() {
    return this.selYear;
  }
  
  public void setSelYear(String selYear) {
    this.selYear = selYear;
  }
  
  public String getSendType() {
    return this.sendType;
  }
  
  public void setSendType(String sendType) {
    this.sendType = sendType;
  }
}
