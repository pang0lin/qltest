package com.js.oa.hr.personnelmanager.bean;

import com.js.system.vo.usermanager.QtqsqkVO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface EmployeeQtqsqkEJB extends EJBObject {
  Boolean save(QtqsqkVO paramQtqsqkVO, Long paramLong) throws Exception, RemoteException;
  
  QtqsqkVO load(Long paramLong) throws Exception, RemoteException;
  
  Boolean update(QtqsqkVO paramQtqsqkVO, Long paramLong1, Long paramLong2) throws Exception, RemoteException;
  
  Boolean delete(Long paramLong) throws Exception, RemoteException;
}
