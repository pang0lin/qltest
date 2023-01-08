package com.js.oa.hr.kq.szgt.service;

import com.js.oa.userdb.util.DbOpt;
import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HolidayUtil {
  private static int QJD_TABLE_ID = 21191;
  
  private static int XJD_TABLE_ID = 21663;
  
  public String[] getHolidyData(String empId, String corpId) {
    String[] holidays = { "0", "0", "0", "0", "0", "0" };
    DbOpt dbopt = new DbOpt();
    try {
      String sql = "select sickannual,leaveannual,leaveinlieuannual from skq_para where corp_id=" + corpId;
      String[][] temp = dbopt.executeQueryToStrArr2(sql, 3);
      if (temp != null) {
        holidays[2] = temp[0][0];
        holidays[3] = temp[0][1];
        holidays[4] = temp[0][2];
      } 
      sql = "select oe.emp_id,oe.empname,oo.orgName,oe.empnumber,oe.empfiredate,oe.intoCompanyDate,oe.jobStatus,oe.zhuanzhengdate,oe.glbdef15 from org_employee oe,org_organization_user oou,org_organization oo where  oe.emp_id=oou.emp_id and oou.org_id=oo.org_id and oe.emp_id=" + empId;
      String[][] userData = dbopt.executeQueryToStrArr2(sql, 9);
      dbopt.close();
      List<String[]> list = (List)new ArrayList<String>();
      list.add(userData[0]);
      Calendar cal = Calendar.getInstance();
      List<String[]> holidayList = getEmpHoliday(cal, list, corpId);
      if (holidayList != null && holidayList.size() > 0) {
        String[] temp1 = holidayList.get(0);
        holidays[0] = temp1[5];
        if (!"正式".equals(userData[0][6])) {
          holidays[1] = String.valueOf(0.0F - Float.parseFloat(temp1[7]));
          holidays[5] = "0";
        } else {
          holidays[5] = "1";
          holidays[1] = String.valueOf(3.0F - Float.parseFloat(temp1[7]));
        } 
      } 
    } catch (Exception ex) {
      if (dbopt != null)
        try {
          dbopt.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    return holidays;
  }
  
  public List<String[]> getEmpHoliday(Calendar calendar, List<String[]> userList, String corpId) {
    Connection conn = null;
    Statement stmt = null;
    String databaseType = SystemCommon.getDatabaseType();
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      for (int i = 0; i < userList.size(); i++) {
        String[] userInfo = userList.get(i);
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
        String[] annualPeriod = getCurAnnualPeriodInfo(userInfo, calendar, corpId, stmt);
        annualRemain = Float.parseFloat(annualPeriod[2]);
        List<String[]> annualLeaves = getAnnualLeaves(userInfo, annualPeriod[0], annualPeriod[1], corpId, stmt);
        for (String[] annualTemp : annualLeaves)
          annualLeave += Float.parseFloat(annualTemp[2]); 
        List<String[]> holidayList = getHolidayList(annualPeriod[0], annualPeriod[1], corpId, stmt);
        for (String[] holiday : holidayList) {
          if ("1".equals(holiday[1])) {
            annualLeave += Float.parseFloat(holiday[0]);
            continue;
          } 
          if ("正式".equals(userInfo[6]))
            annualLeave += Float.parseFloat(holiday[0]); 
        } 
        annualSum = annualLeave;
        annualRemain -= annualSum;
        List<String[]> leaveList = getLeaveList(userInfo, calendar, corpId, stmt);
        for (String[] temp : leaveList) {
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
        List<String[]> reduceLeaveList = getReduceLeaveList(userInfo, calendar, corpId, stmt);
        for (String[] temp : reduceLeaveList) {
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
        String[] holidayInfo = new String[14];
        holidayInfo[0] = userInfo[1];
        holidayInfo[1] = userInfo[2];
        holidayInfo[2] = userInfo[3];
        holidayInfo[3] = annualPeriod[2];
        holidayInfo[4] = String.valueOf(annualSum);
        holidayInfo[5] = String.valueOf(annualRemain);
        holidayInfo[6] = String.valueOf(unpaidLeave * 8.0F);
        holidayInfo[7] = String.valueOf(sickLeave * 8.0F);
        holidayInfo[8] = String.valueOf(unpaidSick * 8.0F);
        holidayInfo[9] = String.valueOf(leaveInlieu * 8.0F);
        holidayInfo[10] = String.valueOf(workInjury * 8.0F);
        holidayInfo[11] = String.valueOf(marriageLeave * 8.0F);
        holidayInfo[12] = String.valueOf(maternityLeave * 8.0F);
        holidayInfo[13] = String.valueOf(funeralLeave * 8.0F);
        userList.set(i, holidayInfo);
      } 
      stmt.close();
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
    return userList;
  }
  
  private int getStandardAnnual(String[] emp, Calendar cal, String corpId, Statement stmt) {
    int annual = 0;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    try {
      Date empDutyDate = new Date();
      if (emp[5] != null && !"".equals(emp[5]))
        empDutyDate = format.parse(emp[5]); 
      Calendar fireDate = Calendar.getInstance();
      fireDate.setTime(empDutyDate);
      fireDate.add(2, -Integer.parseInt(emp[8]));
      fireDate.add(1, 1);
      if (fireDate.before(cal))
        annual = 5; 
      fireDate.add(1, 9);
      if (fireDate.before(cal))
        annual = 10; 
      fireDate.add(1, 10);
      if (fireDate.before(cal))
        annual = 15; 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return annual;
  }
  
  private String[] getCurAnnualPeriodInfo(String[] emp, Calendar cal, String corpId, Statement stmt) {
    String[] period = new String[4];
    int canApply = 0;
    int year = cal.get(1);
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    try {
      int standardAnnual = getStandardAnnual(emp, cal, corpId, stmt);
      String annualPeriod = "0";
      if (emp[5].indexOf(" ") > 0)
        emp[5] = emp[5].substring(0, emp[5].indexOf(" ")); 
      Date dutyDate = null;
      if ("".equals(emp[5])) {
        dutyDate = new Date();
      } else {
        dutyDate = format.parse(emp[5]);
      } 
      int workDutyYear = 1900 + dutyDate.getYear();
      ResultSet rs = stmt.executeQuery("select annualperiod from skq_para where corp_id=" + corpId);
      if (rs.next())
        annualPeriod = rs.getString(1); 
      rs.close();
      boolean workFullYear = false;
      int workOneYearInt = year;
      Date empFireDate = null;
      Calendar calEmpFireDate = Calendar.getInstance();
      calEmpFireDate.setTime(dutyDate);
      calEmpFireDate.add(2, -Integer.parseInt(emp[8]));
      empFireDate = calEmpFireDate.getTime();
      Calendar empFireCal = Calendar.getInstance();
      empFireCal.setTime(empFireDate);
      empFireCal.add(1, 1);
      Date workOneYearDate = empFireCal.getTime();
      workOneYearInt = empFireCal.get(1);
      if (empFireCal.before(cal))
        workFullYear = true; 
      boolean dutyWorkFullYear = false;
      if (workOneYearDate.before(dutyDate))
        dutyWorkFullYear = true; 
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
          if (currentAnnualBegin.after(cal)) {
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
          if (currentAnnualBegin.after(cal)) {
            period[1] = format.format(currentAnnualBegin.getTime());
            currentAnnualBegin.add(1, -1);
            period[0] = format.format(currentAnnualBegin.getTime());
            if (currentAnnualBegin.getTime().after(dutyDate)) {
              canApply = standardAnnual;
            } else {
              Date dateEnd = format.parse(period[1]);
              canApply = (int)((dateEnd.getTime() - workOneYearDate.getTime()) / 86400000L * standardAnnual) / 365;
            } 
          } else {
            period[0] = format.format(currentAnnualBegin.getTime());
            currentAnnualBegin.add(1, 1);
            period[1] = format.format(currentAnnualBegin.getTime());
            currentAnnualBegin.add(1, -2);
            if (currentAnnualBegin.getTime().before(dutyDate)) {
              Date dateEnd = cal.getTime();
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
      period[2] = String.valueOf(canApply);
      period[3] = String.valueOf(standardAnnual);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return period;
  }
  
  private String getAlowAnnualLeave(String[] emp, Calendar cal, String corpId, Statement stmt) {
    String annual = "0";
    try {
      String annualPeriod = "0";
      ResultSet rs = stmt.executeQuery("select annualperiod from skq_para where corp_id=" + corpId);
      if (rs.next())
        annualPeriod = rs.getString(1); 
      rs.close();
      if (!"0".equals(annualPeriod))
        "1".equals(annualPeriod); 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return annual;
  }
  
  private List<String[]> getLeaveList(String[] emp, Calendar cal, String corpId, Statement stmt) {
    List<String[]> leaveList = (List)new ArrayList<String>();
    String firstDate = String.valueOf(cal.get(1)) + "-01-01 00:00:00";
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String now = format.format(cal.getTime());
    String sql = "select qj_kssj,qj_jssj,qj_nj,qj_sj,qj_bjdx,qj_bjbdx,qj_dx,qj_gs,qj_hj,qj_cj,qj_sangj,qj_qjss from skq_qjd qjd where skq_qjd_owner='" + emp[0] + "' and exists (";
    sql = String.valueOf(sql) + "select wf_work_id from jsf_work jw where jw.workstatus=100 and jw.worktable_id=" + QJD_TABLE_ID + " and jw.workrecord_id=qjd.skq_qjd_id";
    sql = String.valueOf(sql) + ")";
    sql = String.valueOf(sql) + " and (qj_kssj>'" + firstDate + "' and qj_jssj<'" + now + "')";
    try {
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        String[] leaves = new String[12];
        leaves[0] = rs.getString(1);
        leaves[1] = rs.getString(2);
        leaves[2] = rs.getString(3);
        leaves[3] = rs.getString(4);
        leaves[4] = rs.getString(5);
        leaves[5] = rs.getString(6);
        leaves[6] = rs.getString(7);
        leaves[7] = rs.getString(8);
        leaves[8] = rs.getString(9);
        leaves[9] = rs.getString(10);
        leaves[10] = rs.getString(11);
        leaves[11] = rs.getString(12);
        leaveList.add(leaves);
      } 
      rs.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return leaveList;
  }
  
  private List<String[]> getReduceLeaveList(String[] emp, Calendar cal, String corpId, Statement stmt) {
    List<String[]> leaveList = (List)new ArrayList<String>();
    String firstDate = String.valueOf(cal.get(1)) + "-01-01 00:00:00";
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String now = format.format(cal.getTime());
    String sql = "select xj_kssj,xj_jssj,xj_nj,xj_sj,xj_bjdx,xj_bjbdx,xj_dx,xj_gs,xj_hj,xj_cj,xj_sangj,xj_xjss from skq_xjd xjd where skq_xjd_owner='" + emp[0] + "' and exists (";
    sql = String.valueOf(sql) + "select wf_work_id from jsf_work jw where jw.workstatus=100 and jw.worktable_id=" + XJD_TABLE_ID + " and jw.workrecord_id=xjd.skq_xjd_id";
    sql = String.valueOf(sql) + ")";
    sql = String.valueOf(sql) + " and (xj_kssj>'" + firstDate + "' and xj_jssj<'" + now + "')";
    try {
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        String[] leaves = new String[12];
        leaves[0] = rs.getString(1);
        leaves[1] = rs.getString(2);
        leaves[2] = rs.getString(3);
        leaves[3] = rs.getString(4);
        leaves[4] = rs.getString(5);
        leaves[5] = rs.getString(6);
        leaves[6] = rs.getString(7);
        leaves[7] = rs.getString(8);
        leaves[8] = rs.getString(9);
        leaves[9] = rs.getString(10);
        leaves[10] = rs.getString(11);
        leaves[11] = rs.getString(12);
        leaveList.add(leaves);
      } 
      rs.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return leaveList;
  }
  
  private List<String[]> getAnnualLeaves(String[] emp, String startDate, String endDate, String corpId, Statement stmt) {
    List<String[]> leaveList = (List)new ArrayList<String>();
    String sql = "select qj_kssj,qj_jssj,qj_nj from skq_qjd qjd where skq_qjd_owner='" + emp[0] + "' and exists (";
    sql = String.valueOf(sql) + "select wf_work_id from jsf_work jw where jw.workstatus=100 and jw.worktable_id=" + QJD_TABLE_ID + " and jw.workrecord_id=qjd.skq_qjd_id";
    sql = String.valueOf(sql) + ")";
    sql = String.valueOf(sql) + " and (qj_kssj>'" + startDate + " 00:00:00' and qj_jssj<'" + endDate + " 23:59:59')";
    try {
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        String[] leaves = new String[3];
        leaves[0] = rs.getString(1);
        leaves[1] = rs.getString(2);
        leaves[2] = rs.getString(3);
        leaveList.add(leaves);
      } 
      rs.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return leaveList;
  }
  
  private List<String[]> getHolidayList(String startDate, String endDate, String corpId, Statement stmt) {
    List<String[]> holidayList = (List)new ArrayList<String>();
    String databaseType = SystemCommon.getDatabaseType();
    try {
      String sql;
      if (databaseType.indexOf("oracle") >= 0) {
        sql = "SELECT id,begin_date,end_date,reducetrial FROM kq_holiday where begin_date>=to_date('" + startDate + " 00:00:00','yyyy-MM-dd HH24:MI:SS') AND end_date<to_date('" + endDate + " 23:59:59','yyyy-MM-dd HH24:MI:SS') and type=0 and reduceannual=1 and corp_id=" + corpId;
      } else {
        sql = "SELECT id,begin_date,end_date,reducetrial FROM kq_holiday where begin_date>='" + startDate + " 00:00:00' AND end_date<'" + endDate + " 23:59:59' and type=0 and reduceannual=1 and corp_id=" + corpId;
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
  
  private boolean dateIsBefore(Date begin, Date end) {
    if (begin.before(end))
      return true; 
    return false;
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
}
