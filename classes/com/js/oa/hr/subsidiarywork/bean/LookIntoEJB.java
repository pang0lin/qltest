package com.js.oa.hr.subsidiarywork.bean;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBObject;

public interface LookIntoEJB extends EJBObject {
  void delBatch(String paramString) throws Exception, RemoteException;
  
  void delAll(String paramString) throws Exception, RemoteException;
  
  void add(Object[] paramArrayOfObject, String paramString1, String paramString2) throws Exception, RemoteException;
  
  Map load(String paramString) throws Exception, RemoteException;
  
  void update(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String paramString8, String paramString9) throws Exception, RemoteException;
  
  String voteAdd(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception, RemoteException;
  
  List voteList(String paramString1, String paramString2) throws Exception, RemoteException;
}
