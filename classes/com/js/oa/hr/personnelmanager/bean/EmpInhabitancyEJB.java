package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.personnelmanager.po.EmpInhabitancyPO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;
import net.sf.hibernate.HibernateException;

public interface EmpInhabitancyEJB extends EJBObject {
  Boolean save(EmpInhabitancyPO paramEmpInhabitancyPO) throws HibernateException, RemoteException;
  
  EmpInhabitancyPO load(Long paramLong) throws HibernateException, RemoteException;
  
  Boolean modify(EmpInhabitancyPO paramEmpInhabitancyPO) throws HibernateException, RemoteException;
  
  Boolean delete(Long paramLong) throws HibernateException, RemoteException;
  
  Boolean batchDel(String paramString) throws HibernateException, RemoteException;
  
  List getEmployeeFROMJZZ2(Integer paramInteger) throws Exception, RemoteException;
  
  Boolean saveTOSENDJZZ(Long paramLong, Integer paramInteger) throws Exception, RemoteException;
}
