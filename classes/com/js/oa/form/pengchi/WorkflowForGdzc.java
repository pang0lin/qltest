package com.js.oa.form.pengchi;

import com.js.oa.form.Workflow;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

public class WorkflowForGdzc extends Workflow {
  public String complete(HttpServletRequest request) {
    String result = super.complete(request);
    String recordId = request.getParameter("recordId");
    String userAccount = request.getSession().getAttribute("userAccount").toString();
    String sql = "Select bh  From kf_gdzcbfsqd Where kf_gdzcbfsqd_id=?";
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String zcbh = "";
    String userName = "";
    try {
      conn = ds.getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, recordId);
      rs = pstmt.executeQuery();
      while (rs.next())
        zcbh = rs.getString("bh"); 
      rs.close();
      pstmt.close();
      String sql3 = "select empname from org_employee where useraccounts=?";
      pstmt = conn.prepareStatement(sql3);
      pstmt.setString(1, userAccount);
      rs = pstmt.executeQuery();
      while (rs.next())
        userName = rs.getString("empname"); 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (SQLException e1) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception e) {
          e.printStackTrace();
        }  
      e1.printStackTrace();
    } 
    DataSource ds2 = (new DataSourceBase()).getDataSource("jdbc/pengchi");
    Connection conn2 = null;
    PreparedStatement pstmt2 = null;
    Calendar ca = Calendar.getInstance();
    String year = (new StringBuilder(String.valueOf(ca.get(1)))).toString();
    year = "1" + year.substring(2);
    String dayCount = (new StringBuilder(String.valueOf(ca.get(6)))).toString();
    String sql2 = "update PRODDTA.FE6OA06@jdedblink set FAEQST='1S',FAAURDT1=? ,FADSC2=? where FANUMB=?";
    try {
      conn2 = ds2.getConnection();
      pstmt2 = conn2.prepareStatement(sql2);
      pstmt2.setString(1, String.valueOf(year) + dayCount);
      pstmt2.setString(2, userName);
      pstmt2.setString(3, zcbh);
      int resultInt = pstmt2.executeUpdate();
      if (resultInt > 0) {
        System.out.println("-----固定资产中间(FE6OA06)数据回写成功(审批结束)-----");
      } else {
        System.out.println("-----固定资产中间(FE6OA06)数据回写失败（审批结束）-----");
      } 
      pstmt2.close();
      conn2.close();
    } catch (Exception e) {
      if (conn2 != null)
        try {
          conn2.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return result;
  }
  
  public Map save(HttpServletRequest request) {
    Map result = super.save(request);
    String recordId = result.get("id").toString();
    String userAccount = request.getSession().getAttribute("userAccount").toString();
    if (!recordId.equals("0")) {
      Map<String, String> info = CreateProcessForGdzc.getInfo(recordId, userAccount);
      CreateProcessForGdzc.insertTable(info);
    } 
    return result;
  }
}
