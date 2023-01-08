package com.js.wap.bean;

import com.js.util.hibernate.HibernateBase;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class WapCommonBean extends HibernateBase implements SessionBean {
  public void ejbActivate() throws EJBException, RemoteException {}
  
  public void ejbPassivate() throws EJBException, RemoteException {}
  
  public void ejbRemove() throws EJBException, RemoteException {}
  
  public void setSessionContext(SessionContext arg0) throws EJBException, RemoteException {}
  
  public List getOrgList(String domainId, String pId) throws Exception {
    List orgList = new ArrayList();
    String sql = "";
    try {
      begin();
      sql = "from OrganizationVO org where org.orgParentOrgId<>-1 and org.orgStatus<>4 and org.domainId=" + 
        domainId + " and orgParentOrgId=" + pId + " order by org.orgIdString";
      orgList = this.session.createQuery(sql).list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return orgList;
  }
}
