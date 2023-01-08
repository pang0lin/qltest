package com.js.oa.hr.subsidiarywork.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class OARemindPO implements Serializable {
  private long id;
  
  private String remindType;
  
  private String empId;
  
  private String orgRange;
  
  private String wishText;
  
  private String wishCard;
  
  private String festivalName;
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof OARemindPO))
      return false; 
    OARemindPO castOther = (OARemindPO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
  }
  
  public String getRemindType() {
    return this.remindType;
  }
  
  public void setRemindType(String remindType) {
    this.remindType = remindType;
  }
  
  public String getEmpId() {
    return this.empId;
  }
  
  public void setEmpId(String empId) {
    this.empId = empId;
  }
  
  public String getOrgRange() {
    return this.orgRange;
  }
  
  public void setOrgRange(String orgRange) {
    this.orgRange = orgRange;
  }
  
  public String getWishText() {
    return this.wishText;
  }
  
  public void setWishText(String wishText) {
    this.wishText = wishText;
  }
  
  public String getWishCard() {
    return this.wishCard;
  }
  
  public void setWishCard(String wishCard) {
    this.wishCard = wishCard;
  }
  
  public String getFestivalName() {
    return this.festivalName;
  }
  
  public void setFestivalName(String festivalName) {
    this.festivalName = festivalName;
  }
}
