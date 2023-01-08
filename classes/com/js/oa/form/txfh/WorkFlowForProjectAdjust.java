package com.js.oa.form.txfh;

import com.js.oa.form.Workflow;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

public class WorkFlowForProjectAdjust extends Workflow {
  public String complete(HttpServletRequest request) {
    String result = super.complete(request);
    String recordId = request.getParameter("recordId");
    StringBuffer mainGetSql = new StringBuffer();
    mainGetSql.append("select jst_3022_f3247,jst_3022_f3248,jst_3022_f3249,jst_3022_f3250,jst_3022_f3251,jst_3022_f3252,jst_3022_f3253,jst_3022_f3257,");
    mainGetSql.append("jst_3022_f3258,jst_3022_f3255,jst_3022_f3254,jst_3022_f3259,jst_3022_f3260,jst_3022_f3262,jst_3022_f3263,jst_3022_f3264,");
    mainGetSql.append("jst_3022_f3265,jst_3022_f3266,jst_3022_f3267,jst_3022_f3268,jst_3022_f3269,jst_3022_f3270,jst_3022_f3271,jst_3022_f3272,");
    mainGetSql.append("jst_3022_f3273,jst_3022_f3275,jst_3022_f3276,jst_3022_f3514,jst_3022_f3256,jst_3022_f3511,jst_3022_f3512,jst_3022_f3508,");
    mainGetSql.append("jst_3022_f3509,jst_3022_f3510,jst_3022_f3530,jst_3022_f3274,jst_3022_f3537,jst_3022_f3539 ");
    mainGetSql.append("from jst_3022 where jst_3022_id=?");
    StringBuffer mainSql = new StringBuffer();
    mainSql.append("update jst_3024 set jst_3024_f3297=?,jst_3024_f3298=?,jst_3024_f3299=?,jst_3024_f3300=?,");
    mainSql.append("jst_3024_f3301=?,jst_3024_f3302=?,jst_3024_f3303=?,jst_3024_f3304=?,jst_3024_f3305=?,jst_3024_f3307=?,");
    mainSql.append("jst_3024_f3309=?,jst_3024_f3310=?,jst_3024_f3312=?,jst_3024_f3313=?,jst_3024_f3314=?,jst_3024_f3315=?,");
    mainSql.append("jst_3024_f3316=?,jst_3024_f3317=?,jst_3024_f3318=?,jst_3024_f3319=?,jst_3024_f3320=?,jst_3024_f3321=?,");
    mainSql.append("jst_3024_f3322=?,jst_3024_f3324=?,jst_3024_f3328=?,jst_3024_f3329=?,jst_3024_f3330=?,jst_3024_f3306=?,");
    mainSql.append("jst_3024_f3516=?,jst_3024_f3517=?,jst_3024_f3518=?,jst_3024_f3519=?,jst_3024_f3520=?,jst_3024_f3326=?,");
    mainSql.append("jst_3024_f3323=?,jst_3024_f3536=?,jst_3024_f3540=?,jst_3024_f3331=?,jst_3024_f3332=?,jst_3024_f3333=?,jst_3024_f3334=? where jst_3024_id=?");
    String subDelSql = "delete from jst_3025 where jst_3025_foreignkey=?";
    StringBuffer subSql = new StringBuffer();
    subSql.append("insert into jst_3025(jst_3025_id,jst_3025_owner,jst_3025_org,jst_3025_f3349,jst_3025_f3336,jst_3025_f3337,jst_3025_f3350,");
    subSql.append("jst_3025_f3341,jst_3025_f3343,jst_3025_f3521,jst_3025_f3342,jst_3025_f3352,jst_3025_f3346,jst_3025_f3345,");
    subSql.append("jst_3025_f3347,jst_3025_f3348,jst_3025_f3351,jst_3025_foreignkey,jst_3025_f3338) ");
    subSql.append("select jst_3023_id,jst_3023_owner,jst_3023_org,jst_3023_f3281,jst_3023_f3282,jst_3023_f3283,");
    subSql.append("jst_3023_f3285,jst_3023_f3286,jst_3023_f3288,jst_3023_f3289,jst_3023_f3287,jst_3023_f3295,");
    subSql.append("jst_3023_f3291,jst_3023_f3292,jst_3023_f3293,jst_3023_f3294,jst_3023_f3513,?,jst_3023_f3284 from jst_3023 where jst_3023_foreignkey=?");
    String psSql = "select DEALWITHEMPLOYEECOMMENT,DEALWITHTIME,COMMENTFIELD,(select CONCAT_WS('  ',orgname,empname) from org_employee a,org_organization_user b,org_organization c WHERE a.emp_id=b.emp_id and b.org_id=c.org_id and a.emp_id=DEALWITHEMPLOYEE_ID) as xm from jsf_dealwithcomment where WF_DEALWITH_ID in (select WF_DEALWITH_ID from jsf_dealwith where DATABASERECORD_ID=?)";
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      pstmt = conn.prepareStatement(mainGetSql.toString());
      pstmt.setString(1, recordId);
      rs = pstmt.executeQuery();
      Map<String, String> map = new HashMap<String, String>();
      if (rs.next()) {
        map.put("jst_3022_f3247", rs.getString("jst_3022_f3247"));
        map.put("jst_3022_f3248", rs.getString("jst_3022_f3248"));
        map.put("jst_3022_f3249", rs.getString("jst_3022_f3249"));
        map.put("jst_3022_f3250", rs.getString("jst_3022_f3250"));
        map.put("jst_3022_f3251", rs.getString("jst_3022_f3251"));
        map.put("jst_3022_f3252", rs.getString("jst_3022_f3252"));
        map.put("jst_3022_f3253", rs.getString("jst_3022_f3253"));
        map.put("jst_3022_f3257", rs.getString("jst_3022_f3257"));
        map.put("jst_3022_f3258", rs.getString("jst_3022_f3258"));
        map.put("jst_3022_f3255", rs.getString("jst_3022_f3255"));
        map.put("jst_3022_f3254", rs.getString("jst_3022_f3254"));
        map.put("jst_3022_f3259", rs.getString("jst_3022_f3259"));
        map.put("jst_3022_f3260", rs.getString("jst_3022_f3260"));
        map.put("jst_3022_f3262", rs.getString("jst_3022_f3262"));
        map.put("jst_3022_f3263", rs.getString("jst_3022_f3263"));
        map.put("jst_3022_f3264", rs.getString("jst_3022_f3264"));
        map.put("jst_3022_f3265", rs.getString("jst_3022_f3265"));
        map.put("jst_3022_f3266", rs.getString("jst_3022_f3266"));
        map.put("jst_3022_f3267", rs.getString("jst_3022_f3267"));
        map.put("jst_3022_f3268", rs.getString("jst_3022_f3268"));
        map.put("jst_3022_f3269", rs.getString("jst_3022_f3269"));
        map.put("jst_3022_f3270", rs.getString("jst_3022_f3270"));
        map.put("jst_3022_f3271", rs.getString("jst_3022_f3271"));
        map.put("jst_3022_f3272", rs.getString("jst_3022_f3272"));
        map.put("jst_3022_f3273", rs.getString("jst_3022_f3273"));
        map.put("jst_3022_f3275", rs.getString("jst_3022_f3275"));
        map.put("jst_3022_f3276", rs.getString("jst_3022_f3276"));
        map.put("jst_3022_f3514", rs.getString("jst_3022_f3514"));
        map.put("jst_3022_f3256", rs.getString("jst_3022_f3256"));
        map.put("jst_3022_f3511", rs.getString("jst_3022_f3511"));
        map.put("jst_3022_f3512", rs.getString("jst_3022_f3512"));
        map.put("jst_3022_f3508", rs.getString("jst_3022_f3508"));
        map.put("jst_3022_f3509", rs.getString("jst_3022_f3509"));
        map.put("jst_3022_f3510", rs.getString("jst_3022_f3510"));
        map.put("jst_3022_f3530", rs.getString("jst_3022_f3530"));
        map.put("jst_3022_f3274", rs.getString("jst_3022_f3274"));
        map.put("jst_3022_f3537", rs.getString("jst_3022_f3537"));
        map.put("jst_3022_f3539", rs.getString("jst_3022_f3539"));
      } 
      rs.close();
      pstmt.close();
      for (Map.Entry<String, String> entry : map.entrySet()) {
        if (entry.getValue() == null || "null".equalsIgnoreCase(entry.getValue()))
          entry.setValue(""); 
      } 
      pstmt = conn.prepareStatement(psSql);
      pstmt.setString(1, recordId);
      rs = pstmt.executeQuery();
      Map<String, String> tmap = new HashMap<String, String>();
      while (rs.next()) {
        StringBuffer temp = new StringBuffer();
        temp.append("<div style=\"line-height: 1.4; font-size: 12px;\">" + rs.getString("DEALWITHEMPLOYEECOMMENT") + "</div>");
        temp.append("<div style=\"text-align: right; padding-right: 20px; font-size: 12px;\">" + rs.getString("xm") + "&nbsp;(" + rs.getString("DEALWITHTIME") + ")</div>");
        tmap.put(rs.getString("COMMENTFIELD"), temp.toString());
      } 
      rs.close();
      pstmt.close();
      pstmt = conn.prepareStatement(mainSql.toString());
      pstmt.setString(1, map.get("jst_3022_f3247"));
      pstmt.setString(2, map.get("jst_3022_f3248"));
      pstmt.setString(3, map.get("jst_3022_f3249"));
      pstmt.setString(4, map.get("jst_3022_f3250"));
      pstmt.setString(5, map.get("jst_3022_f3251"));
      pstmt.setString(6, map.get("jst_3022_f3252"));
      pstmt.setString(7, map.get("jst_3022_f3253"));
      pstmt.setString(8, map.get("jst_3022_f3257"));
      pstmt.setString(9, map.get("jst_3022_f3258"));
      pstmt.setString(10, map.get("jst_3022_f3255"));
      pstmt.setString(11, map.get("jst_3022_f3259"));
      pstmt.setString(12, map.get("jst_3022_f3260"));
      pstmt.setString(13, map.get("jst_3022_f3262"));
      pstmt.setString(14, map.get("jst_3022_f3263"));
      pstmt.setString(15, map.get("jst_3022_f3264"));
      pstmt.setString(16, map.get("jst_3022_f3265"));
      pstmt.setString(17, map.get("jst_3022_f3266"));
      pstmt.setString(18, map.get("jst_3022_f3267"));
      pstmt.setString(19, map.get("jst_3022_f3268"));
      pstmt.setString(20, map.get("jst_3022_f3269"));
      pstmt.setString(21, map.get("jst_3022_f3270"));
      pstmt.setString(22, map.get("jst_3022_f3271"));
      pstmt.setString(23, map.get("jst_3022_f3272"));
      pstmt.setString(24, map.get("jst_3022_f3273"));
      pstmt.setString(25, map.get("jst_3022_f3275"));
      pstmt.setString(26, map.get("jst_3022_f3276"));
      pstmt.setString(27, map.get("jst_3022_f3514"));
      pstmt.setString(28, map.get("jst_3022_f3256"));
      pstmt.setString(29, map.get("jst_3022_f3511"));
      pstmt.setString(30, map.get("jst_3022_f3512"));
      pstmt.setString(31, map.get("jst_3022_f3508"));
      pstmt.setString(32, map.get("jst_3022_f3509"));
      pstmt.setString(33, map.get("jst_3022_f3510"));
      pstmt.setString(34, map.get("jst_3022_f3530"));
      pstmt.setString(35, map.get("jst_3022_f3274"));
      pstmt.setString(36, map.get("jst_3022_f3537"));
      pstmt.setString(37, map.get("jst_3022_f3539"));
      pstmt.setString(38, (tmap.get("jst_3022_f3277") == null) ? "" : tmap.get("jst_3022_f3277"));
      pstmt.setString(39, (tmap.get("jst_3022_f3278") == null) ? "" : tmap.get("jst_3022_f3278"));
      pstmt.setString(40, (tmap.get("jst_3022_f3279") == null) ? "" : tmap.get("jst_3022_f3279"));
      pstmt.setString(41, (tmap.get("jst_3022_f3280") == null) ? "" : tmap.get("jst_3022_f3280"));
      pstmt.setString(42, map.get("jst_3022_f3254"));
      pstmt.executeUpdate();
      pstmt.close();
      pstmt = conn.prepareStatement(subDelSql);
      pstmt.setString(1, map.get("jst_3022_f3254"));
      pstmt.executeUpdate();
      pstmt.close();
      pstmt = conn.prepareStatement(subSql.toString());
      pstmt.setString(1, map.get("jst_3022_f3254"));
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
    return result;
  }
}
