package com.js.oa.hr.personnelmanager.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class WorkAddressPO implements Serializable {
  private Long id;
  
  private String workName;
  
  private String workAddress;
  
  private String workCountry;
  
  private String workState;
  
  private String workCity;
  
  private String workFax;
  
  private String workTelephone;
  
  private Long domain;
  
  private String addrUserName;
  
  private String addrUserId;
  
  private String addrUserOrg;
  
  private String addrUserGroup;
  
  public Long getDomain() {
    return this.domain;
  }
  
  public void setDomain(Long domain) {
    this.domain = domain;
  }
  
  public Long getId() {
    return this.id;
  }
  
  private void setId(Long id) {
    this.id = id;
  }
  
  public String getWorkAddress() {
    return this.workAddress;
  }
  
  public void setWorkAddress(String workAddress) {
    this.workAddress = workAddress;
  }
  
  public String getWorkCity() {
    return this.workCity;
  }
  
  public void setWorkCity(String workCity) {
    this.workCity = workCity;
  }
  
  public String getWorkCountry() {
    return this.workCountry;
  }
  
  public void setWorkCountry(String workCountry) {
    this.workCountry = workCountry;
  }
  
  public String getWorkFax() {
    return this.workFax;
  }
  
  public void setWorkFax(String workFax) {
    this.workFax = workFax;
  }
  
  public String getWorkName() {
    return this.workName;
  }
  
  public void setWorkName(String workName) {
    this.workName = workName;
  }
  
  public String getWorkState() {
    return this.workState;
  }
  
  public void setWorkState(String workState) {
    this.workState = workState;
  }
  
  public String getWorkTelephone() {
    return this.workTelephone;
  }
  
  public String getAddrUserOrg() {
    return this.addrUserOrg;
  }
  
  public String getAddrUserName() {
    return this.addrUserName;
  }
  
  public String getAddrUserId() {
    return this.addrUserId;
  }
  
  public String getAddrUserGroup() {
    return this.addrUserGroup;
  }
  
  public void setWorkTelephone(String workTelephone) {
    this.workTelephone = workTelephone;
  }
  
  public void setAddrUserOrg(String addrUserOrg) {
    this.addrUserOrg = addrUserOrg;
  }
  
  public void setAddrUserName(String addrUserName) {
    this.addrUserName = addrUserName;
  }
  
  public void setAddrUserId(String addrUserId) {
    this.addrUserId = addrUserId;
  }
  
  public void setAddrUserGroup(String addrUserGroup) {
    this.addrUserGroup = addrUserGroup;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof WorkAddressPO))
      return false; 
    WorkAddressPO castOther = (WorkAddressPO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
}
