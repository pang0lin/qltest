package com.qq.weixin.mp.pojo.push;

public class VoicePush extends Push {
  private final String msgtype = "voice";
  
  private Voice voice;
  
  public Voice getVoice() {
    return this.voice;
  }
  
  public void setVoice(Voice voice) {
    this.voice = voice;
  }
  
  public String getMsgtype() {
    return "voice";
  }
  
  public class Voice {
    private String media_id;
    
    public String getMedia_id() {
      return this.media_id;
    }
    
    public void setMedia_id(String media_id) {
      this.media_id = media_id;
    }
  }
}
