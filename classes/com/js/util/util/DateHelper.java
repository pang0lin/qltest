package com.js.util.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {
  public static final String DATETIME_FORMAT_NEW = "yyyy-MM-dd HH:mm:ss";
  
  public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm";
  
  public static final String DATETIME_FORMAT_NO_SPACE = "yyyyMMdd_HHmmss";
  
  public static final String DATE_FORMAT = "yyyy-MM-dd";
  
  public static final String MMdd = "MM-dd";
  
  public static final String DATE_FORMAT_WITHOUT_LINE = "yyyyMMdd";
  
  public static final String yyyyMM = "yyyyMM";
  
  public static final String HHmm = "HH:mm";
  
  public static String date2String(Date date) {
    if (date == null)
      return ""; 
    String result = "";
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    result = dateFormat.format(date);
    return result;
  }
  
  public static String date2String(Date date, String format) {
    if (date == null)
      return date2String(new Date(), null); 
    if (format == null || format.equals(""))
      format = "yyyy-MM-dd HH:mm"; 
    String result = "";
    DateFormat dateFormat = new SimpleDateFormat(format);
    result = dateFormat.format(date);
    return result;
  }
  
  public static Date string2Date(String s) {
    if (s == null || s.equals(""))
      return null; 
    Date result = null;
    try {
      DateFormat format = null;
      if (s.length() > 15) {
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
      } else {
        format = new SimpleDateFormat("yyyy-MM-dd");
      } 
      result = format.parse(s);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  public static Date string2DateHHmm(String s) {
    if (s == null || s.equals(""))
      return null; 
    Date result = null;
    try {
      DateFormat format = null;
      format = new SimpleDateFormat("HH:mm");
      result = format.parse(s);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  public static Date string2DateMMdd(String s) {
    if (s == null || s.equals(""))
      return null; 
    Date result = null;
    try {
      DateFormat format = null;
      format = new SimpleDateFormat("MM-dd");
      result = format.parse(s);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  public static Date string2Date(String s, String format) {
    if (s == null || s.equals(""))
      return null; 
    Date result = null;
    if (format == null || format.equals(""))
      format = "yyyy-MM-dd HH:mm"; 
    try {
      DateFormat mFormat = new SimpleDateFormat(format);
      result = mFormat.parse(s);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  public static Date nextDate(Date date) {
    if (date == null)
      return null; 
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(5, calendar.get(5) + 1);
    return calendar.getTime();
  }
  
  public static Date beforDate(Date date) {
    if (date == null)
      return null; 
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(5, calendar.get(5) - 1);
    return calendar.getTime();
  }
  
  public static Date nextDate(String dateStr) {
    if (dateStr == null || dateStr.equals(""))
      return null; 
    Date date = string2Date(dateStr);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(5, calendar.get(5) + 1);
    return calendar.getTime();
  }
  
  public static Date nextDate(Date date, int day) {
    if (date == null)
      return null; 
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(5, calendar.get(5) + day);
    return calendar.getTime();
  }
  
  public static Date setNowTime(int hour, int minis, int seconds) {
    Calendar cal = Calendar.getInstance();
    Calendar cal2 = cal;
    cal2.set(11, hour);
    cal2.set(12, minis);
    cal2.set(13, seconds);
    return cal2.getTime();
  }
  
  public static int distanceMonth(String startDate, String endDate) throws Exception {
    int startYear = Integer.parseInt(startDate.substring(0, 4));
    int startMonth = Integer.parseInt(startDate.substring(4, 6));
    int endYear = Integer.parseInt(endDate.substring(0, 4));
    int endMonth = Integer.parseInt(endDate.substring(4, 6));
    if (Long.parseLong(endDate) - Long.parseLong(startDate) < 0L)
      throw new Exception("结束时期不能小于开始日期"); 
    long startTotalMonth = (startYear * 12 + startMonth);
    long endTotalMonth = (endYear * 12 + endMonth);
    int distanceMonth = (int)(endTotalMonth - startTotalMonth);
    return distanceMonth;
  }
  
  public static Date distanceMonth(Date date, int month) throws Exception {
    Calendar ca = Calendar.getInstance();
    ca.setTime(date);
    ca.set(2, ca.get(2) + month);
    Date newDate = ca.getTime();
    return newDate;
  }
  
  public static Date distanceYear(Date date, int year) throws Exception {
    Calendar ca = Calendar.getInstance();
    ca.setTime(date);
    ca.set(1, ca.get(1) + year);
    Date newDate = ca.getTime();
    return newDate;
  }
  
  public static Date getMonthFirstDay(Date date, int month, int day) throws Exception {
    Calendar ca = Calendar.getInstance();
    ca.setTime(date);
    ca.set(2, ca.get(2) + month);
    ca.set(5, day);
    ca.set(11, 0);
    ca.set(12, 0);
    ca.set(13, 0);
    Date newDate = ca.getTime();
    return newDate;
  }
  
  public static long getDistance(Date now, Date afterDate) throws Exception {
    long day = 0L;
    long nowDate = now.getTime();
    long afterDateDate = afterDate.getTime();
    day = (afterDateDate - nowDate) / 1000L / 86400L;
    return day;
  }
  
  public static long getDistanceMin(Date now, Date afterDate) {
    long day = 0L;
    long nowDate = now.getTime();
    long afterDateDate = afterDate.getTime();
    if (afterDateDate >= nowDate) {
      day = (afterDateDate - nowDate) / 60000L;
    } else {
      day = (nowDate - afterDateDate) / 60000L;
    } 
    return day;
  }
  
  public static String string2String(String date, String format) {
    Date oldDate = string2Date(date, format);
    String newDate = date2String(oldDate, format);
    return newDate;
  }
}
