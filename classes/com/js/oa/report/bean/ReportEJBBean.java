package com.js.oa.report.bean;

import com.js.oa.report.po.ReportPO;
import com.js.util.hibernate.HibernateBase;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.HibernateException;

public class ReportEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbActivate() throws EJBException, RemoteException {}
  
  public void ejbPassivate() throws EJBException, RemoteException {}
  
  public void ejbRemove() throws EJBException, RemoteException {}
  
  public void setSessionContext(SessionContext sessionContext) throws EJBException, RemoteException {
    this.sessionContext = sessionContext;
  }
  
  public Long saveReport(ReportPO po) throws Exception {
    Long reportId = Long.valueOf(0L);
    try {
      begin();
      reportId = (Long)this.session.save(po);
      this.session.flush();
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return reportId;
  }
  
  public ReportPO loadReport(Long reportId) throws Exception {
    ReportPO po = new ReportPO();
    try {
      begin();
      po = (ReportPO)this.session.load(ReportPO.class, reportId);
      this.session.flush();
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return po;
  }
  
  public boolean updateReport(ReportPO po) throws Exception {
    boolean flag = false;
    try {
      begin();
      this.session.update(po);
      this.session.flush();
      flag = true;
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return flag;
  }
  
  public boolean deleteReport(Long reportId) throws Exception {
    ReportPO po = loadReport(reportId);
    boolean flag = false;
    try {
      begin();
      this.session.delete(po);
      this.session.flush();
      flag = true;
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return flag;
  }
  
  public List queryHql(String hql) {
    List list = null;
    try {
      begin();
      list = this.session.createQuery(hql).list();
      this.session.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return list;
  }
}
