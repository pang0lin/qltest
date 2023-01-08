package com.qq.weixin.mp.pojo.push;

public class MusicPush extends Push {
  private final String msgtype = "music";
  
  private Music music;
  
  public Music getMusic() {
    return this.music;
  }
  
  public void setMusic(Music music) {
    this.music = music;
  }
  
  public String getMsgtype() {
    return "music";
  }
  
  public class Music {
    private String musicurl;
    
    private String hqmusicurl;
    
    private String thumb_media_id;
    
    public String getMusicurl() {
      return this.musicurl;
    }
    
    public void setMusicurl(String musicurl) {
      this.musicurl = musicurl;
    }
    
    public String getHqmusicurl() {
      return this.hqmusicurl;
    }
    
    public void setHqmusicurl(String hqmusicurl) {
      this.hqmusicurl = hqmusicurl;
    }
    
    public String getThumb_media_id() {
      return this.thumb_media_id;
    }
    
    public void setThumb_media_id(String thumb_media_id) {
      this.thumb_media_id = thumb_media_id;
    }
  }
}
