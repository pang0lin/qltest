package com.js.oa.routine.officeroom.util;

import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UpdateTask {
  public void updateOverDue(String timeString) {
    Connection conn = null;
    Statement stmt = null;
    List list = new ArrayList();
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      String dataType = SystemCommon.getDatabaseType();
      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String timeDate = dateFormat.format(new Date());
      String sql = "";
      if (dataType.indexOf("mysql") >= 0) {
        sql = "update  oa_office_use set applay_isoverdue='1' where VISIT_ENDTIME<'" + timeDate + "' and applay_class='1' ";
      } else {
        sql = "update  oa_office_use set applay_isoverdue='1' where VISIT_ENDTIME<to_date('" + timeDate + "','yyyy-mm-dd hh24:mi:ss') and applay_class='1'  ";
      } 
      stmt.executeUpdate(sql);
      stmt.close();
      conn.close();
    } catch (Exception e) {
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
      e.printStackTrace();
    } 
  }
}
