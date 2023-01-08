package com.js.oa.info.channelmanager.bean;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBObject;

public interface DepartmentPageEJB extends EJBObject {
  List getTopChannel(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  List getLeftChTree(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  void ModiDepaStyle(String paramString1, String paramString2) throws Exception, RemoteException;
  
  List departmentDeskop(String paramString1, String paramString2) throws Exception, RemoteException;
  
  void setDepartFlag(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  List getNewAnno(String paramString1, String paramString2) throws Exception, RemoteException;
  
  List getPhotoInfo(String paramString1, String paramString2) throws Exception, RemoteException;
  
  void updateBanner(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  List getMostNewInfo(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  String getOrgBanner(String paramString) throws Exception, RemoteException;
  
  Map departHomepage(String paramString1, String paramString2) throws Exception, RemoteException;
  
  List getCanVindicate(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception, RemoteException;
  
  Boolean deptVindicateInfo(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  List getAllChannel(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  List getCanVindicate(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception, RemoteException;
}
