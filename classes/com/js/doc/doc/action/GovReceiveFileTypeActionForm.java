package com.js.doc.doc.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class GovReceiveFileTypeActionForm extends ActionForm {
  private String id;
  
  private String receiveFileTypeName;
  
  private String userId;
  
  private String userName;
  
  private String groupId;
  
  private String orgId;
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.receiveFileTypeName = "";
  }
  
  public String getId() {
    return this.id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public String getReceiveFileTypeName() {
    return this.receiveFileTypeName;
  }
  
  public void setReceiveFileTypeName(String receiveFileTypeName) {
    this.receiveFileTypeName = receiveFileTypeName;
  }
  
  public String getUserId() {
    return this.userId;
  }
  
  public void setUserId(String userId) {
    this.userId = userId;
  }
  
  public String getUserName() {
    return this.userName;
  }
  
  public void setUserName(String userName) {
    this.userName = userName;
  }
  
  public String getGroupId() {
    return this.groupId;
  }
  
  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }
  
  public String getOrgId() {
    return this.orgId;
  }
  
  public void setOrgId(String orgId) {
    this.orgId = orgId;
  }
}
