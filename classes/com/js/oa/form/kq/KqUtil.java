package com.js.oa.form.kq;

import com.js.oa.hr.kq.po.KqDutyOutPO;
import com.js.oa.hr.kq.service.KqDutyOutBD;
import com.js.system.util.StaticParam;
import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KqUtil {
  Map<String, Float> paibanMap = new HashMap<String, Float>();
  
  Map<String, Float> holidayMap = new HashMap<String, Float>();
  
  String dateString = ",";
  
  String databaseType = SystemCommon.getDatabaseType();
  
  public String[][] dutySetAll;
  
  public String[][] getDutySet(String userId) {
    return getDutySet(userId, "");
  }
  
  public String[][] getDutySet(String userId, String flag) {
    String sql = "SELECT duty_time1,duty_time2,duty_time3,duty_time4,duty_time5,duty_time6,workday,dutyhour,ifPunch,minNum FROM kq_duty_set ";
    sql = String.valueOf(sql) + " where user_ids LIKE '%$" + userId + "$%' ";
    String groupId = StaticParam.getGroupIdByEmpId(userId);
    String orgId = StaticParam.getOrgIdByEmpId(userId);
    String orgIds = "";
    if (!"".equals(orgId)) {
      orgIds = StaticParam.getParentOrgIdsByOrgId(orgId);
      String[] org = orgIds.split(",");
      for (int i = 0; i < org.length; i++)
        sql = String.valueOf(sql) + " OR org_ids LIKE '%*" + org[i] + "*%' "; 
    } 
    if (!"".equals(groupId)) {
      String[] group = groupId.split(",");
      for (int i = 0; i < group.length; i++)
        sql = String.valueOf(sql) + " OR group_ids LIKE '%@" + group[i] + "@%' "; 
    } 
    sql = String.valueOf(sql) + " order by id";
    String weekSet = "0000000";
    List<String[]> list = (List)new ArrayList<String>();
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    try {
      base.begin();
      rs = base.executeQuery(sql);
      while (rs.next() && 
        !"1111111".equals(weekSet)) {
        String[] duty = (String[])null;
        if ("".equals(flag)) {
          duty = new String[8];
        } else {
          duty = new String[10];
        } 
        duty[0] = rs.getString(1);
        duty[1] = rs.getString(2);
        duty[2] = rs.getString(3);
        duty[3] = rs.getString(4);
        duty[4] = rs.getString(5);
        duty[5] = rs.getString(6);
        duty[7] = rs.getString(8);
        if ("0000000".equals(weekSet)) {
          weekSet = rs.getString(7);
          duty[6] = weekSet;
        } else {
          char[] week = weekSet.toCharArray();
          char[] duty7 = rs.getString(7).toCharArray();
          for (int i = 0; i < week.length; i++) {
            if (week[i] != '1') {
              if (duty7[i] == '1')
                week[i] = '1'; 
            } else {
              duty7[i] = '0';
            } 
          } 
          duty[6] = new String(duty7);
          weekSet = new String(week);
        } 
        if (!"0000000".equals(duty[6]))
          list.add(duty); 
        if (!"".equals(flag)) {
          duty[8] = rs.getString(9);
          duty[9] = rs.getString(10);
        } 
      } 
      rs.close();
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    if (list.size() > 0) {
      String[][] arrayOfString = (String[][])null;
      if ("".equals(flag)) {
        arrayOfString = new String[list.size()][8];
        for (int i = 0; i < list.size(); i++)
          arrayOfString[i] = list.get(i); 
      } else {
        arrayOfString = new String[list.size()][10];
        for (int i = 0; i < list.size(); i++)
          arrayOfString[i] = list.get(i); 
      } 
      return arrayOfString;
    } 
    String[][] returnDuty = (String[][])null;
    if ("".equals(flag)) {
      returnDuty = new String[][] { { "08:30", "17:30", "0", "0", "0", "0", "1111111", "1" } };
    } else {
      returnDuty = new String[][] { { "08:30", "17:30", "0", "0", "0", "0", "1111111", "1", "1,0,0,1,0,0", "30,30,30,30,30,30" } };
    } 
    return returnDuty;
  }
  
  public float getHour1(float hourlen, String beginDate, String endDate, String flag, String userId) {
    List<Object[]> list = new ArrayList();
    try {
      this.dutySetAll = getDutySet(userId);
      for (int x = 0; x < this.dutySetAll.length; x++) {
        String[] dutySet = this.dutySetAll[x];
        int[] dutyTime = new int[6];
        int[] timeJiange = new int[3];
        int minute = 0;
        for (int i = 0; i < dutySet.length - 2; i += 2) {
          int beginTime = 0;
          int endTime = 0;
          if (dutySet[i] != null && !"".equals(dutySet[i]) && !"null".equals(dutySet[i]) && dutySet[i].indexOf(":") > 0) {
            String[] timeS = dutySet[i].split(":");
            beginTime += 60 * Integer.valueOf(timeS[0]).intValue();
            beginTime += Integer.valueOf(timeS[1]).intValue();
            dutyTime[i] = beginTime;
          } else {
            dutyTime[i] = dutyTime[i - 1];
          } 
          if (dutySet[i + 1] != null && !"".equals(dutySet[i + 1]) && !"null".equals(dutySet[i + 1]) && dutySet[i].indexOf(":") > 0) {
            String[] timeS = dutySet[i + 1].split(":");
            endTime += 60 * Integer.valueOf(timeS[0]).intValue();
            endTime += Integer.valueOf(timeS[1]).intValue();
            dutyTime[i + 1] = endTime;
          } else {
            dutyTime[i + 1] = dutyTime[i];
          } 
          if (endTime > beginTime) {
            minute += endTime - beginTime;
            timeJiange[i / 2] = endTime - beginTime;
          } 
        } 
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date_start = sdf.parse(beginDate);
        Date date_end = sdf.parse(endDate);
        Calendar cal_start = Calendar.getInstance();
        Calendar cal_end = Calendar.getInstance();
        cal_start.setTime(date_start);
        cal_end.setTime(date_end);
        hourlen = getDaysBetween(hourlen, cal_start, cal_end, dutySet, dutyTime, timeJiange, minute);
        DataSourceBase base = new DataSourceBase();
        ResultSet rs = null;
        String begin = String.valueOf(beginDate.substring(0, 10)) + " 00:00:00";
        String end = String.valueOf(endDate.substring(0, 10)) + " 23:59:59";
        String holidaySql = "";
        if (this.databaseType.indexOf("mysql") >= 0)
          holidaySql = "SELECT begin_date,end_date,TYPE FROM kq_holiday WHERE (begin_date BETWEEN '" + begin + "' " + 
            "AND '" + end + "') OR (end_date BETWEEN '" + begin + "' AND '" + end + "') order by begin_date"; 
        if (this.databaseType.indexOf("oracle") >= 0)
          holidaySql = "SELECT begin_date,end_date,TYPE FROM kq_holiday WHERE (begin_date BETWEEN to_date('" + begin + "','yyyy-MM-dd HH24:MI:SS') " + 
            "AND to_date('" + end + "','yyyy-MM-dd HH24:MI:SS')) OR (end_date BETWEEN to_date('" + begin + "','yyyy-MM-dd HH24:MI:SS')" + 
            " AND to_date('" + end + "','yyyy-MM-dd HH24:MI:SS')) order by begin_date"; 
        try {
          base.begin();
          rs = base.executeQuery(holidaySql);
          SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          while (rs.next()) {
            Object[] obj = new Object[3];
            Calendar startCal = Calendar.getInstance();
            startCal.setTime(format.parse(rs.getString(1)));
            if (startCal.after(cal_start)) {
              obj[0] = startCal;
            } else {
              obj[0] = cal_start;
            } 
            Calendar endCal = Calendar.getInstance();
            endCal.setTime(format.parse(rs.getString(2)));
            if (endCal.after(cal_end)) {
              obj[1] = cal_end;
            } else {
              obj[1] = endCal;
            } 
            obj[2] = Integer.valueOf(rs.getInt(3));
            list.add(obj);
          } 
          rs.close();
          base.end();
        } catch (Exception e) {
          base.end();
          e.printStackTrace();
        } 
        for (int j = 0; j < list.size(); j++) {
          Object[] obj = list.get(j);
          Calendar d1 = (Calendar)obj[0];
          Calendar d2 = (Calendar)obj[1];
          hourlen = getHoliday(hourlen, d1, d2, dutySet, ((Integer)obj[2]).intValue(), dutyTime, timeJiange, minute);
        } 
      } 
      if (!"5".equals(flag))
        try {
          String[] dateStr = (String[])null;
          if (this.dateString.length() > 1) {
            dateStr = this.dateString.substring(1, this.dateString.length() - 1).split(",");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            for (int i = 0; i < dateStr.length; i++) {
              float hour = 0.0F;
              if (this.holidayMap.get(dateStr[i]) != null && this.paibanMap.get(dateStr[i]) != null) {
                hour = ((Float)this.holidayMap.get(dateStr[i])).floatValue() + ((Float)this.paibanMap.get(dateStr[i])).floatValue();
              } else if (this.holidayMap.get(dateStr[i]) != null) {
                hour = ((Float)this.holidayMap.get(dateStr[i])).floatValue();
              } else {
                hour = ((Float)this.paibanMap.get(dateStr[i])).floatValue();
              } 
              if (0.0F != hour) {
                KqDutyOutPO po = new KqDutyOutPO();
                po.setOutEmp(userId);
                po.setOutDate(format.parse(dateStr[i]));
                po.setOutHour(hour);
                po.setOutType(flag);
                po.setOutInputDate(new Date());
                KqDutyOutBD outBD = new KqDutyOutBD();
                outBD.add(po);
              } 
            } 
          } 
        } catch (Exception e) {
          e.printStackTrace();
        }  
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return hourlen;
  }
  
  public float getDaysBetween(float dayNum, Calendar d1, Calendar d2, String[] dutyStr, int[] dutyTime, int[] timeJiange, int minute) {
    float dutyHour = Float.valueOf(dutyStr[7]).floatValue();
    char[] weekDay = dutyStr[6].toCharArray();
    if (d1.after(d2) || d1.equals(d2)) {
      System.out.println("开始时间晚于或等于结束时间！");
    } else {
      int y2 = d2.get(1);
      int y1 = d1.get(1);
      if (y2 == y1) {
        if (d1.get(6) != d2.get(6)) {
          Calendar newC = (Calendar)d1.clone();
          for (int i = d1.get(6); i <= d2.get(6); i++) {
            newC.set(6, i);
            if (i == d1.get(6)) {
              if (weekDay[newC.get(7) - 1] == '1') {
                int beginTime = d1.get(11) * 60 + d1.get(12);
                int todayTime = 0;
                int timeNum = -1;
                for (int t = 0; t < dutyTime.length; t++) {
                  if (dutyTime[t] != 0 && beginTime < dutyTime[t]) {
                    timeNum = t;
                    break;
                  } 
                } 
                if (timeNum >= 0) {
                  if (timeNum % 2 == 0) {
                    for (int tn = timeNum / 2; tn < timeJiange.length; tn++)
                      todayTime += timeJiange[tn]; 
                  } else {
                    for (int tn = (timeNum + 1) / 2; tn < timeJiange.length; tn++)
                      todayTime += timeJiange[tn]; 
                    todayTime += dutyTime[timeNum] - beginTime;
                  } 
                  dayNum += todayTime / minute * dutyHour;
                  String key = String.valueOf(d1.get(1)) + "-" + (d1.get(2) + 1) + "-" + d1.get(5);
                  this.paibanMap.put(key, Float.valueOf(todayTime / minute * dutyHour));
                  if (this.dateString.indexOf("," + key + ",") < 0)
                    this.dateString = String.valueOf(this.dateString) + key + ","; 
                } 
              } 
            } else if (i == d2.get(6)) {
              if (weekDay[newC.get(7) - 1] == '1') {
                int endTime = d2.get(11) * 60 + d2.get(12);
                int todayTime = 0;
                int timeNum = -1;
                for (int t = 0; t < dutyTime.length; t++) {
                  if (dutyTime[t] != 0 && endTime < dutyTime[t]) {
                    timeNum = t;
                    break;
                  } 
                } 
                if (timeNum >= 0) {
                  if (timeNum % 2 == 0) {
                    for (int tn = 0; tn < timeNum / 2; tn++)
                      todayTime += timeJiange[tn]; 
                  } else {
                    for (int tn = 0; tn < (timeNum - 1) / 2; tn++)
                      todayTime += timeJiange[tn]; 
                    todayTime += endTime - dutyTime[timeNum - 1];
                  } 
                  dayNum += todayTime / minute * dutyHour;
                  String key = String.valueOf(d2.get(1)) + "-" + (d2.get(2) + 1) + "-" + d2.get(5);
                  this.paibanMap.put(key, Float.valueOf(todayTime / minute * dutyHour));
                  if (this.dateString.indexOf("," + key + ",") < 0)
                    this.dateString = String.valueOf(this.dateString) + key + ","; 
                } else {
                  dayNum += dutyHour;
                  String key = String.valueOf(d2.get(1)) + "-" + (d2.get(2) + 1) + "-" + d2.get(5);
                  this.paibanMap.put(key, Float.valueOf(dutyHour));
                  if (this.dateString.indexOf("," + key + ",") < 0)
                    this.dateString = String.valueOf(this.dateString) + key + ","; 
                } 
              } 
            } else {
              dayNum += dutyHour;
              String key = String.valueOf(newC.get(1)) + "-" + (newC.get(2) + 1) + "-" + newC.get(5);
              this.paibanMap.put(key, Float.valueOf(dutyHour));
              if (weekDay[newC.get(7) - 1] == '1' && this.dateString.indexOf("," + key + ",") < 0)
                this.dateString = String.valueOf(this.dateString) + key + ","; 
            } 
          } 
        } else {
          dayNum += getHourOneDay(dayNum, d1, d2, dutyTime, timeJiange, minute) * dutyHour;
          String key = String.valueOf(d1.get(1)) + "-" + (d1.get(2) + 1) + "-" + d1.get(5);
          this.paibanMap.put(key, Float.valueOf(dayNum * dutyHour));
          if (this.dateString.indexOf("," + key + ",") < 0)
            this.dateString = String.valueOf(this.dateString) + key + ","; 
        } 
      } else {
        Calendar newC = (Calendar)d1.clone();
        for (int i = d1.get(6); i <= d1.getActualMaximum(6) + d2.get(6); i++) {
          if (i > d1.getActualMaximum(6) + 1) {
            newC.set(6, i - d1.getActualMaximum(6));
          } else {
            newC.set(6, i);
          } 
          if (i == d1.get(6)) {
            if (weekDay[newC.get(7) - 1] == '1') {
              int beginTime = d1.get(11) * 60 + d1.get(12);
              int todayTime = 0;
              int timeNum = -1;
              for (int t = 0; t < dutyTime.length; t++) {
                if (dutyTime[t] != 0 && beginTime < dutyTime[t]) {
                  timeNum = t;
                  break;
                } 
              } 
              if (timeNum >= 0) {
                if (timeNum % 2 == 0) {
                  for (int tn = timeNum / 2; tn < timeJiange.length; tn++)
                    todayTime += timeJiange[tn]; 
                } else {
                  for (int tn = (timeNum + 1) / 2; tn < timeJiange.length; tn++)
                    todayTime += timeJiange[tn]; 
                  todayTime += dutyTime[timeNum] - beginTime;
                } 
                dayNum += todayTime / minute * dutyHour;
                String key = String.valueOf(d1.get(1)) + "-" + (d1.get(2) + 1) + "-" + d1.get(5);
                this.paibanMap.put(key, Float.valueOf(todayTime / minute * dutyHour));
                if (this.dateString.indexOf("," + key + ",") < 0)
                  this.dateString = String.valueOf(this.dateString) + key + ","; 
              } 
            } 
          } else if (i == d1.getActualMaximum(6) + d2.get(6)) {
            if (weekDay[newC.get(7) - 1] == '1') {
              int endTime = d2.get(11) * 60 + d2.get(12);
              int todayTime = 0;
              int timeNum = -1;
              for (int t = 0; t < dutyTime.length; t++) {
                if (dutyTime[t] != 0 && endTime < dutyTime[t]) {
                  timeNum = t;
                  break;
                } 
              } 
              if (timeNum >= 0) {
                if (timeNum % 2 == 0) {
                  for (int tn = 0; tn < timeNum / 2; tn++)
                    todayTime += timeJiange[tn]; 
                } else {
                  for (int tn = 0; tn < (timeNum - 1) / 2; tn++)
                    todayTime += timeJiange[tn]; 
                  todayTime += endTime - dutyTime[timeNum - 1];
                } 
                float x = todayTime / minute * dutyHour;
                dayNum += x;
                String key = String.valueOf(d2.get(1)) + "-" + (d2.get(2) + 1) + "-" + d2.get(5);
                this.paibanMap.put(key, Float.valueOf(x));
                if (this.dateString.indexOf("," + key + ",") < 0)
                  this.dateString = String.valueOf(this.dateString) + key + ","; 
              } else {
                dayNum += dutyHour;
                String key = String.valueOf(d2.get(1)) + "-" + (d2.get(2) + 1) + "-" + d2.get(5);
                this.paibanMap.put(key, Float.valueOf(dutyHour));
                if (this.dateString.indexOf("," + key + ",") < 0)
                  this.dateString = String.valueOf(this.dateString) + key + ","; 
              } 
            } 
          } else {
            dayNum += dutyHour;
            String key = String.valueOf(newC.get(1)) + "-" + (newC.get(2) + 1) + "-" + newC.get(5);
            this.paibanMap.put(key, Float.valueOf(dutyHour));
            if (weekDay[newC.get(7) - 1] == '1' && this.dateString.indexOf("," + key + ",") < 0)
              this.dateString = String.valueOf(this.dateString) + key + ","; 
          } 
        } 
      } 
    } 
    return dayNum;
  }
  
  public String getChineseWeek(Calendar date) {
    String[] dayNames = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
    int dayOfWeek = date.get(7);
    return dayNames[dayOfWeek - 1];
  }
  
  public float getHoliday(float dayNum, Calendar d1, Calendar d2, String[] dutyStr, int flag, int[] dutyTime, int[] timeJiange, int minute) {
    float dutyHour = Float.valueOf(dutyStr[7]).floatValue();
    char[] weekDay = dutyStr[6].toCharArray();
    if (d1.after(d2) || d1.equals(d2)) {
      System.out.println("开始时间晚于或等于结束时间！");
    } else {
      int y2 = d2.get(1);
      int y1 = d1.get(1);
      if (y2 == y1) {
        if (d1.get(6) != d2.get(6)) {
          Calendar newC = (Calendar)d1.clone();
          for (int i = d1.get(6); i <= d2.get(6); i++) {
            newC.set(6, i);
            if (i == d1.get(6)) {
              int beginTime = d1.get(11) * 60 + d1.get(12);
              int todayTime = 0;
              int timeNum = -1;
              for (int t = 0; t < dutyTime.length; t++) {
                if (dutyTime[t] != 0 && beginTime < dutyTime[t]) {
                  timeNum = t;
                  break;
                } 
              } 
              if (timeNum >= 0) {
                if (timeNum % 2 == 0) {
                  for (int tn = timeNum / 2; tn < timeJiange.length; tn++)
                    todayTime += timeJiange[tn]; 
                } else {
                  for (int tn = (timeNum + 1) / 2; tn < timeJiange.length; tn++)
                    todayTime += timeJiange[tn]; 
                  todayTime += dutyTime[timeNum] - beginTime;
                } 
                if (weekDay[newC.get(7) - 1] != '0' || flag != 0)
                  if (weekDay[newC.get(7) - 1] == '0' && flag == 1) {
                    dayNum += todayTime / minute * dutyHour;
                    String key = String.valueOf(d1.get(1)) + "-" + (d1.get(2) + 1) + "-" + d1.get(5);
                    this.holidayMap.put(key, Float.valueOf(todayTime / minute * dutyHour));
                    if (this.dateString.indexOf("," + key + ",") < 0)
                      this.dateString = String.valueOf(this.dateString) + key + ","; 
                  } else if (weekDay[newC.get(7) - 1] == '1' && flag == 0) {
                    dayNum -= todayTime / minute * dutyHour;
                    String key = String.valueOf(d1.get(1)) + "-" + (d1.get(2) + 1) + "-" + d1.get(5);
                    this.holidayMap.put(key, Float.valueOf(-1.0F * todayTime / minute * dutyHour));
                    if (this.dateString.indexOf("," + key + ",") < 0)
                      this.dateString = String.valueOf(this.dateString) + key + ","; 
                  } else if (weekDay[newC.get(7) - 1] == '1') {
                  
                  }  
              } 
            } else if (i == d2.get(6)) {
              int endTime = d2.get(11) * 60 + d2.get(12);
              int todayTime = 0;
              int timeNum = -1;
              for (int t = 0; t < dutyTime.length; t++) {
                if (dutyTime[t] != 0 && endTime < dutyTime[t]) {
                  timeNum = t;
                  break;
                } 
              } 
              if (timeNum >= 0) {
                if (timeNum % 2 == 0) {
                  for (int tn = 0; tn < timeNum / 2; tn++)
                    todayTime += timeJiange[tn]; 
                } else {
                  for (int tn = 0; tn < (timeNum - 1) / 2; tn++)
                    todayTime += timeJiange[tn]; 
                  todayTime += endTime - dutyTime[timeNum - 1];
                } 
                if (weekDay[newC.get(7) - 1] != '0' || flag != 0)
                  if (weekDay[newC.get(7) - 1] == '0' && flag == 1) {
                    dayNum += todayTime / minute * dutyHour;
                    String key = String.valueOf(d2.get(1)) + "-" + (d2.get(2) + 1) + "-" + d2.get(5);
                    this.holidayMap.put(key, Float.valueOf(todayTime / minute * dutyHour));
                    if (this.dateString.indexOf("," + key + ",") < 0)
                      this.dateString = String.valueOf(this.dateString) + key + ","; 
                  } else if (weekDay[newC.get(7) - 1] == '1' && flag == 0) {
                    dayNum -= todayTime / minute * dutyHour;
                    String key = String.valueOf(d2.get(1)) + "-" + (d2.get(2) + 1) + "-" + d2.get(5);
                    this.holidayMap.put(key, Float.valueOf(todayTime / minute * dutyHour * -1.0F));
                    if (this.dateString.indexOf("," + key + ",") < 0)
                      this.dateString = String.valueOf(this.dateString) + key + ","; 
                  } else if (weekDay[newC.get(7) - 1] == '1') {
                  
                  }  
              } else if (weekDay[newC.get(7) - 1] != '0' || flag != 0) {
                if (weekDay[newC.get(7) - 1] == '0' && flag == 1) {
                  dayNum += dutyHour;
                  String key = String.valueOf(d2.get(1)) + "-" + (d2.get(2) + 1) + "-" + d2.get(5);
                  this.holidayMap.put(key, Float.valueOf(dutyHour));
                  if (this.dateString.indexOf("," + key + ",") < 0)
                    this.dateString = String.valueOf(this.dateString) + key + ","; 
                } else if (weekDay[newC.get(7) - 1] == '1' && flag == 0) {
                  dayNum -= dutyHour;
                  String key = String.valueOf(d2.get(1)) + "-" + (d2.get(2) + 1) + "-" + d2.get(5);
                  this.holidayMap.put(key, Float.valueOf(-1.0F * dutyHour));
                  if (this.dateString.indexOf("," + key + ",") < 0)
                    this.dateString = String.valueOf(this.dateString) + key + ","; 
                } else if (weekDay[newC.get(7) - 1] == '1') {
                
                } 
              } 
            } else if (weekDay[newC.get(7) - 1] != '0' || flag != 0) {
              if (weekDay[newC.get(7) - 1] == '0' && flag == 1) {
                dayNum += dutyHour;
                String key = String.valueOf(newC.get(1)) + "-" + (newC.get(2) + 1) + "-" + newC.get(5);
                this.holidayMap.put(key, Float.valueOf(dutyHour));
                if (this.dateString.indexOf("," + key + ",") < 0)
                  this.dateString = String.valueOf(this.dateString) + key + ","; 
              } else if (weekDay[newC.get(7) - 1] == '1' && flag == 0) {
                dayNum -= dutyHour;
                String key = String.valueOf(newC.get(1)) + "-" + (newC.get(2) + 1) + "-" + newC.get(5);
                this.holidayMap.put(key, Float.valueOf(-1.0F * dutyHour));
                if (this.dateString.indexOf("," + key + ",") < 0)
                  this.dateString = String.valueOf(this.dateString) + key + ","; 
              } else if (weekDay[newC.get(7) - 1] == '1') {
              
              } 
            } 
          } 
        } else if (weekDay[d1.get(7) - 1] != '0' || flag != 0) {
          if (weekDay[d1.get(7) - 1] == '0' && flag == 1) {
            dayNum += getHourOneDay(dayNum, d1, d2, dutyTime, timeJiange, minute) * dutyHour;
            String key = String.valueOf(d1.get(1)) + "-" + (d1.get(2) + 1) + "-" + d1.get(5);
            this.holidayMap.put(key, Float.valueOf(getHourOneDay(dayNum, d1, d2, dutyTime, timeJiange, minute) * dutyHour));
            if (this.dateString.indexOf("," + key + ",") < 0)
              this.dateString = String.valueOf(this.dateString) + key + ","; 
          } else if (weekDay[d1.get(7) - 1] == '1' && flag == 0) {
            dayNum -= getHourOneDay(dayNum, d1, d2, dutyTime, timeJiange, minute) * dutyHour;
            String key = String.valueOf(d1.get(1)) + "-" + (d1.get(2) + 1) + "-" + d1.get(5);
            this.holidayMap.put(key, Float.valueOf(getHourOneDay(dayNum, d1, d2, dutyTime, timeJiange, minute) * dutyHour * -1.0F));
            if (this.dateString.indexOf("," + key + ",") < 0)
              this.dateString = String.valueOf(this.dateString) + key + ","; 
          } else if (weekDay[d1.get(7) - 1] == '1') {
          
          } 
        } 
      } else {
        Calendar newC = (Calendar)d1.clone();
        for (int i = d1.get(6); i <= d1.getActualMaximum(6) + d2.get(6); i++) {
          if (i > d1.getActualMaximum(6) + 1) {
            newC.set(6, i - d1.getActualMaximum(6));
          } else {
            newC.set(6, i);
          } 
          if (i == d1.get(6)) {
            int beginTime = d1.get(11) * 60 + d1.get(12);
            int todayTime = 0;
            int timeNum = -1;
            for (int t = 0; t < dutyTime.length; t++) {
              if (dutyTime[t] != 0 && beginTime < dutyTime[t]) {
                timeNum = t;
                break;
              } 
            } 
            if (timeNum >= 0) {
              if (timeNum % 2 == 0) {
                for (int tn = timeNum / 2; tn < timeJiange.length; tn++)
                  todayTime += timeJiange[tn]; 
              } else {
                for (int tn = (timeNum + 1) / 2; tn < timeJiange.length; tn++)
                  todayTime += timeJiange[tn]; 
                todayTime += dutyTime[timeNum] - beginTime;
              } 
              if (weekDay[newC.get(7) - 1] != '0' || flag != 0)
                if (weekDay[newC.get(7) - 1] == '0' && flag == 1) {
                  dayNum += todayTime / minute * dutyHour;
                  String key = String.valueOf(d1.get(1)) + "-" + (d1.get(2) + 1) + "-" + d1.get(5);
                  this.holidayMap.put(key, Float.valueOf(todayTime / minute * dutyHour));
                  if (this.dateString.indexOf("," + key + ",") < 0)
                    this.dateString = String.valueOf(this.dateString) + key + ","; 
                } else if (weekDay[newC.get(7) - 1] == '1' && flag == 0) {
                  dayNum -= todayTime / minute * dutyHour;
                  String key = String.valueOf(d1.get(1)) + "-" + (d1.get(2) + 1) + "-" + d1.get(5);
                  this.holidayMap.put(key, Float.valueOf((todayTime / minute) * dutyHour * -1.0F));
                  if (this.dateString.indexOf("," + key + ",") < 0)
                    this.dateString = String.valueOf(this.dateString) + key + ","; 
                } else if (weekDay[newC.get(7) - 1] == '1') {
                
                }  
            } 
          } else if (i == d1.getActualMaximum(6) + d2.get(6)) {
            int endTime = d2.get(11) * 60 + d2.get(12);
            int todayTime = 0;
            int timeNum = -1;
            for (int t = 0; t < dutyTime.length; t++) {
              if (dutyTime[t] != 0 && endTime < dutyTime[t]) {
                timeNum = t;
                break;
              } 
            } 
            if (timeNum >= 0) {
              if (timeNum % 2 == 0) {
                for (int tn = 0; tn < timeNum / 2; tn++)
                  todayTime += timeJiange[tn]; 
              } else {
                for (int tn = 0; tn < (timeNum - 1) / 2; tn++)
                  todayTime += timeJiange[tn]; 
                todayTime += endTime - dutyTime[timeNum - 1];
              } 
              if (weekDay[newC.get(7) - 1] != '0' || flag != 0)
                if (weekDay[newC.get(7) - 1] == '0' && flag == 1) {
                  dayNum += (todayTime / minute) * dutyHour;
                  String key = String.valueOf(newC.get(1)) + "-" + (newC.get(2) + 1) + "-" + newC.get(5);
                  this.holidayMap.put(key, Float.valueOf((todayTime / minute) * dutyHour));
                  if (this.dateString.indexOf("," + key + ",") < 0)
                    this.dateString = String.valueOf(this.dateString) + key + ","; 
                } else if (weekDay[newC.get(7) - 1] == '1' && flag == 0) {
                  dayNum -= (todayTime / minute) * dutyHour;
                  String key = String.valueOf(newC.get(1)) + "-" + (newC.get(2) + 1) + "-" + newC.get(5);
                  this.holidayMap.put(key, Float.valueOf((todayTime / minute) * dutyHour * -1.0F));
                  if (this.dateString.indexOf("," + key + ",") < 0)
                    this.dateString = String.valueOf(this.dateString) + key + ","; 
                } else if (weekDay[newC.get(7) - 1] == '1') {
                
                }  
            } else if (weekDay[newC.get(7) - 1] != '0' || flag != 0) {
              if (weekDay[newC.get(7) - 1] == '0' && flag == 1) {
                dayNum += dutyHour;
                String key = String.valueOf(newC.get(1)) + "-" + (newC.get(2) + 1) + "-" + newC.get(5);
                this.holidayMap.put(key, Float.valueOf(dutyHour));
                if (this.dateString.indexOf("," + key + ",") < 0)
                  this.dateString = String.valueOf(this.dateString) + key + ","; 
              } else if (weekDay[newC.get(7) - 1] == '1' && flag == 0) {
                dayNum -= dutyHour;
                String key = String.valueOf(newC.get(1)) + "-" + (newC.get(2) + 1) + "-" + newC.get(5);
                this.holidayMap.put(key, Float.valueOf(-1.0F * dutyHour));
                if (this.dateString.indexOf("," + key + ",") < 0)
                  this.dateString = String.valueOf(this.dateString) + key + ","; 
              } else if (weekDay[newC.get(7) - 1] == '1') {
              
              } 
            } 
          } else if (weekDay[newC.get(7) - 1] != '0' || flag != 0) {
            if (weekDay[newC.get(7) - 1] == '0' && flag == 1) {
              dayNum += dutyHour;
              String key = String.valueOf(newC.get(1)) + "-" + (newC.get(2) + 1) + "-" + newC.get(5);
              this.holidayMap.put(key, Float.valueOf(dutyHour));
              if (this.dateString.indexOf("," + key + ",") < 0)
                this.dateString = String.valueOf(this.dateString) + key + ","; 
            } else if (weekDay[newC.get(7) - 1] == '1' && flag == 0) {
              dayNum -= dutyHour;
              String key = String.valueOf(newC.get(1)) + "-" + (newC.get(2) + 1) + "-" + newC.get(5);
              this.holidayMap.put(key, Float.valueOf(-1.0F * dutyHour));
              if (this.dateString.indexOf("," + key + ",") < 0)
                this.dateString = String.valueOf(this.dateString) + key + ","; 
            } else if (weekDay[newC.get(7) - 1] == '1') {
            
            } 
          } 
        } 
      } 
    } 
    return dayNum;
  }
  
  public float getHourOneDay(float dayNum, Calendar d1, Calendar d2, int[] dutyTime, int[] timeJiange, int minute) {
    int beginTime = d1.get(11) * 60 + d1.get(12);
    int endTime = d2.get(11) * 60 + d2.get(12);
    int timebegin = -1;
    int timeend = 6;
    int t;
    for (t = 0; t < dutyTime.length; t++) {
      if (dutyTime[t] != 0 && beginTime < dutyTime[t]) {
        timebegin = t;
        break;
      } 
    } 
    for (t = 0; t < dutyTime.length; t++) {
      if (dutyTime[t] != 0 && endTime < dutyTime[t]) {
        timeend = t;
        break;
      } 
    } 
    if (timebegin == timeend) {
      dayNum = (endTime - beginTime) / minute;
    } else {
      int todayTime = 0;
      if (timebegin % 2 == 1)
        todayTime += dutyTime[timebegin] - beginTime; 
      if (timeend % 2 == 1)
        todayTime += endTime - dutyTime[timeend - 1]; 
      if (timebegin <= 0 && 1 < timeend)
        todayTime += timeJiange[0]; 
      if (2 >= timebegin && 3 < timeend)
        todayTime += timeJiange[1]; 
      if (4 >= timebegin && 5 < timeend)
        todayTime += timeJiange[2]; 
      dayNum = todayTime / minute;
    } 
    return dayNum;
  }
}
