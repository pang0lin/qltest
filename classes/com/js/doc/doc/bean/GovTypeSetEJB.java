package com.js.doc.doc.bean;

import com.js.doc.doc.po.GovTypeSetPO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface GovTypeSetEJB extends EJBObject {
  String add(GovTypeSetPO paramGovTypeSetPO) throws Exception, RemoteException;
  
  String delBatch(String paramString) throws Exception, RemoteException;
  
  GovTypeSetPO load(String paramString) throws Exception, RemoteException;
  
  String update(String paramString, GovTypeSetPO paramGovTypeSetPO) throws Exception, RemoteException;
  
  List getTypeSet(String paramString) throws Exception, RemoteException;
  
  String getTypeNumber(String paramString) throws Exception, RemoteException;
}
