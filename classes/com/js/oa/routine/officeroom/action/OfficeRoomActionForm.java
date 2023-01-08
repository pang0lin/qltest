package com.js.oa.routine.officeroom.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class OfficeRoomActionForm extends ActionForm {
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
  
  private Long officeId;
  
  private String officename;
  
  private String officenumber;
  
  private Long officearea;
  
  private String officefaces;
  
  private String buidnumber;
  
  private String officereader;
  
  private String officereadorg;
  
  private String officereadgroup;
  
  private String officereadname;
  
  private Long officestation;
  
  private String officeAdminId;
  
  private String officeAdminName;
  
  private String officeIsUse;
  
  private Long applayId;
  
  private String applayNumber;
  
  private String applayBuildId;
  
  private String applayClass;
  
  private String applayUsername;
  
  private String applayUserId;
  
  private String applayReason;
  
  private String applayTitle;
  
  private String applaySex;
  
  private String applayOrg;
  
  private String visitName;
  
  private String visitWorkunit;
  
  private String visitOrg;
  
  private String applayBuildNumber;
  
  private String applayOrgName;
  
  private String visitOrgName;
  
  private Long applayStation;
  
  private String applayKth;
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
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
  
  public Long getOfficeId() {
    return this.officeId;
  }
  
  public void setOfficeId(Long officeId) {
    this.officeId = officeId;
  }
  
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
  
  public String getApplayBuildId() {
    return this.applayBuildId;
  }
  
  public void setApplayBuildId(String applayBuildId) {
    this.applayBuildId = applayBuildId;
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
  
  public String getApplayKth() {
    return this.applayKth;
  }
  
  public void setApplayKth(String applayKth) {
    this.applayKth = applayKth;
  }
}
