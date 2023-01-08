package com.js.oa.eform.bean;

import com.js.oa.eform.po.TAreaPO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;
import net.sf.hibernate.HibernateException;

public interface AreaEJB extends EJBObject {
  Long save(TAreaPO paramTAreaPO, String paramString1, String paramString2) throws HibernateException, RemoteException;
  
  Boolean delete(String paramString) throws Exception, RemoteException;
}
