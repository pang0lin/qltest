package com.js.oa.hr.finance.bean;

import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import com.js.util.util.ParameterGenerator;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.Query;

public class FSalaryEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public boolean delete(String ids, String deleteFlag) {
    String databaseType = SystemCommon.getDatabaseType();
    boolean re = true;
    try {
      if (ids.endsWith(","))
        ids = ids.substring(0, ids.length() - 1); 
      if (deleteFlag != null && "pici".equals(deleteFlag)) {
        String sql = "select distinct created_date from f_salary where id in (" + ids + ")";
        FFieldEJBBean fFieldEJBBean = new FFieldEJBBean();
        String[] dateArr = fFieldEJBBean.getArr1ByYourSql(sql);
        String dateStr = "";
        if (dateArr != null && dateArr.length > 0)
          for (int i = 0; i < dateArr.length; i++) {
            String dateStrTemp = dateArr[i].toString();
            if (dateStrTemp.length() > 19)
              dateStrTemp = dateStrTemp.substring(0, 19); 
            if (i == 0) {
              if (databaseType.indexOf("oracle") >= 0) {
                dateStr = String.valueOf(dateStr) + "to_date('" + dateStrTemp + "','yyyy-MM-dd HH24:mi:ss')";
              } else {
                dateStr = String.valueOf(dateStr) + "'" + dateStrTemp + "'";
              } 
            } else if (databaseType.indexOf("oracle") >= 0) {
              dateStr = String.valueOf(dateStr) + ",to_date('" + dateStrTemp + "','yyyy-MM-dd HH24:mi:ss')";
            } else {
              dateStr = String.valueOf(dateStr) + ",'" + dateStrTemp + "'";
            } 
          }  
        if (dateStr != null && !"".equals(dateStr))
          sql = "delete from f_salary where created_date in (" + dateStr + ")"; 
        re = updateByYourYuanShengSql(sql);
      } else {
        String sql = "delete from f_salary where id in (" + ids + ")";
        re = updateByYourYuanShengSql(sql);
      } 
    } catch (Exception e) {
      e.printStackTrace();
      re = false;
    } 
    return re;
  }
  
  public Map getEmpNameOrgIdOrgNameByUserId(String userId) throws Exception {
    Map<Object, Object> map = null;
    ParameterGenerator p = new ParameterGenerator(1);
    begin();
    List<Object[]> list = new ArrayList();
    try {
      Query query1 = this.session.createQuery(" select o.orgId,o.orgName,e.empName from com.js.system.vo.organizationmanager.OrganizationVO o,com.js.system.vo.usermanager.EmployeeOrgVO eo,com.js.system.vo.usermanager.EmployeeVO e  where o.orgId=eo.orgId and e.empId=eo.empId  eo.empId=" + 
          userId);
      list = query1.list();
      if (list != null && list.size() > 0) {
        map = new HashMap<Object, Object>();
        Object[] ob = list.get(0);
        map.put("empName", ob[2]);
        map.put("orgId", ob[0]);
        map.put("orgName", ob[1]);
      } 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return map;
  }
  
  public Map getEmpIdEmpNameEmpNumberOrgIdOrgNameByUserAccounts(String userAccounts) throws Exception {
    Map<Object, Object> map = null;
    ParameterGenerator p = new ParameterGenerator(1);
    begin();
    List<Object[]> list = new ArrayList();
    try {
      Query query1 = this.session.createQuery(" select o.orgId,o.orgName,e.empName,e.empId,e.empNumber from com.js.system.vo.organizationmanager.OrganizationVO o,com.js.system.vo.usermanager.EmployeeOrgVO eo,com.js.system.vo.usermanager.EmployeeVO e  where o.orgId=eo.orgId and e.empId=eo.empId and e.userAccounts='" + 
          userAccounts + "'");
      list = query1.list();
      if (list != null && list.size() > 0) {
        map = new HashMap<Object, Object>();
        Object[] ob = list.get(0);
        map.put("empNumber", ob[4]);
        map.put("empId", ob[3]);
        map.put("empName", ob[2]);
        map.put("orgId", ob[0]);
        map.put("orgName", ob[1]);
      } 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return map;
  }
  
  public Long getTableIdB() throws Exception {
    begin();
    Long r = null;
    try {
      r = getTableId();
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return r;
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
}
