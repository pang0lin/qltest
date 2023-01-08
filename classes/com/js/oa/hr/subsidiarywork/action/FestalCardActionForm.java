package com.js.oa.hr.subsidiarywork.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class FestalCardActionForm extends ActionForm {
  private int appointYear;
  
  private int calendarType;
  
  private String done;
  
  private String editId;
  
  private String festivalName;
  
  private String festivalWish;
  
  private String festivalWishCard;
  
  private int festiveDay;
  
  private int festiveMonth;
  
  private int festiveYear;
  
  private int festivalRemind;
  
  private String wishCardName;
  
  private String queryText;
  
  private String userRangeName;
  
  private String useRange;
  
  private String festivalWishCard1;
  
  public int getAppointYear() {
    return this.appointYear;
  }
  
  public void setAppointYear(int appointYear) {
    this.appointYear = appointYear;
  }
  
  public int getCalendarType() {
    return this.calendarType;
  }
  
  public void setCalendarType(int calendarType) {
    this.calendarType = calendarType;
  }
  
  public String getDone() {
    return this.done;
  }
  
  public void setDone(String done) {
    this.done = done;
  }
  
  public String getEditId() {
    return this.editId;
  }
  
  public void setEditId(String editId) {
    this.editId = editId;
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
  
  public int getFestiveDay() {
    return this.festiveDay;
  }
  
  public void setFestiveDay(int festiveDay) {
    this.festiveDay = festiveDay;
  }
  
  public int getFestiveMonth() {
    return this.festiveMonth;
  }
  
  public void setFestiveMonth(int festiveMonth) {
    this.festiveMonth = festiveMonth;
  }
  
  public int getFestiveYear() {
    return this.festiveYear;
  }
  
  public void setFestiveYear(int festiveYear) {
    this.festiveYear = festiveYear;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.appointYear = 0;
    this.calendarType = 0;
    this.done = null;
    this.editId = null;
    this.festivalName = null;
    this.festivalRemind = 0;
    this.festivalWish = null;
    this.festivalWishCard = null;
    this.festiveDay = 0;
    this.festiveMonth = 0;
    this.festiveYear = 0;
  }
  
  public String getWishCardName() {
    return this.wishCardName;
  }
  
  public void setWishCardName(String wishCardName) {
    this.wishCardName = wishCardName;
  }
  
  public String getQueryText() {
    return this.queryText;
  }
  
  public void setQueryText(String queryText) {
    this.queryText = queryText;
  }
  
  public String getUserRangeName() {
    return this.userRangeName;
  }
  
  public void setUserRangeName(String userRangeName) {
    this.userRangeName = userRangeName;
  }
  
  public String getUseRange() {
    return this.useRange;
  }
  
  public void setUseRange(String useRange) {
    this.useRange = useRange;
  }
  
  public String getFestivalWishCard1() {
    return this.festivalWishCard1;
  }
  
  public void setFestivalWishCard1(String festivalWishCard1) {
    this.festivalWishCard1 = festivalWishCard1;
  }
}
