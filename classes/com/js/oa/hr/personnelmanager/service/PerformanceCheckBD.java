package com.js.oa.hr.personnelmanager.service;

import com.js.oa.hr.personnelmanager.bean.PerformanceCheckEJBHome;
import com.js.oa.hr.personnelmanager.po.PerformanceCheckPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import org.apache.log4j.Logger;

public class PerformanceCheckBD {
  private static Logger logger = Logger.getLogger(PerformanceCheckBD.class
      .getName());
  
  public Boolean delete(Long id) {
    Boolean result = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("PerformanceCheckEJB", 
          "PerformanceCheckEJBLocal", 
          PerformanceCheckEJBHome.class);
      pg.put(id, Long.class);
      result = (Boolean)ejbProxy.invoke("delete", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to delete information :" + 
          e.getMessage());
    } 
    return result;
  }
  
  public Boolean save(PerformanceCheckPO po) {
    Boolean result = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("PerformanceCheckEJB", 
          "PerformanceCheckEJBLocal", 
          PerformanceCheckEJBHome.class);
      pg.put(po, PerformanceCheckPO.class);
      result = (Boolean)ejbProxy.invoke("save", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to save information :" + 
          e.getMessage());
    } 
    return result;
  }
  
  public PerformanceCheckPO load(Long id) {
    PerformanceCheckPO po = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("PerformanceCheckEJB", 
          "PerformanceCheckEJBLocal", 
          PerformanceCheckEJBHome.class);
      pg.put(id, Long.class);
      po = (PerformanceCheckPO)ejbProxy.invoke(
          "load", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to load information :" + 
          e.getMessage());
    } 
    return po;
  }
  
  public Boolean modify(PerformanceCheckPO po) {
    Boolean result = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("PerformanceCheckEJB", 
          "PerformanceCheckEJBLocal", 
          PerformanceCheckEJBHome.class);
      pg.put(po, PerformanceCheckPO.class);
      result = (Boolean)ejbProxy.invoke("modify", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to modify information :" + 
          e.getMessage());
    } 
    return result;
  }
  
  public Boolean batchDel(String ids) {
    Boolean result = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("PerformanceCheckEJB", 
          "PerformanceCheckEJBLocal", 
          PerformanceCheckEJBHome.class);
      pg.put(ids, String.class);
      result = (Boolean)ejbProxy.invoke("batchDel", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to batchDel information :" + 
          e.getMessage());
    } 
    return result;
  }
  
  public boolean checkExists(Long userId, Long id, String year, String month) {
    boolean ret = false;
    try {
      EJBProxy ejbProxy = new EJBProxy("PerformanceCheckEJB", 
          "PerformanceCheckEJBLocal", 
          PerformanceCheckEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(userId, Long.class);
      pg.put(id, Long.class);
      pg.put(year, String.class);
      pg.put(month, String.class);
      ret = ((Boolean)ejbProxy.invoke("checkExists", pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.info("指定userId,year,month的记录时出错:" + e);
    } 
    return ret;
  }
}
