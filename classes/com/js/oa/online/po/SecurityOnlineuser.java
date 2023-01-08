package com.js.oa.online.po;

import java.io.Serializable;
import java.util.Date;

public class SecurityOnlineuser implements Serializable {
  private Long id;
  
  private Long userId;
  
  private String sessionId;
  
  private String userName;
  
  private String userAccount;
  
  private String userIp;
  
  private String serverIp;
  
  private Long userStatus;
  
  private Date userLogtime = null;
  
  private Date userUpdatetime = null;
  
  private Integer domainId;
  
  private String dutyName;
  
  private String orgName;
  
  private Integer ishelper;
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public Long getUserId() {
    return this.userId;
  }
  
  public void setUserId(Long userId) {
    this.userId = userId;
  }
  
  public String getSessionId() {
    return this.sessionId;
  }
  
  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }
  
  public String getUserName() {
    return this.userName;
  }
  
  public void setUserName(String userName) {
    this.userName = userName;
  }
  
  public String getUserAccount() {
    return this.userAccount;
  }
  
  public void setUserAccount(String userAccount) {
    this.userAccount = userAccount;
  }
  
  public String getUserIp() {
    return this.userIp;
  }
  
  public void setUserIp(String userIp) {
    this.userIp = userIp;
  }
  
  public String getServerIp() {
    return this.serverIp;
  }
  
  public void setServerIp(String serverIp) {
    this.serverIp = serverIp;
  }
  
  public Long getUserStatus() {
    return this.userStatus;
  }
  
  public void setUserStatus(Long userStatus) {
    this.userStatus = userStatus;
  }
  
  public Date getUserLogtime() {
    return this.userLogtime;
  }
  
  public void setUserLogtime(Date userLogtime) {
    this.userLogtime = userLogtime;
  }
  
  public Date getUserUpdatetime() {
    return this.userUpdatetime;
  }
  
  public void setUserUpdatetime(Date userUpdatetime) {
    this.userUpdatetime = userUpdatetime;
  }
  
  public Integer getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(Integer domainId) {
    this.domainId = domainId;
  }
  
  public String getDutyName() {
    return this.dutyName;
  }
  
  public void setDutyName(String dutyName) {
    this.dutyName = dutyName;
  }
  
  public String getOrgName() {
    return this.orgName;
  }
  
  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }
  
  public Integer getIshelper() {
    return this.ishelper;
  }
  
  public void setIshelper(Integer ishelper) {
    this.ishelper = ishelper;
  }
}
