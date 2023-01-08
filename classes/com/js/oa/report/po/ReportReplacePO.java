package com.js.oa.report.po;

public class ReportReplacePO {
  private Long replaceId;
  
  private String beReplaceName;
  
  private String beReplaceString;
  
  private String replaceString;
  
  private String sqlType;
  
  private ReportPO report;
  
  public Long getReplaceId() {
    return this.replaceId;
  }
  
  public void setReplaceId(Long replaceId) {
    this.replaceId = replaceId;
  }
  
  public String getBeReplaceName() {
    return this.beReplaceName;
  }
  
  public void setBeReplaceName(String beReplaceName) {
    this.beReplaceName = beReplaceName;
  }
  
  public String getBeReplaceString() {
    return this.beReplaceString;
  }
  
  public void setBeReplaceString(String beReplaceString) {
    this.beReplaceString = beReplaceString;
  }
  
  public String getReplaceString() {
    return this.replaceString;
  }
  
  public void setReplaceString(String replaceString) {
    this.replaceString = replaceString;
  }
  
  public String getSqlType() {
    return this.sqlType;
  }
  
  public void setSqlType(String sqlType) {
    this.sqlType = sqlType;
  }
  
  public ReportPO getReport() {
    return this.report;
  }
  
  public void setReport(ReportPO report) {
    this.report = report;
  }
}
