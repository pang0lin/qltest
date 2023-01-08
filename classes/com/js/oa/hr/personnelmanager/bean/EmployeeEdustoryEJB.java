package com.js.oa.hr.personnelmanager.bean;

import com.js.system.vo.usermanager.EdustoryVO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface EmployeeEdustoryEJB extends EJBObject {
  Boolean save(EdustoryVO paramEdustoryVO, Long paramLong) throws Exception, RemoteException;
  
  EdustoryVO load(Long paramLong) throws Exception, RemoteException;
  
  Boolean update(EdustoryVO paramEdustoryVO, Long paramLong1, Long paramLong2) throws Exception, RemoteException;
  
  Boolean delete(Long paramLong) throws Exception, RemoteException;
}
