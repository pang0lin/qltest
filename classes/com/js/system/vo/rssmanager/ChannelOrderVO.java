package com.js.system.vo.rssmanager;

import java.io.Serializable;

public class ChannelOrderVO implements Serializable {
  private Long orderId;
  
  private Long channelId;
  
  private Long userId;
  
  private String orderState;
  
  private String string1;
  
  private String string2;
  
  public Long getChannelId() {
    return this.channelId;
  }
  
  public void setChannelId(Long channelId) {
    this.channelId = channelId;
  }
  
  public Long getOrderId() {
    return this.orderId;
  }
  
  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }
  
  public String getOrderState() {
    return this.orderState;
  }
  
  public void setOrderState(String orderState) {
    this.orderState = orderState;
  }
  
  public String getString1() {
    return this.string1;
  }
  
  public void setString1(String string1) {
    this.string1 = string1;
  }
  
  public String getString2() {
    return this.string2;
  }
  
  public void setString2(String string2) {
    this.string2 = string2;
  }
  
  public Long getUserId() {
    return this.userId;
  }
  
  public void setUserId(Long userId) {
    this.userId = userId;
  }
}
