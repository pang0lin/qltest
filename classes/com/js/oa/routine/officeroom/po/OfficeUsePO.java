package com.js.oa.routine.officeroom.po;

import java.io.Serializable;
import java.util.Date;

public class OfficeUsePO implements Serializable {
  private Long applayId;
  
  private String applayNumber;
  
  private String applayBuildId;
  
  private String applayBuildNumber;
  
  private String applayClass;
  
  private String applayUsername;
  
  private String applayUserId;
  
  private String applayReason;
  
  private String applayTitle;
  
  private String applaySex;
  
  private String applayOrg;
  
  private String applayOrgName;
  
  private String visitName;
  
  private String visitWorkunit;
  
  private String visitOrg;
  
  private String visitOrgName;
  
  private Date visitBeginTime = null;
  
  private Date visitEndTime = null;
  
  private String bz;
  
  private String applayIsUse;
  
  private Long applayStation;
  
  private String applayKth;
  
  private String applayIsoverdue;
  
  public OfficeUsePO(String applayNumber, String applayClass, String applayUsername, String applayUserId, String applayReason, String applayTitle, String applaySex, String applayOrg, String visitName, String visitWorkunit, String visitOrg, String bz, Date visitBeginTime, Date visitEndTime, String applayBuildId, String applayBuildNumber, String applayOrgName, String visitOrgName, Long applayStation, String applayIsUse, String applayKth, String applayIsoverdue) {
    this.applayNumber = applayNumber;
    this.applayClass = applayClass;
    this.applayUsername = applayUsername;
    this.applayUserId = applayUserId;
    this.applayReason = applayReason;
    this.applayTitle = applayTitle;
    this.applaySex = applaySex;
    this.applayOrg = applayOrg;
    this.visitName = visitName;
    this.visitWorkunit = visitWorkunit;
    this.visitOrg = visitOrg;
    this.visitBeginTime = visitBeginTime;
    this.bz = bz;
    this.visitEndTime = visitEndTime;
    this.applayBuildId = applayBuildId;
    this.applayBuildNumber = applayBuildNumber;
    this.applayOrgName = applayOrgName;
    this.visitOrgName = visitOrgName;
    this.applayStation = applayStation;
    this.applayIsUse = applayIsUse;
    this.applayKth = applayKth;
    this.applayIsoverdue = applayIsoverdue;
  }
  
  public OfficeUsePO() {}
  
  public Long getApplayId() {
    return this.applayId;
  }
  
  public void setApplayId(Long applayId) {
    this.applayId = applayId;
  }
  
  public String getApplayNumber() {
    return this.applayNumber;
  }
  
  public void setApplayNumber(String applayNumber) {
    this.applayNumber = applayNumber;
  }
  
  public String getApplayClass() {
    return this.applayClass;
  }
  
  public void setApplayClass(String applayClass) {
    this.applayClass = applayClass;
  }
  
  public String getApplayUsername() {
    return this.applayUsername;
  }
  
  public void setApplayUsername(String applayUsername) {
    this.applayUsername = applayUsername;
  }
  
  public String getApplayUserId() {
    return this.applayUserId;
  }
  
  public void setApplayUserId(String applayUserId) {
    this.applayUserId = applayUserId;
  }
  
  public String getApplayReason() {
    return this.applayReason;
  }
  
  public void setApplayReason(String applayReason) {
    this.applayReason = applayReason;
  }
  
  public String getApplayTitle() {
    return this.applayTitle;
  }
  
  public void setApplayTitle(String applayTitle) {
    this.applayTitle = applayTitle;
  }
  
  public String getApplaySex() {
    return this.applaySex;
  }
  
  public void setApplaySex(String applaySex) {
    this.applaySex = applaySex;
  }
  
  public String getApplayOrg() {
    return this.applayOrg;
  }
  
  public void setApplayOrg(String applayOrg) {
    this.applayOrg = applayOrg;
  }
  
  public String getVisitName() {
    return this.visitName;
  }
  
  public void setVisitName(String visitName) {
    this.visitName = visitName;
  }
  
  public String getVisitWorkunit() {
    return this.visitWorkunit;
  }
  
  public void setVisitWorkunit(String visitWorkunit) {
    this.visitWorkunit = visitWorkunit;
  }
  
  public String getVisitOrg() {
    return this.visitOrg;
  }
  
  public void setVisitOrg(String visitOrg) {
    this.visitOrg = visitOrg;
  }
  
  public Date getVisitBeginTime() {
    return this.visitBeginTime;
  }
  
  public void setVisitBeginTime(Date visitBeginTime) {
    this.visitBeginTime = visitBeginTime;
  }
  
  public Date getVisitEndTime() {
    return this.visitEndTime;
  }
  
  public void setVisitEndTime(Date visitEndTime) {
    this.visitEndTime = visitEndTime;
  }
  
  public String getBz() {
    return this.bz;
  }
  
  public void setBz(String bz) {
    this.bz = bz;
  }
  
  public String getApplayBuildId() {
    return this.applayBuildId;
  }
  
  public void setApplayBuildId(String applayBuildId) {
    this.applayBuildId = applayBuildId;
  }
  
  public String getApplayBuildNumber() {
    return this.applayBuildNumber;
  }
  
  public void setApplayBuildNumber(String applayBuildNumber) {
    this.applayBuildNumber = applayBuildNumber;
  }
  
  public String getApplayOrgName() {
    return this.applayOrgName;
  }
  
  public void setApplayOrgName(String applayOrgName) {
    this.applayOrgName = applayOrgName;
  }
  
  public String getVisitOrgName() {
    return this.visitOrgName;
  }
  
  public void setVisitOrgName(String visitOrgName) {
    this.visitOrgName = visitOrgName;
  }
  
  public Long getApplayStation() {
    return this.applayStation;
  }
  
  public void setApplayStation(Long applayStation) {
    this.applayStation = applayStation;
  }
  
  public String getApplayIsUse() {
    return this.applayIsUse;
  }
  
  public void setApplayIsUse(String applayIsUse) {
    this.applayIsUse = applayIsUse;
  }
  
  public String getApplayKth() {
    return this.applayKth;
  }
  
  public void setApplayKth(String applayKth) {
    this.applayKth = applayKth;
  }
  
  public String getApplayIsoverdue() {
    return this.applayIsoverdue;
  }
  
  public void setApplayIsoverdue(String applayIsoverdue) {
    this.applayIsoverdue = applayIsoverdue;
  }
}
