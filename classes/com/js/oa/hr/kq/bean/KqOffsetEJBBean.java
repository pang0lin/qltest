package com.js.oa.hr.kq.bean;

import com.js.oa.hr.kq.po.KqOffsetPO;
import com.js.util.hibernate.HibernateBase;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class KqOffsetEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public KqOffsetPO load(long domain_id) throws Exception {
    KqOffsetPO kqOffsetPO = new KqOffsetPO();
    try {
      begin();
      List<KqOffsetPO> list = this.session.createQuery("select po from com.js.oa.hr.kq.po.KqOffsetPO po where po.domainId =" + domain_id).list();
      if (list.isEmpty()) {
        kqOffsetPO = null;
      } else {
        kqOffsetPO = list.get(0);
      } 
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return kqOffsetPO;
  }
  
  public void set(long id, int offsetTime, long domain_id) throws Exception {
    KqOffsetPO kqOffsetPO = new KqOffsetPO();
    try {
      begin();
      if (id == 0L) {
        kqOffsetPO.setDomainId(domain_id);
        kqOffsetPO.setOffsetTime(offsetTime);
        this.session.save(kqOffsetPO);
      } else {
        kqOffsetPO = (KqOffsetPO)this.session.load(KqOffsetPO.class, Long.valueOf(id));
        kqOffsetPO.setOffsetTime(offsetTime);
      } 
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
  }
}
