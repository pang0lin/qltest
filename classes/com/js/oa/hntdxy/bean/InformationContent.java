package com.js.oa.hntdxy.bean;

public class InformationContent {
  private String infoId;
  
  private String title;
  
  private String createtime;
  
  private String content;
  
  public InformationContent() {}
  
  public InformationContent(String infoId, String title, String createtime, String content) {
    this.infoId = infoId;
    this.title = title;
    this.createtime = createtime;
    this.content = content;
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
  
  public String getContent() {
    return this.content;
  }
  
  public void setContent(String content) {
    this.content = content;
  }
  
  public String getCreatetime() {
    return this.createtime;
  }
  
  public void setCreatetime(String createtime) {
    this.createtime = createtime;
  }
}
