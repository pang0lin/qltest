package com.js.oa.hr.finance.bean;

import com.js.oa.hr.finance.po.FPage;
import com.js.oa.oacollect.po.OaCollect;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import com.js.util.util.InfoUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.http.HttpServletRequest;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;

public class FPageEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Long addPage(FPage po) throws Exception {
    Long collectId = null;
    DataSourceBase dataBase = new DataSourceBase();
    String databaseType = SystemCommon.getDatabaseType();
    begin();
    try {
      String content = po.getPageContent();
      if ("oracle".equals(databaseType))
        po.setPageContent(null); 
      collectId = (Long)this.session.save(po);
      this.session.flush();
      if ("oracle".equals(databaseType) && content != null)
        InfoUtil.insert_oracle_clob("f_page", "page_content", "page_id", collectId, content); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return collectId;
  }
  
  public boolean modiPage(FPage po) throws Exception {
    boolean re = true;
    DataSourceBase dataBase = new DataSourceBase();
    String databaseType = SystemCommon.getDatabaseType();
    begin();
    try {
      String content = po.getPageContent();
      if ("oracle".equals(databaseType))
        po.setPageContent(null); 
      this.session.update(po);
      this.session.flush();
      if ("oracle".equals(databaseType) && content != null)
        InfoUtil.insert_oracle_clob("f_page", "page_content", "page_id", po.getPageId(), content); 
    } catch (Exception e) {
      e.printStackTrace();
      re = false;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return re;
  }
  
  public FPage loadFPage(Long pageId) throws Exception {
    FPage fPage = null;
    begin();
    try {
      fPage = (FPage)this.session.get(FPage.class, pageId);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return fPage;
  }
  
  public boolean updateOATaskCollect(OaCollect oaCollect, HttpServletRequest request) throws HibernateException {
    boolean re = true;
    begin();
    try {
      this.session.update(oaCollect);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
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
      String dateString = "now()";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("oracle") >= 0)
        dateString = "sysdate"; 
      String sql = "update JSF_WORK set WORKDONEWITHDATE=" + dateString + ",WORKSTATUS=101 where WORKRECORD_ID  in(" + ids + ")" + 
        " and WORKFILETYPE='数据采集' and INITACTIVITY  in(" + ids + ")";
      updateBySql(sql);
    } catch (Exception e) {
      e.printStackTrace();
      re = false;
    } finally {
      this.session.close();
    } 
    return re;
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
}
