package com.js.oa.hr.kq.bry.util;

import com.js.system.util.StaticParam;
import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BryUtil {
  SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  
  SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
  
  public String[] getDutySet(String userId) {
    String[] dutySet = new String[5];
    String orgId = StaticParam.getOrgIdByEmpId(userId);
    String[] orgIds = StaticParam.getOrgIdStringByOrgId(orgId).split("\\$");
    String sql = "SELECT duty_time1,duty_time2,duty_time3,duty_time4,workday FROM kq_duty_set WHERE user_ids LIKE '%$" + userId + "$%'";
    for (int i = 1; i < orgIds.length; i += 2)
      sql = String.valueOf(sql) + " or org_ids like '%*" + orgIds[i] + "*%'"; 
    sql = String.valueOf(sql) + " order by id";
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      if (rs.next()) {
        if (rs.getString(3) == null || rs.getString(3).equals("")) {
          dutySet[0] = String.valueOf(rs.getString(1)) + ":00";
          dutySet[1] = String.valueOf(rs.getString(2)) + ":00";
          dutySet[2] = String.valueOf(rs.getString(2)) + ":00";
          dutySet[3] = String.valueOf(rs.getString(2)) + ":00";
        } else {
          dutySet[0] = String.valueOf(rs.getString(1)) + ":00";
          dutySet[1] = String.valueOf(rs.getString(2)) + ":00";
          dutySet[2] = String.valueOf(rs.getString(3)) + ":00";
          dutySet[3] = String.valueOf(rs.getString(4)) + ":00";
        } 
        dutySet[4] = rs.getString(5);
      } 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return dutySet;
  }
  
  public Integer[] getDutySetInt(String userId) {
    String[] dutySet = getDutySet(userId);
    Integer[] dutySetInt = new Integer[5];
    dutySetInt[0] = Integer.valueOf(getSInt(dutySet[0]));
    dutySetInt[1] = Integer.valueOf(getSInt(dutySet[1]));
    dutySetInt[2] = Integer.valueOf(getSInt(dutySet[2]));
    dutySetInt[3] = Integer.valueOf(getSInt(dutySet[3]));
    String week = "";
    char[] c = dutySet[4].toCharArray();
    for (int i = 1; i <= c.length; i++) {
      if ('0' == c[i - 1])
        week = String.valueOf(week) + i; 
    } 
    dutySetInt[4] = Integer.valueOf("".equals(week) ? 0 : Integer.valueOf(week).intValue());
    return dutySetInt;
  }
  
  public int getSInt(String timeStr) {
    String[] s = { "0", "0", "0" };
    String[] str = timeStr.split(":");
    for (int i = 0; i < str.length; i++)
      s[i] = str[i]; 
    return Integer.valueOf(s[0]).intValue() * 3600 + Integer.valueOf(s[1]).intValue() * 60 + Integer.valueOf(s[2]).intValue();
  }
  
  public Integer isHoliday(String dateStr, String userId) {
    Integer result = Integer.valueOf(-1);
    DataSourceBase base = new DataSourceBase();
    Date date = null;
    String sql = "select type from kq_holiday where str_to_date('" + dateStr + "','%Y-%m-%d') between begin_date and end_date";
    String dbType = SystemCommon.getDatabaseType();
    if (dbType.equals("oracle"))
      sql = "select type from kq_holiday where to_date('" + dateStr + "','yyyy-mm-dd') between begin_date and end_date"; 
    try {
      base.begin();
      date = this.ymd.parse(dateStr);
      ResultSet rs = base.executeQuery(sql);
      if (rs.next())
        result = Integer.valueOf((rs.getString(1) == null) ? -1 : rs.getInt(1)); 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    if (-1 == result.intValue()) {
      Integer[] dutySetInt = getDutySetInt(userId);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      int weekDay = calendar.get(7);
      if (dutySetInt[4].contains(weekDay)) {
        result = Integer.valueOf(0);
      } else {
        result = Integer.valueOf(1);
      } 
    } 
    return result;
  }
  
  public float getDatelong(Date[] kqData, Date startDate, Date endDate) {
    float returnV = 0.0F;
    long startN = startDate.getTime();
    long endN = endDate.getTime();
    long kqN1 = kqData[0].getTime();
    long kqN2 = kqData[1].getTime();
    long kqN3 = kqData[2].getTime();
    long kqN4 = kqData[3].getTime();
    if (startN <= kqN1 && endN > kqN1 && endN <= kqN2) {
      returnV = (float)(endN - kqN1);
    } else if (startN <= kqN1 && endN > kqN2 && endN <= kqN3) {
      returnV = (float)(kqN2 - kqN1);
    } else if (startN <= kqN1 && endN > kqN3 && endN <= kqN4) {
      returnV = (float)(kqN2 - kqN1 + endN - kqN3);
    } else if (startN <= kqN1 && endN > kqN4) {
      returnV = (float)(kqN2 - kqN1 + kqN4 - kqN3);
    } else if (startN > kqN1 && startN <= kqN2 && endN > kqN1 && endN <= kqN2) {
      returnV = (float)(endN - startN);
    } else if (startN > kqN1 && startN <= kqN2 && endN > kqN2 && endN <= kqN3) {
      returnV = (float)(kqN2 - startN);
    } else if (startN > kqN1 && startN <= kqN2 && endN > kqN3 && endN <= kqN4) {
      returnV = (float)(kqN2 - startN + endN - kqN3);
    } 
    if (startN > kqN1 && startN <= kqN2 && endN > kqN4)
      returnV = (float)(kqN2 - startN + kqN4 - kqN3); 
    if (startN > kqN2 && startN <= kqN3 && endN > kqN3 && endN <= kqN4)
      returnV = (float)(endN - kqN3); 
    if (startN > kqN2 && startN <= kqN3 && endN > kqN4)
      returnV = (float)(kqN4 - kqN3); 
    if (startN > kqN3 && startN <= kqN4 && endN > kqN3 && endN <= kqN4)
      returnV = (float)(endN - startN); 
    if (startN > kqN3 && startN <= kqN4 && endN > kqN4)
      returnV = (float)(kqN4 - startN); 
    return returnV / 3600000.0F;
  }
  
  public float jisuanShiChang(String startDateTime, String endDateTime, String userId) {
    Integer[] dutySetInt = getDutySetInt(userId);
    String[] dutySet = getDutySet(userId);
    return jisuanShiChang(startDateTime, endDateTime, userId, dutySetInt, dutySet);
  }
  
  public float jisuanShiChang(String startDateTime, String endDateTime, String userId, Integer[] dutySetInt, String[] dutySet) {
    String startD = startDateTime.split(" ")[0];
    String startT = startDateTime.split(" ")[1];
    String endD = endDateTime.split(" ")[0];
    String endT = endDateTime.split(" ")[1];
    float returnValue = 0.0F;
    try {
      Date startDate = this.ymdhms.parse(startDateTime);
      Date endDate = this.ymdhms.parse(endDateTime);
      if (startDate.getTime() < endDate.getTime()) {
        long startV = this.ymdhms.parse(String.valueOf(startD) + " 08:00:00").getTime() / 86400000L;
        long endV = this.ymdhms.parse(String.valueOf(endD) + " 08:00:00").getTime() / 86400000L;
        if (startV == endV) {
          if (1 == isHoliday(startD, userId).intValue())
            if (getSInt(startT) <= dutySetInt[0].intValue() && getSInt(endT) >= dutySetInt[3].intValue()) {
              returnValue = 8.0F;
            } else {
              Date[] kqData = new Date[4];
              kqData[0] = this.ymdhms.parse(String.valueOf(startD) + " " + dutySet[0]);
              kqData[1] = this.ymdhms.parse(String.valueOf(startD) + " " + dutySet[1]);
              kqData[2] = this.ymdhms.parse(String.valueOf(startD) + " " + dutySet[2]);
              kqData[3] = this.ymdhms.parse(String.valueOf(startD) + " " + dutySet[3]);
              returnValue = getDatelong(kqData, startDate, endDate);
            }  
        } else {
          for (long i = startV; i <= endV; i++) {
            String dateStr = this.ymd.format(new Date(i * 86400000L + 28800000L));
            if (1 == isHoliday(dateStr, userId).intValue()) {
              Date[] kqData = new Date[4];
              kqData[0] = new Date(i * 86400000L + ((dutySetInt[0].intValue() - 28800) * 1000));
              kqData[1] = new Date(i * 86400000L + ((dutySetInt[1].intValue() - 28800) * 1000));
              kqData[2] = new Date(i * 86400000L + ((dutySetInt[2].intValue() - 28800) * 1000));
              kqData[3] = new Date(i * 86400000L + ((dutySetInt[3].intValue() - 28800) * 1000));
              Date c1 = kqData[0];
              Date c2 = kqData[3];
              if (i == startV) {
                if (getSInt(startT) <= dutySetInt[0].intValue()) {
                  returnValue += 8.0F;
                } else if (getSInt(startT) < dutySetInt[3].intValue()) {
                  c1 = startDate;
                  returnValue += getDatelong(kqData, c1, c2);
                } 
              } else if (i == endV) {
                if (getSInt(endT) >= dutySetInt[3].intValue()) {
                  returnValue += 8.0F;
                } else if (getSInt(endT) > dutySetInt[0].intValue()) {
                  c2 = endDate;
                  returnValue += getDatelong(kqData, c1, c2);
                } 
              } else {
                returnValue += 8.0F;
              } 
            } 
          } 
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return returnValue;
  }
  
  public String dateFormat(String dateTimeStr) {
    dateTimeStr = dateTimeStr.replace("/", "-");
    SimpleDateFormat format = new SimpleDateFormat("yyyy-M-d H:m:s");
    SimpleDateFormat ymdhm = new SimpleDateFormat("yyyy-M-d H:m");
    SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      if (dateTimeStr.indexOf(":") == dateTimeStr.lastIndexOf(":")) {
        dateTimeStr = ymdhms.format(ymdhm.parse(dateTimeStr));
      } else {
        dateTimeStr = ymdhms.format(format.parse(dateTimeStr));
      } 
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    return dateTimeStr;
  }
  
  public float getHour(float num, float jishu, float bijiao) {
    float n = bijiao - jishu;
    int z = (int)(num / jishu);
    num %= jishu;
    if (num > n && num < bijiao) {
      num = (z + 1) * jishu;
    } else {
      num = z * jishu;
    } 
    return num;
  }
  
  public float getHour(float num, float jishu) {
    return getHour(num, jishu, jishu);
  }
  
  public static void main(String[] args) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      Date[] kqData = { sdf.parse("2015-07-15 00:00:00"), 
          sdf.parse("2015-07-15 11:45:00"), 
          sdf.parse("2015-07-15 12:30:00"), 
          sdf.parse("2015-07-15 23:59:59") };
      Date startDate = sdf.parse("2015-07-15 08:15:00");
      Date endDate = sdf.parse("2015-07-15 17:30:00");
      float s = (new BryUtil()).getDatelong(kqData, startDate, endDate);
      System.out.println(s);
    } catch (ParseException e) {
      e.printStackTrace();
    } 
  }
}
