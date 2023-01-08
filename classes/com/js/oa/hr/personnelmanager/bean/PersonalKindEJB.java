package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.personnelmanager.po.PersonalKindPO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface PersonalKindEJB extends EJBObject {
  Boolean save(PersonalKindPO paramPersonalKindPO) throws Exception, RemoteException;
  
  PersonalKindPO load(Long paramLong) throws Exception, RemoteException;
  
  Boolean update(PersonalKindPO paramPersonalKindPO, Long paramLong) throws Exception, RemoteException;
  
  Boolean delete(String paramString) throws Exception, RemoteException;
  
  List list() throws Exception, RemoteException;
  
  Boolean checkExistKind(Long paramLong, String paramString) throws Exception, RemoteException;
}
