package com.js.oa.jsflow.po;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class WFActivityPO implements Serializable {
  private Long wfActivityId;
  
  private String activityName;
  
  private String activityDescription;
  
  private int activityType;
  
  private int allowStandFor;
  
  private int allowCancel;
  
  private int allowTransition;
  
  private int participantType;
  
  private String participantUser;
  
  private int pressType;
  
  private int pressMotionTime;
  
  private String activityDocumentionation;
  
  private String activityIcon;
  
  private int activityBeginEnd;
  
  private WFWorkFlowProcessPO wfWorkFlowProcess;
  
  private Set wfReadWriteControl = null;
  
  private Set wfPress = null;
  
  private Set transitionFrom = null;
  
  private Set transitionTo = null;
  
  private String participantUserName;
  
  private String participantUserField;
  
  private int deadLineTime;
  
  private String participantGroup;
  
  private int actiCommType;
  
  private String actiCommField;
  
  private int needPassRound;
  
  private int passRoundUserType;
  
  private String passRoundUser;
  
  private String passRoundUserGroup;
  
  private String passRoundUserName;
  
  private String passRoundUserField;
  
  private String passRoundCommField;
  
  private String participantRole;
  
  private String passRoundRole;
  
  private int subProcType;
  
  private int activityClass;
  
  private String activitySubProc;
  
  private String formClassMethod;
  
  private String participantGivenOrgName;
  
  private String participantGivenOrg;
  
  private String passRoundGivenOrgName;
  
  private String passRoundGivenOrg;
  
  private String untreadMethod;
  
  private String commentRange;
  
  private String commentRangeName;
  
  private String domainId;
  
  private String formId;
  
  private String operButton;
  
  private String transactType;
  
  private String actiCommFieldType;
  
  private String passRoundCommFieldType;
  
  private Set wfProtectControl = null;
  
  private String allowSmsRemind;
  
  private String tranType;
  
  private String tranCustomExtent;
  
  private String tranCustomExtentId;
  
  private String tranReadType;
  
  private String tranReadCustomExtent;
  
  private String tranReadCustomExtentId;
  
  private String pressDealType;
  
  private String extendMainTable;
  
  private Long mainFormId;
  
  private String exeScript;
  
  private int handleType;
  
  private String handleView;
  
  private String handleClass;
  
  private String handleMethod;
  
  private String handleParam;
  
  private String webServiceUrl;
  
  private String webServiceMethod;
  
  private String webServicePara;
  
  private String webServiceNameSpace;
  
  private String sendMsgToInitiator;
  
  private String sendMsgToDealDone;
  
  private int opinionmust;
  
  private String handSignType;
  
  private String beforeSubmit;
  
  private String opinionLengthSet;
  
  private String opinionLengthMin;
  
  private String opinionLengthMax;
  
  private String allowAutoCheck;
  
  private String isDivide;
  
  private String isGather;
  
  private String isBranch;
  
  public String getSendMsgToDealDone() {
    return this.sendMsgToDealDone;
  }
  
  public void setSendMsgToDealDone(String sendMsgToDealDone) {
    this.sendMsgToDealDone = sendMsgToDealDone;
  }
  
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
  
  public String getOpinionLengthSet() {
    return this.opinionLengthSet;
  }
  
  public void setOpinionLengthSet(String opinionLengthSet) {
    this.opinionLengthSet = opinionLengthSet;
  }
  
  public String getOpinionLengthMin() {
    return this.opinionLengthMin;
  }
  
  public void setOpinionLengthMin(String opinionLengthMin) {
    this.opinionLengthMin = opinionLengthMin;
  }
  
  public String getOpinionLengthMax() {
    return this.opinionLengthMax;
  }
  
  public void setOpinionLengthMax(String opinionLengthMax) {
    this.opinionLengthMax = opinionLengthMax;
  }
  
  public Long getMainFormId() {
    return this.mainFormId;
  }
  
  public void setMainFormId(Long mainFormId) {
    this.mainFormId = mainFormId;
  }
  
  public String getExeScript() {
    return this.exeScript;
  }
  
  public void setExeScript(String exeScript) {
    this.exeScript = exeScript;
  }
  
  public Long getWfActivityId() {
    return this.wfActivityId;
  }
  
  public void setWfActivityId(Long wfActivityId) {
    this.wfActivityId = wfActivityId;
  }
  
  public String getActivityName() {
    return this.activityName;
  }
  
  public void setActivityName(String activityName) {
    this.activityName = activityName;
  }
  
  public String getActivityDescription() {
    return this.activityDescription;
  }
  
  public void setActivityDescription(String activityDescription) {
    this.activityDescription = activityDescription;
  }
  
  public int getActivityType() {
    return this.activityType;
  }
  
  public void setActivityType(int activityType) {
    this.activityType = activityType;
  }
  
  public int getAllowStandFor() {
    return this.allowStandFor;
  }
  
  public void setAllowStandFor(int allowStandFor) {
    this.allowStandFor = allowStandFor;
  }
  
  public int getAllowCancel() {
    return this.allowCancel;
  }
  
  public void setAllowCancel(int allowCancel) {
    this.allowCancel = allowCancel;
  }
  
  public int getAllowTransition() {
    return this.allowTransition;
  }
  
  public void setAllowTransition(int allowTransition) {
    this.allowTransition = allowTransition;
  }
  
  public int getParticipantType() {
    return this.participantType;
  }
  
  public void setParticipantType(int participantType) {
    this.participantType = participantType;
  }
  
  public String getParticipantUser() {
    return this.participantUser;
  }
  
  public void setParticipantUser(String participantUser) {
    this.participantUser = participantUser;
  }
  
  public int getPressType() {
    return this.pressType;
  }
  
  public void setPressType(int pressType) {
    this.pressType = pressType;
  }
  
  public int getPressMotionTime() {
    return this.pressMotionTime;
  }
  
  public void setPressMotionTime(int pressMotionTime) {
    this.pressMotionTime = pressMotionTime;
  }
  
  public String getActivityDocumentionation() {
    return this.activityDocumentionation;
  }
  
  public void setActivityDocumentionation(String activityDocumentionation) {
    this.activityDocumentionation = activityDocumentionation;
  }
  
  public String getActivityIcon() {
    return this.activityIcon;
  }
  
  public void setActivityIcon(String activityIcon) {
    this.activityIcon = activityIcon;
  }
  
  public int getActivityBeginEnd() {
    return this.activityBeginEnd;
  }
  
  public void setActivityBeginEnd(int activityBeginEnd) {
    this.activityBeginEnd = activityBeginEnd;
  }
  
  public WFWorkFlowProcessPO getWfWorkFlowProcess() {
    return this.wfWorkFlowProcess;
  }
  
  public void setWfWorkFlowProcess(WFWorkFlowProcessPO wfWorkFlowProcess) {
    this.wfWorkFlowProcess = wfWorkFlowProcess;
  }
  
  public Set getWfReadWriteControl() {
    return this.wfReadWriteControl;
  }
  
  public void setWfReadWriteControl(Set wfReadWriteControl) {
    this.wfReadWriteControl = wfReadWriteControl;
  }
  
  public Set getWfPress() {
    return this.wfPress;
  }
  
  public void setWfPress(Set wfPress) {
    this.wfPress = wfPress;
  }
  
  public Set getTransitionFrom() {
    return this.transitionFrom;
  }
  
  public void setTransitionFrom(Set transitionFrom) {
    this.transitionFrom = transitionFrom;
  }
  
  public Set getTransitionTo() {
    return this.transitionTo;
  }
  
  public void setTransitionTo(Set transitionTo) {
    this.transitionTo = transitionTo;
  }
  
  public String getParticipantUserName() {
    return this.participantUserName;
  }
  
  public void setParticipantUserName(String participantUserName) {
    this.participantUserName = participantUserName;
  }
  
  public String getParticipantUserField() {
    return this.participantUserField;
  }
  
  public void setParticipantUserField(String participantUserField) {
    this.participantUserField = participantUserField;
  }
  
  public int getDeadLineTime() {
    return this.deadLineTime;
  }
  
  public void setDeadLineTime(int deadLineTime) {
    this.deadLineTime = deadLineTime;
  }
  
  public String getParticipantGroup() {
    return this.participantGroup;
  }
  
  public void setParticipantGroup(String participantGroup) {
    this.participantGroup = participantGroup;
  }
  
  public String getTransactType() {
    return this.transactType;
  }
  
  public void setTransactType(String transactType) {
    this.transactType = transactType;
  }
  
  public int getActiCommType() {
    return this.actiCommType;
  }
  
  public void setActiCommType(int actiCommType) {
    this.actiCommType = actiCommType;
  }
  
  public String getActiCommField() {
    return this.actiCommField;
  }
  
  public void setActiCommField(String actiCommField) {
    this.actiCommField = actiCommField;
  }
  
  public int getNeedPassRound() {
    return this.needPassRound;
  }
  
  public void setNeedPassRound(int needPassRound) {
    this.needPassRound = needPassRound;
  }
  
  public int getPassRoundUserType() {
    return this.passRoundUserType;
  }
  
  public void setPassRoundUserType(int passRoundUserType) {
    this.passRoundUserType = passRoundUserType;
  }
  
  public String getPassRoundUser() {
    return this.passRoundUser;
  }
  
  public void setPassRoundUser(String passRoundUser) {
    this.passRoundUser = passRoundUser;
  }
  
  public String getPassRoundUserGroup() {
    return this.passRoundUserGroup;
  }
  
  public void setPassRoundUserGroup(String passRoundUserGroup) {
    this.passRoundUserGroup = passRoundUserGroup;
  }
  
  public String getPassRoundUserName() {
    return this.passRoundUserName;
  }
  
  public String getPassRoundUserField() {
    return this.passRoundUserField;
  }
  
  public void setPassRoundUserField(String passRoundUserField) {
    this.passRoundUserField = passRoundUserField;
  }
  
  public String getPassRoundCommField() {
    return this.passRoundCommField;
  }
  
  public void setPassRoundCommField(String passRoundCommField) {
    this.passRoundCommField = passRoundCommField;
  }
  
  public String getParticipantRole() {
    return this.participantRole;
  }
  
  public void setParticipantRole(String participantRole) {
    this.participantRole = participantRole;
  }
  
  public String getPassRoundRole() {
    return this.passRoundRole;
  }
  
  public void setPassRoundRole(String passRoundRole) {
    this.passRoundRole = passRoundRole;
  }
  
  public int getSubProcType() {
    return this.subProcType;
  }
  
  public void setSubProcType(int subProcType) {
    this.subProcType = subProcType;
  }
  
  public String getActivitySubProc() {
    return this.activitySubProc;
  }
  
  public void setActivitySubProc(String activitySubProc) {
    this.activitySubProc = activitySubProc;
  }
  
  public int getActivityClass() {
    return this.activityClass;
  }
  
  public void setActivityClass(int activityClass) {
    this.activityClass = activityClass;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof WFActivityPO))
      return false; 
    WFActivityPO castOther = (WFActivityPO)other;
    return (new EqualsBuilder()).append(getWfActivityId(), castOther.getWfActivityId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getWfActivityId()).toHashCode();
  }
  
  public String getFormClassMethod() {
    return this.formClassMethod;
  }
  
  public void setFormClassMethod(String formClassMethod) {
    this.formClassMethod = formClassMethod;
  }
  
  public String getParticipantGivenOrgName() {
    return this.participantGivenOrgName;
  }
  
  public void setParticipantGivenOrgName(String participantGivenOrgName) {
    this.participantGivenOrgName = participantGivenOrgName;
  }
  
  public String getParticipantGivenOrg() {
    return this.participantGivenOrg;
  }
  
  public void setParticipantGivenOrg(String participantGivenOrg) {
    this.participantGivenOrg = participantGivenOrg;
  }
  
  public String getPassRoundGivenOrgName() {
    return this.passRoundGivenOrgName;
  }
  
  public void setPassRoundGivenOrgName(String passRoundGivenOrgName) {
    this.passRoundGivenOrgName = passRoundGivenOrgName;
  }
  
  public String getPassRoundGivenOrg() {
    return this.passRoundGivenOrg;
  }
  
  public void setPassRoundGivenOrg(String passRoundGivenOrg) {
    this.passRoundGivenOrg = passRoundGivenOrg;
  }
  
  public String getUntreadMethod() {
    return this.untreadMethod;
  }
  
  public void setUntreadMethod(String untreadMethod) {
    this.untreadMethod = untreadMethod;
  }
  
  public String getCommentRange() {
    return this.commentRange;
  }
  
  public void setCommentRange(String commentRange) {
    this.commentRange = commentRange;
  }
  
  public String getCommentRangeName() {
    return this.commentRangeName;
  }
  
  public void setCommentRangeName(String commentRangeName) {
    this.commentRangeName = commentRangeName;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
  
  public String getFormId() {
    return this.formId;
  }
  
  public void setFormId(String formId) {
    this.formId = formId;
  }
  
  public String getOperButton() {
    return this.operButton;
  }
  
  public void setOperButton(String operButton) {
    this.operButton = operButton;
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
  
  public Set getWfProtectControl() {
    return this.wfProtectControl;
  }
  
  public void setWfProtectControl(Set wfProtectControl) {
    this.wfProtectControl = wfProtectControl;
  }
  
  public void setPassRoundUserName(String passRoundUserName) {
    this.passRoundUserName = passRoundUserName;
  }
  
  public String getAllowSmsRemind() {
    return this.allowSmsRemind;
  }
  
  public void setAllowSmsRemind(String allowSmsRemind) {
    this.allowSmsRemind = allowSmsRemind;
  }
  
  public String getTranType() {
    return this.tranType;
  }
  
  public void setTranType(String tranType) {
    this.tranType = tranType;
  }
  
  public String getTranCustomExtent() {
    return this.tranCustomExtent;
  }
  
  public void setTranCustomExtent(String tranCustomExtent) {
    this.tranCustomExtent = tranCustomExtent;
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
  
  public String getTranReadCustomExtentId() {
    return this.tranReadCustomExtentId;
  }
  
  public String getTranReadCustomExtent() {
    return this.tranReadCustomExtent;
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
  
  public int getHandleType() {
    return this.handleType;
  }
  
  public void setHandleType(int handleType) {
    this.handleType = handleType;
  }
  
  public String getHandleView() {
    return this.handleView;
  }
  
  public void setHandleView(String handleView) {
    this.handleView = handleView;
  }
  
  public String getHandleClass() {
    return this.handleClass;
  }
  
  public void setHandleClass(String handleClass) {
    this.handleClass = handleClass;
  }
  
  public String getHandleMethod() {
    return this.handleMethod;
  }
  
  public void setHandleMethod(String handleMethod) {
    this.handleMethod = handleMethod;
  }
  
  public String getHandleParam() {
    return this.handleParam;
  }
  
  public void setHandleParam(String handleParam) {
    this.handleParam = handleParam;
  }
  
  public String getWebServiceUrl() {
    return this.webServiceUrl;
  }
  
  public void setWebServiceUrl(String webServiceUrl) {
    this.webServiceUrl = webServiceUrl;
  }
  
  public String getWebServiceMethod() {
    return this.webServiceMethod;
  }
  
  public void setWebServiceMethod(String webServiceMethod) {
    this.webServiceMethod = webServiceMethod;
  }
  
  public String getWebServicePara() {
    return this.webServicePara;
  }
  
  public void setWebServicePara(String webServicePara) {
    this.webServicePara = webServicePara;
  }
  
  public String getWebServiceNameSpace() {
    return this.webServiceNameSpace;
  }
  
  public void setWebServiceNameSpace(String webServiceNameSpace) {
    this.webServiceNameSpace = webServiceNameSpace;
  }
  
  public String getSendMsgToInitiator() {
    return this.sendMsgToInitiator;
  }
  
  public void setSendMsgToInitiator(String sendMsgToInitiator) {
    this.sendMsgToInitiator = sendMsgToInitiator;
  }
  
  public int getOpinionmust() {
    return this.opinionmust;
  }
  
  public void setOpinionmust(int opinionmust) {
    this.opinionmust = opinionmust;
  }
  
  public String getHandSignType() {
    return this.handSignType;
  }
  
  public void setHandSignType(String handSignType) {
    this.handSignType = handSignType;
  }
  
  public String getBeforeSubmit() {
    return this.beforeSubmit;
  }
  
  public void setBeforeSubmit(String beforeSubmit) {
    this.beforeSubmit = beforeSubmit;
  }
}
