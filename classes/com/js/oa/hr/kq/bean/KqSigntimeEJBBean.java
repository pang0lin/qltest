package com.js.oa.hr.kq.bean;

import com.js.oa.hr.kq.po.KqSigntimePO;
import com.js.util.hibernate.HibernateBase;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class KqSigntimeEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public KqSigntimePO load(long domain_id) throws Exception {
    KqSigntimePO kqSigntimePO = new KqSigntimePO();
    try {
      begin();
      List<KqSigntimePO> list = this.session.createQuery("select po from com.js.oa.hr.kq.po.KqSigntimePO po where po.domainId =" + domain_id).list();
      if (list.isEmpty()) {
        kqSigntimePO = null;
      } else {
        kqSigntimePO = list.get(0);
      } 
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return kqSigntimePO;
  }
  
  public void set(long id, int offset, long domain_id) throws Exception {
    KqSigntimePO kqSigntimePO = new KqSigntimePO();
    try {
      begin();
      if (id == 0L) {
        kqSigntimePO.setDomainId(domain_id);
        kqSigntimePO.setOffset(offset);
        this.session.save(kqSigntimePO);
      } else {
        kqSigntimePO = (KqSigntimePO)this.session.load(KqSigntimePO.class, Long.valueOf(id));
        kqSigntimePO.setOffset(offset);
      } 
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
  }
}
