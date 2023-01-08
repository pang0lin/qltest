package com.js.oa.chinaLife.bean;

import com.js.util.util.DataSourceBase;
import com.js.util.util.IO2File;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QingJiaBean {
  private int printFile = 0;
  
  public QingJiaBean() {}
  
  public QingJiaBean(int printFile) {
    this.printFile = printFile;
  }
  
  public String[] getNianjiaData(String userId, String year) {
    int searchYear = Integer.valueOf(year).intValue();
    int thisYear = (new Date()).getYear() + 1900;
    int thisMonth = (new Date()).getMonth() + 1;
    if (searchYear < thisYear)
      thisMonth = 12; 
    String sql = "SELECT sum(qj_hourlong) FROM rst_qj WHERE qj_userId=" + userId + " AND qj_year='" + year + "年' AND qj_type='@type@' AND (qj_isqj=1 or qj_isqj=0)";
    DataSourceBase base = new DataSourceBase();
    Float yiqing = Float.valueOf(0.0F), zonggong = Float.valueOf(0.0F), shengyu = Float.valueOf(0.0F), shijia = Float.valueOf(0.0F), bingjia = Float.valueOf(0.0F), y = Float.valueOf(0.0F);
    String zhiji = "", zhiwu = "", bumen = "", empName = "";
    try {
      base.begin();
      float qunian = getShengNianjia(userId, (new StringBuilder(String.valueOf(searchYear - 1))).toString());
      float qianNian = getYiqing(userId, year);
      ResultSet rs = base.executeQuery(sql.replace("@type@", "年假"));
      if (rs.next())
        yiqing = Float.valueOf(((rs.getString(1) == null) ? Float.valueOf("0") : Float.valueOf(rs.getString(1))).floatValue() / 8.0F); 
      rs.close();
      String nianSql = "SELECT n.rs_njjh_ts,e.empName FROM rs_njjh n right JOIN org_employee e ON upper(n.rs_njjh_yggh)=upper(e.Empnumber) and n.rs_njjh_nf='" + year + "年' " + 
        "WHERE e.emp_id=" + userId + " ";
      rs = base.executeQuery(nianSql);
      if (rs.next()) {
        zonggong = Float.valueOf((rs.getString(1) == null) ? 0.0F : Float.valueOf(rs.getString(1)).floatValue());
        empName = (rs.getString(2) == null) ? "" : rs.getString(2);
      } 
      rs.close();
      if (thisMonth < 7) {
        zonggong = Float.valueOf(zonggong.floatValue() + qunian);
        y = yiqing;
      } else {
        y = Float.valueOf((qunian > qianNian) ? (yiqing.floatValue() - qianNian) : (yiqing.floatValue() - qunian));
      } 
      shengyu = Float.valueOf(zonggong.floatValue() - y.floatValue());
      IO2File.printFile("用户Id：" + userId + (empName.equals("") ? "" : ("    姓名：" + empName)) + 
          "    去年(" + (searchYear - 1) + "年)剩余年假天数：" + qunian + 
          "    今年(" + searchYear + "年)年假总天数：" + zonggong + ((thisMonth < 7) ? ("(包括去年剩余年假天数：" + qunian + ")") : "") + 
          "    " + searchYear + "年前六个月已请年假：" + qianNian + 
          "    " + searchYear + "年实际已请年假天数：" + yiqing + 
          "    " + searchYear + "年前六个月使用今年年假天数：" + y + 
          "    " + searchYear + "年剩余年假天数：" + shengyu, "人寿请假(年假)", 3);
      if (this.printFile == 0)
        IO2File.printFile("获得当年剩余年假信息：select nj_id from rst_nj where nj_year=" + searchYear + " and nj_userId=" + userId, "人寿请假(年假)", 3); 
      rs = base.executeQuery("select nj_id from rst_nj where nj_year=" + searchYear + " and nj_userId=" + userId);
      if (rs.next()) {
        String executeSql = "update rst_nj set nj_shengyu=" + shengyu + ", nj_zong=" + zonggong + ",nj_yiqing=" + yiqing + " ,nj_qunian=" + qunian + 
          " ,nj_qiansan=" + qianNian + " where nj_year=" + searchYear + " and nj_userId=" + userId;
        if (this.printFile == 0)
          IO2File.printFile("更新当年剩余年假信息：" + executeSql, "人寿请假(年假)", 3); 
        base.executeUpdate(executeSql);
      } else {
        String executeSql = "insert into rst_nj (nj_id,nj_year,nj_userId,nj_shengyu,nj_zong,nj_yiqing,nj_quNian,nj_qianSan) values (hibernate_sequence.nextval," + 
          searchYear + "," + userId + "," + shengyu + "," + zonggong + "," + yiqing + "," + qunian + "," + qianNian + ")";
        if (this.printFile == 0)
          IO2File.printFile("插入当年剩余年假信息：" + executeSql, "人寿请假(年假)", 3); 
        base.executeUpdate(executeSql);
      } 
      rs.close();
      if (this.printFile == 0) {
        rs = base.executeQuery(sql.replace("@type@", "事假"));
        if (rs.next())
          shijia = Float.valueOf(((rs.getString(1) == null) ? Float.valueOf("0") : Float.valueOf(rs.getString(1))).floatValue() / 8.0F); 
        rs.close();
        rs = base.executeQuery(sql.replace("@type@", "病假"));
        if (rs.next())
          bingjia = Float.valueOf(((rs.getString(1) == null) ? Float.valueOf("0") : Float.valueOf(rs.getString(1))).floatValue() / 8.0F); 
        rs.close();
        String empSql = "select e.empdutylevel,e.empduty,o.Orgnamestring from org_employee e JOIN org_organization_user ou ON e.emp_id=ou.emp_id JOIN org_organization o ON ou.org_id=o.org_id where e.emp_id=" + 
          
          userId;
        rs = base.executeQuery(empSql);
        if (rs.next()) {
          zhiji = (rs.getString(1) == null) ? "" : rs.getString(1);
          zhiwu = (rs.getString(2) == null) ? "" : rs.getString(2);
          bumen = (rs.getString(3) == null) ? "" : rs.getString(3);
        } 
        rs.close();
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    return new String[] { (String)zonggong, (String)shengyu, (String)shijia, (String)bingjia, zhiji, zhiwu, bumen, (String)yiqing };
  }
  
  public String getNianjia(String userId, String year) {
    String[] data = getNianjiaData(userId, year);
    String xml = "[{k:'rs_qjspd_njts',v:'" + data[0] + "'},{k:'rs_qjspd_synjts',v:'" + data[1] + "'}," + 
      "{k:'rs_qjspd_ljsjts',v:'" + data[2] + "'},{k:'rs_qjspd_ljbjts',v:'" + data[3] + "'}," + 
      "{k:'rs_qjspd_zj',v:'" + data[4] + "'},{k:'rs_qjspd_zw',v:'" + data[5] + "'}," + 
      "{k:'rs_qjspd_wtrbm',v:'" + data[6] + "'}]";
    return xml;
  }
  
  public float getShengNianjia(String userId, String year) {
    Float shengyu = Float.valueOf(0.0F);
    if (Integer.valueOf(year).intValue() >= 2014) {
      DataSourceBase base = new DataSourceBase();
      boolean exits = false;
      try {
        base.begin();
        String sql = "select nj_shengyu from rst_nj where nj_year=" + year + " and nj_userId=" + userId;
        if (this.printFile == 0)
          IO2File.printFile("取得(" + year + "年)剩余年假天数：" + sql, "人寿请假(年假)", 3); 
        ResultSet rSet = base.executeQuery(sql);
        if (rSet.next()) {
          shengyu = Float.valueOf((rSet.getString(1) == null) ? 0.0F : rSet.getFloat(1));
          exits = true;
        } 
        rSet.close();
        if (!exits) {
          sql = "select empnumber from org_employee where emp_id=" + userId;
          rSet = base.executeQuery(sql);
          String empNumber = "0";
          if (rSet.next())
            empNumber = rSet.getString(1); 
          rSet.close();
          String yearShengyu = "0";
          sql = "select rs_njjh_ts from RS_NJJH t where rs_njjh_yggh='" + empNumber + "' and rs_njjh_nf like '" + year + "%'";
          rSet = base.executeQuery(sql);
          if (rSet.next())
            yearShengyu = rSet.getString(1); 
          rSet.close();
          shengyu = Float.valueOf(Float.parseFloat(yearShengyu));
          sql = "insert into rst_nj (nj_id,nj_year,nj_userId,nj_shengyu,nj_zong,nj_yiqing,nj_quNian,nj_qianSan) values (hibernate_sequence.nextval," + 
            year + "," + userId + ",'" + yearShengyu + "',0,0,0,0)";
          if (this.printFile == 0)
            IO2File.printFile("插入(" + year + "年)剩余年假信息：" + sql, "人寿请假(年假)", 3); 
          base.executeUpdate(sql);
          if ("2014".equals(year))
            shengyu = Float.valueOf(get2014(userId)); 
        } 
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        base.end();
      } 
    } 
    return (shengyu.floatValue() < 0.0F) ? 0.0F : shengyu.floatValue();
  }
  
  public float get2014(String userId) {
    float yiqing = 0.0F, zonggong = 0.0F, shengyu = 0.0F;
    String empName = "";
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      String sql = "SELECT sum(qj_hourlong) FROM rst_qj WHERE qj_userId=" + 
        userId + " AND qj_year='2014年' AND qj_type='年假' AND (qj_isqj=1 or qj_isqj=0)";
      if (this.printFile == 0)
        IO2File.printFile("获得前一年(2014年)的年假总数sql:" + sql, "人寿请假(年假)", 3); 
      ResultSet rs = base.executeQuery(sql);
      if (rs.next())
        yiqing = ((rs.getString(1) == null) ? Float.valueOf("0") : Float.valueOf(rs.getString(1))).floatValue() / 8.0F; 
      rs.close();
      sql = "SELECT n.rs_njjh_ts,e.empdutylevel,e.empduty,o.Orgnamestring,e.empName FROM rs_njjh n right JOIN org_employee e ON upper(n.rs_njjh_yggh)=upper(e.Empnumber) and n.rs_njjh_nf='2014年' JOIN org_organization_user ou ON e.emp_id=ou.emp_id JOIN org_organization o ON ou.org_id=o.org_id WHERE e.emp_id=" + 

        
        userId;
      if (this.printFile == 0)
        IO2File.printFile("获得前一年(2014年)的年假总天数sql:" + sql, "人寿请假(年假)", 3); 
      rs = base.executeQuery(sql);
      if (rs.next()) {
        zonggong = ((rs.getString(1) == null) ? Float.valueOf("0") : Float.valueOf(rs.getString(1))).floatValue();
        empName = (rs.getString(5) == null) ? "" : rs.getString(5);
      } 
      rs.close();
      shengyu = zonggong - yiqing;
      if (this.printFile == 0)
        IO2File.printFile("姓名：" + empName + "  年份：2014   年假总天数：" + zonggong + "  已请年假：" + yiqing + "   剩余年假：" + shengyu, "人寿请假(年假)", 3); 
      if (this.printFile == 0)
        IO2File.printFile("更新年假表：update rst_nj set nj_shengyu=" + shengyu + ",nj_zong=" + zonggong + ",nj_yiqing=" + yiqing + " " + 
            "where nj_year=2014 and nj_userId=" + userId, 3); 
      base.executeUpdate("update rst_nj set nj_shengyu=" + shengyu + ",nj_zong=" + zonggong + ",nj_yiqing=" + yiqing + " " + 
          "where nj_year=2014 and nj_userId=" + userId);
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return shengyu;
  }
  
  public float getYiqing(String userId, String year) {
    Float yiqing = Float.valueOf(0.0F);
    if (Integer.valueOf(year).intValue() > 2014) {
      String sql = "SELECT sum(qj_hourlong) FROM rst_qj WHERE qj_userId=" + 
        userId + " AND qj_year='" + year + "年' AND qj_type='年假' " + 
        
        "AND (qj_isqj=1 or qj_isqj=0) and qj_month in ('1月份','2月份','3月份','4月份','5月份','6月份')";
      DataSourceBase base = new DataSourceBase();
      if (this.printFile == 0)
        IO2File.printFile("获得(" + year + "年)前三个月的年假sql:" + sql, "人寿请假(年假)", 3); 
      try {
        base.begin();
        ResultSet rs = base.executeQuery(sql);
        if (rs.next())
          yiqing = Float.valueOf(((rs.getString(1) == null) ? Float.valueOf("0") : Float.valueOf(rs.getString(1))).floatValue() / 8.0F); 
        rs.close();
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        base.end();
      } 
    } 
    return yiqing.floatValue();
  }
  
  public String getDanhao(String recordId) {
    String result = "";
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      String sql = "select rs_qjspd_qjdbh from jst_3003 where jst_3003_id=" + recordId;
      ResultSet rs = base.executeQuery(sql);
      if (rs.next())
        result = rs.getString(1); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    return result;
  }
  
  public String buqianYanzheng(String userId, String beginDate, String endDate) {
    String result = "1";
    DataSourceBase base = new DataSourceBase();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Long tLong = Long.valueOf((new Date()).getTime() / 86400000L);
    try {
      Date b = format.parse(beginDate);
      Date e = format.parse(endDate);
      Long bLong = Long.valueOf(b.getTime() / 86400000L + 1L);
      Long eLong = Long.valueOf(e.getTime() / 86400000L + 1L);
      base.begin();
      for (long l = bLong.longValue(); l <= eLong.longValue() && result.equals("1"); l++) {
        if (tLong.longValue() - bLong.longValue() > 3L) {
          result = "2";
        } else {
          result = "0";
          String dateString = format.format(new Date(l * 86400000L));
          String sql = "SELECT kq_id FROM rst_kq WHERE kq_userId=" + userId + " AND kq_date='" + dateString + "'";
          ResultSet rs = base.executeQuery(sql);
          if (rs.next())
            result = "1"; 
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    return result;
  }
}
