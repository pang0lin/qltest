package com.js.oa.box.po;

public class BoxPO {
  private Long id;
  
  private String netStr;
  
  private String userID;
  
  private String saveImg;
  
  private String synopsis;
  
  private String Name;
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getUserID() {
    return this.userID;
  }
  
  public void setUserID(String userID) {
    this.userID = userID;
  }
  
  public String getNetStr() {
    return this.netStr;
  }
  
  public void setNetStr(String netStr) {
    this.netStr = netStr;
  }
  
  public String getSaveImg() {
    return this.saveImg;
  }
  
  public void setSaveImg(String saveImg) {
    this.saveImg = saveImg;
  }
  
  public String getSynopsis() {
    return this.synopsis;
  }
  
  public void setSynopsis(String synopsis) {
    this.synopsis = synopsis;
  }
  
  public String getName() {
    return this.Name;
  }
  
  public void setName(String name) {
    this.Name = name;
  }
  
  public BoxPO(Long id, String netStr, String userID, String saveImg, String synopsis, String Name) {
    this.id = id;
    this.netStr = netStr;
    this.userID = userID;
    this.saveImg = saveImg;
    this.synopsis = synopsis;
    this.Name = Name;
  }
  
  public BoxPO() {}
}
