package com.js.oa.personalwork.setup.bean;

import com.js.oa.personalwork.setup.po.WorkProxyPO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface WorkProxyEJB extends EJBObject {
  void delBatch(String paramString1, String paramString2) throws Exception, RemoteException;
  
  void delAll(String paramString) throws Exception, RemoteException;
  
  void add(WorkProxyPO paramWorkProxyPO, String paramString) throws Exception, RemoteException;
  
  WorkProxyPO load(String paramString) throws Exception, RemoteException;
  
  void update(WorkProxyPO paramWorkProxyPO, String paramString1, String paramString2) throws Exception, RemoteException;
  
  String getAvailableProxy(String paramString) throws Exception, RemoteException;
  
  String[] getAvailableUsers(String[] paramArrayOfString) throws Exception, RemoteException;
  
  void setUnavailableProxy() throws Exception, RemoteException;
}
