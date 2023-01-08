package com.js.system.action.rolemanager;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class RoleActionForm extends ActionForm {
  private String searchRoleName;
  
  private String roleName;
  
  private String roleDescription;
  
  private String rightIdString;
  
  private long createdOrg;
  
  private String roleUserName;
  
  private String roleUserId;
  
  private String roleUseRange;
  
  private String roleUseName;
  
  private String roleRange;
  
  private String roleRangeName;
  
  private String roleRangeType;
  
  public String getRoleRangeType() {
    return this.roleRangeType;
  }
  
  public void setRoleRangeType(String roleRangeType) {
    this.roleRangeType = roleRangeType;
  }
  
  public String getRoleRange() {
    return this.roleRange;
  }
  
  public void setRoleRange(String roleRange) {
    this.roleRange = roleRange;
  }
  
  public String getRoleRangeName() {
    return this.roleRangeName;
  }
  
  public void setRoleRangeName(String roleRangeName) {
    this.roleRangeName = roleRangeName;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public String getSearchRoleName() {
    return this.searchRoleName;
  }
  
  public void setSearchRoleName(String searchRoleName) {
    this.searchRoleName = searchRoleName;
  }
  
  public String getRoleName() {
    return this.roleName;
  }
  
  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }
  
  public String getRoleDescription() {
    return this.roleDescription;
  }
  
  public void setRoleDescription(String roleDescription) {
    this.roleDescription = roleDescription;
  }
  
  public String getRightIdString() {
    return this.rightIdString;
  }
  
  public void setRightIdString(String rightIdString) {
    this.rightIdString = rightIdString;
  }
  
  public long getCreatedOrg() {
    return this.createdOrg;
  }
  
  public void setCreatedOrg(long createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public String getRoleUserName() {
    return this.roleUserName;
  }
  
  public void setRoleUserName(String roleUserName) {
    this.roleUserName = roleUserName;
  }
  
  public String getRoleUserId() {
    return this.roleUserId;
  }
  
  public void setRoleUserId(String roleUserId) {
    this.roleUserId = roleUserId;
  }
  
  public String getRoleUseRange() {
    return this.roleUseRange;
  }
  
  public void setRoleUseRange(String roleUseRange) {
    this.roleUseRange = roleUseRange;
  }
  
  public String getRoleUseName() {
    return this.roleUseName;
  }
  
  public void setRoleUseName(String roleUseName) {
    this.roleUseName = roleUseName;
  }
}
