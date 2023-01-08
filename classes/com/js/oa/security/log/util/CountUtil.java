package com.js.oa.security.log.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CountUtil {
  private SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
  
  public String[] getBeginAndEndDate(String flag, String beginDateTime, String endDateTime) {
    beginDateTime = dateFormat(beginDateTime);
    endDateTime = dateFormat(endDateTime);
    String[] returnValue = { beginDateTime.equals("") ? endDateTime : beginDateTime, 
        endDateTime.equals("") ? beginDateTime : endDateTime, 
        "1" };
    if (returnValue[0].equals(""))
      returnValue[0] = this.ymd.format(new Date()); 
    if (returnValue[1].equals(""))
      returnValue[1] = this.ymd.format(new Date()); 
    try {
      if ("d".equals(flag)) {
        returnValue[1] = returnValue[0];
      } else if ("w".equalsIgnoreCase(flag)) {
        Calendar weekCalendar = Calendar.getInstance();
        weekCalendar.setTime(this.ymd.parse(returnValue[0]));
        int day_of_week = weekCalendar.get(7) - 1;
        if (day_of_week == 0)
          day_of_week = 7; 
        weekCalendar.add(5, -day_of_week + 1);
        returnValue[0] = this.ymd.format(weekCalendar.getTime());
        if (!this.ymd.format(new Date()).equals(returnValue[1])) {
          weekCalendar.add(5, 6);
          returnValue[1] = this.ymd.format(weekCalendar.getTime());
        } 
      } else if ("m".equalsIgnoreCase(flag)) {
        Calendar monthCalendar = Calendar.getInstance();
        monthCalendar.setTime(this.ymd.parse(returnValue[0]));
        monthCalendar.set(5, 1);
        returnValue[0] = this.ymd.format(monthCalendar.getTime());
        if (!this.ymd.format(new Date()).equals(returnValue[1])) {
          monthCalendar.set(5, monthCalendar.getActualMaximum(5));
          returnValue[1] = this.ymd.format(monthCalendar.getTime());
        } 
      } else {
        Calendar yearCalendar = Calendar.getInstance();
        yearCalendar.setTime(this.ymd.parse(returnValue[0]));
        yearCalendar.set(6, 1);
        returnValue[0] = this.ymd.format(yearCalendar.getTime());
        if ("y".equalsIgnoreCase(flag) && !this.ymd.format(new Date()).equals(returnValue[1])) {
          yearCalendar.set(5, yearCalendar.getActualMaximum(6));
          returnValue[1] = this.ymd.format(yearCalendar.getTime());
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return returnValue;
  }
  
  public String dateFormat(String dateTimeStr) {
    dateTimeStr = dateTimeStr.replace("/", "-");
    if (!dateTimeStr.equals("")) {
      SimpleDateFormat format = new SimpleDateFormat("yyyy-M-d");
      try {
        dateTimeStr = this.ymd.format(format.parse(dateTimeStr));
      } catch (ParseException e) {
        e.printStackTrace();
      } 
    } 
    return dateTimeStr;
  }
  
  public static void main(String[] args) {
    String[] dStrings = (new CountUtil()).getBeginAndEndDate("", "", "2015-3-16");
    System.out.println("开始时间：" + dStrings[0] + "  结束时间：" + dStrings[1] + "   天数：" + dStrings[2]);
  }
}
