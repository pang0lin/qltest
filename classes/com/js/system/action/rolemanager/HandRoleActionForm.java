package com.js.system.action.rolemanager;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class HandRoleActionForm extends ActionForm {
  private long handRoleId;
  
  private String roleDeliverName;
  
  private String roleRecieveName;
  
  private String roleHandTransactor;
  
  private String roleDeliverId;
  
  private String roleRecieveId;
  
  private String roleHandDate;
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.handRoleId = 0L;
    this.roleDeliverId = "";
    this.roleDeliverName = "";
    this.roleRecieveName = "";
    this.roleRecieveName = "";
    this.roleHandTransactor = "";
  }
  
  public long getHandRoleId() {
    return this.handRoleId;
  }
  
  public void setHandRoleId(long handRoleId) {
    this.handRoleId = handRoleId;
  }
  
  public String getRoleDeliverId() {
    return this.roleDeliverId;
  }
  
  public void setRoleDeliverId(String roleDeliverId) {
    this.roleDeliverId = roleDeliverId;
  }
  
  public String getRoleRecieveId() {
    return this.roleRecieveId;
  }
  
  public void setRoleRecieveId(String roleRecieveId) {
    this.roleRecieveId = roleRecieveId;
  }
  
  public String getRoleDeliverName() {
    return this.roleDeliverName;
  }
  
  public void setRoleDeliverName(String roleDeliverName) {
    this.roleDeliverName = roleDeliverName;
  }
  
  public String getRoleRecieveName() {
    return this.roleRecieveName;
  }
  
  public void setRoleRecieveName(String roleRecieveName) {
    this.roleRecieveName = roleRecieveName;
  }
  
  public String getRoleHandDate() {
    return this.roleHandDate;
  }
  
  public void setRoleHandDate(String roleHandDate) {
    this.roleHandDate = roleHandDate;
  }
  
  public String getRoleHandTransactor() {
    return this.roleHandTransactor;
  }
  
  public void setRoleHandTransactor(String roleHandTransactor) {
    this.roleHandTransactor = roleHandTransactor;
  }
}
