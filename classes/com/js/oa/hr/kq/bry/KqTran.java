package com.js.oa.hr.kq.bry;

import com.js.oa.hr.kq.bry.util.BryUtil;
import com.js.util.util.DataSourceBase;
import com.js.util.util.DataSourceUtil;
import com.js.util.util.IO2File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class KqTran {
  private SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
  
  public void userDataTran() {
    String dateStr = this.ymd.format(new Date());
    List<String[]> userList = userList();
    String sql = "insert into bry_kq (kq_userId,kq_userName,kq_orgId,kq_orgName,kq_userNum,kq_date,kq_dateType,kq_sb,kq_sbtype,kq_xb,kq_xbtype,kq_remark,kq_xbRemark,kq_sbRemark) values (?,?,?,?,?,'" + 
      dateStr + "'," + 
      "0,'08:30:00',0,'17:15:00',0,'',0,0)";
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      PreparedStatement pstam = conn.prepareStatement(sql);
      for (int i = 0; i < userList.size(); i++) {
        String[] user = userList.get(i);
        pstam.setLong(1, Long.valueOf(user[0]).longValue());
        pstam.setString(2, user[1]);
        pstam.setLong(3, Long.valueOf(user[3]).longValue());
        pstam.setString(4, user[4]);
        pstam.setString(5, user[2]);
        pstam.execute();
      } 
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }  
    } 
  }
  
  private List<String[]> userList() {
    String sql = "SELECT e.EMP_ID,e.EMPNAME,e.EMPNUMBER,o.org_id,o.ORGNAME,o.ORGNAMESTRING FROM org_employee e JOIN org_organization_user ou ON e.EMP_ID=ou.EMP_ID JOIN org_organization o ON ou.ORG_ID=o.ORG_ID WHERE e.USERISACTIVE=1 AND e.USERISDELETED=0 AND e.emp_id>0 ORDER BY o.ORGIDSTRING,e.EMPDUTYLevel";
    IO2File.printFile("同步所有员工基本考勤信息：" + sql, "宝日医考勤", 3);
    return (new DataSourceUtil()).getListQuery(sql, "");
  }
  
  public void kqDataTran(String dateStr) {
    String sql = "INSERT INTO kq_punch (punch_date,punch_time,punch_oaid,punch_emp,punch_num,punch_ci) VALUES (?,?,?,?,?,?)";
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      conn.createStatement().executeUpdate("delete from kq_punch where punch_date='" + dateStr + "'");
      List<String[]> kqdataList = kqDataList(dateStr);
      PreparedStatement pstam = conn.prepareStatement(sql);
      for (int i = 0; i < kqdataList.size(); i++) {
        String[] kq = kqdataList.get(i);
        if (!"".equals(kq[2])) {
          pstam.setString(1, kq[1]);
          if (kq[2].indexOf(" ") > 0) {
            String time = kq[2].split(" ")[1];
            pstam.setString(2, time);
            String[] t = time.split(":");
            pstam.setInt(5, 3600 * Integer.valueOf(t[0]).intValue() + 60 * Integer.valueOf(t[1]).intValue() + Integer.valueOf(t[2]).intValue());
          } else {
            String time = kq[2];
            pstam.setString(2, time);
            String[] t = time.split(":");
            pstam.setInt(5, 3600 * Integer.valueOf(t[0]).intValue() + 60 * Integer.valueOf(t[1]).intValue() + Integer.valueOf(t[2]).intValue());
          } 
          pstam.setString(3, kq[0]);
          pstam.setString(4, kq[4]);
          pstam.setInt(6, Integer.valueOf(kq[3]).intValue());
          pstam.execute();
        } 
      } 
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }  
    } 
  }
  
  private List<String[]> kqDataList(String dateStr) {
    String sql = "SELECT jst_3012_owner,jst_3012_f3098,jst_3012_f3101,ci,e.EMPNUMBER FROM (SELECT jst_3012_owner,jst_3012_f3098,jst_3012_f3101,1 ci FROM jst_3012 WHERE jst_3012_f3098='" + 
      dateStr + "'" + 
      " UNION " + 
      "SELECT jst_3012_owner,jst_3012_f3098,jst_3012_f3102,2 ci FROM jst_3012 WHERE jst_3012_f3098='" + dateStr + "'" + 
      ") kq JOIN org_employee e ON kq.jst_3012_owner=e.emp_id";
    IO2File.printFile("获得考勤打卡记录sql：" + sql, "宝日医考勤", 3);
    return (new DataSourceUtil()).getListQuery(sql, "");
  }
  
  public void kqTran(String dateStr) {
    List<String[]> kqList = kqList(dateStr);
    String sqlSb = "update bry_kq set kq_datetype=?1,kq_sb='?2',kq_sbType=?3,kq_sbRemark=?4 where kq_userId=?5 and kq_date='?6'";
    String sqlXb = "update bry_kq set kq_datetype=?1,kq_xb='?2',kq_xbType=?3,kq_xbRemark=?4 where kq_userId=?5 and kq_date='?6'";
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      for (int i = 0; i < kqList.size(); i++) {
        String[] kq = kqList.get(i);
        Integer dateType = getDateType(kq[0], kq[1], kq[3], kq[4]);
        String sql = "";
        if ("1".equals(kq[5])) {
          Integer sbType = getKqType(kq[0], kq[1], kq[3], kq[4], "1");
          sql = sqlSb.replace("?1", (CharSequence)dateType)
            .replace("?2", kq[1])
            .replace("?3", (CharSequence)sbType)
            .replace("?4", (CharSequence)sbType)
            .replace("?5", kq[3])
            .replace("?6", kq[0]);
        } else {
          Integer xbType = getKqType(kq[0], kq[1], kq[3], kq[4], "2");
          sql = sqlXb.replace("?1", (CharSequence)dateType)
            .replace("?2", kq[1])
            .replace("?3", (CharSequence)xbType)
            .replace("?4", (CharSequence)xbType)
            .replace("?5", kq[3])
            .replace("?6", kq[0]);
        } 
        IO2File.printFile("考勤sql：" + sql, "宝日医考勤", 3);
        base.addBatch(sql);
      } 
      base.executeBatch();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
  }
  
  private List<String[]> kqList(String dateStr) {
    String sql = "SELECT punch_date,punch_time,punch_emp,punch_oaid,punch_num,punch_ci FROM kq_punch WHERE punch_date='" + dateStr + "' order by punch_ci";
    return (new DataSourceUtil()).getListQuery(sql, "");
  }
  
  public Integer getDateType(String dateStr, String timeStr, String userId, String slong) {
    int result = (new BryUtil()).isHoliday(dateStr, userId).intValue();
    if (result == 0) {
      result = 1;
    } else {
      result = 0;
    } 
    return Integer.valueOf(result);
  }
  
  public Integer getKqType(String dateStr, String timeStr, String userId, String slong, String flag) {
    Integer[] panban = (new BryUtil()).getDutySetInt(userId);
    int result = 1;
    if ("1".equals(flag)) {
      if (Integer.valueOf(slong).intValue() > panban[0].intValue())
        result = 2; 
    } else if (Integer.valueOf(slong).intValue() < panban[3].intValue()) {
      result = 6;
    } 
    return Integer.valueOf(result);
  }
  
  public static void main(String[] arg) {
    (new KqTran()).kqTran("");
  }
}
