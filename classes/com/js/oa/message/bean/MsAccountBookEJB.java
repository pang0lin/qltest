package com.js.oa.message.bean;

import java.rmi.RemoteException;
import javax.ejb.EJBObject;
import net.sf.hibernate.HibernateException;

public interface MsAccountBookEJB extends EJBObject {
  String getMeAccountInfo(String paramString) throws Exception, RemoteException, HibernateException;
}
