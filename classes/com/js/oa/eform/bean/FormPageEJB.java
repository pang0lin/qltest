package com.js.oa.eform.bean;

import com.js.oa.eform.po.TPagePO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;
import net.sf.hibernate.HibernateException;

public interface FormPageEJB extends EJBObject {
  Long save(TPagePO paramTPagePO) throws HibernateException, RemoteException;
  
  List search(String paramString1, String paramString2) throws HibernateException, RemoteException;
  
  Boolean delete(String paramString) throws Exception, RemoteException;
  
  Boolean update(TPagePO paramTPagePO) throws Exception, RemoteException;
  
  List getSingleForm(String paramString) throws HibernateException, RemoteException;
  
  List getFormBaseInfo(String paramString) throws HibernateException, RemoteException;
  
  String getSelectedSubField(String paramString) throws Exception;
  
  TPagePO getPageFromPageId(Long paramLong) throws Exception;
  
  List getFeildsList(String paramString) throws HibernateException;
}
