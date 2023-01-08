package com.js.oa.personalwork.person.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class PersonPO implements Serializable {
  private long id;
  
  private String linkManName;
  
  private String linkManEnName;
  
  private String linkManSex;
  
  private Date linkManBirth = null;
  
  private String linkManUnit;
  
  private String linkManDepart;
  
  private String linkManProfession;
  
  private String linkManDuty;
  
  private String linkManEmail;
  
  private String linkManEmail2;
  
  private String linkManEmail3;
  
  private String linkManWebUrl;
  
  private String bussinessPhone;
  
  private String bussinessFax;
  
  private String fixedPhone;
  
  private String mobilePhone;
  
  private String linkManCountry;
  
  private String linkManState;
  
  private String linkManCounty;
  
  private String linkManAddress;
  
  private String linkManPostZip;
  
  private String linkManDescribe;
  
  private byte linkManType;
  
  private String createdEmpName;
  
  private long createdEmpId;
  
  private String viewScope;
  
  private long createdOrg;
  
  private PersonClassPO linkManClass;
  
  private String domainId;
  
  public PersonPO(PersonClassPO linkManClass, long linkmanclassId, String linkmanname, String linkmanenname, String linkmansex, Date linkmanbirth, String linkmanunit, String linkmandepart, String linkmanprofession, String linkmanduty, String linkmanemail, String linkmanemail2, String linkmanemail3, String linkmanweburl, String bussinessphone, String bussinessfax, String fixedphone, String mobilephone, String linkmancountry, String linkmanstate, String linkmancounty, String linkmanaddress, String linkmanpostzip, String linkmandescribe, byte linkmantype, String createdempname, long createdempid, long createdorg) {
    this.linkManClass = linkManClass;
    this.bussinessPhone = bussinessphone;
  }
  
  public PersonPO(String bussinessphone, String bussinessfax) {
    this.bussinessPhone = bussinessphone;
  }
  
  public PersonPO() {}
  
  public String getLinkManName() {
    return this.linkManName;
  }
  
  public void setLinkManName(String linkManName) {
    this.linkManName = linkManName;
  }
  
  public String getLinkManEnName() {
    return this.linkManEnName;
  }
  
  public void setLinkManEnName(String linkManEnName) {
    this.linkManEnName = linkManEnName;
  }
  
  public String getLinkManSex() {
    return this.linkManSex;
  }
  
  public void setLinkManSex(String linkManSex) {
    this.linkManSex = linkManSex;
  }
  
  public Date getLinkManBirth() {
    return this.linkManBirth;
  }
  
  public void setLinkManBirth(Date linkManBirth) {
    this.linkManBirth = linkManBirth;
  }
  
  public String getLinkManUnit() {
    return this.linkManUnit;
  }
  
  public void setLinkManUnit(String linkManUnit) {
    this.linkManUnit = linkManUnit;
  }
  
  public String getLinkManDepart() {
    return this.linkManDepart;
  }
  
  public void setLinkManDepart(String linkManDepart) {
    this.linkManDepart = linkManDepart;
  }
  
  public String getLinkManProfession() {
    return this.linkManProfession;
  }
  
  public void setLinkManProfession(String linkManProfession) {
    this.linkManProfession = linkManProfession;
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
  
  public String getLinkManWebUrl() {
    return this.linkManWebUrl;
  }
  
  public void setLinkManWebUrl(String linkManWebUrl) {
    this.linkManWebUrl = linkManWebUrl;
  }
  
  public String getBussinessPhone() {
    return this.bussinessPhone;
  }
  
  public void setBussinessPhone(String bussinessPhone) {
    this.bussinessPhone = bussinessPhone;
  }
  
  public String getBussinessFax() {
    return this.bussinessFax;
  }
  
  public void setBussinessFax(String bussinessFax) {
    this.bussinessFax = bussinessFax;
  }
  
  public String getFixedPhone() {
    return this.fixedPhone;
  }
  
  public void setFixedPhone(String fixedPhone) {
    this.fixedPhone = fixedPhone;
  }
  
  public String getMobilePhone() {
    return this.mobilePhone;
  }
  
  public void setMobilePhone(String mobilePhone) {
    this.mobilePhone = mobilePhone;
  }
  
  public String getLinkManCountry() {
    return this.linkManCountry;
  }
  
  public void setLinkManCountry(String linkManCountry) {
    this.linkManCountry = linkManCountry;
  }
  
  public String getLinkManState() {
    return this.linkManState;
  }
  
  public void setLinkManState(String linkManState) {
    this.linkManState = linkManState;
  }
  
  public String getLinkManCounty() {
    return this.linkManCounty;
  }
  
  public void setLinkManCounty(String linkManCounty) {
    this.linkManCounty = linkManCounty;
  }
  
  public String getLinkManAddress() {
    return this.linkManAddress;
  }
  
  public void setLinkManAddress(String linkManAddress) {
    this.linkManAddress = linkManAddress;
  }
  
  public String getLinkManPostZip() {
    return this.linkManPostZip;
  }
  
  public void setLinkManPostZip(String linkManPostZip) {
    this.linkManPostZip = linkManPostZip;
  }
  
  public String getLinkManDescribe() {
    return this.linkManDescribe;
  }
  
  public void setLinkManDescribe(String linkManDescribe) {
    this.linkManDescribe = linkManDescribe;
  }
  
  public byte getLinkManType() {
    return this.linkManType;
  }
  
  public void setLinkManType(byte linkManType) {
    this.linkManType = linkManType;
  }
  
  public String getCreatedEmpName() {
    return this.createdEmpName;
  }
  
  public void setCreatedEmpName(String createdEmpName) {
    this.createdEmpName = createdEmpName;
  }
  
  public long getCreatedEmpId() {
    return this.createdEmpId;
  }
  
  public void setCreatedEmpId(long createdEmpId) {
    this.createdEmpId = createdEmpId;
  }
  
  public long getCreatedOrg() {
    return this.createdOrg;
  }
  
  public void setCreatedOrg(long createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("Id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof PersonPO))
      return false; 
    PersonPO castOther = (PersonPO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
  }
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public PersonClassPO getLinkManClass() {
    return this.linkManClass;
  }
  
  public void setLinkManClass(PersonClassPO linkManClass) {
    this.linkManClass = linkManClass;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public String getViewScope() {
    return this.viewScope;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
  
  public void setViewScope(String viewScope) {
    this.viewScope = viewScope;
  }
}
