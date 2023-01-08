package com.js.system.bean.messages;

import com.js.system.vo.messages.Remind;
import com.js.util.hibernate.HibernateBase;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class RemindBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public void addRemind(Remind forumRemind) throws Exception {
    begin();
    try {
      this.session.save(forumRemind);
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public List searchReminduser(long forumid, String remind_type) throws Exception {
    List list = new ArrayList();
    begin();
    try {
      list = this.session.createQuery("select remind from com.js.system.vo.messages.Remind remind where remind.data_id =" + forumid + " and remind.remind_type like '" + remind_type + "'").list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public String searchYesOrNo(long userId, long dataId, String remind_type) throws Exception {
    String re = "N";
    List list = new ArrayList();
    begin();
    try {
      list = this.session.createQuery("select remind from com.js.system.vo.messages.Remind remind where remind.data_id =" + dataId + " and remind.remind_type like '" + remind_type + "'  and remind.emp_id=" + userId).list();
      if (!list.isEmpty())
        re = "Y"; 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return re;
  }
  
  public void delRemind(long userId, long dataId, String remind_type) throws Exception {
    begin();
    try {
      this.session.delete("from com.js.system.vo.messages.Remind remind where remind.data_id =" + dataId + " and remind.remind_type like '" + remind_type + "'  and remind.emp_id=" + userId);
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
}
