package com.js.oa.userdb.util;

import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import javax.sql.DataSource;

public class DbOpt extends DbConnection {
  public static String dbtype = "";
  
  private String datasourceName = "";
  
  public DbOpt() {
    dbtype = SystemCommon.getDatabaseType();
  }
  
  public DbOpt(String datasourceName) {
    this.datasourceName = datasourceName;
    dbtype = SystemCommon.getDatabaseType();
  }
  
  protected Connection openConnection() throws Exception {
    DataSource ds = null;
    Connection conn = null;
    if (!"".equals(this.datasourceName)) {
      ds = (new DataSourceBase()).getDataSource(this.datasourceName);
    } else {
      ds = (new DataSourceBase()).getDataSource();
    } 
    conn = ds.getConnection();
    return conn;
  }
  
  public Vector getHeaderName(ResultSet rs) {
    Vector<String> headers = new Vector();
    try {
      ResultSetMetaData rsmd = rs.getMetaData();
      int colcount = rsmd.getColumnCount();
      for (int i = 1; i <= colcount; i++)
        headers.add(rsmd.getColumnName(i)); 
    } catch (SQLException sqlex) {
      System.err.println("getHeaderName:" + sqlex.toString());
      return null;
    } 
    return headers;
  }
  
  public Vector getRows(ResultSet rs) {
    Vector<Vector<Object>> rows = new Vector();
    try {
      ResultSetMetaData rsmd = rs.getMetaData();
      int colcount = rsmd.getColumnCount();
      while (rs.next()) {
        Vector<Object> row = new Vector();
        for (int i = 1; i <= colcount; i++)
          row.add(rs.getObject(i)); 
        rows.add(row);
      } 
    } catch (SQLException sqlex) {
      System.err.println("getRows:" + sqlex.toString());
      return null;
    } 
    return rows;
  }
  
  public static void main(String[] args) throws SQLException, Exception {
    DbOpt dbConn = new DbOpt();
    String str_SQL = "select * from tModel";
  }
}
