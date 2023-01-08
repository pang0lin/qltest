package com.js.oa.hr.resume.bean;

import com.js.oa.hr.resume.po.ResumePO;
import com.js.oa.userdb.util.DbOpt;
import com.js.util.hibernate.HibernateBase;
import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class ResumeEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbActivate() throws EJBException, RemoteException {}
  
  public void ejbPassivate() throws EJBException, RemoteException {}
  
  public void ejbRemove() throws EJBException, RemoteException {}
  
  public void setSessionContext(SessionContext arg0) throws EJBException, RemoteException {}
  
  public SessionContext getSessionContext() {
    return this.sessionContext;
  }
  
  public ResumePO getResumeInfo(String id) throws Exception {
    ResumePO po = new ResumePO();
    begin();
    try {
      po = (ResumePO)this.session.load(ResumePO.class, Long.valueOf(id));
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return po;
  }
  
  public void save(String id, String zt, String bz) throws Exception {
    DbOpt db = new DbOpt();
    String sql = "update temp_resume set zt = '" + zt + "' ,bz='" + bz + "' where id='" + id + "'";
    db.executeUpdate(sql);
  }
}
