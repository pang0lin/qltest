package com.js.oa.security.log.util;

import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import com.js.util.util.DateHelper;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogService {
  public static void doMethod() {
    Connection conn = null;
    Statement stmt = null;
    int flag = 0;
    try {
      String temp_str = "";
      Date dt = new Date();
      dt = DateHelper.beforDate(dt);
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      temp_str = sdf.format(dt);
      String databaseType = SystemCommon.getDatabaseType();
      StringBuffer where = new StringBuffer();
      if (databaseType.indexOf("mysql") >= 0) {
        where.append(" where OPRSTARTTIME<='").append(String.valueOf(temp_str) + " 23:59:59")
          .append("' and OPRSTARTTIME>='").append(String.valueOf(temp_str) + " 00:00:00")
          .append("'");
      } else {
        where.append(" where OPRSTARTTIME<=JSDB.FN_STRTODATE('").append(String.valueOf(temp_str) + " 23:59:59")
          .append("','') and OPRSTARTTIME>=JSDB.FN_STRTODATE('").append(String.valueOf(temp_str) + " 00:00:00")
          .append("','')");
      } 
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      StringBuffer sql = new StringBuffer();
      if (databaseType.indexOf("mysql") >= 0) {
        sql.append("  INSERT INTO sec_loghis(EMP_ID,EMPNAME,EMPORGNAME,MODULE_SERIAL,OPRSTARTTIME, ");
        sql.append(" OPRENDTIME,OPRSUBMODULE,OPRTYPE,OPRCONTENT,DOMAIN_ID,LOGIP)");
        sql.append("  SELECT EMP_ID,EMPNAME,EMPORGNAME,MODULE_SERIAL,OPRSTARTTIME,OPRENDTIME,OPRSUBMODULE,");
        sql.append("  OPRTYPE,OPRCONTENT,DOMAIN_ID, LOGIP  FROM sec_log ");
        sql.append(where.toString());
      } else {
        sql.append("  INSERT INTO sec_loghis(LOG_ID,EMP_ID,EMPNAME,EMPORGNAME,MODULE_SERIAL,OPRSTARTTIME, ");
        sql.append(" OPRENDTIME,OPRSUBMODULE,OPRTYPE,OPRCONTENT,DOMAIN_ID,LOGIP)");
        sql.append("  SELECT LOG_ID,EMP_ID,EMPNAME,EMPORGNAME,MODULE_SERIAL,OPRSTARTTIME,OPRENDTIME,OPRSUBMODULE,");
        sql.append("  OPRTYPE,OPRCONTENT,DOMAIN_ID, LOGIP  FROM sec_log ");
        sql.append(where.toString());
      } 
      flag = stmt.executeUpdate(sql.toString());
      String sqlTrc = "delete from sec_log " + where.toString();
      if (flag > 0)
        flag = stmt.executeUpdate(sqlTrc.toString()); 
      stmt.close();
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
      if (stmt != null)
        try {
          stmt.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
    } 
  }
}
