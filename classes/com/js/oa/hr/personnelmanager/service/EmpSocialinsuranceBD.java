package com.js.oa.hr.personnelmanager.service;

import com.js.oa.hr.personnelmanager.bean.EmpSocialinsuranceEJBHome;
import com.js.oa.hr.personnelmanager.po.EmpSocialinsurancePO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import org.apache.log4j.Logger;

public class EmpSocialinsuranceBD {
  private static Logger logger = Logger.getLogger(EmpSocialinsuranceBD.class
      .getName());
  
  public Boolean delete(Long id) {
    Boolean result = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EmpSocialinsuranceEJB", 
          "EmpSocialinsuranceEJBLocal", 
          EmpSocialinsuranceEJBHome.class);
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
  
  public Boolean save(EmpSocialinsurancePO po) {
    Boolean result = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EmpSocialinsuranceEJB", 
          "EmpSocialinsuranceEJBLocal", 
          EmpSocialinsuranceEJBHome.class);
      pg.put(po, EmpSocialinsurancePO.class);
      result = (Boolean)ejbProxy.invoke("save", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to save information :" + 
          e.getMessage());
    } 
    return result;
  }
  
  public EmpSocialinsurancePO load(Long id) {
    EmpSocialinsurancePO po = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EmpSocialinsuranceEJB", 
          "EmpSocialinsuranceEJBLocal", 
          EmpSocialinsuranceEJBHome.class);
      pg.put(id, Long.class);
      po = (EmpSocialinsurancePO)ejbProxy.invoke(
          "load", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to load information :" + 
          e.getMessage());
    } 
    return po;
  }
  
  public Boolean modify(EmpSocialinsurancePO po) {
    Boolean result = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EmpSocialinsuranceEJB", 
          "EmpSocialinsuranceEJBLocal", 
          EmpSocialinsuranceEJBHome.class);
      pg.put(po, EmpSocialinsurancePO.class);
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
      EJBProxy ejbProxy = new EJBProxy("EmpSocialinsuranceEJB", 
          "EmpSocialinsuranceEJBLocal", 
          EmpSocialinsuranceEJBHome.class);
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
