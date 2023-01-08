package com.js.system.bean.rolemanager;

import com.js.system.vo.rolemanager.HandRoleVO;
import com.js.system.vo.rolemanager.RoleVO;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBObject;

public interface RoleEJB extends EJBObject {
  List getRoles() throws Exception, RemoteException;
  
  List getOwnerRoles(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception, RemoteException;
  
  List getRights(String[] paramArrayOfString) throws Exception, RemoteException;
  
  Integer add(RoleVO paramRoleVO, String[] paramArrayOfString) throws Exception, RemoteException;
  
  void deleteSingle(String paramString) throws Exception, RemoteException;
  
  void batchDelete(String[] paramArrayOfString) throws Exception, RemoteException;
  
  void deleteAll() throws Exception, RemoteException;
  
  Integer update(String paramString1, String[] paramArrayOfString, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) throws Exception, RemoteException;
  
  List getSingleRoleInfo(String paramString) throws Exception, RemoteException;
  
  List getSingleRoleRightId(String paramString) throws Exception, RemoteException;
  
  List getAllIdAndName(String paramString) throws Exception, RemoteException;
  
  Map getDistinctRights(String paramString) throws Exception, RemoteException;
  
  List getUserRole(String paramString) throws Exception, RemoteException;
  
  void addHandRole(HandRoleVO paramHandRoleVO) throws Exception, RemoteException;
  
  List getOwnerUserRoles(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) throws Exception, RemoteException;
  
  Integer newUpdate(String paramString1, String[] paramArrayOfString, String paramString2, RoleVO paramRoleVO) throws Exception, RemoteException;
  
  Integer save(RoleVO paramRoleVO, String[] paramArrayOfString) throws Exception, RemoteException;
  
  String newSingleDelete(String paramString) throws Exception, RemoteException;
  
  String newBatchDelete(String[] paramArrayOfString) throws Exception, RemoteException;
  
  String getCanUpdateIds(String paramString) throws Exception;
}
