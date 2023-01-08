package com.js.oa.hr.kq.szgt.service;

import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import com.js.util.util.StringSplit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class KQCaculate {
  private static int CCSQ_TABLE_ID = 21464;
  
  private static int QJD_TABLE_ID = 21191;
  
  private static int WCDJ_TABLE_ID = 21548;
  
  private static int WDK_TABLE_ID = 21588;
  
  private static int JBD_TABLE_ID = 21640;
  
  private static int XJD_TABLE_ID = 21663;
  
  private static int caculateStatus = 0;
  
  public static int getCaculateStatus() {
    return caculateStatus;
  }
  
  private static void setCaculateStatus(int caculateStatus) {
    KQCaculate.caculateStatus = caculateStatus;
  }
  
  public static synchronized boolean executeCaculate(int year, int month, String corpId) {
    boolean result = false;
    setCaculateStatus(1);
    Connection conn = null;
    Statement stmt = null;
    String databaseType = SystemCommon.getDatabaseType();
    String reportMonth = String.valueOf(year);
    if (month < 10)
      reportMonth = String.valueOf(reportMonth) + "0"; 
    reportMonth = String.valueOf(reportMonth) + month;
    System.out.println("开始执行。。。。:" + reportMonth);
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      stmt.executeUpdate("delete from skq_report skq where report_month=" + reportMonth + " and exists (select oo.org_id from org_organization_user oou,org_organization oo where oo.org_id=oou.org_id and skq.emp_id=oou.emp_id and oo.orgidstring like '%$" + corpId + "$%')");
      List<String[]> userList = (List)new ArrayList<String>();
      String sql = "SELECT oe.emp_id,oe.empname,oo.orgIdstring,oo.org_id,oo.orgName,oe.jobstatus,oe.empfiredate,oe.intoCompanyDate,oe.zhuanzhengdate,oe.glbdef15 FROM org_employee oe,org_organization_user oou,org_organization oo WHERE oe.emp_id=oou.emp_id AND oou.org_id=oo.org_id AND NOT EXISTS (SELECT * FROM skq_report kq WHERE kq.emp_id=oe.emp_id AND kq.report_month=" + reportMonth + ") and oo.orgidstring like '%$" + corpId + "$%'";
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        String[] userTemp = new String[10];
        userTemp[0] = rs.getString(1);
        userTemp[1] = rs.getString(2);
        userTemp[2] = StringSplit.splitOrgIdString(rs.getString(3), "$", "_");
        userTemp[3] = rs.getString(4);
        userTemp[4] = rs.getString(5);
        userTemp[5] = rs.getString(6);
        userTemp[6] = rs.getString(7);
        userTemp[7] = rs.getString(8);
        userTemp[8] = rs.getString(9);
        userTemp[9] = rs.getString(10);
        userList.add(userTemp);
      } 
      rs.close();
      if (databaseType.indexOf("oracle") >= 0) {
        sql = "insert into skq_report(report_id,report_month,emp_id,empname,org_id,orgname) values(seq_skq.nextval,?,?,?,?,?)";
      } else {
        sql = "insert into skq_report(report_month,emp_id,empname,org_id,orgname) values(?,?,?,?,?)";
      } 
      PreparedStatement pstmt = conn.prepareCall(sql);
      for (String[] userTemp : userList) {
        pstmt.setString(1, reportMonth);
        pstmt.setString(2, userTemp[0]);
        pstmt.setString(3, userTemp[1]);
        pstmt.setString(4, userTemp[3]);
        pstmt.setString(5, userTemp[4]);
        pstmt.execute();
      } 
      pstmt.close();
      List<String> whiteList = getWhiteList(stmt, "");
      List<String[]> holidayList = getHolidayList(year, month, corpId, stmt);
      int dutySum = 0;
      int nosignSum = 0;
      float shouldDutySum = 0.0F;
      float signOnDutySum = 0.0F;
      float businessTrip = 0.0F;
      float overTime = 0.0F;
      float leaveInlieu = 0.0F;
      float annualLeave = 0.0F;
      float sickLeave = 0.0F;
      float workInjury = 0.0F;
      float marriageLeave = 0.0F;
      float maternityLeave = 0.0F;
      float funeralLeave = 0.0F;
      float unpaidSick = 0.0F;
      float unpaidLeave = 0.0F;
      float stopWork = 0.0F;
      float absenteeism = 0.0F;
      float annualRemain = 0.0F;
      float annualSum = 0.0F;
      float unpaidSickSum = 0.0F;
      float unpaidLeaveSum = 0.0F;
      sql = "update skq_report set duty_sum=?,nosign_sum=?,shouldduty=?,signonduty=?,businesstrip=?,overtime=?,leaveinlieu=?,annualleave=?,sickleave=?,workinjury=?,marriageleave=?,maternityleave=?,funeralleave=?,unpaidsick=?,unpaidleave=?,stopwork=?,absenteeism=?,annualremain=?,annualsum=?,unpaidsicksum=?,unpaidleavesum=? where emp_id=? and report_month=?";
      pstmt = conn.prepareStatement(sql);
      for (String[] userTemp : userList) {
        businessTrip = 0.0F;
        dutySum = 0;
        nosignSum = 0;
        shouldDutySum = 0.0F;
        signOnDutySum = 0.0F;
        businessTrip = 0.0F;
        overTime = 0.0F;
        leaveInlieu = 0.0F;
        annualLeave = 0.0F;
        sickLeave = 0.0F;
        workInjury = 0.0F;
        marriageLeave = 0.0F;
        maternityLeave = 0.0F;
        funeralLeave = 0.0F;
        unpaidSick = 0.0F;
        unpaidLeave = 0.0F;
        stopWork = 0.0F;
        absenteeism = 0.0F;
        annualRemain = 0.0F;
        annualSum = 0.0F;
        unpaidSickSum = 0.0F;
        unpaidLeaveSum = 0.0F;
        for (String[] holiday : holidayList) {
          if ("1".equals(holiday[1])) {
            annualLeave += Float.parseFloat(holiday[0]);
            continue;
          } 
          if (!"1".equals(userTemp[5]))
            annualLeave += Float.parseFloat(holiday[0]); 
        } 
        int workDaySumLegal = getWorkDayOfMonth(reportMonth);
        List<String> workDay = getWorkDay(userTemp, reportMonth, stmt);
        shouldDutySum = workDay.size();
        List<String[]> recordList = getKqRecord(userTemp[0], year, month, stmt);
        List<String[]> leaveList = getLeaveData(userTemp[0], year, month, stmt);
        for (String[] temp : leaveList) {
          if (temp[2] != null)
            annualLeave += Float.parseFloat(temp[2]); 
          if (temp[3] != null)
            unpaidLeave += Float.parseFloat(temp[3]); 
          if (temp[4] != null)
            sickLeave += Float.parseFloat(temp[4]); 
          if (temp[5] != null)
            unpaidSick += Float.parseFloat(temp[5]); 
          if (temp[6] != null)
            leaveInlieu += Float.parseFloat(temp[6]); 
          if (temp[7] != null)
            workInjury += Float.parseFloat(temp[7]); 
          if (temp[8] != null)
            marriageLeave += Float.parseFloat(temp[8]); 
          if (temp[9] != null)
            maternityLeave += Float.parseFloat(temp[9]); 
          if (temp[10] != null)
            funeralLeave += Float.parseFloat(temp[10]); 
        } 
        List<String[]> reduceList = getReduceLeaveData(userTemp[0], year, month, stmt);
        for (String[] temp : reduceList) {
          if (temp[2] != null)
            annualLeave -= Float.parseFloat(temp[2]); 
          if (temp[3] != null)
            unpaidLeave -= Float.parseFloat(temp[3]); 
          if (temp[4] != null)
            sickLeave -= Float.parseFloat(temp[4]); 
          if (temp[5] != null)
            unpaidSick -= Float.parseFloat(temp[5]); 
          if (temp[6] != null)
            leaveInlieu -= Float.parseFloat(temp[6]); 
          if (temp[7] != null)
            workInjury -= Float.parseFloat(temp[7]); 
          if (temp[8] != null)
            marriageLeave -= Float.parseFloat(temp[8]); 
          if (temp[9] != null)
            maternityLeave -= Float.parseFloat(temp[9]); 
          if (temp[10] != null)
            funeralLeave -= Float.parseFloat(temp[10]); 
        } 
        reduceList.clear();
        leaveInlieu *= 8.0F;
        annualLeave *= 8.0F;
        sickLeave *= 8.0F;
        workInjury *= 8.0F;
        marriageLeave *= 8.0F;
        maternityLeave *= 8.0F;
        funeralLeave *= 8.0F;
        unpaidSick *= 8.0F;
        unpaidLeave *= 8.0F;
        List<String[]> noSignList = getNoSignData(userTemp[0], year, month, stmt, leaveList);
        nosignSum = noSignList.size();
        List<String[]> outList = getGooutData(userTemp[0], year, month, stmt, leaveList, noSignList);
        List<String[]> tripList = getBusinessTrip(userTemp[0], year, month, stmt, leaveList, noSignList, outList);
        for (String[] temp : tripList) {
          if (temp[2] != null)
            businessTrip += Float.parseFloat(temp[2]); 
        } 
        List<String[]> overtimeList = getOvertimeSum(userTemp[0], year, month, stmt);
        for (String[] temp : overtimeList) {
          if (temp[2] != null)
            overTime += Float.parseFloat(temp[2]); 
        } 
        boolean noSign = true;
        boolean onDutyOK = false;
        boolean offDutyOK = false;
        if (whiteList.contains(userTemp[0])) {
          noSign = false;
          dutySum = 0;
          nosignSum = 0;
          signOnDutySum = shouldDutySum;
          absenteeism = 0.0F;
        } else {
          for (String currentDay : workDay) {
            noSign = true;
            onDutyOK = false;
            offDutyOK = false;
            for (String[] recordTemp : recordList) {
              if (currentDay.equals(recordTemp[0])) {
                noSign = false;
                if (recordTemp[3] == null || "".equals(recordTemp[3])) {
                  onDutyOK = false;
                  for (String[] noSignTemp : noSignList) {
                    if (recordTemp[0].equals(noSignTemp[0]) && 
                      "1".equals(noSignTemp[1])) {
                      onDutyOK = true;
                      break;
                    } 
                  } 
                  if (!onDutyOK)
                    for (String[] outTemp : outList) {
                      if (timeWithinTheRange(String.valueOf(recordTemp[0]) + " " + recordTemp[1] + ":59", outTemp[0], outTemp[1])) {
                        onDutyOK = true;
                        break;
                      } 
                    }  
                  if (!onDutyOK)
                    for (String[] tripTemp : tripList) {
                      if (timeWithinTheRange(String.valueOf(recordTemp[0]) + " " + recordTemp[1] + ":59", tripTemp[0], tripTemp[1])) {
                        onDutyOK = true;
                        break;
                      } 
                    }  
                  if (!onDutyOK)
                    for (String[] leaveTemp : leaveList) {
                      if (timeWithinTheRange(String.valueOf(recordTemp[0]) + " " + recordTemp[1] + ":59", leaveTemp[0], leaveTemp[1])) {
                        onDutyOK = true;
                        break;
                      } 
                    }  
                  if (!onDutyOK)
                    absenteeism += 8.0F; 
                } else {
                  if (recordTemp[5] != null) {
                    onDutyOK = false;
                    for (String[] noSignTemp : noSignList) {
                      if (recordTemp[0].equals(noSignTemp[0]) && 
                        "1".equals(noSignTemp[1])) {
                        onDutyOK = true;
                        break;
                      } 
                    } 
                    if (!onDutyOK)
                      for (String[] outTemp : outList) {
                        if (timeWithinTheRange(String.valueOf(recordTemp[0]) + " " + recordTemp[1] + ":59", outTemp[0], outTemp[1])) {
                          onDutyOK = true;
                          break;
                        } 
                      }  
                    if (!onDutyOK)
                      for (String[] tripTemp : tripList) {
                        if (timeWithinTheRange(String.valueOf(recordTemp[0]) + " " + recordTemp[1] + ":59", tripTemp[0], tripTemp[1])) {
                          onDutyOK = true;
                          break;
                        } 
                      }  
                    if (!onDutyOK)
                      for (String[] leaveTemp : leaveList) {
                        if (timeWithinTheRange(String.valueOf(recordTemp[0]) + " " + recordTemp[1] + ":59", leaveTemp[0], leaveTemp[1])) {
                          onDutyOK = true;
                          break;
                        } 
                      }  
                    if (!onDutyOK) {
                      float h = caclateHours(recordTemp[5]);
                      if (h > 0.5D) {
                        absenteeism += h;
                      } else {
                        dutySum++;
                      } 
                    } 
                  } 
                  onDutyOK = true;
                } 
                if (onDutyOK)
                  if (recordTemp[4] == null || "".equals(recordTemp[4])) {
                    offDutyOK = false;
                    if (!offDutyOK)
                      for (String[] leaveTemp : leaveList) {
                        if (timeWithinTheRange(String.valueOf(recordTemp[0]) + " " + recordTemp[2] + ":00", leaveTemp[0], leaveTemp[1])) {
                          offDutyOK = true;
                          break;
                        } 
                      }  
                    if (!offDutyOK)
                      for (String[] noSignTemp : noSignList) {
                        if (recordTemp[0].equals(noSignTemp[0]) && 
                          "2".equals(noSignTemp[1])) {
                          offDutyOK = true;
                          break;
                        } 
                      }  
                    if (!offDutyOK)
                      for (String[] outTemp : outList) {
                        if (timeWithinTheRange(String.valueOf(recordTemp[0]) + " " + recordTemp[2] + ":00", outTemp[0], outTemp[1])) {
                          offDutyOK = true;
                          break;
                        } 
                      }  
                    if (!offDutyOK)
                      for (String[] tripTemp : tripList) {
                        if (timeWithinTheRange(String.valueOf(recordTemp[0]) + " " + recordTemp[2] + ":00", tripTemp[0], tripTemp[1])) {
                          offDutyOK = true;
                          break;
                        } 
                      }  
                    if (!offDutyOK)
                      absenteeism += 8.0F; 
                  } else if (recordTemp[6] != null) {
                    offDutyOK = false;
                    if (!offDutyOK)
                      for (String[] leaveTemp : leaveList) {
                        if (timeWithinTheRange(String.valueOf(recordTemp[0]) + " " + recordTemp[2] + ":00", leaveTemp[0], leaveTemp[1])) {
                          offDutyOK = true;
                          break;
                        } 
                      }  
                    if (!offDutyOK)
                      for (String[] noSignTemp : noSignList) {
                        if (recordTemp[0].equals(noSignTemp[0]) && 
                          "2".equals(noSignTemp[1])) {
                          offDutyOK = true;
                          break;
                        } 
                      }  
                    if (!offDutyOK)
                      for (String[] outTemp : outList) {
                        if (timeWithinTheRange(String.valueOf(recordTemp[0]) + " " + recordTemp[2] + ":00", outTemp[0], outTemp[1])) {
                          offDutyOK = true;
                          break;
                        } 
                      }  
                    if (!offDutyOK)
                      for (String[] tripTemp : tripList) {
                        if (timeWithinTheRange(String.valueOf(recordTemp[0]) + " " + recordTemp[2] + ":00", tripTemp[0], tripTemp[1])) {
                          offDutyOK = true;
                          break;
                        } 
                      }  
                    if (!offDutyOK) {
                      float h = caclateHours(recordTemp[6]);
                      absenteeism += h;
                    } 
                  }  
                if (noSign)
                  absenteeism += 8.0F; 
              } 
            } 
          } 
        } 
        List<String[]> unpaidSickLeaves = getUnpaidSickLeave(userTemp[0], year, month, stmt);
        for (String[] unpaidSickLeave : unpaidSickLeaves) {
          if (unpaidSickLeave[2] != null)
            unpaidLeaveSum += Float.parseFloat(unpaidSickLeave[2]); 
          if (unpaidSickLeave[3] != null)
            unpaidSickSum += Float.parseFloat(unpaidSickLeave[3]); 
        } 
        List<String[]> reduceUnpaidSickLeaves = getReduceUnpaidSickLeave(userTemp[0], year, month, stmt);
        for (String[] unpaidSickLeave : reduceUnpaidSickLeaves) {
          if (unpaidSickLeave[2] != null)
            unpaidLeaveSum -= Float.parseFloat(unpaidSickLeave[2]); 
          if (unpaidSickLeave[3] != null)
            unpaidSickSum -= Float.parseFloat(unpaidSickLeave[3]); 
        } 
        unpaidLeaveSum *= 8.0F;
        unpaidSickSum *= 8.0F;
        float[] annualDataSum = getPeriodAnnualSum(userTemp[0], userTemp[5], userTemp[6], userTemp[7], userTemp[8], userTemp[9], corpId, year, month, stmt);
        annualSum = annualDataSum[0] * 8.0F;
        annualRemain = annualDataSum[1] * 8.0F;
        workDay.clear();
        recordList.clear();
        tripList.clear();
        leaveList.clear();
        reduceList.clear();
        overtimeList.clear();
        outList.clear();
        noSignList.clear();
        unpaidSickLeaves.clear();
        reduceUnpaidSickLeaves.clear();
        signOnDutySum = shouldDutySum - (leaveInlieu + annualLeave + sickLeave + workInjury + marriageLeave + maternityLeave + funeralLeave + unpaidSick + unpaidLeave + absenteeism) / 8.0F - businessTrip;
        if (signOnDutySum < 0.0F)
          signOnDutySum = 0.0F; 
        pstmt.setInt(1, dutySum);
        pstmt.setInt(2, nosignSum);
        pstmt.setFloat(3, workDaySumLegal);
        pstmt.setFloat(4, signOnDutySum);
        pstmt.setFloat(5, businessTrip);
        pstmt.setFloat(6, overTime);
        pstmt.setFloat(7, leaveInlieu);
        pstmt.setFloat(8, annualLeave);
        pstmt.setFloat(9, sickLeave);
        pstmt.setFloat(10, workInjury);
        pstmt.setFloat(11, marriageLeave);
        pstmt.setFloat(12, maternityLeave);
        pstmt.setFloat(13, funeralLeave);
        pstmt.setFloat(14, unpaidSick);
        pstmt.setFloat(15, unpaidLeave);
        pstmt.setFloat(16, stopWork);
        pstmt.setFloat(17, absenteeism);
        pstmt.setFloat(18, annualRemain);
        pstmt.setFloat(19, annualSum);
        pstmt.setFloat(20, unpaidSickSum);
        pstmt.setFloat(21, unpaidLeaveSum);
        pstmt.setString(22, userTemp[0]);
        pstmt.setString(23, reportMonth);
        pstmt.execute();
      } 
      pstmt.close();
      stmt.close();
      conn.close();
      whiteList.clear();
      holidayList.clear();
      userList.clear();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    setCaculateStatus(2);
    System.out.println("执行完毕。。。");
    return result;
  }
  
  private static String getLastMonth() {
    String lastMonth;
    Date currentDate = new Date();
    int year = currentDate.getYear() + 1900;
    int month = currentDate.getMonth();
    if (month == 0) {
      lastMonth = String.valueOf(String.valueOf(year - 1)) + String.valueOf(12);
    } else {
      lastMonth = String.valueOf(year);
      if (month < 10)
        lastMonth = String.valueOf(lastMonth) + "0"; 
      lastMonth = String.valueOf(lastMonth) + String.valueOf(month);
    } 
    return lastMonth;
  }
  
  private static List<String[]> getHolidayList(int year, int month, String corpId, Statement stmt) {
    List<String[]> holidayList = (List)new ArrayList<String>();
    String databaseType = SystemCommon.getDatabaseType();
    try {
      String sql;
      Calendar calendar = Calendar.getInstance();
      calendar.set(1, year);
      calendar.set(2, month - 1);
      calendar.set(5, 1);
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
      String firstDay = format.format(calendar.getTime());
      Calendar nextMonth = (Calendar)calendar.clone();
      nextMonth.add(2, 1);
      String nextMonthFirstDay = format.format(nextMonth.getTime());
      if (databaseType.indexOf("oracle") >= 0) {
        sql = "SELECT id,begin_date,end_date,reducetrial FROM kq_holiday where begin_date>=to_date('" + firstDay + " 00:00:00','yyyy-MM-dd HH24:MI:SS') AND end_date<to_date('" + nextMonthFirstDay + " 00:00:00','yyyy-MM-dd HH24:MI:SS') and type=0 and reduceannual=1 and corp_id=" + corpId;
      } else {
        sql = "SELECT id,begin_date,end_date,reducetrial FROM kq_holiday where begin_date>='" + firstDay + " 00:00:00' AND end_date<'" + nextMonthFirstDay + " 00:00:00' and type=0 and reduceannual=1 and corp_id=" + corpId;
      } 
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        String[] holiday = new String[2];
        holiday[0] = String.valueOf(getHolidayDays(rs.getString(2), rs.getString(3)));
        holiday[1] = rs.getString(4);
        holidayList.add(holiday);
      } 
      rs.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return holidayList;
  }
  
  private static long getHolidayDays(String beginDate, String endDate) {
    long days = 1L;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    try {
      Date begin = format.parse(beginDate);
      Date end = format.parse(endDate);
      days = (end.getTime() - begin.getTime()) / 86400000L + 1L;
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return days;
  }
  
  private static List<String> getWorkDay(String[] empPara, String curMonth, Statement stmt) {
    List<String> workDay = new ArrayList<String>();
    String databaseType = SystemCommon.getDatabaseType();
    try {
      String sql = "select workday from kq_duty_set ";
      String where = "";
      String[] userOrgArr = empPara[2].substring(1, empPara[2].length() - 1).split("$$");
      if (userOrgArr.length > 0) {
        where = " where (";
        for (int i = 0; i < userOrgArr.length; i++) {
          if (i == 0) {
            where = String.valueOf(where) + " org_ids like '%$" + userOrgArr[i] + "$%' ";
          } else {
            where = String.valueOf(where) + " or org_ids like '%$" + userOrgArr[i] + "$%' ";
          } 
        } 
        where = String.valueOf(where) + ")";
      } 
      sql = String.valueOf(sql) + where;
      String workDaySet = "0111110";
      ResultSet rs = stmt.executeQuery(sql);
      if (rs.next())
        workDaySet = rs.getString(1); 
      rs.close();
      char[] workWeek = workDaySet.toCharArray();
      int year = Integer.parseInt(curMonth.substring(0, 4));
      int month = Integer.parseInt(curMonth.substring(4));
      Calendar calendar = Calendar.getInstance();
      calendar.set(1, year);
      calendar.set(2, month - 1);
      calendar.set(5, 1);
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
      String firstDay = format.format(calendar.getTime());
      Calendar nextMonth = (Calendar)calendar.clone();
      nextMonth.add(2, 1);
      String nextMonthFirstDay = format.format(nextMonth.getTime());
      List<Calendar[]> shouldWork = (List)new ArrayList<Calendar>();
      List<Calendar[]> shouldHoliday = (List)new ArrayList<Calendar>();
      if (databaseType.indexOf("oracle") >= 0) {
        sql = "SELECT id,TYPE,begin_date,end_date FROM kq_holiday where begin_date>=to_date('" + firstDay + " 00:00:00','yyyy-MM-dd HH24:MI:SS') AND end_date<to_date('" + nextMonthFirstDay + " 00:00:00','yyyy-MM-dd HH24:MI:SS')";
      } else {
        sql = "SELECT id,TYPE,begin_date,end_date FROM kq_holiday where begin_date>='" + firstDay + " 00:00:00' AND end_date<'" + nextMonthFirstDay + " 00:00:00'";
      } 
      rs = stmt.executeQuery(sql);
      while (rs.next()) {
        Calendar[] dateTemp = new Calendar[2];
        Calendar beginCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        String holidayType = rs.getString(2);
        beginCal.setTime(rs.getDate(3));
        endCal.setTime(rs.getDate(4));
        dateTemp[0] = beginCal;
        dateTemp[1] = endCal;
        if ("0".equals(holidayType)) {
          shouldHoliday.add(dateTemp);
          continue;
        } 
        shouldWork.add(dateTemp);
      } 
      rs.close();
      while (calendar.get(1) == year && calendar.get(2) < month) {
        int day = calendar.get(7);
        if ("1".equals(String.valueOf(workWeek[day - 1]))) {
          boolean isHoliday = false;
          for (Calendar[] dateTemp : shouldHoliday) {
            if (isDateInteval(calendar, dateTemp[0], dateTemp[1], year, month)) {
              isHoliday = true;
              break;
            } 
          } 
          if (!isHoliday)
            workDay.add(format.format(calendar.getTime())); 
        } else {
          boolean isWork = false;
          for (Calendar[] dateTemp : shouldWork) {
            if (isDateInteval(calendar, dateTemp[0], dateTemp[1], year, month)) {
              isWork = true;
              break;
            } 
          } 
          if (isWork)
            workDay.add(format.format(calendar.getTime())); 
        } 
        calendar.add(5, 1);
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return workDay;
  }
  
  private static int getWorkDayOfMonth(String curMonth) {
    int workDaySum = 0;
    try {
      int year = Integer.parseInt(curMonth.substring(0, 4));
      int month = Integer.parseInt(curMonth.substring(4));
      Calendar calendar = Calendar.getInstance();
      calendar.set(1, year);
      calendar.set(2, month - 1);
      calendar.set(5, 1);
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
      String firstDay = format.format(calendar.getTime());
      Calendar nextMonth = (Calendar)calendar.clone();
      nextMonth.add(2, 1);
      String nextMonthFirstDay = format.format(nextMonth.getTime());
      List<Calendar[]> shouldWork = (List)new ArrayList<Calendar>();
      List<Calendar[]> shouldHoliday = (List)new ArrayList<Calendar>();
      while (calendar.get(1) == year && calendar.get(2) < month) {
        int day = calendar.get(7);
        if (day != 1 && day != 7)
          workDaySum++; 
        calendar.add(5, 1);
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return workDaySum;
  }
  
  private static boolean isDateInteval(Calendar calendar, Calendar beginDate, Calendar endDate, int year, int month) {
    boolean result = false;
    Calendar endCal = (Calendar)endDate.clone();
    endCal.set(10, 23);
    endCal.set(12, 59);
    endCal.set(13, 59);
    if (calendar.after(beginDate) && calendar.before(endCal))
      result = true; 
    if (calendar.equals(beginDate) && calendar.equals(endDate))
      return true; 
    return result;
  }
  
  private static List<String> getWhiteList(Statement stmt, String corpId) {
    List<String> whiteList = new ArrayList<String>();
    String databaseType = SystemCommon.getDatabaseType();
    try {
      String listuserId = "";
      String sql = "select listuserId from skq_whitelist";
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next())
        listuserId = String.valueOf(listuserId) + rs.getString(1); 
      rs.close();
      sql = "select oe.emp_id from org_employee oe,org_organization_user oou,org_organization oo where oe.emp_id=oou.emp_id and oou.org_id=oo.org_id  and (";
      if (databaseType.indexOf("oracle") >= 0) {
        sql = String.valueOf(sql) + "'" + listuserId + "' like '%$'||oe.emp_id||'$%'";
      } else if (databaseType.indexOf("mysql") >= 0) {
        sql = String.valueOf(sql) + "'" + listuserId + "' like concat('%$',oe.emp_id,'$%')";
      } 
      sql = String.valueOf(sql) + ")";
      rs = stmt.executeQuery(sql);
      while (rs.next())
        whiteList.add(rs.getString(1)); 
      rs.close();
    } catch (Exception err) {
      err.printStackTrace();
    } 
    return whiteList;
  }
  
  private static List<String[]> getLeaveData(String empId, int year, int month, Statement stmt) {
    List<String[]> list = (List)new ArrayList<String>();
    String firstDate = String.valueOf(getFirstDateOfMonth(year, month)) + " 23:59:59";
    String firstDateNextMonth = String.valueOf(getFirstDateNextMonth(year, month)) + " 00:00:00";
    try {
      String sql = "select qj_kssj,qj_jssj,qj_nj,qj_sj,qj_bjdx,qj_bjbdx,qj_dx,qj_gs,qj_hj,qj_cj,qj_sangj,qj_qjss from skq_qjd qjd where skq_qjd_owner='" + empId + "' and exists (";
      sql = String.valueOf(sql) + "select wf_work_id from jsf_work jw where jw.workstatus=100 and jw.worktable_id=" + QJD_TABLE_ID + " and jw.workrecord_id=qjd.skq_qjd_id";
      sql = String.valueOf(sql) + ")";
      sql = String.valueOf(sql) + " and (qj_kssj>'" + firstDate + "' and qj_jssj<'" + firstDateNextMonth + "')";
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        String[] temp = new String[12];
        temp[0] = rs.getString(1);
        temp[1] = rs.getString(2);
        temp[2] = rs.getString(3);
        temp[3] = rs.getString(4);
        temp[4] = rs.getString(5);
        temp[5] = rs.getString(6);
        temp[6] = rs.getString(7);
        temp[7] = rs.getString(8);
        temp[8] = rs.getString(9);
        temp[9] = rs.getString(10);
        temp[10] = rs.getString(11);
        temp[11] = rs.getString(12);
        list.add(temp);
      } 
      rs.close();
    } catch (Exception err) {
      err.printStackTrace();
    } 
    return list;
  }
  
  private static List<String[]> getUnpaidSickLeave(String empId, int year, int month, Statement stmt) {
    List<String[]> list = (List)new ArrayList<String>();
    String firstDate = String.valueOf(year) + "-01-01 00:00:00";
    String endDate = String.valueOf(year) + "-12-31 23:59:59";
    try {
      String sql = "select qj_kssj,qj_jssj,qj_sj,qj_bjbdx from skq_qjd qjd where skq_qjd_owner='" + empId + "' and exists (";
      sql = String.valueOf(sql) + "select wf_work_id from jsf_work jw where jw.workstatus=100 and jw.worktable_id=" + QJD_TABLE_ID + " and jw.workrecord_id=qjd.skq_qjd_id";
      sql = String.valueOf(sql) + ")";
      sql = String.valueOf(sql) + " and (qj_kssj>'" + firstDate + "' and qj_jssj<'" + endDate + "')";
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        String[] temp = new String[4];
        temp[0] = rs.getString(1);
        temp[1] = rs.getString(2);
        temp[2] = rs.getString(3);
        temp[3] = rs.getString(4);
        list.add(temp);
      } 
      rs.close();
    } catch (Exception err) {
      err.printStackTrace();
    } 
    return list;
  }
  
  private static List<String[]> getReduceUnpaidSickLeave(String empId, int year, int month, Statement stmt) {
    List<String[]> list = (List)new ArrayList<String>();
    String firstDate = String.valueOf(year) + "-01-01 00:00:00";
    String endDate = String.valueOf(year) + "-12-31 23:59:59";
    try {
      String sql = "select xj_kssj,xj_jssj,xj_sj,xj_bjbdx from skq_xjd xjd where skq_xjd_owner='" + empId + "' and exists (";
      sql = String.valueOf(sql) + "select wf_work_id from jsf_work jw where jw.workstatus=100 and jw.worktable_id=" + XJD_TABLE_ID + " and jw.workrecord_id=xjd.skq_xjd_id";
      sql = String.valueOf(sql) + ")";
      sql = String.valueOf(sql) + " and (xj_kssj>'" + firstDate + "' and xj_jssj<'" + endDate + "')";
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        String[] temp = new String[4];
        temp[0] = rs.getString(1);
        temp[1] = rs.getString(2);
        temp[2] = rs.getString(3);
        temp[3] = rs.getString(4);
        list.add(temp);
      } 
      rs.close();
    } catch (Exception err) {
      err.printStackTrace();
    } 
    return list;
  }
  
  private static List<String[]> getReduceLeaveData(String empId, int year, int month, Statement stmt) {
    List<String[]> list = (List)new ArrayList<String>();
    String firstDate = String.valueOf(getFirstDateOfMonth(year, month)) + " 23:59:59";
    String firstDateNextMonth = String.valueOf(getFirstDateNextMonth(year, month)) + " 00:00:00";
    try {
      String sql = "select xj_kssj,xj_jssj,xj_nj,xj_sj,xj_bjdx,xj_bjbdx,xj_dx,xj_gs,xj_hj,xj_cj,xj_sangj,xj_xjss from skq_xjd xjd where skq_xjd_owner='" + empId + "' and exists (";
      sql = String.valueOf(sql) + "select wf_work_id from jsf_work jw where jw.workstatus=100 and jw.worktable_id=" + XJD_TABLE_ID + " and jw.workrecord_id=xjd.skq_xjd_id";
      sql = String.valueOf(sql) + ")";
      sql = String.valueOf(sql) + " and (xj_kssj>'" + firstDate + "' and xj_jssj<'" + firstDateNextMonth + "')";
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        String[] temp = new String[12];
        temp[0] = rs.getString(1);
        temp[1] = rs.getString(2);
        temp[2] = rs.getString(3);
        temp[3] = rs.getString(4);
        temp[4] = rs.getString(5);
        temp[5] = rs.getString(6);
        temp[6] = rs.getString(7);
        temp[7] = rs.getString(8);
        temp[8] = rs.getString(9);
        temp[9] = rs.getString(10);
        temp[10] = rs.getString(11);
        temp[11] = rs.getString(12);
        list.add(temp);
      } 
      rs.close();
    } catch (Exception err) {
      err.printStackTrace();
    } 
    return list;
  }
  
  private static List<String[]> getNoSignData(String empId, int year, int month, Statement stmt, List<String[]> leaveList) {
    List<String[]> list = (List)new ArrayList<String>();
    String firstDate = String.valueOf(getFirstDateOfMonth(year, month)) + " 23:59:59";
    String firstDateNextMonth = String.valueOf(getFirstDateNextMonth(year, month)) + " 00:00:00";
    try {
      String sql = "select wdk_wdkrq,wdk_wdklx from skq_wdksm wdk where skq_wdksm_owner='" + empId + "' and exists (";
      sql = String.valueOf(sql) + "select wf_work_id from jsf_work jw where jw.workstatus=100 and jw.worktable_id=" + WDK_TABLE_ID + " and jw.workrecord_id=wdk.skq_wdksm_id";
      sql = String.valueOf(sql) + ")";
      sql = String.valueOf(sql) + " and (wdk_wdkrq>'" + firstDate + "' and wdk_wdkrq<'" + firstDateNextMonth + "')";
      System.out.println("wdksql:" + sql);
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        boolean norecord = false;
        String wdkrq = rs.getString(1);
        String wdklx = rs.getString(2);
        if ("1".equals(wdklx)) {
          String tmpDate = String.valueOf(wdkrq) + " 09:01:00";
          for (String[] leaveTemp : leaveList) {
            if (dateInRange(tmpDate, leaveTemp[0], leaveTemp[1])) {
              norecord = true;
              break;
            } 
          } 
        } else if ("2".equals(wdklx)) {
          String tmpDate = String.valueOf(wdkrq) + " 17:00:00";
          for (String[] leaveTemp : leaveList) {
            if (dateInRange(tmpDate, leaveTemp[0], leaveTemp[1])) {
              norecord = true;
              break;
            } 
          } 
        } 
        if (!norecord) {
          String[] temp = new String[2];
          temp[0] = wdkrq;
          temp[1] = wdklx;
          list.add(temp);
        } 
      } 
      rs.close();
    } catch (Exception err) {
      err.printStackTrace();
    } 
    return list;
  }
  
  private static List<String[]> getGooutData(String empId, int year, int month, Statement stmt, List<String[]> leaveList, List<String[]> noSignList) {
    List<String[]> list = (List)new ArrayList<String>();
    String firstDate = String.valueOf(getFirstDateOfMonth(year, month)) + " 23:59:59";
    String firstDateNextMonth = String.valueOf(getFirstDateNextMonth(year, month)) + " 00:00:00";
    try {
      String sql = "select wc_kssj,wc_jssj from skq_wcdjd wcd where skq_wcdjd_owner='" + empId + "' and exists (";
      sql = String.valueOf(sql) + "select wf_work_id from jsf_work jw where jw.workstatus=100 and jw.worktable_id=" + WCDJ_TABLE_ID + " and jw.workrecord_id=wcd.skq_wcdjd_id";
      sql = String.valueOf(sql) + ")";
      sql = String.valueOf(sql) + " and (wc_kssj>'" + firstDate + "' and wc_jssj<'" + firstDateNextMonth + "')";
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        boolean norecord = false;
        String kssj = rs.getString(1);
        String jssj = rs.getString(2);
        for (String[] leaveTemp : leaveList) {
          if (dateInRange(kssj, leaveTemp[0], leaveTemp[1]) || dateInRange(jssj, leaveTemp[0], leaveTemp[1])) {
            norecord = true;
            break;
          } 
        } 
        if (!norecord)
          for (String[] noSignTemp : noSignList) {
            boolean eqResult = false;
            if ("1".equals(noSignTemp[1])) {
              eqResult = dateInRange(kssj, String.valueOf(noSignTemp[0]) + " 00:00:00", String.valueOf(noSignTemp[0]) + " 12:00:00");
            } else {
              eqResult = dateInRange(kssj, String.valueOf(noSignTemp[0]) + " 12:00:00", String.valueOf(noSignTemp[0]) + " 23:59:59");
            } 
            if (eqResult) {
              norecord = true;
              break;
            } 
          }  
        if (!norecord) {
          String[] temp = new String[2];
          temp[0] = kssj;
          temp[1] = jssj;
          list.add(temp);
        } 
      } 
      rs.close();
    } catch (Exception err) {
      err.printStackTrace();
    } 
    return list;
  }
  
  private static List<String[]> getBusinessTrip(String empId, int year, int month, Statement stmt, List<String[]> leaveList, List<String[]> noSignList, List<String[]> outList) {
    List<String[]> list = (List)new ArrayList<String>();
    String firstDate = String.valueOf(getFirstDateOfMonth(year, month)) + " 23:59:59";
    String firstDateNextMonth = String.valueOf(getFirstDateNextMonth(year, month)) + " 00:00:00";
    try {
      String sql = "select cc_kssj,cc_jssj,cc_ccts from skq_ccsqd ccsq where skq_ccsqd_owner='" + empId + "' and exists (";
      sql = String.valueOf(sql) + "select wf_work_id from jsf_work jw where jw.workstatus=100 and jw.worktable_id=" + CCSQ_TABLE_ID + " and jw.workrecord_id=ccsq.skq_ccsqd_id";
      sql = String.valueOf(sql) + ")";
      sql = String.valueOf(sql) + " and (cc_kssj>'" + firstDate + "' and cc_jssj<'" + firstDateNextMonth + "')";
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        boolean norecord = false;
        String kssj = rs.getString(1);
        String jssj = rs.getString(2);
        for (String[] leaveTemp : leaveList) {
          if (dateInRange(leaveTemp[0], kssj, jssj) || dateInRange(leaveTemp[1], kssj, jssj)) {
            norecord = true;
            break;
          } 
        } 
        if (!norecord)
          for (String[] noSignTemp : noSignList) {
            if (dateInRange(String.valueOf(noSignTemp[0]) + " 09:00:00", kssj, jssj)) {
              norecord = true;
              break;
            } 
          }  
        if (!norecord) {
          String[] temp = new String[3];
          temp[0] = kssj;
          temp[1] = jssj;
          temp[2] = rs.getString(3);
          list.add(temp);
        } 
      } 
      rs.close();
    } catch (Exception err) {
      err.printStackTrace();
    } 
    return list;
  }
  
  private static List<String[]> getOvertimeSum(String empId, int year, int month, Statement stmt) {
    List<String[]> list = (List)new ArrayList<String>();
    String firstDate = String.valueOf(getFirstDateOfMonth(year, month)) + " 23:59:59";
    String firstDateNextMonth = String.valueOf(getFirstDateNextMonth(year, month)) + " 00:00:00";
    try {
      String sql = "select jb_kssj,jb_jssj,jb_jbss from skq_jbd jbd where skq_jbd_owner='" + empId + "' and exists (";
      sql = String.valueOf(sql) + "select wf_work_id from jsf_work jw where jw.workstatus=100 and jw.worktable_id=" + JBD_TABLE_ID + " and jw.workrecord_id=jbd.skq_jbd_id";
      sql = String.valueOf(sql) + ")";
      sql = String.valueOf(sql) + " and (jb_kssj>'" + firstDate + "' and jb_jssj<'" + firstDateNextMonth + "')";
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        String[] temp = new String[3];
        temp[0] = rs.getString(1);
        temp[1] = rs.getString(2);
        temp[2] = rs.getString(3);
        list.add(temp);
      } 
      rs.close();
    } catch (Exception err) {
      err.printStackTrace();
    } 
    return list;
  }
  
  private static float[] getPeriodAnnualSum(String empId, String jobStatus, String fireDate, String dutyDateStr, String zhuanzhengDateStr, String gongling, String corpId, int year, int month, Statement stmt) {
    float[] annualData = { 0.0F, 0.0F };
    float sum = 0.0F;
    int canApply = 0;
    if (dutyDateStr == null)
      return annualData; 
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    try {
      Calendar thisMonthEndDay = Calendar.getInstance();
      thisMonthEndDay.set(1, year);
      thisMonthEndDay.set(2, month - 1);
      thisMonthEndDay.set(5, 1);
      thisMonthEndDay.add(2, 1);
      thisMonthEndDay.add(5, -1);
      int standardAnnual = 0;
      if (dutyDateStr.indexOf(" ") > 0)
        dutyDateStr = dutyDateStr.substring(0, dutyDateStr.indexOf(" ")); 
      Date dutyDate = format.parse(dutyDateStr);
      Calendar calEmpFireDate = Calendar.getInstance();
      calEmpFireDate.setTime(dutyDate);
      calEmpFireDate.add(2, -Integer.parseInt(gongling));
      Date empFireDate = calEmpFireDate.getTime();
      Calendar fireDate1 = Calendar.getInstance();
      fireDate1.setTime(empFireDate);
      fireDate1.add(1, 1);
      if (fireDate1.before(thisMonthEndDay))
        standardAnnual = 5; 
      fireDate1.add(1, 9);
      if (fireDate1.before(thisMonthEndDay))
        standardAnnual = 10; 
      fireDate1.add(1, 10);
      if (fireDate1.before(thisMonthEndDay))
        standardAnnual = 15; 
      String annualPeriod = "0";
      int workDutyYear = 1900 + dutyDate.getYear();
      String[] period = new String[3];
      boolean workFullYear = false;
      int workOneYearInt = year;
      Calendar empFireCal = Calendar.getInstance();
      empFireCal.setTime(empFireDate);
      empFireCal.add(1, 1);
      Date workOneYearDate = empFireCal.getTime();
      workOneYearInt = empFireCal.get(1);
      if (empFireCal.getTime().before(new Date()))
        workFullYear = true; 
      boolean dutyWorkFullYear = false;
      if (workOneYearDate.before(dutyDate))
        dutyWorkFullYear = true; 
      ResultSet rs = stmt.executeQuery("select annualperiod from skq_para where corp_id=" + corpId);
      if (rs.next())
        annualPeriod = rs.getString(1); 
      rs.close();
      if ("0".equals(annualPeriod)) {
        period[0] = String.valueOf(year) + "-01-01";
        period[1] = String.valueOf(year) + "-12-31";
        if (dutyWorkFullYear) {
          if (workDutyYear < year) {
            canApply = standardAnnual;
          } else {
            canApply = (int)((format.parse(period[1]).getTime() - dutyDate.getTime()) / 86400000L * standardAnnual) / 365;
          } 
        } else if (workFullYear) {
          if (workOneYearInt < year) {
            canApply = standardAnnual;
          } else {
            canApply = (int)((format.parse(period[1]).getTime() - workOneYearDate.getTime()) / 86400000L * standardAnnual) / 365;
          } 
        } else {
          canApply = 0;
        } 
      } else if ("1".equals(annualPeriod)) {
        Calendar currentAnnualBegin = Calendar.getInstance();
        if (dutyWorkFullYear) {
          currentAnnualBegin.set(1, year);
          currentAnnualBegin.set(2, dutyDate.getMonth());
          currentAnnualBegin.set(5, dutyDate.getDate());
          if (currentAnnualBegin.after(thisMonthEndDay)) {
            period[1] = format.format(currentAnnualBegin.getTime());
            currentAnnualBegin.add(1, -1);
            period[0] = format.format(currentAnnualBegin.getTime());
          } else {
            period[0] = format.format(currentAnnualBegin.getTime());
            currentAnnualBegin.add(1, 1);
            period[1] = format.format(currentAnnualBegin.getTime());
          } 
          canApply = standardAnnual;
        } else if (workFullYear) {
          currentAnnualBegin.set(1, year);
          currentAnnualBegin.set(2, dutyDate.getMonth());
          currentAnnualBegin.set(5, dutyDate.getDate());
          if (currentAnnualBegin.after(thisMonthEndDay)) {
            period[1] = format.format(currentAnnualBegin.getTime());
            currentAnnualBegin.add(1, -1);
            period[0] = format.format(currentAnnualBegin.getTime());
            if (currentAnnualBegin.getTime().after(dutyDate)) {
              canApply = standardAnnual;
            } else {
              Date dateEnd = thisMonthEndDay.getTime();
              canApply = (int)((dateEnd.getTime() - workOneYearDate.getTime()) / 86400000L * standardAnnual) / 365;
            } 
          } else {
            currentAnnualBegin.add(1, -1);
            if (currentAnnualBegin.getTime().before(dutyDate)) {
              Date dateEnd = thisMonthEndDay.getTime();
              canApply = (int)((dateEnd.getTime() - workOneYearDate.getTime()) / 86400000L * standardAnnual) / 365;
            } else {
              canApply = standardAnnual;
            } 
          } 
        } else {
          standardAnnual = 0;
          canApply = 0;
        } 
      } 
      standardAnnual = canApply;
      String sql = "select qj_kssj,qj_jssj,qj_nj from skq_qjd qjd where skq_qjd_owner='" + empId + "' and exists (";
      sql = String.valueOf(sql) + "select wf_work_id from jsf_work jw where jw.workstatus=100 and jw.worktable_id=" + QJD_TABLE_ID + " and jw.workrecord_id=qjd.skq_qjd_id";
      sql = String.valueOf(sql) + ")";
      sql = String.valueOf(sql) + " and (qj_kssj>'" + period[0] + " 00:00:00' and qj_jssj<'" + period[1] + " 23:59:59')";
      rs = stmt.executeQuery(sql);
      while (rs.next()) {
        String temp = rs.getString(3);
        if (temp != null)
          sum += Float.parseFloat(temp); 
      } 
      rs.close();
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("oracle") >= 0) {
        sql = "SELECT id,begin_date,end_date,reducetrial FROM kq_holiday where begin_date>=to_date('" + period[0] + " 00:00:00','yyyy-MM-dd HH24:MI:SS') AND end_date<to_date('" + period[1] + " 23:59:59','yyyy-MM-dd HH24:MI:SS') and type=0 and reduceannual=1 and corp_id=" + corpId;
      } else {
        sql = "SELECT id,begin_date,end_date,reducetrial FROM kq_holiday where begin_date>='" + period[0] + " 00:00:00' AND end_date<'" + period[1] + " 23:59:59' and type=0 and reduceannual=1 and corp_id=" + corpId;
      } 
      rs = stmt.executeQuery(sql);
      float holidaySum = 0.0F;
      while (rs.next()) {
        holidaySum = (float)getHolidayDays(rs.getString(2), rs.getString(3));
        if ("1".equals(rs.getString(4))) {
          sum += holidaySum;
          continue;
        } 
        if ("正式".equals(jobStatus))
          sum += holidaySum; 
      } 
      rs.close();
      sql = "select xj_kssj,xj_jssj,xj_nj from skq_xjd xjd where skq_xjd_owner='" + empId + "' and exists (";
      sql = String.valueOf(sql) + "select wf_work_id from jsf_work jw where jw.workstatus=100 and jw.worktable_id=" + XJD_TABLE_ID + " and jw.workrecord_id=xjd.skq_xjd_id";
      sql = String.valueOf(sql) + ")";
      sql = String.valueOf(sql) + " and (xj_kssj>'" + period[0] + " 00:00:00' and xj_jssj<'" + period[1] + " 23:59:59')";
      rs = stmt.executeQuery(sql);
      while (rs.next()) {
        String temp = rs.getString(3);
        if (temp != null)
          sum -= Float.parseFloat(temp); 
      } 
      rs.close();
      annualData[0] = sum;
      annualData[1] = standardAnnual - sum;
    } catch (Exception err) {
      err.printStackTrace();
    } 
    return annualData;
  }
  
  private static List<String[]> getKqRecord(String empId, int year, int month, Statement stmt) {
    List<String[]> kqRecord = (List)new ArrayList<String>();
    String firstDateOfMonth = getFirstDateOfMonth(year, month);
    String firstDateNextMonth = getFirstDateNextMonth(year, month);
    try {
      String sql = "select rec_date,rec_onduty,rec_offduty,sign_onduty,sign_offduty,late_time,early_time from skq_record where emp_id=" + empId;
      sql = String.valueOf(sql) + " and rec_date>='" + firstDateOfMonth + "' and rec_date<'" + firstDateNextMonth + "'";
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        String[] tmp = new String[7];
        tmp[0] = rs.getString(1);
        tmp[1] = rs.getString(2);
        tmp[2] = rs.getString(3);
        tmp[3] = rs.getString(4);
        tmp[4] = rs.getString(5);
        tmp[5] = rs.getString(6);
        tmp[6] = rs.getString(7);
        kqRecord.add(tmp);
      } 
      rs.close();
    } catch (Exception err) {
      err.printStackTrace();
    } 
    return kqRecord;
  }
  
  private static String getFirstDateOfMonth(int year, int month) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(1, year);
    calendar.set(2, month - 1);
    calendar.set(5, 1);
    calendar.add(5, -1);
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    return format.format(calendar.getTime());
  }
  
  private static String getFirstDateNextMonth(int year, int month) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(1, year);
    calendar.set(2, month - 1);
    calendar.set(5, 1);
    calendar.add(2, 1);
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    return format.format(calendar.getTime());
  }
  
  private static boolean timeWithinTheRange(String time, String beginTime, String endTime) {
    boolean result = false;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      Date date = format.parse(time);
      Date begin = format.parse(beginTime);
      Date end = format.parse(endTime);
      if ((date.after(begin) && date.before(end)) || date.equals(begin) || date.equals(end))
        result = true; 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return result;
  }
  
  private static float caclateHours(String time) {
    float hour = 0.0F;
    if (time.indexOf(":") > 0) {
      String h = time.substring(0, 2);
      String m = time.substring(3, 5);
      hour += Float.valueOf(h).floatValue() + Float.valueOf(m).floatValue() / 60.0F;
    } 
    return hour;
  }
  
  public boolean dataLock(String corpId, String reportMonth) {
    boolean result = false;
    Connection conn = null;
    PreparedStatement pstmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      String sql = "update skq_report skq set data_lock=1 where report_month=? and exists (select oo.org_id from org_organization_user oou,org_organization oo where oo.org_id=oou.org_id and skq.emp_id=oou.emp_id and oo.orgidstring like ?)";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, reportMonth);
      pstmt.setString(2, "%$" + corpId + "$%");
      pstmt.executeUpdate();
      pstmt.close();
      conn.close();
      result = false;
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    return result;
  }
  
  public int getDataLockStatus(String corpId, String reportMonth) {
    int result = 0;
    Connection conn = null;
    PreparedStatement pstmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      String sql = "select data_lock from skq_report skq where report_month=? and exists (select oo.org_id from org_organization_user oou,org_organization oo where oo.org_id=oou.org_id and skq.emp_id=oou.emp_id and oo.orgidstring like ?)";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, reportMonth);
      pstmt.setString(2, "%$" + corpId + "$%");
      ResultSet rs = pstmt.executeQuery();
      if (rs.next())
        result = rs.getInt(1); 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    return result;
  }
  
  private static boolean dateInRange(String dateStr, String beginStr, String endStr) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    boolean res = false;
    try {
      Date date = format.parse(dateStr);
      Date begin = format.parse(beginStr);
      Date end = format.parse(endStr);
      if (date.after(begin) && date.before(end))
        res = true; 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return res;
  }
  
  public static void main(String[] args) {
    String beginDate = "2015-9-17";
    String endDate = "2015-9-18";
    long days = 1L;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    try {
      Date begin = format.parse(beginDate);
      Date end = format.parse(endDate);
      days = (end.getTime() - begin.getTime()) / 86400000L + 1L;
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
}
