package com.js.oa.tjgzw.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTime {
  private int weeks = 0;
  
  private int MaxDate;
  
  private int MaxYear;
  
  public String nowTime() {
    Date date = new Date();
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String time = format.format(date);
    return time;
  }
  
  public String getNextMonday() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    Calendar cal = Calendar.getInstance();
    cal.setFirstDayOfWeek(2);
    cal.add(4, 1);
    cal.set(7, 2);
    String sDate = sdf.format(cal.getTime());
    return sDate;
  }
  
  public String getNextSunday() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    Calendar cal = Calendar.getInstance();
    cal.setFirstDayOfWeek(2);
    cal.add(4, 1);
    cal.set(7, 1);
    String sDate = sdf.format(cal.getTime());
    return sDate;
  }
  
  public String getNextFriday(String format, String time) {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    SimpleDateFormat sdfc = new SimpleDateFormat("yyyyMMdd");
    Calendar cal = Calendar.getInstance();
    try {
      cal.setTime(sdfc.parse(time));
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    cal.setFirstDayOfWeek(2);
    cal.add(4, 1);
    cal.set(7, 6);
    String sDate = sdf.format(cal.getTime());
    return sDate;
  }
  
  public String getSundayByTime(String time) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    Calendar cal = Calendar.getInstance();
    try {
      cal.setTime(sdf.parse(time));
    } catch (Exception e) {
      e.printStackTrace();
    } 
    cal.setFirstDayOfWeek(2);
    cal.set(7, 1);
    String sDate = sdf.format(cal.getTime());
    return sDate;
  }
  
  public String getFirdayByTime(String time) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
    Calendar cal = Calendar.getInstance();
    try {
      cal.setTime(sdf2.parse(time));
    } catch (Exception e) {
      e.printStackTrace();
    } 
    cal.setFirstDayOfWeek(2);
    cal.set(7, 6);
    String sDate = sdf.format(cal.getTime());
    return sDate;
  }
  
  private int getMonthPlus() {
    Calendar cd = Calendar.getInstance();
    int monthOfNumber = cd.get(5);
    cd.set(5, 1);
    cd.roll(5, -1);
    this.MaxDate = cd.get(5);
    if (monthOfNumber == 1)
      return -this.MaxDate; 
    return 1 - monthOfNumber;
  }
  
  private int getMondayPlus() {
    Calendar cd = Calendar.getInstance();
    int dayOfWeek = cd.get(7) - 1;
    if (dayOfWeek == 1)
      return 0; 
    return 1 - dayOfWeek;
  }
  
  public String getMondayOfweek() {
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
    cal.setFirstDayOfWeek(2);
    cal.set(7, 2);
    String curMonday = df.format(cal.getTime());
    return curMonday;
  }
  
  public String getSundayOfWeek() {
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
    cal.setFirstDayOfWeek(2);
    cal.set(7, 1);
    String curSunday = df.format(cal.getTime());
    return curSunday;
  }
  
  public String getZhouNumByTime(String time) {
    String str = "";
    Calendar ca = Calendar.getInstance();
    ca.setFirstDayOfWeek(2);
    Date date = null;
    try {
      date = (new SimpleDateFormat("yyyyMMdd")).parse(time);
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    ca.setTime(date);
    int weekNumInt = ca.get(3);
    str = String.valueOf(str) + "第" + weekNumInt + "周";
    return str;
  }
  
  public String getZhouNumByTime2(String time) {
    String str = "";
    Calendar ca = Calendar.getInstance();
    ca.setFirstDayOfWeek(2);
    Date date = null;
    try {
      date = (new SimpleDateFormat("yyyyMMdd")).parse(time);
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    ca.setTime(date);
    int weekNumInt = ca.get(3);
    return (new StringBuilder(String.valueOf(weekNumInt))).toString();
  }
  
  public int compare_date(String DATE1, String DATE2) {
    DateFormat df = new SimpleDateFormat("yyyyMMdd");
    try {
      Date dt1 = df.parse(DATE1);
      Date dt2 = df.parse(DATE2);
      if (dt1.getTime() > dt2.getTime())
        return 1; 
      if (dt1.getTime() < dt2.getTime())
        return 2; 
      return 0;
    } catch (Exception exception) {
      exception.printStackTrace();
      return 0;
    } 
  }
  
  public String getNextMondayByTime(String time) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    Date para = null;
    try {
      para = sdf.parse(time);
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    Calendar cal = Calendar.getInstance();
    cal.setTime(para);
    cal.setFirstDayOfWeek(2);
    cal.add(4, 1);
    cal.set(7, 2);
    String sDate = sdf.format(cal.getTime());
    return sDate;
  }
  
  public String getNextSundayByTime(String time) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    Date para = null;
    try {
      para = sdf.parse(time);
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    Calendar cal = Calendar.getInstance();
    cal.setTime(para);
    cal.setFirstDayOfWeek(2);
    cal.add(4, 1);
    cal.set(7, 1);
    String sDate = sdf.format(cal.getTime());
    return sDate;
  }
  
  public String getNextMonByTime(String time) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    Date para = null;
    try {
      para = sdf.parse(time);
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    Calendar cal = Calendar.getInstance();
    cal.setTime(para);
    cal.setFirstDayOfWeek(2);
    cal.add(4, 1);
    cal.set(7, 2);
    String preMonday = sdf.format(cal.getTime());
    cal.set(7, 1);
    String preSunday = sdf.format(cal.getTime());
    String preWeekNum = getZhouNumByTime(preMonday);
    return String.valueOf(preMonday) + "-" + preSunday + ">>" + preWeekNum;
  }
  
  public String getPreviousMondayByTime(String time) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    Date para = null;
    try {
      para = sdf.parse(time);
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    Calendar cal = Calendar.getInstance();
    cal.setTime(para);
    cal.setFirstDayOfWeek(2);
    cal.add(4, -1);
    cal.set(7, 2);
    String preMonday = sdf.format(cal.getTime());
    return preMonday;
  }
  
  public String getPreviousSundayByTime(String time) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    Date para = null;
    try {
      para = sdf.parse(time);
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    Calendar cal = Calendar.getInstance();
    cal.setTime(para);
    cal.setFirstDayOfWeek(2);
    cal.add(4, -1);
    cal.set(7, 1);
    String preSunday = sdf.format(cal.getTime());
    return preSunday;
  }
  
  public String[] getAllWeeksByTime(String time) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    Calendar cal = Calendar.getInstance();
    String[] weeksTime = new String[7];
    try {
      cal.setTime(sdf.parse(time));
    } catch (Exception e) {
      e.printStackTrace();
    } 
    cal.setFirstDayOfWeek(2);
    cal.set(7, 2);
    weeksTime[0] = sdf.format(cal.getTime());
    cal.set(7, 3);
    weeksTime[1] = sdf.format(cal.getTime());
    cal.set(7, 4);
    weeksTime[2] = sdf.format(cal.getTime());
    cal.set(7, 5);
    weeksTime[3] = sdf.format(cal.getTime());
    cal.set(7, 6);
    weeksTime[4] = sdf.format(cal.getTime());
    cal.set(7, 7);
    weeksTime[5] = sdf.format(cal.getTime());
    cal.set(7, 1);
    weeksTime[6] = sdf.format(cal.getTime());
    return weeksTime;
  }
  
  public static void main(String[] args) throws ParseException {
    DateTime d = new DateTime();
    String[] weeksByTime = d.getAllWeeksByTime("20160325");
  }
}
