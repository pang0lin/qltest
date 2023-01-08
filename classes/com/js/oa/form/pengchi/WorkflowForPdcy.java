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
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

public class WorkflowForPdcy extends Workflow {
  public String complete(HttpServletRequest request) {
    String result = super.complete(request);
    String recordId = request.getParameter("recordId");
    String tableId = request.getParameter("tableId");
    String sql = "Select bh,pdsm,fbcs,sqrq From kf_kcpdcysqd Where kf_kcpdcysqd_id=?";
    String commentSql = "SELECT dealwithemployeecomment FROM jsf_dealwith aa,jsf_dealwithcomment bb WHERE aa.wf_dealwith_id=bb.wf_dealwith_id AND databaserecord_id=? AND databasetable_id=?";
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String pddh = "";
    String pdsm = "";
    String fbcs = "";
    String sqrq = "";
    String finalResult = "";
    List<String> commentList = new ArrayList<String>();
    try {
      conn = ds.getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, recordId);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        pddh = rs.getString("bh");
        pdsm = rs.getString("pdsm");
        fbcs = rs.getString("fbcs");
        sqrq = rs.getString("sqrq");
      } 
      rs.close();
      pstmt.close();
      pstmt = conn.prepareStatement(commentSql);
      pstmt.setString(1, recordId);
      pstmt.setString(2, tableId);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        String comment = rs.getString("dealwithemployeecomment");
        commentList.add(comment);
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
    DataSource ds2 = (new DataSourceBase()).getDataSource("jdbc/pengchi");
    Connection conn2 = null;
    PreparedStatement pstmt2 = null;
    String nxtr = "40";
    if (finalResult != null && finalResult.equals("1"))
      nxtr = "60"; 
    SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
    String time = sdf.format(new Date());
    Calendar ca = Calendar.getInstance();
    String year = (new StringBuilder(String.valueOf(ca.get(1)))).toString();
    year = "1" + year.substring(2);
    String dayCount = (new StringBuilder(String.valueOf(ca.get(6)))).toString();
    String sql2 = "insert into PRODDTA.FE6OA04@jdedblink (RDUKID,RDCYNO,RDDL01,RDMCU,RDCYCS,RDCSDJ,RDEV01,RDUSER,RDPID,RDJOBN,RDUPMT,RDUPMJ,RDTDAY,RDDL02,RDDL03,RDDL04)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
    try {
      conn2 = ds2.getConnection();
      pstmt2 = conn2.prepareStatement(sql2);
      pstmt2.setLong(1, Long.valueOf(recordId).longValue());
      pstmt2.setLong(2, Long.valueOf(pddh).longValue());
      pstmt2.setString(3, pdsm);
      pstmt2.setString(4, fbcs);
      pstmt2.setString(5, nxtr);
      pstmt2.setString(6, sqrq);
      pstmt2.setString(7, "0");
      pstmt2.setString(8, "OA");
      pstmt2.setString(9, "OA");
      pstmt2.setString(10, "OA");
      pstmt2.setLong(11, Long.valueOf(time).longValue());
      pstmt2.setLong(12, Long.valueOf(String.valueOf(year) + dayCount).longValue());
      pstmt2.setLong(13, Long.valueOf(time).longValue());
      pstmt2.setString(14, commentList.get(0));
      pstmt2.setString(15, commentList.get(1));
      pstmt2.setString(16, commentList.get(2));
      int resultInt = pstmt2.executeUpdate();
      if (resultInt > 0)
        System.out.println("-----盘点差异中间(FE6OA04)数据回写成功-----"); 
      pstmt2.close();
      conn2.close();
    } catch (SQLException e) {
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
