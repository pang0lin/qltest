package com.qq.weixin.mp.pojo.menu;

public class Pic_SysphotoButton extends Button {
  private String type = "pic_sysphoto";
  
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
