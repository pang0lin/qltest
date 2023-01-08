package com.js.oa.jsflow.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ActivityActionForm extends ActionForm {
  private String activityName;
  
  private String activityType;
  
  private String activityDescription;
  
  private String pressType;
  
  private String participantType;
  
  private String fountainField;
  
  private String allowStandFor;
  
  private String allowCancel;
  
  private String allowTransition;
  
  private String printFileSeeScope;
  
  private String printFileSeeOrg;
  
  private String printFileSeeGroup;
  
  private String printFileSeePerson;
  
  private String operButton;
  
  private String allowSmsRemind;
  
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
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public String getActivityName() {
    return this.activityName;
  }
  
  public void setActivityName(String activityName) {
    this.activityName = activityName;
  }
  
  public String getActivityType() {
    return this.activityType;
  }
  
  public void setActivityType(String activityType) {
    this.activityType = activityType;
  }
  
  public String getActivityDescription() {
    return this.activityDescription;
  }
  
  public void setActivityDescription(String activityDescription) {
    this.activityDescription = activityDescription;
  }
  
  public String getPressType() {
    return this.pressType;
  }
  
  public void setPressType(String pressType) {
    this.pressType = pressType;
  }
  
  public String getParticipantType() {
    return this.participantType;
  }
  
  public void setParticipantType(String participantType) {
    this.participantType = participantType;
  }
  
  public String getFountainField() {
    return this.fountainField;
  }
  
  public void setFountainField(String fountainField) {
    this.fountainField = fountainField;
  }
  
  public String getAllowStandFor() {
    return this.allowStandFor;
  }
  
  public void setAllowStandFor(String allowStandFor) {
    this.allowStandFor = allowStandFor;
  }
  
  public String getAllowCancel() {
    return this.allowCancel;
  }
  
  public void setAllowCancel(String allowCancel) {
    this.allowCancel = allowCancel;
  }
  
  public String getAllowTransition() {
    return this.allowTransition;
  }
  
  public void setAllowTransition(String allowTransition) {
    this.allowTransition = allowTransition;
  }
  
  public String getPrintFileSeeScope() {
    return this.printFileSeeScope;
  }
  
  public void setPrintFileSeeScope(String printFileSeeScope) {
    this.printFileSeeScope = printFileSeeScope;
  }
  
  public String getPrintFileSeeOrg() {
    return this.printFileSeeOrg;
  }
  
  public void setPrintFileSeeOrg(String printFileSeeOrg) {
    this.printFileSeeOrg = printFileSeeOrg;
  }
  
  public String getPrintFileSeeGroup() {
    return this.printFileSeeGroup;
  }
  
  public void setPrintFileSeeGroup(String printFileSeeGroup) {
    this.printFileSeeGroup = printFileSeeGroup;
  }
  
  public String getPrintFileSeePerson() {
    return this.printFileSeePerson;
  }
  
  public void setPrintFileSeePerson(String printFileSeePerson) {
    this.printFileSeePerson = printFileSeePerson;
  }
  
  public String getOperButton() {
    return this.operButton;
  }
  
  public void setOperButton(String operButton) {
    this.operButton = operButton;
  }
  
  public String getAllowSmsRemind() {
    return this.allowSmsRemind;
  }
  
  public void setAllowSmsRemind(String allowSmsRemind) {
    this.allowSmsRemind = allowSmsRemind;
  }
}
