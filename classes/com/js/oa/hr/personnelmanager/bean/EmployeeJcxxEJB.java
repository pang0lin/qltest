package com.js.oa.hr.personnelmanager.bean;

import com.js.system.vo.usermanager.JcxxVO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface EmployeeJcxxEJB extends EJBObject {
  Boolean save(JcxxVO paramJcxxVO, Long paramLong) throws Exception, RemoteException;
  
  JcxxVO load(Long paramLong) throws Exception, RemoteException;
  
  Boolean update(JcxxVO paramJcxxVO, Long paramLong1, Long paramLong2) throws Exception, RemoteException;
  
  Boolean delete(Long paramLong) throws Exception, RemoteException;
}
