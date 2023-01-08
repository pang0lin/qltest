package com.js.oa.chinaLife.util;

import com.js.oa.chinaLife.bean.KqShowBean;
import com.js.oa.chinaLife.kemi.KqDataFromKemi;
import com.js.system.service.messages.RemindUtil;
import com.js.util.util.DataSourceBase;
import com.js.util.util.DataSourceUtil;
import com.js.util.util.IO2File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ChinaLifeKq {
  private SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
  
  private String message = "1";
  
  public ChinaLifeKq(String message) {
    setMessage(message);
  }
  
  public ChinaLifeKq() {}
  
  public void kqTongbu(int num, String dateStr) {
    IO2File.printFile(String.valueOf(dateStr) + "开始同步考勤", "kemiKq.txt", 3);
    (new KqDataFromKemi()).getKqData(dateStr, dateStr, num);
    kqDate(num, dateStr);
    remindUser(num, dateStr);
  }
  
  public void insertUserKqDefault(String dateStr) {
    IO2File.printFile("开始同步考勤人员数据……开始", "人寿请假", 3);
    String isHoliday = IsHoliday.isHoliday(dateStr);
    int holiday = 4;
    if (isHoliday.equals("0"))
      holiday = 6; 
    String sql = "insert into rst_kq (kq_id,kq_userName,kq_userOrg,kq_duty,kq_userId,kq_userNum,kq_date,kq_sbType,kq_xbType,kq_sbremark,kq_xbremark) values (hibernate_sequence.nextval,?,?,?,?,?,'" + 
      dateStr + "',?,?,?,?)";
    String userSql = "select e.emp_id,e.empName,e.empNumber,ou.org_id from org_employee e join org_organization_user ou on e.emp_id=ou.emp_id where e.Userisactive=1 and e.Userisdeleted=0 and e.emp_id>0 and e.Empnumber is not null";
    List<String[]> userList = (new DataSourceUtil()).getListQuery(userSql, "");
    DataSourceBase base = new DataSourceBase();
    Connection conn = null;
    PreparedStatement stat = null;
    try {
      conn = base.getDataSource().getConnection();
      stat = conn.prepareStatement(sql);
      for (int i = 0; i < userList.size(); i++) {
        String[] user = userList.get(i);
        stat.setString(1, user[1]);
        stat.setLong(2, Long.valueOf(user[3]).longValue());
        stat.setString(3, "");
        stat.setLong(4, Long.valueOf(user[0]).longValue());
        stat.setString(5, user[2]);
        stat.setInt(6, holiday);
        stat.setInt(7, holiday);
        stat.setInt(8, holiday);
        stat.setInt(9, holiday);
        stat.executeUpdate();
      } 
      stat.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (conn != null)
          conn.close(); 
      } catch (Exception e2) {
        e2.printStackTrace();
      } 
    } 
    IO2File.printFile("开始同步考勤人员数据……结束", "人寿请假", 3);
  }
  
  private void kqDate(int num, String dateStr) {
    String sql = "";
    if (num == 1) {
      sql = "select punch_emp,min(punch_num),punch_date,punch_oaId,min(punch_time) from kq_punch where punch_date='" + 
        dateStr + "' and punch_ci=1 " + 
        "group by punch_emp,punch_date,punch_oaId order by punch_emp";
      IO2File.printFile("取上午考勤：" + sql, "人寿考勤", 3);
      List<String[]> dataList = (new DataSourceUtil()).getListQuery(sql, "");
      kqDataAm(dataList);
    } else {
      DataSourceUtil util = new DataSourceUtil();
      sql = "select punch_emp, min(punch_num),punch_date,punch_oaId,min(punch_time) from kq_punch where punch_date='" + 
        dateStr + "' and punch_num<=720 " + 
        "group by punch_emp,punch_date,punch_oaId order by punch_emp";
      IO2File.printFile("取上午考勤：" + sql, "人寿考勤", 3);
      List<String[]> dataList = util.getListQuery(sql, "");
      kqDataAm(dataList);
      sql = "select punch_emp, max(punch_num),punch_date,punch_oaId,max(punch_time) from kq_punch where punch_date='" + 
        dateStr + "' and punch_num>720 and punch_ci=2 " + 
        "group by punch_emp,punch_date,punch_oaId order by punch_emp";
      IO2File.printFile("取下午考勤：" + sql, "人寿考勤", 3);
      dataList = util.getListQuery(sql, "");
      kqDataPm(dataList);
    } 
  }
  
  private void kqDataAm(List<String[]> dataList) {
    DataSourceBase base = new DataSourceBase();
    String timeSql = "update rst_kq set kq_sb='?1',kq_sbTime='?1' where kq_userId=?3 and kq_date='?4' ";
    String typeSql = "update rst_kq set kq_sbType=?2,kq_sbremark=?2 where kq_userId=?3 and kq_date='?4' and kq_sbType=4";
    try {
      base.begin();
      for (int i = 0; i < dataList.size(); i++) {
        String type = "4";
        String[] data = dataList.get(i);
        String isHoliday = IsHoliday.isHoliday(data[2]);
        if (isHoliday.equals("0")) {
          type = "6";
        } else if (Integer.valueOf(data[1]).intValue() > 540) {
          type = "2";
        } else {
          type = "1";
        } 
        IO2File.printFile("上午考勤同步时间sql:" + timeSql.replace("?1", data[4]).replace("?3", data[3]).replace("?4", data[2]), "人寿考勤", 3);
        base.addBatch(timeSql.replace("?1", data[4]).replace("?3", data[3]).replace("?4", data[2]));
        IO2File.printFile("上午考勤同步状态sql:" + typeSql.replace("?3", data[3]).replace("?4", data[2]).replaceAll("\\?2", type), "人寿考勤", 3);
        base.addBatch(typeSql.replace("?3", data[3]).replace("?4", data[2]).replaceAll("\\?2", type));
        if (i % 50 == 0)
          base.executeBatch(); 
      } 
      base.executeBatch();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
  }
  
  private void kqDataPm(List<String[]> dataList) {
    DataSourceBase base = new DataSourceBase();
    String sql = "update rst_kq set kq_xb='?1',kq_xbTime='?1',kq_xbType=?2,kq_xbremark=?2 where kq_userId=?3 and kq_date='?4' and kq_xbType=4";
    try {
      base.begin();
      for (int i = 0; i < dataList.size(); i++) {
        String sqlE = sql;
        String[] data = dataList.get(i);
        String isHoliday = IsHoliday.isHoliday(data[2]);
        if (isHoliday.equals("0")) {
          sqlE = sqlE.replaceAll("\\?2", "6");
        } else if (Integer.valueOf(data[1]).intValue() < 1020) {
          sqlE = sqlE.replaceAll("\\?2", "2");
        } else {
          sqlE = sqlE.replaceAll("\\?2", "1");
        } 
        sqlE = sqlE.replace("?1", data[4]).replace("?3", data[3]).replace("?4", data[2]);
        IO2File.printFile("下午考勤同步sql:" + sqlE, "人寿考勤", 3);
        base.addBatch(sqlE);
        if (i % 50 == 0)
          base.executeBatch(); 
      } 
      base.executeBatch();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
  }
  
  private void updateKqData(Map<String, String> map, String dateStr, String userId) {
    DataSourceBase base = new DataSourceBase();
    String updateSql = "";
    for (String key : map.keySet())
      updateSql = String.valueOf(updateSql) + "," + key + "=" + (String)map.get(key); 
    String sql = "update rst_kq set " + updateSql.substring(1) + " where kq_userId=" + userId + " and kq_date='" + dateStr + "'";
    try {
      base.begin();
      base.executeUpdate(sql);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
  }
  
  private void remindUser(int num, String dateStr) {
    String sql = "";
    if (num == 1) {
      sql = "select kq_userId from rst_kq where kq_sbType=4 and kq_date='" + dateStr + "'";
    } else {
      sql = "select kq_userId from rst_kq where kq_xbType=4 and kq_date='" + dateStr + "'";
    } 
    String userIds = "";
    DataSourceBase base = new DataSourceBase();
    KqShowBean kqBean = new KqShowBean();
    List<String> sqlist = new ArrayList<String>();
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      while (rs.next()) {
        if (num == 1) {
          if (kqBean.isQingjia(rs.getString(1), dateStr, "09:00")) {
            sqlist.add("update rst_kq set kq_sbType=5 where kq_userId=" + rs.getString(1) + " and kq_date='" + dateStr + "' and kq_sbType<>6");
            continue;
          } 
          userIds = String.valueOf(userIds) + "," + rs.getString(1);
          continue;
        } 
        if (kqBean.isQingjia(rs.getString(1), dateStr, "17:00")) {
          sqlist.add("update rst_kq set kq_xbType=5 where kq_userId=" + rs.getString(1) + " and kq_date='" + dateStr + "' and kq_xbType<>6");
          continue;
        } 
        userIds = String.valueOf(userIds) + "," + rs.getString(1);
      } 
      rs.close();
      for (int i = 0; i < sqlist.size(); i++) {
        IO2File.printFile("考勤同步请假修改状态sql:" + (String)sqlist.get(i), "人寿考勤", 3);
        base.addBatch(sqlist.get(i));
      } 
      base.executeBatch();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    if ("1".equals(getMessage()) && !"".equals(userIds))
      remindPerson(userIds, num, dateStr); 
  }
  
  private void remindPerson(String userIds, int num, String dateStr) {
    String title = "";
    if (num == 1) {
      title = "您在" + dateStr + "上午上班未打卡，请补签！";
    } else {
      title = "您在" + dateStr + "下午下班未打卡，请补签！";
    } 
    try {
      String url = "";
      RemindUtil.sendMessageToUsers2(title, url, userIds, "kq", new Date(), this.ymd.parse("2050-01-01"), "系统提醒", Long.valueOf(0L), 2);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public String getMessage() {
    return this.message;
  }
  
  public void setMessage(String message) {
    this.message = message;
  }
}
