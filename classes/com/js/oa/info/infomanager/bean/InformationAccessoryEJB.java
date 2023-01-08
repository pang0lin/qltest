package com.js.oa.info.infomanager.bean;

import com.js.oa.info.infomanager.po.InformationAccessoryPO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface InformationAccessoryEJB extends EJBObject {
  List getAccessory(String paramString) throws Exception, RemoteException;
  
  void updateAccessory(String paramString, InformationAccessoryPO paramInformationAccessoryPO) throws Exception, RemoteException;
  
  String getAccessoryFile(String paramString) throws Exception, RemoteException;
  
  List getHistAccessory(String paramString) throws Exception, RemoteException;
  
  String getOneInfoPic(String paramString) throws Exception, RemoteException;
  
  void updateAccessory(String paramString, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4) throws Exception, RemoteException;
}
