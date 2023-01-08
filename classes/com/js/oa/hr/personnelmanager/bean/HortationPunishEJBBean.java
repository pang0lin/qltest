package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.personnelmanager.po.HortationPunishPO;
import com.js.util.hibernate.HibernateBase;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class HortationPunishEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Boolean saveHortationPunish(HortationPunishPO hortationPunishPO) throws Exception {
    Boolean success = new Boolean(false);
    begin();
    try {
      Iterator iter = this.session.createQuery(
          "select po.id from com.js.oa.hr.personnelmanager.po.HortationPunishPO po  where po.hp_name='" + 
          hortationPunishPO.getHp_name() + "' and po.domain=" + hortationPunishPO.getDomain()).iterate();
      if (!iter.hasNext()) {
        this.session.save(hortationPunishPO);
        this.session.flush();
        success = Boolean.TRUE;
      } 
    } catch (Exception e) {
      success = Boolean.FALSE;
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return success;
  }
  
  public Boolean deleteHortationPunish(String ids) throws Exception {
    Boolean success = new Boolean(true);
    begin();
    try {
      this.session.delete("from com.js.oa.hr.personnelmanager.po.HortationPunishPO a where a.id in (" + ids + ")");
      this.session.flush();
    } catch (Exception e) {
      success = Boolean.FALSE;
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return success;
  }
  
  public HortationPunishPO getSingleHortationPunish(Long hortationPunishId) throws Exception {
    HortationPunishPO po = null;
    begin();
    try {
      po = (HortationPunishPO)this.session.load(HortationPunishPO.class, hortationPunishId);
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return po;
  }
  
  public Boolean updateHortationPunish(HortationPunishPO hortationPunishPO, Long id) throws Exception {
    Boolean success = new Boolean(false);
    begin();
    try {
      Iterator iter = this.session.createQuery(
          "select po.id from com.js.oa.hr.personnelmanager.po.HortationPunishPO po  where po.hp_name='" + 
          hortationPunishPO.getHp_name() + "' and po.domain=" + hortationPunishPO.getDomain() + " and po.id<>" + id).iterate();
      if (!iter.hasNext()) {
        HortationPunishPO modifyPO = (HortationPunishPO)this.session.load(
            HortationPunishPO.class, id);
        modifyPO.setHp_name(hortationPunishPO.getHp_name());
        modifyPO.setHp_type(hortationPunishPO.getHp_type());
        modifyPO.setHp_apply(hortationPunishPO.getHp_apply());
        modifyPO.setHp_dealwith(hortationPunishPO.getHp_dealwith());
        modifyPO.setHp_describe(hortationPunishPO.getHp_describe());
        this.session.update(modifyPO);
        this.session.flush();
        success = Boolean.TRUE;
      } 
    } catch (Exception e) {
      success = Boolean.FALSE;
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return success;
  }
  
  public List getHortationPunishList(String domain) throws Exception {
    ArrayList alist = new ArrayList();
    begin();
    try {
      alist = (ArrayList)this.session.createQuery("select a.id,a.hp_name,a.hp_type,a.hp_apply,a.hp_dealwith,a.hp_describe from com.js.oa.hr.personnelmanager.po.hortationPunishPO a where a.domain=" + domain).list();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return alist;
  }
}
