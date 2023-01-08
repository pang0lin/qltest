package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.personnelmanager.po.EmpRemindPO;
import com.js.util.hibernate.HibernateBase;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;

public class EmpRemindEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Boolean save(EmpRemindPO po) throws Exception {
    Boolean success = new Boolean(false);
    begin();
    try {
      this.session.save(po);
      this.session.flush();
      success = Boolean.TRUE;
    } catch (Exception e) {
      success = Boolean.FALSE;
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return success;
  }
  
  public EmpRemindPO load(Long id) throws Exception {
    EmpRemindPO empRemindPO = new EmpRemindPO();
    try {
      begin();
      empRemindPO = (EmpRemindPO)this.session.load(EmpRemindPO.class, id);
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return empRemindPO;
  }
  
  public Boolean modify(EmpRemindPO po) throws Exception {
    Boolean success = new Boolean(false);
    try {
      begin();
      EmpRemindPO o = (EmpRemindPO)this.session.load(EmpRemindPO.class, po.getId());
      o.setEmpId(po.getEmpId());
      o.setEmpName(po.getEmpName());
      o.setCreateId(po.getCreateId());
      o.setCreater(po.getCreater());
      o.setRemindTime(po.getRemindTime());
      o.setRemindType(po.getRemindType());
      o.setSendToId(po.getSendToId());
      o.setSendToName(po.getSendToName());
      o.setState(po.getState());
      this.session.saveOrUpdate(o);
      this.session.flush();
      success = Boolean.TRUE;
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return success;
  }
  
  public Boolean delete(String id) throws Exception {
    Boolean success = new Boolean(false);
    begin();
    try {
      this.session.delete("from com.js.oa.hr.personnelmanager.po.EmpRemindPO erpo  where erpo.id in (" + 
          id + ")");
      this.session.flush();
      success = Boolean.valueOf(true);
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return success;
  }
  
  public List selectRemindList() throws Exception {
    List alist = new ArrayList();
    begin();
    try {
      Query query = this.session.createQuery(" from com.js.oa.hr.personnelmanager.po.EmpRemindPO ");
      alist = query.list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.flush();
      this.session.close();
    } 
    return alist;
  }
}
