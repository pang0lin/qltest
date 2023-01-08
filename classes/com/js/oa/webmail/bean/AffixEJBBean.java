package com.js.oa.webmail.bean;

import com.js.oa.webmail.po.Affix;
import com.js.oa.webmail.util.AffixManager;
import com.js.util.hibernate.HibernateBase;
import java.util.Collection;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.Query;

public class AffixEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Affix getAffixByPath(String oldPath) throws Exception {
    begin();
    Affix affix = null;
    try {
      Query query = this.session.createQuery("from Affix a where a.affixPath='" + oldPath + "'");
      affix = (Affix)query.uniqueResult();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return affix;
  }
  
  public List getAffixList() throws Exception {
    begin();
    List affixList = null;
    try {
      Query query = this.session.createQuery("from Affix ");
      affixList = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return affixList;
  }
  
  public List getAffixListByMailId(String mailId) throws Exception {
    begin();
    List affixList = null;
    try {
      Query query = this.session.createQuery("from Affix a where a.mailId='" + mailId + "'");
      affixList = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return affixList;
  }
  
  public void createAttach(Collection entities) throws Exception {
    begin();
    try {
      createAll(entities);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      AffixManager.getInstance().init();
    } 
  }
  
  public List getAttachList() throws Exception {
    begin();
    List attachList = null;
    try {
      Query query = this.session.createQuery("from Attach ");
      attachList = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return attachList;
  }
}
