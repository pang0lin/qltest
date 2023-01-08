package com.qq.weixin.mp.config.pojo;

public class Pic_SysphotoMenu extends Menu {
  private String type = "";
  
  private String key = "";
  
  private String url = "";
  
  public void Pic_SysphotoMenu() {
    this.type = "pic_sysphoto";
  }
  
  public String getType() {
    return this.type;
  }
  
  public void setType(String type) {
    this.type = type;
  }
  
  public String getKey() {
    return this.key;
  }
  
  public void setKey(String key) {
    this.key = key;
  }
  
  public String getUrl() {
    return this.url;
  }
  
  public void setUrl(String url) {
    this.url = url;
  }
}
