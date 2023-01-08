package com.qq.weixin.mp.pojo.menu;

public class ViewButton extends Button {
  private String type = "view";
  
  private String url;
  
  public String getType() {
    return this.type;
  }
  
  public String getUrl() {
    return this.url;
  }
  
  public void setUrl(String url) {
    this.url = url;
  }
}
