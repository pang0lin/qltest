package com.js.oa.form.pengchi;

import com.js.oa.form.Workflow;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

public class WorkflowForGys extends Workflow {
  public String complete(HttpServletRequest request) {
    HttpSession se = request.getSession(true);
    String userAccount = se.getAttribute("userAccount").toString();
    String result = super.complete(request);
    String recordId = request.getParameter("recordId");
    String sql1 = "Select pgbh,hh From kf_gyspjbmx1 Where kf_gyspjbmx1_foreignkey=?";
    String sql2 = "Select jhdf From kf_gyspjbmx2 Where kf_gyspjbmx2_foreignkey=?";
    String sql3 = "Select pzdf,fwdf,zf,pjjl,fpbl,bz From kf_gyspjbmx3 Where kf_gyspjbmx3_foreignkey=?";
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource();
    Map<String, String> resultData = new HashMap<String, String>();
    Connection conn1 = null;
    PreparedStatement pstmt1 = null;
    ResultSet rs1 = null;
    ResultSet rs2 = null;
    ResultSet rs3 = null;
    try {
      conn1 = ds.getConnection();
      pstmt1 = conn1.prepareStatement(sql1);
      pstmt1.setString(1, recordId);
      rs1 = pstmt1.executeQuery();
      while (rs1.next()) {
        resultData.put("pgbh", rs1.getString("pgbh"));
        resultData.put("hh", rs1.getString("hh"));
      } 
      rs1.close();
      pstmt1.close();
      pstmt1 = conn1.prepareStatement(sql2);
      pstmt1.setString(1, recordId);
      rs2 = pstmt1.executeQuery();
      while (rs2.next())
        resultData.put("jhdf", rs2.getString("jhdf")); 
      rs2.close();
      pstmt1.close();
      pstmt1 = conn1.prepareStatement(sql3);
      pstmt1.setString(1, recordId);
      rs3 = pstmt1.executeQuery();
      while (rs3.next()) {
        String pjjl = rs3.getString("pjjl");
        if (pjjl.equals("") || pjjl.length() == 0)
          pjjl = " "; 
        String bz = rs3.getString("bz");
        if (bz.equals("") || bz.length() == 0)
          bz = " "; 
        resultData.put("pzdf", rs3.getString("pzdf"));
        resultData.put("fwdf", rs3.getString("fwdf"));
        resultData.put("zf", rs3.getString("zf"));
        resultData.put("pjjl", pjjl);
        resultData.put("fpbl", rs3.getString("fpbl"));
        resultData.put("bz", bz);
      } 
      rs3.close();
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
    SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
    String sql4 = "update CPRDTA.FE64311 set SEECV3=? ,SEECV7=?,SEECV8=?,SEAMRC=?,SEFRGD=?,SEVSPC=?,SEDL01=?, SEUSER='" + userAccount + "',SEUPMJ=?,SETDAY=?,SEPID='OA',SEEV05='Y' where SEECO='" + (String)resultData.get("pgbh") + "' and SELNID=?";
    try {
      conn2 = ds2.getConnection();
      pstmt2 = conn2.prepareStatement(sql4);
      pstmt2.setLong(1, Long.valueOf(resultData.get("jhdf")).longValue());
      pstmt2.setLong(2, Long.valueOf(resultData.get("pzdf")).longValue());
      pstmt2.setLong(3, Long.valueOf(resultData.get("fwdf")).longValue());
      pstmt2.setLong(4, Long.valueOf(resultData.get("zf")).longValue());
      pstmt2.setString(5, resultData.get("pjjl"));
      pstmt2.setString(6, resultData.get("fpbl"));
      pstmt2.setString(7, resultData.get("bz"));
      pstmt2.setLong(8, Long.valueOf(dayCount).longValue());
      pstmt2.setLong(9, Long.valueOf(sdf.format(new Date())).longValue());
      pstmt2.setString(10, resultData.get("hh"));
      int resultInt = pstmt2.executeUpdate();
      if (resultInt > 0) {
        System.out.println("-----(供应商评级表--FE64311)数据回写成功-----");
      } else {
        System.out.println("-----(供应商评级表--FE64311)数据回写失败-----");
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
}
