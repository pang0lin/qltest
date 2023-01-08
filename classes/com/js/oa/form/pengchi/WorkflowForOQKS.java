package com.js.oa.form.pengchi;

import com.js.oa.form.Workflow;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

public class WorkflowForOQKS extends Workflow {
  public String complete(HttpServletRequest request) {
    String result = super.complete(request);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    String recordId = request.getParameter("recordId");
    String comment = request.getParameter("include_comment");
    String sql = "Select ukzj,ddgs,ddh,ddlx,ddhz From jst_3397 Where jst_3397_id=?";
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String ukIdForOq = "";
    try {
      conn = ds.getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, recordId);
      rs = pstmt.executeQuery();
      while (rs.next())
        ukIdForOq = rs.getString("ukzj"); 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception e1) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception e) {
          e.printStackTrace();
        }  
      e1.printStackTrace();
    } 
    String sql1 = "Select ukzj From jst_3396 Where jst_3396_foreignkey=?";
    List<String> ukIdForOqmx = new ArrayList<String>();
    Connection conn1 = null;
    PreparedStatement pstmt1 = null;
    ResultSet rs1 = null;
    try {
      conn1 = ds.getConnection();
      pstmt1 = conn1.prepareStatement(sql1);
      pstmt1.setString(1, recordId);
      rs1 = pstmt1.executeQuery();
      while (rs1.next())
        ukIdForOqmx.add(rs1.getString("ukzj")); 
      rs1.close();
      pstmt1.close();
      conn1.close();
    } catch (Exception e) {
      if (conn1 != null)
        try {
          conn1.close();
        } catch (Exception e2) {
          e2.printStackTrace();
        }  
    } 
    DataSource ds2 = (new DataSourceBase()).getDataSource("jdbc/pengchi");
    Connection conn2 = null;
    PreparedStatement pstmt2 = null;
    String nxtr = "120";
    Calendar ca = Calendar.getInstance();
    String year = (new StringBuilder(String.valueOf(ca.get(1)))).toString();
    year = "1" + year.substring(2);
    int today = ca.get(6);
    String dayCount = "";
    if (today < 10) {
      dayCount = "00" + today;
    } else if (today >= 10 && today < 100) {
      dayCount = "0" + today;
    } else {
      dayCount = (new StringBuilder(String.valueOf(today))).toString();
    } 
    String updateRemark = "OA修改状态为120,时间:" + sdf.format(new Date());
    String sql2 = "update PRODDTA.FE64311H@jdedblink set KHNXTR=? ,KHPOHP01=?,KHPOHP02=?,KHADDJ=?,KHDS50=?,KHDL17=? where KHUKID=?";
    try {
      conn2 = ds2.getConnection();
      pstmt2 = conn2.prepareStatement(sql2);
      pstmt2.setString(1, nxtr);
      pstmt2.setString(2, "1");
      pstmt2.setString(3, "N");
      pstmt2.setString(4, String.valueOf(year) + dayCount);
      pstmt2.setString(5, comment);
      pstmt2.setString(6, updateRemark);
      pstmt2.setString(7, ukIdForOq);
      int resultInt = pstmt2.executeUpdate();
      pstmt2.close();
      if (resultInt > 0) {
        System.out.println("-----中间表头(OQ)数据审批回写成功------UKID：【" + ukIdForOq + "】");
        if (ukIdForOqmx.size() > 0) {
          String sql3 = "update PRODDTA.FE64311D@jdedblink set KDPOHP01=1 where KDUKID=?";
          pstmt2 = conn2.prepareStatement(sql3);
          conn2.setAutoCommit(false);
          for (int i = 0; i < ukIdForOqmx.size(); i++) {
            pstmt2.setString(1, ukIdForOqmx.get(i));
            pstmt2.addBatch();
          } 
          pstmt2.executeBatch();
          conn2.commit();
          conn2.setAutoCommit(true);
          pstmt2.close();
        } 
      } else {
        System.out.println("-----中间表头(OQ)数据审批回写失败------UKID：【" + ukIdForOq + "】");
      } 
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
  
  public void back(HttpServletRequest request) {
    super.back(request);
    SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm");
    String recordId = request.getParameter("recordId");
    String dealTips = request.getParameter("dealTips");
    String sql = "Select ukzj,ddgs,ddh,ddlx,ddhz From jst_3397 Where jst_3397_id=?";
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String ukIdForOq = "";
    String oqddh = "";
    try {
      conn = ds.getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, recordId);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        ukIdForOq = rs.getString("ukzj");
        oqddh = rs.getString("ddh");
      } 
      rs.close();
      pstmt.close();
      String thsql = "update  jst_3397 set thbz=? where jst_3397_id=?";
      pstmt = conn.prepareStatement(thsql);
      pstmt.setString(1, "修改退回状态为100,订单号：【" + oqddh + "】,时间:[" + sdf.format(new Date()) + "],意见【" + dealTips + "】");
      pstmt.setString(2, recordId);
      pstmt.executeUpdate();
      pstmt.close();
      conn.close();
    } catch (Exception e1) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception e) {
          e.printStackTrace();
        }  
      e1.printStackTrace();
    } 
    String sql1 = "Select ukzj From jst_3396 Where jst_3396_foreignkey=?";
    List<String> ukIdForOqmx = new ArrayList<String>();
    Connection conn1 = null;
    PreparedStatement pstmt1 = null;
    ResultSet rs1 = null;
    try {
      conn1 = ds.getConnection();
      pstmt1 = conn1.prepareStatement(sql1);
      pstmt1.setString(1, recordId);
      rs1 = pstmt1.executeQuery();
      while (rs1.next())
        ukIdForOqmx.add(rs1.getString("ukzj")); 
      rs1.close();
      pstmt1.close();
      conn1.close();
    } catch (Exception e) {
      if (conn1 != null)
        try {
          conn1.close();
        } catch (Exception e2) {
          e2.printStackTrace();
        }  
    } 
    DataSource ds2 = (new DataSourceBase()).getDataSource("jdbc/pengchi");
    Connection conn2 = null;
    PreparedStatement pstmt2 = null;
    String nxtr = "100";
    Calendar ca = Calendar.getInstance();
    String year = (new StringBuilder(String.valueOf(ca.get(1)))).toString();
    year = "1" + year.substring(2);
    int today = ca.get(6);
    String dayCount = "";
    if (today < 10) {
      dayCount = "00" + today;
    } else if (today >= 10 && today < 100) {
      dayCount = "0" + today;
    } else {
      dayCount = (new StringBuilder(String.valueOf(today))).toString();
    } 
    String sql2 = "update PRODDTA.FE64311H@jdedblink set KHNXTR=? ,KHPOHP01=?,KHPOHP02=?,KHADDJ=?,KHDS50=?,KHDL17=? where KHUKID=?";
    String updateRemark = "OA修改状态为100,时间:" + sdf.format(new Date());
    try {
      conn2 = ds2.getConnection();
      pstmt2 = conn2.prepareStatement(sql2);
      pstmt2.setString(1, nxtr);
      pstmt2.setString(2, "2");
      pstmt2.setString(3, "N");
      pstmt2.setString(4, String.valueOf(year) + dayCount);
      pstmt2.setString(5, dealTips);
      pstmt2.setString(6, updateRemark);
      pstmt2.setString(7, ukIdForOq);
      int resultInt = pstmt2.executeUpdate();
      pstmt2.close();
      if (resultInt > 0) {
        System.out.println("-----中间表头(OQ)数据退回：回写成功--订单号：OQ【" + oqddh + "】");
        if (ukIdForOqmx.size() > 0) {
          String sql3 = "update PRODDTA.FE64311D@jdedblink set KDPOHP01=2 where KDUKID=?";
          pstmt2 = conn2.prepareStatement(sql3);
          conn2.setAutoCommit(false);
          for (int i = 0; i < ukIdForOqmx.size(); i++) {
            pstmt2.setString(1, ukIdForOqmx.get(i));
            pstmt2.addBatch();
          } 
          pstmt2.executeBatch();
          conn2.commit();
          conn2.setAutoCommit(true);
          pstmt2.close();
        } 
      } else {
        System.out.println("-----中间表头(OQ)数据退回：回写失败--订单号：OQ【" + oqddh + "】");
      } 
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
  }
}
