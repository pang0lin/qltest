package com.js.oa.hr.personnelmanager.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class WorkAddressActionForm extends ActionForm {
  private Long id;
  
  private String workName;
  
  private String workAddress;
  
  private String linkManCountry;
  
  private String linkManState;
  
  private String linkManCounty;
  
  private String workFax;
  
  private String workTelephone;
  
  private Long domain;
  
  private String linkManStateSelect;
  
  private String linkManStateText;
  
  private String linkManCountySelect;
  
  private String linkManCountyText;
  
  private String addrUserName;
  
  private String userOrgGroup;
  
  public String getLinkManStateSelect() {
    return this.linkManStateSelect;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public void setDomain(Long domain) {
    this.domain = domain;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public void setWorkAddress(String workAddress) {
    this.workAddress = workAddress;
  }
  
  public void setWorkFax(String workFax) {
    this.workFax = workFax;
  }
  
  public void setWorkName(String workName) {
    this.workName = workName;
  }
  
  public void setWorkTelephone(String workTelephone) {
    this.workTelephone = workTelephone;
  }
  
  public void setLinkManCountySelect(String linkManCountySelect) {
    this.linkManCountySelect = linkManCountySelect;
  }
  
  public void setLinkManCountyText(String linkManCountyText) {
    this.linkManCountyText = linkManCountyText;
  }
  
  public String getUserOrgGroup() {
    return this.userOrgGroup;
  }
  
  public Long getDomain() {
    return this.domain;
  }
  
  public String getAddrUserName() {
    return this.addrUserName;
  }
  
  public String getWorkTelephone() {
    return this.workTelephone;
  }
  
  public String getLinkManCounty() {
    return this.linkManCounty;
  }
  
  public void setLinkManStateSelect(String linkManStateSelect) {
    this.linkManStateSelect = linkManStateSelect;
  }
  
  public void setLinkManStateText(String linkManStateText) {
    this.linkManStateText = linkManStateText;
  }
  
  public void setLinkManCountry(String linkManCountry) {
    this.linkManCountry = linkManCountry;
  }
  
  public String getLinkManState() {
    return this.linkManState;
  }
  
  public void setLinkManCounty(String linkManCounty) {
    this.linkManCounty = linkManCounty;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public String getWorkName() {
    return this.workName;
  }
  
  public String getLinkManCountySelect() {
    return this.linkManCountySelect;
  }
  
  public String getLinkManCountry() {
    return this.linkManCountry;
  }
  
  public String getLinkManStateText() {
    return this.linkManStateText;
  }
  
  public String getWorkAddress() {
    return this.workAddress;
  }
  
  public String getLinkManCountyText() {
    return this.linkManCountyText;
  }
  
  public String getWorkFax() {
    return this.workFax;
  }
  
  public void setLinkManState(String linkManState) {
    this.linkManState = linkManState;
  }
  
  public void setUserOrgGroup(String userOrgGroup) {
    this.userOrgGroup = userOrgGroup;
  }
  
  public void setAddrUserName(String addrUserName) {
    this.addrUserName = addrUserName;
  }
}
