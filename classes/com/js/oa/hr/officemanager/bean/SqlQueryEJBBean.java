package com.js.oa.hr.officemanager.bean;

import com.js.oa.userdb.util.DbOpt;
import com.js.util.hibernate.HibernateBase;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class SqlQueryEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public String[][] query(String sql) {
    String[][] result = (String[][])null;
    DbOpt opt = new DbOpt();
    try {
      opt.createStatement();
      result = opt.executeQueryToStrArr2(sql, 3);
      opt.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
}
