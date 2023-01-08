package com.js.oa.jsflow.bean;

import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class WorkflowBean {
  public boolean insertColumn(String workflowTable, String archivesTable, String[] archivesColumn, String[] workflowColumn) {
    boolean isSuccess = false;
    Connection conn = null;
    Statement stmt = null;
    String sql = "";
    String sql_delete = "";
    String databaseType = 
      SystemCommon.getDatabaseType();
    try {
      if (isConfig(workflowTable, archivesTable))
        sql_delete = "delete from  jsf_archives_link where table_name='" + workflowTable + "' and archives_table_name='" + archivesTable + "'"; 
      conn = (new DataSourceBase()).getDataSource().getConnection();
      boolean status = conn.getAutoCommit();
      conn.setAutoCommit(false);
      stmt = conn.createStatement();
      if (!"".equals(sql_delete))
        stmt.executeUpdate(sql_delete); 
      for (int i = 0; archivesColumn != null && i < archivesColumn.length; i++) {
        if (databaseType.indexOf("oracle") >= 0) {
          sql = "insert into jsf_archives_link values(hibernate_sequence.nextval,'" + workflowTable + "','" + archivesColumn[i] + "','" + workflowColumn[i] + "','" + archivesTable + "')";
        } else if (databaseType.indexOf("mysql") >= 0) {
          sql = "insert into jsf_archives_link(table_name,oa_column_name,archives_column_name,archives_table_name) values ('" + workflowTable + "','" + archivesColumn[i] + "','" + workflowColumn[i] + "','" + archivesTable + "')";
        } 
        stmt.executeUpdate(sql);
      } 
      conn.commit();
      isSuccess = true;
      conn.setAutoCommit(status);
      stmt.close();
    } catch (Exception e) {
      try {
        conn.rollback();
        isSuccess = false;
      } catch (SQLException e1) {
        e1.printStackTrace();
      } 
      e.printStackTrace();
    } finally {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException err) {
          err.printStackTrace();
        }  
    } 
    return isSuccess;
  }
  
  public boolean isConfig(String workflowTable, String archivesTable) {
    boolean result = true;
    Connection conn = null;
    Statement stmt = null;
    String sql = "select * from jsf_archives_link where table_name='" + workflowTable + "' and archives_table_name='" + archivesTable + "'";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      if (rs.next()) {
        result = true;
      } else {
        result = false;
      } 
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException err) {
          err.printStackTrace();
        }  
    } 
    return result;
  }
}
