package com.js.oa.hr.examination.service;

import com.js.oa.hr.examination.bean.ExaminationSelfTestEJBHome;
import com.js.oa.hr.examination.po.ExaminationSelfTestPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import org.apache.log4j.Logger;

public class ExaminationSelfTestBD {
  private static Logger logger = Logger.getLogger(ExaminationSelfTestBD.class
      .getName());
  
  public Long save(ExaminationSelfTestPO po, Object[] para) {
    Long ret = new Long(0L);
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(po, ExaminationSelfTestPO.class);
      pg.put(para, Object[].class);
      EJBProxy ejbProxy = new EJBProxy(
          "ExaminationSelfTestEJB", 
          "ExaminationSelfTestEJBLocal", ExaminationSelfTestEJBHome.class);
      ret = (Long)ejbProxy.invoke("save", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to ExaminationSelfTestBD.save :" + e.getMessage());
    } 
    return ret;
  }
  
  public String[] result(Long id) {
    String[] ret = (String[])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, Long.class);
      EJBProxy ejbProxy = new EJBProxy(
          "ExaminationSelfTestEJB", 
          "ExaminationSelfTestEJBLocal", ExaminationSelfTestEJBHome.class);
      ret = (String[])ejbProxy.invoke("result", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to ExaminationSelfTestBD.result :" + 
          e.getMessage());
    } 
    return ret;
  }
  
  public ExaminationSelfTestPO load(Long id) {
    ExaminationSelfTestPO ret = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, Long.class);
      EJBProxy ejbProxy = new EJBProxy(
          "ExaminationSelfTestEJB", 
          "ExaminationSelfTestEJBLocal", ExaminationSelfTestEJBHome.class);
      ret = (ExaminationSelfTestPO)ejbProxy.invoke("load", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to ExaminationSelfTestBD.load :" + e.getMessage());
    } 
    return ret;
  }
  
  public boolean delete(Long id) {
    boolean ret = true;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, Long.class);
      EJBProxy ejbProxy = new EJBProxy(
          "ExaminationSelfTestEJB", 
          "ExaminationSelfTestEJBLocal", ExaminationSelfTestEJBHome.class);
      ret = ((Boolean)ejbProxy.invoke("delete", 
          pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.error("error to ExaminationSelfTestBD.delete :" + 
          e.getMessage());
    } 
    return ret;
  }
  
  public boolean deleteBatch(String ids) {
    boolean ret = true;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(ids, String.class);
      EJBProxy ejbProxy = new EJBProxy(
          "ExaminationSelfTestEJB", 
          "ExaminationSelfTestEJBLocal", ExaminationSelfTestEJBHome.class);
      ret = ((Boolean)ejbProxy.invoke("deleteBatch", 
          pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.error("error to ExaminationSelfTestBD.deleteBatch :" + 
          e.getMessage());
    } 
    return ret;
  }
}
