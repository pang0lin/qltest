package com.ali.dingding.message;

public class ImageMessage extends Message {
  public String media_id;
  
  public ImageMessage(String mediaId) {
    this.media_id = mediaId;
  }
  
  public String type() {
    return "image";
  }
}
