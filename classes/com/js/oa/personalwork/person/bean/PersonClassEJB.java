package com.js.oa.personalwork.person.bean;

import com.js.oa.personalwork.person.po.PersonClassPO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;
import net.sf.hibernate.HibernateException;

public interface PersonClassEJB extends EJBObject {
  Boolean hasSameClassName(Long paramLong, String paramString1, String paramString2, String paramString3) throws HibernateException, RemoteException;
  
  void delBatch(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  void delAll(String paramString1, String paramString2) throws Exception, RemoteException;
  
  void add(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10) throws Exception, RemoteException;
  
  Integer update(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) throws Exception, RemoteException;
  
  PersonClassPO load(String paramString) throws Exception, RemoteException;
}
