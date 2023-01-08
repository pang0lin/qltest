package com.ali.dingding.message;

public class LinkMessage extends Message {
  public String messageUrl;
  
  public String picUrl;
  
  public String title;
  
  public String text;
  
  public LinkMessage(String messageUrl, String picUrl, String title, String text) {
    this.messageUrl = messageUrl;
    this.picUrl = picUrl;
    this.title = title;
    this.text = text;
  }
  
  public String type() {
    return "link";
  }
}
