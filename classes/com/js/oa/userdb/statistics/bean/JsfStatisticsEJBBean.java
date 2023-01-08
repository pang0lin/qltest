package com.js.oa.userdb.statistics.bean;

import com.js.oa.userdb.statistics.po.JsfStatisticsManage;
import com.js.oa.userdb.util.DbOpt;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.HibernateException;

public class JsfStatisticsEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Long manageAdd(JsfStatisticsManage po) throws Exception {
    Long id = null;
    begin();
    try {
      id = (Long)this.session.save(po);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return id;
  }
  
  public JsfStatisticsManage loadStatisticsManage(Long id) throws Exception {
    JsfStatisticsManage po = null;
    begin();
    try {
      po = (JsfStatisticsManage)this.session.get(JsfStatisticsManage.class, id);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return po;
  }
  
  public boolean manageUpdate(JsfStatisticsManage po) throws HibernateException {
    boolean re = true;
    begin();
    try {
      this.session.update(po);
      this.session.flush();
    } catch (Exception e) {
      re = false;
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return re;
  }
  
  public boolean deleteManage(String ids) throws Exception {
    begin();
    boolean re = true;
    try {
      if (ids.endsWith(","))
        ids = ids.substring(0, ids.length() - 1); 
      this.session.delete("from JsfStatisticsManage po where po.statsId in (" + ids + ")");
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      re = false;
    } finally {
      this.session.close();
    } 
    return re;
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
  
  public boolean deleteOATaskCollect(String ids) throws Exception {
    begin();
    boolean re = true;
    try {
      if (ids.endsWith(","))
        ids = ids.substring(0, ids.length() - 1); 
      this.session.delete("from com.js.oa.oacollect.po.OaCollect po where po.collectId in (" + ids + ")");
      this.session.delete("from com.js.oa.oacollect.po.OaCollectEmp po where po.collectId in (" + ids + ")");
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      re = false;
    } finally {
      this.session.close();
    } 
    return re;
  }
  
  public List queryStatisticsData(String sql) throws Exception {
    List<Object[]> list = new ArrayList();
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = (new DbOpt()).getConnection();
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        Object[] obj = new Object[2];
        obj[0] = rs.getObject(1);
        obj[1] = rs.getObject(2);
        list.add(obj);
      } 
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (stmt != null)
        stmt.close(); 
      if (conn != null)
        conn.close(); 
    } 
    return list;
  }
  
  public int queryStatisticsDataCount(String sql) throws Exception {
    Connection conn = null;
    Statement stmt = null;
    int rowCount = 0;
    try {
      conn = (new DbOpt()).getConnection();
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      if (rs.next())
        rowCount = rs.getInt(1); 
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (stmt != null)
        stmt.close(); 
      if (conn != null)
        conn.close(); 
    } 
    return rowCount;
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
}
