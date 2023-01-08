package com.js.oa.oasysremind.action;

import java.util.Date;
import org.apache.struts.action.ActionForm;

public class OASysRemindActionForm extends ActionForm {
  private static final long serialVersionUID = 1L;
  
  private Long remindId;
  
  private String remindSourceType;
  
  private String remindTableId;
  
  private String remindTableName;
  
  private String remindTableCode;
  
  private String remindType;
  
  private String remindIndex;
  
  private String remindIndexName;
  
  private String compareType;
  
  private String compareValue;
  
  private String remindTimeUnit;
  
  private String remindObjId;
  
  private String remindObjName;
  
  private Date effectiveBegin = null;
  
  private Date effectiveEnd = null;
  
  private String remindTitle;
  
  private String remindUrlUser;
  
  private String remindUrlSys;
  
  private String createEmp;
  
  private String createOrg;
  
  private String filterStr;
  
  private String onTimeMode;
  
  private String remindWeek;
  
  private String remindMonthDay;
  
  private String remindYearMonth;
  
  private String remindYearMonthDay;
  
  private String remindDefQueryCondition;
  
  private String remindDefQueryOrder;
  
  private String remindTypeClass;
  
  private String remindCoun;
  
  public OASysRemindActionForm() {}
  
  public OASysRemindActionForm(String remindTableId, String remindTableName, String remindType, String remindIndexName, String compareType, String compareValue, String filterValue, String remindObjId, String remindObjName, Date effectiveBegin, Date effectiveEnd, String remindTitle, String remindUrl, String remindTypeClass, String remindCoun) {
    this.remindTableId = remindTableId;
    this.remindTableName = remindTableName;
    this.remindType = remindType;
    this.remindIndexName = remindIndexName;
    this.compareType = compareType;
    this.compareValue = compareValue;
    this.remindObjId = remindObjId;
    this.remindObjName = remindObjName;
    this.effectiveBegin = effectiveBegin;
    this.effectiveEnd = effectiveEnd;
    this.remindTitle = remindTitle;
    this.remindTypeClass = remindTypeClass;
    this.remindCoun = remindCoun;
  }
  
  public String getRemindIndex() {
    return this.remindIndex;
  }
  
  public void setRemindIndex(String remindIndex) {
    this.remindIndex = remindIndex;
  }
  
  public Long getRemindId() {
    return this.remindId;
  }
  
  public String getFilterStr() {
    return this.filterStr;
  }
  
  public void setFilterStr(String filterStr) {
    this.filterStr = filterStr;
  }
  
  public void setRemindId(Long remindId) {
    this.remindId = remindId;
  }
  
  public String getRemindTableCode() {
    return this.remindTableCode;
  }
  
  public void setRemindTableCode(String remindTableCode) {
    this.remindTableCode = remindTableCode;
  }
  
  public String getRemindTableId() {
    return this.remindTableId;
  }
  
  public void setRemindTableId(String remindTableId) {
    this.remindTableId = remindTableId;
  }
  
  public String getRemindTableName() {
    return this.remindTableName;
  }
  
  public void setRemindTableName(String remindTableName) {
    this.remindTableName = remindTableName;
  }
  
  public String getRemindSourceType() {
    return this.remindSourceType;
  }
  
  public void setRemindSourceType(String remindSourceType) {
    this.remindSourceType = remindSourceType;
  }
  
  public String getRemindType() {
    return this.remindType;
  }
  
  public void setRemindType(String remindType) {
    this.remindType = remindType;
  }
  
  public String getRemindIndexName() {
    return this.remindIndexName;
  }
  
  public void setRemindIndexName(String remindIndexName) {
    this.remindIndexName = remindIndexName;
  }
  
  public String getCompareType() {
    return this.compareType;
  }
  
  public void setCompareType(String compareType) {
    this.compareType = compareType;
  }
  
  public String getCompareValue() {
    return this.compareValue;
  }
  
  public void setCompareValue(String compareValue) {
    this.compareValue = compareValue;
  }
  
  public String getRemindObjId() {
    return this.remindObjId;
  }
  
  public void setRemindObjId(String remindObjId) {
    this.remindObjId = remindObjId;
  }
  
  public String getRemindObjName() {
    return this.remindObjName;
  }
  
  public void setRemindObjName(String remindObjName) {
    this.remindObjName = remindObjName;
  }
  
  public Date getEffectiveBegin() {
    return this.effectiveBegin;
  }
  
  public void setEffectiveBegin(Date effectiveBegin) {
    this.effectiveBegin = effectiveBegin;
  }
  
  public Date getEffectiveEnd() {
    return this.effectiveEnd;
  }
  
  public void setEffectiveEnd(Date effectiveEnd) {
    this.effectiveEnd = effectiveEnd;
  }
  
  public String getRemindTitle() {
    return this.remindTitle;
  }
  
  public void setRemindTitle(String remindTitle) {
    this.remindTitle = remindTitle;
  }
  
  public String getRemindUrlUser() {
    return this.remindUrlUser;
  }
  
  public void setRemindUrlUser(String remindUrlUser) {
    this.remindUrlUser = remindUrlUser;
  }
  
  public String getRemindUrlSys() {
    return this.remindUrlSys;
  }
  
  public void setRemindUrlSys(String remindUrlSys) {
    this.remindUrlSys = remindUrlSys;
  }
  
  public String getCreateEmp() {
    return this.createEmp;
  }
  
  public void setCreateEmp(String createEmp) {
    this.createEmp = createEmp;
  }
  
  public String getCreateOrg() {
    return this.createOrg;
  }
  
  public void setCreateOrg(String createOrg) {
    this.createOrg = createOrg;
  }
  
  public String getRemindTimeUnit() {
    return this.remindTimeUnit;
  }
  
  public void setRemindTimeUnit(String remindTimeUnit) {
    this.remindTimeUnit = remindTimeUnit;
  }
  
  public String getOnTimeMode() {
    return this.onTimeMode;
  }
  
  public void setOnTimeMode(String onTimeMode) {
    this.onTimeMode = onTimeMode;
  }
  
  public String getRemindWeek() {
    return this.remindWeek;
  }
  
  public void setRemindWeek(String remindWeek) {
    this.remindWeek = remindWeek;
  }
  
  public String getRemindMonthDay() {
    return this.remindMonthDay;
  }
  
  public void setRemindMonthDay(String remindMonthDay) {
    this.remindMonthDay = remindMonthDay;
  }
  
  public String getRemindYearMonth() {
    return this.remindYearMonth;
  }
  
  public void setRemindYearMonth(String remindYearMonth) {
    this.remindYearMonth = remindYearMonth;
  }
  
  public String getRemindYearMonthDay() {
    return this.remindYearMonthDay;
  }
  
  public void setRemindYearMonthDay(String remindYearMonthDay) {
    this.remindYearMonthDay = remindYearMonthDay;
  }
  
  public String getRemindDefQueryCondition() {
    return this.remindDefQueryCondition;
  }
  
  public void setRemindDefQueryCondition(String remindDefQueryCondition) {
    this.remindDefQueryCondition = remindDefQueryCondition;
  }
  
  public String getRemindDefQueryOrder() {
    return this.remindDefQueryOrder;
  }
  
  public void setRemindDefQueryOrder(String remindDefQueryOrder) {
    this.remindDefQueryOrder = remindDefQueryOrder;
  }
  
  public String getRemindTypeClass() {
    return this.remindTypeClass;
  }
  
  public void setRemindTypeClass(String remindTypeClass) {
    this.remindTypeClass = remindTypeClass;
  }
  
  public String getRemindCoun() {
    return this.remindCoun;
  }
  
  public void setRemindCoun(String remindCoun) {
    this.remindCoun = remindCoun;
  }
}
