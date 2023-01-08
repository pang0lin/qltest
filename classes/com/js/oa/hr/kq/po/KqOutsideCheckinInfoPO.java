package com.js.oa.hr.kq.po;

import java.io.Serializable;
import java.util.Date;

public class KqOutsideCheckinInfoPO implements Serializable {
  private long id;
  
  private String userId;
  
  private Date checkinTime = null;
  
  private String weidu;
  
  private String jingdu;
  
  private String position;
  
  private String imageurl;
  
  private String customName;
  
  private String customCompany;
  
  private String reason;
  
  private String customAddress;
  
  private String customType;
  
  private String customBz;
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public String getUserId() {
    return this.userId;
  }
  
  public void setUserId(String userId) {
    this.userId = userId;
  }
  
  public Date getCheckinTime() {
    return this.checkinTime;
  }
  
  public void setCheckinTime(Date checkinTime) {
    this.checkinTime = checkinTime;
  }
  
  public String getWeidu() {
    return this.weidu;
  }
  
  public void setWeidu(String weidu) {
    this.weidu = weidu;
  }
  
  public String getJingdu() {
    return this.jingdu;
  }
  
  public void setJingdu(String jingdu) {
    this.jingdu = jingdu;
  }
  
  public String getPosition() {
    return this.position;
  }
  
  public void setPosition(String position) {
    this.position = position;
  }
  
  public String getImageurl() {
    return this.imageurl;
  }
  
  public void setImageurl(String imageurl) {
    this.imageurl = imageurl;
  }
  
  public String getCustomName() {
    return this.customName;
  }
  
  public void setCustomName(String customName) {
    this.customName = customName;
  }
  
  public String getCustomCompany() {
    return this.customCompany;
  }
  
  public void setCustomCompany(String customCompany) {
    this.customCompany = customCompany;
  }
  
  public String getReason() {
    return this.reason;
  }
  
  public void setReason(String reason) {
    this.reason = reason;
  }
  
  public String getCustomAddress() {
    return this.customAddress;
  }
  
  public void setCustomAddress(String customAddress) {
    this.customAddress = customAddress;
  }
  
  public String getCustomType() {
    return this.customType;
  }
  
  public void setCustomType(String customType) {
    this.customType = customType;
  }
  
  public String getCustomBz() {
    return this.customBz;
  }
  
  public void setCustomBz(String customBz) {
    this.customBz = customBz;
  }
  
  public KqOutsideCheckinInfoPO() {}
  
  public KqOutsideCheckinInfoPO(long id, String userId, Date checkinTime, String weidu, String jingdu, String position, String imageurl, String customName, String customCompany, String reason, String customAddress, String customType, String customBz) {
    this.id = id;
    this.userId = userId;
    this.checkinTime = checkinTime;
    this.weidu = weidu;
    this.jingdu = jingdu;
    this.position = position;
    this.imageurl = imageurl;
    this.customName = customName;
    this.customCompany = customCompany;
    this.reason = reason;
    this.customAddress = customAddress;
    this.customType = customType;
    this.customBz = customBz;
  }
}
