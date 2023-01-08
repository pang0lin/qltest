package com.js.system.service.groupmanager;

import com.js.system.bean.groupmanager.GroupEJBBean;
import com.js.system.bean.groupmanager.GroupEJBHome;
import com.js.system.vo.groupmanager.GroupVO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class GroupBD {
  private static Logger logger = Logger.getLogger(GroupBD.class.getName());
  
  public int add(GroupVO groupVO, String[] id, HttpServletRequest httpServletRequest) {
    int addResult = 2;
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("GroupEJB", "GroupEJBLocal", GroupEJBHome.class);
      pg.put(groupVO, GroupVO.class);
      pg.put(id, String[].class);
      pg.put(httpServletRequest, HttpServletRequest.class);
      addResult = ((Integer)ejbProxy.invoke("add", pg.getParameters())).intValue();
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("Error to add Group information:" + e.getMessage());
    } finally {}
    return addResult;
  }
  
  public String del(String[] id, HttpServletRequest httpServletRequest) {
    String delResult = "";
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("GroupEJB", "GroupEJBLocal", GroupEJBHome.class);
      pg.put(id, String[].class);
      pg.put(httpServletRequest, HttpServletRequest.class);
      delResult = (String)ejbProxy.invoke("del", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to del Group information:" + e.getMessage());
    } finally {}
    return delResult;
  }
  
  public boolean delAll() {
    boolean delResult = false;
    try {
      EJBProxy ejbProxy = new EJBProxy("GroupEJB", "GroupEJBLocal", GroupEJBHome.class);
      ejbProxy.invoke("delAll", (Object[][])null);
      delResult = true;
    } catch (Exception e) {
      logger.error("Error to delAll Group information:" + e.getMessage());
    } finally {}
    return delResult;
  }
  
  public List selectSingle(String id) {
    List list = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      pg.put(id, String.class);
      EJBProxy ejbProxy = new EJBProxy("GroupEJB", "GroupEJBLocal", GroupEJBHome.class);
      list = (List)ejbProxy.invoke("selectSingle", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to select Group information:" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List select() {
    List list = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("GroupEJB", "GroupEJBLocal", GroupEJBHome.class);
      list = (List)ejbProxy.invoke("select", (Object[][])null);
    } catch (Exception e) {
      logger.error("Error to select Group information:" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List selectGroupUser(String id) {
    List list = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      pg.put(id, String.class);
      EJBProxy ejbProxy = new EJBProxy("GroupEJB", "GroupEJBLocal", GroupEJBHome.class);
      list = (List)ejbProxy.invoke("selectGroupUser", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to selectGroupUser information:" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List selectGroupUserEmail(String id) {
    List list = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      pg.put(id, String.class);
      EJBProxy ejbProxy = new EJBProxy("GroupEJB", "GroupEJBLocal", GroupEJBHome.class);
      list = (List)ejbProxy.invoke("selectGroupUserEmail", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to selectGroupUser information:" + e.getMessage());
    } finally {}
    return list;
  }
  
  public int update(String groupId, String groupName, String groupUserString, String[] userId, String groupUserNames, String createdOrg, String rangeName, String rangeEmp, String rangeOrg, String rangeGroup, String groupType, String groupOrder, HttpServletRequest httpServletRequest) {
    ParameterGenerator pg = new ParameterGenerator(13);
    int result = 2;
    try {
      pg.put(groupId, String.class);
      pg.put(groupName, String.class);
      pg.put(groupUserString, String.class);
      pg.put(userId, String[].class);
      pg.put(groupUserNames, "String");
      pg.put(createdOrg, "String");
      pg.put(rangeName, "String");
      pg.put(rangeEmp, "String");
      pg.put(rangeOrg, "String");
      pg.put(rangeGroup, "String");
      pg.put(groupType, "String");
      pg.put(groupOrder, "String");
      pg.put(httpServletRequest, HttpServletRequest.class);
      EJBProxy ejbProxy = new EJBProxy("GroupEJB", "GroupEJBLocal", GroupEJBHome.class);
      result = ((Integer)ejbProxy.invoke("update", pg.getParameters())).intValue();
    } catch (Exception e) {
      logger.error("Error to update group information:" + e.getMessage());
    } finally {}
    return result;
  }
  
  public List selectPersonUser(String id) {
    List list = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      pg.put(id, String.class);
      EJBProxy ejbProxy = new EJBProxy("GroupEJB", "GroupEJBLocal", GroupEJBHome.class);
      list = (List)ejbProxy.invoke("selectPersonUser", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      logger.error("Error to selectPersonUser information:" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List checkGroupByName(String name, String id) {
    List list = null;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      pg.put(name, String.class);
      pg.put(id, String.class);
      EJBProxy ejbProxy = new EJBProxy("GroupEJB", "GroupEJBLocal", GroupEJBHome.class);
      list = (List)ejbProxy.invoke("checkGroupByName", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      logger.error("Error to selectPersonUser information:" + e.getMessage());
    } finally {}
    return list;
  }
  
  public Integer saveAsGroup(String name, String id, String groupName, String domainId, String orgId, String empId) {
    ParameterGenerator pg = new ParameterGenerator(6);
    Integer i = Integer.valueOf(0);
    try {
      pg.put(name, String.class);
      pg.put(id, String.class);
      pg.put(groupName, String.class);
      pg.put(domainId, String.class);
      pg.put(orgId, String.class);
      pg.put(empId, String.class);
      EJBProxy ejbProxy = new EJBProxy("GroupEJB", "GroupEJBLocal", GroupEJBHome.class);
      i = (Integer)ejbProxy.invoke("saveAsGroup", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      logger.error("Error to selectPersonUser information:" + e.getMessage());
    } finally {}
    return i;
  }
  
  public List searchByUserid(long userId) throws Exception {
    List list = null;
    GroupEJBBean groupEJBBean = new GroupEJBBean();
    list = groupEJBBean.searchByUserid(userId);
    return list;
  }
}
