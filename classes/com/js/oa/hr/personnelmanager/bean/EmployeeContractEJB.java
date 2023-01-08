package com.js.oa.hr.personnelmanager.bean;

import com.js.system.vo.usermanager.ContractVO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface EmployeeContractEJB extends EJBObject {
  Boolean save(ContractVO paramContractVO, Long paramLong) throws Exception, RemoteException;
  
  ContractVO load(Long paramLong) throws Exception, RemoteException;
  
  Boolean update(ContractVO paramContractVO, Long paramLong1, Long paramLong2) throws Exception, RemoteException;
  
  Boolean delete(Long paramLong) throws Exception, RemoteException;
}
