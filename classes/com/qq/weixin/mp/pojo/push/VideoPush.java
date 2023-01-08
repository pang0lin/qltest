package com.qq.weixin.mp.pojo.push;

public class VideoPush extends Push {
  private final String msgtype = "video";
  
  private Video video;
  
  public Video getVideo() {
    return this.video;
  }
  
  public void setVideo(Video video) {
    this.video = video;
  }
  
  public String getMsgtype() {
    return "video";
  }
  
  public class Video extends TitleDescription {
    private String media_id;
    
    public String getMedia_id() {
      return this.media_id;
    }
    
    public void setMedia_id(String media_id) {
      this.media_id = media_id;
    }
  }
}
