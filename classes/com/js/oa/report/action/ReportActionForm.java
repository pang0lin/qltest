package com.js.oa.report.action;

import java.io.Serializable;
import org.apache.struts.action.ActionForm;

public class ReportActionForm extends ActionForm implements Serializable {
  private String viewScopeId;
  
  private String viewScope;
  
  private String reportName;
  
  private String reportDescribe;
  
  private String grfName;
  
  private String reportSql;
  
  private String sourceBase;
  
  private String reportType;
  
  private String reportClass = "0";
  
  private String chartSql;
  
  private String reportUrl;
  
  private String phoneUrl;
  
  public String getSourceBase() {
    return this.sourceBase;
  }
  
  public void setSourceBase(String sourceBase) {
    this.sourceBase = sourceBase;
  }
  
  public String getReportType() {
    return this.reportType;
  }
  
  public void setReportType(String reportType) {
    this.reportType = reportType;
  }
  
  public String getViewScopeId() {
    return this.viewScopeId;
  }
  
  public void setViewScopeId(String viewScopeId) {
    this.viewScopeId = viewScopeId;
  }
  
  public String getViewScope() {
    return this.viewScope;
  }
  
  public void setViewScope(String viewScope) {
    this.viewScope = viewScope;
  }
  
  public String getReportName() {
    return this.reportName;
  }
  
  public void setReportName(String reportName) {
    this.reportName = reportName;
  }
  
  public String getReportDescribe() {
    return this.reportDescribe;
  }
  
  public void setReportDescribe(String reportDescribe) {
    this.reportDescribe = reportDescribe;
  }
  
  public String getGrfName() {
    return this.grfName;
  }
  
  public void setGrfName(String grfName) {
    this.grfName = grfName;
  }
  
  public String getReportSql() {
    return this.reportSql;
  }
  
  public void setReportSql(String reportSql) {
    this.reportSql = reportSql;
  }
  
  public String getReportClass() {
    return this.reportClass;
  }
  
  public void setReportClass(String reportClass) {
    this.reportClass = reportClass;
  }
  
  public String getChartSql() {
    return this.chartSql;
  }
  
  public void setChartSql(String chartSql) {
    this.chartSql = chartSql;
  }
  
  public String getReportUrl() {
    return this.reportUrl;
  }
  
  public void setReportUrl(String reportUrl) {
    this.reportUrl = reportUrl;
  }
  
  public String getPhoneUrl() {
    return this.phoneUrl;
  }
  
  public void setPhoneUrl(String phoneUrl) {
    this.phoneUrl = phoneUrl;
  }
}
