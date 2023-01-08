package com.js.oa.routine.resource.service;

import com.js.oa.routine.resource.bean.DrawDeptEJBHome;
import com.js.oa.routine.resource.po.DrawDeptPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class DrawDeptBD {
  private static Logger logger = Logger.getLogger(DrawDeptBD.class.getName());
  
  public Boolean save(DrawDeptPO drawDeptPO, String stockId) {
    Boolean success = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(drawDeptPO, DrawDeptPO.class);
      pg.put(stockId, String.class);
      EJBProxy ejbProxy = new EJBProxy("DrawDeptEJB", "DrawDeptEJBLocal", DrawDeptEJBHome.class);
      success = (Boolean)ejbProxy.invoke("save", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to save information :" + e.getMessage());
    } 
    return success;
  }
  
  public Boolean delete(String ids) {
    Boolean success = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(ids, String.class);
      EJBProxy ejbProxy = new EJBProxy("DrawDeptEJB", "DrawDeptEJBLocal", DrawDeptEJBHome.class);
      success = (Boolean)ejbProxy.invoke("delete", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to delete information :" + e.getMessage());
    } 
    return success;
  }
  
  public String[] getSingleDept(String drawDeptId) {
    String[] drawDept = { "", "", "" };
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(drawDeptId, String.class);
      EJBProxy ejbProxy = new EJBProxy("DrawDeptEJB", "DrawDeptEJBLocal", DrawDeptEJBHome.class);
      drawDept = (String[])ejbProxy.invoke("getSingleDept", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getSingleDept information :" + e.getMessage());
    } 
    return drawDept;
  }
  
  public Boolean update(DrawDeptPO drawDeptPO, String stockId) {
    Boolean success = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(drawDeptPO, DrawDeptPO.class);
      pg.put(stockId, String.class);
      EJBProxy ejbProxy = new EJBProxy("DrawDeptEJB", "DrawDeptEJBLocal", DrawDeptEJBHome.class);
      success = (Boolean)ejbProxy.invoke("update", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to update information :" + e.getMessage());
    } 
    return success;
  }
  
  public String getVindicate(String where) {
    String ids = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(where, String.class);
      EJBProxy ejbProxy = new EJBProxy("DrawDeptEJB", "DrawDeptEJBLocal", DrawDeptEJBHome.class);
      ids = (String)ejbProxy.invoke("getVindicate", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to update information :" + e.getMessage());
    } 
    return ids;
  }
  
  public List getDeptInStock(String stockId) {
    ArrayList alist = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(stockId, String.class);
      EJBProxy ejbProxy = new EJBProxy("DrawDeptEJB", "DrawDeptEJBLocal", DrawDeptEJBHome.class);
      alist = (ArrayList)ejbProxy.invoke("getDeptInStock", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getDeptInStock information :" + e.getMessage());
    } 
    return alist;
  }
  
  public List getUserManaDept(String userId) {
    ArrayList alist = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(userId, String.class);
      EJBProxy ejbProxy = new EJBProxy("DrawDeptEJB", "DrawDeptEJBLocal", DrawDeptEJBHome.class);
      alist = (ArrayList)ejbProxy.invoke("getUserManaDept", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getUserManaDept information :" + e.getMessage());
    } 
    return alist;
  }
}
