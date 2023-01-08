package com.js.oa.form.txfh;

import com.js.oa.form.Workflow;
import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

public class WorkFlowForPayment extends Workflow {
  public String complete(HttpServletRequest request) {
    String result = super.complete(request);
    String recordId = request.getParameter("recordId");
    String sql = "SELECT jst_3014_f3157, jst_3014_f3162 ,jst_3014_f3173 from jst_3014 where jst_3014_id=?";
    String getSql = "SELECT jst_3025_id,jst_3025_f3348 FROM jst_3025 WHERE jst_3025_FOREIGNKEY = ? AND (jst_3025_f3337 = ? or jst_3025_f3338 = ?) order by jst_3025_id asc";
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Long pid = Long.valueOf(0L);
    String fkjl = "";
    try {
      conn = ds.getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, recordId);
      rs = pstmt.executeQuery();
      Map<String, String> map = new HashMap<String, String>();
      if (rs.next()) {
        map.put("jst_3014_f3157", rs.getString("jst_3014_f3157"));
        map.put("jst_3014_f3162", rs.getString("jst_3014_f3162"));
        map.put("jst_3014_f3173", rs.getString("jst_3014_f3173"));
      } 
      rs.close();
      pstmt.close();
      if (map.get("jst_3014_f3162") != null)
        if (((String)map.get("jst_3014_f3162")).indexOf("#") > -1) {
          String temp = map.get("jst_3014_f3162");
          temp = temp.substring(0, temp.indexOf("#"));
          map.put("jst_3014_f3162", temp);
        } else {
          map.put("jst_3014_f3162", "");
        }  
      if (map.get("jst_3014_f3157") != null && map.get("jst_3014_f3162") != null) {
        pstmt = conn.prepareStatement(getSql);
        pstmt.setString(1, map.get("jst_3014_f3157"));
        pstmt.setString(2, map.get("jst_3014_f3162"));
        pstmt.setString(3, map.get("jst_3014_f3162"));
        rs = pstmt.executeQuery();
        if (rs.next()) {
          pid = Long.valueOf(rs.getLong("jst_3025_id"));
          fkjl = rs.getString("jst_3025_f3348");
        } 
        rs.close();
        pstmt.close();
      } 
      if (pid.longValue() > 0L) {
        String fkjlTmp = map.get("jst_3014_f3173");
        if (!";".equals(fkjlTmp.substring(fkjlTmp.length() - 1)))
          fkjlTmp = String.valueOf(fkjlTmp) + ";"; 
        fkjl = String.valueOf(fkjlTmp) + fkjl;
        pstmt = conn.prepareStatement("update jst_3025 set jst_3025_f3348=? where jst_3025_id=?");
        pstmt.setString(1, fkjl);
        pstmt.setLong(2, pid.longValue());
        pstmt.executeUpdate();
        pstmt.close();
      } 
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
    return result;
  }
  
  private String getTableId(Connection conn) throws Exception {
    PreparedStatement pstmt = null;
    String seq = "0";
    if ("oracle".equals(SystemCommon.getDatabaseType())) {
      pstmt = conn.prepareStatement("select hibernate_sequence.nextval from dual");
      ResultSet rs = pstmt.executeQuery();
      if (rs.next())
        seq = rs.getString(1); 
      rs.close();
    } else {
      try {
        pstmt = conn.prepareStatement("select seq_seq from JSDB.oa_seq where id=1");
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
          seq = (new StringBuilder(String.valueOf(rs.getLong(1) + 1L))).toString();
          pstmt = conn.prepareStatement("update JSDB.oa_seq set seq_seq=seq_seq+1");
          pstmt.executeUpdate();
        } else {
          seq = "1000";
          pstmt = conn.prepareStatement("insert into JSDB.oa_seq (id, seq_seq) values (1, 1000)");
          pstmt.executeUpdate();
        } 
        pstmt.close();
        rs.close();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return seq;
  }
}
