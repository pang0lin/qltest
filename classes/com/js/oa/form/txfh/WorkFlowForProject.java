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

public class WorkFlowForProject extends Workflow {
  public String complete(HttpServletRequest request) {
    String result = super.complete(request);
    String recordId = request.getParameter("recordId");
    StringBuffer mainSql = new StringBuffer();
    mainSql.append("insert into jst_3024(jst_3024_id,jst_3024_owner,jst_3024_org,jst_3024_f3297,jst_3024_f3298,jst_3024_f3299,jst_3024_f3300,jst_3024_f3301,jst_3024_f3302, ");
    mainSql.append("jst_3024_f3303,jst_3024_f3304,jst_3024_f3305,jst_3024_f3307,jst_3024_f3308,jst_3024_f3309,jst_3024_f3310,");
    mainSql.append("jst_3024_f3312,jst_3024_f3313,jst_3024_f3314,jst_3024_f3315,jst_3024_f3316,jst_3024_f3317,jst_3024_f3318,");
    mainSql.append("jst_3024_f3319,jst_3024_f3320,jst_3024_f3321,jst_3024_f3322,jst_3024_f3324,jst_3024_f3328,jst_3024_f3329,");
    mainSql.append("jst_3024_f3306,jst_3024_f3516,jst_3024_f3517,jst_3024_f3518,jst_3024_f3519,jst_3024_f3520,jst_3024_f3326,");
    mainSql.append("jst_3024_f3536,jst_3024_f3540 ) ");
    mainSql.append("select ?,jst_3012_owner,jst_3012_org,jst_3012_f3109,jst_3012_f3110,jst_3012_f3111,jst_3012_f3112,jst_3012_f3113,jst_3012_f3114,jst_3012_f3115,CONCAT(jst_3012_f3116,';',jst_3012_owner),CONCAT(jst_3012_f3117,';',jst_3012_org),jst_3012_f3118,jst_3012_f3119,");
    mainSql.append("jst_3012_f3120,jst_3012_f3121,jst_3012_f3123,jst_3012_f3124,jst_3012_f3125,jst_3012_f3126,jst_3012_f3127,jst_3012_f3128,jst_3012_f3129,jst_3012_f3130,jst_3012_f3131,jst_3012_f3132,jst_3012_f3133,");
    mainSql.append("jst_3012_f3134,jst_3012_f3135,jst_3012_f3136,jst_3012_f3156,jst_3012_f3501,jst_3012_f3502,jst_3012_f3503,jst_3012_f3504,jst_3012_f3505,jst_3012_f3506,jst_3012_f3416,jst_3012_f3538 ");
    mainSql.append("from jst_3012 where jst_3012_id=?");
    StringBuffer subSql = new StringBuffer();
    subSql.append("insert into jst_3025(jst_3025_id,jst_3025_owner,jst_3025_org,jst_3025_f3349,jst_3025_f3336,jst_3025_f3337,jst_3025_f3350,");
    subSql.append("jst_3025_f3341,jst_3025_f3343,jst_3025_f3521,jst_3025_f3342,jst_3025_f3352,jst_3025_f3346,jst_3025_f3345,");
    subSql.append("jst_3025_f3347,jst_3025_f3348,jst_3025_f3351,jst_3025_foreignkey,jst_3025_f3338) ");
    subSql.append("select jst_3013_id,jst_3013_owner,jst_3013_org,jst_3013_f3141,jst_3013_f3142,jst_3013_f3143,");
    subSql.append("jst_3013_f3145,jst_3013_f3146,jst_3013_f3148,jst_3013_f3149,jst_3013_f3150,jst_3013_f3151,");
    subSql.append("jst_3013_f3152,jst_3013_f3153,jst_3013_f3154,jst_3013_f3155,jst_3013_f3507,?,jst_3013_f3144 from jst_3013 where jst_3013_foreignkey=?");
    String psSql = "select DEALWITHEMPLOYEECOMMENT,DEALWITHTIME,COMMENTFIELD,(select CONCAT_WS('  ',orgname,empname) from org_employee a,org_organization_user b,org_organization c WHERE a.emp_id=b.emp_id and b.org_id=c.org_id and a.emp_id=DEALWITHEMPLOYEE_ID) as xm from jsf_dealwithcomment where WF_DEALWITH_ID in (select WF_DEALWITH_ID from jsf_dealwith where DATABASERECORD_ID=?)";
    String psUpdateSql = "update jst_3024 set jst_3024_f3331=?,jst_3024_f3332=?,jst_3024_f3333=?,jst_3024_f3334=? where jst_3024_id=?";
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource();
    Connection conn = null;
    PreparedStatement pstmt = null;
    try {
      conn = ds.getConnection();
      String pid = getTableId(conn);
      pstmt = conn.prepareStatement(mainSql.toString());
      pstmt.setString(1, pid);
      pstmt.setString(2, recordId);
      pstmt.executeUpdate();
      pstmt.close();
      pstmt = conn.prepareStatement(subSql.toString());
      pstmt.setString(1, pid);
      pstmt.setString(2, recordId);
      pstmt.executeUpdate();
      pstmt.close();
      pstmt = conn.prepareStatement(psSql);
      pstmt.setString(1, recordId);
      ResultSet rs = pstmt.executeQuery();
      Map<String, String> tmap = new HashMap<String, String>();
      while (rs.next()) {
        StringBuffer temp = new StringBuffer();
        temp.append("<div style=\"line-height: 1.4; font-size: 12px;\">" + rs.getString("DEALWITHEMPLOYEECOMMENT") + "</div>");
        temp.append("<div style=\"text-align: right; padding-right: 20px; font-size: 12px;\">" + rs.getString("xm") + "&nbsp;(" + rs.getString("DEALWITHTIME") + ")</div>");
        tmap.put(rs.getString("COMMENTFIELD"), temp.toString());
      } 
      rs.close();
      pstmt.close();
      pstmt = conn.prepareStatement(psUpdateSql);
      pstmt.setString(1, (tmap.get("jst_3012_f3137") == null) ? "" : tmap.get("jst_3012_f3137"));
      pstmt.setString(2, (tmap.get("jst_3012_f3138") == null) ? "" : tmap.get("jst_3012_f3138"));
      pstmt.setString(3, (tmap.get("jst_3012_f3139") == null) ? "" : tmap.get("jst_3012_f3139"));
      pstmt.setString(4, (tmap.get("jst_3012_f3140") == null) ? "" : tmap.get("jst_3012_f3140"));
      pstmt.setString(5, pid);
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
