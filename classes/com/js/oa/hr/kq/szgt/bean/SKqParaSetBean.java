package com.js.oa.hr.kq.szgt.bean;

import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SKqParaSetBean {
  public String savePara(String paraTitle, String annualstart, String annualperiod, String sickannual, String leaveannual, String leaveinlieuannual, String userange, String userangeId, String corpId) {
    String sql, databaseType = SystemCommon.getDatabaseType();
    if (databaseType.indexOf("oracle") >= 0) {
      sql = "insert into skq_para (para_id,paraTitle,annualstart,annualperiod,sickannual,leaveannual,leaveinlieuannual,userange,userangeId,corp_id)  values(seq_skq.nextval,?,?,?,?,?,?,?,?,?)";
    } else {
      sql = "insert into skq_para (paraTitle,annualstart,annualperiod,sickannual,leaveannual,leaveinlieuannual,userange,userangeId,corp_id)  values(?,?,?,?,?,?,?,?,?)";
    } 
    Connection conn = null;
    try {
      DataSourceBase datasourcebase = new DataSourceBase();
      conn = datasourcebase.getDataSource().getConnection();
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setString(1, paraTitle);
      stmt.setString(2, annualstart);
      stmt.setString(3, annualperiod);
      stmt.setString(4, sickannual);
      stmt.setString(5, leaveannual);
      stmt.setString(6, leaveinlieuannual);
      stmt.setString(7, userange);
      stmt.setString(8, userangeId);
      stmt.setString(9, corpId);
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
  
  public String updatePara(String paraTitle, String annualstart, String annualperiod, String sickannual, String leaveannual, String leaveinlieuannual, String userange, String userangeId, String paraId) {
    String sql = "update skq_para set paraTitle=?,annualstart=?,annualperiod=?,sickannual=?,leaveannual=?,leaveinlieuannual=?,userange=?,userangeId=? where para_id=?";
    Connection conn = null;
    try {
      DataSourceBase datasourcebase = new DataSourceBase();
      conn = datasourcebase.getDataSource().getConnection();
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setString(1, paraTitle);
      stmt.setString(2, annualstart);
      stmt.setString(3, annualperiod);
      stmt.setString(4, sickannual);
      stmt.setString(5, leaveannual);
      stmt.setString(6, leaveinlieuannual);
      stmt.setString(7, userange);
      stmt.setString(8, userangeId);
      stmt.setString(9, paraId);
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
  
  public String[] getParas(String listId) {
    String[] paras = new String[8];
    String sql = "select paraTitle,annualstart,annualperiod,sickannual,leaveannual,leaveinlieuannual,userange,userangeId from skq_para where para_id=?";
    Connection conn = null;
    try {
      DataSourceBase datasourcebase = new DataSourceBase();
      conn = datasourcebase.getDataSource().getConnection();
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setString(1, listId);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        paras[0] = rs.getString(1);
        paras[1] = rs.getString(2);
        paras[2] = rs.getString(3);
        paras[3] = rs.getString(4);
        paras[4] = rs.getString(5);
        paras[5] = rs.getString(6);
        paras[6] = rs.getString(7);
        paras[7] = rs.getString(8);
      } 
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
    return paras;
  }
  
  public String deletePara(String listId) {
    String sql = "delete from skq_para where para_id=?";
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
