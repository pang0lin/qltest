package com.js.oa.routine.resource.bean;

import com.js.oa.routine.resource.po.DrawDeptPO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface DrawDeptEJB extends EJBObject {
  Boolean save(DrawDeptPO paramDrawDeptPO, String paramString) throws Exception, RemoteException;
  
  Boolean delete(String paramString) throws Exception, RemoteException;
  
  String[] getSingleDept(String paramString) throws Exception, RemoteException;
  
  Boolean update(DrawDeptPO paramDrawDeptPO, String paramString) throws Exception, RemoteException;
  
  String getVindicate(String paramString) throws Exception, RemoteException;
  
  List getDeptInStock(String paramString) throws Exception, RemoteException;
  
  List getUserManaDept(String paramString) throws Exception, RemoteException;
}
