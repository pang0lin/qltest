package com.js.oa.personalwork.setup.bean;

import com.js.oa.personalwork.setup.po.WorkProxyPO;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.sql.DataSource;

public class WorkProxyEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public void delBatch(String ids, String userId) throws Exception {
    try {
      begin();
      if (ids != null && !ids.equals(""))
        this.session.delete(
            " from com.js.oa.personalwork.setup.po.WorkProxyPO po where po.id in (" + 
            ids.substring(0, ids.length() - 1) + ")"); 
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public void delAll(String userId) throws Exception {
    begin();
    try {
      this.session.delete(
          "from com.js.oa.personalwork.setup.po.WorkProxyPO po");
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public void add(WorkProxyPO po, String userId) throws Exception {
    try {
      begin();
      this.session.save(po);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public WorkProxyPO load(String editId) throws Exception {
    WorkProxyPO po = null;
    try {
      begin();
      po = (WorkProxyPO)this.session.load(WorkProxyPO.class, new Long(editId));
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return po;
  }
  
  public void update(WorkProxyPO paraPO, String id, String userId) throws Exception {
    try {
      begin();
      WorkProxyPO po = (WorkProxyPO)this.session.load(WorkProxyPO.class, new Long(id));
      po.setEmpId(paraPO.getEmpId());
      po.setEmpName(paraPO.getEmpName());
      po.setBeginTime(paraPO.getBeginTime());
      po.setEndTime(paraPO.getEndTime());
      po.setProxyEmpId(paraPO.getProxyEmpId());
      po.setProxyEmpName(paraPO.getProxyEmpName());
      po.setProxyOrgId(paraPO.getProxyOrgId());
      po.setProxyOrgName(paraPO.getProxyOrgName());
      po.setProxyState(paraPO.getProxyState());
      po.setProxyAllProcess(paraPO.getProxyAllProcess());
      po.setProxyProcess(paraPO.getProxyProcess());
      this.session.update(po);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public String getAvailableProxy(String curUserId) throws Exception {
    String retString = curUserId;
    begin();
    try {
      String where = " where po.empId=" + curUserId;
      String now = (new Date()).toLocaleString();
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        where = String.valueOf(where) + " and '" + now + "' between po.beginTime and po.endTime";
      } else {
        where = String.valueOf(where) + " and JSDB.FN_STRTODATE('" + now + "','L') between po.beginTime and po.endTime";
      } 
      where = String.valueOf(where) + " and po.proxyState =1 ";
      List<String> list = this.session.createQuery("select po.proxyEmpId from com.js.oa.personalwork.setup.po.WorkProxyPO po " + where + " order by po.endTime desc ").list();
      if (!list.isEmpty() && list.size() > 0)
        retString = list.get(0); 
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return retString;
  }
  
  public String[] getAvailableUsers(String[] userIdArr) throws Exception {
    begin();
    try {
      for (int i = 0; i < userIdArr.length; i++) {
        String curUserId = userIdArr[i];
        String where = " where po.empId=" + curUserId;
        String now = (new Date()).toLocaleString();
        String databaseType = SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0) {
          where = String.valueOf(where) + " and '" + now + "' between po.beginTime and po.endTime";
        } else {
          where = String.valueOf(where) + " and JSDB.FN_STRTODATE('" + now + "','L') between po.beginTime and po.endTime";
        } 
        where = String.valueOf(where) + " and po.proxyState =1 ";
        where = "select po.proxyEmpId from com.js.oa.personalwork.setup.po.WorkProxyPO po " + where + " order by po.endTime desc ";
        List<String> list = this.session.createQuery(where).list();
        if (!list.isEmpty() && list.size() > 0)
          curUserId = list.get(0); 
        userIdArr[i] = curUserId;
      } 
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return userIdArr;
  }
  
  public void setUnavailableProxy() throws Exception {
    DataSourceBase dsb = new DataSourceBase();
    DataSource dataSource = dsb.getDataSource();
    Connection conn = dataSource.getConnection();
    Statement stmt = conn.createStatement();
    try {
      String where = " where proxyState=1 ";
      String now = (new Date()).toLocaleString();
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        where = String.valueOf(where) + " and '" + now + "' > endTime";
      } else {
        where = String.valueOf(where) + " and JSDB.FN_STRTODATE('" + now + "','L') > endTime";
      } 
      where = String.valueOf(where) + " and proxyState =1 ";
      stmt.execute("update oa_workproxy set proxyState=0 " + where);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (stmt != null)
        stmt.close(); 
      if (conn != null)
        conn.close(); 
    } 
  }
}
