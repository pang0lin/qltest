package com.js.oa.personalwork.setup.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class OptionSetPO implements Serializable {
  private long empId;
  
  private int calendarRemind;
  
  private String workingDay;
  
  private byte firstDayOfWeek;
  
  private String fisrtWeekOfYear;
  
  private String beginTimeOfDay;
  
  private String endTimeOfDay;
  
  private String finishTaskColor;
  
  private String overdueTaskColor;
  
  private String notePaperColor;
  
  private long id;
  
  private Integer desktopStyle;
  
  private String alwaysSign;
  
  private String alwaysRevert;
  
  public OptionSetPO(long empid, int calendarremind, String workingday, byte firstdayofweek, String fisrtweekofyear, String begintimeofday, String endtimeofday, String finishtaskcolor, String overduetaskcolor, String notepapercolor, Integer desktopStyle, String alwaysSign, String alwaysRevert) {
    this.empId = empid;
    this.calendarRemind = calendarremind;
    this.workingDay = workingday;
    this.firstDayOfWeek = firstdayofweek;
    this.fisrtWeekOfYear = fisrtweekofyear;
    this.beginTimeOfDay = begintimeofday;
    this.endTimeOfDay = endtimeofday;
    this.finishTaskColor = finishtaskcolor;
    this.overdueTaskColor = overduetaskcolor;
    this.notePaperColor = notepapercolor;
    this.desktopStyle = desktopStyle;
    this.alwaysSign = alwaysSign;
    this.alwaysRevert = alwaysRevert;
  }
  
  public OptionSetPO() {}
  
  public long getEmpId() {
    return this.empId;
  }
  
  public void setEmpId(long empId) {
    this.empId = empId;
  }
  
  public int getCalendarRemind() {
    return this.calendarRemind;
  }
  
  public void setCalendarRemind(int calendarRemind) {
    this.calendarRemind = calendarRemind;
  }
  
  public String getWorkingDay() {
    return this.workingDay;
  }
  
  public void setWorkingDay(String workingDay) {
    this.workingDay = workingDay;
  }
  
  public byte getFirstDayOfWeek() {
    return this.firstDayOfWeek;
  }
  
  public void setFirstDayOfWeek(byte firstDayOfWeek) {
    this.firstDayOfWeek = firstDayOfWeek;
  }
  
  public String getFisrtWeekOfYear() {
    return this.fisrtWeekOfYear;
  }
  
  public void setFisrtWeekOfYear(String fisrtWeekOfYear) {
    this.fisrtWeekOfYear = fisrtWeekOfYear;
  }
  
  public String getBeginTimeOfDay() {
    return this.beginTimeOfDay;
  }
  
  public void setBeginTimeOfDay(String beginTimeOfDay) {
    this.beginTimeOfDay = beginTimeOfDay;
  }
  
  public String getEndTimeOfDay() {
    return this.endTimeOfDay;
  }
  
  public void setEndTimeOfDay(String endTimeOfDay) {
    this.endTimeOfDay = endTimeOfDay;
  }
  
  public String getFinishTaskColor() {
    return this.finishTaskColor;
  }
  
  public void setFinishTaskColor(String finishTaskColor) {
    this.finishTaskColor = finishTaskColor;
  }
  
  public String getOverdueTaskColor() {
    return this.overdueTaskColor;
  }
  
  public void setOverdueTaskColor(String overdueTaskColor) {
    this.overdueTaskColor = overdueTaskColor;
  }
  
  public String getNotePaperColor() {
    return this.notePaperColor;
  }
  
  public void setNotePaperColor(String notePaperColor) {
    this.notePaperColor = notePaperColor;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof OptionSetPO))
      return false; 
    OptionSetPO castOther = (OptionSetPO)other;
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
  
  public String getAlwaysRevert() {
    return this.alwaysRevert;
  }
  
  public void setAlwaysRevert(String alwaysRevert) {
    this.alwaysRevert = alwaysRevert;
  }
  
  public String getAlwaysSign() {
    return this.alwaysSign;
  }
  
  public void setAlwaysSign(String alwaysSign) {
    this.alwaysSign = alwaysSign;
  }
  
  public Integer getDesktopStyle() {
    return this.desktopStyle;
  }
  
  public void setDesktopStyle(Integer desktopStyle) {
    this.desktopStyle = desktopStyle;
  }
}
