package com.js.oa.hr.kq.util;

import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import com.js.util.util.DataSourceUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WanXieKq {
  SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
  
  SimpleDateFormat ymdhm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
  
  SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  
  SimpleDateFormat hm = new SimpleDateFormat("HH:mm");
  
  String today = "2014-04-03";
  
  String yesterday = "2014-04-02";
  
  Long flag = Long.valueOf(0L);
  
  String time = SystemCommon.getDayBegin();
  
  int isHoliday = 0;
  
  public String mainFunction(String rtoday) {
    String deleteOldData = (new StringBuilder(String.valueOf(SystemCommon.getWxdel()))).toString();
    try {
      this.today = rtoday;
      this.yesterday = this.ymd.format(new Date(this.ymd.parse(this.today).getTime() - 86400000L));
      this.flag = Long.valueOf(getFlag(this.yesterday));
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    if (insertIntoWork(deleteOldData)) {
      List<String[]> atdRecord = getAtdRecord(this.yesterday, this.today, this.time);
      insertToOa(atdRecord);
      updateKq();
      return "同步完成";
    } 
    return "已同步";
  }
  
  public List<String[]> getList(String rtoday, String userId, String orgId) {
    DataSourceBase base = new DataSourceBase();
    List<String[]> list = (List)new ArrayList<String>();
    try {
      this.today = rtoday;
      this.yesterday = this.ymd.format(new Date(this.ymd.parse(this.today).getTime() - 86400000L));
      this.flag = Long.valueOf(getFlag(this.yesterday));
      base.begin();
      String sql = "select empName,work_empNumber,o.Orgnamestring,work_begin,work_end,work_status,work_length,w.work_id from wx_atdwork w join org_employee e on w.work_empId=e.emp_id join org_organization_user ou on e.emp_id=ou.emp_id join org_organization o on ou.org_id=o.org_id where work_day=" + 

        
        this.flag;
      if (!userId.equals("")) {
        userId = userId.replace("$$", ",").replace("$", "");
        sql = String.valueOf(sql) + " and w.work_empId in (" + userId + ")";
      } 
      if (!orgId.equals("")) {
        sql = String.valueOf(sql) + " and (";
        orgId = orgId.replace("**", ",").replace("*", "");
        String[] orgIds = orgId.split(",");
        for (int i = 0; i < orgIds.length; i++)
          sql = String.valueOf(sql) + " o.orgIdString like '%$" + orgIds[i] + "$%' " + ((i == orgIds.length - 1) ? "" : "or"); 
        sql = String.valueOf(sql) + ")";
      } 
      sql = String.valueOf(sql) + " order by work_empNumber";
      ResultSet rs = base.executeQuery(sql);
      while (rs.next()) {
        String[] single = { (rs.getString(1) == null) ? "" : rs.getString(1), 
            (rs.getString(2) == null) ? "" : rs.getString(2), 
            (rs.getString(3) == null) ? "" : rs.getString(3), 
            (rs.getString(4) == null) ? "" : rs.getString(4), 
            (rs.getString(5) == null) ? "" : rs.getString(5), 
            (rs.getString(6) == null) ? "" : rs.getString(6), 
            (rs.getString(7) == null) ? "" : rs.getString(7), 
            (rs.getString(8) == null) ? "" : rs.getString(8) };
        list.add(single);
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    return list;
  }
  
  public void deleteKq(String rtoday) {
    DataSourceBase base = new DataSourceBase();
    try {
      this.today = rtoday;
      this.yesterday = this.ymd.format(new Date(this.ymd.parse(this.today).getTime() - 86400000L));
      this.flag = Long.valueOf(getFlag(this.yesterday));
      String sql = "delete from wx_atdRecord where record_day=" + this.flag;
      base.begin();
      base.executeSQL(sql);
      sql = "delete from wx_atdWork where work_day=" + this.flag;
      base.executeSQL(sql);
      sql = "delete from wx_atdRest where rest_day=" + this.flag;
      base.executeSQL(sql);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    System.out.println("删除成功");
  }
  
  public void deleteAllKq() {
    DataSourceBase base = new DataSourceBase();
    try {
      String sql = "delete from wx_atdRecord";
      base.begin();
      base.executeSQL(sql);
      sql = "delete from wx_atdWork";
      base.executeSQL(sql);
      sql = "delete from wx_atdRest";
      base.executeSQL(sql);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    System.out.println("删除全部考勤成功！");
  }
  
  private void updateKq() {
    DataSourceBase base = new DataSourceBase();
    String sql = "";
    String workB = SystemCommon.getKqBegin();
    String workE = SystemCommon.getKqEnd();
    try {
      Map<String, String[]> atdRecord = getOARecordMap("1,2,3,4");
      Map<String, String[]> resRecord = getOARecordMap("3,4");
      String[] keys = ((String[])atdRecord.get("keys"))[1].split(",");
      String[] keyr = ((String[])resRecord.get("keys"))[1].split(",");
      base.begin();
      for (int i = 0; i < keys.length; i++) {
        if (!"-1".equals(keys[i])) {
          String[] beg = (String[])null;
          String[] end = (String[])null;
          String[] temp1 = (String[])null;
          String[] temp2 = (String[])null;
          for (int t = 0; t <= 4; t++) {
            if (atdRecord.get(String.valueOf(keys[i]) + "_" + t) != null)
              if (temp1 == null) {
                temp1 = atdRecord.get(String.valueOf(keys[i]) + "_" + t);
              } else {
                temp2 = atdRecord.get(String.valueOf(keys[i]) + "_" + t);
              }  
          } 
          if (temp1 != null && temp2 != null) {
            if (Long.valueOf(temp1[3]).longValue() > Long.valueOf(temp2[3]).longValue()) {
              beg = temp2;
              end = temp1;
            } else {
              beg = temp1;
              end = temp2;
            } 
          } else {
            beg = temp1;
            end = temp2;
          } 
          if (beg != null || end != null) {
            Long beginTime = Long.valueOf(0L), endTime = Long.valueOf(0L);
            String workBegin = "", workEnd = "";
            String equNoB = "000", equNoE = "000";
            String status = "@begin@,@end@";
            if (beg != null) {
              if (getTimeInt(workB) >= getTimeInt(beg[2])) {
                status = status.replace("@begin@", "0");
              } else {
                status = status.replace("@begin@", "2");
              } 
              beginTime = Long.valueOf(beg[3]);
              workBegin = beg[2];
              equNoB = beg[5];
            } 
            if (end != null) {
              if (getTimeInt(workE) <= getTimeInt(end[2]) || (getTimeInt(end[2]) >= 0 && getTimeInt(end[2]) <= getTimeInt(this.time))) {
                status = status.replace("@end@", "1");
              } else {
                status = status.replace("@end@", "3");
              } 
              endTime = Long.valueOf(end[3]);
              workEnd = end[2];
              equNoE = end[5];
            } 
            Long workLong = Long.valueOf(0L);
            if (beginTime.longValue() != 0L && endTime.longValue() != 0L) {
              workLong = Long.valueOf(endTime.longValue() - beginTime.longValue());
              if (workLong.longValue() < 0L)
                workLong = Long.valueOf(0L); 
            } 
            status = status.replace("@begin@", "4").replace("@end@", "5");
            sql = "update wx_atdWork set work_date='" + this.yesterday + "',work_begin='" + workBegin + "',work_end='" + workEnd + "',work_status='" + status + "'," + 
              "work_length=" + workLong + ",work_equNoB='" + equNoB + "',work_equNoE='" + equNoE + "'" + 
              " where work_empNumber='" + keys[i] + "' and work_day=" + this.flag;
            base.addBatch(sql);
          } 
          if (i % 50 == 0)
            base.executeBatch(); 
        } 
      } 
      base.executeBatch();
      atdRecord.clear();
      String str = ",";
      for (int j = 0; j < keyr.length; j++) {
        String keyB = keyr[j].replace("_4_", "_3_");
        String keyE = keyr[j].replace("_3_", "_4_");
        if (!str.contains("," + keyB + ",") && !"".equals(keyB)) {
          str = String.valueOf(str) + keyB + ",";
          String empNumber = keyr[j].substring(0, keyB.indexOf("_"));
          long empId = getEmpIdByNumber(empNumber);
          if (empId != 0L) {
            String[] beg = resRecord.get(keyB);
            String[] end = resRecord.get(keyE);
            if (beg != null || end != null) {
              Long beginTime = Long.valueOf(0L), endTime = Long.valueOf(0L);
              String workBegin = "", workEnd = "";
              String equNoB = "000", equNoE = "000";
              String status = "6";
              if (beg != null) {
                beginTime = Long.valueOf(beg[3]);
                workBegin = beg[2];
                equNoB = beg[5];
              } 
              if (end != null) {
                endTime = Long.valueOf(end[3]);
                workEnd = end[2];
                equNoE = end[5];
              } 
              Long workLong = Long.valueOf(0L);
              if (beginTime.longValue() != 0L && endTime.longValue() != 0L)
                workLong = Long.valueOf(endTime.longValue() - beginTime.longValue()); 
              status = status.replace("@begin@", "4").replace("@end@", "5");
              sql = "insert into wx_atdRest (rest_id,rest_empId,rest_empNumber,rest_date,rest_begin,rest_end,rest_status,rest_day,rest_length,rest_equNoB,rest_equNoE) values (hibernate_sequence.nextval," + 
                empId + ",'" + empNumber + "','" + this.yesterday + "','" + workBegin + "'," + 
                "'" + workEnd + "','" + status + "'," + this.flag + "," + workLong + ",'" + equNoB + "','" + equNoE + "')";
              base.addBatch(sql);
            } 
          } 
          if (j % 50 == 0)
            base.executeBatch(); 
        } 
      } 
      base.executeBatch();
      resRecord.clear();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
  }
  
  private boolean insertIntoWork(String deleteOldData) {
    boolean go = true;
    DataSourceBase base = new DataSourceBase();
    String[] empIds = (String[])null;
    String[] empNums = (String[])null;
    String empIdStr = "", empNumber = "";
    String sql = "SELECT count(work_id) from wx_atdWork where work_day=" + this.flag;
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      if (rs.next()) {
        int num = rs.getInt(1);
        if (num > 0)
          go = false; 
      } 
      rs.close();
      if (!go && "1".equals(deleteOldData)) {
        sql = "delete from wx_atdRecord where record_day=" + this.flag;
        base.executeSQL(sql);
        sql = "delete from wx_atdWork where work_day=" + this.flag;
        base.executeSQL(sql);
        sql = "delete from wx_atdRest where rest_day=" + this.flag;
        base.executeSQL(sql);
        go = true;
      } 
      if (go) {
        sql = "SELECT emp_id,empnumber FROM org_employee WHERE userisdeleted=0 AND userisactive=1 AND emp_id <>-99 AND emp_id <>0  ORDER BY emp_id";
        rs = base.executeQuery(sql);
        while (rs.next()) {
          empIdStr = String.valueOf(empIdStr) + ((rs.getString(1) == null) ? "," : (String.valueOf(rs.getString(1)) + ","));
          empNumber = String.valueOf(empNumber) + ((rs.getString(2) == null) ? "0," : (String.valueOf(rs.getString(2)) + ","));
        } 
        if (!"".equals(empIdStr)) {
          empIds = empIdStr.split(",");
          empNums = empNumber.split(",");
          for (int i = 0; i < empIds.length; i++) {
            String singleSql = "insert into wx_atdWork (work_id,work_empId,work_empNumber,work_date,work_begin,work_end,work_status,work_day,work_length,work_holiday) values (hibernate_sequence.nextval," + 
              
              empIds[i] + ",'" + empNums[i] + "','" + this.yesterday + "','','','4,5'," + this.flag + ",0," + this.isHoliday + ")";
            base.addBatch(singleSql);
            if (i % 50 == 0)
              base.executeBatch(); 
          } 
          base.executeBatch();
        } else {
          go = false;
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    return go;
  }
  
  public Map<String, String[]> getOARecordMap(String flag) {
    DataSourceBase base = new DataSourceBase();
    Map<String, String[]> map = (Map)new HashMap<String, String>();
    String keys = ",";
    try {
      String beginInt = (new StringBuilder(String.valueOf(this.ymdhm.parse(String.valueOf(this.yesterday) + " " + this.time).getTime() / 1000L))).toString();
      String endInt = (new StringBuilder(String.valueOf(this.ymdhm.parse(String.valueOf(this.today) + " " + this.time).getTime() / 1000L))).toString();
      String sql = "select record_empNumber,record_date,record_time,record_timeInt,record_flag,record_equNo,record_Id from wx_atdRecord where record_timeInt<" + 
        endInt + " and record_timeInt>" + beginInt + " and record_flag in (" + flag + ") " + 
        " order by record_empNumber,record_timeInt";
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      String restF = "3";
      int i = 0;
      String serialId = ",-1,";
      String[] backup = { "-1", "", "", "0", "0", "0", "0" };
      while (rs.next()) {
        String[] single = { (rs.getString(1) == null) ? "" : rs.getString(1).trim(), 
            (rs.getString(2) == null) ? "" : rs.getString(2), 
            (rs.getString(3) == null) ? "" : rs.getString(3), 
            (rs.getString(4) == null) ? "0" : rs.getString(4), 
            (rs.getString(5) == null) ? "0" : rs.getString(5), 
            (rs.getString(6) == null) ? "0" : rs.getString(6), 
            (rs.getString(7) == null) ? "0" : rs.getString(7) };
        if (flag.contains("1") || flag.contains("2")) {
          if (!backup[0].equals(single[0])) {
            if (!serialId.contains("," + backup[6] + ",")) {
              String[] back = new String[7];
              for (int j = 0; j < backup.length; ) {
                back[j] = backup[j];
                j++;
              } 
              serialId = String.valueOf(serialId) + backup[6] + ",";
              if (map.get(String.valueOf(backup[0]) + "_" + backup[4]) == null) {
                map.put(String.valueOf(backup[0]) + "_" + backup[4], back);
              } else {
                map.put(String.valueOf(backup[0]) + "_0", back);
              } 
              if (!keys.contains("," + backup[0] + ","))
                keys = String.valueOf(keys) + backup[0] + ","; 
            } 
            if (!serialId.contains("," + single[6] + ",")) {
              map.put(String.valueOf(single[0]) + "_" + single[4], single);
              if (!keys.contains("," + single[0] + ","))
                keys = String.valueOf(keys) + single[0] + ","; 
              serialId = String.valueOf(serialId) + single[6] + ",";
            } 
          } 
        } else {
          if (!"34".equals(String.valueOf(restF) + single[4]) || i == 0)
            i++; 
          map.put(String.valueOf(single[0]) + "_" + single[4] + "_" + i, single);
          if (!keys.contains("," + single[0] + "_" + single[4] + "_" + i + ","))
            keys = String.valueOf(keys) + single[0] + "_" + single[4] + "_" + i + ","; 
          restF = single[4];
        } 
        for (int s = 0; s < single.length; ) {
          backup[s] = single[s];
          s++;
        } 
      } 
      if (!serialId.contains("," + backup[6] + ","))
        if (map.get(String.valueOf(backup[0]) + "_" + backup[4]) == null) {
          map.put(String.valueOf(backup[0]) + "_" + backup[4], backup);
        } else {
          map.put(String.valueOf(backup[0]) + "_0", backup);
        }  
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    map.put("keys", new String[] { flag, (keys.length() > 1) ? keys.substring(0) : "", "" });
    return map;
  }
  
  private List<String[]> getAtdRecord(String fDate, String sDate, String time) {
    List<String[]> list = (List)new ArrayList<String>();
    DataSourceBase base = new DataSourceBase();
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = base.getDataSource("wxkq").getConnection();
      stmt = conn.createStatement();
      for (int i = 0; i < 2; i++) {
        ResultSet rs = stmt.executeQuery(getDifSql(fDate, sDate, i));
        while (rs.next()) {
          String[] single = { (rs.getString(1) == null) ? "" : rs.getString(1).trim(), 
              (rs.getString(2) == null) ? "" : rs.getString(2), 
              (rs.getString(3) == null) ? "" : rs.getString(3), 
              (rs.getString(4) == null) ? "" : rs.getString(4) };
          list.add(single);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (stmt != null)
          stmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    return list;
  }
  
  private String getDifSql(String fDate, String sDate, int num) {
    String[] equNos = { "001,003,010,002", "005,008,006,007" };
    String sql = "select e.workId,r.RecDate,r.RecTime,r.EquNo ";
    sql = String.valueOf(sql) + " from AtdRecord2 r join HrEmployee e on r.EmplID=e.EmplID where ((r.RecDate='" + 
      fDate + "' and r.RecTime > '" + this.time + "') or (r.RecDate='" + sDate + "' and r.RecTime < '" + this.time + "'))";
    String[] equNo = equNos[num].split(",");
    if (equNo.length > 0) {
      sql = String.valueOf(sql) + " and (";
      for (int i = 0; i < equNo.length; i++)
        sql = String.valueOf(sql) + " r.equno='" + equNo[i] + "'" + ((i == equNo.length - 1) ? "" : " or "); 
      sql = String.valueOf(sql) + ")";
    } 
    sql = String.valueOf(sql) + " order by e.workId";
    return sql;
  }
  
  private void insertToOa(List<String[]> list) {
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      for (int i = 0; i < list.size(); i++) {
        String[] single = list.get(i);
        Long timeInt = Long.valueOf(this.ymdhms.parse(String.valueOf(single[1]) + " " + single[2]).getTime() / 1000L);
        String zflag = "";
        if (",001,003,010,".contains("," + single[3] + ",")) {
          zflag = "1";
        } else if ("002".equals(single[3])) {
          zflag = "2";
        } else if (",005,008,".contains("," + single[3] + ",")) {
          zflag = "3";
        } else {
          zflag = "4";
        } 
        String insertSql = "insert into wx_atdRecord (record_id,record_empNumber,record_date,record_time,record_equNo,record_timeInt,record_flag,record_day) values (hibernate_sequence.nextval,'" + 
          single[0] + "','" + single[1] + "','" + single[2] + "','" + single[3] + "'," + timeInt + "," + zflag + "," + this.flag + ")";
        base.addBatch(insertSql);
        if (i % 50 == 0)
          base.executeBatch(); 
      } 
      base.executeBatch();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
  }
  
  private long getFlag(String dateStr) {
    try {
      Long nowLong = Long.valueOf(this.ymdhms.parse(String.valueOf(dateStr) + " 08:00:00").getTime());
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(this.ymd.parse(dateStr));
      int isWeekDay = 0;
      if (calendar.get(7) == 1 || calendar.get(7) == 7)
        isWeekDay = 1; 
      int holidaySet = getHoliday(dateStr);
      if (holidaySet == 0) {
        this.isHoliday = isWeekDay;
      } else if (holidaySet == 1) {
        this.isHoliday = 0;
      } else if (holidaySet == 2) {
        this.isHoliday = 1;
      } 
      return nowLong.longValue() / 86400000L;
    } catch (Exception e) {
      e.printStackTrace();
      return 0L;
    } 
  }
  
  public int getHoliday(String dateStr) {
    String sql = "select id,type from kq_holiday where to_date('" + dateStr + " 12:00:00','yyyy-mm-dd hh24:mi:ss') " + 
      "between begin_date and end_date order by id desc";
    List<String[]> list = (new DataSourceUtil()).getListQuery(sql, "");
    if (list.size() < 1)
      return 0; 
    String data = ((String[])list.get(0))[1];
    if ("1".equals(data))
      return 1; 
    return 2;
  }
  
  private int getTimeInt(String timeHm) {
    String[] times = timeHm.split(":");
    if (times.length > 2)
      return Integer.valueOf(times[0]).intValue() * 3600 + Integer.valueOf(times[1]).intValue() * 60 + Integer.valueOf(times[2]).intValue(); 
    return Integer.valueOf(times[0]).intValue() * 3600 + Integer.valueOf(times[1]).intValue() * 60;
  }
  
  private long getEmpIdByNumber(String empNumber) {
    DataSourceBase base = new DataSourceBase();
    long returnValue = 0L;
    try {
      base.begin();
      String sql = "select emp_id from org_employee where empNumber='" + empNumber + "'";
      ResultSet rs = base.executeQuery(sql);
      if (rs.next())
        returnValue = rs.getLong(1); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    return returnValue;
  }
}
