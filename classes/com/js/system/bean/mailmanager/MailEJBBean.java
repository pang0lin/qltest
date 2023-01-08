package com.js.system.bean.mailmanager;

import com.js.system.action.mailmanager.MailActionForm;
import com.js.system.util.SysSetupReader;
import com.js.system.vo.mailmanager.MailVO;
import com.js.util.hibernate.HibernateBase;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.Query;

public class MailEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public void add(MailVO mailVO) throws Exception {
    begin();
    try {
      Long id = (Long)this.session.save(mailVO);
      System.out.println("Id:" + id);
      this.session.flush();
    } catch (Exception e) {
      System.out.println("-----------------------------------------------");
      e.printStackTrace();
      System.out.println("-----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public void del(String mailId, String[] mailIds) throws Exception {
    begin();
    try {
      if (mailId != null && !mailId.equals("")) {
        Query query = this.session.createQuery("from MailVO mail where mail.mailId=" + mailId);
        SysSetupReader.getInstance().removeEmailMap(((MailVO)query.uniqueResult()).getFromUser());
        this.session.delete("from MailVO mail where mail.mailId=" + mailId);
      } 
      if (mailIds != null && mailIds.length > 0) {
        StringBuffer hql = new StringBuffer();
        for (int i = 0; i < mailIds.length; i++) {
          hql.append(String.valueOf(mailIds[i]) + ",");
          Query query = this.session.createQuery("from MailVO mail where mail.mailId=" + mailIds[i]);
          SysSetupReader.getInstance().removeEmailMap(((MailVO)query.uniqueResult()).getFromUser());
        } 
        this.session.delete("from MailVO mail where mail.mailId in(" + hql.toString().substring(0, hql.toString().length() - 1) + ")");
      } 
      this.session.flush();
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
    } 
  }
  
  public void delAll() throws Exception {
    begin();
    try {
      this.session.delete("from MailVO");
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public MailVO getSingleMail(String mailId) throws Exception {
    begin();
    MailVO mailVo = null;
    try {
      Query query = this.session.createQuery("from MailVO mail where mail.mailId=" + mailId);
      mailVo = (MailVO)query.uniqueResult();
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return mailVo;
  }
  
  public MailVO getSingleMailByFromUser(MailActionForm mailForm) throws Exception {
    begin();
    MailVO mailVo = null;
    try {
      StringBuffer hql = new StringBuffer("from MailVO mail where 1=1 ");
      if (mailForm.getFromUser() != null && !mailForm.getFromUser().equals(""))
        hql.append(" and mail.fromUser=:fromUser"); 
      if (mailForm.getMailId() != null && !mailForm.getMailId().equals(""))
        hql.append(" and mail.mailId!=:mailId"); 
      Query query = this.session.createQuery(hql.toString());
      if (mailForm.getFromUser() != null && !mailForm.getFromUser().equals(""))
        query.setString("fromUser", mailForm.getFromUser()); 
      if (mailForm.getMailId() != null && !mailForm.getMailId().equals(""))
        query.setLong("mailId", mailForm.getMailId().longValue()); 
      mailVo = (MailVO)query.uniqueResult();
    } catch (Exception e) {
      System.out.println("----------------------------------------------");
      e.printStackTrace();
      System.out.println("----------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return mailVo;
  }
  
  public List getMailList() throws Exception {
    begin();
    List list = null;
    try {
      Query query = this.session.createQuery("from MailVO");
      list = query.list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public void modMail(MailVO mailVO) throws Exception {
    begin();
    try {
      this.session.saveOrUpdate(mailVO);
      this.session.flush();
      SysSetupReader.getInstance().modEmailMap(mailVO.getFromUser(), mailVO.getPassWord(), mailVO.getBakString1(), mailVO.getPort(), mailVO.getEncryptionType());
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
  }
}
