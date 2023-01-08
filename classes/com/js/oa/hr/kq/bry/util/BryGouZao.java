package com.js.oa.hr.kq.bry.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BryGouZao {
  private static SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  
  private static SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
  
  public static Long getTimeLong(String dateStr) {
    Long l = Long.valueOf((new Date()).getTime());
    try {
      Date date = ymdhms.parse(dateStr);
      l = Long.valueOf(date.getTime());
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    return l;
  }
  
  public static Date getDateYmdhms(String dateStr) {
    Date date = new Date();
    try {
      date = ymdhms.parse(String.valueOf(dateStr.substring(0, dateStr.lastIndexOf(":"))) + ":00");
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    return date;
  }
  
  public static Date getDateYmd(String dateStr) {
    Date date = new Date();
    try {
      date = ymd.parse(dateStr);
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    return date;
  }
  
  public static String[] chuchaiAndTiaoxiu(String beginDate, String endDate, String userId) {
    float chuchai = 0.0F;
    float tiaoxiu = 0.0F;
    try {
      String[] bDates = beginDate.split(" ");
      String[] eDates = endDate.split(" ");
      long bDate = ymd.parse(bDates[0]).getTime() + 28800000L;
      long eDate = ymd.parse(eDates[0]).getTime() + 28800000L;
      BryUtil util = new BryUtil();
      for (long l = bDate; l <= eDate; l += 86400000L) {
        float jisu = 0.0F;
        if (l == bDate) {
          int bNum = util.getSInt(bDates[1]);
          if (bNum < 43200) {
            jisu++;
          } else {
            jisu = (float)(jisu + 0.5D);
          } 
        } else if (l == eDate) {
          int eNum = util.getSInt(eDates[1]);
          if (eNum > 43200) {
            jisu++;
          } else {
            jisu = (float)(jisu + 0.5D);
          } 
        } else {
          jisu++;
        } 
        if (util.isHoliday(ymd.format(new Date(l)), userId).intValue() == 0)
          tiaoxiu += jisu; 
        chuchai += jisu;
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return new String[] { chuchai * 8.0F, tiaoxiu };
  }
}
