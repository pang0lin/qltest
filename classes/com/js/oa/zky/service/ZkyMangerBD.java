package com.js.oa.zky.service;

import com.js.oa.zky.bean.ZkyMangerEJBHome;
import com.js.oa.zky.po.ZkyMangersPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.List;
import org.apache.log4j.Logger;

public class ZkyMangerBD {
  private static Logger logger = Logger.getLogger(ZkyMangerBD.class.getName());
  
  public List list(String mode, Long domainID) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(mode, String.class);
      pg.put(domainID, Long.class);
      EJBProxy ejbProxy = new EJBProxy("ZkyMangerEJB", 
          "ZkyMangerEJBLocal", ZkyMangerEJBHome.class);
      list = (List)ejbProxy.invoke("list", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  ZkyMangerEJB list information :" + e.getMessage());
      e.printStackTrace();
    } 
    return list;
  }
  
  public Boolean save(ZkyMangersPO po) {
    Boolean success = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(po, ZkyMangersPO.class);
      EJBProxy ejbProxy = new EJBProxy("ZkyMangerEJB", 
          "ZkyMangerEJBLocal", ZkyMangerEJBHome.class);
      success = (Boolean)ejbProxy.invoke("save", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  ZkyMangerBD save information :" + e.getMessage());
    } 
    return success;
  }
  
  public ZkyMangersPO moudleMangerLoad(Long id) {
    ZkyMangersPO po = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, Long.class);
      EJBProxy ejbProxy = new EJBProxy("ZkyMangerEJB", 
          "ZkyMangerEJBLocal", ZkyMangerEJBHome.class);
      po = (ZkyMangersPO)ejbProxy.invoke("moudleMangerLoad", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  ZkyMangerBD moudleMangerLoad information :" + 
          e.getMessage());
    } 
    return po;
  }
  
  public Boolean moudleMangerUpdate(ZkyMangersPO po, Long id) {
    Boolean ret = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(po, ZkyMangersPO.class);
      pg.put(id, Long.class);
      EJBProxy ejbProxy = new EJBProxy("ZkyMangerEJB", 
          "ZkyMangerEJBLocal", ZkyMangerEJBHome.class);
      ret = (Boolean)ejbProxy.invoke("moudleMangerUpdate", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  ZkyMangerBD moudleMangerUpdate information :" + 
          e.getMessage());
    } 
    return ret;
  }
  
  public Boolean deleteMoudleManger(String id) {
    Boolean ret = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, String.class);
      EJBProxy ejbProxy = new EJBProxy("ZkyMangerEJB", 
          "ZkyMangerEJBLocal", ZkyMangerEJBHome.class);
      ret = (Boolean)ejbProxy.invoke("deleteMoudleManger", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  ZkyMangerBD deleteMoudleManger information :" + 
          e.getMessage());
    } 
    return ret;
  }
}
