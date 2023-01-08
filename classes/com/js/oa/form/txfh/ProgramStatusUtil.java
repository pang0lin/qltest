package com.js.oa.form.txfh;

import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

public class ProgramStatusUtil {
  public void setProgramStatus() {
    List<Map<String, String>> datas = readStatus();
    if (datas != null && datas.size() > 0)
      for (Map<String, String> map : datas) {
        String pid = map.get("id");
        String dateStr = map.get("endDate");
        Date afterDate = getAfterDay(dateStr);
        String newStatus = adjustStatus(dateStr, afterDate);
        updateStatus(newStatus, pid);
      }  
  }
  
  public List<Map<String, String>> readStatus() {
    List<Map<String, String>> datas = new ArrayList<Map<String, String>>();
    String sql = "Select jst_3024_id,jst_3024_f3317 FROM jst_3024 where jst_3024_f3517='执行中'";
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        Map<String, String> result = new HashMap<String, String>();
        result.put("id", rs.getString("jst_3024_id"));
        result.put("endDate", rs.getString("jst_3024_f3317"));
        datas.add(result);
      } 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      try {
        if (conn != null)
          conn.close(); 
      } catch (Exception e2) {
        e2.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return datas;
  }
  
  public void updateStatus(String status, String pId) {
    String sql = "update jst_3024 set jst_3024_f3517=? where jst_3024_id=? ";
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource();
    Connection conn = null;
    PreparedStatement pstmt = null;
    try {
      conn = ds.getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, status);
      pstmt.setString(2, pId);
      int n = pstmt.executeUpdate();
      if (n > 0) {
        System.out.println("天下凤凰项目成功设置状态：[" + status + "]成功！");
      } else {
        System.out.println("天下凤凰项目设置状态：[" + status + "]失败！");
      } 
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      try {
        if (conn != null)
          conn.close(); 
      } catch (Exception e2) {
        e2.printStackTrace();
      } 
      e.printStackTrace();
    } 
  }
  
  public Date getAfterDay(String str) {
    SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
    Date endDate = null;
    try {
      endDate = dformat.parse(str);
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    Calendar cd = Calendar.getInstance();
    cd.setTime(endDate);
    cd.add(5, 30);
    Date dat = cd.getTime();
    return dat;
  }
  
  public String adjustStatus(String endStrDate, Date afterDate) {
    SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
    Date endDate = null;
    try {
      endDate = dformat.parse(endStrDate);
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    String status = "执行中";
    Date nowDate = new Date();
    if (nowDate.getTime() >= endDate.getTime() && afterDate.getTime() >= nowDate.getTime()) {
      status = "结算中";
    } else if (afterDate.getTime() < nowDate.getTime()) {
      status = "关闭";
    } 
    return status;
  }
  
  public static String checkLoan(String xmId, String jkje, String type) {
    String result = "";
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String sql = "select zj,yjk from view_loan_calc where xm=?";
    double zje = 0.0D;
    double yjk = 0.0D;
    double jkje1 = 0.0D;
    try {
      jkje1 = Double.parseDouble(jkje);
      conn = ds.getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, xmId);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        zje = rs.getDouble("zj");
        yjk = rs.getDouble("yjk");
      } 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      try {
        if (conn != null)
          conn.close(); 
      } catch (Exception e2) {
        e2.printStackTrace();
      } 
      e.printStackTrace();
    } 
    if (zje - yjk >= jkje1) {
      result = "1";
    } else if ("2".equals(type)) {
      result = "当前项目总可借款金额为" + zje + ";目前已借款" + yjk + ";剩余可借款" + (zje - yjk) + "已不满足本次借款" + jkje1 + "支付需求";
    } else {
      result = "当前项目剩余可借款" + (zje - yjk) + "已不满足本次借款" + jkje1 + "需求";
    } 
    return result;
  }
}
