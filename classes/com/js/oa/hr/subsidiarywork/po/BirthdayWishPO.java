package com.js.oa.hr.subsidiarywork.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class BirthdayWishPO implements Serializable {
  private long id;
  
  private String wishContent;
  
  private String wishSign;
  
  private String wishEmployees;
  
  private String wishCard;
  
  private String wishEmployeesName;
  
  private String wishCardName;
  
  private long createdOrg;
  
  private long createdEmp;
  
  private String domainId;
  
  private String path;
  
  public BirthdayWishPO(long createdOrg, long createdEmp, String wishEmployeesName, String wishCard, String wishEmployees, String wishcontent, String wishsign, long wishpersonid) {
    this.wishEmployees = wishEmployees;
    this.wishContent = wishcontent;
    this.wishSign = wishsign;
    this.wishCard = wishCard;
    this.wishEmployeesName = wishEmployeesName;
    this.createdEmp = createdEmp;
    this.createdOrg = createdOrg;
  }
  
  public BirthdayWishPO() {}
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public String getWishEmployees() {
    return this.wishEmployees;
  }
  
  public void setWishEmployees(String wishEmployees) {
    this.wishEmployees = wishEmployees;
  }
  
  public String getWishContent() {
    return this.wishContent;
  }
  
  public void setWishContent(String wishContent) {
    this.wishContent = wishContent;
  }
  
  public String getWishSign() {
    return this.wishSign;
  }
  
  public void setWishSign(String wishSign) {
    this.wishSign = wishSign;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof BirthdayWishPO))
      return false; 
    BirthdayWishPO castOther = (BirthdayWishPO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
  }
  
  public String getWishCard() {
    return this.wishCard;
  }
  
  public void setWishCard(String wishCard) {
    this.wishCard = wishCard;
  }
  
  public String getWishEmployeesName() {
    return this.wishEmployeesName;
  }
  
  public void setWishEmployeesName(String wishEmployeesName) {
    this.wishEmployeesName = wishEmployeesName;
  }
  
  public String getWishCardName() {
    return this.wishCardName;
  }
  
  public void setWishCardName(String wishCardName) {
    this.wishCardName = wishCardName;
  }
  
  public long getCreatedOrg() {
    return this.createdOrg;
  }
  
  public void setCreatedOrg(long createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public long getCreatedEmp() {
    return this.createdEmp;
  }
  
  public void setCreatedEmp(long createdEmp) {
    this.createdEmp = createdEmp;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
  
  public String getPath() {
    return this.path;
  }
  
  public void setPath(String path) {
    this.path = path;
  }
}
