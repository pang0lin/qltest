package com.js.oa.hr.subsidiarywork.bean;

import com.js.oa.hr.subsidiarywork.po.FestivalSetPO;
import com.js.util.hibernate.HibernateBase;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class FestalCardEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public void delBatch(String ids) throws Exception {
    begin();
    try {
      if (ids != null && !ids.equals(""))
        this.session.delete(
            " from com.js.oa.hr.subsidiarywork.po.FestivalSetPO po where po.id in (" + 
            ids.substring(0, ids.length() - 1) + ")"); 
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public void delAll(String wherePara) throws Exception {
    begin();
    try {
      this.session.delete(
          "from com.js.oa.hr.subsidiarywork.po.FestivalSetPO po where " + wherePara);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public void add(FestivalSetPO po, String userId, String orgId) throws Exception {
    begin();
    try {
      po.setCreatedEmp(Long.parseLong(userId));
      po.setCreatedOrg(Long.parseLong(orgId));
      this.session.save(po);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public FestivalSetPO load(String editId) throws Exception {
    FestivalSetPO po = null;
    begin();
    try {
      po = (FestivalSetPO)this.session.load(FestivalSetPO.class, new Long(editId));
    } catch (Exception e) {
      e.printStackTrace();
      this.transaction.rollback();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return po;
  }
  
  public void update(FestivalSetPO paraPO, String editId, String appointYear) throws Exception {
    begin();
    try {
      FestivalSetPO po = (FestivalSetPO)this.session.load(FestivalSetPO.class, new Long(editId));
      po.setAppointYear(paraPO.getAppointYear());
      po.setCalendarType(paraPO.getCalendarType());
      po.setFestivalName(paraPO.getFestivalName());
      po.setFestivalRemind(paraPO.getFestivalRemind());
      po.setFestivalWish(paraPO.getFestivalWish());
      po.setFestivalWishCard(paraPO.getFestivalWishCard());
      po.setFestiveDay(paraPO.getFestiveDay());
      po.setFestiveMonth(paraPO.getFestiveMonth());
      po.setFestiveYear(paraPO.getFestiveYear());
      po.setUseRange(paraPO.getUseRange());
      po.setUserRangeName(paraPO.getUserRangeName());
      po.setWishCardName(paraPO.getWishCardName());
      if (appointYear.equals("0"))
        po.setFestiveYear(0); 
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
}
