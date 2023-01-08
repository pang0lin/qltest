package com.js.oa.workplan.po;

import java.io.Serializable;

public class WorkplanGroupPO implements Serializable {
  private long id;
  
  private String groupName;
  
  private String groupLeaderName;
  
  private String groupLeaderId;
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public String getGroupName() {
    return this.groupName;
  }
  
  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }
  
  public String getGroupLeaderName() {
    return this.groupLeaderName;
  }
  
  public void setGroupLeaderName(String groupLeaderName) {
    this.groupLeaderName = groupLeaderName;
  }
  
  public String getGroupLeaderId() {
    return this.groupLeaderId;
  }
  
  public void setGroupLeaderId(String groupLeaderId) {
    this.groupLeaderId = groupLeaderId;
  }
}
