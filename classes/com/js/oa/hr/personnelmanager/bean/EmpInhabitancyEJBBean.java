package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.personnelmanager.po.EmpInhabitancyPO;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.HibernateException;

public class EmpInhabitancyEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Boolean save(EmpInhabitancyPO po) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      this.session.save(po);
      this.session.flush();
      result = Boolean.TRUE;
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public EmpInhabitancyPO load(Long id) throws HibernateException {
    EmpInhabitancyPO po = null;
    try {
      begin();
      po = (EmpInhabitancyPO)this.session.load(EmpInhabitancyPO.class, id);
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return po;
  }
  
  public Boolean modify(EmpInhabitancyPO po) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      EmpInhabitancyPO o = (EmpInhabitancyPO)this.session.load(EmpInhabitancyPO.class, 
          po.getId());
      o.setEmp(po.getEmp());
      o.setBeginDate(po.getBeginDate());
      o.setContinueDate(po.getContinueDate());
      o.setYearLimit(po.getYearLimit());
      o.setMemos(po.getMemos());
      this.session.saveOrUpdate(o);
      this.session.flush();
      result = Boolean.TRUE;
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public Boolean delete(Long id) throws HibernateException {
    Boolean result = new Boolean(false);
    EmpInhabitancyPO po = null;
    try {
      begin();
      po = (EmpInhabitancyPO)this.session.load(EmpInhabitancyPO.class, id);
      if (po != null) {
        this.session.delete(po);
        this.session.flush();
        result = Boolean.TRUE;
      } 
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public Boolean batchDel(String ids) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      this.session.delete(
          "from com.js.oa.hr.personnelmanager.po.EmpInhabitancyPO po where po.id in (" + 
          ids + ")");
      this.session.flush();
      result = Boolean.TRUE;
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  private List getEmployeeTOZZ() throws Exception {
    List list = null;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String now = sdf.format(new Date());
    String sql = "select po.empId,po.empName,po.domainId,po.zhuanzhengDate from com.js.system.vo.usermanager.EmployeeVO po ";
    sql = String.valueOf(sql) + " where po.jobStatus = '试用' ";
    sql = String.valueOf(sql) + " and  po.zhuanzhengDate is not null ";
    sql = String.valueOf(sql) + " and JSDB.FN_DATEDIFF('day','" + now + "',po.zhuanzhengDate) >= 0 ";
    sql = String.valueOf(sql) + " and JSDB.FN_DATEDIFF('day','" + now + "',po.zhuanzhengDate) <= 15 ";
    try {
      begin();
      list = this.session.createQuery(sql).list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  private List getEmployeeTOJZZ() throws Exception {
    List list = null;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String now = sdf.format(new Date());
    String sql = "select poo.empId,poo.empName,poo.domainId,po.continueDate from com.js.oa.hr.personnelmanager.po.EmpInhabitancyPO po join po.emp poo ";
    sql = String.valueOf(sql) + " where po.continueDate is not null";
    sql = String.valueOf(sql) + " and JSDB.FN_DATEDIFF('day','" + now + "',po.continueDate) >= 0 ";
    sql = String.valueOf(sql) + " and JSDB.FN_DATEDIFF('day','" + now + "',po.continueDate) <= 15 ";
    try {
      begin();
      list = this.session.createQuery(sql).list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  private List getEmployeeFROMJZZ(Integer status) throws Exception {
    List<String> list = new ArrayList();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String sql = "select a.emp_id from oa_emp_inhabitancy_con a where a.status=" + status;
    try {
      begin();
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      while (rs.next())
        list.add((new StringBuilder(String.valueOf(rs.getLong(1)))).toString()); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (rs != null)
        rs.close(); 
      if (stmt != null)
        stmt.close(); 
      if (conn != null)
        conn.close(); 
      this.session.close();
    } 
    return list;
  }
  
  public List getEmployeeFROMJZZ2(Integer status) throws Exception {
    List<Object[]> list = new ArrayList();
    try {
      List<String> ls = getEmployeeFROMJZZ(status);
      if (status.intValue() == 0) {
        List<Object[]> ls0 = getEmployeeTOZZ();
        if (ls0 != null && ls0.size() > 0)
          if (ls != null && ls.size() > 0) {
            boolean flag = true;
            for (int i = 0; i < ls0.size(); i++) {
              Object[] obj = ls0.get(i);
              for (int k = 0; k < ls.size(); k++) {
                String emp_id = ls.get(k);
                if (emp_id.equals(String.valueOf(obj[i]))) {
                  flag = false;
                  break;
                } 
              } 
              if (flag)
                list.add(obj); 
            } 
          } else {
            list.addAll(ls0);
          }  
      } else if (status.intValue() == 1) {
        List<Object[]> ls0 = getEmployeeTOJZZ();
        if (ls0 != null && ls0.size() > 0)
          if (ls != null && ls.size() > 0) {
            boolean flag = true;
            for (int i = 0; i < ls0.size(); i++) {
              Object[] obj = ls0.get(i);
              for (int k = 0; k < ls.size(); k++) {
                String emp_id = ls.get(k);
                if (emp_id.equals(String.valueOf(obj[i]))) {
                  flag = false;
                  break;
                } 
              } 
              if (flag)
                list.add(obj); 
            } 
          } else {
            list.addAll(ls0);
          }  
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return list;
  }
  
  public Boolean saveTOSENDJZZ(Long userId, Integer status) throws Exception {
    Boolean result = new Boolean(false);
    String databaseType = 
      SystemCommon.getDatabaseType();
    Connection conn = null;
    Statement stmt = null;
    String sql = "insert into oa_emp_inhabitancy_con (id, emp_id, senddate, status) values(hibernate_sequence.nextval," + 
      userId + ",sysdate," + status + ")";
    if (databaseType.indexOf("oracle") > -1) {
      sql = "insert into oa_emp_inhabitancy_con (id, emp_id, senddate, status) values(hibernate_sequence.nextval," + 
        userId + ",sysdate," + status + ")";
    } else {
      sql = 
        "insert into oa_emp_inhabitancy_con (emp_id, senddate, status) values(" + 
        userId + ",getdate()," + status + ")";
    } 
    try {
      begin();
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      stmt.executeUpdate(sql);
      result = Boolean.TRUE;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (stmt != null)
        stmt.close(); 
      if (conn != null)
        conn.close(); 
      this.session.close();
    } 
    return result;
  }
}
