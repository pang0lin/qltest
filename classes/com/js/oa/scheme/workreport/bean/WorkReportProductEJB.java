package com.js.oa.scheme.workreport.bean;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.ejb.EJBObject;

public interface WorkReportProductEJB extends EJBObject {
  Vector list(String paramString1, String paramString2) throws Exception, RemoteException;
  
  Map load(String paramString) throws Exception, RemoteException;
  
  void add(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3) throws Exception, RemoteException;
  
  void update(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3) throws Exception, RemoteException;
  
  String delBatch(String paramString) throws Exception, RemoteException;
  
  List see(String paramString, Long paramLong) throws Exception, RemoteException;
  
  String template(String paramString, Long paramLong) throws Exception, RemoteException;
  
  List initList(List paramList) throws Exception, RemoteException;
}
