package com.js.system.action.groupmanager;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class GroupActionForm extends ActionForm {
  private String groupName;
  
  private String groupUserString;
  
  private String action;
  
  private String groupUserNames;
  
  private String groupId;
  
  private String createdOrg;
  
  private String groupType;
  
  private String groupOrder;
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public String getGroupName() {
    return this.groupName;
  }
  
  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }
  
  public String getGroupUserString() {
    return this.groupUserString;
  }
  
  public void setGroupUserString(String groupUserString) {
    this.groupUserString = groupUserString;
  }
  
  public String getAction() {
    return this.action;
  }
  
  public void setAction(String action) {
    this.action = action;
  }
  
  public String getGroupUserNames() {
    return this.groupUserNames;
  }
  
  public void setGroupUserNames(String groupUserNames) {
    this.groupUserNames = groupUserNames;
  }
  
  public String getGroupId() {
    return this.groupId;
  }
  
  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }
  
  public String getCreatedOrg() {
    return this.createdOrg;
  }
  
  public void setCreatedOrg(String createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public String getGroupType() {
    return this.groupType;
  }
  
  public void setGroupType(String groupType) {
    this.groupType = groupType;
  }
  
  public String getGroupOrder() {
    return this.groupOrder;
  }
  
  public void setGroupOrder(String groupOrder) {
    this.groupOrder = groupOrder;
  }
}
