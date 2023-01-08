package com.js.oa.hr.subsidiarywork.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class FestivalSetPO implements Serializable {
  private long id;
  
  private String festivalName;
  
  private String festivalWish;
  
  private String festivalWishCard;
  
  private int calendarType;
  
  private int appointYear;
  
  private int festiveMonth;
  
  private int festiveDay;
  
  private int festiveYear;
  
  private int festivalRemind;
  
  private String wishCardName;
  
  private long createdEmp;
  
  private long createdOrg;
  
  private String useRange;
  
  private String userRangeName;
  
  private String domainId;
  
  public FestivalSetPO(long createdEmp, long createdOrg, String useRange, int calendarType, int appointYear, int festiveMonth, int festiveDay, int festiveYear, String festivalname, int festivalremind, String festivalwish, String festivalwishcard) {
    this.festivalName = festivalname;
    this.festivalRemind = festivalremind;
    this.festivalWish = festivalwish;
    this.festivalWishCard = festivalwishcard;
    this.calendarType = calendarType;
    this.appointYear = appointYear;
    this.festiveMonth = festiveMonth;
    this.festiveDay = festiveDay;
    this.festiveYear = festiveYear;
    this.createdEmp = createdEmp;
    this.createdOrg = createdOrg;
    this.useRange = useRange;
  }
  
  public FestivalSetPO() {}
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public String getFestivalName() {
    return this.festivalName;
  }
  
  public void setFestivalName(String festivalName) {
    this.festivalName = festivalName;
  }
  
  public int getFestivalRemind() {
    return this.festivalRemind;
  }
  
  public void setFestivalRemind(int festivalRemind) {
    this.festivalRemind = festivalRemind;
  }
  
  public String getFestivalWish() {
    return this.festivalWish;
  }
  
  public void setFestivalWish(String festivalWish) {
    this.festivalWish = festivalWish;
  }
  
  public String getFestivalWishCard() {
    return this.festivalWishCard;
  }
  
  public void setFestivalWishCard(String festivalWishCard) {
    this.festivalWishCard = festivalWishCard;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof FestivalSetPO))
      return false; 
    FestivalSetPO castOther = (FestivalSetPO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
  }
  
  public int getCalendarType() {
    return this.calendarType;
  }
  
  public void setCalendarType(int calendarType) {
    this.calendarType = calendarType;
  }
  
  public int getAppointYear() {
    return this.appointYear;
  }
  
  public void setAppointYear(int appointYear) {
    this.appointYear = appointYear;
  }
  
  public int getFestiveMonth() {
    return this.festiveMonth;
  }
  
  public void setFestiveMonth(int festiveMonth) {
    this.festiveMonth = festiveMonth;
  }
  
  public int getFestiveDay() {
    return this.festiveDay;
  }
  
  public void setFestiveDay(int festiveDay) {
    this.festiveDay = festiveDay;
  }
  
  public int getFestiveYear() {
    return this.festiveYear;
  }
  
  public void setFestiveYear(int festiveYear) {
    this.festiveYear = festiveYear;
  }
  
  public String getWishCardName() {
    return this.wishCardName;
  }
  
  public void setWishCardName(String wishCardName) {
    this.wishCardName = wishCardName;
  }
  
  public long getCreatedEmp() {
    return this.createdEmp;
  }
  
  public void setCreatedEmp(long createdEmp) {
    this.createdEmp = createdEmp;
  }
  
  public long getCreatedOrg() {
    return this.createdOrg;
  }
  
  public void setCreatedOrg(long createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public String getUseRange() {
    return this.useRange;
  }
  
  public void setUseRange(String useRange) {
    this.useRange = useRange;
  }
  
  public String getUserRangeName() {
    return this.userRangeName;
  }
  
  public void setUserRangeName(String userRangeName) {
    this.userRangeName = userRangeName;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
}
