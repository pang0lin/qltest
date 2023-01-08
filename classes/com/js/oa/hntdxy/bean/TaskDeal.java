package com.js.oa.hntdxy.bean;

public class TaskDeal {
  private String infoId;
  
  private String title;
  
  private String createtime;
  
  private String url;
  
  public TaskDeal() {}
  
  public TaskDeal(String infoId, String title, String createtime) {
    this.infoId = infoId;
    this.title = title;
    this.createtime = createtime;
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
