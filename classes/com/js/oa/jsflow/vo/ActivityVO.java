package com.js.oa.jsflow.vo;

import java.io.Serializable;
import java.util.List;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class ActivityVO implements Serializable {
  private String name;
  
  private long id;
  
  private int allowStandFor;
  
  private int allowcancel;
  
  private int pressType;
  
  private int participantType;
  
  private String participantUserField;
  
  private int deadlineTime;
  
  private int pressMotionTime;
  
  private List participantUser = null;
  
  private int beginEnd;
  
  private int passRoundUserType;
  
  private String passRoundUserField;
  
  private List passRoundUser = null;
  
  private Boolean needPassRound;
  
  private String partRole;
  
  private String partRoleName;
  
  private String passRole;
  
  private String passRoleName;
  
  private int activityClass;
  
  private int allowTransition;
  
  private int activityBeginEnd;
  
  private String participantGivenOrg;
  
  private String passRoundGivenOrg;
  
  private String operButton;
  
  private String defaultActivity;
  
  private String transactType;
  
  private String formId;
  
  private String activityDescription;
  
  private String actiCommField;
  
  private String passRoundCommField;
  
  private String actiCommFieldType;
  
  private String passRoundCommFieldType;
  
  private String allowSmsRemind;
  
  private String tranCustomExtent;
  
  private String tranType;
  
  private String tranCustomExtentId;
  
  private String tranReadType;
  
  private String tranReadCustomExtent;
  
  private String tranReadCustomExtentId;
  
  private String pressDealType;
  
  private String extendMainTable;
  
  private String exeScript;
  
  private String beforeSubmit;
  
  private String allowAutoCheck;
  
  private String isDivide;
  
  private String isGather;
  
  private String isBranch;
  
  public String getIsDivide() {
    return this.isDivide;
  }
  
  public void setIsDivide(String isDivide) {
    this.isDivide = isDivide;
  }
  
  public String getIsGather() {
    return this.isGather;
  }
  
  public void setIsGather(String isGather) {
    this.isGather = isGather;
  }
  
  public String getIsBranch() {
    return this.isBranch;
  }
  
  public void setIsBranch(String isBranch) {
    this.isBranch = isBranch;
  }
  
  public String getAllowAutoCheck() {
    return this.allowAutoCheck;
  }
  
  public void setAllowAutoCheck(String allowAutoCheck) {
    this.allowAutoCheck = allowAutoCheck;
  }
  
  public String getExeScript() {
    return this.exeScript;
  }
  
  public void setExeScript(String exeScript) {
    this.exeScript = exeScript;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public int getAllowStandFor() {
    return this.allowStandFor;
  }
  
  public void setAllowStandFor(int allowStandFor) {
    this.allowStandFor = allowStandFor;
  }
  
  public int getAllowcancel() {
    return this.allowcancel;
  }
  
  public void setAllowcancel(int allowcancel) {
    this.allowcancel = allowcancel;
  }
  
  public int getPressType() {
    return this.pressType;
  }
  
  public void setPressType(int pressType) {
    this.pressType = pressType;
  }
  
  public int getParticipantType() {
    return this.participantType;
  }
  
  public void setParticipantType(int participantType) {
    this.participantType = participantType;
  }
  
  public List getParticipantUser() {
    return this.participantUser;
  }
  
  public void setParticipantUser(List participantUser) {
    this.participantUser = participantUser;
  }
  
  public String getParticipantUserField() {
    return this.participantUserField;
  }
  
  public void setParticipantUserField(String participantUserField) {
    this.participantUserField = participantUserField;
  }
  
  public int getDeadlineTime() {
    return this.deadlineTime;
  }
  
  public void setDeadlineTime(int deadlineTime) {
    this.deadlineTime = deadlineTime;
  }
  
  public int getPressMotionTime() {
    return this.pressMotionTime;
  }
  
  public void setPressMotionTime(int pressMotionTime) {
    this.pressMotionTime = pressMotionTime;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof ActivityVO))
      return false; 
    ActivityVO castOther = (ActivityVO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
  
  public int getBeginEnd() {
    return this.beginEnd;
  }
  
  public void setBeginEnd(int beginEnd) {
    this.beginEnd = beginEnd;
  }
  
  public Boolean getNeedPassRound() {
    return this.needPassRound;
  }
  
  public void setNeedPassRound(Boolean needPassRound) {
    this.needPassRound = needPassRound;
  }
  
  public int getPassRoundUserType() {
    return this.passRoundUserType;
  }
  
  public void setPassRoundUserType(int passRoundUserType) {
    this.passRoundUserType = passRoundUserType;
  }
  
  public List getPassRoundUser() {
    return this.passRoundUser;
  }
  
  public void setPassRoundUser(List passRoundUser) {
    this.passRoundUser = passRoundUser;
  }
  
  public String getPassRoundUserField() {
    return this.passRoundUserField;
  }
  
  public void setPassRoundUserField(String passRoundUserField) {
    this.passRoundUserField = passRoundUserField;
  }
  
  public String getPartRole() {
    return this.partRole;
  }
  
  public void setPartRole(String partRole) {
    this.partRole = partRole;
  }
  
  public String getPartRoleName() {
    return this.partRoleName;
  }
  
  public void setPartRoleName(String partRoleName) {
    this.partRoleName = partRoleName;
  }
  
  public String getPassRole() {
    return this.passRole;
  }
  
  public void setPassRole(String passRole) {
    this.passRole = passRole;
  }
  
  public String getPassRoleName() {
    return this.passRoleName;
  }
  
  public void setPassRoleName(String passRoleName) {
    this.passRoleName = passRoleName;
  }
  
  public int getActivityClass() {
    return this.activityClass;
  }
  
  public void setActivityClass(int activityClass) {
    this.activityClass = activityClass;
  }
  
  public int getAllowTransition() {
    return this.allowTransition;
  }
  
  public void setAllowTransition(int allowTransition) {
    this.allowTransition = allowTransition;
  }
  
  public int getActivityBeginEnd() {
    return this.activityBeginEnd;
  }
  
  public void setActivityBeginEnd(int activityBeginEnd) {
    this.activityBeginEnd = activityBeginEnd;
  }
  
  public String getParticipantGivenOrg() {
    return this.participantGivenOrg;
  }
  
  public void setParticipantGivenOrg(String participantGivenOrg) {
    this.participantGivenOrg = participantGivenOrg;
  }
  
  public String getPassRoundGivenOrg() {
    return this.passRoundGivenOrg;
  }
  
  public void setPassRoundGivenOrg(String passRoundGivenOrg) {
    this.passRoundGivenOrg = passRoundGivenOrg;
  }
  
  public String getOperButton() {
    return this.operButton;
  }
  
  public void setOperButton(String operButton) {
    this.operButton = operButton;
  }
  
  public String getDefaultActivity() {
    return this.defaultActivity;
  }
  
  public void setDefaultActivity(String defaultActivity) {
    this.defaultActivity = defaultActivity;
  }
  
  public String getTransactType() {
    return this.transactType;
  }
  
  public void setTransactType(String transactType) {
    this.transactType = transactType;
  }
  
  public String getFormId() {
    return this.formId;
  }
  
  public void setFormId(String formId) {
    this.formId = formId;
  }
  
  public String getActivityDescription() {
    return this.activityDescription;
  }
  
  public void setActivityDescription(String activityDescription) {
    this.activityDescription = activityDescription;
  }
  
  public String getActiCommField() {
    return this.actiCommField;
  }
  
  public void setActiCommField(String actiCommField) {
    this.actiCommField = actiCommField;
  }
  
  public String getPassRoundCommField() {
    return this.passRoundCommField;
  }
  
  public void setPassRoundCommField(String passRoundCommField) {
    this.passRoundCommField = passRoundCommField;
  }
  
  public String getActiCommFieldType() {
    return this.actiCommFieldType;
  }
  
  public void setActiCommFieldType(String actiCommFieldType) {
    this.actiCommFieldType = actiCommFieldType;
  }
  
  public String getPassRoundCommFieldType() {
    return this.passRoundCommFieldType;
  }
  
  public void setPassRoundCommFieldType(String passRoundCommFieldType) {
    this.passRoundCommFieldType = passRoundCommFieldType;
  }
  
  public String getAllowSmsRemind() {
    return this.allowSmsRemind;
  }
  
  public void setAllowSmsRemind(String allowSmsRemind) {
    this.allowSmsRemind = allowSmsRemind;
  }
  
  public String getTranCustomExtent() {
    return this.tranCustomExtent;
  }
  
  public void setTranCustomExtent(String tranCustomExtent) {
    this.tranCustomExtent = tranCustomExtent;
  }
  
  public String getTranType() {
    return this.tranType;
  }
  
  public void setTranType(String tranType) {
    this.tranType = tranType;
  }
  
  public String getTranCustomExtentId() {
    return this.tranCustomExtentId;
  }
  
  public void setTranCustomExtentId(String tranCustomExtentId) {
    this.tranCustomExtentId = tranCustomExtentId;
  }
  
  public String getPressDealType() {
    return this.pressDealType;
  }
  
  public void setPressDealType(String pressDealType) {
    this.pressDealType = pressDealType;
  }
  
  public String getExtendMainTable() {
    return this.extendMainTable;
  }
  
  public void setExtendMainTable(String extendMainTable) {
    this.extendMainTable = extendMainTable;
  }
  
  public String getTranReadCustomExtent() {
    return this.tranReadCustomExtent;
  }
  
  public String getTranReadCustomExtentId() {
    return this.tranReadCustomExtentId;
  }
  
  public String getTranReadType() {
    return this.tranReadType;
  }
  
  public void setTranReadCustomExtent(String tranReadCustomExtent) {
    this.tranReadCustomExtent = tranReadCustomExtent;
  }
  
  public void setTranReadCustomExtentId(String tranReadCustomExtentId) {
    this.tranReadCustomExtentId = tranReadCustomExtentId;
  }
  
  public void setTranReadType(String tranReadType) {
    this.tranReadType = tranReadType;
  }
  
  public String getBeforeSubmit() {
    return this.beforeSubmit;
  }
  
  public void setBeforeSubmit(String beforeSubmit) {
    this.beforeSubmit = beforeSubmit;
  }
}
