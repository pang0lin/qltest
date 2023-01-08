package com.js.oa.userdb.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class DbConnection {
  private Connection m_conn = null;
  
  private Statement m_stmt = null;
  
  private ExecuteUpdate m_update = null;
  
  protected abstract Connection openConnection() throws Exception;
  
  public Connection getConnection() throws Exception {
    if (this.m_conn == null)
      this.m_conn = openConnection(); 
    return this.m_conn;
  }
  
  public void createStatement() throws SQLException, Exception {
    if (this.m_stmt == null)
      this.m_stmt = getConnection().createStatement(); 
  }
  
  public void createScrollStatement() throws SQLException, Exception {
    this.m_stmt = getConnection().createStatement(1004, 
        
        1007);
  }
  
  public Statement getStatement() throws SQLException, Exception {
    if (this.m_stmt == null)
      createStatement(); 
    return this.m_stmt;
  }
  
  public int executeUpdate(String strSql) throws SQLException, Exception {
    return getStatement().executeUpdate(strSql);
  }
  
  public int executePSUpdate(String sql, String[] para) {
    int res = 0;
    try {
      PreparedStatement pstmt = getConnection().prepareStatement(sql);
      for (int i = 0; i < para.length; i++)
        pstmt.setString(i + 1, para[i]); 
      res = pstmt.executeUpdate();
      pstmt.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return res;
  }
  
  public int executePSUpdate(String table, String executeType, String[][] para, String where) {
    int res = -1;
    int paraLen = 0;
    StringBuffer sqlBuffer = new StringBuffer();
    if ("insert".equals(executeType)) {
      sqlBuffer.append("insert into ").append(table).append("(");
      int i;
      for (i = 0; i < para.length; i++) {
        if (para[i][0] != null) {
          sqlBuffer.append(para[i][1]).append(",");
          paraLen++;
        } 
      } 
      sqlBuffer.deleteCharAt(sqlBuffer.length() - 1);
      sqlBuffer.append(") values(");
      for (i = 0; i < paraLen; i++)
        sqlBuffer.append("?,"); 
      sqlBuffer.deleteCharAt(sqlBuffer.length() - 1);
      sqlBuffer.append(")");
    } else {
      sqlBuffer.append("update ").append(table).append(" set ");
      for (int i = 0; i < para.length; i++) {
        if (para[i][0] != null) {
          sqlBuffer.append(para[i][1]).append("=?,");
          paraLen++;
        } 
      } 
      sqlBuffer.deleteCharAt(sqlBuffer.length() - 1);
      sqlBuffer.append(" where ").append(where);
    } 
    try {
      PreparedStatement pstmt = getConnection().prepareStatement(sqlBuffer.toString());
      paraLen = 0;
      for (int i = 0; i < para.length; i++) {
        if (para[i][0] != null) {
          paraLen++;
          if ("long".equals(para[i][0])) {
            pstmt.setLong(paraLen, Long.valueOf(para[i][2]).longValue());
          } else if ("string".equals(para[i][0])) {
            pstmt.setString(paraLen, para[i][2]);
          } 
        } 
      } 
      res = pstmt.executeUpdate();
      pstmt.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return res;
  }
  
  public int executePSUpdate(String table, String executeType, Map paraMap, String where) {
    int res = -1;
    int paraLen = 0;
    StringBuffer sqlBuffer = new StringBuffer();
    if ("insert".equals(executeType)) {
      sqlBuffer.append("insert into ").append(table).append("(");
      Iterator<Map.Entry> it = paraMap.entrySet().iterator();
      while (it.hasNext()) {
        Map.Entry entry = it.next();
        String field = entry.getKey().toString();
        sqlBuffer.append(field).append(",");
        paraLen++;
      } 
      sqlBuffer.deleteCharAt(sqlBuffer.length() - 1);
      sqlBuffer.append(") values(");
      for (int i = 0; i < paraLen; i++)
        sqlBuffer.append("?,"); 
      sqlBuffer.deleteCharAt(sqlBuffer.length() - 1);
      sqlBuffer.append(")");
    } else {
      sqlBuffer.append("update ").append(table).append(" set ");
      Iterator<Map.Entry> it = paraMap.entrySet().iterator();
      while (it.hasNext()) {
        Map.Entry entry = it.next();
        String field = entry.getKey().toString();
        sqlBuffer.append(field).append("=?,");
        paraLen++;
      } 
      sqlBuffer.deleteCharAt(sqlBuffer.length() - 1);
      sqlBuffer.append(" where ").append(where);
    } 
    try {
      PreparedStatement pstmt = getConnection().prepareStatement(sqlBuffer.toString());
      paraLen = 0;
      Iterator<Map.Entry> it = paraMap.entrySet().iterator();
      while (it.hasNext()) {
        Map.Entry entry = it.next();
        String field = entry.getKey().toString();
        paraLen++;
        pstmt.setString(paraLen, paraMap.get(field).toString());
      } 
      res = pstmt.executeUpdate();
      pstmt.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return res;
  }
  
  public ResultSet executeQuery(String strSql) throws SQLException, Exception {
    return getStatement().executeQuery(strSql);
  }
  
  public Map executeQueryToMap(String strSql) throws SQLException, Exception {
    return RS.toMap(executeQuery(strSql));
  }
  
  public Map executeQueryToMapPS(String strSql, String[] para) throws SQLException, Exception {
    Map map = null;
    try {
      PreparedStatement pstmt = getConnection().prepareStatement(strSql);
      for (int i = 0; i < para.length; i++)
        pstmt.setString(i + 1, para[i]); 
      map = RS.toMap(pstmt.executeQuery());
      pstmt.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return map;
  }
  
  public StringMap executeQueryToStrMap(String strSql) throws SQLException, Exception {
    return RS.toStrMap(executeQuery(strSql));
  }
  
  public StringMap executeQueryToStrMapPS(String strSql, String[] para) throws SQLException, Exception {
    StringMap strMap = null;
    try {
      PreparedStatement pstmt = getConnection().prepareStatement(strSql);
      for (int i = 0; i < para.length; i++)
        pstmt.setString(i + 1, para[i]); 
      strMap = RS.toStrMap(pstmt.executeQuery());
      pstmt.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return strMap;
  }
  
  public Map[] executeQueryToMaps(String strSql) throws SQLException, Exception {
    return RS.toMaps(executeQuery(strSql));
  }
  
  public Map[] executeQueryToMapsPS(String strSql, String[] para) throws SQLException, Exception {
    Map[] map = (Map[])null;
    try {
      PreparedStatement pstmt = getConnection().prepareStatement(strSql);
      for (int i = 0; i < para.length; i++)
        pstmt.setString(i + 1, para[i]); 
      map = RS.toMaps(pstmt.executeQuery());
      pstmt.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return map;
  }
  
  public List executeQueryToList(String strSql) throws SQLException, Exception {
    return RS.toList(executeQuery(strSql));
  }
  
  public List executeQueryToListPS(String strSql, String[] para) throws SQLException, Exception {
    List list = null;
    try {
      PreparedStatement pstmt = getConnection().prepareStatement(strSql);
      for (int i = 0; i < para.length; i++)
        pstmt.setString(i + 1, para[i]); 
      list = RS.toList(pstmt.executeQuery());
      pstmt.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return list;
  }
  
  public String executeQueryToStr(String strSql) throws SQLException, Exception {
    return RS.toStr(executeQuery(strSql));
  }
  
  public String executeQueryToStrPS(String strSql, String[] para) throws SQLException, Exception {
    String str = null;
    try {
      PreparedStatement pstmt = getConnection().prepareStatement(strSql);
      for (int i = 0; i < para.length; i++)
        pstmt.setString(i + 1, para[i]); 
      str = RS.toStr(pstmt.executeQuery());
      pstmt.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return str;
  }
  
  public String[] executeQueryToStrArr1(String strSql) throws SQLException, Exception {
    return RS.toStrArr1(executeQuery(strSql));
  }
  
  public String[] executeQueryToStrArr1PS(String strSql, String[] para) throws SQLException, Exception {
    String[] str = (String[])null;
    try {
      PreparedStatement pstmt = getConnection().prepareStatement(strSql);
      for (int i = 0; i < para.length; i++)
        pstmt.setString(i + 1, para[i]); 
      str = RS.toStrArr1(pstmt.executeQuery());
      pstmt.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return str;
  }
  
  public String[][] executeQueryToStrArr2(String strSql) throws SQLException, Exception {
    return RS.toStrArr2(executeQuery(strSql));
  }
  
  public String[][] executeQueryToStrArr2PS(String strSql, String[] para) throws SQLException, Exception {
    String[][] str = (String[][])null;
    try {
      PreparedStatement pstmt = getConnection().prepareStatement(strSql);
      for (int i = 0; i < para.length; i++)
        pstmt.setString(i + 1, para[i]); 
      str = RS.toStrArr2(pstmt.executeQuery());
      pstmt.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return str;
  }
  
  public String[][] executeQueryToStrArr2(String strSql, int columnCount) throws SQLException, Exception {
    if (executeQuery(strSql).next())
      return RS.toStrArr2(executeQuery(strSql), columnCount); 
    return null;
  }
  
  public String[][] executeQueryToStrArr2PS(String strSql, int columnCount, String[] para) throws SQLException, Exception {
    String[][] str = (String[][])null;
    try {
      PreparedStatement pstmt = getConnection().prepareStatement(strSql);
      for (int i = 0; i < para.length; i++)
        pstmt.setString(i + 1, para[i]); 
      str = RS.toStrArr2(pstmt.executeQuery(), columnCount);
      pstmt.close();
    } catch (Exception ex) {
      str = (String[][])null;
      ex.printStackTrace();
    } 
    return str;
  }
  
  public ExecuteUpdate getUpdate() throws SQLException, Exception {
    if (this.m_update == null)
      this.m_update = new ExecuteUpdate(getStatement()); 
    return this.m_update;
  }
  
  public void setAutoCommit(boolean autoCommit) throws SQLException, Exception {
    getConnection().setAutoCommit(autoCommit);
  }
  
  public void commit() throws SQLException, Exception {
    getConnection().commit();
  }
  
  public void rollback() throws SQLException, Exception {
    getConnection().rollback();
  }
  
  public void closeConnection() throws SQLException {
    if (this.m_conn != null)
      this.m_conn.close(); 
  }
  
  public void closeStatement() throws SQLException {
    if (this.m_stmt != null)
      this.m_stmt.close(); 
  }
  
  public void close() throws SQLException {
    closeStatement();
    closeConnection();
    this.m_conn = null;
    this.m_stmt = null;
  }
}
