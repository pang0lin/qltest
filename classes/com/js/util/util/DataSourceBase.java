package com.js.util.util;

import com.js.util.config.SystemCommon;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DataSourceBase {
  private static String dataSourceName = "java:comp/env/jdbc/jsdb";
  
  private static String dataSourcePreName = "java:comp/env/";
  
  private String dataBaseType = "mysql";
  
  protected DataSource dataSource = null;
  
  protected Connection conn = null;
  
  protected Statement stmt = null;
  
  public static void setDataSourceName(String dataSourceName) {
    DataSourceBase.dataSourceName = dataSourceName;
  }
  
  public static void setDataSourcePreName(String dataSourcePreName) {
    DataSourceBase.dataSourcePreName = dataSourcePreName;
  }
  
  public void begin() throws Exception {
    try {
      Context ctx = new InitialContext();
      this.dataSource = (DataSource)ctx.lookup(dataSourceName);
      this.conn = this.dataSource.getConnection();
      setDataBaseType(this.conn.getMetaData().getDatabaseProductName()
          .toLowerCase());
      this.stmt = this.conn.createStatement();
    } catch (Exception e) {
      throw e;
    } 
  }
  
  public DataSource getDataSource() {
    try {
      Context ctx = new InitialContext();
      this.dataSource = (DataSource)ctx.lookup(dataSourceName);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return this.dataSource;
  }
  
  public DataSource getDataSource(String name) {
    try {
      Context ctx = new InitialContext();
      this.dataSource = (DataSource)ctx.lookup(String.valueOf(dataSourcePreName) + name);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return this.dataSource;
  }
  
  public void begin(String dataName) throws Exception {
    try {
      Context ctx = new InitialContext();
      this.dataSource = (DataSource)ctx.lookup(String.valueOf(dataSourcePreName) + dataName);
      this.conn = this.dataSource.getConnection();
      this.stmt = this.conn.createStatement();
    } catch (Exception e) {
      throw e;
    } 
  }
  
  public Connection getConnection() {
    return this.conn;
  }
  
  public void addBatch(String sql) {
    try {
      this.stmt.addBatch(sql);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  public int[] executeBatch() {
    try {
      return this.stmt.executeBatch();
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public void clearBatch() {
    try {
      this.stmt.clearBatch();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  public String getTableId() throws Exception {
    String seq = "0";
    if ("oracle".equals(SystemCommon.getDatabaseType())) {
      ResultSet rs = this.stmt
        .executeQuery("select hibernate_sequence.nextval from dual");
      if (rs.next())
        seq = rs.getString(1); 
      rs.close();
    } else {
      try {
        ResultSet rs = this.stmt
          .executeQuery("select seq_seq from JSDB.oa_seq where id=1");
        if (rs.next()) {
          seq = (new StringBuilder(String.valueOf(rs.getLong(1) + 1L))).toString();
          this.stmt.execute("update JSDB.oa_seq set seq_seq=seq_seq+1");
        } else {
          seq = "1000";
          this.stmt.execute("insert into JSDB.oa_seq (id, seq_seq) values (1, 1000)");
        } 
        rs.close();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return seq;
  }
  
  public String getTableId(int scan) throws Exception {
    String seq = "0";
    if ("oracle".equals(SystemCommon.getDatabaseType())) {
      synchronized (this) {
        ResultSet rs = this.stmt
          .executeQuery("select hibernate_sequence.nextval from dual");
        if (rs.next())
          seq = rs.getString(1); 
        rs.close();
        for (int i = 0; i < scan; i++)
          this.stmt.executeQuery("select hibernate_sequence.nextval from dual"); 
      } 
    } else {
      try {
        synchronized (this) {
          ResultSet rs = this.stmt
            .executeQuery("select seq_seq from JSDB.oa_seq where id=1");
          if (rs.next()) {
            seq = (new StringBuilder(String.valueOf(rs.getLong(1) + 1L))).toString();
            this.stmt.execute("update JSDB.oa_seq set seq_seq=seq_seq+" + scan);
          } else {
            seq = "1000";
            this.stmt.execute("insert into JSDB.oa_seq (id, seq_seq) values (1, 1000+" + scan + ")");
          } 
          rs.close();
        } 
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return seq;
  }
  
  public void end() {
    try {
      if (this.stmt != null)
        this.stmt.close(); 
      if (this.conn != null)
        this.conn.close(); 
    } catch (Exception e) {
      if (this.conn != null)
        try {
          this.conn.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }  
      e.printStackTrace();
    } 
  }
  
  public boolean executeSQL(String sql) {
    boolean result = false;
    try {
      result = this.stmt.execute(sql);
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
    } 
    return result;
  }
  
  public boolean executeSqlList(List<String> sqlList) {
    boolean result = true;
    try {
      for (String sql : sqlList)
        this.stmt.addBatch(sql); 
      this.stmt.executeBatch();
    } catch (Exception e) {
      e.printStackTrace();
      result = false;
    } 
    return result;
  }
  
  public ResultSet executeQuery(String sql) {
    try {
      return this.stmt.executeQuery(sql);
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public int executeUpdate(String sql) {
    try {
      return this.stmt.executeUpdate(sql);
    } catch (Exception ex) {
      ex.printStackTrace();
      return 0;
    } 
  }
  
  public String[][] getArrayQuery(String sql, int columnNum) {
    String[][] array = new String[0][0];
    List<String[]> list = (List)new ArrayList<String>();
    try {
      ResultSet resultSet = this.stmt.executeQuery(sql);
      ResultSetMetaData rsmd = resultSet.getMetaData();
      int columnCount = rsmd.getColumnCount();
      if (columnNum > columnCount || columnNum < 0)
        columnNum = columnCount; 
      while (resultSet.next()) {
        String[] rsValue = new String[columnCount];
        for (int j = 0; j < columnNum; j++)
          rsValue[j] = (new StringBuilder(String.valueOf(resultSet.getString(j + 1)))).toString(); 
        list.add(rsValue);
      } 
      resultSet.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    array = new String[list.size()][columnNum];
    for (int i = 0; i < list.size(); i++)
      array[i] = list.get(i); 
    return array;
  }
  
  public String[][] getArrayQuery(String sql) {
    return getArrayQuery(sql, -1);
  }
  
  public String[][] queryArrayBySql(String sql) {
    String[][] array = (String[][])null;
    try {
      begin();
      array = getArrayQuery(sql);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      end();
    } 
    return array;
  }
  
  public String[] queryBySql(String sql) {
    String[][] array = queryArrayBySql(sql);
    if (array.length < 1)
      return null; 
    return array[0];
  }
  
  public String queryStrBySql(String sql) {
    String[] array = queryBySql(sql);
    if (array != null && array.length == 1)
      return array[0]; 
    return "";
  }
  
  public int executeSqlUpdate(String sql) {
    int num = 0;
    try {
      begin();
      num = executeUpdate(sql);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      end();
    } 
    return num;
  }
  
  public String getDataBaseType() {
    return this.dataBaseType;
  }
  
  public void setDataBaseType(String dataBaseType) {
    this.dataBaseType = dataBaseType;
  }
}
