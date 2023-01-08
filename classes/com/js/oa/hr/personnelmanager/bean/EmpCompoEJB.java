package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.personnelmanager.po.EmpCompoPO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;
import net.sf.hibernate.HibernateException;

public interface EmpCompoEJB extends EJBObject {
  Boolean save(EmpCompoPO paramEmpCompoPO) throws HibernateException, RemoteException;
  
  EmpCompoPO load(Long paramLong) throws HibernateException, RemoteException;
  
  Boolean modify(EmpCompoPO paramEmpCompoPO) throws HibernateException, RemoteException;
  
  Boolean delete(Long paramLong) throws HibernateException, RemoteException;
  
  Boolean batchDel(String paramString) throws HibernateException, RemoteException;
}
