package com.js.oa.routine.officeroom.po;

import java.io.Serializable;

public class OfficeBuildPO implements Serializable {
  private Long buidId;
  
  private String buildname;
  
  private String buildparentid;
  
  private String buildlevel;
  
  private String buildidaccount;
  
  private String buildidstring;
  
  private String buildreader;
  
  private String buildreadorg;
  
  private String buildreadgroup;
  
  private String buildreadname;
  
  private Long createdemp;
  
  private String createdempname;
  
  private String bz;
  
  private String buildAdminId;
  
  private String buildAdminName;
  
  public OfficeBuildPO(String buildname, String buildparentid, String buildlevel, String buildidaccount, String buildidstring, String buildreader, String buildreadorg, String buildreadgroup, String buildreadname, Long createdemp, String bz, String createdempname, String buildAdminId, String buildAdminName) {
    this.buildname = buildname;
    this.buildparentid = buildparentid;
    this.buildlevel = buildlevel;
    this.buildidaccount = buildidaccount;
    this.buildidstring = buildidstring;
    this.buildreader = buildreader;
    this.buildreadorg = buildreadorg;
    this.buildreadgroup = buildreadgroup;
    this.buildreadname = buildreadname;
    this.createdemp = createdemp;
    this.bz = bz;
    this.createdempname = createdempname;
    this.buildAdminId = buildAdminId;
    this.buildAdminName = buildAdminName;
  }
  
  public OfficeBuildPO() {}
  
  public String getCreatedempname() {
    return this.createdempname;
  }
  
  public void setCreatedempname(String createdempname) {
    this.createdempname = createdempname;
  }
  
  public Long getBuidId() {
    return this.buidId;
  }
  
  public void setBuidId(Long buidId) {
    this.buidId = buidId;
  }
  
  public String getBuildname() {
    return this.buildname;
  }
  
  public void setBuildname(String buildname) {
    this.buildname = buildname;
  }
  
  public String getBuildparentid() {
    return this.buildparentid;
  }
  
  public void setBuildparentid(String buildparentid) {
    this.buildparentid = buildparentid;
  }
  
  public String getBuildlevel() {
    return this.buildlevel;
  }
  
  public void setBuildlevel(String buildlevel) {
    this.buildlevel = buildlevel;
  }
  
  public String getBuildidaccount() {
    return this.buildidaccount;
  }
  
  public void setBuildidaccount(String buildidaccount) {
    this.buildidaccount = buildidaccount;
  }
  
  public String getBuildidstring() {
    return this.buildidstring;
  }
  
  public void setBuildidstring(String buildidstring) {
    this.buildidstring = buildidstring;
  }
  
  public String getBuildreader() {
    return this.buildreader;
  }
  
  public void setBuildreader(String buildreader) {
    this.buildreader = buildreader;
  }
  
  public String getBuildreadorg() {
    return this.buildreadorg;
  }
  
  public void setBuildreadorg(String buildreadorg) {
    this.buildreadorg = buildreadorg;
  }
  
  public String getBuildreadgroup() {
    return this.buildreadgroup;
  }
  
  public void setBuildreadgroup(String buildreadgroup) {
    this.buildreadgroup = buildreadgroup;
  }
  
  public String getBuildreadname() {
    return this.buildreadname;
  }
  
  public void setBuildreadname(String buildreadname) {
    this.buildreadname = buildreadname;
  }
  
  public Long getCreatedemp() {
    return this.createdemp;
  }
  
  public void setCreatedemp(Long createdemp) {
    this.createdemp = createdemp;
  }
  
  public String getBz() {
    return this.bz;
  }
  
  public void setBz(String bz) {
    this.bz = bz;
  }
  
  public String getBuildAdminId() {
    return this.buildAdminId;
  }
  
  public void setBuildAdminId(String buildAdminId) {
    this.buildAdminId = buildAdminId;
  }
  
  public String getBuildAdminName() {
    return this.buildAdminName;
  }
  
  public void setBuildAdminName(String buildAdminName) {
    this.buildAdminName = buildAdminName;
  }
}
