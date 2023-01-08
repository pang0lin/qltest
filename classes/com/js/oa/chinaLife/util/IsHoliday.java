package com.js.oa.chinaLife.util;

import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class IsHoliday {
  private static SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
  
  public static String isHoliday(String dateStr) {
    String result = "-1";
    DataSourceBase base = new DataSourceBase();
    Date date = null;
    String sql = "select type from kq_holiday where to_date('" + dateStr + "','yyyy-mm-dd') between begin_date and end_date";
    try {
      base.begin();
      date = ymd.parse(dateStr);
      ResultSet rs = base.executeQuery(sql);
      if (rs.next())
        result = (rs.getString(1) == null) ? "-1" : rs.getString(1); 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    if ("-1".equals(result)) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      int weekDay = calendar.get(7);
      if (weekDay == 1 || weekDay == 7) {
        result = "0";
      } else {
        result = "1";
      } 
    } 
    return result;
  }
  
  public static boolean isBuqian(String dateStr, String time) {
    return false;
  }
}
