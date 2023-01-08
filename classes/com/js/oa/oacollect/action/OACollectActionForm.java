package com.js.oa.oacollect.action;

import java.io.Serializable;
import java.util.Date;
import org.apache.struts.action.ActionForm;

public class OACollectActionForm extends ActionForm implements Serializable {
  private Long collectId;
  
  private String collectTitle;
  
  private String collectTTable;
  
  private String collectTPage;
  
  private String collectTProcess;
  
  private String collectTTableName;
  
  private String collectTProcessName;
  
  private String collectOperationType;
  
  private String collectRecordOperation;
  
  private String collectOperationimg;
  
  private String collectOperationcomponert;
  
  private String collectOpenStyle;
  
  private String collectRelationRefFlow;
  
  private String collectQueryelements;
  
  private String collectDisplayelements;
  
  private String collectDefquerycondition;
  
  private String collectDefqueryorder;
  
  private Long collectMantblSubtbl;
  
  private String collectMantblSubtblname;
  
  private Long collectStatus;
  
  private Long collectEnable;
  
  private Date collectBeginTime = null;
  
  private Date collectEndTime = null;
  
  private String createEmp;
  
  private String createOrg;
  
  private String collectEmps;
  
  private String collectEmpNames;
  
  private String queryCaseName;
  
  private String listCaseName;
  
  private String queryCasesSel;
  
  private String listCasesSel;
  
  private Long isMultiCollect;
  
  private Long ifRemind;
  
  private Long collectRemindDays;
  
  private Long categoryId;
  
  private Long collectZl;
  
  private Long ifRemindPerDay;
  
  private String collectRemindTime;
  
  public OACollectActionForm() {}
  
  public OACollectActionForm(String collectTitle, String collectTTable, String collectTPage, String collectTProcess, Long collectStatus, Long collectEnable, Date collectBeginTime, Date collectEndTime, String createEmp, String createOrg) {
    this.collectTitle = collectTitle;
    this.collectTTable = collectTTable;
    this.collectTPage = collectTPage;
    this.collectTProcess = collectTProcess;
    this.collectStatus = collectStatus;
    this.collectEnable = collectEnable;
    this.collectBeginTime = collectBeginTime;
    this.collectEndTime = collectEndTime;
    this.createEmp = createEmp;
    this.createOrg = createOrg;
  }
  
  public Long getCollectId() {
    return this.collectId;
  }
  
  public Long getCollectZl() {
    return this.collectZl;
  }
  
  public void setCollectZl(Long collectZl) {
    this.collectZl = collectZl;
  }
  
  public Long getIfRemindPerDay() {
    return this.ifRemindPerDay;
  }
  
  public void setIfRemindPerDay(Long ifRemindPerDay) {
    this.ifRemindPerDay = ifRemindPerDay;
  }
  
  public String getCollectRemindTime() {
    return this.collectRemindTime;
  }
  
  public void setCollectRemindTime(String collectRemindTime) {
    this.collectRemindTime = collectRemindTime;
  }
  
  public Long getCategoryId() {
    return this.categoryId;
  }
  
  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }
  
  public Long getIfRemind() {
    return this.ifRemind;
  }
  
  public void setIfRemind(Long ifRemind) {
    this.ifRemind = ifRemind;
  }
  
  public Long getCollectRemindDays() {
    return this.collectRemindDays;
  }
  
  public void setCollectRemindDays(Long collectRemindDays) {
    this.collectRemindDays = collectRemindDays;
  }
  
  public void setCollectId(Long collectId) {
    this.collectId = collectId;
  }
  
  public Long getIsMultiCollect() {
    return this.isMultiCollect;
  }
  
  public void setIsMultiCollect(Long isMultiCollect) {
    this.isMultiCollect = isMultiCollect;
  }
  
  public String getQueryCaseName() {
    return this.queryCaseName;
  }
  
  public void setQueryCaseName(String queryCaseName) {
    this.queryCaseName = queryCaseName;
  }
  
  public String getCollectTitle() {
    return this.collectTitle;
  }
  
  public String getListCaseName() {
    return this.listCaseName;
  }
  
  public void setListCaseName(String listCaseName) {
    this.listCaseName = listCaseName;
  }
  
  public String getCollectEmps() {
    return this.collectEmps;
  }
  
  public void setCollectEmps(String collectEmps) {
    this.collectEmps = collectEmps;
  }
  
  public String getCollectEmpNames() {
    return this.collectEmpNames;
  }
  
  public void setCollectEmpNames(String collectEmpNames) {
    this.collectEmpNames = collectEmpNames;
  }
  
  public void setCollectTitle(String collectTitle) {
    this.collectTitle = collectTitle;
  }
  
  public String getQueryCasesSel() {
    return this.queryCasesSel;
  }
  
  public void setQueryCasesSel(String queryCasesSel) {
    this.queryCasesSel = queryCasesSel;
  }
  
  public String getListCasesSel() {
    return this.listCasesSel;
  }
  
  public void setListCasesSel(String listCasesSel) {
    this.listCasesSel = listCasesSel;
  }
  
  public String getCollectTTable() {
    return this.collectTTable;
  }
  
  public void setCollectTTable(String collectTTable) {
    this.collectTTable = collectTTable;
  }
  
  public String getCollectTTableName() {
    return this.collectTTableName;
  }
  
  public void setCollectTTableName(String collectTTableName) {
    this.collectTTableName = collectTTableName;
  }
  
  public String getCollectTProcessName() {
    return this.collectTProcessName;
  }
  
  public void setCollectTProcessName(String collectTProcessName) {
    this.collectTProcessName = collectTProcessName;
  }
  
  public String getCollectOperationType() {
    return this.collectOperationType;
  }
  
  public void setCollectOperationType(String collectOperationType) {
    this.collectOperationType = collectOperationType;
  }
  
  public String getCollectRecordOperation() {
    return this.collectRecordOperation;
  }
  
  public void setCollectRecordOperation(String collectRecordOperation) {
    this.collectRecordOperation = collectRecordOperation;
  }
  
  public String getCollectOperationimg() {
    return this.collectOperationimg;
  }
  
  public void setCollectOperationimg(String collectOperationimg) {
    this.collectOperationimg = collectOperationimg;
  }
  
  public String getCollectOperationcomponert() {
    return this.collectOperationcomponert;
  }
  
  public void setCollectOperationcomponert(String collectOperationcomponert) {
    this.collectOperationcomponert = collectOperationcomponert;
  }
  
  public String getCollectOpenStyle() {
    return this.collectOpenStyle;
  }
  
  public void setCollectOpenStyle(String collectOpenStyle) {
    this.collectOpenStyle = collectOpenStyle;
  }
  
  public String getCollectRelationRefFlow() {
    return this.collectRelationRefFlow;
  }
  
  public void setCollectRelationRefFlow(String collectRelationRefFlow) {
    this.collectRelationRefFlow = collectRelationRefFlow;
  }
  
  public String getCollectQueryelements() {
    return this.collectQueryelements;
  }
  
  public void setCollectQueryelements(String collectQueryelements) {
    this.collectQueryelements = collectQueryelements;
  }
  
  public String getCollectDisplayelements() {
    return this.collectDisplayelements;
  }
  
  public void setCollectDisplayelements(String collectDisplayelements) {
    this.collectDisplayelements = collectDisplayelements;
  }
  
  public String getCollectDefquerycondition() {
    return this.collectDefquerycondition;
  }
  
  public void setCollectDefquerycondition(String collectDefquerycondition) {
    this.collectDefquerycondition = collectDefquerycondition;
  }
  
  public String getCollectDefqueryorder() {
    return this.collectDefqueryorder;
  }
  
  public void setCollectDefqueryorder(String collectDefqueryorder) {
    this.collectDefqueryorder = collectDefqueryorder;
  }
  
  public Long getCollectMantblSubtbl() {
    return this.collectMantblSubtbl;
  }
  
  public void setCollectMantblSubtbl(Long collectMantblSubtbl) {
    this.collectMantblSubtbl = collectMantblSubtbl;
  }
  
  public String getCollectMantblSubtblname() {
    return this.collectMantblSubtblname;
  }
  
  public void setCollectMantblSubtblname(String collectMantblSubtblname) {
    this.collectMantblSubtblname = collectMantblSubtblname;
  }
  
  public String getCollectTPage() {
    return this.collectTPage;
  }
  
  public void setCollectTPage(String collectTPage) {
    this.collectTPage = collectTPage;
  }
  
  public String getCollectTProcess() {
    return this.collectTProcess;
  }
  
  public void setCollectTProcess(String collectTProcess) {
    this.collectTProcess = collectTProcess;
  }
  
  public Long getCollectStatus() {
    return this.collectStatus;
  }
  
  public void setCollectStatus(Long collectStatus) {
    this.collectStatus = collectStatus;
  }
  
  public Long getCollectEnable() {
    return this.collectEnable;
  }
  
  public void setCollectEnable(Long collectEnable) {
    this.collectEnable = collectEnable;
  }
  
  public Date getCollectBeginTime() {
    return this.collectBeginTime;
  }
  
  public void setCollectBeginTime(Date collectBeginTime) {
    this.collectBeginTime = collectBeginTime;
  }
  
  public Date getCollectEndTime() {
    return this.collectEndTime;
  }
  
  public void setCollectEndTime(Date collectEndTime) {
    this.collectEndTime = collectEndTime;
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
}
