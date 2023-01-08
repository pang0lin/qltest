package com.js.doc.doc.bean;

import com.js.doc.doc.po.GovTopicWordPO;
import com.js.util.hibernate.HibernateBase;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class GovTopicWordEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public String add(GovTopicWordPO po) throws Exception {
    String retString = "false";
    begin();
    try {
      this.session.save(po);
      this.session.flush();
      retString = "true";
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
    } 
    return retString;
  }
  
  public String delBatch(String ids) throws Exception {
    String retString = "false";
    begin();
    try {
      if (ids != null && ids.indexOf(",") != -1) {
        ids = ids.substring(0, ids.length() - 1);
        this.session.delete(" from com.js.doc.doc.po.GovTopicWordPO po where po.id in(" + 
            ids + ")");
        this.session.flush();
        retString = "true";
      } 
    } catch (Exception e) {
      retString = "false";
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return retString;
  }
  
  public GovTopicWordPO load(String editId) throws Exception {
    GovTopicWordPO po = null;
    begin();
    Long tmpId = Long.valueOf(editId);
    try {
      po = (GovTopicWordPO)this.session.load(GovTopicWordPO.class, tmpId);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return po;
  }
  
  public String update(String id, GovTopicWordPO po) throws Exception {
    begin();
    String retString = "false";
    Long tmpId = Long.valueOf(id);
    try {
      GovTopicWordPO lpo = (GovTopicWordPO)this.session.load(GovTopicWordPO.class, 
          tmpId);
      lpo.setTopicWordName(po.getTopicWordName());
      this.session.update(lpo);
      this.session.flush();
      retString = "true";
    } catch (Exception e) {
      retString = "false";
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return retString;
  }
}
