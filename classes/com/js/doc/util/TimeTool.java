package com.js.doc.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TimeTool {
  public static Date convertStringToDate(String date, String hour, String minute) throws Exception {
    Date result = null;
    if (date == null || "".equals(date))
      throw new Exception("日期不能为空！"); 
    date = date.replaceAll("\\-", "\\/");
    result = new Date(date);
    try {
      if ("".equals(hour)) {
        result.setHours(0);
      } else {
        int intHour = Integer.parseInt(hour);
        result.setHours(intHour);
      } 
      if ("".equals(minute)) {
        result.setMinutes(0);
      } else {
        int intMinute = Integer.parseInt(minute);
        result.setMinutes(intMinute);
      } 
    } catch (Exception e) {
      result.setHours(0);
      result.setMinutes(0);
      e.printStackTrace();
      throw new Exception("日期格式不正确！[小时|分钟]");
    } finally {}
    return result;
  }
  
  public static String convertObjectToYearMonthDate(Object obj) {
    String retString = "";
    if (obj == null)
      return "&nbsp;"; 
    Date dd = (Date)obj;
    retString = String.valueOf(1900 + dd.getYear()) + "-" + (dd.getMonth() + 1) + "-" + dd.getDate();
    return retString;
  }
  
  public static String convertObjectToYearMonthDateDot(Object obj) {
    String retString = "";
    if (obj == null)
      return "&nbsp;"; 
    Date dd = (Date)obj;
    retString = String.valueOf(1900 + dd.getYear()) + "." + (dd.getMonth() + 1) + "." + dd.getDate();
    return retString;
  }
  
  public static String convertObjectToYearMonthDateCN(Object obj) {
    String retString = "";
    if (obj == null)
      return "&nbsp;"; 
    Date dd = (Date)obj;
    retString = String.valueOf(1900 + dd.getYear()) + "年" + (dd.getMonth() + 1) + "月" + dd.getDate() + "日";
    return retString;
  }
  
  public static String convertDateToString(Object obj) {
    String retString = "";
    if (obj == null)
      return null; 
    Date dd = (Date)obj;
    retString = String.valueOf(1900 + dd.getYear()) + "/" + (dd.getMonth() + 1) + "/" + dd.getDate();
    return retString;
  }
  
  public static String convertObjectToYearMonthDateMinSec(Object obj) {
    String retString = "";
    if (obj == null)
      return "&nbsp;"; 
    Date dd = (Date)obj;
    retString = String.valueOf(1900 + dd.getYear()) + "-" + (dd.getMonth() + 1) + "-" + dd.getDate() + "&nbsp;" + dd.getHours() + ":" + dd.getMinutes() + ":00";
    return retString;
  }
  
  public static String convertObjectToYearMonthDateMin(Object obj) {
    String retString = "";
    if (obj == null)
      return "&nbsp;"; 
    Date dd = (Date)obj;
    retString = String.valueOf(1900 + dd.getYear()) + "-" + (dd.getMonth() + 1) + "-" + dd.getDate() + "&nbsp;" + dd.getHours() + ":" + dd.getMinutes();
    return retString;
  }
  
  public static String convertObjectToHourMinute(Object obj) {
    String retString = "";
    if (obj == null)
      return "&nbsp;"; 
    Date dd = (Date)obj;
    retString = String.valueOf(dd.getHours()) + "时" + dd.getMinutes() + "分";
    return retString;
  }
  
  public static boolean betweenBeginToEndDate(Object beginObj, Object endObj, Object nowObj) {
    Date begingTime = (Date)beginObj;
    Date endTime1 = (Date)endObj;
    Date endTime = (Date)endTime1.clone();
    endTime.setHours(23);
    endTime.setMinutes(59);
    endTime.setSeconds(59);
    Date nowTime = (Date)nowObj;
    if (begingTime.before(nowTime) && endTime.after(nowTime))
      return true; 
    return false;
  }
  
  public static String formatSqlTime(Object objTime) {
    return (String)objTime;
  }
  
  public static String formatObjectTime(Object objTime) {
    return (String)objTime;
  }
  
  public static String formatRequestTime(String strTime) {
    if (strTime == null)
      return "&nbsp;"; 
    if (strTime.indexOf(":") == -1)
      return strTime; 
    return strTime.substring(0, strTime.lastIndexOf(":"));
  }
  
  public static List getNextWeekYear() {
    List<String> list = new ArrayList();
    Calendar cal = Calendar.getInstance();
    cal.setFirstDayOfWeek(2);
    int curDayOfWeek = cal.get(7);
    int sub = 9 - curDayOfWeek;
    cal.add(5, sub);
    list.add((new StringBuilder(String.valueOf(cal.get(1)))).toString());
    list.add((new StringBuilder(String.valueOf(cal.get(2) + 1))).toString());
    list.add((new StringBuilder(String.valueOf(cal.get(5)))).toString());
    return list;
  }
  
  public static List getNextWeekOfYear() {
    List<String> list = new ArrayList();
    Calendar cal = Calendar.getInstance();
    cal.setFirstDayOfWeek(2);
    int curDayOfWeek = cal.get(7);
    int sub = 9 - curDayOfWeek;
    cal.add(5, sub);
    list.add((new StringBuilder(String.valueOf(cal.get(1)))).toString());
    list.add((new StringBuilder(String.valueOf(cal.get(3)))).toString());
    return list;
  }
  
  public static List getCurWeekYear(Object year, Object week) {
    List<String> list = new ArrayList();
    Calendar cal = Calendar.getInstance();
    cal.setFirstDayOfWeek(2);
    if (year != null && week != null)
      try {
        cal.set(1, Integer.parseInt((String)year));
        cal.set(3, Integer.parseInt((String)week));
      } catch (Exception e) {
        e.printStackTrace();
      }  
    list.add((new StringBuilder(String.valueOf(cal.get(1)))).toString());
    list.add((new StringBuilder(String.valueOf(cal.get(3)))).toString());
    return list;
  }
  
  public static Date getLastDateOfMonthByYearMonth(String year, String month) {
    Date result = new Date();
    Date tmp = new Date(String.valueOf(year) + "/" + month + "/31");
    if (tmp.getMonth() + 1 > Integer.parseInt(month)) {
      tmp = new Date(String.valueOf(year) + "/" + month + "/30");
      if (tmp.getMonth() + 1 > Integer.parseInt(month)) {
        tmp = new Date(String.valueOf(year) + "/" + month + "/29");
        if (tmp.getMonth() + 1 > Integer.parseInt(month))
          tmp = new Date(String.valueOf(year) + "/" + month + "/28"); 
      } 
    } 
    tmp.setHours(23);
    tmp.setMinutes(59);
    tmp.setSeconds(59);
    result = (Date)tmp.clone();
    return result;
  }
  
  public static Date getFirstDateOfMonthByYearMonth(String year, String month) {
    Date result = new Date();
    Date tmp = new Date(String.valueOf(year) + "/" + month + "/1");
    tmp.setHours(0);
    tmp.setMinutes(0);
    tmp.setSeconds(1);
    result = (Date)tmp.clone();
    return result;
  }
}
