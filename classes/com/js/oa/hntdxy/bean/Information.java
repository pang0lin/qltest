package com.js.oa.hntdxy.bean;

public class Information {
  private String infoId;
  
  private String title;
  
  private String createtime;
  
  private String url;
  
  public Information() {}
  
  public Information(String infoId, String title, String createtime, String url) {
    this.infoId = infoId;
    this.title = title;
    this.createtime = createtime;
    this.url = url;
  }
  
  public String getUrl() {
    return this.url;
  }
  
  public void setUrl(String url) {
    this.url = url;
  }
  
  public String getInfoId() {
    return this.infoId;
  }
  
  public void setInfoId(String infoId) {
    this.infoId = infoId;
  }
  
  public String getTitle() {
    return this.title;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  public String getCreatetime() {
    return this.createtime;
  }
  
  public void setCreatetime(String createtime) {
    this.createtime = createtime;
  }
}
