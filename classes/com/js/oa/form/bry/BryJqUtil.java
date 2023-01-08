package com.js.oa.form.bry;

import com.js.oa.hr.kq.bry.BryJq;
import com.js.oa.hr.kq.bry.util.BryUtil;
import com.js.util.util.DataSourceBase;
import com.js.util.util.IO2File;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class BryJqUtil {
  private SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  
  public void insertJq(BryJq jq) {
    String sql = "insert into bry_jq (jq_userId,jq_userName,jq_orgId,jq_orgName,jq_userNum,jq_startDate,jq_endDate,jq_hourLong,jq_type,jq_remark,jq_use,jq_otype) values (1?,'2?',3?,'4?','5?','6?','7?',8?,9?,'10?',11?,'12?')";
    sql = sql.replace("11?", "1")
      .replace("12?", (new StringBuilder(String.valueOf(jq.getJqOtype()))).toString())
      .replace("1?", (CharSequence)jq.getUserId())
      .replace("2?", jq.getUserName())
      .replace("3?", (CharSequence)jq.getOrgId())
      .replace("4?", jq.getOrgName())
      .replace("5?", (jq.getUserNumber() == null) ? "" : jq.getUserNumber())
      .replace("6?", this.ymdhms.format(jq.getStartDate()))
      .replace("7?", this.ymdhms.format(jq.getEndDate()))
      .replace("8?", (new StringBuilder(String.valueOf(jq.getHourLong()))).toString())
      .replace("9?", (CharSequence)jq.getJqType())
      .replace("10?", (jq.getJqRemark() == null) ? "" : jq.getJqRemark());
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      IO2File.printFile(sql);
      base.executeUpdate(sql);
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
  }
  
  public BryJq getBryJq(String processId, String recordId, String tableName) {
    BryJq jq = null;
    String jqType = "0";
    String sql = "";
    DataSourceBase base = new DataSourceBase();
    if (tableName == null || "".equals(tableName)) {
      String tableSql = "SELECT a.area_table FROM jsf_workflowprocess p JOIN tarea a ON p.ACCESSDATABASEID=a.PAGE_ID WHERE p.WF_WORKFLOWPROCESS_ID=" + 
        processId + " AND a.area_name='form1'";
      try {
        base.begin();
        ResultSet rs = base.executeQuery(tableSql);
        if (rs.next())
          tableName = (rs.getString(1) == null) ? "" : rs.getString(1); 
        base.end();
      } catch (Exception e) {
        base.end();
        e.printStackTrace();
      } 
    } 
    if ("jst_3015".equals(tableName)) {
      IO2File.printFile("外出", "宝日医考勤", 3);
      jqType = "3";
      sql = "SELECT e.emp_id,e.EMPNAME,e.EMPNUMBER,o.org_id,o.ORGNAME,j.jst_3015_f3158,j.jst_3015_f3163,'0',jst_3015_f3164 FROM " + 
        tableName + " j JOIN org_employee e ON j." + tableName + "_owner=e.EMP_ID JOIN org_organization o ON o.ORG_ID=j." + tableName + "_org " + 
        "where " + tableName + "_id=" + recordId;
    } else if ("jst_chuchai".equals(tableName)) {
      IO2File.printFile("出差", "宝日医考勤", 3);
      jqType = "4";
      sql = "SELECT e.emp_id,e.EMPNAME,e.EMPNUMBER,o.org_id,o.ORGNAME,chuchai_start,chuchai_end,'0','0' FROM " + 
        tableName + " j JOIN org_employee e ON j." + tableName + "_owner=e.EMP_ID JOIN org_organization o ON o.ORG_ID=j." + tableName + "_org " + 
        "where " + tableName + "_id=" + recordId;
    } else if ("jst_3017".equals(tableName)) {
      IO2File.printFile("加班", "宝日医考勤", 3);
      jqType = "2";
      sql = "SELECT e.emp_id,e.EMPNAME,e.EMPNUMBER,o.org_id,o.ORGNAME,CONCAT(j.jst_3017_f3183,' ',j.jst_3017_f3184),CONCAT(j.jst_3017_f3183,' ',j.jst_3017_f3185),j.jst_3017_f3182,'' FROM " + 
        tableName + " j JOIN org_employee e ON j." + tableName + "_owner=e.EMP_ID JOIN org_organization o ON o.ORG_ID=j." + tableName + "_org " + 
        "where " + tableName + "_id=" + recordId;
    } else if ("jst_3016".equals(tableName)) {
      IO2File.printFile("请假", "宝日医考勤", 3);
      jqType = "5";
      sql = "SELECT e.emp_id,e.EMPNAME,e.EMPNUMBER,o.org_id,o.ORGNAME,j.jst_3016_f3169,j.jst_3016_f3170,j.jst_3016_f3168,'' FROM " + 
        tableName + " j JOIN org_employee e ON j." + tableName + "_owner=e.EMP_ID JOIN org_organization o ON o.ORG_ID=j." + tableName + "_org " + 
        "where " + tableName + "_id=" + recordId;
    } 
    IO2File.printFile("同步数据sql语句：" + sql, "宝日医考勤", 3);
    BryUtil util = new BryUtil();
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      if (rs.next()) {
        String startDateTime = (rs.getString(6) == null) ? "" : rs.getString(6);
        String endDateTime = (rs.getString(7) == null) ? "" : rs.getString(7);
        jq = new BryJq(
            Long.valueOf(rs.getLong(1)), 
            (rs.getString(2) == null) ? "" : rs.getString(2), 
            Long.valueOf(rs.getLong(4)), 
            (rs.getString(5) == null) ? "" : rs.getString(5), 
            (rs.getString(3) == null) ? "" : rs.getString(3), 
            util.dateFormat(startDateTime), 
            util.dateFormat(endDateTime), 
            jqType, 
            (rs.getString(8) == null) ? "0" : rs.getString(8));
        jq.setJqRemark((jq.getJqRemark() == null || "".equals(jq.getJqRemark())) ? (
            (rs.getString(9) == null) ? "" : rs.getString(9)) : jq.getJqRemark());
      } 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return jq;
  }
  
  public void updateJq(BryJq jq) {
    Map<String, String> map = new HashMap<String, String>();
    map.put("事假", "jst_3013_f3104");
    map.put("病假", "jst_3013_f3105");
    map.put("调休", "jst_3013_f3107");
    map.put("产假", "jst_3013_f3108");
    map.put("婚假", "jst_3013_f3109");
    map.put("丧假", "jst_3013_f3110");
    map.put("哺乳假", "jst_3013_f3111");
    map.put("其他", "jst_3013_f3112");
    if (jq.getJqType().intValue() == 5) {
      String oType = jq.getJqOtype();
      String sql = "";
      if (map.get(oType) != null) {
        if (",事假,病假,哺乳假,".contains("," + oType + ",")) {
          sql = "update jst_3013 set " + (String)map.get(oType) + "=" + (String)map.get(oType) + "+" + jq.getHourLong() + " where jst_3013_f3114='" + jq.getUserName() + "'";
        } else {
          sql = "update jst_3013 set " + (String)map.get(oType) + "=" + (String)map.get(oType) + "+" + (jq.getHourLong() * 100.0F / 8.0F / 100.0F) + " where jst_3013_f3114='" + jq.getUserName() + "'";
        } 
        System.out.println(sql);
        if (!"".equals(sql)) {
          IO2File.printFile("修改假期管理：" + sql);
          DataSourceBase base = new DataSourceBase();
          base.executeSqlUpdate(sql);
        } 
      } 
    } 
  }
}
