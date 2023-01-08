package com.js.oa.routine.officeroom.po;

import java.io.Serializable;

public class OfficePO implements Serializable {
  private Long officeId;
  
  private String officename;
  
  private String officenumber;
  
  private Long officearea;
  
  private String officefaces;
  
  private Long buidId;
  
  private String buildname;
  
  private String buidnumber;
  
  private String officereader;
  
  private String officereadorg;
  
  private String officereadgroup;
  
  private String officereadname;
  
  private Long createdemp;
  
  private String createdempname;
  
  private String bz;
  
  private Long officestation;
  
  private String officeAdminId;
  
  private String officeAdminName;
  
  private String officeIsUse;
  
  public OfficePO(String officename, String officenumber, Long officearea, String officefaces, Long buidId, String buidnumber, String officereader, String officereadorg, String officereadgroup, String officereadname, Long createdemp, String bz, String createdempname, String buildname, Long officestation, String officeAdminId, String officeAdminName, String officeIsUse) {
    this.officename = officename;
    this.officenumber = officenumber;
    this.officearea = officearea;
    this.officefaces = officefaces;
    this.buidId = buidId;
    this.buildname = buildname;
    this.buidnumber = buidnumber;
    this.officereader = officereader;
    this.officereadorg = officereadorg;
    this.officereadgroup = officereadgroup;
    this.officereadname = officereadname;
    this.createdemp = createdemp;
    this.bz = bz;
    this.createdempname = createdempname;
    this.officestation = officestation;
    this.officeAdminId = officeAdminId;
    this.officeAdminName = officeAdminName;
    this.officeIsUse = officeIsUse;
  }
  
  public OfficePO() {}
  
  public Long getOfficeId() {
    return this.officeId;
  }
  
  public void setOfficeId(Long officeId) {
    this.officeId = officeId;
  }
  
  public String getOfficename() {
    return this.officename;
  }
  
  public void setOfficename(String officename) {
    this.officename = officename;
  }
  
  public String getOfficenumber() {
    return this.officenumber;
  }
  
  public void setOfficenumber(String officenumber) {
    this.officenumber = officenumber;
  }
  
  public Long getOfficearea() {
    return this.officearea;
  }
  
  public void setOfficearea(Long officearea) {
    this.officearea = officearea;
  }
  
  public String getOfficefaces() {
    return this.officefaces;
  }
  
  public void setOfficefaces(String officefaces) {
    this.officefaces = officefaces;
  }
  
  public Long getBuidId() {
    return this.buidId;
  }
  
  public void setBuidId(Long buidId) {
    this.buidId = buidId;
  }
  
  public String getBuidnumber() {
    return this.buidnumber;
  }
  
  public void setBuidnumber(String buidnumber) {
    this.buidnumber = buidnumber;
  }
  
  public String getOfficereader() {
    return this.officereader;
  }
  
  public void setOfficereader(String officereader) {
    this.officereader = officereader;
  }
  
  public String getOfficereadorg() {
    return this.officereadorg;
  }
  
  public void setOfficereadorg(String officereadorg) {
    this.officereadorg = officereadorg;
  }
  
  public String getOfficereadgroup() {
    return this.officereadgroup;
  }
  
  public void setOfficereadgroup(String officereadgroup) {
    this.officereadgroup = officereadgroup;
  }
  
  public String getOfficereadname() {
    return this.officereadname;
  }
  
  public void setOfficereadname(String officereadname) {
    this.officereadname = officereadname;
  }
  
  public Long getCreatedemp() {
    return this.createdemp;
  }
  
  public void setCreatedemp(Long createdemp) {
    this.createdemp = createdemp;
  }
  
  public String getCreatedempname() {
    return this.createdempname;
  }
  
  public void setCreatedempname(String createdempname) {
    this.createdempname = createdempname;
  }
  
  public String getBz() {
    return this.bz;
  }
  
  public void setBz(String bz) {
    this.bz = bz;
  }
  
  public String getBuildname() {
    return this.buildname;
  }
  
  public void setBuildname(String buildname) {
    this.buildname = buildname;
  }
  
  public Long getOfficestation() {
    return this.officestation;
  }
  
  public void setOfficestation(Long officestation) {
    this.officestation = officestation;
  }
  
  public String getOfficeAdminId() {
    return this.officeAdminId;
  }
  
  public void setOfficeAdminId(String officeAdminId) {
    this.officeAdminId = officeAdminId;
  }
  
  public String getOfficeAdminName() {
    return this.officeAdminName;
  }
  
  public void setOfficeAdminName(String officeAdminName) {
    this.officeAdminName = officeAdminName;
  }
  
  public String getOfficeIsUse() {
    return this.officeIsUse;
  }
  
  public void setOfficeIsUse(String officeIsUse) {
    this.officeIsUse = officeIsUse;
  }
}
