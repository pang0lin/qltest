package com.js.oa.personalwork.person.action;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class PersonOwnActionForm extends ActionForm {
  private String bussinessFax;
  
  private String bussinessPhone;
  
  private String classId;
  
  private String con;
  
  private String done;
  
  private String fixedPhone;
  
  private String linkManAddress;
  
  private String linkManCountry;
  
  private String linkManCounty;
  
  private String linkManDepart;
  
  private String linkManDuty;
  
  private String linkManEmail;
  
  private String linkManEmail2;
  
  private String linkManEmail3;
  
  private String linkManEnName;
  
  private String linkManDescribe;
  
  private String linkManName;
  
  private String linkManPostZip;
  
  private String linkManProfession;
  
  private String linkManSex;
  
  private String linkManState;
  
  private String linkManUnit;
  
  private String linkManWebUrl;
  
  private String mobilePhone;
  
  private String tmpLinkMan;
  
  private String tmpLinkManBirth;
  
  private String editId;
  
  private String queryItem;
  
  private String queryText;
  
  private String linkManStateSelect;
  
  private String linkManStateText;
  
  private String linkManCountySelect;
  
  private String linkManCountyText;
  
  private String createdEmpName;
  
  private String viewScope;
  
  private String email;
  
  private Date linkManBirth = null;
  
  public String getCreatedEmpName() {
    return this.createdEmpName;
  }
  
  public void setCreatedEmpName(String createdEmpName) {
    this.createdEmpName = createdEmpName;
  }
  
  public String getBussinessFax() {
    return this.bussinessFax;
  }
  
  public void setBussinessFax(String bussinessFax) {
    this.bussinessFax = bussinessFax;
  }
  
  public String getBussinessPhone() {
    return this.bussinessPhone;
  }
  
  public void setBussinessPhone(String bussinessPhone) {
    this.bussinessPhone = bussinessPhone;
  }
  
  public String getClassId() {
    return this.classId;
  }
  
  public void setClassId(String classId) {
    this.classId = classId;
  }
  
  public String getCon() {
    return this.con;
  }
  
  public void setCon(String con) {
    this.con = con;
  }
  
  public String getDone() {
    return this.done;
  }
  
  public void setDone(String done) {
    this.done = done;
  }
  
  public String getFixedPhone() {
    return this.fixedPhone;
  }
  
  public void setFixedPhone(String fixedPhone) {
    this.fixedPhone = fixedPhone;
  }
  
  public String getLinkManAddress() {
    return this.linkManAddress;
  }
  
  public void setLinkManAddress(String linkManAddress) {
    this.linkManAddress = linkManAddress;
  }
  
  public String getLinkManCountry() {
    return this.linkManCountry;
  }
  
  public void setLinkManCountry(String linkManCountry) {
    this.linkManCountry = linkManCountry;
  }
  
  public String getLinkManCounty() {
    return this.linkManCounty;
  }
  
  public void setLinkManCounty(String linkManCounty) {
    this.linkManCounty = linkManCounty;
  }
  
  public String getLinkManDepart() {
    return this.linkManDepart;
  }
  
  public void setLinkManDepart(String linkManDepart) {
    this.linkManDepart = linkManDepart;
  }
  
  public String getLinkManDuty() {
    return this.linkManDuty;
  }
  
  public void setLinkManDuty(String linkManDuty) {
    this.linkManDuty = linkManDuty;
  }
  
  public String getLinkManEmail() {
    return this.linkManEmail;
  }
  
  public void setLinkManEmail(String linkManEmail) {
    this.linkManEmail = linkManEmail;
  }
  
  public String getLinkManEmail2() {
    return this.linkManEmail2;
  }
  
  public void setLinkManEmail2(String linkManEmail2) {
    this.linkManEmail2 = linkManEmail2;
  }
  
  public String getLinkManEmail3() {
    return this.linkManEmail3;
  }
  
  public void setLinkManEmail3(String linkManEmail3) {
    this.linkManEmail3 = linkManEmail3;
  }
  
  public String getLinkManEnName() {
    return this.linkManEnName;
  }
  
  public void setLinkManEnName(String linkManEnName) {
    this.linkManEnName = linkManEnName;
  }
  
  public String getLinkManDescribe() {
    return this.linkManDescribe;
  }
  
  public void setLinkManDescribe(String linkManDescribe) {
    this.linkManDescribe = linkManDescribe;
  }
  
  public String getLinkManName() {
    return this.linkManName;
  }
  
  public void setLinkManName(String linkManName) {
    this.linkManName = linkManName;
  }
  
  public String getLinkManPostZip() {
    return this.linkManPostZip;
  }
  
  public void setLinkManPostZip(String linkManPostZip) {
    this.linkManPostZip = linkManPostZip;
  }
  
  public String getLinkManProfession() {
    return this.linkManProfession;
  }
  
  public void setLinkManProfession(String linkManProfession) {
    this.linkManProfession = linkManProfession;
  }
  
  public String getLinkManSex() {
    return this.linkManSex;
  }
  
  public void setLinkManSex(String linkManSex) {
    this.linkManSex = linkManSex;
  }
  
  public String getLinkManState() {
    return this.linkManState;
  }
  
  public void setLinkManState(String linkManState) {
    this.linkManState = linkManState;
  }
  
  public String getLinkManUnit() {
    return this.linkManUnit;
  }
  
  public void setLinkManUnit(String linkManUnit) {
    this.linkManUnit = linkManUnit;
  }
  
  public String getLinkManWebUrl() {
    return this.linkManWebUrl;
  }
  
  public void setLinkManWebUrl(String linkManWebUrl) {
    this.linkManWebUrl = linkManWebUrl;
  }
  
  public String getMobilePhone() {
    return this.mobilePhone;
  }
  
  public void setMobilePhone(String mobilePhone) {
    this.mobilePhone = mobilePhone;
  }
  
  public String getTmpLinkMan() {
    return this.tmpLinkMan;
  }
  
  public void setTmpLinkMan(String tmpLinkMan) {
    this.tmpLinkMan = tmpLinkMan;
  }
  
  public String getTmpLinkManBirth() {
    return this.tmpLinkManBirth;
  }
  
  public void setTmpLinkManBirth(String tmpLinkManBirth) {
    this.tmpLinkManBirth = tmpLinkManBirth;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.bussinessFax = null;
    this.bussinessPhone = null;
    this.classId = null;
    this.fixedPhone = null;
    this.linkManAddress = null;
    this.linkManCountry = null;
    this.linkManCounty = null;
    this.linkManDepart = null;
    this.linkManDuty = null;
    this.linkManEmail = null;
    this.linkManEmail2 = null;
    this.linkManEmail3 = null;
    this.linkManEnName = null;
    this.linkManDescribe = null;
    this.linkManName = null;
    this.linkManPostZip = null;
    this.linkManProfession = null;
    this.linkManSex = null;
    this.linkManState = null;
    this.linkManUnit = null;
    this.linkManWebUrl = null;
    this.mobilePhone = null;
    this.viewScope = null;
    this.tmpLinkMan = null;
    this.tmpLinkManBirth = null;
  }
  
  public String getEditId() {
    return this.editId;
  }
  
  public void setEditId(String editId) {
    this.editId = editId;
  }
  
  public String getQueryItem() {
    return this.queryItem;
  }
  
  public void setQueryItem(String queryItem) {
    this.queryItem = queryItem;
  }
  
  public String getQueryText() {
    return this.queryText;
  }
  
  public void setQueryText(String queryText) {
    this.queryText = queryText;
  }
  
  public String getLinkManStateSelect() {
    return this.linkManStateSelect;
  }
  
  public void setLinkManStateSelect(String linkManStateSelect) {
    this.linkManStateSelect = linkManStateSelect;
  }
  
  public String getLinkManStateText() {
    return this.linkManStateText;
  }
  
  public void setLinkManStateText(String linkManStateText) {
    this.linkManStateText = linkManStateText;
  }
  
  public String getLinkManCountySelect() {
    return this.linkManCountySelect;
  }
  
  public void setLinkManCountySelect(String linkManCountySelect) {
    this.linkManCountySelect = linkManCountySelect;
  }
  
  public String getLinkManCountyText() {
    return this.linkManCountyText;
  }
  
  public void setLinkManCountyText(String linkManCountyText) {
    this.linkManCountyText = linkManCountyText;
  }
  
  public Date getLinkManBirth() {
    return this.linkManBirth;
  }
  
  public String getViewScope() {
    return this.viewScope;
  }
  
  public void setLinkManBirth(Date linkManBirth) {
    this.linkManBirth = linkManBirth;
  }
  
  public void setViewScope(String viewScope) {
    this.viewScope = viewScope;
  }
  
  public String getEmail() {
    return this.email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
}
