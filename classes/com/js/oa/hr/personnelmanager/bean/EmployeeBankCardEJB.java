package com.js.oa.hr.personnelmanager.bean;

import com.js.system.vo.usermanager.BankCardVO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface EmployeeBankCardEJB extends EJBObject {
  Boolean save(BankCardVO paramBankCardVO, Long paramLong) throws Exception, RemoteException;
  
  BankCardVO load(Long paramLong) throws Exception, RemoteException;
  
  Boolean update(BankCardVO paramBankCardVO, Long paramLong1, Long paramLong2) throws Exception, RemoteException;
  
  Boolean delete(Long paramLong) throws Exception, RemoteException;
}
