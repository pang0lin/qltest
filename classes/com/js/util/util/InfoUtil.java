package com.js.util.util;

import java.io.ByteArrayInputStream;
import java.io.Writer;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InfoUtil {
  public static void insertInfoContent_oracle(Long infoId, String content) {
    insert_oracle_clob("oa_information", "informationContent", "information_id", infoId, content);
  }
  
  public static void insert_oracle_clob(String tableName, String columnName, String idName, Long idValue, String content) {
    Connection conn = null;
    CallableStatement proc = null;
    PreparedStatement ps = null;
    try {
      String sql = "select " + columnName + " from " + tableName + " where " + idName + " = " + idValue + " for update";
      conn = (new DataSourceBase()).getDataSource().getConnection();
      ps = conn.prepareStatement("update " + tableName + " set " + columnName + " = empty_clob() where " + idName + "=" + idValue);
      ps.execute();
      String call = "{call JSDB.UPDATE_CLOB_CONTENT(?,?)}";
      String temp = "";
      do {
        if (content.length() > 4000) {
          temp = content.substring(0, 4000);
          content = content.substring(4000);
        } else {
          temp = content;
          content = "";
        } 
        proc = conn.prepareCall(call);
        proc.setObject(1, sql);
        proc.setObject(2, temp);
        proc.execute();
      } while (content.length() > 0);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (ps != null)
          ps.close(); 
        if (proc != null)
          proc.close(); 
        if (conn != null)
          conn.close(); 
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
  }
  
  public static void insert_oracle_blob(String tableName, String columnName, String idName, Long idValue, byte[] content) {
    Connection conn = null;
    PreparedStatement ps = null;
    if (content == null)
      return; 
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      ByteArrayInputStream is = new ByteArrayInputStream(content);
      ps = conn.prepareStatement("update " + tableName + " set " + columnName + "=? where " + idName + "=?");
      ps.setObject(1, is);
      ps.setObject(2, idValue);
      ps.execute();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (ps != null)
          ps.close(); 
        if (conn != null)
          conn.close(); 
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
  }
  
  public static byte[] read_oracle_blob(String tableName, String columnName, String idName, Long idValue) {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    byte[] result = (byte[])null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      ps = conn.prepareStatement("select " + columnName + " from " + tableName + " where " + idName + "=?");
      ps.setObject(1, idValue);
      rs = ps.executeQuery();
      if (rs.next())
        result = rs.getBytes(columnName); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (ps != null)
          ps.close(); 
        if (conn != null)
          conn.close(); 
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return result;
  }
  
  public static Clob oracleStr2Clob(String str, Clob lob) throws Exception {
    Method methodToInvoke = lob.getClass().getMethod(
        "getCharacterOutputStream", null);
    Writer writer = (Writer)methodToInvoke.invoke(lob, null);
    writer.write(str);
    writer.close();
    return lob;
  }
  
  public static void insert_mysql_longtext(String tableName, String columnName, String idName, Long idValue, String content) {
    String sql = "";
    Connection conn = null;
    PreparedStatement pstmt = null;
    sql = "update " + tableName + " set " + columnName + "=? where " + idName + "=" + idValue;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, content);
      pstmt.executeUpdate();
      pstmt.close();
      conn.close();
      System.gc();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
  }
}
