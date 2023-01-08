package com.js.system.vo.rolemanager;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class HandRoleVO implements Serializable {
  private String roleRecieveName;
  
  private Date roleHandDate = null;
  
  private String roleHandTransactor;
  
  private long handRoleId;
  
  private long roleDeliverId;
  
  private Set role = null;
  
  private String roleDeliverName;
  
  private long roleRecieveId;
  
  private String createdOrg;
  
  private String createdEmp;
  
  public long getHandRoleId() {
    return this.handRoleId;
  }
  
  public void setHandRoleId(long handRoleId) {
    this.handRoleId = handRoleId;
  }
  
  public long getRoleDeliverId() {
    return this.roleDeliverId;
  }
  
  public void setRoleDeliverId(long roleDeliverId) {
    this.roleDeliverId = roleDeliverId;
  }
  
  public long getRoleRecieveId() {
    return this.roleRecieveId;
  }
  
  public void setRoleRecieveId(long roleRecieveId) {
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
  
  public Date getRoleHandDate() {
    return this.roleHandDate;
  }
  
  public void setRoleHandDate(Date roleHandDate) {
    this.roleHandDate = roleHandDate;
  }
  
  public String getRoleHandTransactor() {
    return this.roleHandTransactor;
  }
  
  public void setRoleHandTransactor(String roleHandTransactor) {
    this.roleHandTransactor = roleHandTransactor;
  }
  
  public Set getRole() {
    return this.role;
  }
  
  public void setRole(Set role) {
    this.role = role;
  }
  
  public String getCreatedOrg() {
    return this.createdOrg;
  }
  
  public void setCreatedOrg(String createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public String getCreatedEmp() {
    return this.createdEmp;
  }
  
  public void setCreatedEmp(String createdEmp) {
    this.createdEmp = createdEmp;
  }
}
