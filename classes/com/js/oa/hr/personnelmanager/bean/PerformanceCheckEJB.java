package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.personnelmanager.po.PerformanceCheckPO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;
import net.sf.hibernate.HibernateException;

public interface PerformanceCheckEJB extends EJBObject {
  Boolean save(PerformanceCheckPO paramPerformanceCheckPO) throws HibernateException, RemoteException;
  
  PerformanceCheckPO load(Long paramLong) throws HibernateException, RemoteException;
  
  Boolean modify(PerformanceCheckPO paramPerformanceCheckPO) throws HibernateException, RemoteException;
  
  Boolean delete(Long paramLong) throws HibernateException, RemoteException;
  
  Boolean batchDel(String paramString) throws HibernateException, RemoteException;
  
  Boolean checkExists(Long paramLong1, Long paramLong2, String paramString1, String paramString2) throws Exception, RemoteException;
}
