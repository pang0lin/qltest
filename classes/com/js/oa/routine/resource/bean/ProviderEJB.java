package com.js.oa.routine.resource.bean;

import com.js.oa.routine.resource.po.ProviderPO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface ProviderEJB extends EJBObject {
  Boolean save(ProviderPO paramProviderPO) throws Exception, RemoteException;
  
  ProviderPO getModifyProvider(String paramString) throws Exception, RemoteException;
  
  Boolean update(ProviderPO paramProviderPO, String paramString) throws Exception, RemoteException;
  
  Boolean delete(String paramString) throws Exception, RemoteException;
}
