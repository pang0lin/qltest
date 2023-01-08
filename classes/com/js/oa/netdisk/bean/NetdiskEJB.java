package com.js.oa.netdisk.bean;

import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface NetdiskEJB extends EJBObject {
  boolean addfolder(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) throws Exception, RemoteException;
  
  boolean deletemydisk(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  boolean copyorcut(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception, RemoteException;
  
  boolean shared(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11) throws Exception, RemoteException;
  
  boolean rename(String paramString1, String paramString2) throws Exception, RemoteException;
  
  boolean deleteall(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  String checksharetype(String paramString) throws Exception, RemoteException;
  
  boolean copyorcutshare(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) throws Exception, RemoteException;
  
  String getInfoReader(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception, RemoteException;
  
  String getupuse(String paramString) throws Exception, RemoteException;
  
  boolean saveupload(List paramList) throws Exception, RemoteException;
  
  boolean unshared(String paramString) throws Exception, RemoteException;
  
  List getinfodetail(String paramString) throws Exception, RemoteException;
  
  List getWriteAccess(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception, RemoteException;
  
  List getReadAccess(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  boolean checkFolderName(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception, RemoteException;
}
