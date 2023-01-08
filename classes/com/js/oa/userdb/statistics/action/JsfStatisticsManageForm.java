package com.js.oa.userdb.statistics.action;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import org.apache.struts.action.ActionForm;

public class JsfStatisticsManageForm extends ActionForm implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private Long statsId;
  
  private Long statsTableId;
  
  private String statsTitle;
  
  private String statsIndex;
  
  private String statsIndexName;
  
  private String statsType;
  
  private String statsGroupIndex;
  
  private String statsOrder;
  
  private String statsOrderNum;
  
  private Date statsBeginTime = null;
  
  private Date statsEndTime = null;
  
  private String statsChart;
  
  private String statsStatus;
  
  private String isExtendUrl;
  
  private String extendUrl;
  
  public JsfStatisticsManageForm() {}
  
  public JsfStatisticsManageForm(String statsTitle, String statsIndex, String statsIndexName, String statsType, String statsGroupIndex, String statsOrder, String statsOrderNum, Timestamp statsBeginTime, Timestamp statsEndTime, String statsChart, String statsStatus, String isExtendUrl, String extendUrl) {
    this.statsTitle = statsTitle;
    this.statsIndex = statsIndex;
    this.statsIndexName = statsIndexName;
    this.statsType = statsType;
    this.statsGroupIndex = statsGroupIndex;
    this.statsOrder = statsOrder;
    this.statsOrderNum = statsOrderNum;
    this.statsBeginTime = statsBeginTime;
    this.statsEndTime = statsEndTime;
    this.statsChart = statsChart;
    this.statsStatus = statsStatus;
    this.isExtendUrl = isExtendUrl;
    this.extendUrl = extendUrl;
  }
  
  public Long getStatsId() {
    return this.statsId;
  }
  
  public void setStatsId(Long statsId) {
    this.statsId = statsId;
  }
  
  public String getStatsTitle() {
    return this.statsTitle;
  }
  
  public void setStatsTitle(String statsTitle) {
    this.statsTitle = statsTitle;
  }
  
  public String getStatsIndex() {
    return this.statsIndex;
  }
  
  public void setStatsIndex(String statsIndex) {
    this.statsIndex = statsIndex;
  }
  
  public String getStatsIndexName() {
    return this.statsIndexName;
  }
  
  public Long getStatsTableId() {
    return this.statsTableId;
  }
  
  public void setStatsTableId(Long statsTableId) {
    this.statsTableId = statsTableId;
  }
  
  public void setStatsIndexName(String statsIndexName) {
    this.statsIndexName = statsIndexName;
  }
  
  public String getStatsType() {
    return this.statsType;
  }
  
  public void setStatsType(String statsType) {
    this.statsType = statsType;
  }
  
  public String getStatsGroupIndex() {
    return this.statsGroupIndex;
  }
  
  public void setStatsGroupIndex(String statsGroupIndex) {
    this.statsGroupIndex = statsGroupIndex;
  }
  
  public String getStatsOrder() {
    return this.statsOrder;
  }
  
  public void setStatsOrder(String statsOrder) {
    this.statsOrder = statsOrder;
  }
  
  public String getStatsOrderNum() {
    return this.statsOrderNum;
  }
  
  public void setStatsOrderNum(String statsOrderNum) {
    this.statsOrderNum = statsOrderNum;
  }
  
  public Date getStatsBeginTime() {
    return this.statsBeginTime;
  }
  
  public void setStatsBeginTime(Date statsBeginTime) {
    this.statsBeginTime = statsBeginTime;
  }
  
  public Date getStatsEndTime() {
    return this.statsEndTime;
  }
  
  public void setStatsEndTime(Date statsEndTime) {
    this.statsEndTime = statsEndTime;
  }
  
  public String getStatsChart() {
    return this.statsChart;
  }
  
  public void setStatsChart(String statsChart) {
    this.statsChart = statsChart;
  }
  
  public String getStatsStatus() {
    return this.statsStatus;
  }
  
  public void setStatsStatus(String statsStatus) {
    this.statsStatus = statsStatus;
  }
  
  public String getIsExtendUrl() {
    return this.isExtendUrl;
  }
  
  public void setIsExtendUrl(String isExtendUrl) {
    this.isExtendUrl = isExtendUrl;
  }
  
  public String getExtendUrl() {
    return this.extendUrl;
  }
  
  public void setExtendUrl(String extendUrl) {
    this.extendUrl = extendUrl;
  }
}
