package com.js.oa.hr.kq.bry;

import com.js.oa.hr.kq.bry.util.BryGouZao;

public class BryKq {
  private Long userId;
  
  private String userName;
  
  private Long orgId;
  
  private String orgName;
  
  private String userNumber;
  
  private String kqDate;
  
  private Integer kqDateType;
  
  private String sbKqTime;
  
  private Integer sbKqType;
  
  private Long sbKqLong;
  
  private String xbKqTime;
  
  private Integer xbKqType;
  
  private Long xbKqLong;
  
  private String kqRemark;
  
  private Integer sbRemark;
  
  private Integer xbRemark;
  
  public BryKq() {}
  
  public BryKq(Long userId, String userName, Long orgId, String orgName, String userNumber, String kqDate, String sbKqTime, Integer sbKqType, String xbKqTime, Integer xbKqType) {
    setUserId(userId);
    setUserName(userName);
    setOrgId(orgId);
    setOrgName(orgName);
    setUserNumber(userNumber);
    setKqDate(kqDate);
    setSbKqTime(sbKqTime);
    setSbKqType(sbKqType);
    setSbKqLong(BryGouZao.getTimeLong(String.valueOf(kqDate) + " " + sbKqTime));
    setXbKqTime(xbKqTime);
    setXbKqType(xbKqType);
    setXbKqLong(BryGouZao.getTimeLong(String.valueOf(kqDate) + " " + xbKqTime));
    setSbRemark(sbKqType);
    setXbRemark(xbKqType);
  }
  
  public BryKq(Long userId, String userName, Long orgId, String orgName, String userNumber, String kqDate) {
    setUserId(userId);
    setUserName(userName);
    setOrgId(orgId);
    setOrgName(orgName);
    setUserNumber(userNumber);
    setKqDate(kqDate);
    setSbKqTime("08:30:00");
    setSbKqType(Integer.valueOf(0));
    setSbKqLong(BryGouZao.getTimeLong(String.valueOf(kqDate) + " " + getSbKqTime()));
    setXbKqTime("17:15:00");
    setXbKqType(Integer.valueOf(0));
    setXbKqLong(BryGouZao.getTimeLong(String.valueOf(kqDate) + " " + getXbKqTime()));
  }
  
  public Long getUserId() {
    return this.userId;
  }
  
  public void setUserId(Long userId) {
    this.userId = userId;
  }
  
  public String getUserName() {
    return this.userName;
  }
  
  public void setUserName(String userName) {
    this.userName = userName;
  }
  
  public Long getOrgId() {
    return this.orgId;
  }
  
  public void setOrgId(Long orgId) {
    this.orgId = orgId;
  }
  
  public String getOrgName() {
    return this.orgName;
  }
  
  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }
  
  public String getUserNumber() {
    return this.userNumber;
  }
  
  public void setUserNumber(String userNumber) {
    this.userNumber = userNumber;
  }
  
  public String getKqDate() {
    return this.kqDate;
  }
  
  public void setKqDate(String kqDate) {
    this.kqDate = kqDate;
  }
  
  public Integer getKqDateType() {
    return this.kqDateType;
  }
  
  public void setKqDateType(Integer kqDateType) {
    this.kqDateType = kqDateType;
  }
  
  public String getSbKqTime() {
    return this.sbKqTime;
  }
  
  public void setSbKqTime(String sbKqTime) {
    this.sbKqTime = sbKqTime;
  }
  
  public Integer getSbKqType() {
    return this.sbKqType;
  }
  
  public void setSbKqType(Integer sbKqType) {
    this.sbKqType = sbKqType;
  }
  
  public Long getSbKqLong() {
    return this.sbKqLong;
  }
  
  public void setSbKqLong(Long sbKqLong) {
    this.sbKqLong = sbKqLong;
  }
  
  public String getXbKqTime() {
    return this.xbKqTime;
  }
  
  public void setXbKqTime(String xbKqTime) {
    this.xbKqTime = xbKqTime;
  }
  
  public Integer getXbKqType() {
    return this.xbKqType;
  }
  
  public void setXbKqType(Integer xbKqType) {
    this.xbKqType = xbKqType;
  }
  
  public Long getXbKqLong() {
    return this.xbKqLong;
  }
  
  public void setXbKqLong(Long xbKqLong) {
    this.xbKqLong = xbKqLong;
  }
  
  public String getKqRemark() {
    return this.kqRemark;
  }
  
  public void setKqRemark(String kqRemark) {
    this.kqRemark = kqRemark;
  }
  
  public Integer getSbRemark() {
    return this.sbRemark;
  }
  
  public void setSbRemark(Integer sbRemark) {
    this.sbRemark = sbRemark;
  }
  
  public Integer getXbRemark() {
    return this.xbRemark;
  }
  
  public void setXbRemark(Integer xbRemark) {
    this.xbRemark = xbRemark;
  }
}
