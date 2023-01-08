package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.personnelmanager.po.EmployeeChangePO;
import com.js.oa.hr.personnelmanager.po.EmployeeChangeTypePO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;
import net.sf.hibernate.HibernateException;

public interface EmpChangeEJB extends EJBObject {
  Boolean hasSameNameExists(String paramString1, String paramString2) throws HibernateException, RemoteException;
  
  Boolean hasSameNameExists(String paramString1, String paramString2, Long paramLong) throws HibernateException, RemoteException;
  
  boolean addEmpChange(EmployeeChangePO paramEmployeeChangePO) throws Exception, RemoteException;
  
  boolean deleteEmpChange(Long paramLong) throws Exception, RemoteException;
  
  boolean deleteBatchEmpChange(String paramString) throws Exception, RemoteException;
  
  EmployeeChangePO selectEmpChangeView(Long paramLong) throws HibernateException, RemoteException;
  
  boolean updateEmpChange(EmployeeChangePO paramEmployeeChangePO) throws Exception, RemoteException;
  
  String selectEmpName(Long paramLong) throws HibernateException, RemoteException;
  
  String selectOrgName(Long paramLong) throws HibernateException, RemoteException;
  
  List selectEmpDuty(String paramString) throws HibernateException, RemoteException;
  
  List selectEmpType(String paramString) throws HibernateException, RemoteException;
  
  Integer addEmpChangeType(EmployeeChangeTypePO paramEmployeeChangeTypePO) throws Exception, RemoteException;
  
  boolean deleteEmpChangeType(Long paramLong) throws Exception, RemoteException;
  
  boolean deleteBatchEmpChangeType(String paramString) throws Exception, RemoteException;
  
  EmployeeChangeTypePO selectEmpChangeTypeView(Long paramLong) throws HibernateException, RemoteException;
  
  Integer updateEmpChangeType(EmployeeChangeTypePO paramEmployeeChangeTypePO) throws Exception, RemoteException;
}
