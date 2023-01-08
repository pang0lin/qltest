package com.js.oa.form.zgrs;

import com.js.util.util.DataSourceBase;
import com.js.util.util.DataSourceUtil;
import com.js.util.util.IO2File;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KqUtil {
  public void kqOp(String recordId) {
    List<String> sqlList = new ArrayList<String>();
    String sql = "select rs_bqd_bqr,rs_bqd_f3368 from rs_bqd where rs_bqd_id=" + recordId;
    String bqId = "0";
    String liyou = "";
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      if (rs.next()) {
        bqId = rs.getString(1);
        liyou = (rs.getString(2) == null) ? "" : rs.getString(2);
      } 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    sql = "select rs_kqzb_qsrq,rs_kqzb_jsrq,rs_kqzb_qssjd,rs_kqzb_jssjd from rs_kqzb where rs_kqzb_foreignkey=" + recordId;
    List<String[]> bqList = (new DataSourceUtil()).getListQuery(sql, "");
    int i;
    for (i = 0; i < bqList.size(); i++) {
      String[] bq = bqList.get(i);
      IO2File.printFile("补签提交数据：补签日期：" + bq[0] + "——" + bq[1] + "   补签时间：" + bq[2] + "——" + bq[3], "人寿补签", 3);
      if (bq[0].equals(bq[1])) {
        String sqle = "";
        if (bq[2].equals("9")) {
          sqle = "update rst_kq set kq_sb='9:00',kq_sbType=3,kq_sbbqContext='" + liyou + "' " + 
            "where kq_userId=" + bqId + " and kq_date='" + bq[0] + "'";
          sqlList.add(sqle);
        } 
        if (bq[3].equals("17")) {
          sqle = "update rst_kq set kq_xb='17:00',kq_xbType=3,kq_xbbqContext='" + liyou + "' " + 
            "where kq_userId=" + bqId + " and kq_date='" + bq[0] + "'";
          sqlList.add(sqle);
        } 
      } else {
        Long msLong = Long.valueOf(86400000L);
        SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
        long startDay = 0L;
        long endDay = 0L;
        try {
          startDay = (ymd.parse(bq[0]).getTime() + 28800000L) / msLong.longValue();
          endDay = (ymd.parse(bq[1]).getTime() + 28800000L) / msLong.longValue();
        } catch (Exception e) {
          e.printStackTrace();
        } 
        for (long t = startDay; t <= endDay; t++) {
          String curDate = ymd.format(new Date(t * msLong.longValue()));
          String sqle = "";
          if (t == startDay) {
            if (bq[2].equals("9")) {
              sqle = "update rst_kq set kq_sb='9:00',kq_sbType=3,kq_sbbqContext='" + liyou + "' " + 
                "where kq_userId=" + bqId + " and kq_date='" + curDate + "'";
              sqlList.add(sqle);
            } 
            sqlList.add("update rst_kq set kq_xb='17:00',kq_xbType=3,kq_xbbqContext='" + liyou + "' " + 
                "where kq_userId=" + bqId + " and kq_date='" + curDate + "'");
          } else if (t == endDay) {
            sqlList.add("update rst_kq set kq_sb='9:00',kq_sbType=3,kq_sbbqContext='" + liyou + "' " + 
                "where kq_userId=" + bqId + " and kq_date='" + curDate + "'");
            if (bq[3].equals("17")) {
              sqle = "update rst_kq set kq_xb='17:00',kq_xbType=3,kq_xbbqContext='" + liyou + "' " + 
                "where kq_userId=" + bqId + " and kq_date='" + curDate + "'";
              sqlList.add(sqle);
            } 
          } else {
            sqlList.add("update rst_kq set kq_sb='9:00',kq_sbType=3,kq_sbbqContext='" + liyou + "' " + 
                "where kq_userId=" + bqId + " and kq_date='" + curDate + "'");
            sqlList.add("update rst_kq set kq_xb='17:00',kq_xbType=3,kq_xbbqContext='" + liyou + "' " + 
                "where kq_userId=" + bqId + " and kq_date='" + curDate + "'");
          } 
        } 
      } 
    } 
    try {
      base.begin();
      for (i = 0; i < sqlList.size(); i++) {
        IO2File.printFile("考勤补签：补签人Id：" + bqId + "   执行sql：" + (String)sqlList.get(i), "人寿补签", 3);
        base.addBatch(sqlList.get(i));
        if (i % 50 == 0)
          base.executeBatch(); 
      } 
      base.executeBatch();
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
  }
}
