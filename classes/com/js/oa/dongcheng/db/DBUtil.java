package com.js.oa.dongcheng.db;

import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {
  public static Connection getConn() {
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
    } catch (SQLException e) {
      e.printStackTrace();
    } 
    return conn;
  }
  
  public static void closeAll(ResultSet rs, PreparedStatement ps, Connection conn) {
    try {
      if (rs != null)
        rs.close(); 
      if (ps != null)
        ps.close(); 
      if (conn != null)
        conn.close(); 
    } catch (SQLException e) {
      e.printStackTrace();
    } 
  }
}
