package com.js.system.util;

import java.util.HashMap;

public class EndowVO {
  private HashMap orgIdAndName = null;
  
  private HashMap empIdAndName = null;
  
  private HashMap groupIdAndName = null;
  
  private String orgIdArray;
  
  private String orgNameArray;
  
  private String empIdArray;
  
  private String empNameArray;
  
  private String groupIdArray;
  
  private String groupNameArray;
  
  public HashMap getEmpIdAndName() {
    return this.empIdAndName;
  }
  
  public HashMap getGroupIdAndName() {
    return this.groupIdAndName;
  }
  
  public HashMap getOrgIdAndName() {
    return this.orgIdAndName;
  }
  
  public void setOrgIdAndName(HashMap orgIdAndName) {
    this.orgIdAndName = orgIdAndName;
  }
  
  public void setGroupIdAndName(HashMap groupIdAndName) {
    this.groupIdAndName = groupIdAndName;
  }
  
  public void setEmpIdAndName(HashMap empIdAndName) {
    this.empIdAndName = empIdAndName;
  }
  
  public String getEmpIdArray() {
    return this.empIdArray;
  }
  
  public String getEmpNameArray() {
    return this.empNameArray;
  }
  
  public String getGroupIdArray() {
    return this.groupIdArray;
  }
  
  public String getGroupNameArray() {
    return this.groupNameArray;
  }
  
  public String getOrgIdArray() {
    return this.orgIdArray;
  }
  
  public String getOrgNameArray() {
    return this.orgNameArray;
  }
  
  public void setOrgNameArray(String orgNameArray) {
    this.orgNameArray = orgNameArray;
  }
  
  public void setOrgIdArray(String orgIdArray) {
    this.orgIdArray = orgIdArray;
  }
  
  public void setGroupNameArray(String groupNameArray) {
    this.groupNameArray = groupNameArray;
  }
  
  public void setGroupIdArray(String groupIdArray) {
    this.groupIdArray = groupIdArray;
  }
  
  public void setEmpNameArray(String empNameArray) {
    this.empNameArray = empNameArray;
  }
  
  public void setEmpIdArray(String empIdArray) {
    this.empIdArray = empIdArray;
  }
}
