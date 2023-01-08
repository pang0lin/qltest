package com.js.system.action.usermanager;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class UserForm extends ActionForm {
  private String action = "";
  
  private String saveType = "";
  
  private String updatedId = "";
  
  private String rightIds = "";
  
  private String rightScopeTypes = "";
  
  private String rightScopeScopes = "";
  
  private String empEnglishName = "";
  
  private String empNumber = "";
  
  private String empName = "";
  
  private byte empSex = 1;
  
  private long rightId = 0L;
  
  private String rightScopeScope = "";
  
  private short rightScopeType = 0;
  
  private String rightType = "";
  
  private long roleId = 0L;
  
  private String userAccounts = "";
  
  private byte userIsSuper = 0;
  
  private String orgIds = "";
  
  private String orgNames = "";
  
  private String userPassword = "";
  
  private Long userId;
  
  private String userRoleId;
  
  private String empLeaderName;
  
  private String empLeaderId;
  
  private String browseRange;
  
  private String browseRangeName;
  
  private String userOrderCode;
  
  private String empDuty;
  
  private String keyValidate = "0";
  
  private String keySerial = "";
  
  private String rightScopeDsp;
  
  private String userSimpleName;
  
  private String signatureImgName;
  
  private String signatureImgSaveName;
  
  private String sidelineOrgName;
  
  private String sidelineOrg;
  
  private Long empPositionId;
  
  private String empPositionOtherId;
  
  private String grantRange;
  
  private String grantRangeName;
  
  private String mailPost;
  
  private String weixinPost;
  
  private String dingdingPost;
  
  private String opinionRemind;
  
  public String getOpinionRemind() {
    return this.opinionRemind;
  }
  
  public void setOpinionRemind(String opinionRemind) {
    this.opinionRemind = opinionRemind;
  }
  
  public String getMailPost() {
    return this.mailPost;
  }
  
  public void setMailPost(String mailPost) {
    this.mailPost = mailPost;
  }
  
  public Long getEmpPositionId() {
    return this.empPositionId;
  }
  
  public void setEmpPositionId(Long empPositionId) {
    this.empPositionId = empPositionId;
  }
  
  public String getEmpPositionOtherId() {
    return this.empPositionOtherId;
  }
  
  public void setEmpPositionOtherId(String empPositionOtherId) {
    this.empPositionOtherId = empPositionOtherId;
  }
  
  public String getAction() {
    return this.action;
  }
  
  public void setAction(String action) {
    this.saveType = action;
  }
  
  public String getSaveType() {
    return this.saveType;
  }
  
  public void setSaveType(String saveType) {
    this.saveType = saveType;
  }
  
  public String getRightIds() {
    return this.rightIds;
  }
  
  public void setRightIds(String rightIds) {
    this.rightIds = rightIds;
  }
  
  public String getRightScopeTypes() {
    return this.rightScopeTypes;
  }
  
  public void setRightScopeTypes(String rightScopeTypes) {
    this.rightScopeTypes = rightScopeTypes;
  }
  
  public String getRightScopeScopes() {
    return this.rightScopeScopes;
  }
  
  public void setRightScopeScopes(String rightScopeScopes) {
    this.rightScopeScopes = rightScopeScopes;
  }
  
  public String getEmpEnglishName() {
    return this.empEnglishName;
  }
  
  public void setEmpEnglishName(String empEnglishName) {
    this.empEnglishName = empEnglishName;
  }
  
  public String getEmpNumber() {
    return this.empNumber;
  }
  
  public void setEmpNumber(String empNumber) {
    this.empNumber = empNumber;
  }
  
  public String getEmpName() {
    return this.empName;
  }
  
  public void setEmpName(String empName) {
    this.empName = empName;
  }
  
  public byte getEmpSex() {
    return this.empSex;
  }
  
  public void setEmpSex(byte empSex) {
    this.empSex = empSex;
  }
  
  public long getRightId() {
    return this.rightId;
  }
  
  public void setRightId(long rightId) {
    this.rightId = rightId;
  }
  
  public String getRightScopeScope() {
    return this.rightScopeScope;
  }
  
  public void setRightScopeScope(String rightScopeScope) {
    this.rightScopeScope = rightScopeScope;
  }
  
  public short getRightScopeType() {
    return this.rightScopeType;
  }
  
  public void setRightScopeType(short rightScopeType) {
    this.rightScopeType = rightScopeType;
  }
  
  public String getRightType() {
    return this.rightType;
  }
  
  public void setRightType(String rightType) {
    this.rightType = rightType;
  }
  
  public long getRoleId() {
    return this.roleId;
  }
  
  public void setRoleId(long roleId) {
    this.roleId = roleId;
  }
  
  public String getUserAccounts() {
    return this.userAccounts;
  }
  
  public void setUserAccounts(String userAccounts) {
    this.userAccounts = userAccounts;
  }
  
  public byte getUserIsSuper() {
    return this.userIsSuper;
  }
  
  public void setUserIsSuper(byte userIsSuper) {
    this.userIsSuper = userIsSuper;
  }
  
  public String getOrgIds() {
    return this.orgIds;
  }
  
  public void setOrgIds(String orgIds) {
    this.orgIds = orgIds;
  }
  
  public String getUserPassword() {
    return this.userPassword;
  }
  
  public void setUserPassword(String userPassword) {
    this.userPassword = userPassword;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.action = null;
    this.saveType = null;
    this.empEnglishName = null;
    this.empNumber = null;
    this.empName = null;
    this.empSex = 0;
    this.rightId = 0L;
    this.rightScopeScope = null;
    this.rightScopeType = 0;
    this.rightType = null;
    this.roleId = 0L;
    this.userAccounts = null;
    this.userSimpleName = null;
    this.userIsSuper = 0;
    this.orgIds = null;
    this.userPassword = null;
    this.userId = null;
  }
  
  public Long getUserId() {
    return this.userId;
  }
  
  public void setUserId(Long userId) {
    this.userId = userId;
  }
  
  public String getOrgNames() {
    return this.orgNames;
  }
  
  public void setOrgNames(String orgNames) {
    this.orgNames = orgNames;
  }
  
  public String getUpdatedId() {
    return this.updatedId;
  }
  
  public void setUpdatedId(String updatedId) {
    this.updatedId = updatedId;
  }
  
  public String getUserRoleId() {
    return this.userRoleId;
  }
  
  public void setUserRoleId(String userRoleId) {
    this.userRoleId = userRoleId;
  }
  
  public String getEmpLeaderName() {
    return this.empLeaderName;
  }
  
  public void setEmpLeaderName(String empLeaderName) {
    this.empLeaderName = empLeaderName;
  }
  
  public String getEmpLeaderId() {
    return this.empLeaderId;
  }
  
  public void setEmpLeaderId(String empLeaderId) {
    this.empLeaderId = empLeaderId;
  }
  
  public String getBrowseRange() {
    return this.browseRange;
  }
  
  public void setBrowseRange(String browseRange) {
    this.browseRange = browseRange;
  }
  
  public String getBrowseRangeName() {
    return this.browseRangeName;
  }
  
  public void setBrowseRangeName(String browseRangeName) {
    this.browseRangeName = browseRangeName;
  }
  
  public String getUserOrderCode() {
    return this.userOrderCode;
  }
  
  public void setUserOrderCode(String userOrderCode) {
    this.userOrderCode = userOrderCode;
  }
  
  public String getEmpDuty() {
    return this.empDuty;
  }
  
  public void setEmpDuty(String empDuty) {
    this.empDuty = empDuty;
  }
  
  public String getKeyValidate() {
    return this.keyValidate;
  }
  
  public void setKeyValidate(String keyValidate) {
    this.keyValidate = keyValidate;
  }
  
  public String getKeySerial() {
    return this.keySerial;
  }
  
  public void setKeySerial(String keySerial) {
    this.keySerial = keySerial;
  }
  
  public String getRightScopeDsp() {
    return this.rightScopeDsp;
  }
  
  public void setRightScopeDsp(String rightScopeDsp) {
    this.rightScopeDsp = rightScopeDsp;
  }
  
  public String getUserSimpleName() {
    return this.userSimpleName;
  }
  
  public void setUserSimpleName(String userSimpleName) {
    this.userSimpleName = userSimpleName;
  }
  
  public String getSignatureImgName() {
    return this.signatureImgName;
  }
  
  public void setSignatureImgName(String signatureImgName) {
    this.signatureImgName = signatureImgName;
  }
  
  public String getSignatureImgSaveName() {
    return this.signatureImgSaveName;
  }
  
  public void setSignatureImgSaveName(String signatureImgSaveName) {
    this.signatureImgSaveName = signatureImgSaveName;
  }
  
  public String getSidelineOrgName() {
    return this.sidelineOrgName;
  }
  
  public void setSidelineOrgName(String sidelineOrgName) {
    this.sidelineOrgName = sidelineOrgName;
  }
  
  public String getSidelineOrg() {
    return this.sidelineOrg;
  }
  
  public void setSidelineOrg(String sidelineOrg) {
    this.sidelineOrg = sidelineOrg;
  }
  
  public String getGrantRange() {
    return this.grantRange;
  }
  
  public void setGrantRange(String grantRange) {
    this.grantRange = grantRange;
  }
  
  public String getGrantRangeName() {
    return this.grantRangeName;
  }
  
  public void setGrantRangeName(String grantRangeName) {
    this.grantRangeName = grantRangeName;
  }
  
  public String getWeixinPost() {
    return this.weixinPost;
  }
  
  public void setWeixinPost(String weixinPost) {
    this.weixinPost = weixinPost;
  }
  
  public String getDingdingPost() {
    return this.dingdingPost;
  }
  
  public void setDingdingPost(String dingdingPost) {
    this.dingdingPost = dingdingPost;
  }
}
