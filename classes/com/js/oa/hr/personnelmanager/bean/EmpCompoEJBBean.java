package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.personnelmanager.po.EmpCompoPO;
import com.js.util.hibernate.HibernateBase;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.HibernateException;

public class EmpCompoEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Boolean save(EmpCompoPO po) throws HibernateException {
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
  
  public EmpCompoPO load(Long id) throws HibernateException {
    EmpCompoPO po = null;
    try {
      begin();
      po = (EmpCompoPO)this.session.load(EmpCompoPO.class, id);
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return po;
  }
  
  public Boolean modify(EmpCompoPO po) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      EmpCompoPO o = (EmpCompoPO)this.session.load(EmpCompoPO.class, 
          po.getId());
      o.setEmp(po.getEmp());
      o.setOccurDate(po.getOccurDate());
      o.setAccident(po.getAccident());
      o.setCompensateUnit(po.getCompensateUnit());
      o.setInsuranceCompany(po.getInsuranceCompany());
      o.setCompensateMoney(po.getCompensateMoney());
      o.setAppraisalLevel(po.getAppraisalLevel());
      o.setEmpOrg(po.getEmpOrg());
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
    EmpCompoPO po = null;
    try {
      begin();
      po = (EmpCompoPO)this.session.load(EmpCompoPO.class, id);
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
      this.session.delete("from com.js.oa.hr.personnelmanager.po.EmpCompoPO po where po.id in (" + ids + ")");
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
