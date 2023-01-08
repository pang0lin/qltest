package com.js.oa.eform.bean;

import com.js.oa.eform.po.TAreaPO;
import com.js.oa.eform.po.TEltPO;
import com.js.oa.eform.po.TPagePO;
import com.js.oa.userdb.util.DbOpt;
import com.js.util.hibernate.HibernateBase;
import java.sql.SQLException;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.HibernateException;

public class EltEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Boolean save(TEltPO eltPO, String pageId, String areaId) throws HibernateException {
    Boolean success = new Boolean(true);
    begin();
    try {
      TAreaPO area = (TAreaPO)this.session.load(TAreaPO.class, new Long(areaId));
      eltPO.setTpage((TPagePO)this.session.load(TPagePO.class, new Long(pageId)));
      eltPO.setTarea(area);
      this.session.save(eltPO);
      this.session.flush();
    } catch (Exception e) {
      success = Boolean.FALSE;
      System.out.print("------------------------------------------------");
      System.out.print("EltEJBBean error on execute save function:");
      e.printStackTrace();
      System.out.print("------------------------------------------------");
    } finally {}
    this.session.close();
    this.session = null;
    return success;
  }
  
  public Boolean delete(String pageId) throws Exception {
    DbOpt dbopt = null;
    Boolean success = new Boolean(false);
    try {
      dbopt = new DbOpt();
      dbopt.executeUpdate("delete from TELT where PAGE_ID=" + pageId);
      success = Boolean.TRUE;
      dbopt.close();
    } catch (Exception e) {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
      e.printStackTrace();
    } 
    return success;
  }
}
