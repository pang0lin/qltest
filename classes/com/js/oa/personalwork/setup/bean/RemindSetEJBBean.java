package com.js.oa.personalwork.setup.bean;

import com.js.oa.personalwork.setup.po.RemindSetPO;
import com.js.oa.personalwork.setup.po.RemindTypePO;
import com.js.util.hibernate.HibernateBase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.HibernateException;

public class RemindSetEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public List seachByUserId(long userId) throws HibernateException {
    List list = new ArrayList();
    begin();
    try {
      list = this.session.createQuery("select remindset from com.js.oa.personalwork.setup.po.RemindSetPO remindset where remindset.emp_id=" + userId).list();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public void remindSet(long userId, String type, String status) throws HibernateException {
    RemindSetPO remindSetPO = null;
    begin();
    status = (status == null || status.equals("")) ? "-1" : status;
    try {
      remindSetPO = (RemindSetPO)this.session.createQuery("select remindset from com.js.oa.personalwork.setup.po.RemindSetPO remindset where remindset.emp_id=" + userId + " and remindset.type like '" + type + "'").uniqueResult();
      if (remindSetPO == null) {
        remindSetPO = new RemindSetPO();
        remindSetPO.setEmp_id(userId);
        remindSetPO.setStatus(status);
        remindSetPO.setType(type);
        this.session.save(remindSetPO);
      } else {
        remindSetPO.setStatus(status);
        this.session.update(remindSetPO);
      } 
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
  }
  
  public String getStatusByEmpId(long userId, String type) throws HibernateException {
    RemindSetPO remindSetPO = null;
    String re = "";
    begin();
    try {
      remindSetPO = (RemindSetPO)this.session.createQuery("select remindset from com.js.oa.personalwork.setup.po.RemindSetPO remindset where remindset.emp_id=" + userId + " and remindset.type like '" + type + "'").uniqueResult();
      if (remindSetPO == null) {
        re = "N";
      } else {
        re = remindSetPO.getStatus();
      } 
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return re;
  }
  
  public List getRemindType() throws HibernateException {
    List list = new ArrayList();
    begin();
    try {
      list = this.session.createQuery("select po from com.js.oa.personalwork.setup.po.RemindTypePO po ").list();
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public Map<String, String> getRemindTypeMap() throws HibernateException {
    Map<String, String> map = new HashMap<String, String>();
    List<RemindTypePO> list = new ArrayList();
    RemindTypePO remindTypePO = new RemindTypePO();
    begin();
    try {
      list = this.session.createQuery("select po from com.js.oa.personalwork.setup.po.RemindTypePO po ").list();
      if (!list.isEmpty())
        for (int i = 0; i < list.size(); i++) {
          remindTypePO = list.get(i);
          map.put(remindTypePO.getType(), remindTypePO.getName());
        }  
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return map;
  }
}
