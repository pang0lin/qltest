package com.js.oa.personalwork.netaddress.bean;

import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface AddressClassEJB extends EJBObject {
  void delBatch(String paramString1, String paramString2) throws Exception, RemoteException;
  
  void delAll(String paramString) throws Exception, RemoteException;
  
  Boolean add(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception, RemoteException;
  
  Boolean update(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception, RemoteException;
  
  String load(String paramString) throws Exception, RemoteException;
}
