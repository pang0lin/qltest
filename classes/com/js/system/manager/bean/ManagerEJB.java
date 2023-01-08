package com.js.system.manager.bean;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBObject;

public interface ManagerEJB extends EJBObject {
  List getUserList(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  String getNameById(String paramString) throws Exception, RemoteException;
  
  Boolean hasRightType(String paramString1, String paramString2) throws Exception, RemoteException;
  
  Boolean hasRightTypeName(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  List getRightScope(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  Boolean hasRightTypeScope(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception, RemoteException;
  
  String getAllJuniorOrgIdByRange(String paramString) throws Exception, RemoteException;
  
  Map getOrgAndGroupByRange(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) throws Exception, RemoteException;
  
  Map getSubOrgAndUsers(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) throws Exception, RemoteException;
  
  String getRightWhere(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) throws Exception, RemoteException;
  
  String getRightFinalWhere(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) throws Exception, RemoteException;
  
  String getScopeWhere(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) throws Exception, RemoteException;
  
  String getScopeFinalWhere(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) throws Exception, RemoteException;
  
  String getScopeFinalWhere(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) throws Exception, RemoteException;
  
  String getEmployeesAccounts(String paramString) throws RemoteException;
  
  Boolean hasRight(String paramString1, String paramString2) throws Exception, RemoteException;
  
  Boolean hasRightTypeScope(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception, RemoteException;
  
  List getRightScope(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String getRightWhere(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception, RemoteException;
  
  String getRightFinalWhere(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception, RemoteException;
  
  List getAllDuty(String paramString) throws Exception;
  
  Map getSelectedGroupAndOrgAndUsers(String paramString) throws Exception;
  
  Boolean hasRights(String paramString1, String paramString2) throws Exception;
  
  String getEmpNameByEmpId(String paramString) throws Exception;
  
  String getOrgNameByOrgId(String paramString) throws Exception;
  
  String getGroupNameByGroupId(String paramString) throws Exception;
}
