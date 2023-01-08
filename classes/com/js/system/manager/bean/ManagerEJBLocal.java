package com.js.system.manager.bean;

import java.util.List;
import java.util.Map;
import javax.ejb.EJBLocalObject;

public interface ManagerEJBLocal extends EJBLocalObject {
  List getUserList(String paramString1, String paramString2, String paramString3) throws Exception;
  
  String getNameById(String paramString) throws Exception;
  
  Boolean hasRightType(String paramString1, String paramString2) throws Exception;
  
  Boolean hasRightTypeName(String paramString1, String paramString2, String paramString3) throws Exception;
  
  List getRightScope(String paramString1, String paramString2, String paramString3) throws Exception;
  
  Boolean hasRightTypeScope(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception;
  
  String getAllJuniorOrgIdByRange(String paramString) throws Exception;
  
  Map getOrgAndGroupByRange(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) throws Exception;
  
  Map getSubOrgAndUsers(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) throws Exception;
  
  String getRightWhere(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) throws Exception;
  
  String getRightFinalWhere(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) throws Exception;
  
  String getScopeWhere(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) throws Exception;
  
  String getScopeFinalWhere(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) throws Exception;
  
  String getEmployeesAccounts(String paramString);
  
  Boolean hasRight(String paramString1, String paramString2) throws Exception;
  
  Boolean hasRightTypeScope(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  List getRightScope(String paramString1, String paramString2) throws Exception;
  
  String getRightWhere(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception;
  
  String getRightFinalWhere(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception;
  
  List getAllDuty(String paramString) throws Exception;
  
  Map getSelectedGroupAndOrgAndUsers(String paramString) throws Exception;
  
  Boolean hasRights(String paramString1, String paramString2) throws Exception;
  
  String getEmpNameByEmpId(String paramString) throws Exception;
  
  String getOrgNameByOrgId(String paramString) throws Exception;
  
  String getGroupNameByGroupId(String paramString) throws Exception;
}
