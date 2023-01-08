package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.personnelmanager.po.HprecordPO;
import com.js.util.hibernate.HibernateBase;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;

public class HprecordEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public boolean addHprecord(HprecordPO hprecordPO) throws Exception {
    boolean result = false;
    begin();
    try {
      this.session.save(hprecordPO);
      this.session.flush();
      result = true;
    } catch (Exception e) {
      System.out.println("addHprecordEJB Exception:" + e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public boolean deleteHprecord(Long hprecordId) throws Exception {
    boolean result = false;
    begin();
    try {
      HprecordPO hprecordPO = (HprecordPO)this.session.load(HprecordPO.class, 
          hprecordId);
      this.session.delete(hprecordPO);
      this.session.flush();
      result = true;
    } catch (Exception e) {
      System.out.println("deleteHprecordEJB Exception:" + e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public boolean deleteBatchHprecord(String hprecordIds) throws Exception {
    boolean result = false;
    begin();
    try {
      String[] idsArr = hprecordIds.split(",");
      for (int i = 0; i < idsArr.length; i++) {
        HprecordPO hprecordPO = (HprecordPO)this.session.load(HprecordPO.class, 
            Long.valueOf(idsArr[i]));
        this.session.delete(hprecordPO);
      } 
      this.session.flush();
      result = true;
    } catch (Exception e) {
      System.out.println("deleteBatchHprecordEJB Exception:" + e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public HprecordPO selectHprecordView(Long hprecordId) throws HibernateException {
    HprecordPO hprecordPO = null;
    try {
      begin();
      hprecordPO = (HprecordPO)this.session.load(HprecordPO.class, hprecordId);
    } catch (HibernateException e) {
      System.out.println("selectHprecordViewEJB Exception:" + e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return hprecordPO;
  }
  
  public boolean updateHprecord(HprecordPO hprecordPO) throws Exception {
    boolean result = false;
    begin();
    try {
      HprecordPO hprecord = (HprecordPO)this.session.load(HprecordPO.class, 
          hprecordPO.getId());
      hprecord.setId(hprecordPO.getId());
      hprecord.setHpDate(hprecordPO.getHpDate());
      hprecord.setHpTitle(hprecordPO.getHpTitle());
      hprecord.setHpType(hprecordPO.getHpType());
      hprecord.setHpPersonnel(hprecordPO.getHpPersonnel());
      hprecord.setHpPersonnelName(hprecordPO.getHpPersonnelName());
      hprecord.setHpExplain(hprecordPO.getHpExplain());
      this.session.update(hprecord);
      this.session.flush();
      result = true;
    } catch (Exception e) {
      System.out.println("updateHprecordEJB Exception:" + e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public List selectHpName(String domain) throws HibernateException {
    List list = new ArrayList();
    try {
      begin();
      Query selectQuery = this.session.createQuery("select hortationPunishPO.id,hortationPunishPO.hp_name from com.js.oa.hr.personnelmanager.po.HortationPunishPO hortationPunishPO where hortationPunishPO.domain = " + domain + " order by hortationPunishPO.hp_name");
      list = selectQuery.list();
    } catch (HibernateException e) {
      System.out.println("selectHpNameEJB Exception:" + e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
}
