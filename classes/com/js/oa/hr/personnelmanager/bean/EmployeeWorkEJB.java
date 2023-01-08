package com.js.oa.hr.personnelmanager.bean;

import com.js.system.vo.usermanager.WorkVO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface EmployeeWorkEJB extends EJBObject {
  Boolean save(WorkVO paramWorkVO, Long paramLong) throws Exception, RemoteException;
  
  WorkVO load(Long paramLong) throws Exception, RemoteException;
  
  Boolean update(WorkVO paramWorkVO, Long paramLong1, Long paramLong2) throws Exception, RemoteException;
  
  Boolean delete(Long paramLong) throws Exception, RemoteException;
}
