package com.js.oa.webmail.bean;

import com.js.oa.webmail.po.WebMail;
import com.js.oa.webmail.util.AffixManager;
import com.js.oa.webmail.util.UUIDManager;
import com.js.oa.webmail.util.WebMailInfoManager;
import com.js.util.hibernate.HibernateBase;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.Query;

public class WebMailEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Serializable createAll(Collection entities, Collection ent) throws Exception {
    Serializable result = null;
    begin();
    try {
      int i = 0;
      if (entities != null) {
        Iterator it = entities.iterator();
        while (it.hasNext()) {
          Object object = it.next();
          result = this.session.save(object);
          this.session.flush();
          i++;
          if (i % 20 == 0)
            this.session.clear(); 
        } 
      } 
      if (ent != null) {
        Iterator its = ent.iterator();
        while (its.hasNext()) {
          Object object = its.next();
          result = this.session.save(object);
          this.session.flush();
          i++;
          if (i % 20 == 0)
            this.session.clear(); 
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
      WebMailInfoManager.getInstance().init();
      AffixManager.getInstance().init();
    } 
    return result;
  }
  
  public Serializable createAllUUID(Collection entities) throws Exception {
    Serializable result = null;
    begin();
    try {
      createAll(entities);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
      UUIDManager.getInstance().init();
    } 
    return result;
  }
  
  public Map getSingleWebMail(String mailId) throws Exception {
    begin();
    Map<Object, Object> map = new HashMap<Object, Object>();
    try {
      map.clear();
      Query query = this.session.createQuery("from WebMail mail where mail.mailInfoId='" + mailId + "'");
      WebMail wm = (WebMail)query.uniqueResult();
      Query query1 = this.session.createQuery("from Affix affix where affix.mailId in('" + wm.getMailId() + "')");
      List list = query1.list();
      map.put("WebMail", wm);
      map.put("AffixList", list);
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return map;
  }
  
  public List getWebMailList(String[] mailId) throws Exception {
    begin();
    List<WebMail> list = null;
    try {
      if (mailId != null && mailId.length > 0) {
        list = new ArrayList();
        for (int i = 0; i < mailId.length; i++) {
          Query query = this.session.createQuery("from WebMail mail where mail.mailInfoId='" + mailId[i] + "'");
          WebMail wm = (WebMail)query.uniqueResult();
          list.add(wm);
        } 
      } 
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public List getWebMailList() throws Exception {
    begin();
    List list = null;
    try {
      Query query = this.session.createQuery("from WebMail");
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public List getWebMailListByBoxId(String boxid, String mailIds) throws Exception {
    begin();
    List list = null;
    try {
      StringBuffer sb = new StringBuffer();
      sb.append("from WebMail w where w.mailBox='" + boxid + "' ");
      if (mailIds != null && !mailIds.equals("") && !mailIds.equals("null"))
        sb.append(" and w.mailInfoId in (" + mailIds + ")"); 
      Query query = this.session.createQuery(sb.toString());
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public List getAllUUIDList() throws Exception {
    begin();
    List list = null;
    try {
      Query query = this.session.createQuery("from WebMailTemp");
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public List getAllUUIDListById(String userId) throws Exception {
    begin();
    List list = null;
    try {
      Query query = this.session.createQuery("select w.mailId from WebMailTemp  w where w.mailId like '%$" + userId + "$%'");
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public void modMailInfo(WebMail wm) throws Exception {
    begin();
    try {
      this.session.update(wm);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
    } 
  }
  
  public void moveMail2_(Collection entities) throws Exception {
    begin();
    try {
      int i = 0;
      Iterator it = entities.iterator();
      while (it.hasNext()) {
        Object object = it.next();
        this.session.update(object);
        i++;
        if (i % 20 == 0)
          this.session.clear(); 
        this.session.flush();
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
    } 
  }
  
  public void delMail(String[] mailIds) throws Exception {
    begin();
    try {
      if (mailIds != null && mailIds.length > 0)
        for (int i = 0; i < mailIds.length; i++) {
          Query query = this.session.createQuery("from WebMail wm where wm.mailInfoId='" + mailIds[i] + "'");
          if (query.list() != null && query.list().size() > 0)
            for (int j = 0; j < query.list().size(); j++) {
              WebMail wm = query.list().get(j);
              this.session.delete("from Affix a where a.mailId='" + wm.getMailId() + "'");
              this.session.delete("from Attach a where a.mailInfoId=" + wm.getMailInfoId());
            }  
          this.session.delete("from WebMail mail where mail.mailInfoId='" + mailIds[i] + "'");
        }  
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      WebMailInfoManager.getInstance().init();
      AffixManager.getInstance().init();
    } 
  }
  
  public void delAll() throws Exception {
    begin();
    try {
      Query query = this.session.createQuery("from WebMail wm where wm.mailBox='2'");
      if (query.list() != null && query.list().size() > 0)
        for (int i = 0; i < query.list().size(); i++) {
          WebMail wm = query.list().get(i);
          this.session.delete("from Affix a where a.mailId='" + wm.getMailId() + "'");
          this.session.delete("from Attach a where a.mailInfoId=" + wm.getMailInfoId());
        }  
      this.session.delete("from WebMail wm where wm.mailBox='2'");
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      WebMailInfoManager.getInstance().init();
      AffixManager.getInstance().init();
    } 
  }
  
  public Long createMail(WebMail wm) throws Exception {
    begin();
    Long mailInfoId = null;
    try {
      this.session.save(wm);
      this.session.flush();
      WebMail mail = (WebMail)this.session.load(WebMail.class, wm.getMailInfoId());
      mailInfoId = mail.getMailInfoId();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
    } 
    return mailInfoId;
  }
  
  public void updateMail(WebMail wm) throws Exception {
    begin();
    try {
      this.session.update(wm);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
    } 
  }
  
  public Float getMailboxSize(Long userId) throws Exception {
    begin();
    float result = 1000.0F;
    try {
      Iterator<Integer> iter = this.session.iterate("select emp.mailboxSize from EmployeeVO emp where emp.empId=" + userId);
      String mailBoxSize = "0";
      if (iter.hasNext()) {
        Object tmp = iter.next();
        if (tmp != null && !"".equals(tmp.toString()) && 
          !tmp.toString().equalsIgnoreCase("null"))
          mailBoxSize = tmp.toString(); 
      } 
      iter = this.session.iterate("select sum(mail.mailSize) from WebMail mail  where mail.userId=" + userId);
      Integer accessorySize = Integer.valueOf(0);
      if (iter.hasNext()) {
        accessorySize = iter.next();
        if (accessorySize == null)
          accessorySize = Integer.valueOf(0); 
      } 
      result = Float.parseFloat(mailBoxSize) - Float.parseFloat(accessorySize.toString()) / 1048576.0F;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return Float.valueOf((new StringBuilder(String.valueOf(result))).toString());
  }
}
