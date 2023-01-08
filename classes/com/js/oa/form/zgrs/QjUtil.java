package com.js.oa.form.zgrs;

import com.js.util.util.DataSourceBase;
import com.js.util.util.DataSourceUtil;
import com.js.util.util.IO2File;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QjUtil {
  public void qjOp(String recordId, String bianhao) {
    String sql = "";
    Map<String, String> qjMap = new HashMap<String, String>();
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      sql = "select j.rs_qjspd_qjrid,j.rs_qjspd_qjdbh,j.rs_qjspd_sqrq,j.rs_qjspd_zw,j.rs_qjspd_gw,case when j.rs_qjspd_qjlx is null then '因公出差' else j.rs_qjspd_qjlx end,j.rs_qjspd_qjts,e.empName,o.Orgname,o.org_id,e.Empnumber,j.rs_qjspd_njts,j.rs_qjspd_synjts,j.rs_qjspd_ljsjts,j.rs_qjspd_sflj,j.jst_3003_f3350,j.rs_qjspd_qjsy,j.rs_qjspd_zj,j.rs_qjspd_xgqjdbh,j.jst_3003_owner,jst_3003_f3446 from jst_3003 j join org_employee e on j.rs_qjspd_qjrid=e.emp_id join org_organization_user ou on e.emp_id=ou.emp_id join org_organization o on ou.org_id=o.org_id where j.jst_3003_id=" + 




        
        recordId;
      ResultSet rs = base.executeQuery(sql);
      if (rs.next()) {
        qjMap.put("yonghuId", (rs.getString(1) == null) ? "0" : rs.getString(1));
        qjMap.put("bianhao", (rs.getString(2) == null) ? ((rs.getString(21) == null) ? "" : rs.getString(21)) : rs.getString(2));
        qjMap.put("qingjiariqi", (rs.getString(3) == null) ? "" : rs.getString(3));
        qjMap.put("zhiwu", (rs.getString(4) == null) ? "" : rs.getString(4));
        qjMap.put("ganwei", (rs.getString(5) == null) ? "" : rs.getString(5));
        qjMap.put("leixing", (rs.getString(6) == null) ? "" : rs.getString(6));
        qjMap.put("qingjiatianshu", (rs.getString(7) == null) ? "0" : rs.getString(7));
        qjMap.put("xingming", (rs.getString(8) == null) ? "" : rs.getString(8));
        qjMap.put("bumingmingcheng", (rs.getString(9) == null) ? "" : rs.getString(9));
        qjMap.put("bumenId", (rs.getString(10) == null) ? "" : rs.getString(10));
        qjMap.put("gonghao", (rs.getString(11) == null) ? "" : rs.getString(11));
        qjMap.put("zhiji", (rs.getString(18) == null) ? "" : rs.getString(18));
        qjMap.put("nianjiazong", (rs.getString(12) == null) ? "0" : rs.getString(12));
        qjMap.put("nianjiasheng", (rs.getString(13) == null) ? "0" : rs.getString(13));
        qjMap.put("nianjiaqing", (rs.getString(14) == null) ? "0" : rs.getString(14));
        qjMap.put("shifoulijing", (rs.getString(15) == null) ? "否" : rs.getString(15));
        qjMap.put("shifouchujing", (rs.getString(16) == null) ? "是" : rs.getString(16));
        qjMap.put("qingjiashiyou", zifu((rs.getString(17) == null) ? "" : rs.getString(17)));
        bianhao = (rs.getString(19) == null) ? "" : rs.getString(19);
        qjMap.put("faqiren", (rs.getString(20) == null) ? "" : rs.getString(20));
      } 
      rs.close();
      List<String> fields = new ArrayList<String>();
      String field = "insert into rst_qj (qj_id,qj_userName,qj_userId,qj_type,qj_duty,qj_gw,qj_userOrg,qj_userNum,qj_year,qj_month,qj_number,qj_startDate,qj_endDate,qj_sjsHour,qj_sjjHour,qj_sHour,qj_jHour,qj_hourLong,qj_isQj,qj_isBd,qj_njall,qj_njsy,qj_njyq,qj_isLijing,qj_isChujing,qj_cause,qj_zj,qj_fqr) values (hibernate_sequence.nextval,'" + 

        
        (String)qjMap.get("xingming") + "'," + (String)qjMap.get("yonghuId") + ",'" + (String)qjMap.get("leixing") + "','" + (String)qjMap.get("zhiwu") + 
        "','" + (String)qjMap.get("ganwei") + "'," + (String)qjMap.get("bumenId") + ",'" + (String)qjMap.get("gonghao") + "',?1,?2,'" + (String)qjMap.get("bianhao") + "',?3,?4,?5,?6,?7," + 
        "?8,?9,0,0," + (String)qjMap.get("nianjiazong") + "," + (String)qjMap.get("nianjiasheng") + "," + (String)qjMap.get("nianjiaqing") + ",'" + (String)qjMap.get("shifoulijing") + 
        "','" + (String)qjMap.get("shifouchujing") + "','" + (String)qjMap.get("qingjiashiyou") + "','" + (String)qjMap.get("zhiji") + "'," + (String)qjMap.get("faqiren") + ")";
      if ("事假".equals(qjMap.get("leixing"))) {
        sql = "select rs_qjspdsj_qjnf,rs_qjspdsj_qjyf,rs_qjspdsj_id,rs_qjspdsj_qsrq,rs_qjspdsj_jsrq,rs_qjspdsj_qssjd,rs_qjspdsj_jssjd,rs_qjspdsj_qjsc from rs_qjspdsj where rs_qjspdsj_foreignkey=" + 
          recordId;
        rs = base.executeQuery(sql);
        while (rs.next())
          fields.add(field.replace("?1", "'" + ((rs.getString(1) == null) ? "" : rs.getString(1)) + "'")
              .replace("?2", "'" + ((rs.getString(2) == null) ? "" : rs.getString(2)) + "'")
              .replace("?3", "'" + dateFormat((rs.getString(4) == null) ? "" : rs.getString(4)) + "'")
              .replace("?4", "'" + dateFormat((rs.getString(5) == null) ? "" : rs.getString(5)) + "'")
              .replace("?5", "'" + ((rs.getString(6) == null) ? "" : rs.getString(6)) + "'")
              .replace("?6", "'" + ((rs.getString(7) == null) ? "" : rs.getString(7)) + "'")
              .replace("?7", "''")
              .replace("?8", "''")
              .replace("?9", (CharSequence)Float.valueOf((rs.getString(8) == null) ? "0" : rs.getString(8)))); 
        rs.close();
      } else {
        sql = "select rs_qjspdzb_qjnf,rs_qjspdzb_qjyf,rs_qjspdzb_id,rs_qjspdzb_qsrq,rs_qjspdzb_jsrq,rs_qjspdzb_qssjd,rs_qjspdzb_jssjd,rs_qjspdzb_qjts from rs_qjspdzb where rs_qjspdzb_foreignkey=" + 
          recordId;
        rs = base.executeQuery(sql);
        while (rs.next())
          fields.add(field.replace("?1", "'" + ((rs.getString(1) == null) ? "" : rs.getString(1)) + "'")
              .replace("?2", "'" + ((rs.getString(2) == null) ? "" : rs.getString(2)) + "'")
              .replace("?3", "'" + dateFormat((rs.getString(4) == null) ? "" : rs.getString(4)) + "'")
              .replace("?4", "'" + dateFormat((rs.getString(5) == null) ? "" : rs.getString(5)) + "'")
              .replace("?5", "''")
              .replace("?6", "''")
              .replace("?7", "'" + ((rs.getString(6) == null) ? "" : rs.getString(6)) + "'")
              .replace("?8", "'" + ((rs.getString(7) == null) ? "" : rs.getString(7)) + "'")
              .replace("?9", Float.valueOf((rs.getString(8) == null) ? "0" : rs.getString(8)).floatValue() * 8.0F)); 
        rs.close();
      } 
      for (int i = 0; i < fields.size(); i++) {
        IO2File.printFile("请假数据保存：" + (String)fields.get(i), "人寿请假", 3);
        base.executeUpdate(fields.get(i));
      } 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
  }
  
  public void isQj(String tableId, String recordId) {
    DataSourceBase base = new DataSourceBase();
    String tableName = "";
    try {
      base.begin();
      ResultSet rs = base.executeQuery("select area_table from tarea where page_id=" + tableId + " and area_name='form1'");
      if (rs.next())
        tableName = rs.getString(1); 
      if ("jst_3003".equals(tableName))
        setQj(recordId, "-3"); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
  }
  
  public void setQj(String recordId, String isQj) {
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      ResultSet rs = base.executeQuery("SELECT rs_qjspd_qjdbh,rs_qjspd_xgqjdbh,jst_3003_f3446 FROM jst_3003 where jst_3003_id=" + recordId);
      String bianhao = "";
      String biangeng = "";
      if (rs.next()) {
        bianhao = (rs.getString(1) == null) ? ((rs.getString(3) == null) ? "" : rs.getString(3)) : rs.getString(1);
        biangeng = (rs.getString(2) == null) ? "" : rs.getString(2);
      } 
      IO2File.printFile("请假标记位修改：update rst_qj set qj_isQj=" + isQj + " where qj_number='" + bianhao + "' and (qj_isQj=0 or qj_isQj=1)", "人寿请假", 3);
      base.executeUpdate("update rst_qj set qj_isQj=" + isQj + " where qj_number='" + bianhao + "' and (qj_isQj=0 or qj_isQj=1)");
      if ("-2".equals(isQj))
        updateKq(bianhao); 
      if (!biangeng.equals("") && !"-2".equals(isQj)) {
        IO2File.printFile("被变更请假标记位修改：update rst_qj set qj_isQj=-1 where qj_number='" + biangeng + "' and (qj_isQj=0 or qj_isQj=1)", "人寿请假", 3);
        base.executeUpdate("update rst_qj set qj_isQj=-1 where qj_number='" + biangeng + "' and (qj_isQj=0 or qj_isQj=1)");
        updateKq(biangeng);
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
  }
  
  public void xiaoJia0(String recordId, String isQj) {
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      ResultSet rs = base.executeQuery("SELECT rs_qjspd_xgqjdbh FROM jst_3030 where jst_3030_id=" + recordId);
      String bianhao = "";
      if (rs.next())
        bianhao = (rs.getString(1) == null) ? "" : rs.getString(1); 
      IO2File.printFile("请假标记位修改：update rst_qj set qj_isQj=" + isQj + " where qj_number='" + bianhao + "' and (qj_isQj=0 or qj_isQj=1)", "人寿请假", 3);
      base.executeUpdate("update rst_qj set qj_isQj=" + isQj + " where qj_number='" + bianhao + "' and (qj_isQj=0 or qj_isQj=1)");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
  }
  
  public void deleteShuju(String tableName, String recordId, String isQj) {
    if ("jst_3003".equals(tableName)) {
      DataSourceBase base = new DataSourceBase();
      try {
        base.begin();
        String sql = "SELECT rs_qjspd_qjdbh,jst_3003_f3446 FROM jst_3003 where jst_3003_id in (" + recordId
          .substring(0, recordId.lastIndexOf(",")) + ")";
        ResultSet rs = base.executeQuery(sql);
        String bianhao = "";
        while (rs.next()) {
          bianhao = (rs.getString(1) == null) ? ((rs.getString(2) == null) ? "" : rs.getString(2)) : rs.getString(1);
          base.addBatch("update rst_qj set qj_isQj=" + isQj + " where qj_number='" + bianhao + "' and (qj_isQj=0 or qj_isQj=1)");
        } 
        base.executeBatch();
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        base.end();
      } 
    } 
  }
  
  public void updateKq(String bianhao) {
    String sql = "select qj_userId,qj_type,qj_startDate,qj_endDate from rst_qj where qj_number='" + bianhao + "'";
    List<String[]> list = (new DataSourceUtil()).getListQuery(sql, "");
    List<String> sqlList = new ArrayList<String>();
    for (int i = 0; i < list.size(); i++) {
      String[] qj = list.get(i);
      sqlList.add("update rst_kq set kq_sbType=kq_sbremark where (kq_date between '" + qj[2] + "' and '" + qj[3] + "') and kq_userid=" + qj[0] + " and kq_sbtype=5");
      sqlList.add("update rst_kq set kq_xbType=kq_xbremark where (kq_date between '" + qj[2] + "' and '" + qj[3] + "') and kq_userid=" + qj[0] + " and kq_xbtype=5");
    } 
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      for (int j = 0; j < sqlList.size(); j++) {
        IO2File.printFile("销假后，考勤数据恢复：" + (String)sqlList.get(j), "人寿请假", 3);
        base.executeUpdate(sqlList.get(j));
      } 
      base.executeBatch();
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
  }
  
  public String dateFormat(String dateTimeStr) {
    dateTimeStr = dateTimeStr.replace("/", "-");
    if (!dateTimeStr.equals("")) {
      SimpleDateFormat format = new SimpleDateFormat("yyyy-M-d");
      SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd");
      try {
        dateTimeStr = ymdhms.format(format.parse(dateTimeStr));
      } catch (ParseException e) {
        e.printStackTrace();
      } 
    } 
    return dateTimeStr;
  }
  
  private String zifu(String str) {
    str = str.replaceAll("\\n\\r", "<br>").replaceAll("\\r\\n", "<br>")
      .replaceAll("'", "&acute;");
    return str;
  }
}
