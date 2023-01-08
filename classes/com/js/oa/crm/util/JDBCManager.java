package com.js.oa.crm.util;

import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.sql.DataSource;

public class JDBCManager {
  public static void executeSQL(String sql) {
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      stmt.executeUpdate(sql);
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
  }
  
  public static void removeByTableAndId(String table, String id) {
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    try {
      String sql = "delete from " + table + " where id in(" + id + ")";
      conn = ds.getConnection();
      stmt = conn.createStatement();
      stmt.executeUpdate(sql);
      if (table.equals("cst_s"))
        stmt.executeUpdate("delete from cst_s_h where se_id in(" + id + ")"); 
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
  }
  
  public static int checkName(String table, String id, String name, String type) {
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    int selectCount = 0;
    try {
      String sql = "";
      if (id == null || id.equals("") || id.equals("null")) {
        sql = "select count(*) from " + table + " where name='" + name.trim() + "'";
      } else if (id != null && !id.equals("") && !id.equals("null")) {
        sql = "select count(*) from " + table + " where id<>" + id + " and name='" + name.trim() + "' ";
      } 
      if (type != null && !type.equals(""))
        sql = String.valueOf(sql) + " and type=" + type; 
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      if (rs.next())
        selectCount = rs.getInt(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return selectCount;
  }
  
  public static String checkName(String table, String where) {
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String selectCount = null;
    try {
      String sql = "select id from " + table + " where " + where + " ";
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      if (rs.next())
        selectCount = (new StringBuilder(String.valueOf(rs.getInt(1)))).toString(); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return selectCount;
  }
  
  public static String get(String select, String table, String where) {
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String selectCount = null;
    try {
      String sql = "select " + select + " from " + table + " where " + where + " ";
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      if (rs.next())
        selectCount = String.valueOf(rs.getInt(1)) + ";" + rs.getString(2); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return selectCount;
  }
  
  public static String getTemp(String select, String table, String where) {
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String selectCount = null;
    try {
      String sql = "select " + select + " from " + table + " where " + where + " ";
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      if (rs.next())
        selectCount = String.valueOf(rs.getInt(1)) + ";" + rs.getInt(2); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return selectCount;
  }
  
  public static String getNameByTableAndId(String table, String id) {
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String name = "";
    try {
      String sql = "select name from " + table + " where id=" + id;
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      if (rs.next())
        name = rs.getString(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return name;
  }
  
  public static String getProNameById(String id) {
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String name = "";
    try {
      String sql = "select title from pro_body where id=" + id;
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      if (rs.next())
        name = rs.getString(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return name;
  }
  
  public static String getInitLongById(String id) {
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String name = "";
    try {
      String sql = "select init_long from cst_type_n where id=" + id;
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      if (rs.next())
        name = String.valueOf(rs.getInt(1)); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return name;
  }
  
  public static void update(String sql) {
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      String[] sqls = sql.split(";");
      for (int i = 0; i < sqls.length; i++)
        stmt.executeUpdate(sqls[i]); 
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
  }
  
  public static String getIncream(String column, String table) {
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String name = "";
    try {
      String sql = "select " + column + " from " + table + " ";
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      if (rs.next())
        name = rs.getString(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return name;
  }
  
  public static String getCount(String head, String num) {
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String name = "";
    try {
      String sql = "select count(*),pro_name from cst_s where pro_num_head='" + head + "' and pro_num_incream='" + num + "'";
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      if (rs.next())
        name = String.valueOf(rs.getInt(1)) + ";" + rs.getString(2); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return name;
  }
  
  public static String getCstSell(String column) {
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    StringBuffer sb = new StringBuffer();
    try {
      String sql = "select cst_state,cst_state_name from cst_c where id=" + column;
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      if (rs.next())
        sb.append(rs.getString(1)).append(";").append(rs.getString(2)).append(";"); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return sb.toString();
  }
  
  public static boolean isDeptLeader(String userId, String orgId) {
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    boolean flag = false;
    try {
      String sql = "select count(*) from org_organization where ORGMANAGEREMPID like '%$" + userId + "$%' and org_id=" + orgId;
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      if (rs.next()) {
        int selectCount = rs.getInt(1);
        if (selectCount > 0)
          flag = true; 
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return flag;
  }
  
  public static boolean hasUserRange(String userId, String orgId, String menuName) {
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    boolean flag = false;
    try {
      String sql = "select count(*) from cst_type_v where name='" + menuName + "' and (user_range like '%$" + userId + "$%' or user_range like '%*" + orgId + "*%')";
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      if (rs.next()) {
        int selectCount = rs.getInt(1);
        if (selectCount > 0)
          flag = true; 
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return flag;
  }
  
  public static String getDateRange(String column, String menuName) {
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String name = "";
    try {
      String sql = "select org_range from cst_type_v where name='" + menuName + "' ";
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      if (rs.next()) {
        name = rs.getString(1);
        if (name != null && !name.equals("null") && !name.equals("")) {
          name = name.substring(name.indexOf("*") + 1, name.lastIndexOf("*"));
          name = name.replaceAll("\\*", ",").replaceAll(",,", ",");
        } else {
          name = "";
        } 
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return name;
  }
  
  public static String getUserRange(String column, String menuName) {
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String name = "";
    try {
      String sql = "select user_range from cst_type_v where name='" + menuName + "' ";
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      if (rs.next())
        name = rs.getString(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return name;
  }
  
  public static String getRelaProjectCrm(String column, String name) {
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    StringBuffer docs = new StringBuffer("");
    try {
      String sql1 = "select id from pro_body where title='" + name.trim() + "'";
      String sql2 = "select channel_id from oa_informationchannel where relproject_id=(" + sql1 + ")";
      String sql = "select informationtitle from oa_information where channel_id=(" + sql2 + ")";
      conn = ds.getConnection();
      stmt = conn.createStatement();
      if (column.equals("id")) {
        rs = stmt.executeQuery(sql1);
        if (rs.next())
          docs.append(rs.getInt(1)); 
      } 
      if (column.equals("title")) {
        rs = stmt.executeQuery(sql);
        while (rs.next())
          docs.append(rs.getString(1)).append(";"); 
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return docs.toString();
  }
}
