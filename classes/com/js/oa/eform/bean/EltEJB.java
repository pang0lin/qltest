package com.js.oa.eform.bean;

import com.js.oa.eform.po.TEltPO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;
import net.sf.hibernate.HibernateException;

public interface EltEJB extends EJBObject {
  Boolean save(TEltPO paramTEltPO, String paramString1, String paramString2) throws HibernateException, RemoteException;
  
  Boolean delete(String paramString) throws Exception, RemoteException;
}
