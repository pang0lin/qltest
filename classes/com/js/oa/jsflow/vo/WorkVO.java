package com.js.oa.jsflow.vo;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class WorkVO implements Serializable {
  private String fileType;
  
  private String curStep;
  
  private String selfMainLinkFile;
  
  private String submitPerson;
  
  private int workType;
  
  private int deleted;
  
  private int isTran;
  
  private String workUser;
  
  private String remindValue;
  
  private String toMainLinkFile;
  
  private Long activity;
  
  private Long curEmployeeId;
  
  private Long id;
  
  private Long processId;
  
  private Long recordId;
  
  private Long submitEmployeeId;
  
  private Long tableId;
  
  private int allowCancel;
  
  private int allowStandFor;
  
  private int userType;
  
  private int pressType;
  
  private String deadLine;
  
  private String pressTime;
  
  private boolean needPassRound;
  
  private int passRoundUserType;
  
  private String[] passRoundUser;
  
  private String creatorCancelLink;
  
  private String isSubProcWork;
  
  private String pareProcActiId;
  
  private String pareStepCount;
  
  private String pareTableId;
  
  private String pareRecordId;
  
  private String pareProcNextActiId;
  
  private String docTitle;
  
  private String submitOrg;
  
  private String domainId;
  
  private String emergence;
  
  private String transactType;
  
  private String stepCount;
  
  private String workDeadlinePressDate;
  
  private String workDeadlineDate;
  
  private String tranFromPersonId;
  
  private String tranType;
  
  private Long relProjectId;
  
  private String processDeadlineDate;
  
  public String getProcessDeadlineDate() {
    return this.processDeadlineDate;
  }
  
  public void setProcessDeadlineDate(String processDeadlineDate) {
    this.processDeadlineDate = processDeadlineDate;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof WorkVO))
      return false; 
    WorkVO castOther = (WorkVO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
  
  public Long getCurEmployeeId() {
    return this.curEmployeeId;
  }
  
  public void setCurEmployeeId(Long curEmployeeId) {
    this.curEmployeeId = curEmployeeId;
  }
  
  public String getFileType() {
    return this.fileType;
  }
  
  public void setFileType(String fileType) {
    this.fileType = fileType;
  }
  
  public String getCurStep() {
    return this.curStep;
  }
  
  public void setCurStep(String curStep) {
    this.curStep = curStep;
  }
  
  public String getSelfMainLinkFile() {
    return this.selfMainLinkFile;
  }
  
  public void setSelfMainLinkFile(String selfMainLinkFile) {
    this.selfMainLinkFile = selfMainLinkFile;
  }
  
  public Long getActivity() {
    return this.activity;
  }
  
  public void setActivity(Long activity) {
    this.activity = activity;
  }
  
  public String getSubmitPerson() {
    return this.submitPerson;
  }
  
  public void setSubmitPerson(String submitPerson) {
    this.submitPerson = submitPerson;
  }
  
  public Long getSubmitEmployeeId() {
    return this.submitEmployeeId;
  }
  
  public void setSubmitEmployeeId(Long submitEmployeeId) {
    this.submitEmployeeId = submitEmployeeId;
  }
  
  public int getWorkType() {
    return this.workType;
  }
  
  public void setWorkType(int workType) {
    this.workType = workType;
  }
  
  public Long getProcessId() {
    return this.processId;
  }
  
  public void setProcessId(Long processId) {
    this.processId = processId;
  }
  
  public Long getTableId() {
    return this.tableId;
  }
  
  public void setTableId(Long tableId) {
    this.tableId = tableId;
  }
  
  public Long getRecordId() {
    return this.recordId;
  }
  
  public void setRecordId(Long recordId) {
    this.recordId = recordId;
  }
  
  public String getDeadLine() {
    return this.deadLine;
  }
  
  public void setDeadLine(String deadLine) {
    this.deadLine = deadLine;
  }
  
  public String getPressTime() {
    return this.pressTime;
  }
  
  public void setPressTime(String pressTime) {
    this.pressTime = pressTime;
  }
  
  public int getDeleted() {
    return this.deleted;
  }
  
  public void setDeleted(int deleted) {
    this.deleted = deleted;
  }
  
  public int getIsTran() {
    return this.isTran;
  }
  
  public void setIsTran(int isTran) {
    this.isTran = isTran;
  }
  
  public String getWorkUser() {
    return this.workUser;
  }
  
  public void setWorkUser(String workUser) {
    this.workUser = workUser;
  }
  
  public String getRemindValue() {
    return this.remindValue;
  }
  
  public void setRemindValue(String remindValue) {
    this.remindValue = remindValue;
  }
  
  public String getToMainLinkFile() {
    return this.toMainLinkFile;
  }
  
  public void setToMainLinkFile(String toMainLinkFile) {
    this.toMainLinkFile = toMainLinkFile;
  }
  
  public int getAllowCancel() {
    return this.allowCancel;
  }
  
  public void setAllowCancel(int allowCancel) {
    this.allowCancel = allowCancel;
  }
  
  public int getAllowStandFor() {
    return this.allowStandFor;
  }
  
  public void setAllowStandFor(int allowStandFor) {
    this.allowStandFor = allowStandFor;
  }
  
  public int getUserType() {
    return this.userType;
  }
  
  public void setUserType(int userType) {
    this.userType = userType;
  }
  
  public int getPressType() {
    return this.pressType;
  }
  
  public void setPressType(int pressType) {
    this.pressType = pressType;
  }
  
  public boolean isNeedPassRound() {
    return this.needPassRound;
  }
  
  public void setNeedPassRound(boolean needPassRound) {
    this.needPassRound = needPassRound;
  }
  
  public int getPassRoundUserType() {
    return this.passRoundUserType;
  }
  
  public void setPassRoundUserType(int passRoundUserType) {
    this.passRoundUserType = passRoundUserType;
  }
  
  public String[] getPassRoundUser() {
    return this.passRoundUser;
  }
  
  public void setPassRoundUser(String[] passRoundUser) {
    this.passRoundUser = passRoundUser;
  }
  
  public String getCreatorCancelLink() {
    return this.creatorCancelLink;
  }
  
  public void setCreatorCancelLink(String creatorCancelLink) {
    this.creatorCancelLink = creatorCancelLink;
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
  
  public String getDocTitle() {
    return this.docTitle;
  }
  
  public void setDocTitle(String docTitle) {
    this.docTitle = docTitle;
  }
  
  public String getSubmitOrg() {
    return this.submitOrg;
  }
  
  public void setSubmitOrg(String submitOrg) {
    this.submitOrg = submitOrg;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
  
  public String getEmergence() {
    return this.emergence;
  }
  
  public void setEmergence(String emergence) {
    this.emergence = emergence;
  }
  
  public String getTransactType() {
    return this.transactType;
  }
  
  public void setTransactType(String transactType) {
    this.transactType = transactType;
  }
  
  public String getStepCount() {
    return this.stepCount;
  }
  
  public void setStepCount(String stepCount) {
    this.stepCount = stepCount;
  }
  
  public String getWorkDeadlinePressDate() {
    return this.workDeadlinePressDate;
  }
  
  public void setWorkDeadlinePressDate(String workDeadlinePressDate) {
    this.workDeadlinePressDate = workDeadlinePressDate;
  }
  
  public String getWorkDeadlineDate() {
    return this.workDeadlineDate;
  }
  
  public void setWorkDeadlineDate(String workDeadlineDate) {
    this.workDeadlineDate = workDeadlineDate;
  }
  
  public String getTranFromPersonId() {
    return this.tranFromPersonId;
  }
  
  public void setTranFromPersonId(String tranFromPersonId) {
    this.tranFromPersonId = tranFromPersonId;
  }
  
  public String getTranType() {
    return this.tranType;
  }
  
  public void setTranType(String tranType) {
    this.tranType = tranType;
  }
  
  public Long getRelProjectId() {
    return this.relProjectId;
  }
  
  public void setRelProjectId(Long relProjectId) {
    this.relProjectId = relProjectId;
  }
}
