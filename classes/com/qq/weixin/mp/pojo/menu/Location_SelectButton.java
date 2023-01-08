package com.qq.weixin.mp.pojo.menu;

public class Location_SelectButton extends Button {
  private String type = "location_select";
  
  private String key;
  
  public String getType() {
    return this.type;
  }
  
  public String getKey() {
    return this.key;
  }
  
  public void setKey(String key) {
    this.key = key;
  }
}
