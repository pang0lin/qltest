package com.js.oa.hr.kq.szgt.bean;

import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SKqWhitelistBean {
  public String saveWhitelist(String title, String listuser, String listuserId, String corpId) {
    String sql, databaseType = SystemCommon.getDatabaseType();
    if (databaseType.indexOf("oracle") >= 0) {
      sql = "insert into skq_whitelist (list_id,listtitle,listuser,listuserId,corp_id) values(seq_skq.nextval,?,?,?,?)";
    } else {
      sql = "insert into skq_whitelist (listtitle,listuser,listuserId,corp_id) values(?,?,?,?)";
    } 
    Connection conn = null;
    try {
      DataSourceBase datasourcebase = new DataSourceBase();
      conn = datasourcebase.getDataSource().getConnection();
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setString(1, title);
      stmt.setString(2, listuser);
      stmt.setString(3, listuserId);
      stmt.setString(4, corpId);
      stmt.execute();
      stmt.close();
      conn.close();
    } catch (SQLException e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return null;
  }
  
  public String updateWhitelist(String title, String listuser, String listuserId, String listId) {
    String sql = "update skq_whitelist set listtitle=?,listuser=?,listuserId=? where list_id=?";
    Connection conn = null;
    try {
      DataSourceBase datasourcebase = new DataSourceBase();
      conn = datasourcebase.getDataSource().getConnection();
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setString(1, title);
      stmt.setString(2, listuser);
      stmt.setString(3, listuserId);
      stmt.setString(4, listId);
      stmt.execute();
      stmt.close();
      conn.close();
    } catch (SQLException e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return null;
  }
  
  public String[] getWhitelist(String listId) {
    String[] list = new String[3];
    String sql = "select * from skq_whitelist where list_id=?";
    Connection conn = null;
    try {
      DataSourceBase datasourcebase = new DataSourceBase();
      conn = datasourcebase.getDataSource().getConnection();
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setString(1, listId);
      System.out.println("ddddddddddddd:" + listId);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        System.out.println("ddddddddddd222dd:" + listId);
        list[0] = rs.getString("listtitle");
        list[1] = rs.getString("listuser");
        list[2] = rs.getString("listuserId");
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (SQLException e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return list;
  }
  
  public String deleteWhitelist(String listId) {
    String sql = "delete from skq_whitelist where list_id=?";
    Connection conn = null;
    try {
      DataSourceBase datasourcebase = new DataSourceBase();
      conn = datasourcebase.getDataSource().getConnection();
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setString(1, listId);
      stmt.execute();
      stmt.close();
      conn.close();
    } catch (SQLException e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return null;
  }
}
