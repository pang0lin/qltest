package com.js.oa.routine.resource.bean;

import com.js.oa.routine.resource.po.TypeDefinePO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface TypeDefineEJB extends EJBObject {
  String save(TypeDefinePO paramTypeDefinePO) throws Exception, RemoteException;
  
  TypeDefinePO load(Long paramLong) throws Exception, RemoteException;
  
  Boolean update(TypeDefinePO paramTypeDefinePO, Long paramLong) throws Exception, RemoteException;
  
  List list(String paramString, Long paramLong) throws Exception, RemoteException;
  
  Boolean delete(Long paramLong) throws Exception, RemoteException;
}
