package com.js.system.service.organizationmanager;

import com.js.system.bean.organizationmanager.OrganizationEJBBean;
import com.js.system.bean.organizationmanager.OrganizationEJBHome;
import com.js.system.vo.organizationmanager.DomainVO;
import com.js.system.vo.organizationmanager.OrganizationVO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.sf.hibernate.HibernateException;
import org.apache.log4j.Logger;

public class OrganizationBD {
  private static Logger logger = Logger.getLogger(OrganizationBD.class
      .getName());
  
  public boolean add(OrganizationVO organizationVO, String currentOrderCode, String parentIdString, Integer sort) {
    boolean addResult = false;
    ParameterGenerator pg = new ParameterGenerator(4);
    try {
      EJBProxy ejbProxy = new EJBProxy("OrganizationEJB", 
          "OrganizationEJBLocal", OrganizationEJBHome.class);
      pg.put(organizationVO, OrganizationVO.class);
      pg.put(currentOrderCode, "String");
      pg.put(parentIdString, "String");
      pg.put(sort, Integer.class);
      ejbProxy.invoke("add", pg.getParameters());
      addResult = true;
    } catch (Exception e) {
      logger.error("Error to add Organization information:" + 
          e.getMessage());
    } 
    return addResult;
  }
  
  public boolean add(OrganizationVO organizationVO, String currentOrderCode, String parentIdString, Integer sort, String[] log) {
    boolean addResult = false;
    ParameterGenerator pg = new ParameterGenerator(4);
    try {
      (new OrganizationEJBBean()).add(organizationVO, currentOrderCode, parentIdString, sort, log);
      addResult = true;
    } catch (Exception e) {
      logger.error("Error to add Organization information:" + 
          e.getMessage());
    } finally {}
    return addResult;
  }
  
  public String delete(long key, String type) {
    String result = "";
    Object[] parameters = new Object[2];
    try {
      EJBProxy ejbProxy = new EJBProxy("OrganizationEJB", "OrganizationEJBLocal", OrganizationEJBHome.class);
      parameters[0] = new Long(key);
      parameters[1] = new String(type);
      result = (String)ejbProxy.invoke("delete", parameters);
    } catch (Exception e) {
      logger.error("Error to delete Organization information:" + 
          e.getMessage());
    } finally {}
    return result;
  }
  
  public String delete(long key, String type, String[] log) {
    String result = "";
    Object[] parameters = new Object[2];
    try {
      (new OrganizationEJBBean()).delete(Long.valueOf(key), type, log);
    } catch (Exception e) {
      logger.error("Error to delete Organization information:" + 
          e.getMessage());
    } finally {}
    return result;
  }
  
  public String reDept(long key) {
    String name = "";
    try {
      OrganizationEJBBean organizationEJBBean = new OrganizationEJBBean();
      name = organizationEJBBean.reDept(Long.valueOf(key));
    } catch (Exception e) {
      logger.error("Error to delete Organization information:" + e.getMessage());
    } 
    return name;
  }
  
  public String reDept(long key, String[] log) {
    String name = "";
    try {
      OrganizationEJBBean organizationEJBBean = new OrganizationEJBBean();
      name = organizationEJBBean.reDept(Long.valueOf(key), log);
    } catch (Exception e) {
      logger.error("Error to delete Organization information:" + e.getMessage());
    } 
    return name;
  }
  
  public String findOrgSerial(long key) {
    String result = "";
    try {
      OrganizationEJBBean organizationEJBBean = new OrganizationEJBBean();
      result = organizationEJBBean.findOrgSerial(key);
    } catch (Exception e) {
      logger.error("Error to delete Organization information:" + 
          e.getMessage());
    } 
    return result;
  }
  
  public String findStationName(String id) {
    String result = "";
    try {
      OrganizationEJBBean organizationEJBBean = new OrganizationEJBBean();
      result = organizationEJBBean.findStationName(id);
    } catch (Exception e) {
      logger.error("Error to delete Organization information:" + 
          e.getMessage());
    } 
    return result;
  }
  
  public boolean update(OrganizationVO organizationVO, String currentOrderCode, String parentIdString, Integer sort, String hasChanged) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(5);
      pg.put(organizationVO, OrganizationVO.class);
      pg.put(currentOrderCode, "String");
      pg.put(parentIdString, "String");
      pg.put(sort, Integer.class);
      pg.put(hasChanged, "String");
      EJBProxy ejbProxy = new EJBProxy("OrganizationEJB", 
          "OrganizationEJBLocal", OrganizationEJBHome.class);
      ejbProxy.invoke("update", pg.getParameters());
      result = true;
    } catch (Exception e) {
      logger.error("Error to update Organization information:" + 
          e.getMessage());
    } finally {}
    return result;
  }
  
  public boolean update(OrganizationVO organizationVO, String currentOrderCode, String parentIdString, Integer sort, String hasChanged, String[] log) {
    boolean result = false;
    try {
      (new OrganizationEJBBean()).update(organizationVO, currentOrderCode, parentIdString, sort, hasChanged, log);
      result = true;
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("Error to update Organization information:" + 
          e.getMessage());
    } finally {}
    return result;
  }
  
  public boolean updateRootDept(OrganizationVO organizationVO) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(organizationVO, OrganizationVO.class);
      EJBProxy ejbProxy = new EJBProxy("OrganizationEJB", 
          "OrganizationEJBLocal", OrganizationEJBHome.class);
      ejbProxy.invoke("updateRootDept", pg.getParameters());
      result = true;
    } catch (Exception e) {
      logger.error("Error to update Organization information:" + 
          e.getMessage());
    } finally {}
    return result;
  }
  
  public boolean updateRootDept(OrganizationVO organizationVO, String[] log) {
    boolean result = false;
    try {
      (new OrganizationEJBBean()).updateRootDept(organizationVO, log);
      result = true;
    } catch (Exception e) {
      logger.error("Error to update Organization information:" + 
          e.getMessage());
    } finally {}
    return result;
  }
  
  public boolean updateRelationDept(String orgId, String empId, String empName, String type) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(orgId, String.class);
      pg.put(empId, String.class);
      pg.put(empName, String.class);
      pg.put(type, String.class);
      EJBProxy ejbProxy = new EJBProxy("OrganizationEJB", "OrganizationEJBLocal", OrganizationEJBHome.class);
      ejbProxy.invoke("updateRelationDept", pg.getParameters());
      result = true;
    } catch (Exception e) {
      logger.error("Error to update updateRelationDept information:" + e.getMessage());
    } finally {}
    return result;
  }
  
  public List getAllOrgs() {
    List allOrgArray = null;
    EJBProxy ejbProxy = null;
    try {
      ejbProxy = new EJBProxy("OrganizationEJB", 
          "OrganizationEJBLocal", 
          OrganizationEJBHome.class);
      allOrgArray = (List)ejbProxy.invoke("getAllOrgs", (Object[][])null);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return allOrgArray;
  }
  
  public List getValidOrgs() {
    List validOrgs = null;
    EJBProxy ejbProxy = null;
    try {
      ejbProxy = new EJBProxy("OrganizationEJB", 
          "OrganizationEJBLocal", 
          OrganizationEJBHome.class);
      validOrgs = (List)ejbProxy.invoke("getValidOrgs", (Object[][])null);
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return validOrgs;
  }
  
  public List getSubOrgs(String orgId) {
    List subOrg = null;
    ParameterGenerator parameterGenerator = new ParameterGenerator(1);
    parameterGenerator.put(new Long(orgId), "Long");
    EJBProxy ejbProxy = null;
    try {
      ejbProxy = new EJBProxy("OrganizationEJB", 
          "OrganizationEJBLocal", 
          OrganizationEJBHome.class);
      subOrg = (List)ejbProxy.invoke("getSubOrgs", 
          parameterGenerator.getParameters());
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return subOrg;
  }
  
  public Set getSubUsers(String orgId) {
    Set subUser = null;
    ParameterGenerator parameterGenerator = new ParameterGenerator(1);
    parameterGenerator.put(new Long(orgId), "Long");
    EJBProxy ejbProxy = null;
    try {
      ejbProxy = new EJBProxy("OrganizationEJB", 
          "OrganizationEJBLocal", 
          OrganizationEJBHome.class);
      subUser = (Set)ejbProxy.invoke("getSubUsers", 
          parameterGenerator.getParameters());
    } catch (Exception e) {
      logger.error("Can not get organization's info:" + e.getMessage());
    } 
    return subUser;
  }
  
  public List getHasChannel() {
    List list = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("OrganizationEJB", 
          "OrganizationEJBLocal", OrganizationEJBHome.class);
      list = (List)ejbProxy.invoke("getHasChannel", (Object[][])null);
    } catch (Exception e) {
      logger.error("errot to getHasChannel information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public String getOrgName(String orgId) {
    String orgFullName = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(orgId, String.class);
      EJBProxy ejbProxy = new EJBProxy("OrganizationEJB", 
          "OrganizationEJBLocal", OrganizationEJBHome.class);
      orgFullName = (String)ejbProxy.invoke("getOrgName", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getOrgName information :" + e.getMessage());
    } finally {}
    return orgFullName;
  }
  
  public List getSons(String orgId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(orgId, String.class);
      EJBProxy ejbProxy = new EJBProxy("OrganizationEJB", "OrganizationEJBLocal", OrganizationEJBHome.class);
      list = (List)ejbProxy.invoke("getSons", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getSons information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getSuperior(String orgId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(orgId, String.class);
      EJBProxy ejbProxy = new EJBProxy("OrganizationEJB", "OrganizationEJBLocal", OrganizationEJBHome.class);
      list = (List)ejbProxy.invoke("getSuperior", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getSuperior information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getNameAndId(String orgIds) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(orgIds, String.class);
      EJBProxy ejbProxy = new EJBProxy("OrganizationEJB", "OrganizationEJBLocal", OrganizationEJBHome.class);
      list = (List)ejbProxy.invoke("getNameAndId", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getOrgNameAndId :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getValidOrgsByRange(String orgIds, String domainId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(orgIds, String.class);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("OrganizationEJB", "OrganizationEJBLocal", OrganizationEJBHome.class);
      list = (List)ejbProxy.invoke("getValidOrgsByRange", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getValidOrgsByRange :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getSimpleOrg(String userId, String domainId) {
    List list = null;
    ParameterGenerator pg = new ParameterGenerator(2);
    pg.put(userId, "String");
    pg.put(domainId, "String");
    try {
      EJBProxy ejbProxy = new EJBProxy("OrganizationEJB", "OrganizationEJBLocal", OrganizationEJBHome.class);
      list = (List)ejbProxy.invoke("getSimpleOrg", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getSimpleOrg information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getAllChannelList(String userId) {
    List list = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    pg.put(userId, "String");
    try {
      EJBProxy ejbProxy = new EJBProxy("OrganizationEJB", "OrganizationEJBLocal", OrganizationEJBHome.class);
      list = (List)ejbProxy.invoke("getAllChannelList", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getChannelOrg information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public Integer checkOrganizationSerial(String orgId, String orgSerial, String flag) {
    Integer result = null;
    ParameterGenerator pg = new ParameterGenerator(3);
    pg.put(orgId, "String");
    pg.put(orgSerial, "String");
    pg.put(flag, "String");
    try {
      EJBProxy ejbProxy = new EJBProxy("OrganizationEJB", "OrganizationEJBLocal", OrganizationEJBHome.class);
      result = (Integer)ejbProxy.invoke("checkOrganizationSerial", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getChannelOrg information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public boolean isSubOrg(String orgId, String parentOrgId) {
    return (new OrganizationEJBBean()).isSubOrg(orgId, parentOrgId);
  }
  
  public Integer checkOrgSerialAndOrgParentorgid(String orgParentorgid, String orgSerial) {
    Integer result = null;
    ParameterGenerator pg = new ParameterGenerator(2);
    pg.put(orgParentorgid, "String");
    pg.put(orgSerial, "String");
    try {
      EJBProxy ejbProxy = new EJBProxy("OrganizationEJB", "OrganizationEJBLocal", OrganizationEJBHome.class);
      result = (Integer)ejbProxy.invoke("checkOrgSerialAndOrgParentorgid", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getChannelOrg information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public Integer checkOrganizationName(String orgId, String orgName) {
    Integer result = null;
    ParameterGenerator pg = new ParameterGenerator(2);
    pg.put(orgId, "String");
    pg.put(orgName, "String");
    try {
      EJBProxy ejbProxy = new EJBProxy("OrganizationEJB", "OrganizationEJBLocal", OrganizationEJBHome.class);
      result = (Integer)ejbProxy.invoke("checkOrganizationName", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getChannelOrg information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public OrganizationVO getOrgByOrgId(String orgId) throws Exception {
    OrganizationVO organizationVO = null;
    OrganizationEJBBean organizationEJBBean = new OrganizationEJBBean();
    organizationVO = organizationEJBBean.getOrgByOrgId(orgId);
    return organizationVO;
  }
  
  public List getParentOrgListTemp(String orgId, String demainid, String range) throws Exception {
    OrganizationEJBBean organizationEJBBean = new OrganizationEJBBean();
    List list = organizationEJBBean.getParentOrgListTemp(orgId, demainid, range);
    return list;
  }
  
  public DomainVO loadDomain(String domainId) {
    DomainVO result = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    pg.put(domainId, "String");
    try {
      EJBProxy ejbProxy = new EJBProxy("OrganizationEJB", "OrganizationEJBLocal", OrganizationEJBHome.class);
      result = (DomainVO)ejbProxy.invoke("loadDomain", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to loadDomain information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public String findOrgIdString(long key) throws Exception {
    String orgIdString = "";
    OrganizationEJBBean organizationEJBBean = new OrganizationEJBBean();
    orgIdString = organizationEJBBean.findOrgIdString(key);
    return orgIdString;
  }
  
  public long exselAdd(OrganizationVO organizationVO, String currentOrderCode, String parentIdString, Integer sort) throws HibernateException {
    Long orgId = null;
    OrganizationEJBBean organizationEJBBean = new OrganizationEJBBean();
    orgId = Long.valueOf(organizationEJBBean.activeAdd(organizationVO, currentOrderCode, parentIdString, 
          sort));
    return orgId.longValue();
  }
  
  public Long exselAdd(OrganizationVO organizationVO, String currentOrderCode, String parentIdString, Integer sort, String[] log) throws HibernateException {
    Long logId = null;
    OrganizationEJBBean organizationEJBBean = new OrganizationEJBBean();
    logId = organizationEJBBean.activeAdd(organizationVO, currentOrderCode, parentIdString, 
        sort, log);
    return logId;
  }
  
  public void delById(long orgid) throws Exception {
    OrganizationEJBBean organizationEJBBean = new OrganizationEJBBean();
    organizationEJBBean.delById(orgid);
  }
  
  public void delByLogId(long logId) throws Exception {
    OrganizationEJBBean organizationEJBBean = new OrganizationEJBBean();
    organizationEJBBean.delByLogId(logId);
  }
  
  public String activeAdd(OrganizationVO organizationVO, String currentOrderCode, String parentIdString, Integer sort) throws HibernateException {
    OrganizationEJBBean organizationEJBBean = new OrganizationEJBBean();
    organizationEJBBean.activeAdd(organizationVO, currentOrderCode, parentIdString, 
        sort);
    return organizationVO.getOrgName();
  }
  
  public OrganizationVO getOrgBySerial(String serial) throws Exception {
    OrganizationVO organizationVO = null;
    OrganizationEJBBean organizationEJBBean = new OrganizationEJBBean();
    organizationVO = organizationEJBBean.getOrgBySerial(serial);
    return organizationVO;
  }
  
  public String getMaxOrgOrderCode(long orgParentOrgId) throws Exception {
    String OrgOrderCode = "";
    OrganizationEJBBean organizationEJBBean = new OrganizationEJBBean();
    OrgOrderCode = organizationEJBBean.getMaxOrgOrderCode(orgParentOrgId);
    return OrgOrderCode;
  }
  
  public void delAllOrganization() throws Exception {
    OrganizationEJBBean organizationEJBBean = new OrganizationEJBBean();
    organizationEJBBean.delAllOrganization();
  }
  
  public List<OrganizationVO> selectAllOrganization() throws Exception {
    List<OrganizationVO> list = new ArrayList<OrganizationVO>();
    OrganizationEJBBean organizationEJBBean = new OrganizationEJBBean();
    list = organizationEJBBean.selectAllOrganization();
    return list;
  }
  
  public List<String> selectAllOrgSerial() throws Exception {
    List<String> list = new ArrayList<String>();
    OrganizationEJBBean organizationEJBBean = new OrganizationEJBBean();
    list = organizationEJBBean.selectAllOrgSerial();
    return list;
  }
  
  public List<OrganizationVO> selectAllParentOrganization() throws Exception {
    List<OrganizationVO> list = new ArrayList<OrganizationVO>();
    OrganizationEJBBean organizationEJBBean = new OrganizationEJBBean();
    list = organizationEJBBean.selectAllParentOrganization();
    return list;
  }
  
  public String getOrgIdByUserID(String userId) throws Exception {
    String orgId = null;
    ParameterGenerator p = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("OrganizationEJB", "OrganizationEJBLocal", OrganizationEJBHome.class);
      p.put(userId, String.class);
      orgId = (String)ejbProxy.invoke("getOrgIdByUserID", p.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return orgId;
  }
  
  public String getAllSubOrgIdByOrgId(String orgId) throws Exception {
    String allSubOrgId = null;
    ParameterGenerator p = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("OrganizationEJB", "OrganizationEJBLocal", OrganizationEJBHome.class);
      p.put(orgId, String.class);
      allSubOrgId = (String)ejbProxy.invoke("getAllSubOrgIdByOrgId", p.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return allSubOrgId;
  }
  
  public void setOrder(String orgIdString, String orgOrderCode, String ordId) throws Exception {
    (new OrganizationEJBBean()).setOrder(orgIdString, orgOrderCode, ordId);
  }
  
  public void updateLeader(String empIds) {
    (new OrganizationEJBBean()).updateLeader(empIds);
  }
}
