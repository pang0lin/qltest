package com.js.oa.hr.personnelmanager.service;

import com.js.oa.hr.personnelmanager.bean.NewEmployeeEJBHome;
import com.js.system.vo.usermanager.EmployeeOtherInfoVO;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class NewEmployeeBD {
  private static Logger logger = Logger.getLogger(NewEmployeeBD.class.getName());
  
  public List selectSingle(Long empId) {
    List list = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("NewEmployeeEJB", "NewEmployeeEJBLocal", NewEmployeeEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(empId, "Long");
      list = (List)ejbProxy.invoke("selectSingle", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return list;
  }
  
  public int add(EmployeeVO employeeVO, EmployeeOtherInfoVO employeeOtherInfoVO, String orgId) {
    int addResult = 2;
    try {
      EJBProxy ejbProxy = new EJBProxy("NewEmployeeEJB", "NewEmployeeEJBLocal", NewEmployeeEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(employeeVO, EmployeeVO.class);
      pg.put(employeeOtherInfoVO, EmployeeOtherInfoVO.class);
      pg.put(orgId, "String");
      addResult = ((Integer)ejbProxy.invoke("add", pg.getParameters())).intValue();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return addResult;
  }
  
  public List postTitle(String postTitleSeries) {
    List list = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("NewEmployeeEJB", "NewEmployeeEJBLocal", NewEmployeeEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(postTitleSeries, String.class);
      list = (List)ejbProxy.invoke("postTitle", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return list;
  }
  
  public int update(EmployeeVO employeeVO, EmployeeOtherInfoVO employeeOtherInfoVO, String orgId, Long empId) {
    int addResult = 2;
    try {
      EJBProxy ejbProxy = new EJBProxy("NewEmployeeEJB", "NewEmployeeEJBLocal", NewEmployeeEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(employeeVO, EmployeeVO.class);
      pg.put(employeeOtherInfoVO, EmployeeOtherInfoVO.class);
      pg.put(orgId, "String");
      pg.put(empId, Long.class);
      addResult = ((Integer)ejbProxy.invoke("update", pg.getParameters())).intValue();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return addResult;
  }
  
  public int update(EmployeeVO employeeVO, EmployeeOtherInfoVO employeeOtherInfoVO, String orgId, Long empId, String[] log) {
    int addResult = 2;
    try {
      EJBProxy ejbProxy = new EJBProxy("NewEmployeeEJB", "NewEmployeeEJBLocal", NewEmployeeEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(5);
      pg.put(employeeVO, EmployeeVO.class);
      pg.put(employeeOtherInfoVO, EmployeeOtherInfoVO.class);
      pg.put(orgId, "String");
      pg.put(empId, Long.class);
      pg.put(log, String[].class);
      addResult = ((Integer)ejbProxy.invoke("update", pg.getParameters())).intValue();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return addResult;
  }
  
  public List getMaturityAlertSettings(String type, String domainId) {
    List list = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("NewEmployeeEJB", 
          "NewEmployeeEJBLocal", NewEmployeeEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(type, String.class);
      pg.put(domainId, String.class);
      list = (List)ejbProxy.invoke("getMaturityAlertSettings", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return list;
  }
  
  public Boolean saveMaturityAlertSettings(String type, String[][] args, String domainId) {
    Boolean ret = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("NewEmployeeEJB", 
          "NewEmployeeEJBLocal", NewEmployeeEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(type, String.class);
      pg.put(args, String[][].class);
      pg.put(domainId, String.class);
      ret = (Boolean)ejbProxy.invoke("saveMaturityAlertSettings", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return ret;
  }
  
  public String getMaturityAlertSettingsValue(String type, String code, String domainId) {
    String ret = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("NewEmployeeEJB", 
          "NewEmployeeEJBLocal", NewEmployeeEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(type, String.class);
      pg.put(code, String.class);
      pg.put(domainId, String.class);
      ret = (String)ejbProxy.invoke("getMaturityAlertSettingsValue", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return ret;
  }
  
  public Integer getLogCountByUserId(String userId) {
    Integer ret = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("NewEmployeeEJB", 
          "NewEmployeeEJBLocal", NewEmployeeEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(userId, String.class);
      ret = (Integer)ejbProxy.invoke("getLogCountByUserId", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return ret;
  }
  
  public Integer getLogCountByOrgId(String orgId) {
    Integer ret = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("NewEmployeeEJB", 
          "NewEmployeeEJBLocal", NewEmployeeEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(orgId, String.class);
      ret = (Integer)ejbProxy.invoke("getLogCountByOrgId", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return ret;
  }
}
