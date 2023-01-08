package com.js.oa.hr.personnelmanager.service;

import com.js.oa.hr.personnelmanager.bean.WorkAttendanceEJBHome;
import com.js.oa.hr.personnelmanager.po.WorkAttendancePO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class WorkAttendanceBD {
  private static Logger logger = Logger.getLogger(WorkAttendanceBD.class.getName());
  
  public void testMethod() {
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkAttendanceEJB", "WorkAttendanceEJBLocal", WorkAttendanceEJBHome.class);
      ejbProxy.invoke("testMethod", (Object[][])null);
    } catch (Exception e) {
      logger.error("error to testMethod information :" + e.getMessage());
    } 
  }
  
  public Boolean save(WorkAttendancePO workAttendancePO) {
    Boolean success = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(workAttendancePO, WorkAttendancePO.class);
      EJBProxy ejbProxy = new EJBProxy("WorkAttendanceEJB", "WorkAttendanceEJBLocal", WorkAttendanceEJBHome.class);
      success = (Boolean)ejbProxy.invoke("save", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to save information :" + e.getMessage());
    } 
    return success;
  }
  
  public List stat(String orgId, String year, String month) {
    ArrayList alist = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(orgId, String.class);
      pg.put(year, String.class);
      pg.put(month, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkAttendanceEJB", "WorkAttendanceEJBLocal", WorkAttendanceEJBHome.class);
      alist = (ArrayList)ejbProxy.invoke("stat", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to stat information :" + e.getMessage());
    } 
    return alist;
  }
  
  public Boolean delete(String ids) {
    Boolean success = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(ids, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkAttendanceEJB", "WorkAttendanceEJBLocal", WorkAttendanceEJBHome.class);
      success = (Boolean)ejbProxy.invoke("delete", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to save information :" + e.getMessage());
    } 
    return success;
  }
  
  public Object[] getSingle(String id) {
    Object[] alist = (Object[])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkAttendanceEJB", "WorkAttendanceEJBLocal", WorkAttendanceEJBHome.class);
      alist = (Object[])ejbProxy.invoke("getSingle", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to stat information :" + e.getMessage());
    } 
    return alist;
  }
  
  public Boolean update(WorkAttendancePO workAttendancePO) {
    Boolean success = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(workAttendancePO, WorkAttendancePO.class);
      EJBProxy ejbProxy = new EJBProxy("WorkAttendanceEJB", "WorkAttendanceEJBLocal", WorkAttendanceEJBHome.class);
      success = (Boolean)ejbProxy.invoke("update", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to update information :" + e.getMessage());
    } 
    return success;
  }
}
