package com.js.oa.module.bean;

import com.js.oa.module.po.SystemMenuPO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;
import net.sf.hibernate.HibernateException;

public interface ModuleMenuAllEJB extends EJBObject {
  List getOriginalMenuSet(String paramString1, String paramString2) throws HibernateException, Exception, RemoteException;
  
  SystemMenuPO loadMneuSet(String paramString) throws HibernateException, Exception, RemoteException;
  
  boolean updateOriginalMenuSet(SystemMenuPO paramSystemMenuPO) throws HibernateException, RemoteException;
  
  boolean delBatchMenuSet(String paramString1, String paramString2) throws HibernateException, RemoteException;
  
  boolean delAllCustomizeMenuSet(String paramString) throws HibernateException, RemoteException;
  
  List getAllMenuSet(String paramString) throws HibernateException, RemoteException;
  
  Integer checkSubMenuExists(String paramString) throws RemoteException;
  
  List loadMneuSetByCode(String paramString1, String paramString2) throws HibernateException, RemoteException;
  
  Long saveOriginalMenuSet(SystemMenuPO paramSystemMenuPO) throws HibernateException, Exception, RemoteException;
}
