package com.js.system.service.rightmanager;

import com.js.system.bean.rightmanager.RightEJBHome;
import com.js.system.vo.rightmanager.RightVO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.List;
import org.apache.log4j.Logger;

public class RightBD {
  private static Logger logger = Logger.getLogger(RightVO.class.getName());
  
  public List getRightType() {
    List list = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("RightEJB", "RightEJBLocal", RightEJBHome.class);
      list = (List)ejbProxy.invoke("getRightType", (Object[][])null);
    } catch (Exception e) {
      logger.error("error to selectTypeAndName information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getRightIdAndName(String rightType) {
    List list = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("RightEJB", "RightEJBLocal", RightEJBHome.class);
      pg.put(rightType, String.class);
      list = (List)ejbProxy.invoke("getRightIdAndName", (Object[][])null);
    } catch (Exception e) {
      logger.error("error to getRightIdAndName information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getIdTypeName(String domainId) {
    List list = null;
    ParameterGenerator pg = new ParameterGenerator(2);
    pg.put("1", "String");
    pg.put(domainId, String.class);
    try {
      EJBProxy ejbProxy = new EJBProxy("RightEJB", "RightEJBLocal", RightEJBHome.class);
      list = (List)ejbProxy.invoke("getIdTypeName", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getIdTypeName information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getIdTypeName(String manager, String domainId) {
    List list = null;
    ParameterGenerator pg = new ParameterGenerator(2);
    pg.put(manager, "String");
    pg.put(domainId, "String");
    try {
      EJBProxy ejbProxy = new EJBProxy("RightEJB", "RightEJBLocal", RightEJBHome.class);
      list = (List)ejbProxy.invoke("getIdTypeName", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getIdTypeName information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getRightInfo(String rightId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(rightId, String.class);
      EJBProxy ejbProxy = new EJBProxy("RightEJB", "RightEJBLocal", RightEJBHome.class);
      list = (List)ejbProxy.invoke("getRightInfo", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getRightInfo information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getRoleId(String rightId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(rightId, String.class);
      EJBProxy ejbProxy = new EJBProxy("RightEJB", "RightEJBLocal", RightEJBHome.class);
      list = (List)ejbProxy.invoke("getRoleId", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getRoleId information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public boolean updateRole(String rightId, String[] roleId) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(rightId, String.class);
      pg.put(roleId, String[].class);
      EJBProxy ejbProxy = new EJBProxy("RightEJB", "RightEJBLocal", RightEJBHome.class);
      ejbProxy.invoke("updateRole", pg.getParameters());
      result = true;
    } catch (Exception e) {
      logger.error("error to updateRole information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public List getUserRightScope(String userId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(userId, String.class);
      EJBProxy ejbProxy = new EJBProxy("RightEJB", "RightEJBLocal", RightEJBHome.class);
      list = (List)ejbProxy.invoke("getUserRightScope", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getUserRightScope information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getAuditRightScope(String logId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(logId, String.class);
      EJBProxy ejbProxy = new EJBProxy("RightEJB", "RightEJBLocal", RightEJBHome.class);
      list = (List)ejbProxy.invoke("getAuditRightScope", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getUserRightScope information :" + e.getMessage());
    } finally {}
    return list;
  }
}
