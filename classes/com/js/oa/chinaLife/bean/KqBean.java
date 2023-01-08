package com.js.oa.chinaLife.bean;

import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class KqBean {
  public String getKqOnload(String userId, String year) {
    SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
    String sql = "SELECT empduty,empdutylevel  FROM org_employee WHERE emp_id=" + userId;
    DataSourceBase base = new DataSourceBase();
    Map<String, String> map = new HashMap<String, String>();
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      if (rs.next()) {
        map.put("rs_bqd_f3361", (rs.getString(1) == null) ? "" : rs.getString(1));
        map.put("rs_bqd_f3362", (rs.getString(2) == null) ? "" : rs.getString(2));
      } 
      int[] kqNum = (new KqNumUtil()).getKqNum(userId, "", String.valueOf(year) + "-01-01", ymd.format(new Date()));
      map.put("rs_bqd_f3364", (new StringBuilder(String.valueOf(kqNum[0]))).toString());
      map.put("rs_bqd_f3365", (new StringBuilder(String.valueOf(kqNum[1]))).toString());
      map.put("rs_bqd_f3366", (new StringBuilder(String.valueOf(kqNum[2]))).toString());
      map.put("rs_bqd_f3367", (new StringBuilder(String.valueOf(kqNum[3]))).toString());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    String xml = "[{k:'rs_bqd_f3361',v:'" + (String)map.get("rs_bqd_f3361") + "'},{k:'rs_bqd_f3362',v:'" + (String)map.get("rs_bqd_f3362") + "'}," + 
      "{k:'rs_bqd_f3364',v:'" + (String)map.get("rs_bqd_f3364") + "'},{k:'rs_bqd_f3365',v:'" + (String)map.get("rs_bqd_f3365") + "'}," + 
      "{k:'rs_bqd_f3366',v:'" + (String)map.get("rs_bqd_f3366") + "'},{k:'rs_bqd_f3367',v:'" + (String)map.get("rs_bqd_f3367") + "'}]";
    return xml;
  }
}
