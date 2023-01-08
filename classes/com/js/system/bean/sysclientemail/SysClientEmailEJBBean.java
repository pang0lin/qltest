package com.js.system.bean.sysclientemail;

import com.js.system.vo.sysclientemail.SysClientEmailVO;
import com.js.util.hibernate.HibernateBase;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class SysClientEmailEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public List<String> searchEmailUrl() throws Exception {
    List<String> list = new ArrayList<String>();
    begin();
    try {
      list = this.session.createQuery("select po.emailUrl from com.js.system.vo.sysclientemail.SysClientEmailVO po").list();
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public void add(SysClientEmailVO sysClientEmailVO) throws Exception {
    begin();
    try {
      this.session.save(sysClientEmailVO);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
  }
}
