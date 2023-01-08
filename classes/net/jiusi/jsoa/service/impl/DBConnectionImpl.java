package net.jiusi.jsoa.service.impl;

import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import net.jiusi.jsoa.service.IDBConnection;

public class DBConnectionImpl implements IDBConnection {
  public Connection getConnection() {
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
    } catch (SQLException e) {
      e.printStackTrace();
    } 
    return conn;
  }
  
  public void close(ResultSet rs, PreparedStatement pstmt, Statement stmt, Connection conn) {
    try {
      if (rs != null)
        rs.close(); 
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (pstmt != null)
          pstmt.close(); 
        if (stmt != null)
          stmt.close(); 
      } catch (SQLException e1) {
        e1.printStackTrace();
      } finally {
        try {
          if (conn != null)
            conn.close(); 
        } catch (SQLException e2) {
          e2.printStackTrace();
        } 
      } 
    } 
  }
}
