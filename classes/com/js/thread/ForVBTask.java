package com.js.thread;

import com.js.oa.logon.service.LogonBD;
import com.js.system.service.messages.MessagesBD;
import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;
import javax.sql.DataSource;

public class ForVBTask extends TimerTask {
  public void run() {
    Connection conn = null;
    ResultSet rs = null;
    Statement stmt = null;
    try {
      DataSource ds = (new DataSourceBase()).getDataSource();
      conn = ds.getConnection();
      stmt = conn.createStatement();
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(new Date());
      calendar.add(12, -15);
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String empId = "";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        rs = stmt.executeQuery("select online.USER_ID  from sec_onlineuser online where online.USER_UPDATETIME < '" + format.format(calendar.getTime()) + "' and online.ishelper=1 ");
      } else if (databaseType.indexOf("oracle") >= 0) {
        rs = stmt.executeQuery("select online_user.USER_ID from sec_onlineuser online_user where online_user.USER_UPDATETIME < TO_DATE('" + format.format(calendar.getTime()) + "', 'YYYY-MM-DD HH24:MI:SS') and online_user.ishelper=1");
      } 
      while (rs.next()) {
        empId = rs.getString(1);
        LogonBD logonBD = new LogonBD();
        MessagesBD messagesBD = new MessagesBD();
        logonBD.delForVb(empId);
        messagesBD.setMessageStatus(empId, "0");
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (rs != null)
        try {
          rs.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }  
      if (stmt != null)
        try {
          stmt.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }  
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }  
    } 
  }
}
