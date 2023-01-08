package com.js.system.service.rolemanager;

import com.js.oa.audit.po.AuditLog;
import com.js.oa.audit.po.RolePO;
import com.js.system.bean.rolemanager.RoleEJBBean;
import com.js.system.bean.rolemanager.RoleEJBHome;
import com.js.system.vo.rolemanager.HandRoleVO;
import com.js.system.vo.rolemanager.RoleVO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class RoleBD {
  private static Logger logger = Logger.getLogger(RoleBD.class);
  
  public List getRoles() {
    List rolesArray = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("RoleEJB", "RoleEJBLocal", RoleEJBHome.class);
      rolesArray = (List)ejbProxy.invoke("getRoles", (Object[][])null);
    } catch (Exception e) {
      logger.error("Can not get roles info:" + e.getMessage());
    } 
    return rolesArray;
  }
  
  public List getOwnerRoles(String userId, String orgId, String browseRange, String managerType, String domainId) {
    List roles = null;
    ParameterGenerator pg = new ParameterGenerator(5);
    try {
      EJBProxy ejbProxy = new EJBProxy("RoleEJB", "RoleEJBLocal", RoleEJBHome.class);
      pg.put(userId, "String");
      pg.put(orgId, "String");
      pg.put(browseRange, "String");
      pg.put(managerType, "String");
      pg.put(domainId, "String");
      roles = (List)ejbProxy.invoke("getOwnerRoles", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("Can not get roles info:" + e.getMessage());
    } 
    return roles;
  }
  
  public List getUserRoleHistory(String userId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(userId, String.class);
      EJBProxy ejbProxy = new EJBProxy("RoleEJB", "RoleEJBLocal", RoleEJBHome.class);
      list = (List)ejbProxy.invoke("getUserRoleHistory", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getUserRoleHistory information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getOwnerUserRoles(String userId, String orgId, String browseRange, String managerType, String modiUserId, String domainId) {
    List roles = null;
    ParameterGenerator pg = new ParameterGenerator(6);
    try {
      EJBProxy ejbProxy = new EJBProxy("RoleEJB", "RoleEJBLocal", RoleEJBHome.class);
      pg.put(userId, "String");
      pg.put(orgId, "String");
      pg.put(browseRange, "String");
      pg.put(managerType, "String");
      pg.put(modiUserId, "String");
      pg.put(domainId, "String");
      roles = (List)ejbProxy.invoke("getOwnerUserRoles", pg.getParameters());
    } catch (Exception e) {
      logger.error("Can not get roles info:" + e.getMessage());
    } 
    return roles;
  }
  
  public List getRights(String[] roleIds) {
    List rolesArray = null;
    EJBProxy ejbProxy = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    pg.put(roleIds, "String[]");
    try {
      ejbProxy = new EJBProxy("RoleEJB", "RoleEJBLocal", RoleEJBHome.class);
      rolesArray = (List)ejbProxy.invoke("getRights", pg.getParameters());
    } catch (Exception e) {
      logger.error("Can not get rights info:" + e.getMessage());
    } 
    return rolesArray;
  }
  
  public int add(RoleVO roleVO, String[] ids) {
    int result = 2;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(roleVO, RoleVO.class);
      pg.put(ids, String[].class);
      EJBProxy ejbProxy = new EJBProxy("RoleEJB", "RoleEJBLocal", RoleEJBHome.class);
      result = ((Integer)ejbProxy.invoke("add", pg.getParameters())).intValue();
    } catch (Exception e) {
      logger.error("error to add role information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public boolean deleteSingle(String id) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, String.class);
      EJBProxy ejbProxy = new EJBProxy("RoleEJB", "RoleEJBLocal", RoleEJBHome.class);
      ejbProxy.invoke("deleteSingle", pg.getParameters());
      result = true;
    } catch (Exception e) {
      logger.error("error to delete single role information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public boolean batchDelete(String[] batchDeleteId) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(batchDeleteId, String[].class);
      EJBProxy ejbProxy = new EJBProxy("RoleEJB", "RoleEJBLocal", RoleEJBHome.class);
      ejbProxy.invoke("batchDelete", pg.getParameters());
      result = true;
    } catch (Exception e) {
      logger.error("error to delete batch role information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public boolean deleteAll() {
    boolean result = false;
    try {
      EJBProxy ejbProxy = new EJBProxy("RoleEJB", "RoleEJBLocal", RoleEJBHome.class);
      ejbProxy.invoke("deleteAll", (Object[][])null);
      result = true;
    } catch (Exception e) {
      logger.error("error to delete all role information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public boolean deleteAll(String[] log) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(log, String[].class);
      EJBProxy ejbProxy = new EJBProxy("RoleEJB", "RoleEJBLocal", RoleEJBHome.class);
      ejbProxy.invoke("deleteAll", (Object[][])null);
      result = true;
    } catch (Exception e) {
      logger.error("error to delete all role information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public int update(String modifyId, String[] rightId, String empIds, String roleUserId, String roleUserName, String roleName, String roleDescription, String createdOrg) {
    int result = 2;
    try {
      ParameterGenerator pg = new ParameterGenerator(8);
      pg.put(modifyId, String.class);
      pg.put(rightId, String[].class);
      pg.put(empIds, String.class);
      pg.put(roleUserId, String.class);
      pg.put(roleUserName, String.class);
      pg.put(roleName, String.class);
      pg.put(roleDescription, String.class);
      pg.put(createdOrg, "String");
      EJBProxy ejbProxy = new EJBProxy("RoleEJB", "RoleEJBLocal", RoleEJBHome.class);
      result = ((Integer)ejbProxy.invoke("update", pg.getParameters())).intValue();
    } catch (Exception e) {
      logger.error("error to update role infomation :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public List getSingleRoleInfo(String roleId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(roleId, String.class);
      EJBProxy ejbProxy = new EJBProxy("RoleEJB", "RoleEJBLocal", RoleEJBHome.class);
      list = (List)ejbProxy.invoke("getSingleRoleInfo", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getSingleRoleInfo information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getSingleRoleInfo(Long logId) {
    List list = null;
    try {
      list = (new RoleEJBBean()).getSingleRoleInfo(logId);
    } catch (Exception e) {
      logger.error("error to getSingleRoleInfo information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getSingleRoleRightId(String roleId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(roleId, String.class);
      EJBProxy ejbProxy = new EJBProxy("RoleEJB", "RoleEJBLocal", RoleEJBHome.class);
      list = (List)ejbProxy.invoke("getSingleRoleRightId", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getSingleRoleRightId information :" + e.getMessage());
      System.out.println(e.getMessage());
      System.out.println(e.toString());
    } finally {}
    return list;
  }
  
  public List getSingleRoleRightId(Long logId) {
    List list = null;
    try {
      list = (new RoleEJBBean()).getSingleRoleRightId(logId);
    } catch (Exception e) {
      logger.error("error to getSingleRoleRightId information :" + e.getMessage());
      System.out.println(e.getMessage());
      System.out.println(e.toString());
    } finally {}
    return list;
  }
  
  public List getAllIdAndName(String domainId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("RoleEJB", "RoleEJBLocal", RoleEJBHome.class);
      list = (List)ejbProxy.invoke("getAllIdAndName", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAllIdAndName information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public Map getDistinctRights(String roleId) {
    Map map = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(roleId, String.class);
      EJBProxy ejbProxy = new EJBProxy("RoleEJB", "RoleEJBLocal", RoleEJBHome.class);
      map = (Map)ejbProxy.invoke("getDistinctRights", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getDistinctRight information :" + e.getMessage());
    } finally {}
    return map;
  }
  
  public List getUserRole(String userId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(userId, String.class);
      EJBProxy ejbProxy = new EJBProxy("RoleEJB", "RoleEJBLocal", RoleEJBHome.class);
      list = (List)ejbProxy.invoke("getUserRole", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getUserRole information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getAuditUserRole(String logId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(logId, String.class);
      EJBProxy ejbProxy = new EJBProxy("RoleEJB", "RoleEJBLocal", RoleEJBHome.class);
      list = (List)ejbProxy.invoke("getAuditUserRole", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getUserRole information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public boolean addHandRole(HandRoleVO handRoleVO) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(handRoleVO, HandRoleVO.class);
      EJBProxy ejbProxy = new EJBProxy("RoleEJB", "RoleEJBLocal", RoleEJBHome.class);
      ejbProxy.invoke("addHandRole", pg.getParameters());
      result = true;
    } catch (Exception e) {
      logger.error("error to addHandRole information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public int newUpdate(String modifyId, String[] rightId, String empIds, RoleVO roleVO) {
    int result = 2;
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(modifyId, String.class);
      pg.put(rightId, String[].class);
      pg.put(empIds, String.class);
      pg.put(roleVO, RoleVO.class);
      EJBProxy ejbProxy = new EJBProxy("RoleEJB", "RoleEJBLocal", RoleEJBHome.class);
      result = ((Integer)ejbProxy.invoke("newUpdate", pg.getParameters())).intValue();
    } catch (Exception e) {
      logger.error("error to update role infomation :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public int newUpdate(String modifyId, String[] rightId, String empIds, RoleVO roleVO, String[] log) {
    int result = 2;
    try {
      ParameterGenerator pg = new ParameterGenerator(5);
      pg.put(modifyId, String.class);
      pg.put(rightId, String[].class);
      pg.put(empIds, String.class);
      pg.put(roleVO, RoleVO.class);
      pg.put(log, String[].class);
      EJBProxy ejbProxy = new EJBProxy("RoleEJB", "RoleEJBLocal", RoleEJBHome.class);
      result = ((Integer)ejbProxy.invoke("newUpdate", pg.getParameters())).intValue();
    } catch (Exception e) {
      logger.error("error to update role infomation :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public int save(RoleVO roleVO, String[] ids) {
    int result = 2;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(roleVO, RoleVO.class);
      pg.put(ids, String[].class);
      EJBProxy ejbProxy = new EJBProxy("RoleEJB", "RoleEJBLocal", RoleEJBHome.class);
      result = ((Integer)ejbProxy.invoke("save", pg.getParameters())).intValue();
    } catch (Exception e) {
      logger.error("error to save role information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public int save(RoleVO roleVO, String[] ids, String[] log) {
    int result = 2;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(roleVO, RoleVO.class);
      pg.put(ids, String[].class);
      pg.put(log, String[].class);
      EJBProxy ejbProxy = new EJBProxy("RoleEJB", "RoleEJBLocal", RoleEJBHome.class);
      result = ((Integer)ejbProxy.invoke("save", pg.getParameters())).intValue();
    } catch (Exception e) {
      logger.error("error to save role information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public String newSingleDelete(String id) {
    String result = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, String.class);
      EJBProxy ejbProxy = new EJBProxy("RoleEJB", "RoleEJBLocal", RoleEJBHome.class);
      result = (String)ejbProxy.invoke("newSingleDelete", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to delete single role information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public String newSingleDelete(String id, String[] log) {
    String result = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(id, String.class);
      pg.put(log, String[].class);
      EJBProxy ejbProxy = new EJBProxy("RoleEJB", "RoleEJBLocal", RoleEJBHome.class);
      result = (String)ejbProxy.invoke("newSingleDelete", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to delete single role information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public String newBatchDelete(String[] batchDeleteId) {
    String result = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(batchDeleteId, String[].class);
      EJBProxy ejbProxy = new EJBProxy("RoleEJB", "RoleEJBLocal", RoleEJBHome.class);
      result = (String)ejbProxy.invoke("newBatchDelete", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to delete single role information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public String newBatchDelete(String[] batchDeleteId, String[] log) {
    String result = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(batchDeleteId, String[].class);
      pg.put(log, String[].class);
      EJBProxy ejbProxy = new EJBProxy("RoleEJB", "RoleEJBLocal", RoleEJBHome.class);
      result = (String)ejbProxy.invoke("newBatchDelete", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to delete single role information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public String getCanUpdateIds(String sql) {
    String result = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(sql, "String");
      EJBProxy ejbProxy = new EJBProxy("RoleEJB", "RoleEJBLocal", RoleEJBHome.class);
      result = ejbProxy.invoke("getCanUpdateIds", pg.getParameters()).toString();
    } catch (Exception e) {
      result = "";
      logger.error("error to getCanUpdateIds :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public List searchIdByName(String roleName) throws Exception {
    List reList = null;
    RoleEJBBean roleEJBBean = new RoleEJBBean();
    reList = roleEJBBean.searchIdByName(roleName);
    return reList;
  }
  
  public Long searchEmpPositionIdByName(String roleName) throws Exception {
    Long id = null;
    RoleEJBBean roleEJBBean = new RoleEJBBean();
    id = roleEJBBean.searchEmpPositionIdByName(roleName);
    return id;
  }
  
  public void auditRole(String logId, String flag, String checkEmpid, String checkEmpName) throws Exception {
    RoleEJBBean bean = new RoleEJBBean();
    bean.auditRole(logId, flag, checkEmpid, checkEmpName);
  }
  
  public RolePO getRolePO(String logId) throws Exception {
    return (new RoleEJBBean()).getRolePO(logId);
  }
  
  public String getRightId(String logId) throws Exception {
    return (new RoleEJBBean()).getRightId(logId);
  }
  
  public String getEmpId(String logId) throws Exception {
    return (new RoleEJBBean()).getEmpId(logId);
  }
  
  public AuditLog auditLog(String logId) throws Exception {
    return (new RoleEJBBean()).auditLog(logId);
  }
}
