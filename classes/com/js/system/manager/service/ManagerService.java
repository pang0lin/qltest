package com.js.system.manager.service;

import com.js.oa.crm.bean.CstEJBBean;
import com.js.system.bean.organizationmanager.OrganizationEJBBean;
import com.js.system.manager.bean.ManagerEJBBean;
import com.js.system.manager.bean.ManagerEJBHome;
import com.js.system.service.organizationmanager.OrganizationBD;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class ManagerService {
  private static Logger logger = Logger.getLogger(ManagerService.class
      .getName());
  
  ManagerEJBBean mbean = new ManagerEJBBean();
  
  public List getSubOrgs(String orgId, String domainId) {
    List subOrg = null;
    OrganizationEJBBean bean = new OrganizationEJBBean();
    try {
      subOrg = bean.getSubOrgs(Long.valueOf(orgId), domainId);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return subOrg;
  }
  
  public List getOrgList(String domainId, String range, String sele) {
    List orgList = null;
    OrganizationEJBBean bean = new OrganizationEJBBean();
    try {
      orgList = bean.getOrgList(domainId, range, sele);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return orgList;
  }
  
  public List getOrgCurrentList(String domainId, String orgID) {
    List orgList = null;
    OrganizationEJBBean bean = new OrganizationEJBBean();
    try {
      orgList = bean.getOrgCurrentList(domainId, orgID);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return orgList;
  }
  
  public List getChildOrgList(String domainId, String range, String parentId) {
    List orgList = null;
    OrganizationEJBBean bean = new OrganizationEJBBean();
    try {
      orgList = bean.getChildOrgList(domainId, range, parentId);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return orgList;
  }
  
  public String getOrgIdString(String domainId, String parentOrgId, String orgId) {
    OrganizationEJBBean bean = new OrganizationEJBBean();
    String returnStr = "null";
    try {
      returnStr = bean.getOrgIdString(domainId, parentOrgId, orgId);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return returnStr;
  }
  
  public List getRootDept(String domainId) {
    List orgList = null;
    OrganizationEJBBean bean = new OrganizationEJBBean();
    try {
      orgList = bean.getRootDept(domainId);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return orgList;
  }
  
  public List getPrivateList(String userId, String domainId) {
    List orgList = null;
    OrganizationEJBBean bean = new OrganizationEJBBean();
    try {
      orgList = bean.getPrivateList(userId, domainId);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return orgList;
  }
  
  public List getPublicList(String userId, String domainId) {
    List orgList = null;
    OrganizationEJBBean bean = new OrganizationEJBBean();
    try {
      orgList = bean.getPublicList(userId, domainId);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return orgList;
  }
  
  public List getGroupList(String userId, String domainId, String orgId, String groupRange) {
    List orgList = null;
    OrganizationEJBBean bean = new OrganizationEJBBean();
    try {
      orgList = bean.getGroupList(userId, domainId, orgId, groupRange);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return orgList;
  }
  
  public List getPrivateGroupList(String userId, String domainId, String orgId, String groupRange) {
    List orgList = null;
    OrganizationEJBBean bean = new OrganizationEJBBean();
    try {
      orgList = bean.getPrivateGroupList(userId, domainId, orgId, 
          groupRange);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return orgList;
  }
  
  public List getGroupListByRange(String userId, String domainId, String range, String orgId) {
    List orgList = null;
    OrganizationEJBBean bean = new OrganizationEJBBean();
    try {
      orgList = bean.getGroupListByRange(userId, domainId, range, orgId);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return orgList;
  }
  
  public List getZWList(String domainId) {
    return getZWList(domainId, "");
  }
  
  public List getZWList(String domainId, String corpId) {
    List gwList = null;
    OrganizationEJBBean bean = new OrganizationEJBBean();
    try {
      gwList = bean.getZWList(domainId, corpId);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return gwList;
  }
  
  public List getGWList(String domainId) {
    return getGWList(domainId, "");
  }
  
  public List getGWList(String domainId, String corpId) {
    List gwList = null;
    OrganizationEJBBean bean = new OrganizationEJBBean();
    try {
      gwList = bean.getGWList(domainId, corpId);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return gwList;
  }
  
  public List getUserInGroupList(String groupId, String condition, String text) {
    List orgUserList = null;
    OrganizationEJBBean bean = new OrganizationEJBBean();
    try {
      orgUserList = bean.getUserInGroupList(groupId, condition, text);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return orgUserList;
  }
  
  public List getUserInGZWList(String id, String condition, String text, String type) {
    List orgUserList = null;
    OrganizationEJBBean bean = new OrganizationEJBBean();
    try {
      orgUserList = bean.getUserInGZWList(id, condition, text, type);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return orgUserList;
  }
  
  public List getUserInPrivateList(String classId, String condition, String text, String type, String userId, String orgId) {
    List orgUserList = null;
    OrganizationEJBBean bean = new OrganizationEJBBean();
    try {
      orgUserList = bean.getUserInPPList(classId, condition, text, type, 
          userId, orgId);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return orgUserList;
  }
  
  public List getUserInOrgList(String orgId, String condition, String text, String typeTemp) {
    List orgUserList = null;
    OrganizationEJBBean bean = new OrganizationEJBBean();
    try {
      orgUserList = bean.getUserInOrgList(orgId, condition, text, 
          typeTemp);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return orgUserList;
  }
  
  public List getUserInRange(String range, String condition) {
    List orgUserList = null;
    OrganizationEJBBean bean = new OrganizationEJBBean();
    try {
      orgUserList = bean.getUserInRange(range, condition);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return orgUserList;
  }
  
  public List getRootDeptList(String domainId, String range) {
    List orgList = null;
    OrganizationEJBBean bean = new OrganizationEJBBean();
    try {
      orgList = bean.getRootDeptList(domainId, range);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return orgList;
  }
  
  public List getRootOrg(String domainId) {
    List orgList = null;
    OrganizationEJBBean bean = new OrganizationEJBBean();
    try {
      orgList = bean.getRootOrgList(domainId);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return orgList;
  }
  
  public List getQueryUserList(String condition, String text, String type, String groupIdString, String range, String option) {
    List orgUserList = null;
    OrganizationEJBBean bean = new OrganizationEJBBean();
    try {
      orgUserList = bean.getQueryUserList(condition, text, type, 
          groupIdString, range, option);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return orgUserList;
  }
  
  public List getQueryDownDownEmp(List list, String text) {
    List orgUserList = null;
    OrganizationEJBBean bean = new OrganizationEJBBean();
    try {
      orgUserList = bean.getQueryDownDownEmp(list, text);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return orgUserList;
  }
  
  public List getQueryCst(String name) {
    List orgUserList = null;
    CstEJBBean bean = new CstEJBBean();
    try {
      orgUserList = bean.getQueryCstByName(name);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return orgUserList;
  }
  
  public List getQueryCstSell(String name) {
    List orgUserList = null;
    CstEJBBean bean = new CstEJBBean();
    try {
      orgUserList = bean.getQueryCstSellByName(name);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return orgUserList;
  }
  
  public List getParentOrgList(String domainId) {
    List orgList = null;
    OrganizationEJBBean bean = new OrganizationEJBBean();
    try {
      orgList = bean.getParentOrgList(domainId);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return orgList;
  }
  
  public List getUserList(String para, String vo, String where) {
    List userList = null;
    try {
      userList = this.mbean.getUserList(para, vo, where);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return userList;
  }
  
  public String getNameBYId(String idString) {
    String result = "";
    try {
      result = this.mbean.getNameById(idString);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return result;
  }
  
  public boolean hasRightType(String userId, String rightType) {
    Boolean result = new Boolean(false);
    try {
      result = this.mbean.hasRightType(userId, rightType);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return result.booleanValue();
  }
  
  public boolean hasRightTypeName(String userId, String rightType, String rightName) {
    Boolean result = new Boolean(false);
    try {
      result = this.mbean.hasRightTypeName(userId, rightType, rightName);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return result.booleanValue();
  }
  
  public List getRightScope(String userId, String rightType, String rightName) {
    List list = null;
    try {
      list = this.mbean.getRightScope(userId, rightType, rightName);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return list;
  }
  
  public boolean hasRightTypeScope(String userId, String orgId, String rightType, String rightName, String channelType) {
    Boolean result = new Boolean(false);
    try {
      result = this.mbean.hasRightTypeScope(userId, orgId, rightType, 
          rightName, channelType);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return result.booleanValue();
  }
  
  public String getAllJuniorOrgIdByRange(String range) {
    String result = "";
    try {
      result = this.mbean.getAllJuniorOrgIdByRange(range);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return result;
  }
  
  public List getValidOrgsByRange(String range, String domainId) {
    return (new OrganizationBD()).getValidOrgsByRange(range, domainId);
  }
  
  public Map getOrgAndGroupByRange(String range, String group, String orgIdString, String empId, String currentOrg, String domainId) {
    Map map = null;
    try {
      map = this.mbean.getOrgAndGroupByRange(range, group, orgIdString, empId, 
          currentOrg, domainId);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return map;
  }
  
  public Map getSubOrgAndUsers(String orgId, String currentOrg, String domainId, String rootCorpId, String corpId, String departId, String otherDepart) {
    Map map = null;
    try {
      map = this.mbean.getSubOrgAndUsers(orgId, currentOrg, domainId, 
          rootCorpId, corpId, departId, otherDepart);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return map;
  }
  
  public Map getSubOrgAndUsersEmail(String orgId, String currentOrg, String domainId, String rootCorpId, String corpId, String departId, String otherDepart) {
    Map map = null;
    try {
      map = this.mbean.getSubOrgAndUsersEmail(orgId, currentOrg, domainId, 
          rootCorpId, corpId, departId, otherDepart);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return map;
  }
  
  public Map getSelectedGroupAndOrgAndUsers(String selectedId) {
    Map map = null;
    try {
      map = this.mbean.getSelectedGroupAndOrgAndUsers(selectedId);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return map;
  }
  
  public String getRightWhere(String userId, String orgId, String orgIdString, String rightType, String rightName, String fieldOrg, String fieldEmp) {
    String where = "";
    try {
      where = this.mbean.getRightWhere(userId, orgId, orgIdString, rightType, 
          rightName, fieldOrg, fieldEmp);
    } catch (Exception e) {
      logger.error("Can not get getRightWhere's info:" + e.getMessage());
      e.printStackTrace();
    } 
    return where;
  }
  
  public String getRightFinalWhere(String userId, String orgId, String orgIdString, String rightType, String rightName, String fieldOrg, String fieldEmp) {
    String where = "";
    try {
      where = this.mbean.getRightFinalWhere(userId, orgId, orgIdString, 
          rightType, rightName, fieldOrg, fieldEmp);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return where;
  }
  
  public String getScopeWhere(String userId, String orgId, String orgIdString, String fieldUser, String fieldOrg, String fieldGroup, String fieldCreatedEmp) {
    String where = "";
    try {
      where = this.mbean.getScopeWhere(userId, orgId, orgIdString, fieldUser, 
          fieldOrg, fieldGroup, fieldCreatedEmp);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return where;
  }
  
  public String getScopeFinalWhere(String userId, String orgId, String orgIdString, String fieldUser, String fieldOrg, String fieldGroup, String fieldName) {
    String where = "";
    try {
      where = this.mbean.getScopeFinalWhere(userId, orgId, orgIdString, 
          fieldUser, fieldOrg, fieldGroup, fieldName);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return where;
  }
  
  public String getScopeFinalWhere(String userId, String orgId, String orgIdString, String fieldUser, String fieldOrg, String fieldGroup) {
    String where = "";
    try {
      where = this.mbean.getScopeFinalWhere(userId, orgId, orgIdString, 
          fieldUser, fieldOrg, fieldGroup);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return where;
  }
  
  public String getEmployeesAccounts(String empIds) {
    String result = "";
    try {
      result = this.mbean.getEmployeesAccounts(empIds);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return result;
  }
  
  public boolean hasRight(String userId, String rightCode) {
    Boolean result = new Boolean(false);
    try {
      result = this.mbean.hasRight(userId, rightCode);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return result.booleanValue();
  }
  
  public boolean hasRights(String userId, String rightCodes) {
    Boolean result = new Boolean(false);
    try {
      result = this.mbean.hasRights(userId, rightCodes);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return result.booleanValue();
  }
  
  public boolean hasRightTypeScope(String userId, String orgId, String rightCode, String channelType) {
    Boolean result = new Boolean(false);
    try {
      result = this.mbean.hasRightTypeScope(userId, orgId, rightCode, 
          channelType);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return result.booleanValue();
  }
  
  public List getRightScope(String userId, String rightCode) {
    List list = null;
    try {
      list = this.mbean.getRightScope(userId, rightCode);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return list;
  }
  
  public String getRightWhere(String userId, String orgId, String rightCode, String fieldOrg, String fieldEmp) {
    String where = "";
    try {
      where = this.mbean.getRightWhere(userId, orgId, rightCode, fieldOrg, 
          fieldEmp);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return where;
  }
  
  public String getRightFinalWhere(String userId, String orgId, String rightCode, String fieldOrg, String fieldEmp) {
    String where = "";
    try {
      where = this.mbean.getRightFinalWhere(userId, orgId, rightCode, 
          fieldOrg, fieldEmp);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return where;
  }
  
  public Map getLeft(String range, String group) {
    Map<Object, Object> result = new HashMap<Object, Object>();
    return result;
  }
  
  public List getAllDuty(String domainId) {
    List result = new ArrayList();
    try {
      result = this.mbean.getAllDuty(domainId);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return result;
  }
  
  public String getEmpNameByEmpId(String strin) throws Exception {
    String str = null;
    ParameterGenerator p = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("ManagerEJB", "ManagerEJBLocal", 
          ManagerEJBHome.class);
      p.put(strin, String.class);
      str = (String)ejbProxy.invoke("getEmpNameByEmpId", 
          p.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return str;
  }
  
  public String getOrgNameByOrgId(String strin) throws Exception {
    String str = null;
    ParameterGenerator p = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("ManagerEJB", "ManagerEJBLocal", 
          ManagerEJBHome.class);
      p.put(strin, String.class);
      str = (String)ejbProxy.invoke("getOrgNameByOrgId", 
          p.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return str;
  }
  
  public String getGroupNameByGroupId(String strin) throws Exception {
    String str = null;
    ParameterGenerator p = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("ManagerEJB", "ManagerEJBLocal", 
          ManagerEJBHome.class);
      p.put(strin, String.class);
      str = (String)ejbProxy.invoke("getGroupNameByGroupId", 
          p.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return str;
  }
  
  public List getUsersByRange(String range) {
    List userList = null;
    OrganizationEJBBean bean = new OrganizationEJBBean();
    try {
      userList = bean.getUsersByRange(range);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
      e.printStackTrace();
    } 
    return userList;
  }
}
