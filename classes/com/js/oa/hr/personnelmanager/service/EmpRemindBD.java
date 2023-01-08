package com.js.oa.hr.personnelmanager.service;

import com.js.oa.hr.personnelmanager.bean.EmpRemindEJBHome;
import com.js.oa.hr.personnelmanager.po.EmpRemindPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class EmpRemindBD {
  private static Logger logger = Logger.getLogger(EmpRemindBD.class.getName());
  
  public Boolean save(EmpRemindPO po) {
    Boolean success = new Boolean(false);
    try {
      EJBProxy ejbProxy = new EJBProxy("EmpRemindEJB", "EmpRemindEJBLocal", EmpRemindEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(po, EmpRemindPO.class);
      success = (Boolean)ejbProxy.invoke("save", pg.getParameters());
    } catch (Exception e) {
      logger.info(e);
    } 
    return success;
  }
  
  public Boolean modify(EmpRemindPO po) {
    Boolean success = new Boolean(false);
    try {
      EJBProxy ejbProxy = new EJBProxy("EmpRemindEJB", "EmpRemindEJBLocal", EmpRemindEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(po, EmpRemindPO.class);
      success = (Boolean)ejbProxy.invoke("modify", pg.getParameters());
    } catch (Exception e) {
      logger.info(e);
    } 
    return success;
  }
  
  public Boolean delete(String id) {
    Boolean success = new Boolean(false);
    try {
      EJBProxy ejbProxy = new EJBProxy("EmpRemindEJB", "EmpRemindEJBLocal", EmpRemindEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, String.class);
      success = (Boolean)ejbProxy.invoke("delete", pg.getParameters());
    } catch (Exception e) {
      logger.info(e);
    } 
    return success;
  }
  
  public List selectRemindList() throws Exception {
    List alist = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("EmpRemindEJB", "EmpRemindEJBLocal", EmpRemindEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(0);
      alist = (List)ejbProxy.invoke("selectRemindList", pg.getParameters());
    } catch (Exception e) {
      logger.info(e);
    } 
    return alist;
  }
  
  public EmpRemindPO load(Long id) throws Exception {
    EmpRemindPO empRemindPO = new EmpRemindPO();
    try {
      EJBProxy ejbProxy = new EJBProxy("EmpRemindEJB", "EmpRemindEJBLocal", EmpRemindEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, Long.class);
      empRemindPO = (EmpRemindPO)ejbProxy.invoke("load", pg.getParameters());
    } catch (Exception e) {
      logger.info(e);
    } 
    return empRemindPO;
  }
}
