package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.personnelmanager.po.EmpAttendancePO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;
import net.sf.hibernate.HibernateException;

public interface EmpAttendanceEJB extends EJBObject {
  Boolean save(EmpAttendancePO paramEmpAttendancePO) throws HibernateException, RemoteException;
  
  EmpAttendancePO load(Long paramLong) throws HibernateException, RemoteException;
  
  Boolean modify(EmpAttendancePO paramEmpAttendancePO) throws HibernateException, RemoteException;
  
  Boolean delete(Long paramLong) throws HibernateException, RemoteException;
  
  Boolean batchDel(String paramString) throws HibernateException, RemoteException;
  
  Boolean checkExists(Long paramLong1, Long paramLong2, String paramString) throws Exception, RemoteException;
}
