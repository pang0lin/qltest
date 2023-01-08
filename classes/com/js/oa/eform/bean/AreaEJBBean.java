package com.js.oa.eform.bean;

import com.js.oa.eform.po.TAreaPO;
import com.js.oa.eform.po.TAreaTypePO;
import com.js.oa.eform.po.TPagePO;
import com.js.oa.userdb.util.DbOpt;
import com.js.util.hibernate.HibernateBase;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.HibernateException;

public class AreaEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Long save(TAreaPO areaPO, String pageId, String areaTypeId) throws HibernateException {
    Long areaId = null;
    begin();
    try {
      areaPO.setTpage((TPagePO)this.session.load(TPagePO.class, new Long(pageId)));
      areaPO.setAreatype((TAreaTypePO)this.session.load(TAreaTypePO.class, new Long(areaTypeId)));
      areaId = (Long)this.session.save(areaPO);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
    } 
    return areaId;
  }
  
  public Boolean delete(String pageId) throws Exception {
    DbOpt dbopt = null;
    Boolean success = new Boolean(false);
    try {
      dbopt = new DbOpt();
      dbopt.executeUpdate("delete from TAREA where PAGE_ID=" + pageId);
      success = Boolean.TRUE;
      dbopt.close();
    } catch (Exception e) {
      dbopt.close();
      System.out.print("AreaEJBBean error on execute delete function:");
      e.printStackTrace();
    } 
    return success;
  }
}
