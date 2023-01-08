package com.js.oa.hr.personnelmanager.bean;

import com.js.system.vo.usermanager.PxjlVO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface EmployeePxjlEJB extends EJBObject {
  Boolean save(PxjlVO paramPxjlVO, Long paramLong) throws Exception, RemoteException;
  
  PxjlVO load(Long paramLong) throws Exception, RemoteException;
  
  Boolean update(PxjlVO paramPxjlVO, Long paramLong1, Long paramLong2) throws Exception, RemoteException;
  
  Boolean delete(Long paramLong) throws Exception, RemoteException;
}
