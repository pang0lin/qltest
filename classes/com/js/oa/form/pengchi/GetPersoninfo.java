package com.js.oa.form.pengchi;

import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;

public class GetPersoninfo {
  public static final String sourceName = "jdbc/pengchi";
  
  public static Map<String, String> getOa(String useraccount) {
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    ResultSet rs1 = null;
    String empname = "";
    String empid = "";
    String orgname = "";
    Map<String, String> result = new HashMap<String, String>();
    String sql = "select empname,emp_id from org_employee where useraccounts=?";
    String sql1 = "SELECT orgname FROM org_organization aa,org_organization_user bb WHERE aa.org_id =bb.org_id AND bb.emp_id=?";
    try {
      conn = ds.getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, useraccount);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        empname = rs.getString("empname");
        empid = rs.getString("emp_id");
      } 
      rs.close();
      pstmt.close();
      pstmt = conn.prepareStatement(sql1);
      pstmt.setString(1, empid);
      rs1 = pstmt.executeQuery();
      while (rs1.next())
        orgname = rs1.getString("orgname"); 
      rs1.close();
      pstmt.close();
      conn.close();
      result.put("empname", empname);
      result.put("orgname", orgname);
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception e1) {
          e1.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return result;
  }
  
  public static String getOaUserId(String useraccount) {
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String empid = "";
    String sql = "select empname,emp_id from org_employee where useraccounts=?";
    try {
      conn = ds.getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, useraccount);
      rs = pstmt.executeQuery();
      while (rs.next())
        empid = rs.getString("emp_id"); 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception e1) {
          e1.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return empid;
  }
  
  public static String getOaUseraccount(String username) {
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String sql = "select useraccounts from org_employee where  empname=?";
    String useraccount = "";
    try {
      conn = ds.getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, username);
      rs = pstmt.executeQuery();
      if (rs.next())
        useraccount = rs.getString("useraccounts"); 
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception e1) {
          e1.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return useraccount;
  }
  
  public static String getDepartmentHead(String useraccount) {
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String leaderId = "";
    String leaderAccount = "";
    String sql = "select empLeaderId from org_employee where useraccounts=?";
    try {
      conn = ds.getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, useraccount);
      rs = pstmt.executeQuery();
      if (rs.next())
        leaderId = rs.getString(1); 
      pstmt.close();
      rs.close();
      if (!leaderId.equals("") && leaderId.length() > 0) {
        leaderId = leaderId.substring(1, leaderId.length() - 1);
        String sql1 = "select useraccounts from org_employee where emp_id=?";
        try {
          pstmt = conn.prepareStatement(sql1);
          pstmt.setString(1, leaderId);
          rs = pstmt.executeQuery();
          if (rs.next())
            leaderAccount = rs.getString(1); 
          pstmt.close();
          rs.close();
        } catch (Exception e) {
          e.printStackTrace();
        } 
      } 
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception e1) {
          e1.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return leaderAccount;
  }
  
  public static String getJdeZdr(String usernumb) {
    DataSource ds = (new DataSourceBase()).getDataSource("jdbc/pengchi");
    Connection conn = null;
    try {
      conn = ds.getConnection();
    } catch (Exception e1) {
      e1.printStackTrace();
    } 
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String userName = "";
    String sql = "select ABALPH from PRODDTA.F0092@jdedblink ,PRODDTA.F0101@jdedblink where ULAN8=ABAN8 and ULUSER='" + usernumb + "'";
    try {
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next())
        userName = rs.getString("ABALPH").trim(); 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return userName;
  }
  
  public static String getDwName(String para) {
    DataSource ds = (new DataSourceBase()).getDataSource("jdbc/pengchi");
    Connection conn = null;
    try {
      conn = ds.getConnection();
    } catch (Exception e1) {
      e1.printStackTrace();
    } 
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String dwname = "";
    String sql = "select DRDL01 from PRODCTL.F0005@jdedblink WHERE DRSY='00' AND DRRT='UM' AND TRIM(DRKY)=?";
    try {
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, para);
      rs = pstmt.executeQuery();
      while (rs.next())
        dwname = rs.getString("DRDL01").trim(); 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return dwname;
  }
  
  public static String getDwCode(String para) {
    DataSource ds = (new DataSourceBase()).getDataSource("jdbc/pengchi");
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String result = "";
    try {
      String sql = "select IMUOM1 from PRODDTA.F4101@jdedblink where TRIM(IMLITM)='" + para + "'";
      conn = ds.getConnection();
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next())
        result = rs.getString(1); 
      rs.close();
      pstmt.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return result;
  }
}
