package com.js.oa.hr.kq.bean;

import com.js.oa.hr.kq.po.KqWeiXinBMDPO;
import com.js.util.hibernate.HibernateBase;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class KqWeiXinBMDEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Long add(KqWeiXinBMDPO kqWeiXinBMDPO) throws Exception {
    Long eveId = Long.valueOf("-1");
    begin();
    try {
      eveId = (Long)this.session.save(kqWeiXinBMDPO);
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return eveId;
  }
  
  public void update(KqWeiXinBMDPO kqWeiXinBMDPO) throws Exception {
    begin();
    try {
      this.session.update(kqWeiXinBMDPO);
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public void del(long id) throws Exception {
    begin();
    try {
      this.session.delete(this.session.load(KqWeiXinBMDPO.class, Long.valueOf(id)));
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public KqWeiXinBMDPO searchById(long KqWeiXinBMDId) throws Exception {
    KqWeiXinBMDPO kqHolidayPO = new KqWeiXinBMDPO();
    begin();
    try {
      kqHolidayPO = (KqWeiXinBMDPO)this.session.load(KqWeiXinBMDPO.class, Long.valueOf(KqWeiXinBMDId));
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return kqHolidayPO;
  }
  
  public boolean searchByDate(String date) throws Exception {
    boolean re = false;
    List list = new ArrayList();
    begin();
    try {
      list = this.session.createQuery(" select po from com.js.oa.hr.kq.po.KqWeiXinBMDPO po where po.beginTime <= '" + date + "' and  po.endTime >= '" + date + "' ").list();
      if (list.isEmpty()) {
        re = true;
      } else {
        re = false;
      } 
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return re;
  }
}
