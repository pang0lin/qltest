package com.js.system.vo.rssmanager;

import java.io.Serializable;

public class ItemStateVO implements Serializable {
  private Long stateId;
  
  private Long itemId;
  
  private Long userId;
  
  private String readState;
  
  private String string1;
  
  private String string2;
  
  public Long getItemId() {
    return this.itemId;
  }
  
  public void setItemId(Long itemId) {
    this.itemId = itemId;
  }
  
  public String getReadState() {
    return this.readState;
  }
  
  public void setReadState(String readState) {
    this.readState = readState;
  }
  
  public Long getStateId() {
    return this.stateId;
  }
  
  public void setStateId(Long stateId) {
    this.stateId = stateId;
  }
  
  public String getString1() {
    return this.string1;
  }
  
  public void setString1(String string1) {
    this.string1 = string1;
  }
  
  public String getString2() {
    return this.string2;
  }
  
  public void setString2(String string2) {
    this.string2 = string2;
  }
  
  public Long getUserId() {
    return this.userId;
  }
  
  public void setUserId(Long userId) {
    this.userId = userId;
  }
}
