package com.js.oa.form.pengchi;

import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.sql.DataSource;

public class GetHiStoryForOP {
  public static String getCkjInfo(String lh, String fbcs, String gys, String ddrq, String ddlx) {
    DataSource ds = (new DataSourceBase()).getDataSource("jdbc/pengchi");
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String ckj = "";
    int rq = Integer.valueOf(ddrq).intValue();
    try {
      conn = ds.getConnection();
      String sql2 = "select pdurrf from PRODDTA.F4311@Jdedblink where  TRIM(PDMCU)='" + fbcs + "'  AND TRIM(PDLITM)='" + lh + "' AND TRIM(PDAN8)='" + gys + "' and PDDCTO='" + ddlx + "' and PDTRDJ<" + rq + " and pdnxtr='400' and pdlttr<>'980' order by pdtrdj desc";
      pstmt = conn.prepareStatement(sql2);
      rs = pstmt.executeQuery();
      if (rs.next())
        ckj = rs.getString("pdurrf").trim(); 
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
    if (!ckj.equals(""))
      try {
        ckj = String.valueOf(Math.round(Float.valueOf(ckj).floatValue() * 100.0D) / 100.0D);
      } catch (Exception e) {
        e.printStackTrace();
      }  
    return ckj;
  }
}
