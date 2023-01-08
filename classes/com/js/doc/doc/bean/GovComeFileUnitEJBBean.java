package com.js.doc.doc.bean;

import com.js.doc.doc.po.GovComeFileUnitPO;
import com.js.util.hibernate.HibernateBase;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class GovComeFileUnitEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public String add(GovComeFileUnitPO po) throws Exception {
    String retString = "false";
    begin();
    try {
      this.session.save(po);
      this.session.flush();
      retString = "true";
    } catch (Exception e) {
      this.transaction.rollback();
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
        this.session.delete(" from com.js.doc.doc.po.GovComeFileUnitPO po where po.id in(" + 
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
  
  public GovComeFileUnitPO load(String editId) throws Exception {
    GovComeFileUnitPO po = null;
    begin();
    try {
      po = (GovComeFileUnitPO)this.session.load(GovComeFileUnitPO.class, Long.valueOf(editId));
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return po;
  }
  
  public String update(String id, String comeFileUnitName) throws Exception {
    String retString = "false";
    begin();
    try {
      GovComeFileUnitPO lpo = (GovComeFileUnitPO)this.session.load(GovComeFileUnitPO.class, Long.valueOf(id));
      lpo.setComeFileUnitName(comeFileUnitName);
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
  
  public List getComeFileUnit(String wherePara) throws Exception {
    List list = new ArrayList();
    begin();
    try {
      list = this.session.createQuery("select po.comeFileUnitName  from com.js.doc.doc.po.GovComeFileUnitPO po where " + wherePara).list();
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
}
