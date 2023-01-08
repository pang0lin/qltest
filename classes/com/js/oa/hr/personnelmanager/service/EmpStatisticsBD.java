package com.js.oa.hr.personnelmanager.service;

import com.js.oa.hr.personnelmanager.bean.EmpStatisticsEJBHome;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

public class EmpStatisticsBD {
  private static Logger logger = Logger.getLogger(EmpStatisticsBD.class
      .getName());
  
  public Map listEmpChange(String orgId, String userId, String rightCode, String yearMonth, String empType, Integer pageSize, Integer pageNo) {
    Map<Object, Object> ret = new HashMap<Object, Object>(0);
    ParameterGenerator pg = new ParameterGenerator(7);
    try {
      EJBProxy ejbProxy = new EJBProxy("EmpStatisticsEJB", 
          "EmpStatisticsEJBLocal", 
          EmpStatisticsEJBHome.class);
      pg.put(orgId, String.class);
      pg.put(userId, String.class);
      pg.put(rightCode, String.class);
      pg.put(yearMonth, String.class);
      pg.put(empType, String.class);
      pg.put(pageSize, Integer.class);
      pg.put(pageNo, Integer.class);
      ret = (Map<Object, Object>)ejbProxy.invoke("listEmpChange", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to listEmpChange :" + 
          e.getMessage());
    } finally {}
    return ret;
  }
  
  public Map listEmpStruct(String orgId, String userId, String rightCode, String yearMonth, String empType, Integer pageSize, Integer pageNo) {
    Map<Object, Object> ret = new HashMap<Object, Object>(0);
    ParameterGenerator pg = new ParameterGenerator(7);
    try {
      EJBProxy ejbProxy = new EJBProxy("EmpStatisticsEJB", 
          "EmpStatisticsEJBLocal", 
          EmpStatisticsEJBHome.class);
      pg.put(orgId, String.class);
      pg.put(userId, String.class);
      pg.put(rightCode, String.class);
      pg.put(yearMonth, String.class);
      pg.put(empType, String.class);
      pg.put(pageSize, Integer.class);
      pg.put(pageNo, Integer.class);
      ret = (Map<Object, Object>)ejbProxy.invoke("listEmpStruct", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to listEmpStruct :" + 
          e.getMessage());
    } finally {}
    return ret;
  }
  
  public Map listEmpCizhi(String orgId, String userId, String rightCode, String yearMonth, String empType, Integer pageSize, Integer pageNo) {
    Map<Object, Object> ret = new HashMap<Object, Object>(0);
    ParameterGenerator pg = new ParameterGenerator(7);
    try {
      EJBProxy ejbProxy = new EJBProxy("EmpStatisticsEJB", 
          "EmpStatisticsEJBLocal", 
          EmpStatisticsEJBHome.class);
      pg.put(orgId, String.class);
      pg.put(userId, String.class);
      pg.put(rightCode, String.class);
      pg.put(yearMonth, String.class);
      pg.put(empType, String.class);
      pg.put(pageSize, Integer.class);
      pg.put(pageNo, Integer.class);
      ret = (Map<Object, Object>)ejbProxy.invoke("listEmpCizhi", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to listEmpCizhi :" + 
          e.getMessage());
    } finally {}
    return ret;
  }
  
  public Map listEmpZhuanzheng(String orgId, String userId, String rightCode, String yearMonth, String empType, Integer pageSize, Integer pageNo) {
    Map<Object, Object> ret = new HashMap<Object, Object>(0);
    ParameterGenerator pg = new ParameterGenerator(7);
    try {
      EJBProxy ejbProxy = new EJBProxy("EmpStatisticsEJB", 
          "EmpStatisticsEJBLocal", 
          EmpStatisticsEJBHome.class);
      pg.put(orgId, String.class);
      pg.put(userId, String.class);
      pg.put(rightCode, String.class);
      pg.put(yearMonth, String.class);
      pg.put(empType, String.class);
      pg.put(pageSize, Integer.class);
      pg.put(pageNo, Integer.class);
      ret = (Map<Object, Object>)ejbProxy.invoke("listEmpZhuanzheng", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to listEmpZhuanzheng :" + 
          e.getMessage());
    } finally {}
    return ret;
  }
}
