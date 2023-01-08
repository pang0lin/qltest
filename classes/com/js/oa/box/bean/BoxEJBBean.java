package com.js.oa.box.bean;

import com.js.oa.box.po.BoxPO;
import com.js.util.hibernate.HibernateBase;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class BoxEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public void addBox(BoxPO boxPO) throws Exception {
    try {
      begin();
      this.session.save(boxPO);
      this.session.flush();
      this.session.close();
    } catch (Exception ex) {
      ex.printStackTrace();
      this.session.close();
    } 
  }
  
  public List getAllBox(String userId) throws Exception {
    List list = null;
    try {
      begin();
      list = this.session.createQuery("from BoxPO boxPO where boxPO.userID='" + userId + "'").list();
      if (list.size() <= 0)
        list = new ArrayList(); 
      this.session.close();
    } catch (Exception ex) {
      this.session.close();
    } 
    return list;
  }
  
  public BoxPO getOne(String id) throws Exception {
    BoxPO oneBoxPO = null;
    try {
      begin();
      List<BoxPO> list = this.session.createQuery("from  BoxPO boxPO  where boxPO.id='" + id + "'").list();
      if (list.size() > 0)
        oneBoxPO = list.get(0); 
      this.session.close();
    } catch (Exception ex) {
      ex.printStackTrace();
      this.session.close();
    } 
    return oneBoxPO;
  }
  
  public List getByName(String name) throws Exception {
    List list = null;
    try {
      begin();
      list = this.session.createQuery("from BoxPO boxPO where boxPO.Name like '%" + name + "%'").list();
      if (list.size() <= 0)
        list = new ArrayList(); 
      this.session.close();
    } catch (Exception ex) {
      this.session.close();
    } 
    return list;
  }
}
