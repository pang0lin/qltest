package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.personnelmanager.po.EmpRemindPO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBLocalObject;
import net.sf.hibernate.HibernateException;

public interface EmpRemindEJBLocal extends EJBLocalObject {
  Boolean save(EmpRemindPO paramEmpRemindPO) throws HibernateException, RemoteException;
  
  EmpRemindPO load(Long paramLong) throws HibernateException, RemoteException;
  
  Boolean modify(EmpRemindPO paramEmpRemindPO) throws HibernateException, RemoteException;
  
  Boolean delete(String paramString) throws HibernateException, RemoteException;
  
  List selectRemindList() throws HibernateException, RemoteException;
}
