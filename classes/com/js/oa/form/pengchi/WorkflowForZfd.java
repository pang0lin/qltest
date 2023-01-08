package com.js.oa.form.pengchi;

import com.js.oa.form.Workflow;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

public class WorkflowForZfd extends Workflow {
  public String complete(HttpServletRequest request) {
    String result = super.complete(request);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    String recordId = request.getParameter("recordId");
    List<Map<String, String>> listInfo = getKeyInfo(recordId);
    DataSource ds2 = (new DataSourceBase()).getDataSource("jdbc/pengchi");
    Connection conn2 = null;
    PreparedStatement pstmt2 = null;
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
    if (listInfo != null && listInfo.size() > 0)
      try {
        conn2 = ds2.getConnection();
        String sql2 = "update PRODDTA.FE6OA05@jdedblink set PIPST='A' ,PITRDJ=? where PIKCO=? and PIDCT=? and PIDOC=?";
        pstmt2 = conn2.prepareStatement(sql2);
        conn2.setAutoCommit(false);
        for (Map<String, String> zf : listInfo) {
          String sql3 = "update PRODDTA.FE6OA05@jdedblink set PIPST='A' ,PITRDJ=" + year + dayCount + " where PIKCO='" + (String)zf.get("gs") + "' and PIDCT='" + (String)zf.get("dct") + "' and PIDOC=" + (String)zf.get("doc");
          System.out.println("支付单结束回写SQL:" + sql3);
          pstmt2.setString(1, String.valueOf(year) + dayCount);
          pstmt2.setString(2, zf.get("gs"));
          pstmt2.setString(3, zf.get("dct"));
          pstmt2.setString(4, zf.get("doc"));
          pstmt2.addBatch();
        } 
        try {
          int[] num = pstmt2.executeBatch();
          conn2.commit();
          conn2.setAutoCommit(true);
          pstmt2.close();
          if (num.length == listInfo.size()) {
            System.out.println("-----支付单中间(FE6OA05)数据回写成功-----共" + num.length + "条");
          } else {
            System.out.println("---------支付单(FE6OA05)回写-----------" + num.length + "条");
          } 
        } catch (Exception e1) {
          e1.printStackTrace();
        } 
        conn2.close();
        System.out.println("支付单回写时间：【" + year + dayCount + "】-【" + sdf.format(new Date()) + "】流程记录：【" + recordId + "】");
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
    if (!recordId.equals("0")) {
      List<Map<String, String>> zfList = getKeyInfo(recordId);
      CreateProcessForZfd.updateTable(zfList);
    } 
    return result;
  }
  
  public static List<Map<String, String>> getKeyInfo(String recordId) {
    List<Map<String, String>> result = new ArrayList<Map<String, String>>();
    String sql = "SELECT gs,zfdh FROM kf_zfd_zfxx  WHERE kf_zfd_zfxx_FOREIGNKEY=?";
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, recordId);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        Map<String, String> info = new HashMap<String, String>();
        info.put("gs", rs.getString("gs"));
        info.put("dct", rs.getString("zfdh").substring(0, 2));
        info.put("doc", rs.getString("zfdh").substring(2));
        result.add(info);
      } 
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
    return result;
  }
}
