package com.js.system.bean.groupmanager;

import com.js.system.vo.groupmanager.GroupVO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface GroupEJB extends EJBObject {
  Integer add(GroupVO paramGroupVO, String[] paramArrayOfString) throws Exception, RemoteException;
  
  String del(String[] paramArrayOfString) throws Exception, RemoteException;
  
  void delAll() throws Exception, RemoteException;
  
  List selectSingle(String paramString) throws Exception, RemoteException;
  
  List select() throws Exception, RemoteException;
  
  List selectGroupUser(String paramString) throws Exception, RemoteException;
  
  List selectGroupUserEmail(String paramString) throws Exception, RemoteException;
  
  Integer update(String paramString1, String paramString2, String paramString3, String[] paramArrayOfString, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11) throws Exception, RemoteException;
  
  List selectPersonUser(String paramString) throws Exception, RemoteException;
  
  List checkGroupByName(String paramString1, String paramString2) throws Exception, RemoteException;
  
  Integer saveAsGroup(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) throws Exception, RemoteException;
}
