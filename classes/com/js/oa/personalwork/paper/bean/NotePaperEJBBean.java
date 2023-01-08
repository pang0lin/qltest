package com.js.oa.personalwork.paper.bean;

import com.js.oa.personalwork.paper.po.NotePaperPO;
import com.js.util.hibernate.HibernateBase;
import java.util.Date;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class NotePaperEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public String getColor(Long userId) throws Exception {
    String str = "1";
    begin();
    try {
      List list = this.session.createQuery("select po.notePaperColor  from com.js.oa.personalwork.setup.po.OptionSetPO po where po.empId=" + 
          userId).list();
      if (list.size() > 0)
        str = String.valueOf(list.get(0)); 
      this.session.close();
      this.transaction = null;
    } catch (Exception e) {
      e.printStackTrace();
      str = "1";
      throw e;
    } 
    return str;
  }
  
  public void delBatch(String ids, String userId) throws Exception {
    try {
      begin();
      if (ids != null && !ids.equals("")) {
        this.session.delete(
            " from com.js.oa.personalwork.paper.po.NotePaperPO po where po.id in (" + 
            ids.substring(0, ids.length() - 1) + ")" + 
            " and po.empId=" + userId);
        this.session.flush();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception(e.getMessage());
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public void add(String notePaperColor, String notePaperContent, String userId, String domainId) throws Exception {
    NotePaperPO po = new NotePaperPO();
    try {
      begin();
      po.setCreatedTime(new Date());
      po.setEmpId(Long.parseLong(userId));
      po.setNotePaperColor(notePaperColor);
      po.setNotePaperContent(notePaperContent);
      po.setDomainId(domainId);
      this.session.save(po);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception(e.getMessage());
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public void update(String editId, String notPaperColor, String notePaperContent) throws Exception {
    try {
      begin();
      NotePaperPO po = (NotePaperPO)this.session.load(NotePaperPO.class, 
          new Long(editId));
      po.setNotePaperColor(notPaperColor);
      po.setNotePaperContent(notePaperContent);
      po.setUpdateTime(new Date());
      this.session.update(po);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception(e.getMessage());
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public NotePaperPO load(String editId) throws Exception {
    NotePaperPO po = null;
    try {
      begin();
      po = (NotePaperPO)this.session.load(NotePaperPO.class, new Long(editId));
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception(e.getMessage());
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return po;
  }
}
