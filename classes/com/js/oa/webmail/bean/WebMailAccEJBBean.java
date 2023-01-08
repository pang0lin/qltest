package com.js.oa.webmail.bean;

import com.js.oa.webmail.po.WebMailAcc;
import com.js.oa.webmail.util.WebMailAccManager;
import com.js.util.hibernate.HibernateBase;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.Query;

public class WebMailAccEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public List getMailAccList() throws Exception {
    begin();
    List list = null;
    try {
      Query query = this.session.createQuery("from WebMailAcc");
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public WebMailAcc getMailAccInfoById(Long mailAccId) throws Exception {
    begin();
    WebMailAcc list = null;
    try {
      Query query = this.session.createQuery("from WebMailAcc wma where wma.mailAccId=" + mailAccId);
      list = (WebMailAcc)query.uniqueResult();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public List getMailAccListByUserAcco(Long userId, String account) throws Exception {
    begin();
    List list = null;
    try {
      Query query = this.session.createQuery("from WebMailAcc wma where wma.userId=" + userId + " and wma.mailAccUser='" + account + "'");
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public List getMailAccListByUserId(Long userId, String flag) throws Exception {
    begin();
    List list = null;
    try {
      Query query = this.session.createQuery("from WebMailAcc wma where wma.userId=" + userId + " and wma.defaultFlag='1'");
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public void createMailAcc(WebMailAcc webMailAcc) throws Exception {
    begin();
    try {
      this.session.save(webMailAcc);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      WebMailAccManager.getInstance().init();
    } 
  }
  
  public void modMailAcc(WebMailAcc webMailAcc) throws Exception {
    begin();
    try {
      this.session.saveOrUpdate(webMailAcc);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      WebMailAccManager.getInstance().init();
    } 
  }
  
  public void delMailAcc(String id, String[] ids) throws Exception {
    begin();
    try {
      if (id != null && !id.equals(""))
        this.session.delete("from WebMailAcc wma where wma.mailAccId=" + id); 
      if (ids != null && ids.length > 0) {
        StringBuffer hql = new StringBuffer();
        for (int i = 0; i < ids.length; i++)
          hql.append(String.valueOf(ids[i]) + ","); 
        this.session.delete("from WebMailAcc wma where wma.mailAccId in(" + hql.toString().substring(0, hql.toString().length() - 1) + ")");
      } 
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      WebMailAccManager.getInstance().init();
    } 
  }
}
