package com.js.oa.scheme.event.vo;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class DayData implements Serializable {
  private long day;
  
  private int month;
  
  private int tian;
  
  private int weekDay;
  
  private int year;
  
  private List events = null;
  
  public DayData() {}
  
  public DayData(long day, int weekDay, List events) {
    this.day = day;
    this.weekDay = weekDay;
    this.events = events;
  }
  
  public long getDay() {
    return this.day;
  }
  
  public void setDay(long day) {
    this.day = day;
    GregorianCalendar calendar = new GregorianCalendar(Locale.CHINESE);
    calendar.setTimeInMillis(day);
    this.year = calendar.get(1);
    this.month = calendar.get(2) + 1;
    this.tian = calendar.get(5);
    this.weekDay = calendar.get(7) + 1;
  }
  
  public int getWeekDay() {
    return this.weekDay;
  }
  
  public List getEvents() {
    return this.events;
  }
  
  public void setEvents(List events) {
    this.events = events;
  }
  
  public boolean equals(Object o) {
    if (this == o)
      return true; 
    if (!(o instanceof DayData))
      return false; 
    DayData dayData = (DayData)o;
    if (this.day != dayData.day)
      return false; 
    if (this.weekDay != dayData.weekDay)
      return false; 
    if ((this.events != null) ? !this.events.equals(dayData.events) : (dayData.events != null))
      return false; 
    return true;
  }
  
  public int hashCode() {
    int result = (int)(this.day ^ this.day >>> 32L);
    result = 29 * result + this.weekDay;
    result = 29 * result + ((this.events != null) ? this.events.hashCode() : 0);
    return result;
  }
  
  public String toString() {
    return "DayData{day=" + 
      this.day + 
      ", weekDay=" + this.weekDay + 
      ", events=" + this.events + 
      "}";
  }
  
  public int getMonth() {
    return this.month;
  }
  
  public int getTian() {
    return this.tian;
  }
  
  public int getYear() {
    return this.year;
  }
}
