package com.qq.weixin.mp.pojo.push;

public class ImagePush extends Push {
  private final String msgtype = "image";
  
  private Image Image;
  
  public Image getImage() {
    return this.Image;
  }
  
  public void setImage(Image image) {
    this.Image = image;
  }
  
  public String getMsgtype() {
    return "image";
  }
  
  public class Image {
    private String media_id;
    
    public String getMedia_id() {
      return this.media_id;
    }
    
    public void setMedia_id(String media_id) {
      this.media_id = media_id;
    }
  }
}
