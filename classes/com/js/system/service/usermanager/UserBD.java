package com.js.system.service.usermanager;

import com.active.e_uc.user.po.TblJilu;
import com.js.oa.audit.po.AuditEmployeePO;
import com.js.oa.audit.po.AuditLog;
import com.js.oa.hr.personnelmanager.po.EmployeeChangePO;
import com.js.system.bean.usermanager.UserEJBBean;
import com.js.system.bean.usermanager.UserEJBHome;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;

public class UserBD {
  private static Logger logger = Logger.getLogger(UserBD.class.getName());
  
  public int add(EmployeeVO employeeVO, String[] orgId, String[] rightId, String[] rightScopeType, String[] rightScopeScope, String rightScopeDsp, String[] roleId) {
    int result = 2;
    ParameterGenerator pg = new ParameterGenerator(7);
    pg.put(employeeVO, EmployeeVO.class);
    pg.put(orgId, String[].class);
    pg.put(rightId, String[].class);
    pg.put(rightScopeType, String[].class);
    pg.put(rightScopeScope, String[].class);
    pg.put(rightScopeDsp, String.class);
    pg.put(roleId, String[].class);
    try {
      EJBProxy ejbProxy = new EJBProxy("UserEJB", 
          "UserEJBLocal", UserEJBHome.class);
      result = ((Integer)ejbProxy.invoke("add", pg.getParameters())).intValue();
    } catch (Exception e) {
      logger.error("Error to add User information:" + e.getMessage());
    } finally {}
    return result;
  }
  
  public int add(EmployeeVO employeeVO, String[] orgId, String[] rightId, String[] rightScopeType, String[] rightScopeScope, String rightScopeDsp, String[] roleId, String[] log) {
    int result = 2;
    try {
      result = (new UserEJBBean()).add(employeeVO, orgId, rightId, rightScopeType, 
          rightScopeScope, rightScopeDsp, roleId, log).intValue();
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("Error to add User information:" + e.getMessage());
    } finally {}
    return result;
  }
  
  public String delete(String[] ids) {
    String result = "";
    try {
      EJBProxy ejbProxy = new EJBProxy("UserEJB", 
          "UserEJBLocal", UserEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(ids, String[].class);
      result = (String)ejbProxy.invoke("delete", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to delete User information:" + e.getMessage());
      e.printStackTrace();
    } finally {}
    return result;
  }
  
  public String delete(String[] ids, String[] log) {
    String result = "";
    try {
      EJBProxy ejbProxy = new EJBProxy("UserEJB", 
          "UserEJBLocal", UserEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(ids, String[].class);
      pg.put(log, String[].class);
      result = (String)ejbProxy.invoke("delete", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to delete User information:" + e.getMessage());
      e.printStackTrace();
    } finally {}
    return result;
  }
  
  public boolean delete(String status) {
    boolean result = false;
    try {
      EJBProxy ejbProxy = new EJBProxy("UserEJB", 
          "UserEJBLocal", UserEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(status, "String");
      if (ejbProxy.invoke("delete", pg.getParameters()) != null)
        result = true; 
    } catch (Exception e) {
      logger.error("Error to delete User information:" + e.getMessage());
      e.printStackTrace();
    } finally {}
    return result;
  }
  
  public String disable(String[] ids) {
    String result = "";
    try {
      EJBProxy ejbProxy = new EJBProxy("UserEJB", 
          "UserEJBLocal", UserEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(ids, String[].class);
      result = (String)ejbProxy.invoke("disable", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to disable User:" + e.getMessage());
      e.printStackTrace();
    } finally {}
    return result;
  }
  
  public String disable(String[] ids, String[] log) {
    String result = "";
    try {
      EJBProxy ejbProxy = new EJBProxy("UserEJB", 
          "UserEJBLocal", UserEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(ids, String[].class);
      pg.put(log, String[].class);
      result = (String)ejbProxy.invoke("disable", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to disable User:" + e.getMessage());
      e.printStackTrace();
    } finally {}
    return result;
  }
  
  public String recover(String[] ids) {
    String result = "";
    try {
      EJBProxy ejbProxy = new EJBProxy("UserEJB", 
          "UserEJBLocal", UserEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(ids, String[].class);
      result = (String)ejbProxy.invoke("recover", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to recover User:" + e.getMessage());
      e.printStackTrace();
    } finally {}
    return result;
  }
  
  public String recover(String[] ids, String[] log) {
    String result = "";
    try {
      EJBProxy ejbProxy = new EJBProxy("UserEJB", 
          "UserEJBLocal", UserEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(ids, String[].class);
      pg.put(log, String[].class);
      result = (String)ejbProxy.invoke("recover", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to recover User:" + e.getMessage());
      e.printStackTrace();
    } finally {}
    return result;
  }
  
  public boolean open(String[] ids) {
    boolean result = false;
    try {
      EJBProxy ejbProxy = new EJBProxy("UserEJB", 
          "UserEJBLocal", UserEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(ids, String[].class);
      if (ejbProxy.invoke("open", pg.getParameters()) != null)
        result = true; 
    } catch (Exception e) {
      logger.error("Error to open User:" + e.getMessage());
      e.printStackTrace();
    } finally {}
    return result;
  }
  
  public HashMap getUserRelativeInfo(Long userId, String type) {
    EJBProxy ejbProxy = null;
    HashMap result = null;
    try {
      ParameterGenerator parameterGenerator = new ParameterGenerator(
          2);
      parameterGenerator.put(userId, "Long");
      parameterGenerator.put(type, "String");
      ejbProxy = new EJBProxy("UserEJB", "UserEJBLocal", 
          UserEJBHome.class);
      result = (HashMap)ejbProxy.invoke("getUserRelativeInfo", 
          parameterGenerator.getParameters());
    } catch (Exception e) {
      logger.error("Can not get user relative info:" + e.getMessage());
    } 
    return result;
  }
  
  public int update(EmployeeVO employeeVO, String[] orgId, String[] rightId, String[] rightScopeType, String[] rightScopeScope, String rightScopeDsp, String[] roleId) {
    int result = 2;
    EJBProxy ejbProxy = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(7);
      pg.put(employeeVO, EmployeeVO.class);
      pg.put(orgId, String[].class);
      pg.put(rightId, String[].class);
      pg.put(rightScopeType, String[].class);
      pg.put(rightScopeScope, String[].class);
      pg.put(rightScopeDsp, String.class);
      pg.put(roleId, String[].class);
      ejbProxy = new EJBProxy("UserEJB", "UserEJBLocal", 
          UserEJBHome.class);
      result = ((Integer)ejbProxy.invoke("update", pg.getParameters())).intValue();
    } catch (Exception e) {
      logger.error("Can not get userName info by id:" + e.getMessage());
    } 
    return result;
  }
  
  public int update(EmployeeVO employeeVO, String[] orgId, String[] rightId, String[] rightScopeType, String[] rightScopeScope, String rightScopeDsp, String[] roleId, String[] log) {
    int result = 2;
    try {
      result = (new UserEJBBean()).update(employeeVO, orgId, rightId, rightScopeType, rightScopeScope, rightScopeDsp, roleId, log).intValue();
    } catch (Exception e) {
      logger.error("Can not get userName info by id:" + e.getMessage());
    } 
    return result;
  }
  
  public String getUserNameById(String userId) {
    EJBProxy ejbProxy = null;
    String userName = "";
    try {
      ParameterGenerator parameterGenerator = new ParameterGenerator(1);
      parameterGenerator.put(userId, "String");
      ejbProxy = new EJBProxy("UserEJB", "UserEJBLocal", 
          UserEJBHome.class);
      userName = (String)ejbProxy.invoke("getUserNameById", 
          parameterGenerator
          .getParameters());
    } catch (Exception e) {
      logger.error("Can not get userName info by id:" + e.getMessage());
    } 
    return userName;
  }
  
  public String getUserAccountsByEnglistName(String englishName) {
    EJBProxy ejbProxy = null;
    String userAcounts = "";
    try {
      ParameterGenerator parameterGenerator = new ParameterGenerator(1);
      parameterGenerator.put(englishName, "String");
      ejbProxy = new EJBProxy("UserEJB", "UserEJBLocal", 
          UserEJBHome.class);
      userAcounts = (String)ejbProxy.invoke("getUserAccountByEnglistName", 
          parameterGenerator
          .getParameters());
    } catch (Exception e) {
      logger.error("Can not get userAcounts info by EnglishName:" + e.getMessage());
    } 
    return userAcounts;
  }
  
  public List getUserInfo(Long empId) {
    List list = null;
    EJBProxy ejbProxy = null;
    String userName = "";
    try {
      ParameterGenerator parameterGenerator = new ParameterGenerator(1);
      parameterGenerator.put(empId, Long.class);
      ejbProxy = new EJBProxy("UserEJB", "UserEJBLocal", 
          UserEJBHome.class);
      list = (List)ejbProxy.invoke("getUserInfo", 
          parameterGenerator
          .getParameters());
    } catch (Exception e) {
      logger.error("Can not get userInfo by method getUserInfo:" + e.getMessage());
    } 
    return list;
  }
  
  public List getUserInfo(String logId) {
    List list = null;
    EJBProxy ejbProxy = null;
    String userName = "";
    try {
      ParameterGenerator parameterGenerator = new ParameterGenerator(1);
      parameterGenerator.put(logId, String.class);
      ejbProxy = new EJBProxy("UserEJB", "UserEJBLocal", 
          UserEJBHome.class);
      list = (List)ejbProxy.invoke("getUserInfo", 
          parameterGenerator
          .getParameters());
    } catch (Exception e) {
      logger.error("Can not get userInfo by method getUserInfo:" + e.getMessage());
    } 
    return list;
  }
  
  public List getUserIdByEmpName(String name) {
    List list = null;
    EJBProxy ejbProxy = null;
    try {
      ParameterGenerator parameterGenerator = new ParameterGenerator(1);
      parameterGenerator.put(name, String.class);
      ejbProxy = new EJBProxy("UserEJB", "UserEJBLocal", UserEJBHome.class);
      list = (List)ejbProxy.invoke("getUserIdByEmpName", parameterGenerator.getParameters());
    } catch (Exception e) {
      logger.error("Can not get userInfo by method getUserInfo:" + e.getMessage());
    } 
    return list;
  }
  
  public List getUserIdAndNameByEmpNumber(String empNumber) {
    List list = new ArrayList();
    EJBProxy ejbProxy = null;
    try {
      ParameterGenerator parameterGenerator = new ParameterGenerator(1);
      parameterGenerator.put(empNumber, String.class);
      ejbProxy = new EJBProxy("UserEJB", "UserEJBLocal", UserEJBHome.class);
      list = (List)ejbProxy.invoke("getUserIdAndNameByEmpNumber", parameterGenerator.getParameters());
    } catch (Exception e) {
      logger.error("Can not get userInfo by method getUserInfo:" + e.getMessage());
    } 
    return list;
  }
  
  public String getUserDefaultDomain(String empId) {
    String domain = null;
    EJBProxy ejbProxy = null;
    try {
      ParameterGenerator parameterGenerator = new ParameterGenerator(1);
      parameterGenerator.put(empId, String.class);
      ejbProxy = new EJBProxy("UserEJB", "UserEJBLocal", 
          UserEJBHome.class);
      domain = (String)ejbProxy.invoke("getUserDefaultDomain", 
          parameterGenerator
          .getParameters());
    } catch (Exception e) {
      System.out.println("UserBD:error:" + e);
      logger.error("Can not get userInfo by method getUserDefaultDomain:" + e.getMessage());
    } 
    return domain;
  }
  
  public List getUserDomain(String empId) {
    List domain = null;
    EJBProxy ejbProxy = null;
    try {
      ParameterGenerator parameterGenerator = new ParameterGenerator(1);
      parameterGenerator.put(empId, String.class);
      ejbProxy = new EJBProxy("UserEJB", "UserEJBLocal", 
          UserEJBHome.class);
      domain = (List)ejbProxy.invoke("getUserDomain", 
          parameterGenerator
          .getParameters());
    } catch (Exception e) {
      logger.error("Can not get userInfo by method getUserInfo:" + e.getMessage());
    } 
    return domain;
  }
  
  public String getUserAccountByIds(String idStr) {
    String result = null;
    EJBProxy ejbProxy = null;
    try {
      ParameterGenerator parameterGenerator = new ParameterGenerator(1);
      parameterGenerator.put(idStr, String.class);
      ejbProxy = new EJBProxy("UserEJB", "UserEJBLocal", 
          UserEJBHome.class);
      result = (String)ejbProxy.invoke("getUserAccountByIds", 
          parameterGenerator
          .getParameters());
    } catch (Exception e) {
      logger.error("Can not get userInfo by method getCurrentUser:" + e.getMessage());
    } 
    return result;
  }
  
  public List<EmployeeVO> getUsersByUserName(String userName) {
    List<EmployeeVO> list = new ArrayList<EmployeeVO>();
    EJBProxy ejbProxy = null;
    try {
      ParameterGenerator parameterGenerator = new ParameterGenerator(1);
      parameterGenerator.put(userName, String.class);
      ejbProxy = new EJBProxy("UserEJB", "UserEJBLocal", 
          UserEJBHome.class);
      list = (List<EmployeeVO>)ejbProxy.invoke("getUsersByUserName", 
          parameterGenerator
          .getParameters());
    } catch (Exception e) {
      logger.error("Can not get Result by method isUserNameExists:" + e.getMessage());
    } 
    return list;
  }
  
  public Integer getUserNum() {
    Integer result = Integer.valueOf("0");
    EJBProxy ejbProxy = null;
    try {
      ejbProxy = new EJBProxy("UserEJB", "UserEJBLocal", UserEJBHome.class);
      result = (Integer)ejbProxy.invoke("getUserNum", (Object[][])null);
    } catch (Exception e) {
      logger.error("Can not get userInfo by method getCurrentUser:" + e.getMessage());
    } 
    return result;
  }
  
  public boolean getRtxLogin(String userId) {
    boolean result = false;
    EJBProxy ejbProxy = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      pg.put(userId, String.class);
      ejbProxy = new EJBProxy("UserEJB", "UserEJBLocal", UserEJBHome.class);
      result = ((Boolean)ejbProxy.invoke("getRtxLogin", pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.error("Can not get getRtxLogin by method getRtxLogin:" + e.getMessage());
    } 
    return result;
  }
  
  public Integer getIsChangePwd(String userId) {
    Integer result = Integer.valueOf("0");
    EJBProxy ejbProxy = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      pg.put(userId, String.class);
      ejbProxy = new EJBProxy("UserEJB", "UserEJBLocal", UserEJBHome.class);
      result = (Integer)ejbProxy.invoke("getIsChangePwd", pg.getParameters());
    } catch (Exception e) {
      logger.error("Can not get getIsChangePwd :" + e.getMessage());
    } 
    return result;
  }
  
  public int getMailBoxSize(String userId) {
    int result = 0;
    EJBProxy ejbProxy = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      pg.put(userId, String.class);
      ejbProxy = new EJBProxy("UserEJB", "UserEJBLocal", UserEJBHome.class);
      result = ((Integer)ejbProxy.invoke("getMailBoxSize", pg.getParameters())).intValue();
    } catch (Exception e) {
      logger.error("Can not get getMailBoxSize :" + e.getMessage());
    } 
    return result;
  }
  
  public int getNetDiskSize(String userId) {
    int result = 0;
    EJBProxy ejbProxy = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      pg.put(userId, String.class);
      ejbProxy = new EJBProxy("UserEJB", "UserEJBLocal", 
          UserEJBHome.class);
      result = ((Integer)ejbProxy.invoke("getNetDiskSize", 
          pg.getParameters())).intValue();
    } catch (Exception e) {
      logger.error("Can not get getNetDiskSize :" + e.getMessage());
    } 
    return result;
  }
  
  public String getSignature(String userId) {
    String result = "";
    EJBProxy ejbProxy = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      pg.put(userId, String.class);
      ejbProxy = new EJBProxy("UserEJB", "UserEJBLocal", 
          UserEJBHome.class);
      result = (String)ejbProxy.invoke("getSignature", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("Can not get getSignature :" + e.getMessage());
    } 
    return result;
  }
  
  public void updateUserByEmpChange(EmployeeChangePO employeeVO) throws Exception {
    UserEJBBean userEJBBean = new UserEJBBean();
    userEJBBean.updateUserByEmpChange(employeeVO);
  }
  
  public String getUserName(Long empId) throws Exception {
    String userName = "";
    UserEJBBean userEJBBean = new UserEJBBean();
    userName = userEJBBean.getUserName(empId);
    return userName;
  }
  
  public EmployeeVO getEmployeeVO(Long userId) throws Exception {
    EmployeeVO employeeVO = null;
    UserEJBBean userEJBBean = new UserEJBBean();
    employeeVO = userEJBBean.getEmployeeVO(userId);
    return employeeVO;
  }
  
  public void addTblJilu(TblJilu tblJilu) throws Exception {
    UserEJBBean userEJBBean = new UserEJBBean();
    userEJBBean.addTblJilu(tblJilu);
  }
  
  public void delTblJilu(TblJilu tblJilu) throws Exception {
    UserEJBBean userEJBBean = new UserEJBBean();
    userEJBBean.delTblJilu(tblJilu);
  }
  
  public TblJilu findTblJiluByUsername(String username) throws Exception {
    TblJilu tblJilu = new TblJilu();
    UserEJBBean userEJBBean = new UserEJBBean();
    tblJilu = userEJBBean.findTblJiluByUsername(username);
    return tblJilu;
  }
  
  public long exselAdd(EmployeeVO employeeVO, Long orgId, List roleIdList) throws Exception {
    Long empId = null;
    UserEJBBean userEJBBean = new UserEJBBean();
    empId = Long.valueOf(userEJBBean.exselAdd(employeeVO, orgId, roleIdList));
    return empId.longValue();
  }
  
  public long exselAdd(EmployeeVO employeeVO, Long orgId, List roleIdList, String[] log) throws Exception {
    Long logId = null;
    UserEJBBean userEJBBean = new UserEJBBean();
    logId = Long.valueOf(userEJBBean.exselAdd(employeeVO, orgId, roleIdList, log));
    return logId.longValue();
  }
  
  public void delById(long userid) throws Exception {
    UserEJBBean userEJBBean = new UserEJBBean();
    userEJBBean.delById(userid);
  }
  
  public void delById(String logId) throws Exception {
    UserEJBBean userEJBBean = new UserEJBBean();
    userEJBBean.delById(logId);
  }
  
  public void update(EmployeeVO modiEmployeeVO) throws Exception {
    UserEJBBean userEJBBean = new UserEJBBean();
    userEJBBean.update(modiEmployeeVO);
  }
  
  public EmployeeVO getEmployeeVOByNumber(String empnumber) throws Exception {
    EmployeeVO employeeVO = null;
    UserEJBBean userEJBBean = new UserEJBBean();
    employeeVO = userEJBBean.getEmployeeVOByNumber(empnumber);
    return employeeVO;
  }
  
  public String validateUserAndPassword(String username, String password) throws Exception {
    String longStr = "N";
    UserEJBBean userEJBBean = new UserEJBBean();
    longStr = userEJBBean.validateUserAndPassword(username, password);
    return longStr;
  }
  
  public String findUserRTXloginById(Long userId) throws Exception {
    String rtxLogin = "";
    UserEJBBean userEJBBean = new UserEJBBean();
    rtxLogin = userEJBBean.findUserRTXloginById(userId);
    return rtxLogin;
  }
  
  public void activeAdd(EmployeeVO employeeVO, Long orgId) throws Exception {
    UserEJBBean userEJBBean = new UserEJBBean();
    userEJBBean.activeAdd(employeeVO, orgId);
  }
  
  public List<EmployeeVO> selectAllEmployee() throws Exception {
    List<EmployeeVO> list = new ArrayList<EmployeeVO>();
    UserEJBBean userEJBBean = new UserEJBBean();
    list = userEJBBean.selectAllEmployee();
    return list;
  }
  
  public Long getUserOrgId(Long empId) {
    Long ORG_ID = null;
    UserEJBBean userEJBBean = new UserEJBBean();
    try {
      ORG_ID = userEJBBean.getUserOrgId(empId);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return ORG_ID;
  }
  
  public List<String> selectAllUserAccounts() throws Exception {
    List<String> list = new ArrayList<String>();
    UserEJBBean userEJBBean = new UserEJBBean();
    list = userEJBBean.selectAllUserAccounts();
    return list;
  }
  
  public EmployeeVO getEmpByid(Long empId) throws Exception {
    EmployeeVO employeeVO = new EmployeeVO();
    UserEJBBean userEJBBean = new UserEJBBean();
    employeeVO = userEJBBean.getEmpByid(empId);
    return employeeVO;
  }
  
  public long findUserIdByUserAccount(String userAccount) throws Exception {
    Long userId = null;
    UserEJBBean userEJBBean = new UserEJBBean();
    userId = Long.valueOf(userEJBBean.findUserIdByUserAccount(userAccount));
    return userId.longValue();
  }
  
  public boolean updateRelationEmp(String empId) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(empId, String.class);
      EJBProxy ejbProxy = new EJBProxy("UserEJB", "UserEJBLocal", UserEJBHome.class);
      ejbProxy.invoke("updateRelationEmp", pg.getParameters());
      result = true;
    } catch (Exception e) {
      logger.error("Error to update updateRelationEmp information:" + e.getMessage());
    } finally {}
    return result;
  }
  
  public List searchOrgIdByUserId(String userId) throws Exception {
    List list = new ArrayList();
    UserEJBBean userEJBBean = new UserEJBBean();
    list = userEJBBean.searchOrgIdByUserId(userId);
    return list;
  }
  
  public List selectEmpIdByOrgIds(String orgIds) throws Exception {
    List list = new ArrayList();
    UserEJBBean userEJBBean = new UserEJBBean();
    list = userEJBBean.selectEmpIdByOrgIds(orgIds);
    return list;
  }
  
  public List selectEmpIdsAndOrgIds(String userIds) throws Exception {
    List list = new ArrayList();
    UserEJBBean userEJBBean = new UserEJBBean();
    list = userEJBBean.selectEmpIdsAndOrgIds(userIds);
    return list;
  }
  
  public List selectEmpForRecord() throws Exception {
    List list = new ArrayList();
    UserEJBBean userEJBBean = new UserEJBBean();
    list = userEJBBean.selectEmpForRecord();
    return list;
  }
  
  public List selectMyUnderling(String userId) throws Exception {
    List list = new ArrayList();
    UserEJBBean userEJBBean = new UserEJBBean();
    list = userEJBBean.selectMyUnderling(userId);
    return list;
  }
  
  public Integer canImportUserNum(String domainId) throws Exception {
    List list = new ArrayList();
    UserEJBBean userEJBBean = new UserEJBBean();
    return userEJBBean.canImportUserNum(domainId);
  }
  
  public void auditEmp(String logId, String flag, String checkEmpid, String checkEmpName) throws Exception {
    UserEJBBean userEJBBean = new UserEJBBean();
    userEJBBean.auditEmp(logId, flag, checkEmpid, checkEmpName);
  }
  
  public AuditLog auditLog(String logId) throws Exception {
    UserEJBBean userEJBBean = new UserEJBBean();
    return userEJBBean.auditLog(logId);
  }
  
  public AuditEmployeePO getEmployeePO(String logId) throws Exception {
    UserEJBBean userEJBBean = new UserEJBBean();
    return userEJBBean.getEmployeePO(logId);
  }
  
  public EmployeeVO getEmployeeByUserName(String userName) {
    try {
      return (new UserEJBBean()).getEmployeeByUserName(userName);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
  }
}
