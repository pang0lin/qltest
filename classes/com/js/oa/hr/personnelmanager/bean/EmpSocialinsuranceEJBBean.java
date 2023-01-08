package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.personnelmanager.po.EmpSocialinsurancePO;
import com.js.util.hibernate.HibernateBase;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.HibernateException;

public class EmpSocialinsuranceEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Boolean save(EmpSocialinsurancePO po) throws HibernateException {
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
  
  public EmpSocialinsurancePO load(Long id) throws HibernateException {
    EmpSocialinsurancePO po = null;
    try {
      begin();
      po = (EmpSocialinsurancePO)this.session.load(EmpSocialinsurancePO.class, id);
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return po;
  }
  
  public Boolean modify(EmpSocialinsurancePO po) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      EmpSocialinsurancePO o = (EmpSocialinsurancePO)this.session.load(EmpSocialinsurancePO.class, 
          po.getId());
      o.setPayMonth(po.getPayMonth());
      o.setPayType(po.getPayType());
      o.setPayBase(po.getPayBase());
      o.setStopMonth(po.getStopMonth());
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
    EmpSocialinsurancePO po = null;
    try {
      begin();
      po = (EmpSocialinsurancePO)this.session.load(EmpSocialinsurancePO.class, id);
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
          "from com.js.oa.hr.personnelmanager.po.EmpSocialinsurancePO po where po.id in (" + 
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
}
