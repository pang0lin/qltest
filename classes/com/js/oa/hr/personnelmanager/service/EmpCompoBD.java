package com.js.oa.hr.personnelmanager.service;

import com.js.oa.hr.personnelmanager.bean.EmpCompoEJBHome;
import com.js.oa.hr.personnelmanager.po.EmpCompoPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import org.apache.log4j.Logger;

public class EmpCompoBD {
  private static Logger logger = Logger.getLogger(EmpCompoBD.class
      .getName());
  
  public Boolean delete(Long id) {
    Boolean result = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EmpCompoEJB", 
          "EmpCompoEJBLocal", 
          EmpCompoEJBHome.class);
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
  
  public Boolean save(EmpCompoPO po) {
    Boolean result = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EmpCompoEJB", 
          "EmpCompoEJBLocal", 
          EmpCompoEJBHome.class);
      pg.put(po, EmpCompoPO.class);
      result = (Boolean)ejbProxy.invoke("save", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to save information :" + 
          e.getMessage());
    } 
    return result;
  }
  
  public EmpCompoPO load(Long id) {
    EmpCompoPO po = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EmpCompoEJB", 
          "EmpCompoEJBLocal", 
          EmpCompoEJBHome.class);
      pg.put(id, Long.class);
      po = (EmpCompoPO)ejbProxy.invoke(
          "load", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to load information :" + 
          e.getMessage());
    } 
    return po;
  }
  
  public Boolean modify(EmpCompoPO po) {
    Boolean result = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EmpCompoEJB", 
          "EmpCompoEJBLocal", 
          EmpCompoEJBHome.class);
      pg.put(po, EmpCompoPO.class);
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
      EJBProxy ejbProxy = new EJBProxy("EmpCompoEJB", 
          "EmpCompoEJBLocal", 
          EmpCompoEJBHome.class);
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
}
