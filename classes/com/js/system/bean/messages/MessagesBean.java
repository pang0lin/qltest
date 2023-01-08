package com.js.system.bean.messages;

import com.js.oa.userdb.util.DbOpt;
import com.js.system.vo.messages.MessagesStatusSet;
import com.js.system.vo.messages.MessagesVO;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.Query;

public class MessagesBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public void messageAdd(MessagesVO message) throws Exception {
    begin();
    try {
      this.session.save(message);
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
  }
  
  public void messageUpdate(long messageID) {
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      PreparedStatement pstmt = conn.prepareStatement("update sys_messages set message_status=0 where message_id=?");
      pstmt.setLong(1, messageID);
      pstmt.executeUpdate();
      pstmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
      ex.printStackTrace();
    } 
  }
  
  public List selectByUserID(String userId) throws Exception {
    List list = new ArrayList();
    begin();
    try {
      Query query = null;
      String audit = "";
      if (SystemCommon.getAudit() == 0)
        audit = " and massges.message_type<>'audit' "; 
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        query = this.session.createQuery("select massges from com.js.system.vo.messages.MessagesVO massges where massges.message_status=1 and  massges.message_toUserId =:userId and massges.message_date_begin <= now() and massges.message_date_end >= now() " + audit + " order by massges.message_id desc").setString("userId", userId);
      } else if (databaseType.indexOf("oracle") >= 0) {
        query = this.session.createQuery("select massges from com.js.system.vo.messages.MessagesVO massges where massges.message_status=1 and  massges.message_toUserId =:userId and massges.message_date_begin <= sysdate and massges.message_date_end >= sysdate " + audit + " order by massges.message_id desc").setString("userId", userId);
      } else {
        query = this.session.createQuery("select massges from com.js.system.vo.messages.MessagesVO massges where massges.message_status=1 and  massges.message_toUserId =:userId and massges.message_date_begin <= getdate() and massges.message_date_end >= getdate() " + audit + " order by massges.message_id desc").setString("userId", userId);
      } 
      query.setFirstResult(0);
      query.setMaxResults(20);
      list = query.list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public int selectCountByUserID(String userId) throws Exception {
    Integer cum = Integer.valueOf(0);
    begin();
    try {
      Query query = null;
      String databaseType = SystemCommon.getDatabaseType();
      String message_type = "";
      if (SystemCommon.getAudit() == 0)
        message_type = " and massges.message_type<>'audit' "; 
      if (databaseType.indexOf("mysql") >= 0) {
        query = this.session.createQuery("select count(massges.message_id) from com.js.system.vo.messages.MessagesVO massges where massges.message_status=1" + message_type + " and  massges.message_toUserId=:userId and massges.message_date_begin <= now() and massges.message_date_end >= now()").setString("userId", userId);
      } else if (databaseType.indexOf("oracle") >= 0) {
        query = this.session.createQuery("select count(massges.message_id) from com.js.system.vo.messages.MessagesVO massges where massges.message_status=1" + message_type + " and  massges.message_toUserId =:userId and massges.message_date_begin <= sysdate and massges.message_date_end >= sysdate").setString("userId", userId);
      } else {
        query = this.session.createQuery("select count(massges.message_id) from com.js.system.vo.messages.MessagesVO massges where massges.message_status=1" + message_type + " and  massges.message_toUserId =:userId and massges.message_date_begin <= getdate() and massges.message_date_end >= getdate()").setString("userId", userId);
      } 
      cum = (Integer)query.uniqueResult();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return cum.intValue();
  }
  
  public void delBatch(String ids, String type, String timeScan, String userId) throws Exception {
    Statement stmt = null;
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      String where = " where message_id <> 0 and message_id in(" + ids + "-1) ";
      String databaseType = SystemCommon.getDatabaseType();
      if (!"".equals(timeScan)) {
        where = String.valueOf(where) + " and message_toUserId =" + userId;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (!"all".equals(timeScan)) {
          Calendar calendar = Calendar.getInstance();
          calendar.setTime(new Date());
          if ("new".equals(timeScan)) {
            if (databaseType.indexOf("mysql") >= 0) {
              where = String.valueOf(where) + " and message_time < '" + dateFormat.format(calendar.getTime()) + "'";
            } else if (databaseType.indexOf("oracle") >= 0) {
              where = String.valueOf(where) + " and message_time < to_date('" + dateFormat.format(calendar.getTime()) + "','YYYY-MM-DD HH24:MI:SS') ";
            } 
            calendar.add(6, -7);
            if (databaseType.indexOf("mysql") >= 0) {
              where = String.valueOf(where) + " and message_time > '" + dateFormat.format(calendar.getTime()) + "'";
            } else if (databaseType.indexOf("oracle") >= 0) {
              where = String.valueOf(where) + " and message_time > to_date('" + dateFormat.format(calendar.getTime()) + "','YYYY-MM-DD HH24:MI:SS') ";
            } 
          } else if ("week".equals(timeScan)) {
            calendar.add(6, -7);
            if (databaseType.indexOf("mysql") >= 0) {
              where = String.valueOf(where) + " and message_time < '" + dateFormat.format(calendar.getTime()) + "'";
            } else if (databaseType.indexOf("oracle") >= 0) {
              where = String.valueOf(where) + " and message_time < to_date('" + dateFormat.format(calendar.getTime()) + "','YYYY-MM-DD HH24:MI:SS') ";
            } 
          } else if ("month".equals(timeScan)) {
            calendar.add(2, -1);
            if (databaseType.indexOf("mysql") >= 0) {
              where = String.valueOf(where) + " and message_time < '" + dateFormat.format(calendar.getTime()) + "'";
            } else if (databaseType.indexOf("oracle") >= 0) {
              where = String.valueOf(where) + " and message_time < to_date('" + dateFormat.format(calendar.getTime()) + "','YYYY-MM-DD HH24:MI:SS') ";
            } 
          } else if ("tmonth".equals(timeScan)) {
            calendar.add(2, -3);
            if (databaseType.indexOf("mysql") >= 0) {
              where = String.valueOf(where) + " and message_time < '" + dateFormat.format(calendar.getTime()) + "'";
            } else if (databaseType.indexOf("oracle") >= 0) {
              where = String.valueOf(where) + " and message_time < to_date('" + dateFormat.format(calendar.getTime()) + "','YYYY-MM-DD HH24:MI:SS') ";
            } 
          } else if ("hyear".equals(timeScan)) {
            calendar.add(2, -6);
            if (databaseType.indexOf("mysql") >= 0) {
              where = String.valueOf(where) + " and message_time < '" + dateFormat.format(calendar.getTime()) + "'";
            } else if (databaseType.indexOf("oracle") >= 0) {
              where = String.valueOf(where) + " and message_time < to_date('" + dateFormat.format(calendar.getTime()) + "','YYYY-MM-DD HH24:MI:SS') ";
            } 
          } else if ("year".equals(timeScan)) {
            calendar.add(2, -12);
            if (databaseType.indexOf("mysql") >= 0) {
              where = String.valueOf(where) + " and message_time < '" + dateFormat.format(calendar.getTime()) + "'";
            } else if (databaseType.indexOf("oracle") >= 0) {
              where = String.valueOf(where) + " and message_time < to_date('" + dateFormat.format(calendar.getTime()) + "','YYYY-MM-DD HH24:MI:SS') ";
            } 
          } 
        } else if (databaseType.indexOf("mysql") >= 0) {
          where = String.valueOf(where) + " and message_time < '" + dateFormat.format(new Date()) + "'";
        } else if (databaseType.indexOf("oracle") >= 0) {
          where = String.valueOf(where) + " and message_time < to_date('" + dateFormat.format(new Date()) + "','YYYY-MM-DD HH24:MI:SS') ";
        } 
      } else if (ids != null) {
        ids.equals("");
      } 
      if (ids != null && !ids.equals(""))
        if ("del".equals(type)) {
          stmt.executeUpdate("delete from sys_messages " + where);
        } else {
          stmt.executeUpdate("update sys_messages set message_status=0 " + where);
        }  
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception(e.getMessage());
    } finally {
      if (stmt != null)
        stmt.close(); 
      if (conn != null)
        conn.close(); 
      this.transaction = null;
    } 
  }
  
  public void setMessageStatus(String emp_id, String status) throws Exception {
    try {
      begin();
      if (!"".equals(emp_id))
        this.session.delete(" from com.js.system.vo.messages.MessagesStatusSet po where po.emp_id =" + emp_id); 
      this.session.flush();
      if ("1".equals(status)) {
        MessagesStatusSet messagesStatusSet = new MessagesStatusSet();
        messagesStatusSet.setEmp_id(Long.parseLong(emp_id));
        messagesStatusSet.setStatus(1);
        this.session.save(messagesStatusSet);
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception(e.getMessage());
    } finally {
      this.session.close();
    } 
  }
  
  public void clearMessageStatus() throws Exception {
    try {
      begin();
      this.session.delete(
          " from com.js.system.vo.messages.MessagesStatusSet po ");
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception(e.getMessage());
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public String serchMessageStatus(String emp_id) throws Exception {
    String re = "N";
    try {
      begin();
      List list = this.session.createQuery("from com.js.system.vo.messages.MessagesStatusSet po where po.emp_id =" + emp_id).list();
      if (!list.isEmpty())
        re = "Y"; 
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception(e.getMessage());
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return re;
  }
  
  public void changeMessageStatus(String data_id, String userId, String type, String be) throws Exception {
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        dbopt.executeUpdate("update sys_messages set message_status =0 where data_id =" + data_id + " and  message_toUserId=" + userId + " and message_type ='" + type + "' and message_date_begin <= now() and message_date_end >= now()");
      } else if (databaseType.indexOf("oracle") >= 0) {
        dbopt.executeUpdate("update sys_messages set message_status =0 where data_id =" + data_id + " and  message_toUserId=" + userId + " and message_type ='" + type + "' and message_date_begin <= sysdate and message_date_end >= sysdate");
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
  }
  
  public void delMessage(String data_id, String type, String be) throws Exception {
    try {
      begin();
      this.session.delete(" from com.js.system.vo.messages.MessagesVO po where po.data_id =" + data_id + " and po.message_type like '" + type + "' and  po.message_status=1 ");
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
  }
  
  public void changeMessageStatusForBbs(String data_id, String type, String be) throws Exception {
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        dbopt.executeUpdate("update sys_messages set message_status =0 where data_id =" + data_id + " and message_type ='" + type + "' and message_date_begin <= now() and message_date_end >= now()");
      } else if (databaseType.indexOf("oracle") >= 0) {
        dbopt.executeUpdate("update sys_messages set message_status =0 where data_id =" + data_id + " and message_type ='" + type + "' and message_date_begin <= sysdate and message_date_end >= sysdate");
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
  }
  
  public void changeTaskStatus(String data_id, String userId) throws Exception {
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      if (!"".equals(userId)) {
        dbopt.executeUpdate("update sys_messages set message_status =0 where data_id =" + data_id + " and  message_toUserId=" + userId + " and message_type in ('NewTask','TaskReport','TaskKaohe','TaskKaoheResult','TaskCancel')");
      } else {
        dbopt.executeUpdate("update sys_messages set message_status =0 where data_id =" + data_id + " and message_type in ('NewTask','TaskReport','TaskKaohe','TaskKaoheResult','TaskCancel')");
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
  }
  
  public void readMessage(String data_id, String userId, String message_type) throws Exception {
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      if (!"".equals(userId)) {
        dbopt.executeUpdate("update sys_messages set message_status =0 where data_id =" + data_id + " and  message_toUserId=" + userId + " and message_type='" + message_type + "'");
      } else {
        dbopt.executeUpdate("update sys_messages set message_status =0 where data_id =" + data_id + " and message_type='" + message_type + "'");
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
  }
}
