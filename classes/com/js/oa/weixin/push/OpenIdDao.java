package com.js.oa.weixin.push;

import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;

public class OpenIdDao {
  private static Logger log = Logger.getLogger(OpenIdDao.class);
  
  private static final String SQL = "SELECT emp_weixin_open_id FROM org_employee WHERE emp_id=EMP_ID";
  
  public static String findOpenId(int id) {
    String openId = "";
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      String sql = "SELECT emp_weixin_open_id FROM org_employee WHERE emp_id=EMP_ID".replace("EMP_ID", Integer.toString(id));
      log.debug("sql = " + sql);
      rs = stmt.executeQuery(sql);
      if (rs.next())
        openId = rs.getString("emp_weixin_open_id"); 
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (rs != null)
        try {
          rs.close();
        } catch (SQLException sQLException) {} 
      if (stmt != null)
        try {
          stmt.close();
        } catch (SQLException sQLException) {} 
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException sQLException) {} 
    } 
    return openId;
  }
}
