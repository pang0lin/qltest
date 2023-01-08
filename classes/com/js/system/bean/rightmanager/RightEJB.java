package com.js.system.bean.rightmanager;

import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface RightEJB extends EJBObject {
  List getRightType() throws Exception, RemoteException;
  
  List getRightIdAndName(String paramString) throws Exception, RemoteException;
  
  List getIdTypeName(String paramString1, String paramString2) throws Exception, RemoteException;
  
  List getRightInfo(String paramString) throws Exception, RemoteException;
  
  List getRoleId(String paramString) throws Exception, RemoteException;
  
  void updateRole(String paramString, String[] paramArrayOfString) throws Exception, RemoteException;
  
  List getUserRightScope(String paramString) throws Exception, RemoteException;
}
