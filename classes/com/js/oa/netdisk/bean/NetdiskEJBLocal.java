package com.js.oa.netdisk.bean;

import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBLocalObject;

public interface NetdiskEJBLocal extends EJBLocalObject {
  boolean addfolder(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) throws Exception;
  
  boolean deletemydisk(String paramString1, String paramString2, String paramString3) throws Exception;
  
  boolean copyorcut(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception;
  
  boolean shared(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11) throws Exception;
  
  boolean rename(String paramString1, String paramString2) throws Exception;
  
  boolean deleteall(String paramString1, String paramString2, String paramString3) throws Exception;
  
  String checksharetype(String paramString) throws Exception;
  
  boolean copyorcutshare(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) throws Exception;
  
  String getInfoReader(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  String getupuse(String paramString) throws Exception;
  
  boolean saveupload(List paramList) throws Exception;
  
  boolean unshared(String paramString) throws Exception;
  
  List getinfodetail(String paramString) throws Exception;
  
  List getWriteAccess(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception, RemoteException;
  
  List getReadAccess(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  boolean checkFolderName(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception, RemoteException;
}
