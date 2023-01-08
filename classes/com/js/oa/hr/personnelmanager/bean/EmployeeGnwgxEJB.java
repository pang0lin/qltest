package com.js.oa.hr.personnelmanager.bean;

import com.js.system.vo.usermanager.GnwgxVO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface EmployeeGnwgxEJB extends EJBObject {
  Boolean save(GnwgxVO paramGnwgxVO, Long paramLong) throws Exception, RemoteException;
  
  GnwgxVO load(Long paramLong) throws Exception, RemoteException;
  
  Boolean update(GnwgxVO paramGnwgxVO, Long paramLong1, Long paramLong2) throws Exception, RemoteException;
  
  Boolean delete(Long paramLong) throws Exception, RemoteException;
}
