package com.js.oa.hr.kq.szgt.bean;

import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class KqDataImportBean {
  public List KqDataInsert(List<String[]> list, String savetype, int row) {
    String[] str = (String[])null;
    Connection conn = null;
    PreparedStatement stmt = null;
    List<Integer> record = new ArrayList();
    int kqidtemp = 0;
    String sql = "";
    int newInputNum = 0;
    int oldInputNum = 0;
    int badInputNum = 0;
    String databaseType = SystemCommon.getDatabaseType();
    if (databaseType.indexOf("oracle") >= 0) {
      sql = "insert into skq_record (rec_id,emp_id,org_id,rec_jobid,empName,rec_date,rec_onduty,rec_offduty,sign_onduty,sign_offduty,late_time,early_time) values(seq_skq.nextval,?,?,?,?,?,?,?,?,?,?,?)";
    } else {
      sql = "insert into skq_record (emp_id,org_id,rec_jobid,empName,rec_date,rec_onduty,rec_offduty,sign_onduty,sign_offduty,late_time,early_time) values(?,?,?,?,?,?,?,?,?,?,?)";
    } 
    DataSourceBase datasourcebase = new DataSourceBase();
    try {
      conn = datasourcebase.getDataSource().getConnection();
      stmt = conn.prepareStatement(sql);
      for (int i = 0; i < list.size(); i++) {
        str = list.get(i);
        stmt.setString(1, str[0]);
        stmt.setString(2, str[1]);
        stmt.setString(3, str[2]);
        stmt.setString(4, str[3]);
        stmt.setString(5, str[4]);
        stmt.setString(6, str[5]);
        stmt.setString(7, str[6]);
        stmt.setString(8, str[7]);
        stmt.setString(9, str[8]);
        stmt.setString(10, str[9]);
        stmt.setString(11, str[10]);
        kqidtemp = KqDataCheck(str[2], str[3], str[4]);
        if ("1".equals(savetype))
          if (kqidtemp != 0) {
            oldInputNum++;
          } else {
            stmt.executeUpdate();
            newInputNum++;
          }  
        if ("2".equals(savetype)) {
          if (kqidtemp != 0) {
            KqDataDelete(kqidtemp);
            oldInputNum++;
          } else {
            newInputNum++;
          } 
          stmt.executeUpdate();
        } 
      } 
      badInputNum = row - newInputNum - oldInputNum - 1;
      record.add(Integer.valueOf(newInputNum));
      record.add(Integer.valueOf(oldInputNum));
      record.add(Integer.valueOf(badInputNum));
    } catch (SQLException e) {
      e.printStackTrace();
    } 
    try {
      conn.close();
      stmt.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } 
    return record;
  }
  
  public int KqDataCheck(String kqnum, String kqName, String dkrq) {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    int kqid = 0;
    String sql = "";
    sql = "select rec_id from skq_record where rec_jobid='" + kqnum + "' and rec_date='" + dkrq + "' and empName='" + kqName + "'";
    DataSourceBase datasourcebase = new DataSourceBase();
    try {
      conn = datasourcebase.getDataSource().getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      while (rs.next())
        kqid = rs.getInt(1); 
    } catch (SQLException e) {
      e.printStackTrace();
    } 
    try {
      conn.close();
      stmt.close();
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } 
    return kqid;
  }
  
  public void KqDataDelete(int kqid) {
    Connection conn = null;
    Statement stmt = null;
    int n = 0;
    String sql = "";
    sql = "delete from skq_record where rec_id=" + kqid;
    DataSourceBase datasourcebase = new DataSourceBase();
    try {
      conn = datasourcebase.getDataSource().getConnection();
      stmt = conn.createStatement();
      n = stmt.executeUpdate(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    } 
    try {
      conn.close();
      stmt.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } 
  }
  
  public List<String> empIdCheck(String kqnum) {
    List<String> list = new ArrayList<String>();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String sql = "";
    sql = "select a.emp_id,b.org_id from org_employee a left join org_organization_user b on a.emp_id=b.emp_id left join org_organization c on b.org_id=c.org_id";
    sql = String.valueOf(sql) + " where a.empnumber='" + kqnum + "' ";
    DataSourceBase datasourcebase = new DataSourceBase();
    try {
      conn = datasourcebase.getDataSource().getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      while (rs.next()) {
        list.add(rs.getString(1));
        list.add(rs.getString(2));
      } 
    } catch (SQLException e) {
      e.printStackTrace();
    } 
    try {
      conn.close();
      stmt.close();
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } 
    return list;
  }
}
