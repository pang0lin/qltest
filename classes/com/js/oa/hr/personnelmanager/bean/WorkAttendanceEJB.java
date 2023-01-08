package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.personnelmanager.po.WorkAttendancePO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface WorkAttendanceEJB extends EJBObject {
  void testMethod() throws RemoteException;
  
  Boolean save(WorkAttendancePO paramWorkAttendancePO) throws Exception, RemoteException;
  
  List stat(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  Boolean delete(String paramString) throws Exception, RemoteException;
  
  Object[] getSingle(String paramString) throws Exception, RemoteException;
  
  Boolean update(WorkAttendancePO paramWorkAttendancePO) throws Exception, RemoteException;
}
