package com.js.oa.report.po;

import java.util.Date;
import java.util.Set;

public class ReportPO {
  private Long reportId;
  
  private String viewScopeId;
  
  private String viewScope;
  
  private String reportName;
  
  private String reportDescribe;
  
  private String grfName;
  
  private String createEmp;
  
  private Long createEmpId;
  
  private Date createDate = null;
  
  private String domainId;
  
  private String corpId;
  
  private Long createOrgId;
  
  private String reportSql;
  
  private String sourceBase;
  
  private String reportType;
  
  private String reportClass = "0";
  
  private String chartSql;
  
  private String reportUrl;
  
  private String phoneUrl;
  
  private Set<ReportReplacePO> replaceSet;
  
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
  
  public Long getReportId() {
    return this.reportId;
  }
  
  public void setReportId(Long reportId) {
    this.reportId = reportId;
  }
  
  public String getCreateEmp() {
    return this.createEmp;
  }
  
  public void setCreateEmp(String createEmp) {
    this.createEmp = createEmp;
  }
  
  public Long getCreateEmpId() {
    return this.createEmpId;
  }
  
  public void setCreateEmpId(Long createEmpId) {
    this.createEmpId = createEmpId;
  }
  
  public Date getCreateDate() {
    return this.createDate;
  }
  
  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
  
  public String getCorpId() {
    return this.corpId;
  }
  
  public void setCorpId(String corpId) {
    this.corpId = corpId;
  }
  
  public Long getCreateOrgId() {
    return this.createOrgId;
  }
  
  public void setCreateOrgId(Long createOrgId) {
    this.createOrgId = createOrgId;
  }
  
  public String getReportSql() {
    return this.reportSql;
  }
  
  public void setReportSql(String reportSql) {
    this.reportSql = reportSql;
  }
  
  public Set<ReportReplacePO> getReplaceSet() {
    return this.replaceSet;
  }
  
  public void setReplaceSet(Set<ReportReplacePO> replaceSet) {
    this.replaceSet = replaceSet;
  }
  
  public String getPhoneUrl() {
    return this.phoneUrl;
  }
  
  public void setPhoneUrl(String phoneUrl) {
    this.phoneUrl = phoneUrl;
  }
}
