package com.qq.weixin.mp.pojo.push;

public class TextPush extends Push {
  private final String msgtype = "text";
  
  private Text text;
  
  public String getMsgtype() {
    return "text";
  }
  
  public Text getText() {
    return this.text;
  }
  
  public void setText(Text text) {
    this.text = text;
  }
  
  public class Text {
    private String content;
    
    public String getContent() {
      return this.content;
    }
    
    public void setContent(String content) {
      this.content = content;
    }
  }
}
