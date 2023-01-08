package com.js.oa.hr.personnelmanager.service;

import com.js.oa.hr.personnelmanager.bean.EmpAttendanceEJBHome;
import com.js.oa.hr.personnelmanager.po.EmpAttendancePO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import org.apache.log4j.Logger;

public class EmpAttendanceBD {
  public Boolean batchDel(String ids) {
    Boolean result = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      EJBProxy ejbProxy = new EJBProxy("EmpAttendanceEJB", "EmpAttendanceEJBLocal", EmpAttendanceEJBHome.class);
      pg.put(ids, String.class);
      result = (Boolean)ejbProxy.invoke("batchDel", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to batchDel information :" + e.getMessage());
    } 
    return result;
  }
  
  public boolean checkExists(Long userId, Long id, String yearMonth) {
    boolean ret = false;
    try {
      EJBProxy ejbProxy = new EJBProxy("EmpAttendanceEJB", "EmpAttendanceEJBLocal", EmpAttendanceEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(userId, Long.class);
      pg.put(id, Long.class);
      pg.put(yearMonth, String.class);
      ret = ((Boolean)ejbProxy.invoke("checkExists", pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.info("指定userId,yearMonth的记录时出错:" + e);
    } 
    return ret;
  }
  
  public Boolean delete(Long id) {
    Boolean result = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EmpAttendanceEJB", "EmpAttendanceEJBLocal", EmpAttendanceEJBHome.class);
      pg.put(id, Long.class);
      result = (Boolean)ejbProxy.invoke("delete", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to delete information :" + e.getMessage());
    } 
    return result;
  }
  
  public EmpAttendancePO load(Long id) {
    EmpAttendancePO po = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EmpAttendanceEJB", "EmpAttendanceEJBLocal", EmpAttendanceEJBHome.class);
      pg.put(id, Long.class);
      po = (EmpAttendancePO)ejbProxy.invoke("load", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to load information :" + e.getMessage());
    } 
    return po;
  }
  
  public Boolean modify(EmpAttendancePO po) {
    Boolean result = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EmpAttendanceEJB", "EmpAttendanceEJBLocal", EmpAttendanceEJBHome.class);
      pg.put(po, EmpAttendancePO.class);
      result = (Boolean)ejbProxy.invoke("modify", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to modify information :" + e.getMessage());
    } 
    return result;
  }
  
  public Boolean save(EmpAttendancePO po) {
    Boolean result = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EmpAttendanceEJB", "EmpAttendanceEJBLocal", EmpAttendanceEJBHome.class);
      pg.put(po, EmpAttendancePO.class);
      result = (Boolean)ejbProxy.invoke("save", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to save information :" + e.getMessage());
    } 
    return result;
  }
  
  private static Logger logger = Logger.getLogger(EmpAttendanceBD.class.getName());
}
