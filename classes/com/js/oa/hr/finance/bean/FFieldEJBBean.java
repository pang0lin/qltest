package com.js.oa.hr.finance.bean;

import com.js.oa.userdb.util.DbOpt;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.Query;

public class FFieldEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public boolean updateByYourYuanShengSql(String sql) throws Exception {
    boolean result = true;
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      boolean status_db = conn.getAutoCommit();
      conn.setAutoCommit(false);
      stmt = conn.createStatement();
      stmt.executeUpdate(sql);
      stmt.close();
      conn.commit();
      conn.setAutoCommit(status_db);
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
      result = false;
    } 
    return result;
  }
  
  public String updateBySql(String sql) throws Exception {
    StringBuffer res = new StringBuffer(",");
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      stmt.executeUpdate(sql);
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    return res.toString();
  }
  
  public List getListByYourSQL(String sql) throws Exception {
    begin();
    List list = new ArrayList();
    try {
      Query query1 = this.session.createQuery(sql);
      list = query1.list();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public String[][] getArr2ByYourSql(String searchSql, int length, String paras) throws Exception {
    String[][] list = (String[][])null;
    DbOpt dbopt = new DbOpt();
    try {
      if (searchSql.indexOf("where") > 0 && paras != null && 
        paras.length() > 0) {
        searchSql = String.valueOf(searchSql) + " " + paras.substring(0, paras.lastIndexOf("and"));
      } else {
        searchSql = String.valueOf(searchSql) + " " + paras;
      } 
      list = dbopt.executeQueryToStrArr2(searchSql, length);
      dbopt.close();
    } catch (Exception e) {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
      e.printStackTrace();
      throw e;
    } 
    return list;
  }
  
  public String[] getArr1ByYourSql(String sql) {
    DbOpt dbopt = null;
    String[] result = (String[])null;
    try {
      dbopt = new DbOpt();
      result = dbopt.executeQueryToStrArr1(sql);
      dbopt.close();
    } catch (Exception e) {
      e.printStackTrace();
      try {
        dbopt.close();
      } catch (SQLException ex) {
        ex.printStackTrace();
      } 
    } finally {}
    return result;
  }
  
  public String getCountByYourSql(String sql) throws Exception {
    String count = "";
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      String dbType = SystemCommon.getDatabaseType();
      count = dbopt.executeQueryToStr("select count(*) from ( " + sql + ") wk");
      dbopt.close();
    } catch (Exception e) {
      if (dbopt != null)
        try {
          dbopt.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }  
      e.printStackTrace();
      throw e;
    } 
    return count;
  }
}
