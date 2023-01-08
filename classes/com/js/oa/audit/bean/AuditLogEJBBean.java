package com.js.oa.audit.bean;

import com.js.oa.audit.po.AuditLog;
import com.js.util.hibernate.HibernateBase;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class AuditLogEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Long saveAuditLog(AuditLog auditLog) throws Exception {
    Long result = null;
    begin();
    try {
      result = (Long)this.session.save(auditLog);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return result;
  }
}
