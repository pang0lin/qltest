package com.ali.dingding.message;

public class TextMessage extends Message {
  public String content;
  
  public TextMessage(String content) {
    this.content = content;
  }
  
  public String type() {
    return "text";
  }
}
