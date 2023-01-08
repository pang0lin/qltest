package com.js.oa.jsflow.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class WFWorkPO implements Serializable {
  private Long wfWorkId;
  
  private Long wfCurEmployeeId;
  
  private Integer workStatus;
  
  private String workFileType;
  
  private String workCurStep;
  
  private String workTitle;
  
  private String workLeftLinkFile;
  
  private String workMainLinkFile;
  
  private Integer workListControl;
  
  private Long workActivity;
  
  private String workSubmitPerson;
  
  private Date workSubmitTime = null;
  
  private Long wfSubmitEmployeeId;
  
  private Integer workReadMarker;
  
  private Integer workType;
  
  private Long workProcessId;
  
  private Long workTableId;
  
  private Long workRecordId;
  
  private Long workDeadLine;
  
  private Long workPressTime;
  
  private Date workCreateDate = null;
  
  private Integer workStartFlag;
  
  private Date workDoneWithDate = null;
  
  private Integer workAllowCancel;
  
  private String workCancelReason;
  
  private Integer workDelete;
  
  private Integer workIsTran;
  
  private String workUser;
  
  private Integer workStepCount;
  
  private String creatorCancelLink;
  
  private String isStandForWork;
  
  private Long standForUserId;
  
  private String standForUserName;
  
  private Long initActivity;
  
  private Integer initStepCount;
  
  private String submitOrg;
  
  private Integer printNum;
  
  private String emergence;
  
  private String transActType;
  
  private String activityDelaySend = "";
  
  private String initActivityName;
  
  private Date workDeadlineDate = null;
  
  private Date workDeadlinePressDate = null;
  
  private String tranType;
  
  private String tranFromPersonId;
  
  private Date processDeadlineDate = null;
  
  private String wfCurEmployeeOrgId;
  
  private String isSubProcWork;
  
  private String pareProcActiId;
  
  private String pareStepCount;
  
  private String pareTableId;
  
  private String pareRecordId;
  
  private String pareProcNextActiId;
  
  private Long domainId;
  
  private Long relProjectId;
  
  private String workHangup;
  
  private Long stickie = Long.valueOf(0L);
  
  public Long getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }
  
  public Long getWfWorkId() {
    return this.wfWorkId;
  }
  
  public void setWfWorkId(Long wfWorkId) {
    this.wfWorkId = wfWorkId;
  }
  
  public Long getWfCurEmployeeId() {
    return this.wfCurEmployeeId;
  }
  
  public void setWfCurEmployeeId(Long wfCurEmployeeId) {
    this.wfCurEmployeeId = wfCurEmployeeId;
  }
  
  public Integer getWorkStatus() {
    return this.workStatus;
  }
  
  public void setWorkStatus(Integer workStatus) {
    this.workStatus = workStatus;
  }
  
  public String getWorkFileType() {
    return this.workFileType;
  }
  
  public void setWorkFileType(String workFileType) {
    this.workFileType = workFileType;
  }
  
  public String getWorkCurStep() {
    return this.workCurStep;
  }
  
  public void setWorkCurStep(String workCurStep) {
    this.workCurStep = workCurStep;
  }
  
  public String getWorkTitle() {
    return this.workTitle;
  }
  
  public void setWorkTitle(String workTitle) {
    this.workTitle = workTitle;
  }
  
  public String getWorkLeftLinkFile() {
    return this.workLeftLinkFile;
  }
  
  public void setWorkLeftLinkFile(String workLeftLinkFile) {
    this.workLeftLinkFile = workLeftLinkFile;
  }
  
  public String getWorkMainLinkFile() {
    return this.workMainLinkFile;
  }
  
  public void setWorkMainLinkFile(String workMainLinkFile) {
    this.workMainLinkFile = workMainLinkFile;
  }
  
  public Integer getWorkListControl() {
    return this.workListControl;
  }
  
  public void setWorkListControl(Integer workListControl) {
    this.workListControl = workListControl;
  }
  
  public Long getWorkActivity() {
    return this.workActivity;
  }
  
  public void setWorkActivity(Long workActivity) {
    this.workActivity = workActivity;
  }
  
  public String getWorkSubmitPerson() {
    return this.workSubmitPerson;
  }
  
  public void setWorkSubmitPerson(String workSubmitPerson) {
    this.workSubmitPerson = workSubmitPerson;
  }
  
  public Date getWorkSubmitTime() {
    return this.workSubmitTime;
  }
  
  public void setWorkSubmitTime(Date workSubmitTime) {
    this.workSubmitTime = workSubmitTime;
  }
  
  public Long getWfSubmitEmployeeId() {
    return this.wfSubmitEmployeeId;
  }
  
  public void setWfSubmitEmployeeId(Long wfSubmitEmployeeId) {
    this.wfSubmitEmployeeId = wfSubmitEmployeeId;
  }
  
  public Integer getWorkReadMarker() {
    return this.workReadMarker;
  }
  
  public void setWorkReadMarker(Integer workReadMarker) {
    this.workReadMarker = workReadMarker;
  }
  
  public Integer getWorkType() {
    return this.workType;
  }
  
  public void setWorkType(Integer workType) {
    this.workType = workType;
  }
  
  public Long getWorkProcessId() {
    return this.workProcessId;
  }
  
  public void setWorkProcessId(Long workProcessId) {
    this.workProcessId = workProcessId;
  }
  
  public Long getWorkTableId() {
    return this.workTableId;
  }
  
  public void setWorkTableId(Long workTableId) {
    this.workTableId = workTableId;
  }
  
  public Long getWorkRecordId() {
    return this.workRecordId;
  }
  
  public void setWorkRecordId(Long workRecordId) {
    this.workRecordId = workRecordId;
  }
  
  public Long getWorkDeadLine() {
    return this.workDeadLine;
  }
  
  public void setWorkDeadLine(Long workDeadLine) {
    this.workDeadLine = workDeadLine;
  }
  
  public Long getWorkPressTime() {
    return this.workPressTime;
  }
  
  public void setWorkPressTime(Long workPressTime) {
    this.workPressTime = workPressTime;
  }
  
  public Date getWorkCreateDate() {
    return this.workCreateDate;
  }
  
  public void setWorkCreateDate(Date workCreateDate) {
    this.workCreateDate = workCreateDate;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof WFWorkPO))
      return false; 
    WFWorkPO castOther = (WFWorkPO)other;
    return (new EqualsBuilder())
      .append(getWfWorkId(), castOther.getWfWorkId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getWfWorkId())
      .toHashCode();
  }
  
  public Integer getWorkStartFlag() {
    return this.workStartFlag;
  }
  
  public void setWorkStartFlag(Integer workStartFlag) {
    this.workStartFlag = workStartFlag;
  }
  
  public Date getWorkDoneWithDate() {
    return this.workDoneWithDate;
  }
  
  public void setWorkDoneWithDate(Date workDoneWithDate) {
    this.workDoneWithDate = workDoneWithDate;
  }
  
  public Integer getWorkAllowCancel() {
    return this.workAllowCancel;
  }
  
  public void setWorkAllowCancel(Integer workAllowCancel) {
    this.workAllowCancel = workAllowCancel;
  }
  
  public String getWorkCancelReason() {
    return this.workCancelReason;
  }
  
  public void setWorkCancelReason(String workCancelReason) {
    this.workCancelReason = workCancelReason;
  }
  
  public Integer getWorkDelete() {
    return this.workDelete;
  }
  
  public void setWorkDelete(Integer workDelete) {
    this.workDelete = workDelete;
  }
  
  public Integer getWorkIsTran() {
    return this.workIsTran;
  }
  
  public void setWorkIsTran(Integer workIsTran) {
    this.workIsTran = workIsTran;
  }
  
  public String getWorkUser() {
    return this.workUser;
  }
  
  public void setWorkUser(String workUser) {
    this.workUser = workUser;
  }
  
  public Integer getWorkStepCount() {
    return this.workStepCount;
  }
  
  public void setWorkStepCount(Integer workStepCount) {
    this.workStepCount = workStepCount;
  }
  
  public String getCreatorCancelLink() {
    return this.creatorCancelLink;
  }
  
  public void setCreatorCancelLink(String creatorCancelLink) {
    this.creatorCancelLink = creatorCancelLink;
  }
  
  public String getIsStandForWork() {
    return this.isStandForWork;
  }
  
  public void setIsStandForWork(String isStandForWork) {
    this.isStandForWork = isStandForWork;
  }
  
  public Long getStandForUserId() {
    return this.standForUserId;
  }
  
  public void setStandForUserId(Long standForUserId) {
    this.standForUserId = standForUserId;
  }
  
  public String getStandForUserName() {
    return this.standForUserName;
  }
  
  public void setStandForUserName(String standForUserName) {
    this.standForUserName = standForUserName;
  }
  
  public Long getInitActivity() {
    return this.initActivity;
  }
  
  public void setInitActivity(Long initActivity) {
    this.initActivity = initActivity;
  }
  
  public Integer getInitStepCount() {
    return this.initStepCount;
  }
  
  public void setInitStepCount(Integer initStepCount) {
    this.initStepCount = initStepCount;
  }
  
  public String getSubmitOrg() {
    return this.submitOrg;
  }
  
  public void setSubmitOrg(String submitOrg) {
    this.submitOrg = submitOrg;
  }
  
  public Integer getPrintNum() {
    return this.printNum;
  }
  
  public void setPrintNum(Integer printNum) {
    this.printNum = printNum;
  }
  
  public String getEmergence() {
    return this.emergence;
  }
  
  public void setEmergence(String emergence) {
    this.emergence = emergence;
  }
  
  public String getInitActivityName() {
    return this.initActivityName;
  }
  
  public void setInitActivityName(String initActivityName) {
    this.initActivityName = initActivityName;
  }
  
  public Date getWorkDeadlineDate() {
    return this.workDeadlineDate;
  }
  
  public void setWorkDeadlineDate(Date workDeadlineDate) {
    this.workDeadlineDate = workDeadlineDate;
  }
  
  public Date getWorkDeadlinePressDate() {
    return this.workDeadlinePressDate;
  }
  
  public void setWorkDeadlinePressDate(Date workDeadlinePressDate) {
    this.workDeadlinePressDate = workDeadlinePressDate;
  }
  
  public String getTranType() {
    return this.tranType;
  }
  
  public void setTranType(String tranType) {
    this.tranType = tranType;
  }
  
  public String getTranFromPersonId() {
    return this.tranFromPersonId;
  }
  
  public void setTranFromPersonId(String tranFromPersonId) {
    this.tranFromPersonId = tranFromPersonId;
  }
  
  public Date getProcessDeadlineDate() {
    return this.processDeadlineDate;
  }
  
  public void setProcessDeadlineDate(Date processDeadlineDate) {
    this.processDeadlineDate = processDeadlineDate;
  }
  
  public String getWfCurEmployeeOrgId() {
    return this.wfCurEmployeeOrgId;
  }
  
  public void setWfCurEmployeeOrgId(String wfCurEmployeeOrgId) {
    this.wfCurEmployeeOrgId = wfCurEmployeeOrgId;
  }
  
  public Long getRelProjectId() {
    return this.relProjectId;
  }
  
  public void setRelProjectId(Long relProjectId) {
    this.relProjectId = relProjectId;
  }
  
  public String getIsSubProcWork() {
    return this.isSubProcWork;
  }
  
  public void setIsSubProcWork(String isSubProcWork) {
    this.isSubProcWork = isSubProcWork;
  }
  
  public String getPareProcActiId() {
    return this.pareProcActiId;
  }
  
  public void setPareProcActiId(String pareProcActiId) {
    this.pareProcActiId = pareProcActiId;
  }
  
  public String getPareStepCount() {
    return this.pareStepCount;
  }
  
  public void setPareStepCount(String pareStepCount) {
    this.pareStepCount = pareStepCount;
  }
  
  public String getPareTableId() {
    return this.pareTableId;
  }
  
  public void setPareTableId(String pareTableId) {
    this.pareTableId = pareTableId;
  }
  
  public String getPareRecordId() {
    return this.pareRecordId;
  }
  
  public void setPareRecordId(String pareRecordId) {
    this.pareRecordId = pareRecordId;
  }
  
  public String getPareProcNextActiId() {
    return this.pareProcNextActiId;
  }
  
  public void setPareProcNextActiId(String pareProcNextActiId) {
    this.pareProcNextActiId = pareProcNextActiId;
  }
  
  public String getTransActType() {
    return this.transActType;
  }
  
  public void setTransActType(String transActType) {
    this.transActType = transActType;
  }
  
  public String getWorkHangup() {
    return this.workHangup;
  }
  
  public void setWorkHangup(String workHangup) {
    this.workHangup = workHangup;
  }
  
  public Long getStickie() {
    return this.stickie;
  }
  
  public void setStickie(Long stickie) {
    this.stickie = stickie;
  }
  
  public String getActivityDelaySend() {
    if ("null".equalsIgnoreCase(this.activityDelaySend))
      this.activityDelaySend = ""; 
    return this.activityDelaySend;
  }
  
  public void setActivityDelaySend(String activityDelaySend) {
    if ("null".equalsIgnoreCase(activityDelaySend))
      activityDelaySend = ""; 
    this.activityDelaySend = activityDelaySend;
  }
}
