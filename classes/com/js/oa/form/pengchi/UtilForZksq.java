package com.js.oa.form.pengchi;

import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;

public class UtilForZksq {
  public static Map<String, String> getWLinfo(String lh) {
    DataSource ds = (new DataSourceBase()).getDataSource("jdbc/pengchi");
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Map<String, String> result = new HashMap<String, String>();
    try {
      String sql = "select TRIM(IMDSC1),TRIM(IMDSC2) from PRODDTA.F4101@jdedblink  where TRIM(IMLITM)='" + lh + "'";
      conn = ds.getConnection();
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        result.put("pm", rs.getString(1).trim());
        result.put("gg", rs.getString(2).trim());
      } 
      rs.close();
      pstmt.close();
      String sql2 = "select FPPRRC from PRODDTA.FE64201@jdedblink where TRIM(FPKY)='01' and CRPDTA.Dton (SYSDATE)>=FPEFTJ and  CRPDTA.Dton (SYSDATE) <=FPEXDJ and TRIM(FPLITM)='" + lh + "'";
      String mj = "";
      pstmt = conn.prepareStatement(sql2);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        mj = String.valueOf(rs.getLong(1) / 10.0D);
        result.put("mj", mj);
      } 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return result;
  }
  
  public static Map<String, String> getHl(String lh) {
    DataSource ds = (new DataSourceBase()).getDataSource("jdbc/pengchi");
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Map<String, String> result = new HashMap<String, String>();
    try {
      String sql = "select IMSRP2,DRDL01 from proddTA.F4101@Jdedblink ,PRODCTL.F0005@jdedblink  WHERE DRSY='41' AND DRRT='S2' AND TRIM(DRKY)=TRIM(IMSRP2) and trim(IMLITM)='" + lh + "'";
      conn = ds.getConnection();
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        result.put("hldm", rs.getString(1).trim());
        result.put("hlmc", rs.getString(2).trim());
      } 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return result;
  }
  
  public static String getZk(String hldm) {
    DataSource ds = (new DataSourceBase()).getDataSource("jdbc/pengchi");
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String result = "";
    try {
      String sql = "SELECT LDCDSA FROM proddTA.FE64202@Jdedblink WHERE  FE64202.LDEFTJ<=CRPDTA.DTON(SYSDATE) AND FE64202.LDEXDJ>=CRPDTA.DTON(SYSDATE) and TRIM(LDSRP2)='" + hldm + "'";
      conn = ds.getConnection();
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      if (rs.next())
        result = rs.getString(1); 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return result;
  }
}
