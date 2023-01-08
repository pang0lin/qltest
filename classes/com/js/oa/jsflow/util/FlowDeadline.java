package com.js.oa.jsflow.util;

import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FlowDeadline {
  private static String workingDaySet = "0111110";
  
  private static long workingTime = 0L;
  
  private static int workBeginHour = 8;
  
  private static int workBeginMinute = 30;
  
  private static int workEndHour = 17;
  
  private static int workEndMinute = 30;
  
  private static void initWorkingSet() {
    Connection conn = null;
    Statement stmt = null;
    String workDaySet = "0111110";
    int beginHour = 8;
    int beginMinute = 30;
    int endHour = 17;
    int endMinute = 30;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      String sql = "SELECT workday,duty_time1,duty_time2 FROM kq_duty_set ORDER BY id desc";
      ResultSet rs = stmt.executeQuery(sql);
      if (rs.next()) {
        workDaySet = rs.getString(1);
        String beginTime = rs.getString(2);
        String endTime = rs.getString(3);
        if (beginTime.indexOf(":") > 0) {
          beginHour = Integer.parseInt(beginTime.split(":")[0]);
          beginMinute = Integer.parseInt(beginTime.split(":")[1]);
        } 
        if (endTime.indexOf(":") > 0) {
          endHour = Integer.parseInt(endTime.split(":")[0]);
          endMinute = Integer.parseInt(endTime.split(":")[1]);
        } 
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
    } 
    workingDaySet = workDaySet;
    workBeginHour = beginHour;
    workBeginMinute = beginMinute;
    workEndHour = endHour;
    workEndMinute = endMinute;
    workingTime = (workEndHour * 3600 + workEndMinute * 60 - workBeginHour * 3600 + workBeginMinute * 60);
  }
  
  public static Date getDeadline(Date now, long deadline) {
    if ("0".equals(SystemCommon.getDeadlineUseWorkingDay()))
      return new Date(now.getTime() + deadline * 1000L); 
    if (workingTime == 0L)
      initWorkingSet(); 
    if ("0000000".equals(workingDaySet))
      return new Date(now.getTime() + deadline * 1000L); 
    Calendar deadlineStartDate = Calendar.getInstance();
    deadlineStartDate.setTime(now);
    boolean workingDayChanged = false;
    while (!currentDayIsWorkingDay(deadlineStartDate)) {
      workingDayChanged = true;
      deadlineStartDate.add(5, 1);
    } 
    if (workingDayChanged) {
      deadlineStartDate.set(11, workBeginHour);
      deadlineStartDate.set(12, workBeginMinute);
      deadlineStartDate.set(13, 0);
    } 
    if (deadline <= workingTime) {
      Calendar deadlineStartDateWorkEndTime = (Calendar)deadlineStartDate.clone();
      deadlineStartDateWorkEndTime.set(11, workEndHour);
      deadlineStartDateWorkEndTime.set(12, workEndMinute);
      deadlineStartDateWorkEndTime.set(13, 0);
      long shortTime = deadlineStartDate.getTimeInMillis() + deadline * 1000L - deadlineStartDateWorkEndTime.getTimeInMillis();
      if (shortTime <= 0L) {
        Calendar workingStartTime = Calendar.getInstance();
        workingStartTime.set(11, workBeginHour);
        workingStartTime.set(12, workBeginMinute);
        workingStartTime.set(13, 0);
        if (workingStartTime.getTimeInMillis() > now.getTime()) {
          deadlineStartDate.setTimeInMillis(workingStartTime.getTimeInMillis() + deadline * 1000L);
        } else {
          deadlineStartDate.setTimeInMillis(deadlineStartDate.getTimeInMillis() + deadline * 1000L);
        } 
      } else {
        long nowToWorkEndTime = deadlineStartDateWorkEndTime.getTimeInMillis() - now.getTime();
        if (nowToWorkEndTime < 0L)
          nowToWorkEndTime = 0L; 
        deadlineStartDateWorkEndTime.add(5, 1);
        while (!currentDayIsWorkingDay(deadlineStartDateWorkEndTime))
          deadlineStartDateWorkEndTime.add(5, 1); 
        deadlineStartDateWorkEndTime.set(11, workBeginHour);
        deadlineStartDateWorkEndTime.set(12, workBeginMinute);
        deadlineStartDateWorkEndTime.set(13, 0);
        deadlineStartDate.setTimeInMillis(deadlineStartDateWorkEndTime.getTimeInMillis() + deadline * 1000L - nowToWorkEndTime);
      } 
    } else {
      long deadlineDays = deadline / 86400L;
      if (deadlineDays != 0L)
        for (int i = 1; i <= deadlineDays; i++) {
          deadlineStartDate.add(5, 1);
          while (!currentDayIsWorkingDay(deadlineStartDate))
            deadlineStartDate.add(5, 1); 
        }  
      deadlineStartDate.setTimeInMillis(deadlineStartDate.getTimeInMillis() + deadline % 86400L * 1000L);
    } 
    return deadlineStartDate.getTime();
  }
  
  private void getWorkDaySet() {}
  
  private static boolean currentDayIsWorkingDay(Calendar curDay) {
    boolean isWorkingDay = true;
    Connection conn = null;
    Statement stmt = null;
    String databaseType = SystemCommon.getDatabaseType();
    SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd");
    String curDayStr = sformat.format(curDay.getTime());
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      int setCount = 0;
      int weekDay = curDay.get(7);
      if (workingDaySet.charAt(weekDay - 1) == '1') {
        isWorkingDay = true;
        String sql = "select count(id) from kq_holiday where type=0 and ";
        if (databaseType.indexOf("oracle") >= 0) {
          sql = String.valueOf(sql) + "(begin_date<=to_date('" + curDayStr + "','yyyy-mm-dd hh24:mi:ss') and end_date>=to_date('" + curDayStr + "','yyyy-mm-dd hh24:mi:ss'))";
        } else {
          sql = String.valueOf(sql) + "(begin_date<='" + curDayStr + "' and end_date>='" + curDayStr + "')";
        } 
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next())
          setCount = rs.getInt(1); 
        rs.close();
        if (setCount > 0)
          isWorkingDay = false; 
      } else {
        isWorkingDay = false;
        String sql = "select count(id) from kq_holiday where type=1 and ";
        if (databaseType.indexOf("oracle") >= 0) {
          sql = String.valueOf(sql) + "(begin_date<=to_date('" + curDayStr + "','yyyy-mm-dd hh24:mi:ss') and end_date>=to_date('" + curDayStr + "','yyyy-mm-dd hh24:mi:ss'))";
        } else {
          sql = String.valueOf(sql) + "(begin_date<='" + curDayStr + "' and end_date>='" + curDayStr + "')";
        } 
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next())
          setCount = rs.getInt(1); 
        rs.close();
        if (setCount > 0)
          isWorkingDay = true; 
      } 
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
      ex.printStackTrace();
    } 
    return isWorkingDay;
  }
  
  private static Calendar getNextWorkingDay(Calendar curDay, Connection conn) {
    Calendar startTime = (Calendar)curDay.clone();
    try {
      boolean isWorkingDay = true;
      Statement stmt = conn.createStatement();
      int setCount = 0;
      int weekDay = curDay.get(7);
      if (workingDaySet.charAt(weekDay - 1) == '\001') {
        isWorkingDay = true;
        String sql = "select count(id) from kq_duty_set where ";
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next())
          setCount = rs.getInt(1); 
        rs.close();
        if (setCount > 0)
          isWorkingDay = false; 
      } else {
        isWorkingDay = false;
        String sql = "select count(id) from kq_duty_set where ";
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next())
          setCount = rs.getInt(1); 
        rs.close();
        if (setCount > 0)
          isWorkingDay = true; 
      } 
      if (!isWorkingDay) {
        startTime.add(5, 1);
        while (!currentDayIsWorkingDay(startTime))
          startTime.add(5, 1); 
      } 
      stmt.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return startTime;
  }
  
  public static void main(String[] args) {
    String str = "0111110";
    System.out.println("a:" + str.charAt(1));
    System.out.println("b:" + ((str.charAt(1) == '1') ? 1 : 0));
    Calendar cal = Calendar.getInstance();
    cal.set(1, 2017);
    cal.set(2, 3);
    cal.set(5, 16);
    System.out.println("周:" + cal.getTime().toLocaleString());
    System.out.println("周:" + cal.get(7));
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd");
  }
}
