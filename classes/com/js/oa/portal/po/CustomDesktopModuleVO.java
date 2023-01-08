package com.js.oa.portal.po;

import java.util.List;

public class CustomDesktopModuleVO {
  private String title;
  
  private String description;
  
  private String link;
  
  private String imageName;
  
  private String imageNewsTitle;
  
  private String imageNewsTitleLink;
  
  private List ItemList = null;
  
  private String urlname;
  
  public String getTitle() {
    return this.title;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  public String getDescription() {
    return this.description;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
  
  public String getLink() {
    return this.link;
  }
  
  public String getImageNewsTitleLink() {
    return this.imageNewsTitleLink;
  }
  
  public String getImageNewsTitle() {
    return this.imageNewsTitle;
  }
  
  public String getImageName() {
    return this.imageName;
  }
  
  public void setLink(String link) {
    this.link = link;
  }
  
  public void setImageName(String imageName) {
    this.imageName = imageName;
  }
  
  public void setImageNewsTitle(String imageNewsTitle) {
    this.imageNewsTitle = imageNewsTitle;
  }
  
  public void setImageNewsTitleLink(String imageNewsTitleLink) {
    this.imageNewsTitleLink = imageNewsTitleLink;
  }
  
  public List getItemList() {
    return this.ItemList;
  }
  
  public void setItemList(List ItemList) {
    this.ItemList = ItemList;
  }
  
  public String getUrlname() {
    return this.urlname;
  }
  
  public void setUrlname(String urlname) {
    this.urlname = urlname;
  }
}
