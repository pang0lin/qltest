package com.js.oa.form.pengchi;

import com.js.oa.form.Workflow;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

public class WorkflowForBfsq extends Workflow {
  public String complete(HttpServletRequest request) {
    String result = super.complete(request);
    String recordId = request.getParameter("recordId");
    String userAccount = request.getSession().getAttribute("userAccount").toString();
    String sql = "Select bflh,sqrq,sqbmbm,sl,dw,bfgx,cz ,CONCAT(YEAR(now())-1900,DATE_FORMAT(now(),'%j')) as today,DATE_FORMAT(NOW(),'%H%i%s') as time From kf_bfsqd Where kf_bfsqd_id=?";
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String bflh = "";
    long ddrq = 0L;
    long sl = 0L;
    String dw = "";
    String bfgx = "";
    String cz = "";
    String sqbmbm = "";
    long gxrq = 0L;
    long gxsj = 0L;
    try {
      conn = ds.getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, recordId);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        bflh = rs.getString("bflh");
        ddrq = rs.getLong("totay");
        sl = Long.valueOf(String.valueOf(rs.getString("sl")) + "0000").longValue();
        dw = rs.getString("dw");
        bfgx = rs.getString("bfgx");
        cz = rs.getString("cz");
        gxrq = rs.getLong("totay");
        gxsj = rs.getLong("time");
        sqbmbm = rs.getString("sqbmbm");
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
    PreparedStatement pstmt3 = null;
    ResultSet rs2 = null;
    ResultSet rs3 = null;
    ResultSet rs4 = null;
    String sql2 = "select IMITM,IMAITM,IMDSC1,IMDSC2,MCMCO from PRODDTA.F4101@jdedblink where IMLITM=? ";
    String dxmh = "";
    String dsxmh = "";
    String cpsm = "";
    String cpgg = "";
    String gsbm = "";
    String fqrbm = "";
    String sql3 = "select MCCO from PRODDTA.F0006@jdedblink where MCMCU=?";
    String sql4 = "select ULAN8 from PRODDTA.F0092@jdedblink where ULUSER=?";
    try {
      conn2 = ds2.getConnection();
      pstmt2 = conn2.prepareStatement(sql2);
      pstmt2.setString(1, bflh);
      rs2 = pstmt2.executeQuery();
      if (rs2.next()) {
        dxmh = rs2.getString("IMITM").trim();
        dsxmh = rs2.getString("IMAITM").trim();
        cpsm = rs2.getString("IMDSC1").trim();
        cpgg = rs2.getString("IMDSC2").trim();
      } 
      if (dxmh.equals(""))
        dxmh = "0"; 
      if (dsxmh.equals("") && dsxmh.length() == 0)
        dsxmh = " "; 
      pstmt2.close();
      rs2.close();
      pstmt2 = conn.prepareStatement(sql3);
      pstmt2.setString(1, sqbmbm);
      rs3 = pstmt2.executeQuery();
      if (rs3.next())
        gsbm = rs3.getString("MCCO").trim(); 
      pstmt2.close();
      rs3.close();
      pstmt2 = conn.prepareStatement(sql4);
      pstmt2.setString(1, userAccount);
      rs4 = pstmt2.executeQuery();
      if (rs4.next())
        fqrbm = rs4.getString("ULAN8").trim(); 
      pstmt2.close();
      rs4.close();
      String sql5 = "insert into PRODDTA.FE6OA01@jdedblink(MSUKID,MSDOCO,MSDCTO,MSKCOO,MSLNID,MSMMCU,MSMCU,MSAN8,MSRORN,MSRCTO,MSRKCO,MSITM,MSLITM,MSAITM,MSDSC1,MSDSC2,MSTRDJ,MSUORG,MSUOM,MSUSER,MSUPMJ,MSTDAY,MSPID,MSJOBN,MSURCD,MSURDT,MSURAT,MSURAB,MSURRF,MSDL02,MSDL03,MSDL04,MSDL05,MSADDJ,MSEFTJ,MSEXDJ,MSEV05,MSEV06,MSEV07,MSQTY1,MSQTY2,MSAN01,MSAN02,MSUNCS,MSLOTN,MSLOCN values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      pstmt3 = conn2.prepareStatement(sql5);
      pstmt3.setLong(1, Long.valueOf(recordId).longValue());
      pstmt3.setLong(2, 0L);
      pstmt3.setString(3, "I9");
      pstmt3.setString(4, gsbm);
      pstmt3.setInt(5, 1000);
      pstmt3.setString(6, sqbmbm);
      pstmt3.setString(7, " ");
      pstmt3.setString(8, fqrbm);
      pstmt3.setString(9, " ");
      pstmt3.setString(10, " ");
      pstmt3.setString(11, " ");
      pstmt3.setLong(12, Long.valueOf(dxmh).longValue());
      pstmt3.setString(13, bflh);
      pstmt3.setString(14, dsxmh);
      pstmt3.setString(15, cpsm);
      pstmt3.setString(16, cpgg);
      pstmt3.setLong(17, ddrq);
      pstmt3.setLong(18, sl);
      pstmt3.setString(19, dw);
      pstmt3.setString(20, userAccount);
      pstmt3.setLong(21, gxrq);
      pstmt3.setLong(22, gxsj);
      pstmt3.setString(23, "OA");
      pstmt3.setString(24, "OA");
      pstmt3.setString(25, " ");
      pstmt3.setInt(26, 0);
      pstmt3.setInt(27, 0);
      pstmt3.setInt(28, 0);
      pstmt3.setString(29, " ");
      pstmt3.setString(30, bfgx);
      pstmt3.setString(31, cz);
      pstmt3.setString(32, " ");
      pstmt3.setString(33, " ");
      pstmt3.setInt(34, 0);
      pstmt3.setInt(35, 0);
      pstmt3.setInt(36, 0);
      pstmt3.setString(37, "0");
      pstmt3.setString(38, " ");
      pstmt3.setString(39, " ");
      pstmt3.setInt(40, 0);
      pstmt3.setInt(41, 0);
      pstmt3.setInt(42, 0);
      pstmt3.setInt(43, 0);
      pstmt3.setInt(44, 0);
      pstmt3.setString(45, " ");
      pstmt3.setString(46, " ");
      boolean insertResult = pstmt2.execute();
      if (insertResult) {
        System.out.println("报废申请单写入数据成功");
      } else {
        System.out.println("报废申请单写入数据失败");
      } 
      pstmt3.close();
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
