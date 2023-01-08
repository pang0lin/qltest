package com.js.oa.hr.personnelmanager.bean;

import com.js.system.vo.usermanager.ChildrenVO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface EmployeeChildrenEJB extends EJBObject {
  Boolean save(ChildrenVO paramChildrenVO, Long paramLong) throws Exception, RemoteException;
  
  ChildrenVO load(Long paramLong) throws Exception, RemoteException;
  
  Boolean update(ChildrenVO paramChildrenVO, Long paramLong1, Long paramLong2) throws Exception, RemoteException;
  
  Boolean delete(Long paramLong) throws Exception, RemoteException;
}
