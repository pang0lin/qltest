package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.personnelmanager.po.EmpSocialinsurancePO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;
import net.sf.hibernate.HibernateException;

public interface EmpSocialinsuranceEJB extends EJBObject {
  Boolean save(EmpSocialinsurancePO paramEmpSocialinsurancePO) throws HibernateException, RemoteException;
  
  EmpSocialinsurancePO load(Long paramLong) throws HibernateException, RemoteException;
  
  Boolean modify(EmpSocialinsurancePO paramEmpSocialinsurancePO) throws HibernateException, RemoteException;
  
  Boolean delete(Long paramLong) throws HibernateException, RemoteException;
  
  Boolean batchDel(String paramString) throws HibernateException, RemoteException;
}
