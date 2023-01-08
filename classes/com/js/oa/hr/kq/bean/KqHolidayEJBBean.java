package com.js.oa.hr.kq.bean;

import com.js.oa.hr.kq.po.KqHolidayPO;
import com.js.util.hibernate.HibernateBase;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class KqHolidayEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Long add(KqHolidayPO kqHolidayPO) throws Exception {
    Long eveId = Long.valueOf("-1");
    begin();
    try {
      eveId = (Long)this.session.save(kqHolidayPO);
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return eveId;
  }
  
  public void update(KqHolidayPO kqHolidayPO) throws Exception {
    begin();
    try {
      this.session.update(kqHolidayPO);
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
      this.session.delete(this.session.load(KqHolidayPO.class, Long.valueOf(id)));
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public KqHolidayPO searchById(long KqHolidayId) throws Exception {
    KqHolidayPO kqHolidayPO = new KqHolidayPO();
    begin();
    try {
      kqHolidayPO = (KqHolidayPO)this.session.load(KqHolidayPO.class, Long.valueOf(KqHolidayId));
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
      list = this.session.createQuery(" select po from com.js.oa.hr.kq.po.KqHolidayPO po where po.beginDate <= '" + date + "' and  po.endDate >= '" + date + "' ").list();
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
  
  public List getPaibanList() throws Exception {
    List list = null;
    try {
      begin();
      String hql = "select po.id,po.dutyName from com.js.oa.hr.kq.po.KqDutySetPO po order by po.id";
      list = this.session.createQuery(hql).list();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
}
