package com.qq.weixin.mp.pojo;

public class PositionInfo {
  long createTime;
  
  private String location_X;
  
  private String location_Y;
  
  private String label;
  
  public long getCreateTime() {
    return this.createTime;
  }
  
  public void setCreateTime(long createTime) {
    this.createTime = createTime;
  }
  
  public String getLocation_X() {
    return this.location_X;
  }
  
  public void setLocation_X(String location_X) {
    this.location_X = location_X;
  }
  
  public String getLocation_Y() {
    return this.location_Y;
  }
  
  public void setLocation_Y(String location_Y) {
    this.location_Y = location_Y;
  }
  
  public String getLabel() {
    return this.label;
  }
  
  public void setLabel(String label) {
    this.label = label;
  }
}
