package com.js.oa.hr.subsidiarywork.bean;

import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import com.js.util.util.LunarCalendar;
import com.js.util.util.StringSplit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class DesktopEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public List getWishRemind(String userId, String orgIdString) throws Exception {
    List list = null;
    begin();
    try {
      orgIdString = "$" + orgIdString + "$";
      String[] orgIdArray = orgIdString.split("\\$\\$");
      StringBuffer buffer = new StringBuffer();
      int k = 0;
      for (int i = 0; i < orgIdArray.length; i++) {
        if (!"".equals(orgIdArray[i])) {
          if (k == 0) {
            buffer.append(" (remind.orgRange like '%*").append(
                orgIdArray[i]).append("*%'");
          } else {
            buffer.append(" or remind.orgRange like '%*").append(
                orgIdArray[i]).append("*%'");
          } 
          k++;
        } 
      } 
      if (k > 0) {
        buffer.append(" or remind.orgRange is null)");
      } else {
        buffer.append(" remind.orgRange is null");
      } 
      String birthWish = " remind.wishCard in(select bpo.id from com.js.oa.hr.subsidiarywork.po.BirthdayWishPO bpo)";
      String festivalWish = " remind.wishCard in (select fpo.id from com.js.oa.hr.subsidiarywork.po.FestivalSetPO fpo)";
      list = this.session.createQuery("select remind.festivalName,remind.remindType,remind.wishCard,remind.wishText from com.js.oa.hr.subsidiarywork.po.OARemindPO remind where (remind.remindType=1 and remind.empId=" + userId + " and " + birthWish + ") or (remind.remindType=0 and " + buffer.toString() + " and " + festivalWish + ")").list();
    } catch (Exception ex) {
      System.out.println("DesktopEJB error:" + ex);
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public void executeWishRemind() {
    Connection conn = null;
    Connection dsConn = null;
    Statement stmt = null;
    Statement dsStmt = null;
    ResultSet rs = null;
    Calendar today = Calendar.getInstance();
    Calendar festivalDay = Calendar.getInstance();
    Calendar shouldRemind = Calendar.getInstance();
    int currentYear = today.get(1);
    int currentMonth = today.get(2);
    int currentDay = today.get(5);
    LunarCalendar lunarCalendar = new LunarCalendar();
    lunarCalendar.setLunar(today);
    int lunarYear = lunarCalendar.getLunarYear();
    int lunarMonth = lunarCalendar.getLunarMonth();
    int lunarDay = lunarCalendar.getLunarDay();
    boolean lunarLeap = lunarCalendar.getLunarLeap();
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      dsConn = (new DataSourceBase()).getDataSource().getConnection();
      dsStmt = dsConn.createStatement();
      int index = 0;
      stmt.executeUpdate("delete from JSDB.oa_remind");
      rs = stmt.executeQuery("select festival_id,calendarType,appointYear,festivalName,festiveYear,festiveMonth,festiveDay,festivalRemind,festivalWish,festivalWishCard,useRange from JSDB.oa_festivalSet");
      while (rs.next()) {
        index++;
        String festivalWishCard = rs.getString("festival_id");
        int calendarType = rs.getInt("calendarType");
        int appointYear = rs.getInt("appointYear");
        String festivalName = rs.getString("festivalName");
        int festiveYear = rs.getInt("festiveYear");
        int festiveMonth = rs.getInt("festiveMonth");
        int festiveDay = rs.getInt("festiveDay");
        int festivalRemind = rs.getInt("festivalRemind");
        String festivalWish = rs.getString("festivalWish");
        String useRange = rs.getString("useRange");
        if (calendarType == 0) {
          festiveMonth--;
          if (appointYear == 0) {
            if (currentMonth < festiveMonth || (currentMonth == festiveMonth && currentDay <= festiveDay)) {
              festivalDay.set(currentYear, festiveMonth, festiveDay);
            } else {
              festivalDay.set(currentYear + 1, festiveMonth, festiveDay);
            } 
          } else {
            if (festiveYear < currentYear || currentYear + 1 < festiveYear)
              continue; 
            festivalDay.set(festiveYear, festiveMonth, festiveDay);
          } 
          shouldRemind.setTime(festivalDay.getTime());
          int j = shouldRemind.get(6);
          if (festivalRemind > j)
            shouldRemind.roll(1, -1); 
          shouldRemind.roll(6, -festivalRemind);
          if ((today.after(shouldRemind) || today.equals(shouldRemind)) && (today.before(festivalDay) || today.equals(festivalDay)))
            dsStmt.addBatch("insert into JSDB.oa_remind(remind_id,emp_id,orgRange,remindType,wishText,wishCard,festivalName) values (" + index + ",-1,'" + useRange + "',0,'" + festivalWish + "','" + festivalWishCard + "','" + festivalName + "')"); 
          continue;
        } 
        if (appointYear == 0) {
          if (lunarMonth < festiveMonth || (lunarMonth == festiveMonth && lunarDay <= festiveDay)) {
            festiveYear = lunarYear;
          } else {
            festiveYear = lunarYear + 1;
          } 
        } else {
          if (festiveYear < lunarYear || lunarYear + 1 < festiveYear)
            continue; 
          festiveYear = lunarYear;
        } 
        int offset = lunarCalendar.getScan(lunarYear, lunarMonth, lunarDay, lunarLeap, festiveYear, festiveMonth, festiveDay);
        if (offset <= festivalRemind)
          dsStmt.addBatch("insert into JSDB.oa_remind(remind_id,emp_id,orgRange,remindType,wishText,wishCard,festivalName) values (" + index + ",-1,'" + useRange + "',0,'" + festivalWish + "','" + festivalWishCard + "','" + festivalName + "')"); 
      } 
      rs.close();
      dsStmt.executeBatch();
      dsStmt.clearBatch();
      List<String> list = new ArrayList();
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        rs = stmt.executeQuery("select emp_id from JSDB.org_employee where MONTH(EMPBIRTH)=" + (currentMonth + 1) + " AND DAYOFMONTH(EMPBIRTH)=" + currentDay);
      } else {
        rs = stmt.executeQuery("select emp_id from JSDB.org_employee where JSDB.FN_DATENAME('MONTH', EMPBIRTH)=" + (currentMonth + 1) + " AND JSDB.FN_DATENAME('DAY', EMPBIRTH)=" + currentDay);
      } 
      while (rs.next())
        list.add(rs.getString(1)); 
      rs.close();
      StringBuffer where = new StringBuffer();
      int flag = 0;
      for (int i = 0; i < list.size(); i++) {
        where = new StringBuffer();
        index++;
        int empId = Integer.parseInt(list.get(i).toString());
        rs = stmt.executeQuery("select org.orgIdString from JSDB.org_organization org,JSDB.org_organization_user orgUser where org.org_id=orgUser.org_id and orgUser.emp_id=" + empId);
        if (rs.next()) {
          String orgIdString = rs.getString(1);
          orgIdString = StringSplit.splitOrgIdString(orgIdString, "$", 
              "_");
          orgIdString = "$" + orgIdString + "$";
          String[] orgIdArray = orgIdString.split("\\$\\$");
          for (int j = 0; j < orgIdArray.length; j++) {
            if (!"".equals(orgIdArray[j])) {
              if (flag == 0) {
                where.append(" or (wishEmployees like '%*")
                  .append(
                    orgIdArray[j]).append("*%'");
              } else {
                where.append(" or wishEmployees like '%*")
                  .append(
                    orgIdArray[j]).append("*%'");
              } 
              flag++;
            } 
          } 
        } 
        rs.close();
        if (flag > 0) {
          where.append(")");
          flag = 0;
        } 
        rs = stmt.executeQuery("select wishContent,wish_id,wishSign from JSDB.oa_birthdayWish where wishEmployees like '%$" + empId + "$%' " + where.toString());
        if (rs.next()) {
          dsStmt.addBatch("insert into JSDB.oa_remind(remind_id,emp_id,remindType,wishText,wishCard) values(" + index + "," + empId + ",1,'" + rs.getString(1) + "','" + rs.getString(2) + "')");
        } else {
          dsStmt.addBatch("insert into JSDB.oa_remind(remind_id,emp_id,remindType) values(" + index + "," + empId + ",1)");
        } 
        rs.close();
      } 
      dsStmt.executeBatch();
      stmt.close();
      dsStmt.close();
      conn.close();
      dsConn.close();
    } catch (Exception ex) {
      System.out.println("error executeRemind of RemindTask!");
      ex.printStackTrace();
    } 
  }
}
