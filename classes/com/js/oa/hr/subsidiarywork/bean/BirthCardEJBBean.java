package com.js.oa.hr.subsidiarywork.bean;

import com.js.oa.hr.subsidiarywork.po.BirthdayWishPO;
import com.js.util.hibernate.HibernateBase;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class BirthCardEJBBean extends HibernateBase implements SessionBean {
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
            " from com.js.oa.hr.subsidiarywork.po.BirthdayWishPO po where po.id in (" + 
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
          "from com.js.oa.hr.subsidiarywork.po.BirthdayWishPO po where " + wherePara);
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
  
  public void add(BirthdayWishPO po, String userId1, String orgId1) throws Exception {
    begin();
    try {
      long userId = Long.parseLong(userId1);
      long orgId = Long.parseLong(orgId1);
      po.setCreatedEmp(userId);
      po.setCreatedOrg(orgId);
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
  
  public BirthdayWishPO load(String editId) throws Exception {
    BirthdayWishPO po = null;
    begin();
    try {
      po = (BirthdayWishPO)this.session.load(BirthdayWishPO.class, new Long(editId));
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return po;
  }
  
  public void update(String editId, String wishCard, String wishCardName, String wishEmployees, String wishEmployeesName, String wishSign, String wishContent, String path) throws Exception {
    begin();
    try {
      BirthdayWishPO po = (BirthdayWishPO)this.session.load(BirthdayWishPO.class, new Long(editId));
      po.setWishCard(wishCard);
      po.setWishCardName(wishCardName);
      po.setWishEmployees(wishEmployees);
      po.setWishEmployeesName(wishEmployeesName);
      po.setWishSign(wishSign);
      po.setWishContent(wishContent);
      po.setPath(path);
      this.session.update(po);
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
