package com.js.system.bean.rolemanager;

import com.js.system.vo.rolemanager.HandRoleVO;
import com.js.system.vo.rolemanager.RoleVO;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBLocalObject;

public interface RoleEJBLocal extends EJBLocalObject {
  List getRoles() throws Exception;
  
  List getOwnerRoles(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception;
  
  List getRights(String[] paramArrayOfString) throws Exception;
  
  Integer add(RoleVO paramRoleVO, String[] paramArrayOfString) throws Exception;
  
  void deleteSingle(String paramString) throws Exception;
  
  void batchDelete(String[] paramArrayOfString) throws Exception;
  
  void deleteAll() throws Exception;
  
  Integer update(String paramString1, String[] paramArrayOfString, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) throws Exception;
  
  List getSingleRoleInfo(String paramString) throws Exception;
  
  List getSingleRoleRightId(String paramString) throws Exception;
  
  Integer save(RoleVO paramRoleVO, String[] paramArrayOfString) throws Exception;
  
  List getAllIdAndName(String paramString) throws Exception;
  
  Map getDistinctRights(String paramString) throws Exception;
  
  List getUserRole(String paramString) throws Exception;
  
  void addHandRole(HandRoleVO paramHandRoleVO) throws Exception;
  
  List getOwnerUserRoles(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) throws Exception;
  
  Integer newUpdate(String paramString1, String[] paramArrayOfString, String paramString2, RoleVO paramRoleVO) throws Exception;
  
  String newSingleDelete(String paramString) throws Exception;
  
  String newBatchDelete(String[] paramArrayOfString) throws Exception;
  
  String getCanUpdateIds(String paramString) throws Exception;
}
