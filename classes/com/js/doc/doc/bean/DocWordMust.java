package com.js.doc.doc.bean;

import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocWordMust {
  public List<String[]> getfield(String dovtype) {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Connection conn = null;
    String sqlstr = " AND id NOT IN(7)";
    if ("1".equals(dovtype))
      sqlstr = " AND id NOT IN(47)"; 
    String sql = "SELECT gffdisplayname,gffname FROM form_initfield WHERE govformtype=?" + sqlstr;
    DataSourceBase base = new DataSourceBase();
    List<String[]> list = (List)new ArrayList<String>();
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, dovtype);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        list.add(new String[] { rs.getString(1), rs.getString(2) });
      } 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (SQLException e) {
      try {
        if (rs != null)
          rs.close(); 
        if (pstmt != null)
          pstmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (Exception e1) {
        e1.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return list;
  }
  
  public Map getMustWord(String tableid) {
    String sql = "SELECT mustword,govcheckfield FROM form_content WHERE govformid=?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Connection conn = null;
    String mustword = "";
    String govcheckfield = "";
    Map<Object, Object> map = new HashMap<Object, Object>();
    DataSourceBase base = new DataSourceBase();
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, tableid);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        mustword = rs.getString(1);
        if (mustword != null && !"".equals(mustword) && !"null".equals(mustword))
          mustword = mustword.substring(0, mustword.indexOf("$")); 
        govcheckfield = rs.getString(2);
        map.put("mustword", mustword);
        map.put("govcheckfield", govcheckfield);
      } 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (SQLException e) {
      try {
        if (rs != null)
          rs.close(); 
        if (pstmt != null)
          pstmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (Exception e1) {
        e1.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return map;
  }
  
  public String selectMyReceivefile(String sendFileId) {
    String result = "";
    String sql = "select * FROM doc_sendfile_user WHERE sendfile_id=?";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    DataSourceBase base = new DataSourceBase();
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, sendFileId);
      rs = pstmt.executeQuery();
      if (rs.next())
        result = "1"; 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (SQLException e) {
      try {
        if (rs != null)
          rs.close(); 
        if (pstmt != null)
          pstmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (SQLException e1) {
        e1.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return result;
  }
}
