package com.js.oa.userdb.newCode.bean;

import com.js.oa.userdb.newCode.po.NewCodePO;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class NewCodeEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public NewCodePO load(Long id) throws Exception {
    NewCodePO po = null;
    try {
      begin();
      po = (NewCodePO)this.session.load(NewCodePO.class, id);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return po;
  }
  
  public Long save(NewCodePO po) throws Exception {
    Long result = new Long(-1L);
    try {
      begin();
      result = (Long)this.session.save(po);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public Boolean del(Long id) throws Exception {
    Boolean result = new Boolean(false);
    DataSourceBase base = null;
    try {
      base = new DataSourceBase();
      base.begin();
      base.executeSQL("delete from jsf_newcode where code_id='" + id + "'");
      base.executeSQL("delete from jsf_newcode_record where code_id='" + id + "'");
      base.end();
      result = Boolean.valueOf(true);
    } catch (Exception ex) {
      if (base != null)
        base.end(); 
      ex.printStackTrace();
    } 
    return result;
  }
  
  public Boolean modi(NewCodePO po) throws Exception {
    Boolean result = new Boolean(false);
    try {
      begin();
      this.session.update(po);
      this.session.flush();
      result = Boolean.TRUE;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return result;
  }
}
