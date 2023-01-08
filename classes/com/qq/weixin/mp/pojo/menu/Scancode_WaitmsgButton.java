package com.qq.weixin.mp.pojo.menu;

public class Scancode_WaitmsgButton extends Button {
  private String type = "scancode_waitmsg";
  
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
