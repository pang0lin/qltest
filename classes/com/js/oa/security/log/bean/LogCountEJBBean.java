package com.js.oa.security.log.bean;

import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import com.js.util.util.DataSourceUtil;
import com.js.util.util.IO2File;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogCountEJBBean {
  public String getOrgUserInfo(String sql) {
    String empIds = "";
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      while (rs.next())
        empIds = String.valueOf(empIds) + rs.getString(1) + ","; 
      if (empIds.endsWith(","))
        empIds = empIds.substring(0, empIds.length() - 1); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    return empIds;
  }
  
  public Map<String, Integer> getLogEmpNum(String empIds, String beginDate, String endDate) {
    Map<String, Integer> map = new HashMap<String, Integer>();
    if (!empIds.equals("")) {
      String select = "emp_id";
      if ("mysql".equalsIgnoreCase(SystemCommon.getDatabaseType())) {
        select = String.valueOf(select) + ",DATE_FORMAT(oprstarttime,'%Y-%m-%d') optime";
      } else {
        select = String.valueOf(select) + ",to_char(oprstarttime,'yyyy-mm-dd') optime";
      } 
      String where = "MODULE_SERIAL='oa_index' AND oprtype IN (0,4) AND emp_id in (" + empIds + ")";
      if ("oracle".equals(SystemCommon.getDatabaseType())) {
        where = String.valueOf(where) + " and (oprstarttime between to_date('" + beginDate + " 00:00:00','yyyy-mm-dd hh24:mi:ss') " + 
          "and to_date('" + endDate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')) ";
      } else {
        where = String.valueOf(where) + " and (oprstarttime between '" + beginDate + " 00:00:00' and '" + endDate + " 23:59:59') ";
      } 
      String selectSql = "SELECT " + select + " FROM sec_log WHERE " + where + 
        "UNION " + 
        "SELECT " + select + " FROM sec_loghis WHERE " + where;
      String sql = "SELECT DISTINCT a.emp_id,optime FROM (" + selectSql + ") a ";
      IO2File.printFile("上线统计：" + sql, "上线统计");
      DataSourceBase base = new DataSourceBase();
      try {
        base.begin();
        ResultSet rs = base.executeQuery(sql);
        while (rs.next()) {
          if (map.get(rs.getString(2)) == null) {
            map.put(rs.getString(2), Integer.valueOf(1));
            continue;
          } 
          map.put(rs.getString(2), Integer.valueOf(((Integer)map.get(rs.getString(2))).intValue() + 1));
        } 
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        base.end();
      } 
    } 
    return map;
  }
  
  public Map<String, String> getLoginMap(String empIds, String beginDate, String endDate) {
    Map<String, String> map = new HashMap<String, String>();
    String select = "emp_id";
    if ("mysql".equalsIgnoreCase(SystemCommon.getDatabaseType())) {
      select = String.valueOf(select) + ",DATE_FORMAT(oprstarttime,'%Y-%m-%d %H:%i:%s') optime";
    } else {
      select = String.valueOf(select) + ",to_char(oprstarttime,'yyyy-mm-dd hh24:mi:ss') optime";
    } 
    String where = "MODULE_SERIAL='oa_index' AND oprtype=0 AND emp_id in (" + empIds + ")";
    if ("oracle".equals(SystemCommon.getDatabaseType())) {
      where = String.valueOf(where) + " and (oprstarttime between to_date('" + beginDate + " 00:00:00','yyyy-mm-dd hh24:mi:ss') " + 
        "and to_date('" + endDate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')) ";
    } else {
      where = String.valueOf(where) + " and (oprstarttime between '" + beginDate + " 00:00:00' and '" + endDate + " 23:59:59') ";
    } 
    String selectSql = "SELECT " + select + " FROM sec_log WHERE " + where + 
      "UNION " + 
      "SELECT " + select + " FROM sec_loghis WHERE " + where;
    String sql = "SELECT emp_id,min(optime) FROM (" + selectSql + ") a GROUP BY a.emp_id";
    List<String[]> list = (new DataSourceUtil()).getListQuery(sql, "");
    for (String[] obj : list)
      map.put(obj[0], obj[1]); 
    return map;
  }
  
  public Map<String, String> getLogoutMap(String empIds, String beginDate, String endDate) {
    Map<String, String> map = new HashMap<String, String>();
    String select = "emp_id";
    if ("mysql".equalsIgnoreCase(SystemCommon.getDatabaseType())) {
      select = String.valueOf(select) + ",DATE_FORMAT(oprstarttime,'%Y-%m-%d %H:%i:%s') optime";
    } else {
      select = String.valueOf(select) + ",to_char(oprstarttime,'yyyy-mm-dd hh24:mi:ss') optime";
    } 
    String where = "MODULE_SERIAL='oa_index' AND oprtype=4 AND emp_id in (" + empIds + ")";
    if ("oracle".equals(SystemCommon.getDatabaseType())) {
      where = String.valueOf(where) + " and (oprstarttime between to_date('" + beginDate + " 00:00:00','yyyy-mm-dd hh24:mi:ss') " + 
        "and to_date('" + endDate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')) ";
    } else {
      where = String.valueOf(where) + " and (oprstarttime between '" + beginDate + " 00:00:00' and '" + endDate + " 23:59:59') ";
    } 
    String selectSql = "SELECT " + select + " FROM sec_log WHERE " + where + 
      "UNION " + 
      "SELECT " + select + " FROM sec_loghis WHERE " + where;
    String sql = "SELECT emp_id,max(optime) FROM (" + selectSql + ") a GROUP BY a.emp_id";
    List<String[]> list = (new DataSourceUtil()).getListQuery(sql, "");
    for (String[] obj : list)
      map.put(obj[0], obj[1]); 
    return map;
  }
  
  public String[] getDownloadCount(String empId) {
    String[] temp = { "0", "0", "0", "0", "0" };
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      String sql = "select count(*)  from (select * from sec_loghis where oprtype = 8 and oprstarttime > (sysdate-7) union select * from sec_log where oprtype = 8 and oprstarttime > (sysdate-7)) a where a.emp_id = " + 
        empId;
      ResultSet rs = base.executeQuery(sql);
      while (rs.next())
        temp[0] = rs.getString(1); 
      sql = "select count(*)  from (select * from sec_loghis where oprtype = 8 and oprstarttime > (sysdate-30) union select * from sec_log where oprtype = 8 and oprstarttime > (sysdate-30)) a where a.emp_id = " + 
        empId;
      rs = base.executeQuery(sql);
      while (rs.next())
        temp[1] = rs.getString(1); 
      sql = "select count(*)  from (select * from sec_loghis where oprtype = 8 and oprstarttime > (sysdate-180) union select * from sec_log where oprtype = 8 and oprstarttime > (sysdate-180)) a where a.emp_id = " + 
        empId;
      rs = base.executeQuery(sql);
      while (rs.next())
        temp[2] = rs.getString(1); 
      sql = "select count(*)  from (select * from sec_loghis where oprtype = 8 and oprstarttime > (sysdate-360) union select * from sec_log where  oprtype = 8 and oprstarttime > (sysdate-360)) a where a.emp_id = " + 
        empId;
      rs = base.executeQuery(sql);
      while (rs.next())
        temp[3] = rs.getString(1); 
      sql = "select count(*)  from (select * from sec_loghis where oprtype = 8 and oprstarttime < sysdate union select * from sec_log where oprtype = 8 and oprstarttime < sysdate) a where a.emp_id = " + 
        empId;
      rs = base.executeQuery(sql);
      while (rs.next())
        temp[4] = rs.getString(1); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    return temp;
  }
  
  public String getDownloadCount(String empId, String beginDate, String endDate) {
    String num = "0";
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      if (!empId.equals("")) {
        String sql = "select count(*) from (select * from  sec_loghis where oprstarttime between to_date('" + beginDate + " 00:00:00','yyyy-mm-dd hh24:mi:ss') " + 
          "and to_date('" + endDate + " 23:59:59','yyyy-mm-dd hh24:mi:ss') and oprtype = 8 and emp_id =  " + empId + " union " + 
          "select * from  sec_log where oprstarttime between to_date('" + beginDate + " 00:00:00','yyyy-mm-dd hh24:mi:ss') " + 
          "and to_date('" + endDate + " 23:59:59','yyyy-mm-dd hh24:mi:ss') and oprtype = 8 and emp_id =  " + empId + " ) a";
        ResultSet rs = base.executeQuery(sql);
        while (rs.next())
          num = rs.getString(1); 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    return num;
  }
}
