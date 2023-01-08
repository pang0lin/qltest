package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.personnelmanager.po.PerformanceCheckPO;
import com.js.util.hibernate.HibernateBase;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.HibernateException;

public class PerformanceCheckEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Boolean save(PerformanceCheckPO po) throws HibernateException {
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
  
  public PerformanceCheckPO load(Long id) throws HibernateException {
    PerformanceCheckPO po = null;
    try {
      begin();
      po = (PerformanceCheckPO)this.session.load(PerformanceCheckPO.class, id);
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return po;
  }
  
  public Boolean modify(PerformanceCheckPO po) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      PerformanceCheckPO o = (PerformanceCheckPO)this.session.load(PerformanceCheckPO.class, 
          po.getId());
      o.setEmp(po.getEmp());
      o.setEmpOrg(po.getEmpOrg());
      o.setCheckYear(po.getCheckYear());
      o.setCheckMonth(po.getCheckMonth());
      o.setCheckMark(po.getCheckMark());
      o.setAddMark(po.getAddMark());
      o.setAddReason(po.getAddReason());
      o.setDeductMark(po.getDeductMark());
      o.setDeductReason(po.getDeductReason());
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
    PerformanceCheckPO po = null;
    try {
      begin();
      po = (PerformanceCheckPO)this.session.load(PerformanceCheckPO.class, id);
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
          "from com.js.oa.hr.personnelmanager.po.PerformanceCheckPO po where po.id in (" + 
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
  
  public Boolean checkExists(Long userId, Long id, String year, String month) throws Exception {
    Boolean ret = Boolean.FALSE;
    try {
      begin();
      List list = this.session.createQuery(
          "select po from com.js.oa.hr.personnelmanager.po.PerformanceCheckPO po where " + ((
          id != null && !"".equals(id)) ? (
          " po.id != " + id + " and ") : "") + 
          " po.emp.empId = " + userId + " and po.checkYear ='" + year + "' and po.checkMonth = '" + month + "'")
        .list();
      if (list != null && list.size() > 0)
        ret = Boolean.TRUE; 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return ret;
  }
}
