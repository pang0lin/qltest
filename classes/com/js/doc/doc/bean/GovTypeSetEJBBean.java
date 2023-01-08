package com.js.doc.doc.bean;

import com.js.doc.doc.po.GovTypeSetPO;
import com.js.util.hibernate.HibernateBase;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class GovTypeSetEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public String add(GovTypeSetPO po) throws Exception {
    begin();
    String retString = "false";
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
        this.session.delete(
            " from com.js.doc.doc.po.GovTypeSetPO po where po.id in(" + 
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
  
  public GovTypeSetPO load(String editId) throws Exception {
    begin();
    GovTypeSetPO form = new GovTypeSetPO();
    Long tmpId = Long.valueOf(editId);
    try {
      form = (GovTypeSetPO)this.session.load(GovTypeSetPO.class, tmpId);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return form;
  }
  
  public String update(String id, GovTypeSetPO form) throws Exception {
    begin();
    String retString = "false";
    Long tmpId = Long.valueOf(id);
    try {
      GovTypeSetPO lpo = (GovTypeSetPO)this.session.load(GovTypeSetPO.class, tmpId);
      lpo.setTypeSetName(form.getTypeSetName());
      lpo.setTypeSetWordNumber(form.getTypeSetWordNumber());
      lpo.setSendToUser(form.getSendToUser());
      lpo.setSendToOrg(form.getSendToOrg());
      lpo.setSendToGroup(form.getSendToGroup());
      lpo.setSendToName(form.getSendToName());
      lpo.setRedHeadId(form.getRedHeadId());
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
  
  public List getTypeSet(String wherePara) throws Exception {
    List list = new ArrayList();
    begin();
    try {
      list = this.session.createQuery("select po.id, po.typeSetName from com.js.doc.doc.po.GovTypeSetPO po where " + 
          wherePara).list();
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
  
  public String getTypeNumber(String typeId) throws Exception {
    begin();
    String retString = "-1";
    Long tmpId = Long.valueOf(typeId);
    try {
      GovTypeSetPO lpo = (GovTypeSetPO)this.session.load(GovTypeSetPO.class, 
          tmpId);
      retString = lpo.getTypeSetWordNumber();
      this.session.flush();
    } catch (Exception e) {
      retString = "-1";
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return retString;
  }
}
