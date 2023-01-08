package com.qq.weixin.mp.message.req;

public class ImageMessage extends BaseMessage {
  private String PicUrl;
  
  public String getPicUrl() {
    return this.PicUrl;
  }
  
  public void setPicUrl(String picUrl) {
    this.PicUrl = picUrl;
  }
}
