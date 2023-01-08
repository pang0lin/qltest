package com.js.oa.hr.kq.bry.util;

import com.js.util.util.DataSourceBase;
import com.js.util.util.DataSourceUtil;
import com.js.util.util.IO2File;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BryKqUtil {
  SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  
  SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
  
  public String chuQinTianShu(String beginDate, String endDate, String userId) {
    int num = 0;
    try {
      Long begin = Long.valueOf(this.ymd.parse(beginDate).getTime());
      Long end = Long.valueOf(this.ymd.parse(endDate).getTime());
      BryUtil util = new BryUtil();
      for (long l = begin.longValue(); l <= end.longValue(); l += 86400000L) {
        if (util.isHoliday(this.ymd.format(new Date(l)), userId).intValue() == 1)
          num++; 
      } 
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    return num;
  }
  
  public String chiDaoCishu(String beginDate, String endDate, String userId) {
    String num = "0";
    String sql = "SELECT COUNT(kq_id) FROM bry_kq WHERE kq_sbtype=2 AND kq_date BETWEEN '" + beginDate + "' AND '" + endDate + "' AND kq_userId=" + userId;
    IO2File.printFile("获得迟到次数：" + sql, "宝日医考勤统计", 3);
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      if (rs.next())
        num = rs.getString(1); 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return num;
  }
  
  public float[] qjTongji(String beginDate, String endDate, String userId) {
    float heji = 0.0F;
    float[] qj = { 
        0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 
        0.0F, 0.0F, 0.0F, 0.0F, heji };
    String sql = "SELECT jq_hourLong,jq_oType,jq_type,jq_Remark FROM bry_jq WHERE (jq_startDate BETWEEN '" + beginDate + " 00:00:00' AND '" + endDate + " 23:59:59' " + 
      "OR jq_endDate BETWEEN '" + beginDate + " 00:00:00' AND '" + endDate + " 23:59:59' ) AND jq_userId=" + userId;
    DataSourceUtil util = new DataSourceUtil();
    List<String[]> jqList = util.getListQuery(sql, "0");
    int i;
    for (i = 0; i < jqList.size(); i++) {
      String[] jq = jqList.get(i);
      if (jq[2].equals("5")) {
        if ("病假".equals(jq[1])) {
          qj[0] = qj[0] + Float.valueOf(jq[0]).floatValue();
        } else if ("事假".equals(jq[1])) {
          qj[1] = qj[1] + Float.valueOf(jq[0]).floatValue();
        } else if ("产检".equals(jq[1])) {
          qj[2] = qj[2] + Float.valueOf(jq[0]).floatValue();
        } else if ("产假".equals(jq[1])) {
          qj[3] = qj[3] + Float.valueOf(jq[0]).floatValue();
        } else if ("哺乳假".equals(jq[1])) {
          qj[4] = qj[4] + Float.valueOf(jq[0]).floatValue();
        } else if ("年假".equals(jq[1])) {
          qj[5] = qj[5] + Float.valueOf(jq[0]).floatValue();
        } else if ("婚假".equals(jq[1])) {
          qj[6] = qj[6] + Float.valueOf(jq[0]).floatValue();
        } else if ("其他".equals(jq[1]) || "丧假".equals(jq[1])) {
          qj[7] = qj[7] + Float.valueOf(jq[0]).floatValue();
        } else if ("调休".equals(jq[1])) {
          qj[8] = qj[8] + Float.valueOf(jq[0]).floatValue();
        } 
      } else if (jq[2].equals("4")) {
        qj[9] = qj[9] + Float.valueOf(jq[0]).floatValue();
        qj[10] = qj[10] + Float.valueOf((jq[3].equals("") || "null".equalsIgnoreCase(jq[3])) ? "0" : jq[3]).floatValue();
      } else if (jq[2].equals("2")) {
        if ("普通加班".equals(jq[1])) {
          qj[11] = qj[11] + Float.valueOf(jq[0]).floatValue();
        } else if ("周末加班".equals(jq[1])) {
          qj[12] = qj[12] + Float.valueOf(jq[0]).floatValue();
        } else if ("节假日加班".equals(jq[1])) {
          qj[13] = qj[13] + Float.valueOf(jq[0]).floatValue();
        } 
      } 
    } 
    for (i = 0; i < 11; i++) {
      if (i != 10)
        qj[i] = Float.valueOf(formatNum(qj[i] / 8.0F)).floatValue(); 
    } 
    qj[qj.length - 1] = Float.valueOf(qj[0]).floatValue() + Float.valueOf(qj[1]).floatValue() + Float.valueOf(qj[2]).floatValue() + Float.valueOf(qj[3]).floatValue() + 
      Float.valueOf(qj[4]).floatValue() + Float.valueOf(qj[6]).floatValue() + Float.valueOf(qj[7]).floatValue();
    return qj;
  }
  
  public String[] wuCanFei(String beginDate, String endDate, String userId) {
    float[] wuCan = { 0.0F, 0.0F, 0.0F };
    String sql = "SELECT jq_hourLong,jq_remark FROM bry_jq WHERE (jq_startDate BETWEEN '" + beginDate + " 00:00:00' AND '" + endDate + " 23:59:59' " + 
      "OR jq_endDate BETWEEN '" + beginDate + " 00:00:00' AND '" + endDate + " 23:59:59' ) and jq_type=3 AND jq_userId=" + userId + " and jq_remark='是'";
    IO2File.printFile("获得(外出午餐天数)外出申请流程填写是否外出午餐，记录本月次数：" + sql, "宝日医考勤统计", 3);
    DataSourceUtil util = new DataSourceUtil();
    List<String[]> jqList = util.getListQuery(sql, "");
    wuCan[0] = jqList.size();
    jqList.clear();
    sql = "SELECT kq_id FROM bry_kq WHERE kq_userId=" + userId + " AND kq_date BETWEEN '" + beginDate + "' AND '" + endDate + 
      "' AND (kq_sbtype<>0 or kq_xbtype<>0)";
    IO2File.printFile("获得(报出勤天数)上下班打卡过的天数，只要打过卡，就算出勤：" + sql, "宝日医考勤统计", 3);
    jqList = util.getListQuery(sql, "0");
    wuCan[1] = jqList.size();
    jqList.clear();
    IO2File.printFile("(报出勤午餐费)报出勤天数-外出午餐天数）* 午餐费(午餐费固定)：" + wuCan[1] + "-" + wuCan[0] + "*" + '\024', "宝日医考勤统计", 3);
    wuCan[2] = (wuCan[1] - wuCan[0]) * 20.0F;
    return new String[] { formatNum(wuCan[0]), formatNum(wuCan[1]), formatNum(wuCan[2]) };
  }
  
  public String[] nianJia(String beginDate, String endDate, String userId) {
    String year = beginDate.split("-")[0];
    DataSourceBase base = new DataSourceBase();
    float n = 0.0F, q = 0.0F, s = 0.0F;
    try {
      base.begin();
      String sql = "SELECT jst_3013_f3106 FROM jst_3013 j JOIN org_employee e ON j.jst_3013_f3114=e.EMPNAME WHERE e.emp_id=" + userId + 
        " ORDER BY j.jst_3013_id DESC";
      ResultSet rs = base.executeQuery(sql);
      if (rs.next())
        n = rs.getFloat(1); 
      rs.close();
      sql = "SELECT SUM(jq_hourLong) FROM bry_jq WHERE jq_type=5 AND jq_otype='年假' AND jq_userId= " + userId + 
        " AND (jq_startDate BETWEEN '" + year + "-01-01 00:00:00' AND '" + beginDate + " 00:00:00' " + 
        "OR jq_endDate BETWEEN '" + year + "-01-01 00:00:00' AND '" + beginDate + " 00:00:00' )";
      rs = base.executeQuery(sql);
      if (rs.next())
        q = rs.getFloat(1) / 8.0F; 
      rs.close();
      sql = "SELECT SUM(jq_hourLong) FROM bry_jq WHERE jq_type=5 AND jq_otype='年假' AND jq_userId= " + userId + 
        " AND (jq_startDate BETWEEN '" + beginDate + " 00:00:00' AND '" + endDate + " 23:59:59' " + 
        "OR jq_endDate BETWEEN '" + beginDate + " 00:00:00' AND '" + endDate + " 23:59:59' )";
      rs = base.executeQuery(sql);
      if (rs.next())
        s = q + rs.getFloat(1) / 8.0F; 
      rs.close();
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return new String[] { formatNum(n), formatNum(n - q), formatNum(n - s) };
  }
  
  public String[] tiaoXiu(String beginDate, String endDate, String userId, int num, String yytx) {
    String[] tx = { "0", "0", "0" };
    String sql = "SELECT kqtj_xsycx,kqtj_xsycx FROM bry_kqtj WHERE kqtj_userId=" + userId + " AND kqtj_num=" + (num - 1);
    IO2File.printFile("取得上月的调休：" + sql, "宝日医考勤统计", 3);
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      float f = 0.0F;
      ResultSet rs = base.executeQuery(sql);
      if (rs.next()) {
        float cx = rs.getFloat(1);
        float sy = rs.getFloat(2);
        if (cx > sy) {
          f = sy;
        } else {
          f = cx;
        } 
      } 
      tx[0] = formatNum(f);
      sql = "SELECT jq_remark FROM bry_jq WHERE jq_type=4 and jq_userId= " + userId + 
        " AND (jq_startDate BETWEEN '" + beginDate + " 00:00:00' AND '" + endDate + " 23:59:59' " + 
        "OR jq_endDate BETWEEN '" + beginDate + " 00:00:00' AND '" + endDate + " 23:59:59' )";
      IO2File.printFile("当月存休：" + sql, "宝日医考勤统计", 3);
      rs = base.executeQuery(sql);
      float xcx = 0.0F;
      while (rs.next())
        xcx += (rs.getString(1) == null) ? 0.0F : rs.getFloat(1); 
      tx[1] = formatNum(xcx);
      tx[2] = formatNum(f + xcx - Float.valueOf(yytx).floatValue());
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return tx;
  }
  
  public String[] heji(String[] para) {
    String shijiChuqin = formatNum(Float.valueOf(para[2]).floatValue() - Float.valueOf(para[3]).floatValue());
    return new String[] { formatNum(Float.valueOf(para[0]).floatValue() + Float.valueOf(para[1]).floatValue()), 
        shijiChuqin, 
        para[4], 
        formatNum(Float.valueOf(para[4]).floatValue() * Float.valueOf(shijiChuqin).floatValue()) };
  }
  
  private String formatNum(float num) {
    String v = String.format("%.2f", new Object[] { Float.valueOf(num) });
    return v;
  }
}
