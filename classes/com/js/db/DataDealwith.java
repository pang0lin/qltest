package com.js.db;

import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataDealwith {
  public void DataDealwith() {}
  
  public boolean createUserTableIndex() {
    boolean returnValue = false;
    Connection conn = null;
    List<String> list = new ArrayList<String>();
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT table_name FROM ttable");
      while (rs.next())
        list.add(rs.getString(1)); 
      rs.close();
      for (int i = 0; i < list.size(); i++) {
        try {
          stmt.executeUpdate("ALTER TABLE " + (String)list.get(i) + " ADD PRIMARY KEY (" + (String)list.get(i) + "_id)");
        } catch (Exception err) {
          System.out.println("table " + (String)list.get(i) + "建立索引失败: " + err.getMessage());
        } 
      } 
      stmt.close();
      conn.close();
      returnValue = true;
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return returnValue;
  }
  
  public boolean cleanFlowData() {
    boolean returnValue = false;
    Connection conn = null;
    List<String> list = new ArrayList<String>();
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String sql = "SELECT wf_proceedactivity_id FROM jsf_p_activity jp LEFT JOIN jsf_work jw ON  jp.trecord_id=jw.workrecord_id AND jp.ttable_id=jw.worktable_id WHERE jw.workstatus=-1";
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next())
        list.add(rs.getString(1)); 
      rs.close();
      System.out.print("开始删除jsf_p_activity...");
      PreparedStatement pstmt = conn.prepareStatement("delete from jsf_p_activity where wf_proceedactivity_id=?");
      for (int i = 0; i < list.size(); i++) {
        pstmt.setString(1, ((String)list.get(i)).toString());
        pstmt.execute();
      } 
      pstmt.cancel();
      pstmt.close();
      list.clear();
      System.out.println(" 完成！");
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    return returnValue;
  }
  
  private void deleteFlowTransData(int index) {
    Connection conn = null;
    List<String> list = new ArrayList<String>();
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String sql = "";
      System.out.print("开始删除第" + index + "批数据......");
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        sql = "SELECT trans_id FROM (SELECT wf_proceedtransition_id trans_id,pa.wf_proceedactivity_id FROM jsf_p_transition pt LEFT JOIN jsf_p_activity pa ON pt.wf_proceedactivity_id=pa.wf_proceedactivity_id) aaa WHERE aaa.wf_proceedactivity_id IS NULL limit 0,10000";
      } else if (databaseType.indexOf("oracle") >= 0) {
        sql = "select * from (select aaa.trans_id,rownum rn from (select wf_proceedtransition_id trans_id,pa.wf_proceedactivity_id from jsf_p_transition pt left join jsf_p_activity pa on pt.wf_proceedactivity_id=pa.wf_proceedactivity_id) aaa where wf_proceedactivity_id is null and rownum<=10000)  where rn>0";
      } 
      ResultSet rs = stmt.executeQuery(sql);
      int i = 0;
      while (rs.next())
        list.add(rs.getString(1)); 
      rs.close();
      stmt.close();
      PreparedStatement pstmt1 = conn.prepareStatement("delete from jsf_p_tr where wf_proceedtransition_id=?");
      PreparedStatement pstmt2 = conn.prepareStatement("delete from jsf_p_transition where wf_proceedtransition_id=?");
      for (i = 0; i < list.size(); i++) {
        pstmt1.setString(1, ((String)list.get(i)).toString());
        pstmt1.execute();
        pstmt2.setString(1, ((String)list.get(i)).toString());
        pstmt2.execute();
      } 
      pstmt1.close();
      pstmt2.close();
      System.out.println(" 完成！");
      list.clear();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
  }
  
  public static void main() {
    System.out.println("date:" + (new Date()).toLocaleString());
  }
}
